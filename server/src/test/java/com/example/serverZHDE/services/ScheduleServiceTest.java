package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.Schedule;
import com.example.serverZHDE.repositories.ScheduleRepository;
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
public class ScheduleServiceTest {

    @Autowired
    private ScheduleService service;

    @MockBean
    private ScheduleRepository repository;

    @Test
    public void create() {
        Schedule schedule = new Schedule();
        Schedule expected = new Schedule();
        Mockito.doReturn(expected).when(repository).save(schedule);
        System.out.println(expected);
        Assert.assertEquals(service.create(schedule), expected);
    }

    @Test
    public void findAll() {
        Schedule schedule = new Schedule();
        ArrayList<Schedule> expected = new ArrayList<>();
        expected.add(schedule);
        Mockito.when(service.findAll()).thenReturn(expected);
        Assert.assertEquals(service.findAll(), expected);
    }

    @Test
    public void find() {
        Optional<Schedule> expected = Optional.of(new Schedule());
        Mockito.doReturn(expected).when(repository).findById(1L);
        Assert.assertEquals(service.find(1L), expected);
    }

    @Test
    public void update() {
        Schedule schedule = new Schedule();
        Schedule expected = new Schedule();
        Mockito.doReturn(expected).when(repository).save(schedule);
        System.out.println(expected);
        Assert.assertEquals(service.create(schedule), expected);
    }

    @Test
    public void delete() {
        Mockito.doReturn(Optional.of(new Schedule())).when(repository).findById(1L);
        Assert.assertTrue(service.delete(1L));
    }

}