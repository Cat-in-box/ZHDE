package com.example.serverZHDE.controllers;

import com.example.serverZHDE.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final ScheduleService ScheduleService;

    @Autowired
    public TaskController(ScheduleService ScheduleService) {
        this.ScheduleService = ScheduleService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Task task) {
        ScheduleService.create(task);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Task>> findAll(){
        final List<Task> taskList = ScheduleService.findAll();
        return taskList != null && !taskList.isEmpty()
                ? new ResponseEntity<>(taskList, HttpStatus.OK)
                : new ResponseEntity<>(taskList, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable(name = "id") Long id){
        final Optional<Task> task = ScheduleService.find(id);
        return task.isPresent()
                ? new ResponseEntity<>(task, HttpStatus.OK)
                : new ResponseEntity<>(task, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Task task) {
        if (ScheduleService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ScheduleService.update(id, task);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (ScheduleService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ScheduleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}