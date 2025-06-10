package com.ritesh.dag.model;

import lombok.Data;
import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import com.ritesh.dag.model.Status;

@Data
@Entity
public class WorkflowExecution {
    @Id
    private String id;
    
    private String name;
    private Status status;
    private Instant startTime;
    private Instant endTime;
    private String errorMessage;
    
    @OneToMany(mappedBy = "workflow", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskExecution> tasks = new ArrayList<>();
}
