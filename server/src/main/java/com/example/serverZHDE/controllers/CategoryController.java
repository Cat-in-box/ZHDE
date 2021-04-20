package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.Stations;
import com.example.serverZHDE.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService CategoryService;

    @Autowired
    public CategoryController(CategoryService CategoryService) {
        this.CategoryService = CategoryService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Stations stations) {
        CategoryService.create(stations);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Stations>> findAll(){
        final List<Stations> stationsList = CategoryService.findAll();
        return stationsList != null && !stationsList.isEmpty()
                ? new ResponseEntity<>(stationsList, HttpStatus.OK)
                : new ResponseEntity<>(stationsList, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable(name = "id") Long id){
        final Optional<Stations> category = CategoryService.find(id);
        return category.isPresent()
                ? new ResponseEntity<>(category, HttpStatus.OK)
                : new ResponseEntity<>(category, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Stations stations) {
        if (CategoryService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CategoryService.update(id, stations);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (CategoryService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CategoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

