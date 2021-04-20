package com.example.serverZHDE.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Carriage_Types")
public class CarriageType {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "blocks_number")
    private Integer blocksNumber;

    @Column(name = "block_seats_number")
    private Integer blockSeatsNumber;

    @OneToMany(mappedBy = "carriageTypeId")
    private List<TrainComposition> trainCompositionList;
}
