package com.example.serverZHDE.repositories;

import com.example.serverZHDE.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс JpaRepository для работы с коллекцией направлений
 */
public interface TripRepository extends JpaRepository<Trip, Long> {
}
