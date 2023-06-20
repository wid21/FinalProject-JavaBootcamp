package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.Dto.DtoRate;
import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.BookingRepository;
import com.example.finalproject.Repository.CustomerRepository;
import com.example.finalproject.Repository.DetailsRepository;
import com.example.finalproject.Repository.RateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RateService {

    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    private final RateRepository rateRepository;
    private final DetailsRepository detailsRepository;

public void add(Integer customerId, DtoRate rate) {
    Customer customer = customerRepository.findCustomerById(customerId);
    if (customer == null) {
        throw new ApiException("Customer not found");
    }
    Booking booking = bookingRepository.findBookingById(rate.getBookingId());
    if (booking == null) {
        throw new ApiException("Booking not found");
    }
    if (!booking.getStatus().equals("complete")) {
        throw new ApiException("Booking is not complete");
    }
    if(booking.getRate()!=null){
        throw new ApiException("You already rate this booking ");
    }
    Rate rateEntity = new Rate();
    rateEntity.setRate(rate.getRate());
    rateEntity.setReview(rate.getReview());
    rateEntity.setBooking(booking); // Set the Booking property of the Rate entity
    rateRepository.save(rateEntity); // Save the Rate entity to the database
    booking.setRate(rateEntity); // Set the Rate property of the Booking entity
    bookingRepository.save(booking); // Save the Booking entity to the database
}


    public double getAverageRatingForDetail(Integer detailId) {
        List<Integer> ratings = new ArrayList<>();
        List<Booking> bookings = bookingRepository.findBookingsByStatus("complete");
        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            List<Details> details = detailsRepository.findDetailsByBookings(booking);
            for (int j = 0; j < details.size(); j++) {
                Details detail = details.get(j);
                if (detail.getId() == detailId && booking.getRate() != null) {
                    ratings.add(booking.getRate().getRate());
                }
            }
        }
        if (ratings.isEmpty()) {
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < ratings.size(); i++) {
            sum += ratings.get(i);
        }
        double averageRating = (double) sum / ratings.size();
        return averageRating;
    }
    }


