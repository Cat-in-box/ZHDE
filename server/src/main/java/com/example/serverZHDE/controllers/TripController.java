package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.Schedule;
import com.example.serverZHDE.entities.Ticket;
import com.example.serverZHDE.entities.Trip;
import com.example.serverZHDE.services.TicketService;
import com.example.serverZHDE.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService TripService;
    private final TicketService TicketService;

    @Autowired
    public TripController(TripService TripService, TicketService TicketService) {
        this.TripService = TripService;
        this.TicketService = TicketService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Trip trip) {
        TripService.create(trip);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Trip>> findAll(){
        final List<Trip> tripList = TripService.findAll();
        return tripList != null && !tripList.isEmpty()
                ? new ResponseEntity<>(tripList, HttpStatus.OK)
                : new ResponseEntity<>(tripList, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable(name = "id") Long id){
        final Optional<Trip> trip = TripService.find(id);
        return trip.isPresent()
                ? new ResponseEntity<>(trip, HttpStatus.OK)
                : new ResponseEntity<>(trip, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Trip trip) {
        if (TripService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TripService.update(id, trip);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (TripService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TripService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/top")
    public ResponseEntity<List<List<?>>> findTop(){
        Dictionary<Trip, List<Integer>> tripDict = new Dictionary<Trip, List<Integer>>() {
            @Override
            public int size() { return 0; }
            @Override
            public boolean isEmpty() { return false; }
            @Override
            public Enumeration<Trip> keys() { return null; }
            @Override
            public Enumeration<List<Integer>> elements() { return null; }
            @Override
            public List<Integer> get(Object key) { return null; }
            @Override
            public List<Integer> put(Trip key, List<Integer> value) { return null; }
            @Override
            public List<Integer> remove(Object key) { return null; }};

        List<Trip> tripList = TripService.findAll();
        for (Trip trip : tripList) {
            tripDict.put(trip, List.of(0,0));
            Integer listSize = 0;
            Integer listSum = 0;
            List<Schedule> scheduleList = trip.getScheduleList();
            for (Schedule schedule : scheduleList) {
                List<Ticket> ticketList = schedule.getTicketList();
                listSize = listSize + ticketList.size();
                for (Ticket ticket : ticketList) {
                    listSum = listSum + ticket.getPrice();
                }
            }
            tripDict.put(trip, List.of(listSize,listSum));
        }

        List<Integer> tripTicketNumber = List.of(0);
        Enumeration enu = tripDict.elements();
        while (enu.hasMoreElements()) {
            tripTicketNumber.add(List.of(enu.nextElement()).indexOf(0));
        }

        Collections.sort(tripTicketNumber);
        Collections.reverse(tripTicketNumber);

        List<List<String>> topTripList = List.of(List.of("0"));
        for (Integer item : tripTicketNumber) {
            Enumeration key = tripDict.keySet();
            while (key.hasMoreElements()) {
                if (tripDict.get(key.nextElement()).indexOf(0) == item){
                    topTripList.add(List.of(key.nextElement().get));
                }
            }
        }

        return topTripList != null && !topTripList.isEmpty()
                ? new ResponseEntity<>(topTripList, HttpStatus.OK)
                : new ResponseEntity<>(topTripList, HttpStatus.NOT_FOUND);
    }
}
