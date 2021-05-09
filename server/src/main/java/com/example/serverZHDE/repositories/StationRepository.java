package com.example.serverZHDE.repositories;

import com.example.serverZHDE.entities.Station;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс JpaRepository для работы с коллекцией станций
 */
public interface StationRepository extends JpaRepository<Station, Long> {
}