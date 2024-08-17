package com.ritesh.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

@Getter
@Setter
@Slf4j
public abstract class AbstractTask {

    private String name;
    private Set<AbstractTask> nextTasks = new HashSet<>();
    private Set<AbstractTask> parentTasks = new HashSet<>();
    private Callable<?> taskLogic;

    private Callable<?> beforeCore;
    private Callable<?> afterLogic;

    public AbstractTask(@NonNull String name, @NonNull Callable<?> taskLogic) {
        this.name = name;
        this.taskLogic = taskLogic;
    }

    private void addNextTask(@NonNull AbstractTask task) {
        if (this == task) throw new IllegalArgumentException("Can not self reference the next task");
        nextTasks.add(task);
    }

    private void addNextTasks(@NonNull Set<AbstractTask> tasks) {
        if (tasks.stream().anyMatch(task -> this == task))
            throw new IllegalArgumentException("Can not self reference the next task");
        nextTasks.addAll(tasks);
    }

    public void addDependency(AbstractTask parentTask) {
        if (this == parentTask) throw new IllegalArgumentException("Can not self reference as the dependent task");
        this.parentTasks.add(parentTask);
        this.parentTasks.forEach(pTask -> pTask.addNextTask(this));
    }

    public void addDependencies(Set<AbstractTask> parentTasks) {
        parentTasks.forEach(this::addDependency);
    }

    public Object invoke() throws Exception {
        if (beforeCore != null) beforeCore.call();
        Object result = taskLogic.call();
        if (afterLogic != null) afterLogic.call();
        return result;
    }

    public void dependsOn(@NonNull AbstractTask task) {
        this.addDependency(task);
    }

    public void dependsOn(@NonNull Set<AbstractTask> tasks) {
        tasks.forEach(this::dependsOn);
    }
}
