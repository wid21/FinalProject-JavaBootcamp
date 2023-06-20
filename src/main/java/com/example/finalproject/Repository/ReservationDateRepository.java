package com.example.finalproject.Repository;

import com.example.finalproject.Model.Booking;
import com.example.finalproject.Model.Details;
import com.example.finalproject.Model.ReservationDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationDateRepository extends JpaRepository<ReservationDate,Integer> {
    List<ReservationDate> findAllByDetails(Details details );

}

