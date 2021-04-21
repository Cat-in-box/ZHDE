package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.TrainComposition;
import com.example.serverZHDE.repositories.TrainCompositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainCompositionService {

    private final TrainCompositionRepository trainCompositionRepository;

    @Autowired
    public TrainCompositionService(TrainCompositionRepository trainCompositionRepository){
        this.trainCompositionRepository = trainCompositionRepository;
    }

    public void create(TrainComposition trainComposition){
        trainCompositionRepository.save(trainComposition);
    }

    public List<TrainComposition> findAll(){
        return trainCompositionRepository.findAll();
    }

    public Optional<TrainComposition> find(Long id){
        return trainCompositionRepository.findById(id);
    }

    public void update(Long id, TrainComposition trainComposition){
        trainCompositionRepository.deleteById(id);
        trainCompositionRepository.save(trainComposition);
    }

    public void delete(Long id){
        trainCompositionRepository.deleteById(id);
    }

}