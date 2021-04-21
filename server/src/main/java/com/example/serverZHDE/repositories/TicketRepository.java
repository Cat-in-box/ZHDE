package com.example.serverZHDE.repositories;

import com.example.serverZHDE.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}