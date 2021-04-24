package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.Station;
import com.example.serverZHDE.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carriagetypes")
public class StationController {

    private final StationService StationService;

    @Autowired
    public StationController(StationService StationService) {
        this.StationService = StationService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Station station) {
        StationService.create(station);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Station>> findAll(){
        final List<Station> stationList = StationService.findAll();
        return stationList != null && !stationList.isEmpty()
                ? new ResponseEntity<>(stationList, HttpStatus.OK)
                : new ResponseEntity<>(stationList, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable(name = "id") Long id){
        final Optional<Station> station = StationService.find(id);
        return station.isPresent()
                ? new ResponseEntity<>(station, HttpStatus.OK)
                : new ResponseEntity<>(station, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Station station) {
        if (StationService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        StationService.update(id, station);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (StationService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        StationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

