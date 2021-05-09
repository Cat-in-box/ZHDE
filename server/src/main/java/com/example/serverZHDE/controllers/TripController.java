package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.Ticket;
import com.example.serverZHDE.services.TicketService;
import com.example.serverZHDE.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * The type Trip controller
 */
@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService TripService;
    private final TicketService TicketService;

    /**
     * Instantiates a new Trip controller
     *
     * @param TripService the trip service
     * @param TicketService the ticket service
     */
    @Autowired
    public TripController(TripService TripService, TicketService TicketService) {
        this.TripService = TripService;
        this.TicketService = TicketService;
    }

    /**
     * Получение топа 5 направлений по купленным билетам
     *
     * @return response entity
     */
    @GetMapping("/top5")
    public ResponseEntity<List<List<String>>> findTop(){
        Map<Long, List<Integer>> tripMap = new HashMap<>();

        // Получаем список всех купленных билетов
        List<Ticket> ticketList = TicketService.findAll();
        // Для каждого билета проверяем, внесено ли направление, на которое куплен билет, в итоговый список
        for (Ticket ticket : ticketList) {
            Long tripId = ticket.getSchedule().getTrip().getId();
            // Если направление уже есть в итоговом списке, увеличиваем число билетов на 1, и суммарную цену на цену билета
            if (tripMap.size()!=0 && tripMap.containsKey(tripId)) {
                Integer newCount = tripMap.get(tripId).get(0)+1;
                Integer newPrice = tripMap.get(tripId).get(1)+ticket.getPrice();
                tripMap.put(tripId, List.of(newCount, newPrice));
            }
            // Если направления еще нет в списке - добавляем с колличеством 1 и
            // суммарной стоимостью, равной стоимости текущего билета
            else {
                List list = List.of(1, ticket.getPrice());
                tripMap.put(tripId, list);
            }
        }

        // Выносим количество билетов в отдельный список для последующей сортировки
        // (берем только уникальные значения)
        ArrayList<Integer> tripTicketNumber = new ArrayList<>();
        for (List<Integer> tripMapValues : tripMap.values()){
            Integer smth = tripMapValues.get(0);
            if (!tripTicketNumber.contains(smth)) {
                tripTicketNumber.add(smth);
            }
        }

        // Сортируем полученный список
        Collections.sort(tripTicketNumber);
        Collections.reverse(tripTicketNumber);

        // По отсортированному списку находим рейс с таким количеством билетов и добавляем информацию о нем в итоговый список
        ArrayList<List<String>> topTripList = new ArrayList<>();
        for (Integer item : tripTicketNumber) {
            for (Long tripId : tripMap.keySet()) {
                if (tripMap.get(tripId).get(0).equals(item)) {
                    topTripList.add(List.of(TripService.find(tripId).get().getDepartureStation().getStationName(),
                            TripService.find(tripId).get().getDestinationStation().getStationName(),
                            // Считаем среднюю цену за билет
                            String.valueOf(tripMap.get(tripId).get(1)/tripMap.get(tripId).get(0))));
                }
            }
        }

        // Обрезаем список до топа 5 при возврате на клиентское приложение
        return topTripList.size() != 0
                ? new ResponseEntity<>(topTripList.subList(0,5), HttpStatus.OK)
                : new ResponseEntity<>(topTripList.subList(0,5), HttpStatus.NOT_FOUND);
    }
}