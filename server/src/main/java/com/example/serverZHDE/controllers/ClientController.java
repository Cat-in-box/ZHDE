package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.Client;
import com.example.serverZHDE.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService ClientService;

    @Autowired
    public ClientController(ClientService ClientService) {
        this.ClientService = ClientService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Client client) {
        ClientService.create(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Client>> findAll(){
        final List<Client> clientList = ClientService.findAll();
        return clientList != null && !clientList.isEmpty()
                ? new ResponseEntity<>(clientList, HttpStatus.OK)
                : new ResponseEntity<>(clientList, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable(name = "id") Long id){
        final Optional<Client> client = ClientService.find(id);
        return client.isPresent()
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(client, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Client client) {
        if (ClientService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ClientService.update(id, client);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (ClientService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ClientService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/authorisation/{login}/{password}")
    public ResponseEntity<String> authorisation(@PathVariable(name = "login") String login, @PathVariable(name = "password") String password) {
        final List<Client> clientList = ClientService.findAll();
        for (Client client : clientList) {
            System.out.println("login " + client.getEmail());
            System.out.println(login);
            if (client.getEmail().equals(login)) {
                System.out.println("password " + client.getUserPassword());

                if (client.getUserPassword().equals(password)) {
                    return new ResponseEntity<>(client.getId().toString(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<Map<String, String>> getInfo(@PathVariable(name = "id") Long id) {
        Optional<Client> client = ClientService.find(id);
        if (!client.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Client resultClient = client.get();

        Map<String, String> info = new HashMap<>();

        info.put("email", resultClient.getEmail());
        info.put("userPassword", resultClient.getUserPassword());
        info.put("passport", resultClient.getPassport().toString());
        info.put("lastName", resultClient.getLastName());
        info.put("firstName", resultClient.getFirstName());
        info.put("patronymic", resultClient.getPatronymic());
        info.put("dateOfBirth", resultClient.getDateOfBirth().toString());
        info.put("phoneNumber", resultClient.getPhoneNumber().toString());

        return new ResponseEntity<>(info, HttpStatus.OK);
    }

}