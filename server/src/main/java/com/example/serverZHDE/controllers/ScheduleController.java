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

/**
 * The type Schedule controller
 */
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService ScheduleService;
    private final TrainCompositionService TrainCompositionService;
    private final CarriageTypeService CarriageTypeService;

    /**
     * Instantiates a new Schedule controller
     *
     * @param ScheduleService the schedule service
     * @param TrainCompositionService the train composition service
     * @param CarriageTypeService the carriage type service
     */
    @Autowired
    public ScheduleController(ScheduleService ScheduleService, TrainCompositionService TrainCompositionService, CarriageTypeService CarriageTypeService) {
        this.ScheduleService = ScheduleService;
        this.TrainCompositionService = TrainCompositionService;
        this.CarriageTypeService = CarriageTypeService;
    }

    /**
     * Получение списка дат, на которые существуют рейсы в расписании
     *
     * @return response entity
     */
    @GetMapping("/getAllDates")
    public ResponseEntity<?> getDates(){
        // Получаем список всех рейсов
        final List<Schedule> scheduleList = ScheduleService.findAll();
        // Создаем паттерн для преобразования дат в строку
        Format pattern = new SimpleDateFormat("yyyy.MM.dd");
        ArrayList<String> dateList = new ArrayList<>();
        // Добавляем даты в список
        for (Schedule schedule : scheduleList) {
            dateList.add(pattern.format(schedule.getDateAndTime()));
        }

        // Убираем повторяющиеся значения и сортируем список
        Set<String> uniqDateSet = new TreeSet<>();
        uniqDateSet.addAll(dateList);

        return uniqDateSet.size() != 0
                ? new ResponseEntity<>(uniqDateSet, HttpStatus.OK)
                : new ResponseEntity<>(uniqDateSet, HttpStatus.NOT_FOUND);
    }

    /**
     * Получение расписания по заданному пользователем сложному критерию
     *
     * @param from - станция отправления
     * @param to - станция назначения
     * @param data - дата
     * @return response entity
     */
    @GetMapping("/getSchedule/{from}/{to}/{data}")
    public ResponseEntity<ArrayList<List<String>>> getDates(@PathVariable(name = "from") String from, @PathVariable(name = "to") String to, @PathVariable(name = "data") String data) {
        // Получаем все рейсы из БД
        final List<Schedule> scheduleList = ScheduleService.findAll();
        ArrayList<Schedule> Buf1List = new ArrayList<>();
        // Если станция отправления задана, то ищем соответствующие рейсы
        if (!from.equals("Все")) {
            for (Schedule schedule : scheduleList) {
                if (schedule.getTrip().getDepartureStation().getStationName().equals(from)) {
                    Buf1List.add(schedule);
                }
            }
        // Если станция отправления любая, то возвращаем все рейсы
        } else {
            Buf1List.addAll(scheduleList);
        }

        ArrayList<Schedule> Buf2List = new ArrayList<>();
        // Если станция назначения задана, то ищем соответствующие рейсы из полученного списка на предыдущем шаге
        if (!to.equals("Все")) {
            for (Schedule schedule : Buf1List) {
                if (schedule.getTrip().getDestinationStation().getStationName().equals(to)) {
                    Buf2List.add(schedule);
                }
            }
        // Если станция назначения любая, то возвращаем все рейсы из полученного списка на предыдущем шаге
        } else {
            Buf2List.addAll(Buf1List);
        }

        Buf1List.clear();
        // Задаем паттерн для конвертации дат в строки
        Format pattern = new SimpleDateFormat("yyyy.MM.dd");
        // Если дата задана, то ищем соответствующие рейсы из полученного списка на предыдущем шаге
        if (!data.equals("Все")) {
            for (Schedule schedule : Buf2List) {
                if (pattern.format(schedule.getDateAndTime()).equals(data)) {
                    Buf1List.add(schedule);
                }
            }
        // Если дата любая, то возвращаем все рейсы из полученного списка на предыдущем шаге
        } else {
            Buf1List.addAll(Buf2List);
        }

        ArrayList<List<String>> selectedList = new ArrayList<>();
        // Переопределяем паттерн для конвертации дат в строки
        pattern = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        // Если нашлись рейсы по заданным критериям
        if (Buf1List.size()!=0) {
            for (Schedule schedule : Buf1List) {
                ArrayList<Long> carriageTypeList = new ArrayList<>();
                // Получаем список цен на данный рейс
                for (TrainComposition trainComposition : TrainCompositionService.findAll()) {
                    if (trainComposition.getTrain().getId().equals(schedule.getTrain().getId())) {
                        carriageTypeList.add(trainComposition.getCarriageType().getId());
                    }
                }

                // Сортируем список цен, чтобы вернуть минимальную
                Collections.sort(carriageTypeList);
                Collections.reverse(carriageTypeList);
                // Добавляем нужную информацию о рейсе в итоговый лист
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

