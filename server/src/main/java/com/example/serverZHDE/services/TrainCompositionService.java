package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.TrainComposition;
import com.example.serverZHDE.repositories.TrainCompositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с композициями поездов
 */
@Service
public class TrainCompositionService {

    private final TrainCompositionRepository trainCompositionRepository;

    /**
     * Instantiates a new Train Composition service
     *
     * @param trainCompositionRepository the train composition repository
     */
    @Autowired
    public TrainCompositionService(TrainCompositionRepository trainCompositionRepository){
        this.trainCompositionRepository = trainCompositionRepository;
    }

    /**
     * Создание новой композиции
     *
     * @param trainComposition - новая сущность TrainComposition
     */
    public void create(TrainComposition trainComposition){
        trainCompositionRepository.save(trainComposition);
    }

    /**
     * Получение списка всех композиций из БД
     *
     * @return list
     */
    public List<TrainComposition> findAll(){
        return trainCompositionRepository.findAll();
    }

    /**
     * Получение композиции по уникальному идентификатору
     *
     * @param id - id композиции в БД
     * @return optional
     */
    public Optional<TrainComposition> find(Long id){
        return trainCompositionRepository.findById(id);
    }

    /**
     * Обновление данных существующей композиции
     *
     * @param id - id композиции в БД
     * @param trainComposition - обновленная сущность TrainComposition для внесения в БД
     */
    public void update(Long id, TrainComposition trainComposition){
        trainCompositionRepository.deleteById(id);
        trainCompositionRepository.save(trainComposition);
    }

    /**
     * Удаление существующей композиции из БД
     *
     * @param id - id композиции в БД
     */
    public void delete(Long id){
        trainCompositionRepository.deleteById(id);
    }

}