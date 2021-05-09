package com.example.serverZHDE.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Сущность станции в MySQL БД
 */
@Entity
@NoArgsConstructor
@Table(name = "Stations")
public class Station {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "station_name")
    private String stationName;

    public String getStationName() {
        return this.stationName;
    }

    @OneToMany(mappedBy = "departureStation")
    private List<Trip> tripDepartureList;

    @OneToMany(mappedBy = "destinationStation")
    private List<Trip> tripDestinationList;
}
