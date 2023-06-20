package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@AllArgsConstructor
@Setter
@Getter
@RequiredArgsConstructor
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name shouldn't be empty")
    @Column(columnDefinition = "varchar(25) not null ")
    private String name;

    @NotEmpty(message = "Gender shouldn't be empty")
    @Column(columnDefinition = "varchar(20) not null check (gender='male' or gender='female') ")
    private String gender;

    @NotNull(message = "Age should not be empty")
    @Positive(message = "Age should be number ")
    @Min(value = 18,message = "Age should be above 18")
    @Column(columnDefinition = "decimal not null ")
    private int age;

    @NotEmpty(message = "Nationality shouldn't be empty")
    @Column(columnDefinition = "varchar(25) not null ")
    private String nationality;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "company_id",referencedColumnName = "my_user_id")
    private Company company;



}
