package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.Station;
import com.example.serverZHDE.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/stations")
public class StationController {

    private final StationService StationService;

    @Autowired
    public StationController(StationService StationService) {
        this.StationService = StationService;
    }

    @GetMapping("/getAllNames")
    public ResponseEntity<ArrayList<String>> getAllNames(){
        final List<Station> stationList = StationService.findAll();
        ArrayList<String> stationNameList = new ArrayList<>();
        for (Station station : stationList) {
            stationNameList.add(station.getStationName());
        }

        Collections.sort(stationNameList);

            return !stationNameList.isEmpty()
                    ? new ResponseEntity<>(stationNameList, HttpStatus.OK)
                    : new ResponseEntity<>(stationNameList, HttpStatus.NOT_FOUND);
    }

}

