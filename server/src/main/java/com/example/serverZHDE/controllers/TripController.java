package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.Ticket;
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

    @GetMapping("/top5")
    public ResponseEntity<List<List<String>>> findTop(){
        Map<Long, List<Integer>> tripMap = new HashMap<>();

        List<Ticket> ticketList = TicketService.findAll();
        for (Ticket ticket : ticketList) {
            Long tripId = ticket.getSchedule().getTrip().getId();
            if (tripMap.size()!=0 && tripMap.containsKey(tripId)) {
                Integer newCount = tripMap.get(tripId).get(0)+1;
                Integer newPrice = tripMap.get(tripId).get(1)+ticket.getPrice();
                tripMap.put(tripId, List.of(newCount, newPrice));
            }
            else {
                List list = List.of(1, ticket.getPrice());
                tripMap.put(tripId, list);
            }
        }

        ArrayList<Integer> tripTicketNumber = new ArrayList<>();
        for (List<Integer> tripMapValues : tripMap.values()){
            Integer smth = tripMapValues.get(0);
            if (!tripTicketNumber.contains(smth)) {
                tripTicketNumber.add(smth);
            }
        }

        Collections.sort(tripTicketNumber);
        Collections.reverse(tripTicketNumber);

        ArrayList<List<String>> topTripList = new ArrayList<>();
        for (Integer item : tripTicketNumber) {
            for (Long tripId : tripMap.keySet()) {
                if (tripMap.get(tripId).get(0).equals(item)) {
                    topTripList.add(List.of(TripService.find(tripId).get().getDepartureStation().getStationName(),
                            TripService.find(tripId).get().getDestinationStation().getStationName(),
                            String.valueOf(tripMap.get(tripId).get(1)/tripMap.get(tripId).get(0))));
                }
            }
        }

        return topTripList.size() != 0
                ? new ResponseEntity<>(topTripList.subList(0,5), HttpStatus.OK)
                : new ResponseEntity<>(topTripList.subList(0,5), HttpStatus.NOT_FOUND);
    }
}