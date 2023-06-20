package com.example.finalproject.Repository;

import com.example.finalproject.Model.Booking;
import com.example.finalproject.Model.Company;
import com.example.finalproject.Model.Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface DetailsRepository extends JpaRepository<Details,Integer> {

  Details findDetailsById(Integer id);
  List<Details> findDetailsByCategory(String category);
  List<Details> findAllById(Integer id);
  @Query("select p from Details p where p.price < ?1")
  List<Details> findDetailsByPrice(double price);

  List<Details> findDetailsByBookings(Booking booking);

}
