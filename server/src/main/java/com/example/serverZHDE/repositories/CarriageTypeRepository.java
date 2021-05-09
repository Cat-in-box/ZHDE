package com.example.serverZHDE.repositories;

import com.example.serverZHDE.entities.CarriageType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс JpaRepository для работы с коллекцией типов вагонов
 */
public interface CarriageTypeRepository extends JpaRepository<CarriageType, Long> {
}