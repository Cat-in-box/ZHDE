package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.Station;
import com.example.serverZHDE.repositories.StationRepository;
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
public class StationServiceTest {

    @Autowired
    private StationService service;

    @MockBean
    private StationRepository repository;

    @Test
    public void create() {
        Station station = new Station();
        Station expected = new Station();
        Mockito.doReturn(expected).when(repository).save(station);
        System.out.println(expected);
        Assert.assertEquals(service.create(station), expected);
    }

    @Test
    public void findAll() {
        Station station = new Station();
        ArrayList<Station> expected = new ArrayList<>();
        expected.add(station);
        Mockito.when(service.findAll()).thenReturn(expected);
        Assert.assertEquals(service.findAll(), expected);
    }

    @Test
    public void find() {
        Optional<Station> expected = Optional.of(new Station());
        Mockito.doReturn(expected).when(repository).findById(1L);
        Assert.assertEquals(service.find(1L), expected);
    }

    @Test
    public void update() {
        Station station = new Station();
        Station expected = new Station();
        Mockito.doReturn(expected).when(repository).save(station);
        System.out.println(expected);
        Assert.assertEquals(service.create(station), expected);
    }

    @Test
    public void delete() {
        Mockito.doReturn(Optional.of(new Station())).when(repository).findById(1L);
        Assert.assertTrue(service.delete(1L));
    }

}