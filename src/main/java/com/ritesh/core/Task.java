package com.ritesh.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Slf4j
public class Task extends AbstractTask {

    @Override
    public void core() {
        log.info("Performing core logic for task 0");
    }

    public Task(String name) {
        super(name);
    }


}
