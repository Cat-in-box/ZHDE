package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.Schedule;
import com.example.serverZHDE.entities.Station;
import com.example.serverZHDE.entities.Ticket;
import com.example.serverZHDE.entities.Trip;
import com.example.serverZHDE.services.ScheduleService;
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
    private final ScheduleService ScheduleService;
    private final TicketService TicketService;

    @Autowired
    public TripController(TripService TripService, ScheduleService ScheduleService, TicketService TicketService) {
        this.TripService = TripService;
        this.ScheduleService = ScheduleService;
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
        Map<Long, List<Integer>> tripMap = new HashMap<>();

        System.out.println("Дошли до поиска");
        List<Ticket> ticketList = TicketService.findAll();
        System.out.println("Лист билетов");
        for (Ticket ticket : ticketList) {
            System.out.println("Зашли в цикл");
            Long tripId = ticket.getSchedule().getTrip().getId();
            System.out.println("Дотянулись до трипа " + tripId);
            System.out.println("Ключи в словаре " + tripMap.keySet());
            if (tripMap.size()!=0 && tripMap.containsKey(tripId)) {
                System.out.println("Старый " + tripId + tripMap.get(tripId));
                Integer newCount = tripMap.get(tripId).get(0)+1;
                System.out.println(tripMap.get(tripId).get(0) + "+" + 1 + "->" + newCount);
                Integer newPrice = tripMap.get(tripId).get(1)+ticket.getPrice();
                System.out.println(tripMap.get(tripId).get(1) + "+" + ticket.getPrice() + "->" + newPrice);
                tripMap.put(tripId, List.of(newCount, newPrice));
                System.out.println("Обновили " + tripId + tripMap.get(tripId));
            }
            else {
                System.out.println("Новый");
                List list = List.of(1, ticket.getPrice());
                System.out.println("Засунули " + list);

                tripMap.put(tripId, list);
                System.out.println("Занесли " + tripId + tripMap.get(tripId));
            }
        }

        System.out.println("Словарь создан " + tripMap.size());
        System.out.println(tripMap);

        ArrayList<Integer> tripTicketNumber = new ArrayList<>();
        System.out.println("ТрипТикетНумбер");
        for (List<Integer> tripMapValues : tripMap.values()){
            System.out.println("Зашли в цикл");
            Integer smth = tripMapValues.get(0);
            System.out.println("Достали элемент " + smth);
            if (!tripTicketNumber.contains(smth)) {
                tripTicketNumber.add(smth);
                System.out.println("Добавили его");
            }
        }

        System.out.println("Засунули все в лист" + tripTicketNumber);
        Collections.sort(tripTicketNumber);
        Collections.reverse(tripTicketNumber);
        System.out.println("Отсортировали" + tripTicketNumber);

        ArrayList<List<String>> topTripList = new ArrayList<>();
        System.out.println(tripTicketNumber.size());
        for (Integer item : tripTicketNumber) {
            for (Long tripId : tripMap.keySet()) {
                if (tripMap.get(tripId).get(0) == item) {
                    topTripList.add(List.of(TripService.find(tripId).get().getDepartureStation().getStationName(),
                            TripService.find(tripId).get().getDestinationStation().getStationName(),
                            String.valueOf(tripMap.get(tripId).get(1)/tripMap.get(tripId).get(0))));
                }
            }
        }
        System.out.println("Сделали конечный лист" + topTripList);
        System.out.println("Обрезали" + topTripList.subList(0,5));

        return topTripList.size() != 0
                ? new ResponseEntity<>(topTripList.subList(0,5), HttpStatus.OK)
                : new ResponseEntity<>(topTripList.subList(0,5), HttpStatus.NOT_FOUND);
    }
}