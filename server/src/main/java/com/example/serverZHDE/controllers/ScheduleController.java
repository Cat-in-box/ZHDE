package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.Schedule;
import com.example.serverZHDE.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService ScheduleService;

    @Autowired
    public ScheduleController(ScheduleService ScheduleService) {
        this.ScheduleService = ScheduleService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Schedule schedule) {
        ScheduleService.create(schedule);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Schedule>> findAll(){
        final List<Schedule> scheduleList = ScheduleService.findAll();
        return scheduleList != null && !scheduleList.isEmpty()
                ? new ResponseEntity<>(scheduleList, HttpStatus.OK)
                : new ResponseEntity<>(scheduleList, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable(name = "id") Long id){
        final Optional<Schedule> schedule = ScheduleService.find(id);
        return schedule.isPresent()
                ? new ResponseEntity<>(schedule, HttpStatus.OK)
                : new ResponseEntity<>(schedule, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Schedule schedule) {
        if (ScheduleService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ScheduleService.update(id, schedule);
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

