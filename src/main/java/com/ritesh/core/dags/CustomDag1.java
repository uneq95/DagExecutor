package com.ritesh.core.dags;

import com.ritesh.core.AbstractTask;
import com.ritesh.core.DagDefinition;
import com.ritesh.core.Task;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class CustomDag1 implements DagDefinition {

    private final AbstractTask task1 = new Task("task1", () -> {
        log.info("Running callable 1");
        return null;
    });

    private final AbstractTask task2 = new Task("task2", () -> {
        log.info("Running callable 2");
        return null;
    });

    private final AbstractTask task3 = new Task("task3", () -> {
        log.info("Running callable 3");
        return null;
    });

    private final Map<String, ?> conf;

    public CustomDag1() {
        this.conf = new HashMap<>();
        setup();
    }

    public CustomDag1(Map<String, ?> conf){
        this.conf = conf;
        setup();
    }

    @Override
    public void setup() {
        task2.dependsOn(task1);
        task3.dependsOn(task1);
    }

    @Override
    public Set<AbstractTask> getTasks() {
        return Set.of(task1, task2, task3);
    }
}
