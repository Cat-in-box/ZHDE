package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.User;
import com.example.serverZHDE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void create(User user){
        userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> find(Long id){
        return userRepository.findById(id);
    }

    public void update(Long id, User user){
        userRepository.deleteById(id);
        userRepository.save(user);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

}