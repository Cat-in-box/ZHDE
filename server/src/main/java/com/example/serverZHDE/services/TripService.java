package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.Trip;
import com.example.serverZHDE.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    private final TripRepository tripRepository;

    @Autowired
    public TripService(TripRepository tripRepository){
        this.tripRepository = tripRepository;
    }

    public void create(Trip trip){
        tripRepository.save(trip);
    }

    public List<Trip> findAll(){
        return tripRepository.findAll();
    }

    public Optional<Trip> find(Long id){
        return tripRepository.findById(id);
    }

    public void update(Long id, Trip trip){
        tripRepository.deleteById(id);
        tripRepository.save(trip);
    }

    public void delete(Long id){
        tripRepository.deleteById(id);
    }

}