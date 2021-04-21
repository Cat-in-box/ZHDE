package com.example.serverZHDE.repositories;

import com.example.serverZHDE.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}