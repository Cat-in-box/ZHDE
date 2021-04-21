package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.NearStation;
import com.example.serverZHDE.repositories.NearStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NearStationService {

    private final NearStationRepository nearStationRepository;

    @Autowired
    public NearStationService(NearStationRepository nearStationRepository){
        this.nearStationRepository = nearStationRepository;
    }

    public void create(NearStation nearStation){
        nearStationRepository.save(nearStation);
    }

    public List<NearStation> findAll(){
        return nearStationRepository.findAll();
    }

    public Optional<NearStation> find(Long id){
        return nearStationRepository.findById(id);
    }

    public void update(Long id, NearStation nearStation){
        nearStationRepository.deleteById(id);
        nearStationRepository.save(nearStation);
    }

    public void delete(Long id){
        nearStationRepository.deleteById(id);
    }

}


