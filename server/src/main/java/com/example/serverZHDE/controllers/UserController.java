package com.example.serverZHDE.controllers;

import com.example.serverZHDE.services.TrainCompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final TrainCompositionService TrainCompositionService;

    @Autowired
    public UserController(TrainCompositionService TrainCompositionService) {
        this.TrainCompositionService = TrainCompositionService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody User user) {
        TrainCompositionService.create(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<User>> findAll(){
        final List<User> userList = TrainCompositionService.findAll();
        return userList != null && !userList.isEmpty()
                ? new ResponseEntity<>(userList, HttpStatus.OK)
                : new ResponseEntity<>(userList, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable(name = "id") Long id){
        final Optional<User> user = TrainCompositionService.find(id);
        return user.isPresent()
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody User user) {
        if (TrainCompositionService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TrainCompositionService.update(id, user);
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
