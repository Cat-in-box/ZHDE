package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.Client;
import com.example.serverZHDE.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с клиентами
 */
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    /**
     * Instantiates a new Client service
     *
     * @param clientRepository the client repository
     */
    @Autowired
    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    /**
     * Создание нового клиента
     *
     * @param client - новая сущность Client
     */
    public Client create(Client client){
        return clientRepository.save(client);
    }

    /**
     * Получение списка всех клиентов из БД
     *
     * @return list
     */
    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    /**
     * Получение клиента по уникальному идентификатору
     *
     * @param id - id клиента в БД
     * @return optional
     */
    public Optional<Client> find(Long id){
        return clientRepository.findById(id);
    }

    /**
     * Обновление данных существующего клиента
     *
     * @param client - обновленная сущность Client для внесения в БД
     */
    public Client update(Client client){
        return clientRepository.save(client);
    }

    /**
     * Удаление существующего клиента из БД
     *
     * @param id - id клиента в БД
     */
    public Boolean delete(Long id) {
        try {
            clientRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}


