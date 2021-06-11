package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.CarriageType;
import com.example.serverZHDE.repositories.CarriageTypeRepository;
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
public class CarriageTypeServiceTest {

    @Autowired
    private CarriageTypeService service;

    @MockBean
    private CarriageTypeRepository repository;

    @Test
    public void create() {
        CarriageType carriageType = new CarriageType();
        CarriageType expected = new CarriageType();
        Mockito.doReturn(expected).when(repository).save(carriageType);
        System.out.println(expected);
        Assert.assertEquals(service.create(carriageType), expected);
    }

    @Test
    public void findAll() {
        CarriageType carriageType = new CarriageType();
        ArrayList<CarriageType> expected = new ArrayList<>();
        expected.add(carriageType);
        Mockito.when(service.findAll()).thenReturn(expected);
        Assert.assertEquals(service.findAll(), expected);
    }

    @Test
    public void find() {
        Optional<CarriageType> expected = Optional.of(new CarriageType());
        Mockito.doReturn(expected).when(repository).findById(1L);
        Assert.assertEquals(service.find(1L), expected);
    }

    @Test
    public void update() {
        CarriageType carriageType = new CarriageType();
        CarriageType expected = new CarriageType();
        Mockito.doReturn(expected).when(repository).save(carriageType);
        System.out.println(expected);
        Assert.assertEquals(service.create(carriageType), expected);
    }

    @Test
    public void delete() {
        Mockito.doReturn(Optional.of(new CarriageType())).when(repository).findById(1L);
        Assert.assertTrue(service.delete(1L));
    }

}