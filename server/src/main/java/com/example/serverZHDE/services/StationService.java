package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.Station;
import com.example.serverZHDE.repositories.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы со станциями
 */
@Service
public class StationService {

    private final StationRepository stationRepository;

    /**
     * Instantiates a new Station service
     *
     * @param stationRepository the station repository
     */
    @Autowired
    public StationService(StationRepository stationRepository){
        this.stationRepository = stationRepository;
    }

    /**
     * Создание новой станции
     *
     * @param station - новая сущность Station
     */
    public void create(Station station){
        stationRepository.save(station);
    }

    /**
     * Получение списка всех станций из БД
     *
     * @return list
     */
    public List<Station> findAll(){
        return stationRepository.findAll();
    }

    /**
     * Получение станции по уникальному идентификатору
     *
     * @param id - id станции в БД
     * @return optional
     */
    public Optional<Station> find(Long id){
        return stationRepository.findById(id);
    }

    /**
     * Обновление данных существующей станции
     *
     * @param id - id станции в БД
     * @param station - обновленная сущность Station для внесения в БД
     */
    public void update(Long id, Station station){
        stationRepository.deleteById(id);
        stationRepository.save(station);
    }

    /**
     * Удаление существующей станции из БД
     *
     * @param id - id станции в БД
     */
    public void delete(Long id){
        stationRepository.deleteById(id);
    }

}