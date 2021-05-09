package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.Train;
import com.example.serverZHDE.repositories.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с поездами
 */
@Service
public class TrainService {

    private final TrainRepository trainRepository;

    /**
     * Instantiates a new Train service
     *
     * @param trainRepository the train repository
     */
    @Autowired
    public TrainService(TrainRepository trainRepository){
        this.trainRepository = trainRepository;
    }

    /**
     * Создание нового поезда
     *
     * @param train - новая сущность Train
     */
    public void create(Train train){
        trainRepository.save(train);
    }

    /**
     * Получение списка всех поездов из БД
     *
     * @return list
     */
    public List<Train> findAll(){
        return trainRepository.findAll();
    }

    /**
     * Получение поезда по уникальному идентификатору
     *
     * @param id - id поезда в БД
     * @return optional
     */
    public Optional<Train> find(Long id){
        return trainRepository.findById(id);
    }

    /**
     * Обновление данных существующего поезда
     *
     * @param id - id поезда в БД
     * @param train - обновленная сущность Train для внесения в БД
     */
    public void update(Long id, Train train){
        trainRepository.deleteById(id);
        trainRepository.save(train);
    }

    /**
     * Удаление существующего поезда из БД
     *
     * @param id - id поезда в БД
     */
    public void delete(Long id){
        trainRepository.deleteById(id);
    }

}