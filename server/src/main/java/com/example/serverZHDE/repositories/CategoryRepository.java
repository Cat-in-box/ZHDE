package com.example.serverZHDE.repositories;


import com.example.serverZHDE.entities.Stations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Stations, Long> {
}