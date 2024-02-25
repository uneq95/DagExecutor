package com.ritesh.core;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Task2 extends AbstractTask{
    @Override
    public void core() {
        log.info("Running task 2");
    }
}
