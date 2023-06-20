package com.example.finalproject.Controller;

import com.example.finalproject.Model.Booking;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.ReservationDate;
import com.example.finalproject.Service.ReservationDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/date")
@RequiredArgsConstructor
@RestController
public class ReservationDateController {
    private final ReservationDateService reservationDateService;

    @GetMapping("/get-reservedates")
    public ResponseEntity getReservationDates() {
        List<ReservationDate> dates = reservationDateService.getAllReservation();
        return ResponseEntity.status(200).body(dates);
    }
}
