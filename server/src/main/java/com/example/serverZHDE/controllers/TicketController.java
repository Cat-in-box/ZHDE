package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.Ticket;
import com.example.serverZHDE.services.ClientService;
import com.example.serverZHDE.services.ScheduleService;
import com.example.serverZHDE.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * The type Ticket controller
 */
@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService TicketService;
    private final ClientService ClientService;
    private final ScheduleService ScheduleService;

    /**
     * Instantiates a new Ticket controller
     *
     * @param TicketService the ticket service
     * @param ClientService the client service
     * @param ScheduleService the schedule service
     */
    @Autowired
    public TicketController(TicketService TicketService, ClientService ClientService, ScheduleService ScheduleService) {
        this.TicketService = TicketService;
        this.ClientService = ClientService;
        this.ScheduleService = ScheduleService;
    }

    /**
     * Удаление билета
     *
     * @param id - id билета в БД
     * @return response entity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        // Проверка на существование билета
        if (TicketService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TicketService.delete(id);
        return new ResponseEntity<>(0, HttpStatus.OK);
    }

    /**
     * Получение списка билетов
     *
     * @param id - id клиента
     * @return response entity
     */
    @GetMapping("/my/{id}")
    public ResponseEntity<ArrayList<List<String>>> myTicket(@PathVariable(name = "id") Long id){
        // Получение всех билетов из БД
        List<Ticket> ticketList = TicketService.findAll();
        ArrayList<List<String>> myTickets = new ArrayList<>();
        // Поиск билетов в списке, у которых id клиента равен полученному параметру
        for (Ticket ticket : ticketList) {
            if (ticket.getClient().getId().equals(id)) {
                // Добавление в список нужной информации о билете
                myTickets.add(List.of(ticket.getId().toString(),
                        ticket.getSchedule().getTrip().getDepartureStation().getStationName(),
                        ticket.getSchedule().getTrip().getDestinationStation().getStationName(),
                        ticket.getSchedule().getDateAndTime().toString(),
                        ticket.getSchedule().getTrain().getId().toString(),
                        ticket.getRailwayCarriage().toString(),
                        ticket.getPlace().toString(),
                        ticket.getPrice().toString()));
            }
        }

        return (!ticketList.isEmpty() && myTickets.size() != 0)
                ? new ResponseEntity<>(myTickets, HttpStatus.OK)
                : new ResponseEntity<>(myTickets, HttpStatus.NOT_FOUND);
    }

    /**
     * Получение занятых мест на переданный рейс
     * (поиск среди купленных билетов на рейс)
     *
     * @param scheduleId - id рейса
     * @return response entity
     */
    @GetMapping("/getoccupiedplaces/{scheduleId}")
    public ResponseEntity<?> getOccupiedPlacesList(@PathVariable(name = "scheduleId") Long scheduleId) {
        // Получение всех билетов из БД
        final List<Ticket> ticketList = TicketService.findAll();

        ArrayList<Integer> occupiedPlacesList = new ArrayList<>();

        // Для каждого билета проверка на то, соответствует ли рейс в билете переданному параметру
        for (Ticket ticket : ticketList) {
            if (ticket.getSchedule().getId().equals(scheduleId)) {
                occupiedPlacesList.add(ticket.getPlace());
            }
        }

        return !occupiedPlacesList.isEmpty()
                ? new ResponseEntity<>(occupiedPlacesList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Создание нового билета
     *
     * @param ticketInfo - информация о билете
     * @return response entity
     */
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody HashMap<String, String> ticketInfo) {
        Ticket ticket = new Ticket();

        // Генерация нового id
        for (Integer i = 1; i < TicketService.findAll().size()+1; i++) {
            if (TicketService.find(Long.parseLong(i.toString())).isEmpty()) {
                ticket.setId(Long.parseLong(i.toString()));
            }
        }
        if (ticket.getId() == null) {
            ticket.setId(Long.parseLong(Integer.toString(TicketService.findAll().size() + 1)));
        }

        // Пытаемся задать атрибуты нового билета из переданных в словаре,
        // если ошибок не возникло - добавляем новый билет в БД
        try {
            ticket.setClient(ClientService.find(Long.parseLong(ticketInfo.get("clientId"))).get());
            ticket.setSchedule(ScheduleService.find(Long.parseLong(ticketInfo.get("scheduleId"))).get());
            ticket.setRailwayCarriage(Integer.parseInt(ticketInfo.get("railwayCarriage")));
            ticket.setPlace(Integer.parseInt(ticketInfo.get("place")));
            ticket.setPrice(Integer.parseInt(ticketInfo.get("price")));
            TicketService.create(ticket);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}