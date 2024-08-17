package com.ritesh.core;

import java.util.Set;

public interface DagDefinition {

    default String getDagName() {
        return getClass().getSimpleName();
    }

    void setup();

    Set<AbstractTask> getTasks();

}

