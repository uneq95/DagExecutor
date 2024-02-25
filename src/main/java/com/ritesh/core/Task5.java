package com.ritesh.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class Task5 extends AbstractTask{


    @Override
    public void core() {
        log.info("Running task 5");
    }
}
