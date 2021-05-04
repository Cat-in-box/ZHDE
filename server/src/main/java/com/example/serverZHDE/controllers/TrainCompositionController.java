package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.CarriageType;
import com.example.serverZHDE.entities.TrainComposition;
import com.example.serverZHDE.services.CarriageTypeService;
import com.example.serverZHDE.services.ScheduleService;
import com.example.serverZHDE.services.TrainCompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/traincompositions")
public class TrainCompositionController {

    private final TrainCompositionService TrainCompositionService;
    private final ScheduleService ScheduleService;
    private  final CarriageTypeService CarriageTypeService;

    @Autowired
    public TrainCompositionController(TrainCompositionService TrainCompositionService, ScheduleService ScheduleService, CarriageTypeService CarriageTypeService) {
        this.TrainCompositionService = TrainCompositionService;
        this.ScheduleService = ScheduleService;
        this.CarriageTypeService = CarriageTypeService;
    }

    @GetMapping("/carriagenumber/{scheduleId}")
    public ResponseEntity<Integer> getCarriageNumber(@PathVariable(name = "scheduleId") String scheduleId){
        if (scheduleId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        final Long currentTrainId = ScheduleService.find(Long.parseLong(scheduleId)).get().getTrain().getId();
        final List<TrainComposition> trainCompositionList = TrainCompositionService.findAll();

        Integer carriageNumber = 0;

        for (TrainComposition trainComposition : trainCompositionList) {
            if (trainComposition.getTrain().getId().equals(currentTrainId)) {
                carriageNumber += trainComposition.getCarriageNumber();
            }
        }

        return carriageNumber != 0
                ? new ResponseEntity<>(carriageNumber, HttpStatus.OK)
                : new ResponseEntity<>(carriageNumber, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getcarriageinfo/{scheduleId}/{carriageNumber}")
    public ResponseEntity<List<Integer>> getPrice(@PathVariable(name = "scheduleId") String scheduleId, @PathVariable(name = "carriageNumber") String carriageNumber) {
        if (scheduleId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final Long currentTrainId = ScheduleService.find(Long.parseLong(scheduleId)).get().getTrain().getId();
        final List<CarriageType> carriageTypeList = CarriageTypeService.findAll();
        final List<TrainComposition> trainCompositionList = TrainCompositionService.findAll();

        ArrayList<TrainComposition> currentTrainCompositionList = new ArrayList<>();
        for (TrainComposition trainComposition : trainCompositionList) {
            if (trainComposition.getTrain().getId().equals(currentTrainId)) {
                currentTrainCompositionList.add(trainComposition);
            }
        }

        ArrayList<Long> carriageTypeIdList = new ArrayList<>();
        for (CarriageType carriageType : carriageTypeList) {
            carriageTypeIdList.add(carriageType.getId());
        }

        Collections.sort(carriageTypeIdList);
        Long carriageCounter = Long.parseLong("1");
        Integer toNextTypeCounter = 0;
        Integer startPlaceNumber = 1;

        for (Long carriageTypeId : carriageTypeIdList) {
            for (TrainComposition currentTrainComposition : currentTrainCompositionList) {
                if (currentTrainComposition.getCarriageType().getId().equals(carriageTypeId)) {
                    toNextTypeCounter += currentTrainComposition.getCarriageNumber();
                    while (carriageCounter <= toNextTypeCounter) {
                        if (carriageCounter == Long.parseLong(carriageNumber)) {

                            return new ResponseEntity<>(List.of(currentTrainComposition.getCarriageType().getPrice(),
                                    currentTrainComposition.getCarriageType().getBlocksNumber(),
                                    currentTrainComposition.getCarriageType().getBlockSeatsNumber(),
                                    startPlaceNumber), HttpStatus.OK);
                        }
                        carriageCounter += 1;
                        startPlaceNumber += currentTrainComposition.getCarriageType().getBlocksNumber() *
                                currentTrainComposition.getCarriageType().getBlockSeatsNumber();
                    }
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
