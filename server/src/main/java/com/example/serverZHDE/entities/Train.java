package com.example.serverZHDE.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Сущность поезда в MySQL БД
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "Trains")
public class Train {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "train_type")
    private String trainType;

    @Column(name = "issue_year")
    private Date issueYear;

    @OneToMany(mappedBy = "train")
    private List<TrainComposition> trainCompositionList;

    @OneToMany(mappedBy = "train")
    private List<Schedule> scheduleList;
}
