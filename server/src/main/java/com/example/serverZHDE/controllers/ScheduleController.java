package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.CarriageType;
import com.example.serverZHDE.entities.Schedule;
import com.example.serverZHDE.entities.TrainComposition;
import com.example.serverZHDE.services.CarriageTypeService;
import com.example.serverZHDE.services.ScheduleService;
import com.example.serverZHDE.services.TrainCompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService ScheduleService;
    private final TrainCompositionService TrainCompositionService;
    private final CarriageTypeService CarriageTypeService;

    @Autowired
    public ScheduleController(ScheduleService ScheduleService, TrainCompositionService TrainCompositionService, CarriageTypeService CarriageTypeService) {
        this.ScheduleService = ScheduleService;
        this.TrainCompositionService = TrainCompositionService;
        this.CarriageTypeService = CarriageTypeService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Schedule schedule) {
        ScheduleService.create(schedule);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Schedule>> findAll(){
        final List<Schedule> scheduleList = ScheduleService.findAll();
        return scheduleList != null && !scheduleList.isEmpty()
                ? new ResponseEntity<>(scheduleList, HttpStatus.OK)
                : new ResponseEntity<>(scheduleList, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable(name = "id") Long id){
        final Optional<Schedule> schedule = ScheduleService.find(id);
        return schedule.isPresent()
                ? new ResponseEntity<>(schedule, HttpStatus.OK)
                : new ResponseEntity<>(schedule, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Schedule schedule) {
        if (ScheduleService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ScheduleService.update(id, schedule);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (ScheduleService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ScheduleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getAllDates")
    public ResponseEntity<?> getDates(){
        final List<Schedule> scheduleList = ScheduleService.findAll();
        Format pattern = new SimpleDateFormat("dd.MM.yyyy");
        ArrayList<String> dateList = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            dateList.add(pattern.format(schedule.getDateAndTime()));
        }

        return dateList.size() != 0
                ? new ResponseEntity<>(dateList, HttpStatus.OK)
                : new ResponseEntity<>(dateList, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getSchedule/{from}/{to}/{data}")
    public ResponseEntity<ArrayList<List<String>>> getDates(@PathVariable(name = "from") String from, @PathVariable(name = "to") String to, @PathVariable(name = "data") String data) {
        System.out.println(from + " " + to + " " + data);

        final List<Schedule> scheduleList = ScheduleService.findAll();
        ArrayList<Schedule> Buf1List = new ArrayList<>();
        if (!from.equals("Все")) {
            for (Schedule schedule : scheduleList) {
                System.out.println(schedule.getTrip().getDepartureStation().getStationName());
                if (schedule.getTrip().getDepartureStation().getStationName().equals(from)) {
                    System.out.println("Зашли в условие");
                    Buf1List.add(schedule);
                    System.out.println("Занесли в список");
                }
            }
        } else {
            Buf1List.addAll(scheduleList);
        }

        System.out.println("Отправление");

        ArrayList<Schedule> Buf2List = new ArrayList<>();
        if (!to.equals("Все")) {
            for (Schedule schedule : Buf1List) {
                System.out.println(schedule.getTrip().getDestinationStation().getStationName());
                if (schedule.getTrip().getDestinationStation().getStationName().equals(to)) {
                    System.out.println("Зашли в условие");
                    Buf2List.add(schedule);
                    System.out.println("Занесли в список");
                }
            }
        } else {
            System.out.println("Поняли, что отправление любое");
            Buf2List.addAll(Buf1List);
            System.out.println(Buf2List.size());
        }

        System.out.println("Прибытие");

        Buf1List.clear();
        Format pattern = new SimpleDateFormat("dd.MM.yyyy");
        if (!data.equals("Все")) {
            System.out.println("Дата не любая");
            for (Schedule schedule : Buf2List) {
                if (pattern.format(schedule.getDateAndTime()).equals(data)) {
                    Buf1List.add(schedule);
                }
            }
        } else {
            System.out.println("Поняли, что дата любая");
            Buf1List.addAll(Buf2List);
        }

        System.out.println("Дату посмотрели");
        System.out.println(Buf1List.size());

        ArrayList<List<String>> selectedList = new ArrayList<>();
        pattern = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        if (Buf1List.size()!=0) {
            for (Schedule schedule : Buf1List) {
                ArrayList<Long> carriageTypeList = new ArrayList<>();
                for (TrainComposition trainComposition : TrainCompositionService.findAll()) {
                    if (trainComposition.getTrain().getId().equals(schedule.getTrain().getId())) {
                        carriageTypeList.add(trainComposition.getCarriageType().getId());
                    }
                }

                Collections.sort(carriageTypeList);
                Collections.reverse(carriageTypeList);
                selectedList.add(List.of(schedule.getId().toString(),
                        schedule.getTrip().getDepartureStation().getStationName(),
                        schedule.getTrip().getDestinationStation().getStationName(),
                        pattern.format(schedule.getDateAndTime()),
                        "от " + CarriageTypeService.find(carriageTypeList.get(0)).get().getPrice().toString()));
            }
        }

        System.out.println(selectedList);

        return selectedList.size() != 0
                ? new ResponseEntity<>(selectedList, HttpStatus.OK)
                : new ResponseEntity<>(selectedList, HttpStatus.NOT_FOUND);
    }
}

