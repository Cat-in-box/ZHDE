package com.example.serverZHDE.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "mainStationId")
    private List<NearStation> nearStationToList;

    @OneToMany(mappedBy = "nearStationId")
    private List<NearStation> nearStationFromList;

    @OneToOne(mappedBy = "departureStation")
    private Trip tripDeparture;

    @OneToOne(mappedBy = "destinationStation")
    private Trip tripDestination;

    @OneToOne(mappedBy = "departureStation")
    private Ticket ticketDeparture;

    @OneToOne(mappedBy = "destinationStation")
    private Ticket ticketDestination;
}
