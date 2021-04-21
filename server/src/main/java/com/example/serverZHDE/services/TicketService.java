package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.Ticket;
import com.example.serverZHDE.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
    }

    public void create(Ticket ticket){
        ticketRepository.save(ticket);
    }

    public List<Ticket> findAll(){
        return ticketRepository.findAll();
    }

    public Optional<Ticket> find(Long id){
        return ticketRepository.findById(id);
    }

    public void update(Long id, Ticket ticket){
        ticketRepository.deleteById(id);
        ticketRepository.save(ticket);
    }

    public void delete(Long id){
        ticketRepository.deleteById(id);
    }

}