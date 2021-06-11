package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.Ticket;
import com.example.serverZHDE.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с билетами
 */
@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    /**
     * Instantiates a new Ticket service
     *
     * @param ticketRepository the ticket repository
     */
    @Autowired
    public TicketService(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
    }

    /**
     * Создание нового билета
     *
     * @param ticket - новая сущность Ticket
     */
    public Ticket create(Ticket ticket){
        return ticketRepository.save(ticket);
    }

    /**
     * Получение списка всех билетов из БД
     *
     * @return list
     */
    public List<Ticket> findAll(){
        return ticketRepository.findAll();
    }

    /**
     * Получение билета по уникальному идентификатору
     *
     * @param id - id билета в БД
     * @return optional
     */
    public Optional<Ticket> find(Long id){
        return ticketRepository.findById(id);
    }

    /**
     * Обновление данных существующего билета
     *
     * @param ticket - обновленная сущность Ticket для внесения в БД
     */
    public void update(Ticket ticket){
        ticketRepository.save(ticket);
    }

    /**
     * Удаление существующего билета из БД
     *
     * @param id - id билета в БД
     */
    public Boolean delete(Long id){
        try {
            ticketRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}