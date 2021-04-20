package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.Stations;
import com.example.serverZHDE.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public void create(Stations stations){
        categoryRepository.save(stations);
    }

    public List<Stations> findAll(){
        return categoryRepository.findAll();
    }

    public Optional<Stations> find(Long id){
        return categoryRepository.findById(id);
    }

    public void update(Long id, Stations stations){
        categoryRepository.deleteById(id);
        categoryRepository.save(stations);
    }

    public void delete(Long id){
        categoryRepository.deleteById(id);
    }

}


