package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.Client;
import com.example.serverZHDE.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        if (client.isEmpty()) {
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

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody HashMap<String, String> clientInfo) {
        if (ClientService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Client client = ClientService.find(id).get();
        if (!clientInfo.get("lastName").equals("") && clientInfo.get("lastName") != null) {
            client.setLastName(clientInfo.get("lastName"));
        }
        if (!clientInfo.get("firstName").equals("") && clientInfo.get("firstName") != null) {
            client.setFirstName(clientInfo.get("firstName"));
        }
        if (!clientInfo.get("patronymic").equals("") && clientInfo.get("patronymic") != null) {
            client.setPatronymic(clientInfo.get("patronymic"));
        }
        if (!clientInfo.get("phoneNumber").equals("") && clientInfo.get("phoneNumber") != null) {
            if (clientInfo.get("phoneNumber").length() >= 11) {
                client.setPhoneNumber(Long.parseLong(clientInfo.get("phoneNumber")));
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        if (!clientInfo.get("passport").equals("") && clientInfo.get("passport") != null) {
            if (clientInfo.get("passport").length() >= 10) {
                client.setPassport(Long.parseLong(clientInfo.get("passport")));
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        if (!clientInfo.get("dateOfBirth").equals("") && clientInfo.get("dateOfBirth") != null) {
            try {
                client.setDateOfBirth(Date.valueOf(clientInfo.get("dateOfBirth")));
            } catch (Exception e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        System.out.println(clientInfo);

        ClientService.update(id, client);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody HashMap<String, String> clientInfo) {
        for (Client existClient : ClientService.findAll()) {
            if (existClient.getEmail().equals(clientInfo.get("email"))){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else if (existClient.getPassport().equals(clientInfo.get("passport"))) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        Client client = new Client();


        client.setId(Long.parseLong(Integer.toString(ClientService.findAll().size()+1)));

        if (clientInfo.get("email").contains("@")) {
            client.setEmail(clientInfo.get("email"));
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (clientInfo.get("password").length()>=8) {
            client.setUserPassword(clientInfo.get("password"));
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        client.setLastName(clientInfo.get("lastName"));
        client.setFirstName(clientInfo.get("firstName"));
        client.setPatronymic(clientInfo.get("patronymic"));
        if (clientInfo.get("phoneNumber").length() >= 11) {
            client.setPhoneNumber(Long.parseLong(clientInfo.get("phoneNumber")));
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (clientInfo.get("passport").length() >= 10) {
            client.setPassport(Long.parseLong(clientInfo.get("passport")));
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            client.setDateOfBirth(Date.valueOf(clientInfo.get("dateOfBirth")));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        System.out.println(clientInfo);
        System.out.println(client);

        ClientService.create(client);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}