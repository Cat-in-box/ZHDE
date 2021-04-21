package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.CarriageType;
import com.example.serverZHDE.repositories.CarriageTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarriageTypeService {

    private final CarriageTypeRepository carriageTypeRepository;

    @Autowired
    public CarriageTypeService(CarriageTypeRepository carriageTypeRepository){
        this.carriageTypeRepository = carriageTypeRepository;
    }

    public void create(CarriageType carriageType){
        carriageTypeRepository.save(carriageType);
    }

    public List<CarriageType> findAll(){
        return carriageTypeRepository.findAll();
    }

    public Optional<CarriageType> find(Long id){
        return carriageTypeRepository.findById(id);
    }

    public void update(Long id, CarriageType carriageType){
        carriageTypeRepository.deleteById(id);
        carriageTypeRepository.save(carriageType);
    }

    public void delete(Long id){
        carriageTypeRepository.deleteById(id);
    }

}


