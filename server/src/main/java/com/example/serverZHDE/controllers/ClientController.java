package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.Client;
import com.example.serverZHDE.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.*;


/**
 * The type Client controller
 */
@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService ClientService;

    /**
     * Instantiates a new Client controller.
     *
     * @param ClientService the client service
     */
    @Autowired
    public ClientController(ClientService ClientService) {
        this.ClientService = ClientService;
    }

    /**
     * Авторизация клиента
     *
     * @param login - логин клиента
     * @param password - пароль клиента
     * @return response entity
     */
    @GetMapping("/authorisation/{login}/{password}")
    public ResponseEntity<String> authorisation(@PathVariable(name = "login") String login, @PathVariable(name = "password") String password) {
        final List<Client> clientList = ClientService.findAll();
        // Ищем клиента по переданному логину
        for (Client client : clientList) {
            // Если нашли, проверяем переданный пароль
            if (client.getEmail().equals(login)) {
                // Пароль совпал - возвращаем id клиента для cookie
                if (client.getUserPassword().equals(password)) {
                    return new ResponseEntity<>(client.getId().toString(), HttpStatus.OK);
                // Клиент допустил ошибку в пароле
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }

        //Если не нашли такого клиента
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Возвращаем информацию о клиенте для личного кабинета
     *
     * @param id - id клиента в БД
     * @return response entity
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<Map<String, String>> getInfo(@PathVariable(name = "id") Long id) {
        // Проверка на существования клиента с переданным id
        Optional<Client> client = ClientService.find(id);
        if (client.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Client resultClient = client.get();
        // Собираем информацию о клиенте в HashMap
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

    /**
     * Обновление данных о клиенте
     *
     * @param id - id клиента в БД
     * @param clientInfo - словарь новых данных
     * @return response entity
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody HashMap<String, String> clientInfo) {
        // Проверка на существование клиента с переданным id
        if (ClientService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Проверка на уникальность переданного паспорта в БД
        for (Client existClient : ClientService.findAll()) {
            if (existClient.getPassport().toString().equals(clientInfo.get("passport"))) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }

        Client client = ClientService.find(id).get();
        // Если для данного параметра пришло обновленное значение, перезаписываем, если нет, оставляем старое
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

        // Обновляем запись в БД
        ClientService.update(id, client);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Создание нового клиента
     *
     * @param clientInfo - словарь информации о клиенте
     * @return response entity
     */
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody HashMap<String, String> clientInfo) {
        // Проверка на уникальность email'а и пасспорта в БД
        for (Client existClient : ClientService.findAll()) {
            if (existClient.getEmail().equals(clientInfo.get("email"))){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else if (existClient.getPassport().toString().equals(clientInfo.get("passport"))) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        Client client = new Client();
        // Генерация нового id
        for (Integer i = 1; i < ClientService.findAll().size()+1; i++) {
            if (ClientService.find(Long.parseLong(i.toString())).isEmpty()) {
                client.setId(Long.parseLong(i.toString()));
            }
        }
        if (client.getId() == null) {
            client.setId(Long.parseLong(Integer.toString(ClientService.findAll().size()+1)));
        }

        // Заносим новые значения в сущность клиента
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

        // Создаем клиента в БД
        ClientService.create(client);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}