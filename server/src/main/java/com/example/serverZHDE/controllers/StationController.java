package com.example.serverZHDE.controllers;

import com.example.serverZHDE.entities.Station;
import com.example.serverZHDE.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * The type Station controller
 */
@RestController
@RequestMapping("/stations")
public class StationController {

    private final StationService StationService;

    /**
     * Instantiates a new Station controller
     *
     * @param StationService the station service
     */
    @Autowired
    public StationController(StationService StationService) {
        this.StationService = StationService;
    }

    /**
     * Получение списка всех станций
     *
     * @return response entity
     */
    @GetMapping("/getAllNames")
    public ResponseEntity<ArrayList<String>> getAllNames(){
        // Получаем все станции
        final List<Station> stationList = StationService.findAll();
        ArrayList<String> stationNameList = new ArrayList<>();
        // Создаем список из названий станций
        for (Station station : stationList) {
            stationNameList.add(station.getStationName());
        }

        // Сортируем список по алфавиту
        Collections.sort(stationNameList);

            return !stationNameList.isEmpty()
                    ? new ResponseEntity<>(stationNameList, HttpStatus.OK)
                    : new ResponseEntity<>(stationNameList, HttpStatus.NOT_FOUND);
    }

}

