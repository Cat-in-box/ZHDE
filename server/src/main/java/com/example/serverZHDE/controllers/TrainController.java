package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.Train;
import com.example.serverZHDE.services.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TrainController {

    private final TrainService TrainService;

    @Autowired
    public TrainController(TrainService TrainService) {
        this.TrainService = TrainService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Train train) {
        TrainService.create(train);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Train>> findAll(){
        final List<Train> trainList = TrainService.findAll();
        return trainList != null && !trainList.isEmpty()
                ? new ResponseEntity<>(trainList, HttpStatus.OK)
                : new ResponseEntity<>(trainList, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable(name = "id") Long id){
        final Optional<Train> train = TrainService.find(id);
        return train.isPresent()
                ? new ResponseEntity<>(train, HttpStatus.OK)
                : new ResponseEntity<>(train, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Train train) {
        if (TrainService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TrainService.update(id, train);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (TrainService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TrainService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}