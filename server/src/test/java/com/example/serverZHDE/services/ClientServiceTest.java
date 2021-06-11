package com.example.serverZHDE.services;

import com.example.serverZHDE.entities.Client;
import com.example.serverZHDE.repositories.ClientRepository;
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
public class ClientServiceTest {

    @Autowired
    private ClientService service;

    @MockBean
    private ClientRepository repository;

    @Test
    public void create() {
        /*
        client.setId(1L);
        client.setEmail("cat@mail.ru");
        client.setUserPassword("22222222");
        client.setPassport(Long.parseLong("1234123123"));
        client.setLastName("Пушистый");
        client.setFirstName("Кот");
        client.setDateOfBirth(Date.valueOf("2000-02-22"));
        client.setPhoneNumber(Long.parseLong("89992220022"));
        */

        Client client = new Client();
        Client expected = new Client();
        Mockito.doReturn(expected).when(repository).save(client);
        System.out.println(expected);
        Assert.assertEquals(service.create(client), expected);
    }

    @Test
    public void findAll() {
        Client client = new Client();
        ArrayList<Client> expected = new ArrayList<>();
        expected.add(client);
        Mockito.when(service.findAll()).thenReturn(expected);
        Assert.assertEquals(service.findAll(), expected);
    }

    @Test
    public void find() {
        Optional<Client> expected = Optional.of(new Client());
        Mockito.doReturn(expected).when(repository).findById(1L);
        Assert.assertEquals(service.find(1L), expected);
    }

    @Test
    public void update() {
        Client client = new Client();
        Client expected = new Client();
        Mockito.doReturn(expected).when(repository).save(client);
        System.out.println(expected);
        Assert.assertEquals(service.create(client), expected);
    }

    @Test
    public void delete() {
        Mockito.doReturn(Optional.of(new Client())).when(repository).findById(1L);
        Assert.assertTrue(service.delete(1L));
    }

}