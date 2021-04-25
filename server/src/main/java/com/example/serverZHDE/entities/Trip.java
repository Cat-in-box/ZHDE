package com.example.serverZHDE.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Trips")
public class Trip {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="departure_station_id", nullable = false)
    private Station departureStation;

    @ManyToOne
    @JoinColumn(name="destination_station_id", nullable = false)
    private Station destinationStation;

    @OneToMany(mappedBy = "trip")
    private List<Schedule> scheduleList;
}
