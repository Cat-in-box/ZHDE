package com.example.serverZHDE.repositories;

import com.example.serverZHDE.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс JpaRepository для работы с коллекцией клиентов
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
}