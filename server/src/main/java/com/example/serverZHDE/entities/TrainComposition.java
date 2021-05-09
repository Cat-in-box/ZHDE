package com.example.serverZHDE.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Сущность композиции поезда в MySQL БД
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "Train_Compositions")
public class TrainComposition {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="train_id", nullable = false)
    private Train train;

    @ManyToOne
    @JoinColumn(name="carriage_type_id", nullable = false)
    private CarriageType carriageType;

    @Column(name = "carriage_number")
    private Integer carriageNumber;
}
