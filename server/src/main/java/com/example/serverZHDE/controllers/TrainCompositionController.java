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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody TrainComposition trainComposition) {
        TrainCompositionService.create(trainComposition);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<TrainComposition>> findAll(){
        final List<TrainComposition> trainCompositionList = TrainCompositionService.findAll();
        return trainCompositionList != null && !trainCompositionList.isEmpty()
                ? new ResponseEntity<>(trainCompositionList, HttpStatus.OK)
                : new ResponseEntity<>(trainCompositionList, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable(name = "id") Long id){
        final Optional<TrainComposition> trainComposition = TrainCompositionService.find(id);
        return trainComposition.isPresent()
                ? new ResponseEntity<>(trainComposition, HttpStatus.OK)
                : new ResponseEntity<>(trainComposition, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody TrainComposition trainComposition) {
        if (TrainCompositionService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TrainCompositionService.update(id, trainComposition);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (TrainCompositionService.find(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TrainCompositionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/carriagenumber/{scheduleId}")
    public ResponseEntity<Integer> getCarriageNumber(@PathVariable(name = "scheduleId") String scheduleId){
        if (scheduleId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println(scheduleId);
        final Long currentTrainId = ScheduleService.find(Long.parseLong(scheduleId)).get().getTrain().getId();
        final List<TrainComposition> trainCompositionList = TrainCompositionService.findAll();

        Integer carriageNumber = 0;

        for (TrainComposition trainComposition : trainCompositionList) {
            if (trainComposition.getTrain().getId() == currentTrainId) {
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
            if (trainComposition.getTrain().getId() == currentTrainId) {
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
                if (currentTrainComposition.getCarriageType().getId() == carriageTypeId) {
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
                        System.out.println("вагон " + carriageCounter + "начинается с " + startPlaceNumber);
                    }
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
