package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.Ticket;
import com.example.serverZHDE.repositories.TicketRepository;
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
public class TicketServiceTest {

    @Autowired
    private TicketService service;

    @MockBean
    private TicketRepository repository;

    @Test
    public void create() {
        Ticket ticket = new Ticket();
        Ticket expected = new Ticket();
        Mockito.doReturn(expected).when(repository).save(ticket);
        System.out.println(expected);
        Assert.assertEquals(service.create(ticket), expected);
    }

    @Test
    public void findAll() {
        Ticket ticket = new Ticket();
        ArrayList<Ticket> expected = new ArrayList<>();
        expected.add(ticket);
        Mockito.when(service.findAll()).thenReturn(expected);
        Assert.assertEquals(service.findAll(), expected);
    }

    @Test
    public void find() {
        Optional<Ticket> expected = Optional.of(new Ticket());
        Mockito.doReturn(expected).when(repository).findById(1L);
        Assert.assertEquals(service.find(1L), expected);
    }

    @Test
    public void update() {
        Ticket ticket = new Ticket();
        Ticket expected = new Ticket();
        Mockito.doReturn(expected).when(repository).save(ticket);
        System.out.println(expected);
        Assert.assertEquals(service.create(ticket), expected);
    }

    @Test
    public void delete() {
        Mockito.doReturn(Optional.of(new Ticket())).when(repository).findById(1L);
        Assert.assertTrue(service.delete(1L));
    }

}