package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.Ticket;
import com.example.serverZHDE.services.ClientService;
import com.example.serverZHDE.services.ScheduleService;
import com.example.serverZHDE.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService TicketService;
    private final ClientService ClientService;
    private final ScheduleService ScheduleService;

    @Autowired
    public TicketController(TicketService TicketService, ClientService ClientService, ScheduleService ScheduleService) {
        this.TicketService = TicketService;
        this.ClientService = ClientService;
        this.ScheduleService = ScheduleService;
    }

    @GetMapping()
    public ResponseEntity<List<Ticket>> findAll(){
        final List<Ticket> ticketList = TicketService.findAll();
        return ticketList != null && !ticketList.isEmpty()
                ? new ResponseEntity<>(ticketList, HttpStatus.OK)
                : new ResponseEntity<>(ticketList, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable(name = "id") Long id){
        final Optional<Ticket> ticket = TicketService.find(id);
        return ticket.isPresent()
                ? new ResponseEntity<>(ticket, HttpStatus.OK)
                : new ResponseEntity<>(ticket, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Ticket ticket) {
        if (TicketService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TicketService.update(id, ticket);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (TicketService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TicketService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/my/{id}")
    public ResponseEntity<ArrayList<List<String>>> myTicket(@PathVariable(name = "id") Long id){
        System.out.println("Запрос дошел");
        List<Ticket> ticketList = TicketService.findAll();
        ArrayList<List<String>> myTickets = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            if (ticket.getClient().getId() == id) {
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
        System.out.println(myTickets);
        System.out.println("Отправили ответ");
        return (ticketList != null && !ticketList.isEmpty() && myTickets.size() != 0)
                ? new ResponseEntity<>(myTickets, HttpStatus.OK)
                : new ResponseEntity<>(myTickets, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getoccupiedplaces/{scheduleId}")
    public ResponseEntity<?> getOccupiedPlacesList(@PathVariable(name = "scheduleId") Long scheduleId) {
        final List<Ticket> ticketList = TicketService.findAll();
        ArrayList<Integer> occupiedPlacesList = new ArrayList<>();

        for (Ticket ticket : ticketList) {
            if (ticket.getSchedule().getId() == scheduleId) {
                occupiedPlacesList.add(ticket.getPlace());
            }
        }

        System.out.println(occupiedPlacesList);

        return occupiedPlacesList.size() != 0
                ? new ResponseEntity<>(occupiedPlacesList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody HashMap<String, String> ticketInfo) {
        Ticket ticket = new Ticket();

        System.out.println(ticketInfo);
        try {
            Integer id = TicketService.findAll().size();
            ticket.setId(Long.parseLong(id.toString()));
            ticket.setClient(ClientService.find(Long.parseLong(ticketInfo.get("clientId"))).get());
            System.out.println("#1");
            ticket.setSchedule(ScheduleService.find(Long.parseLong(ticketInfo.get("scheduleId"))).get());
            System.out.println("#2");
            ticket.setRailwayCarriage(Integer.parseInt(ticketInfo.get("railwayCarriage")));
            System.out.println("#3");
            ticket.setPlace(Integer.parseInt(ticketInfo.get("place")));
            System.out.println("#4");
            ticket.setPrice(Integer.parseInt(ticketInfo.get("price")));
            System.out.println("#5");
            TicketService.create(ticket);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}