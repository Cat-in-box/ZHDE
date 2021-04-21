package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.Train;
import com.example.serverZHDE.repositories.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainService {

    private final TrainRepository trainRepository;

    @Autowired
    public TrainService(TrainRepository trainRepository){
        this.trainRepository = trainRepository;
    }

    public void create(Train train){
        trainRepository.save(train);
    }

    public List<Train> findAll(){
        return trainRepository.findAll();
    }

    public Optional<Train> find(Long id){
        return trainRepository.findById(id);
    }

    public void update(Long id, Train train){
        trainRepository.deleteById(id);
        trainRepository.save(train);
    }

    public void delete(Long id){
        trainRepository.deleteById(id);
    }

}