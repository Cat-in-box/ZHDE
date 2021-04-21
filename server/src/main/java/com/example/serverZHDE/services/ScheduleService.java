package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.Schedule;
import com.example.serverZHDE.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

    public void create(Schedule schedule){
        scheduleRepository.save(schedule);
    }

    public List<Schedule> findAll(){
        return scheduleRepository.findAll();
    }

    public Optional<Schedule> find(Long id){
        return scheduleRepository.findById(id);
    }

    public void update(Long id, Schedule schedule){
        scheduleRepository.deleteById(id);
        scheduleRepository.save(schedule);
    }

    public void delete(Long id){
        scheduleRepository.deleteById(id);
    }

}