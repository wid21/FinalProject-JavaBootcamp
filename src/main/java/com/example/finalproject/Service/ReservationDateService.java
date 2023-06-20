package com.example.finalproject.Service;

import com.example.finalproject.Model.ReservationDate;
import com.example.finalproject.Repository.ReservationDateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationDateService {
    public final ReservationDateRepository reservationDateRepository;
    public List<ReservationDate> getAllReservation(){
       return reservationDateRepository.findAll();

    }
}
