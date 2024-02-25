package com.ritesh.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Slf4j
public class Task extends AbstractTask {

    public <T> Task(String name, Callable<T> taskLogic) {
        super(name, taskLogic);
    }
}
