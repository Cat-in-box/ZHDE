package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.Task;
import com.example.serverZHDE.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public void create(Task task){
        taskRepository.save(task);
    }

    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    public Optional<Task> find(Long id){
        return taskRepository.findById(id);
    }

    public void update(Long id, Task task){
        taskRepository.deleteById(id);
        taskRepository.save(task);
    }

    public void delete(Long id){
        taskRepository.deleteById(id);
    }

}