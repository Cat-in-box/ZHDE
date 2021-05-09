package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.Schedule;
import com.example.serverZHDE.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с рейсами в расписании
 */
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    /**
     * Instantiates a new Schedule service
     *
     * @param scheduleRepository the schedule repository
     */
    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

    /**
     * Создание нового рейса
     *
     * @param schedule - новая сущность Schedule
     */
    public void create(Schedule schedule){
        scheduleRepository.save(schedule);
    }

    /**
     * Получение списка всех рейсов из БД
     *
     * @return list
     */
    public List<Schedule> findAll(){
        return scheduleRepository.findAll();
    }

    /**
     * Получение рейса по уникальному идентификатору
     *
     * @param id - id рейса в БД
     * @return optional
     */
    public Optional<Schedule> find(Long id){
        return scheduleRepository.findById(id);
    }

    /**
     * Обновление данных существующего рейса
     *
     * @param id - id рейса в БД
     * @param schedule - обновленная сущность Schedule для внесения в БД
     */
    public void update(Long id, Schedule schedule){
        scheduleRepository.deleteById(id);
        scheduleRepository.save(schedule);
    }

    /**
     * Удаление существующего рейса из БД
     *
     * @param id - id рейса в БД
     */
    public void delete(Long id){
        scheduleRepository.deleteById(id);
    }

}