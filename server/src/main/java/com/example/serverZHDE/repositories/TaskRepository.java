package com.example.serverZHDE.repositories;

import com.example.serverZHDE.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}