package com.ritesh.core;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
public class DagExecutor {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static final DagExecutor dagExecutor = new DagExecutor();

    public static DagExecutor getInstance() {
        return dagExecutor;
    }


    public void executeDag(Dag dag) {
        Map<String, CompletableFuture<Void>> taskFutures = new HashMap<>();

        // Prepare CompletableFutures for all tasks, initially incomplete
        dag.getTasks().forEach(task -> taskFutures.put(task.getName(), new CompletableFuture<>()));

        // Setup task execution considering dependencies
        dag.getTasks().forEach(task -> setupTaskExecution(task, taskFutures));

        // Wait for all tasks to complete and then shut down the executor
        CompletableFuture<Void> allTasksComplete = CompletableFuture.allOf(taskFutures.values().toArray(new CompletableFuture[0]));
        allTasksComplete.whenComplete((result, throwable) -> {
            if (throwable != null) {
                System.out.println("Errors occurred during DAG execution: " + throwable.getMessage());
            }
        });
    }

    private static void setupTaskExecution(AbstractTask task, Map<String, CompletableFuture<Void>> taskFutures) {
        CompletableFuture<Void> taskFuture = taskFutures.get(task.getName());

        List<CompletableFuture<Void>> dependencyFutures = task.getParentTasks().stream()
                .map(key -> taskFutures.get(key.getName()))
                .collect(Collectors.toList());

        CompletableFuture<Void> allDependencies = CompletableFuture.allOf(dependencyFutures.toArray(new CompletableFuture[0]));

        // Run the task after all dependencies are complete
        allDependencies.thenRunAsync(() -> {
            try {
                task.run();
                taskFuture.complete(null); // Mark this task's future as complete upon successful execution
            } catch (Exception e) {
                taskFuture.completeExceptionally(e); // Propagate error if task execution fails
            }
        }, executorService);
    }

}
