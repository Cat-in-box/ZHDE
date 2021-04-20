package com.example.serverZHDE.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Near_Stations")
public class NearStation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="main_station_id", nullable = false)
    private Station mainStation;

    @ManyToOne
    @JoinColumn(name="near_station_id", nullable = false)
    private Station nearStation;

    @Column(name = "time_until")
    private Integer timeUntil;

}
