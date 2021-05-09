package com.example.serverZHDE.repositories;

import com.example.serverZHDE.entities.Train;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс JpaRepository для работы с коллекцией поездов
 */
public interface TrainRepository extends JpaRepository<Train, Long> {
}