package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@Setter
@Getter
@RequiredArgsConstructor
public class Details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Category shouldn't be empty")
    @Column(columnDefinition = "varchar(25) not null")
    private String category;

    @NotEmpty(message = "description shouldn't be empty")
    @Column(columnDefinition = "varchar(200) not null")
    private String description;

    @NotNull(message = "hours shouldn't be empty")
    @Positive(message = "hours should be postive")
    private int hours;

    @NotNull(message = "Price should not be empty")
    @Column(columnDefinition = "decimal not null")
    private double price;



    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "company_id",referencedColumnName = "my_user_id")
    private Company company;



    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "details")
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private Set<Booking> bookings;





    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "details")
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private Set<ReservationDate> reservationDates;











}
