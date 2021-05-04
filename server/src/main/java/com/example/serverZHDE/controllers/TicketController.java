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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (TicketService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TicketService.delete(id);
        return new ResponseEntity<>(0, HttpStatus.OK);
    }

    @GetMapping("/my/{id}")
    public ResponseEntity<ArrayList<List<String>>> myTicket(@PathVariable(name = "id") Long id){
        List<Ticket> ticketList = TicketService.findAll();
        ArrayList<List<String>> myTickets = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            if (ticket.getClient().getId().equals(id)) {
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

    @GetMapping("/getoccupiedplaces/{scheduleId}")
    public ResponseEntity<?> getOccupiedPlacesList(@PathVariable(name = "scheduleId") Long scheduleId) {
        final List<Ticket> ticketList = TicketService.findAll();

        ArrayList<Integer> occupiedPlacesList = new ArrayList<>();

        for (Ticket ticket : ticketList) {
            if (ticket.getSchedule().getId().equals(scheduleId)) {
                occupiedPlacesList.add(ticket.getPlace());
            }
        }

        return !occupiedPlacesList.isEmpty()
                ? new ResponseEntity<>(occupiedPlacesList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody HashMap<String, String> ticketInfo) {
        Ticket ticket = new Ticket();

        for (Integer i = 1; i < TicketService.findAll().size()+1; i++) {
            if (TicketService.find(Long.parseLong(i.toString())).isEmpty()) {
                ticket.setId(Long.parseLong(i.toString()));
            }
        }
        if (ticket.getId() == null) {
            ticket.setId(Long.parseLong(Integer.toString(TicketService.findAll().size() + 1)));
        }

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