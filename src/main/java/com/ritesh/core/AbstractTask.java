package com.ritesh.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Slf4j
public abstract class AbstractTask implements Runnable {

    private String name;
    private Set<AbstractTask> nextTasks = new HashSet<>();
    private Set<AbstractTask> parentTasks = new HashSet<>();

    public AbstractTask(){}

    public AbstractTask(String name){
        this.name = name;
    }
    private void addNextTask(@NonNull AbstractTask task) {
        if (this == task) throw new IllegalArgumentException("Can not self reference the next task");
        nextTasks.add(task);
    }

    private void addNextTasks(@NonNull Set<AbstractTask> tasks) {
        if (tasks.stream().anyMatch(task -> this == task)) throw new IllegalArgumentException("Can not self reference the next task");
        nextTasks.addAll(tasks);
    }
    public void beforeCore(){
        //log.info("Before core logic call");
    }

    public void afterCore(){
        //log.info("After core logic call");
    }

    public void addDependency(AbstractTask parentTask){
        if (this == parentTask) throw new IllegalArgumentException("Can not self reference as the dependent task");
        this.parentTasks.add(parentTask);
        this.parentTasks.forEach(pTask -> pTask.addNextTask(this));
    }

    public void addDependencies(Set<AbstractTask> parentTasks){
        parentTasks.forEach(this::addDependency);
    }

    public abstract void core();

    @Override
    public void run() {
        beforeCore();
        core();
        afterCore();
    }

}
