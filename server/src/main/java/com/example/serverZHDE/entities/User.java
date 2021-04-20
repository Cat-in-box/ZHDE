package com.example.serverZHDE.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String lastName;
    private String firstName;
    private String secondName;
    private LocalDate birthday;

    LocalDate createdDate;
    LocalDate changedDate;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;
}