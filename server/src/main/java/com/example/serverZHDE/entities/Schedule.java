package com.example.serverZHDE.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Schedules")
public class Schedule {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="trip_id", nullable = false)
    private Trip trip;

    @Column(name = "date_and_time")
    private Date dateAndTime;

    @ManyToOne
    @JoinColumn(name="train_id", nullable = false)
    private Train train;

    @Column(name = "platform")
    private Integer platform;

    @OneToMany(mappedBy = "schedule")
    private List<Ticket> ticketList;
}
