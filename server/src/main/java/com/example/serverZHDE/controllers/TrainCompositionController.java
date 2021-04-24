package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.TrainComposition;
import com.example.serverZHDE.services.TrainCompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class TrainCompositionController {

    private final com.example.serverZHDE.services.TrainCompositionService TrainCompositionService;

    @Autowired
    public TrainCompositionController(TrainCompositionService TrainCompositionService) {
        this.TrainCompositionService = TrainCompositionService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody TrainComposition trainComposition) {
        TrainCompositionService.create(trainComposition);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<TrainComposition>> findAll(){
        final List<TrainComposition> trainCompositionList = TrainCompositionService.findAll();
        return trainCompositionList != null && !trainCompositionList.isEmpty()
                ? new ResponseEntity<>(trainCompositionList, HttpStatus.OK)
                : new ResponseEntity<>(trainCompositionList, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable(name = "id") Long id){
        final Optional<TrainComposition> trainComposition = TrainCompositionService.find(id);
        return trainComposition.isPresent()
                ? new ResponseEntity<>(trainComposition, HttpStatus.OK)
                : new ResponseEntity<>(trainComposition, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody TrainComposition trainComposition) {
        if (TrainCompositionService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TrainCompositionService.update(id, trainComposition);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (TrainCompositionService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TrainCompositionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
