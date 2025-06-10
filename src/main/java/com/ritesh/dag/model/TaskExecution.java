package com.ritesh.dag.model;

import lombok.Data;
import javax.persistence.*;
import java.time.Instant;
import com.ritesh.dag.model.Status;


@Data
@Entity
public class TaskExecution {
    @Id
    private String id;
    
    private String name;
    private Status status;
    private Instant startTime;
    private Instant endTime;
    private String errorMessage;
    private String stackTrace;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private WorkflowExecution workflow;
}

