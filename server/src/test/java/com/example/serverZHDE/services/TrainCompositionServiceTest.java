package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.TrainComposition;
import com.example.serverZHDE.repositories.TrainCompositionRepository;
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
public class TrainCompositionServiceTest {

    @Autowired
    private TrainCompositionService service;

    @MockBean
    private TrainCompositionRepository repository;

    @Test
    public void create() {
        TrainComposition trainComposition = new TrainComposition();
        TrainComposition expected = new TrainComposition();
        Mockito.doReturn(expected).when(repository).save(trainComposition);
        System.out.println(expected);
        Assert.assertEquals(service.create(trainComposition), expected);
    }

    @Test
    public void findAll() {
        TrainComposition trainComposition = new TrainComposition();
        ArrayList<TrainComposition> expected = new ArrayList<>();
        expected.add(trainComposition);
        Mockito.when(service.findAll()).thenReturn(expected);
        Assert.assertEquals(service.findAll(), expected);
    }

    @Test
    public void find() {
        Optional<TrainComposition> expected = Optional.of(new TrainComposition());
        Mockito.doReturn(expected).when(repository).findById(1L);
        Assert.assertEquals(service.find(1L), expected);
    }

    @Test
    public void update() {
        TrainComposition trainComposition = new TrainComposition();
        TrainComposition expected = new TrainComposition();
        Mockito.doReturn(expected).when(repository).save(trainComposition);
        System.out.println(expected);
        Assert.assertEquals(service.create(trainComposition), expected);
    }

    @Test
    public void delete() {
        Mockito.doReturn(Optional.of(new TrainComposition())).when(repository).findById(1L);
        Assert.assertTrue(service.delete(1L));
    }

}