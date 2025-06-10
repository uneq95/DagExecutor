package com.ritesh.dag.repository;

import com.ritesh.dag.model.TaskExecution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskExecutionRepository extends JpaRepository<TaskExecution, String> {
}