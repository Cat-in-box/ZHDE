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

/**
 * The type Train Composition controller
 */
@RestController
@RequestMapping("/traincompositions")
public class TrainCompositionController {

    private final TrainCompositionService TrainCompositionService;
    private final ScheduleService ScheduleService;
    private  final CarriageTypeService CarriageTypeService;

    /**
     * Instantiates a new Train Composition controller
     *
     * @param TrainCompositionService the train composition service
     * @param ScheduleService the schedule service
     * @param CarriageTypeService the carriage type service
     */
    @Autowired
    public TrainCompositionController(TrainCompositionService TrainCompositionService, ScheduleService ScheduleService, CarriageTypeService CarriageTypeService) {
        this.TrainCompositionService = TrainCompositionService;
        this.ScheduleService = ScheduleService;
        this.CarriageTypeService = CarriageTypeService;
    }

    /**
     * Получение количества вагонов в поезде, идущем по рейсу
     *
     * @param scheduleId - id рейса
     * @return response entity
     */
    @GetMapping("/carriagenumber/{scheduleId}")
    public ResponseEntity<Integer> getCarriageNumber(@PathVariable(name = "scheduleId") String scheduleId){
        // Проверка переданного значения
        if (scheduleId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Находим id поезда, идущего по заданному рейсу
        final Long currentTrainId = ScheduleService.find(Long.parseLong(scheduleId)).get().getTrain().getId();
        // Получаем все композиции поездов (список соответствий вагонов каждому поезду)
        final List<TrainComposition> trainCompositionList = TrainCompositionService.findAll();

        // Подсчитываем общее количество вагонов в поезде
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

    /**
     * Получение расположения мест в выбранном вагоне
     *
     * @param scheduleId - id рейса
     * @param carriageNumber - номер вагона
     * @return response entity
     */
    @GetMapping("/getcarriageinfo/{scheduleId}/{carriageNumber}")
    public ResponseEntity<List<Integer>> getPrice(@PathVariable(name = "scheduleId") String scheduleId, @PathVariable(name = "carriageNumber") String carriageNumber) {
        // Проверка переданного значения
        if (scheduleId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Находим id поезда, идущего по заданному рейсу
        final Long currentTrainId = ScheduleService.find(Long.parseLong(scheduleId)).get().getTrain().getId();
        // Получаем список всех типов вагонов
        final List<CarriageType> carriageTypeList = CarriageTypeService.findAll();
        // Получаем все композиции поездов (список соответствий вагонов каждому поезду)
        final List<TrainComposition> trainCompositionList = TrainCompositionService.findAll();

        // Находим композицию для данного поезда
        ArrayList<TrainComposition> currentTrainCompositionList = new ArrayList<>();
        for (TrainComposition trainComposition : trainCompositionList) {
            if (trainComposition.getTrain().getId().equals(currentTrainId)) {
                currentTrainCompositionList.add(trainComposition);
            }
        }

        // Получаем список типов вагонов в данном поезде из композиции
        ArrayList<Long> carriageTypeIdList = new ArrayList<>();
        for (CarriageType carriageType : carriageTypeList) {
            carriageTypeIdList.add(carriageType.getId());
        }

        // Сортируем полученный список по возрастанию (убыванию привилегированности)
        Collections.sort(carriageTypeIdList);
        Long carriageCounter = Long.parseLong("1");

        Integer toNextTypeCounter = 0;
        Integer startPlaceNumber = 1;

        // Проходимся по отсортированному списку типов вагонов ДАННОГО ПОЕЗДА
        for (Long carriageTypeId : carriageTypeIdList) {
            // Для ищем соответствующую этому типу композицию в списке композиций для ДАННОГО ПОЕЗДА
            for (TrainComposition currentTrainComposition : currentTrainCompositionList) {
                if (currentTrainComposition.getCarriageType().getId().equals(carriageTypeId)) {
                    // Суммируем общее число вагонов данного типа.
                    // После того, как номер текущего вагона превысит это сумму, переходим к следующему типу
                    toNextTypeCounter += currentTrainComposition.getCarriageNumber();
                    while (carriageCounter <= toNextTypeCounter) {
                        // Если счетчик текущего вагона равен переданному в параметре номеру вагона
                        if (carriageCounter == Long.parseLong(carriageNumber)) {
                            // Формируем список расположения мест в вагоне и номер первого места
                            return new ResponseEntity<>(List.of(currentTrainComposition.getCarriageType().getPrice(),
                                    currentTrainComposition.getCarriageType().getBlocksNumber(),
                                    currentTrainComposition.getCarriageType().getBlockSeatsNumber(),
                                    startPlaceNumber), HttpStatus.OK);
                        }
                        // Переходим к следующему вагону, номер первого места увеличиваем на количество мест в данном вагоне
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
