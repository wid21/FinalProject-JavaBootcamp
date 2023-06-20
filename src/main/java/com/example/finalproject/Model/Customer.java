package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class Customer {


    @Id
    private Integer id;

    @NotEmpty(message = "phone number should not be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String phoneNumber;

    @NotEmpty(message = "Address should not be empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String address;

    private int points;




    @OneToOne
    @MapsId
    @JsonIgnore
    private MyUser myUser;


    @OneToMany(mappedBy = "customer",cascade = CascadeType.DETACH )
    @PrimaryKeyJoinColumn
    private Set<Booking> bookings;



}
