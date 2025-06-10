package com.ritesh.dag.repository;

import com.ritesh.dag.model.TaskExecution;
import com.ritesh.dag.model.WorkflowExecution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowExecutionRepository extends JpaRepository<WorkflowExecution, String> {
}
