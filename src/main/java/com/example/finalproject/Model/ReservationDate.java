package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Setter
@Getter
@RequiredArgsConstructor
public class ReservationDate {
    @Id
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @FutureOrPresent(message = "wrong date")
    private LocalDateTime reservationDate;


    @OneToOne
    @MapsId
    @JsonIgnore
    private Booking booking;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "details_id",referencedColumnName = "id")
    private Details details ;
}
