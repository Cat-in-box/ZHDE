package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.Trip;
import com.example.serverZHDE.repositories.TripRepository;
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
public class TripServiceTest {

    @Autowired
    private TripService service;

    @MockBean
    private TripRepository repository;

    @Test
    public void create() {
        Trip trip = new Trip();
        Trip expected = new Trip();
        Mockito.doReturn(expected).when(repository).save(trip);
        System.out.println(expected);
        Assert.assertEquals(service.create(trip), expected);
    }

    @Test
    public void findAll() {
        Trip trip = new Trip();
        ArrayList<Trip> expected = new ArrayList<>();
        expected.add(trip);
        Mockito.when(service.findAll()).thenReturn(expected);
        Assert.assertEquals(service.findAll(), expected);
    }

    @Test
    public void find() {
        Optional<Trip> expected = Optional.of(new Trip());
        Mockito.doReturn(expected).when(repository).findById(1L);
        Assert.assertEquals(service.find(1L), expected);
    }

    @Test
    public void update() {
        Trip trip = new Trip();
        Trip expected = new Trip();
        Mockito.doReturn(expected).when(repository).save(trip);
        System.out.println(expected);
        Assert.assertEquals(service.create(trip), expected);
    }

    @Test
    public void delete() {
        Mockito.doReturn(Optional.of(new Trip())).when(repository).findById(1L);
        Assert.assertTrue(service.delete(1L));
    }

}