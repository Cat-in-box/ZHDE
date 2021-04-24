package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.NearStation;
import com.example.serverZHDE.services.NearStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class NearStationController {

    private final com.example.serverZHDE.services.NearStationService NearStationService;

    @Autowired
    public NearStationController(NearStationService NearStationService) {
        this.NearStationService = NearStationService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody NearStation nearStation) {
        NearStationService.create(nearStation);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<NearStation>> findAll(){
        final List<NearStation> nearStationList = NearStationService.findAll();
        return nearStationList != null && !nearStationList.isEmpty()
                ? new ResponseEntity<>(nearStationList, HttpStatus.OK)
                : new ResponseEntity<>(nearStationList, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable(name = "id") Long id){
        final Optional<NearStation> nearStation = NearStationService.find(id);
        return nearStation.isPresent()
                ? new ResponseEntity<>(nearStation, HttpStatus.OK)
                : new ResponseEntity<>(nearStation, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody NearStation nearStation) {
        if (NearStationService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        NearStationService.update(id, nearStation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (NearStationService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        NearStationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
