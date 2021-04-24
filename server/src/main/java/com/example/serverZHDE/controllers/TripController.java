package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.Schedule;
import com.example.serverZHDE.entities.Station;
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

    @GetMapping("/top5")
    public ResponseEntity<List<List<String>>> findTop(){
        Map<Trip, List<Integer>> tripMap = new Map<Trip, List<Integer>>() {
            @Override
            public int size() { return 0; }
            @Override
            public boolean isEmpty() { return false; }
            @Override
            public boolean containsKey(Object key) { return false; }
            @Override
            public boolean containsValue(Object value) { return false; }
            @Override
            public List<Integer> get(Object key) { return null; }
            @Override
            public List<Integer> put(Trip key, List<Integer> value) { return null; }
            @Override
            public List<Integer> remove(Object key) { return null; }
            @Override
            public void putAll(Map<? extends Trip, ? extends List<Integer>> m) { }
            @Override
            public void clear() {}
            @Override
            public Set<Trip> keySet() { return null; }
            @Override
            public Collection<List<Integer>> values() { return null; }
            @Override
            public Set<Entry<Trip, List<Integer>>> entrySet() { return null; }};

        List<Trip> tripList = TripService.findAll();
        for (Trip trip : tripList) {
            tripMap.put(trip, List.of(0,0));
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
            tripMap.put(trip, List.of(listSize,listSum));
        }

        List<Integer> tripTicketNumber = List.of(0);
        for (List<Integer> tripMapValues : tripMap.values()){
            tripTicketNumber.add(tripMapValues.indexOf(0));
        }

        Collections.sort(tripTicketNumber);
        Collections.reverse(tripTicketNumber);

        List<List<String>> topTripList = List.of(List.of());
        for (Integer item : tripTicketNumber.subList(0, 5)) {
            for (Trip key : tripMap.keySet()) {
                if (tripMap.get(key).indexOf(0) == item) {
                    Station departureStation = key.getDepartureStation();
                    Station destinationStation = key.getDestinationStation();
                    topTripList.add(List.of(departureStation.getStationName(), destinationStation.getStationName(), String.valueOf(tripMap.get(key).indexOf(1)/tripMap.get(key).indexOf(0))));
                }
            }
        }

        return !topTripList.isEmpty()
                ? new ResponseEntity<>(topTripList, HttpStatus.OK)
                : new ResponseEntity<>(topTripList, HttpStatus.NOT_FOUND);
    }
}