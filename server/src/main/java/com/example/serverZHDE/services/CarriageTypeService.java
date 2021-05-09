package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.CarriageType;
import com.example.serverZHDE.repositories.CarriageTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с типами вагонов
 */
@Service
public class CarriageTypeService {

    private final CarriageTypeRepository carriageTypeRepository;

    /**
     * Instantiates a new Carriage type service
     *
     * @param carriageTypeRepository the carriage type repository
     */
    @Autowired
    public CarriageTypeService(CarriageTypeRepository carriageTypeRepository){
        this.carriageTypeRepository = carriageTypeRepository;
    }

    /**
     * Создание нового типа вагона
     *
     * @param carriageType - новая сущность СarriageType
     */
    public void create(CarriageType carriageType){
        carriageTypeRepository.save(carriageType);
    }

    /**
     * Получение списка всех типов вагонов из БД
     *
     * @return list
     */
    public List<CarriageType> findAll(){
        return carriageTypeRepository.findAll();
    }

    /**
     * Получение типа вагона по уникальному идентификатору
     *
     * @param id - id типа вагона в БД
     * @return optional
     */
    public Optional<CarriageType> find(Long id){
        return carriageTypeRepository.findById(id);
    }

    /**
     * Обновление данных существующего типа вагона
     *
     * @param id - id типа вагона в БД
     * @param carriageType - обновленная сущность CarriageType для внесения в БД
     */
    public void update(Long id, CarriageType carriageType){
        carriageTypeRepository.deleteById(id);
        carriageTypeRepository.save(carriageType);
    }

    /**
     * Удаление существующего типа вагона из БД
     *
     * @param id - id типа вагона в БД
     */
    public void delete(Long id){
        carriageTypeRepository.deleteById(id);
    }

}


