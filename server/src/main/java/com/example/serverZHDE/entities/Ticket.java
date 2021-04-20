package com.example.serverZHDE.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Tickets")
public class Ticket {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name="schedule_id", nullable = false)
    private Schedule schedule;

    @OneToOne
    @JoinColumn(name="departure_station")
    private Station departureStation;

    @OneToOne
    @JoinColumn(name="destination_station")
    private Station destinationStation;

    @Column(name = "railway_carriage")
    private Integer railwayCarriage;

    @Column(name = "place")
    private Integer place;

    @Column(name = "price")
    private Integer price;
}
