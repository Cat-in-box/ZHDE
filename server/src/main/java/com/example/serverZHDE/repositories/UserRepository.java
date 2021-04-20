package com.example.serverZHDE.repositories;

import com.example.serverZHDE.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
