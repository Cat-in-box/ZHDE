package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.Station;
import com.example.serverZHDE.repositories.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StationService {

    private final StationRepository stationRepository;

    @Autowired
    public StationService(StationRepository stationRepository){
        this.stationRepository = stationRepository;
    }

    public void create(Station station){
        stationRepository.save(station);
    }

    public List<Station> findAll(){
        return stationRepository.findAll();
    }

    public Optional<Station> find(Long id){
        return stationRepository.findById(id);
    }

    public void update(Long id, Station station){
        stationRepository.deleteById(id);
        stationRepository.save(station);
    }

    public void delete(Long id){
        stationRepository.deleteById(id);
    }

}