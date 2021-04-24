package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.CarriageType;
import com.example.serverZHDE.services.CarriageTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carriagetypes")
public class CarriageTypeController {

    private final CarriageTypeService CarriageTypeService;

    @Autowired
    public CarriageTypeController(CarriageTypeService CarriageTypeService) {
        this.CarriageTypeService = CarriageTypeService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody CarriageType carriageType) {
        CarriageTypeService.create(carriageType);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<CarriageType>> findAll(){
        final List<CarriageType> carriageTypeList = CarriageTypeService.findAll();
        return carriageTypeList != null && !carriageTypeList.isEmpty()
                ? new ResponseEntity<>(carriageTypeList, HttpStatus.OK)
                : new ResponseEntity<>(carriageTypeList, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable(name = "id") Long id){
        final Optional<CarriageType> carriageType = CarriageTypeService.find(id);
        return carriageType.isPresent()
                ? new ResponseEntity<>(carriageType, HttpStatus.OK)
                : new ResponseEntity<>(carriageType, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody CarriageType carriageType) {
        if (CarriageTypeService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CarriageTypeService.update(id, carriageType);
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

