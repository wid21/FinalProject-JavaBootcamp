package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class Company {

    @Id
    private Integer id;

    @NotEmpty(message = "name shouldn't be empty")
    @Column(columnDefinition = "varchar(25) not null")
    private String name;

    @NotEmpty(message = "phone number should not be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String phoneNumber;

    @NotEmpty(message = "Address should not be empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String address;

    @NotEmpty(message = "Licence Number should not be empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String licenceNum;

    @Column(columnDefinition = "varchar(25) not null check (status='pending' or status='approved' or status='rejected')")
    private String status;



    @OneToOne
    @MapsId
    @JsonIgnore
    private MyUser myUser;


    @OneToMany(mappedBy = "company",cascade = CascadeType.DETACH )
    @PrimaryKeyJoinColumn
    private Set<Staff> staff;

    @OneToMany(mappedBy = "company",cascade = CascadeType.DETACH)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private Set<Details> details;


}

