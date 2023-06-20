package com.example.finalproject.Controller;

import com.example.finalproject.ApiResponse.ApiResponse;
import com.example.finalproject.Dto.DtoBooking;
import com.example.finalproject.Model.Booking;
import com.example.finalproject.Model.Customer;
import com.example.finalproject.Model.Details;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
@RestController
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/get-allbooking")
    public ResponseEntity getAllbooking() {
        List<Booking> bookings = bookingService.getallbooking();
        return ResponseEntity.status(200).body(bookings);
    }

    @PostMapping("/add-booking")
    public ResponseEntity addbooking (@AuthenticationPrincipal MyUser customer,@RequestBody DtoBooking dtoBooking){
        Double price= bookingService.addOrder(customer,dtoBooking);
        return ResponseEntity.status(200).body(new ApiResponse("Your booking was received and  your total price is : " + price));
    }
    @PutMapping  ("/add-points/{customerId}/{bookingId}")
    public ResponseEntity addPoints(@AuthenticationPrincipal MyUser admin,@PathVariable Integer customerId, @PathVariable Integer bookingId){
        int points = bookingService.addPoints(admin,customerId,bookingId);
        return ResponseEntity.status(200).body(new ApiResponse("Points added to customer account and it is : " + points));

    }
    @PutMapping  ("/cancel-booking/{bookingId}")
    public ResponseEntity cancelbooking(@AuthenticationPrincipal MyUser customer,@PathVariable Integer bookingId){
      bookingService.cancelBooking(customer,bookingId);
        return ResponseEntity.status(200).body(new ApiResponse("Your Booking was Canceled"));

    }

    @PutMapping("/update-booking/{bookingId}")
    public ResponseEntity updatebooking ( @AuthenticationPrincipal MyUser customer,@RequestBody DtoBooking booking,@PathVariable Integer bookingId){
     double price=bookingService.updateOrder(customer,booking,bookingId);
        return ResponseEntity.status(200).body(new ApiResponse("Your Booking Updated and yor total price is " + price) );
    }

    @DeleteMapping("/delete-booking/{bookingId}")
    public ResponseEntity deleteOrder(@AuthenticationPrincipal MyUser customer, @PathVariable Integer bookingId){
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.status(200).body(new ApiResponse("Booking deleted "));
    }


    @PutMapping("/change-status/{status}/{bookingId}")
    public ResponseEntity changeStatus(@AuthenticationPrincipal MyUser company,@Valid @PathVariable String status, @PathVariable Integer  bookingId) {
        bookingService.changeStatus(company,status,bookingId);
        return ResponseEntity.status(200).body(new ApiResponse("Status changed successfully "));
    }

    @GetMapping("/get-status/{status}")
    public ResponseEntity getStatus(@PathVariable String status){
        List<Booking> booking= bookingService.getBookingByStatus(status);
        return ResponseEntity.status(200).body(booking);
    }

    @GetMapping("/get-statuscus/{status}")
    public ResponseEntity getStatusCust(@AuthenticationPrincipal MyUser customer,@PathVariable String status){
        List<Booking> booking= bookingService.getBookingByStatusCust(customer,status);
        return ResponseEntity.status(200).body(booking);
    }
    @GetMapping("/get-customerOrders")
    public ResponseEntity getCustomerBookings(@AuthenticationPrincipal MyUser user) {
        List<Booking> orders = bookingService.getCustomerOrders(user.getId());
        return ResponseEntity.status(200).body(orders);
    }
    @GetMapping("/get-companyOrders")
    public ResponseEntity getCompanyBookings(@AuthenticationPrincipal MyUser user) {
        List<Booking> orders = bookingService.getCompanyOrders(user.getId());
        return ResponseEntity.status(200).body(orders);
    }

}
