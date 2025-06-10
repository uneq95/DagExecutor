package com.ritesh.dag.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class DagExecutor {
    private final ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    public DagExecutor(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @Async
    public CompletableFuture<Void> executeDag(Dag dag) {
        Map<String, CompletableFuture<Void>> taskFutures = new HashMap<>();

        // Prepare CompletableFutures for all tasks
        dag.getTasks().forEach(task -> taskFutures.put(task.getName(), new CompletableFuture<>()));

        // Setup task execution considering dependencies
        dag.getTasks().forEach(task -> setupTaskExecution(task, taskFutures));

        // Return future that completes when all tasks are done
        return CompletableFuture.allOf(
            taskFutures.values().toArray(new CompletableFuture[0])
        );
    }

    private void setupTaskExecution(AbstractTask task, Map<String, CompletableFuture<Void>> taskFutures) {
        CompletableFuture<Void> taskFuture = taskFutures.get(task.getName());

        // Get all dependency futures
        CompletableFuture<?>[] dependencyFutures = task.getParentTasks().stream()
                .map(parent -> taskFutures.get(parent.getName()))
                .toArray(CompletableFuture[]::new);

        // When all dependencies complete, execute this task
        CompletableFuture.allOf(dependencyFutures)
            .thenRunAsync(() -> {
                try {
                    task.run();
                    taskFuture.complete(null);
                } catch (Exception e) {
                    taskFuture.completeExceptionally(e);
                }
            }, taskExecutor);
    }
}