package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.Task;
import com.example.serverZHDE.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService TaskService;

    @Autowired
    public TaskController(TaskService TaskService) {
        this.TaskService = TaskService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Task task) {
        TaskService.create(task);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Task>> findAll(){
        final List<Task> taskList = TaskService.findAll();
        return taskList != null && !taskList.isEmpty()
                ? new ResponseEntity<>(taskList, HttpStatus.OK)
                : new ResponseEntity<>(taskList, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable(name = "id") Long id){
        final Optional<Task> task = TaskService.find(id);
        return task.isPresent()
                ? new ResponseEntity<>(task, HttpStatus.OK)
                : new ResponseEntity<>(task, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Task task) {
        if (TaskService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TaskService.update(id, task);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (TaskService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TaskService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}