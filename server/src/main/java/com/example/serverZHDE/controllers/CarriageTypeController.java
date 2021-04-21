package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.Stations;
import com.example.serverZHDE.services.CarriageTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CarriageTypeController {

    private final CarriageTypeService CarriageTypeService;

    @Autowired
    public CategoryController(CarriageTypeService CarriageTypeService) {
        this.CarriageTypeService = CarriageTypeService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Stations stations) {
        CarriageTypeService.create(stations);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Stations>> findAll(){
        final List<Stations> stationsList = CarriageTypeService.findAll();
        return stationsList != null && !stationsList.isEmpty()
                ? new ResponseEntity<>(stationsList, HttpStatus.OK)
                : new ResponseEntity<>(stationsList, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable(name = "id") Long id){
        final Optional<Stations> category = CarriageTypeService.find(id);
        return category.isPresent()
                ? new ResponseEntity<>(category, HttpStatus.OK)
                : new ResponseEntity<>(category, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Stations stations) {
        if (CarriageTypeService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CarriageTypeService.update(id, stations);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (CarriageTypeService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CarriageTypeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

