package com.example.serverZHDE.repositories;

import com.example.serverZHDE.entities.TrainComposition;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс JpaRepository для работы с коллекцией композиций поездов
 */
public interface TrainCompositionRepository extends JpaRepository<TrainComposition, Long> {
}

