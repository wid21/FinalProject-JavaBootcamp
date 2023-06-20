package com.example.finalproject.Repository;

import com.example.finalproject.Model.Booking;
import com.example.finalproject.Model.Company;
import com.example.finalproject.Model.MyUser;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {

    Booking findBookingById(Integer id);

    List<Booking> findBookingByCustomer_Id(Integer id);

    List<Booking> findBookingsByStatus(String status);

    List<Booking> findAllByDetailsCompany(Company company);
}