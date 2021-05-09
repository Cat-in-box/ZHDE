package com.example.serverZHDE.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Сущность клиента (пользователя) в MySQL БД
 */
@Entity
@NoArgsConstructor
@Data
@Table(name = "Clients")
public class Client {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "passport")
    private Long passport;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @OneToMany(mappedBy = "client")
    private List<Ticket> ticketList;
}
