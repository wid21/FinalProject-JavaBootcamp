package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@Setter
@Getter
@RequiredArgsConstructor

public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double totalprice;
    private int newpoints;

    @Column(columnDefinition = "varchar(25) not null check (status='new' or status='in progress' or status='complete' or status='canceled')")
    private String status;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "details_id",referencedColumnName = "id")
    private Details details;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customer_id",referencedColumnName = "my_user_id")
    private Customer customer;


    @OneToOne(mappedBy = "booking",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ReservationDate date;



    @OneToOne
    @PrimaryKeyJoinColumn
    private Rate rate ;

}
