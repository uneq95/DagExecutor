package com.ritesh.core;

import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class Dag {

    private String name;
    private Set<AbstractTask> tasks;
    private Set<AbstractTask> leafTasks;
    private Set<AbstractTask> taskHeadsPerConnectedGroup;

    public Dag(String name, Set<AbstractTask> tasks) {
        this.name = name;
        this.tasks = tasks;
        this.taskHeadsPerConnectedGroup = this.calculateConnectedComponents();
        this.leafTasks = this.calculateLeafTasks();
    }

    private Set<AbstractTask> calculateConnectedComponents() {
        return this.tasks.stream()
                .filter(task -> task.getParentTasks().isEmpty()).collect(Collectors.toSet());
    }

    private Set<AbstractTask> calculateLeafTasks() {
        return this.tasks.stream().filter(task -> task.getNextTasks().isEmpty()).collect(Collectors.toSet());
    }

}
