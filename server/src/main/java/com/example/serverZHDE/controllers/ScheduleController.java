package com.example.serverZHDE.controllers;

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
import java.util.*;

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

    @GetMapping("/getAllDates")
    public ResponseEntity<?> getDates(){
        final List<Schedule> scheduleList = ScheduleService.findAll();
        Format pattern = new SimpleDateFormat("yyyy.MM.dd");
        ArrayList<String> dateList = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            dateList.add(pattern.format(schedule.getDateAndTime()));
        }

        Set<String> uniqDateSet = new TreeSet<>();
        uniqDateSet.addAll(dateList);

        return uniqDateSet.size() != 0
                ? new ResponseEntity<>(uniqDateSet, HttpStatus.OK)
                : new ResponseEntity<>(uniqDateSet, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getSchedule/{from}/{to}/{data}")
    public ResponseEntity<ArrayList<List<String>>> getDates(@PathVariable(name = "from") String from, @PathVariable(name = "to") String to, @PathVariable(name = "data") String data) {
        final List<Schedule> scheduleList = ScheduleService.findAll();
        ArrayList<Schedule> Buf1List = new ArrayList<>();
        if (!from.equals("Все")) {
            for (Schedule schedule : scheduleList) {
                if (schedule.getTrip().getDepartureStation().getStationName().equals(from)) {
                    Buf1List.add(schedule);
                }
            }
        } else {
            Buf1List.addAll(scheduleList);
        }

        ArrayList<Schedule> Buf2List = new ArrayList<>();
        if (!to.equals("Все")) {
            for (Schedule schedule : Buf1List) {
                if (schedule.getTrip().getDestinationStation().getStationName().equals(to)) {
                    Buf2List.add(schedule);
                }
            }
        } else {
            Buf2List.addAll(Buf1List);
        }

        Buf1List.clear();
        Format pattern = new SimpleDateFormat("yyyy.MM.dd");
        if (!data.equals("Все")) {
            for (Schedule schedule : Buf2List) {
                if (pattern.format(schedule.getDateAndTime()).equals(data)) {
                    Buf1List.add(schedule);
                }
            }
        } else {
            Buf1List.addAll(Buf2List);
        }

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

        return selectedList.size() != 0
                ? new ResponseEntity<>(selectedList, HttpStatus.OK)
                : new ResponseEntity<>(selectedList, HttpStatus.NOT_FOUND);
    }
}

