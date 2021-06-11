package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.Train;
import com.example.serverZHDE.repositories.TrainRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrainServiceTest {

    @Autowired
    private TrainService service;

    @MockBean
    private TrainRepository repository;

    @Test
    public void create() {
        Train train = new Train();
        Train expected = new Train();
        Mockito.doReturn(expected).when(repository).save(train);
        System.out.println(expected);
        Assert.assertEquals(service.create(train), expected);
    }

    @Test
    public void findAll() {
        Train train = new Train();
        ArrayList<Train> expected = new ArrayList<>();
        expected.add(train);
        Mockito.when(service.findAll()).thenReturn(expected);
        Assert.assertEquals(service.findAll(), expected);
    }

    @Test
    public void find() {
        Optional<Train> expected = Optional.of(new Train());
        Mockito.doReturn(expected).when(repository).findById(1L);
        Assert.assertEquals(service.find(1L), expected);
    }

    @Test
    public void update() {
        Train train = new Train();
        Train expected = new Train();
        Mockito.doReturn(expected).when(repository).save(train);
        System.out.println(expected);
        Assert.assertEquals(service.create(train), expected);
    }

    @Test
    public void delete() {
        Mockito.doReturn(Optional.of(new Train())).when(repository).findById(1L);
        Assert.assertTrue(service.delete(1L));
    }

}