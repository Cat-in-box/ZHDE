package com.example.serverZHDE.repositories;

import com.example.serverZHDE.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс JpaRepository для работы с коллекцией рейсов в расписании
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
