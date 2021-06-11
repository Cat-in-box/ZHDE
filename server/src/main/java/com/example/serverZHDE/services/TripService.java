package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.Trip;
import com.example.serverZHDE.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Сервис для работы с направлениями
 */
@Service
public class TripService {

    private final TripRepository tripRepository;

    /**
     * Instantiates a new Trip service
     *
     * @param tripRepository the trip repository
     */
    @Autowired
    public TripService(TripRepository tripRepository){
        this.tripRepository = tripRepository;
    }

    /**
     * Создание нового направления
     *
     * @param trip - новая сущность Trip
     */
    public Trip create(Trip trip){
        return tripRepository.save(trip);
    }

    /**
     * Получение списка всех направлений из БД
     *
     * @return list
     */
    public List<Trip> findAll(){
        return tripRepository.findAll();
    }

    /**
     * Получение направления по уникальному идентификатору
     *
     * @param id - id направления в БД
     * @return optional
     */
    public Optional<Trip> find(Long id){
        return tripRepository.findById(id);
    }

    /**
     * Обновление данных существующего направления
     *
     * @param trip - обновленная сущность Trip для внесения в БД
     */
    public void update(Trip trip){
        tripRepository.save(trip);
    }

    /**
     * Удаление существующего направления из БД
     *
     * @param id - id направления в БД
     */
    public Boolean delete(Long id){
        try {
            tripRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}