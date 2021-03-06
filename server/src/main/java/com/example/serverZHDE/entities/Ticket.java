package com.example.serverZHDE.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Сущность билета в MySQL БД
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "Tickets")
public class Ticket {
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name="schedule_id", nullable = false)
    private Schedule schedule;

    @Column(name = "railway_carriage")
    private Integer railwayCarriage;

    @Column(name = "place")
    private Integer place;

    @Column(name = "price")
    private Integer price;
}
