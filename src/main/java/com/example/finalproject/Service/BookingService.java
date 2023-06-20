package com.example.finalproject.Service;


import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.Dto.DtoBooking;
import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.*;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class BookingService {
    private final CustomerRepository customerRepository;
    private final BookingRepository bookRepository;
    private final CompanyRepository companyRepository;
    private final DetailsRepository detailsRepository;
    private final StaffRepository staffRepository;
    private final ReservationDateRepository reservationDateRepository;
    private final RateRepository rateRepository;



    public List<Booking> getCustomerOrders(Integer customerId) {
        List<Booking>orders=bookRepository.findBookingByCustomer_Id(customerId);
        if (orders ==null){
            throw new ApiException("You do not have any orders ");
        }
        return orders;
    }
    public List<Booking> getBookingByStatus(String status){
        List<Booking> booking=bookRepository.findBookingsByStatus(status);
        if(booking==null){
            throw new ApiException("not found");
        }
        return booking;
    }

    public List<Booking> getBookingByStatusCust(MyUser customer, String status){
       Customer customer1 = customerRepository.findCustomerById(customer.getId());
        List<Booking> booking=bookRepository.findBookingsByStatus(status);
        if(booking==null){
            throw new ApiException("not found");
        }
        if(customer1.getId()==null){
            throw new ApiException("customer not found");
        }
        return booking;
    }

    public List<Booking> getCompanyOrders(Integer companyId) {
        Company company = companyRepository.findCompanyById(companyId);
        List<Booking>orders=bookRepository.findAllByDetailsCompany(company);
        if (orders ==null){
            throw new ApiException("Company do not have any orders ");
        }
        return orders;
    }
    public List<Booking> getallbooking(){

        return bookRepository.findAll();
    }
    public double addOrder(MyUser customer1, DtoBooking dtoBooking) {
        Customer customer = customerRepository.findCustomerById(customer1.getId());
        if (customer == null) {
            throw new ApiException("Cannot add order, customer not found");
        }
        Company company = companyRepository.findCompanyById(dtoBooking.getCompany_Id());
        if (company == null) {
            throw new ApiException("Cannot add order, company not found");
        }
        if (company.getDetails() == null) {
            throw new ApiException("Cannot add order, company has no service details");
        }
        if (company.getStaff() == null) {
            throw new ApiException("Cannot add order, company has no staff assigned");
        }

        Details details = detailsRepository.findDetailsById(dtoBooking.getDetails_id());
        if (details == null) {
            throw new ApiException("Cannot add order, details not found");
        }

        if(!company.getStatus().equalsIgnoreCase("Approved")){
            throw new ApiException("Sorry! Can not order to this company");
        }

        List<ReservationDate> Dates = reservationDateRepository.findAllByDetails(details);
        for(int i = 0; i < Dates.size(); i++) {
            if (Dates.get(i).getReservationDate().equals(dtoBooking.getReservationDate())){
                throw new ApiException("Service is reserved already");}
        }
        LocalDateTime localDate = LocalDateTime.now();
        if (localDate.isAfter(dtoBooking.getReservationDate())){
            throw new ApiException("Cannot add order, reservation date has already passed");
        }
        ReservationDate reservationdate = new ReservationDate();
        reservationdate.setReservationDate(dtoBooking.getReservationDate());
        reservationdate.setDetails(details);

        double totalPrice = details.getPrice();
        int newpoint = 0;
        if (totalPrice > 500 && totalPrice < 1000) {
            newpoint = 250;
        } else if (totalPrice >= 1000) {
            newpoint = 500;
        } else {
            newpoint = 100;
        }
        int userpoints = customer.getPoints();
        double discountedPrice = calculateDiscount(userpoints, totalPrice);
        Booking newOrder = new Booking();

        newOrder.setTotalprice(discountedPrice);
        newOrder.setNewpoints(newpoint);
        newOrder.setStatus("new");
        newOrder.setDate(reservationdate);
        newOrder.setDetails(details);
        newOrder.setCustomer(customer);
        customer.setPoints(0);
        customerRepository.save(customer);
        reservationdate.setBooking(newOrder);

        reservationDateRepository.save(reservationdate);
        detailsRepository.save(details);
        bookRepository.save(newOrder);
        return discountedPrice;
    }

    public double calculateDiscount(int userpoints, double totalPrice) {
        if(userpoints ==0){
            return totalPrice;
        }
        int discount = userpoints / 100; // Calculate the number of discounts available
        double discountAmount = 5.0 * discount; // Calculate the total discount amount
        double discountedPrice = totalPrice - discountAmount;

        return discountedPrice;
    }

    public void cancelBooking (MyUser customer,Integer bookingId) {
        Customer customer1 = customerRepository.findCustomerById(customer.getId());
        if (customer1 == null) {
            throw new ApiException("Cannot add points, customer not found");
        }
        Booking booking = bookRepository.findBookingById(bookingId);

        if (booking == null) {
            throw new ApiException("Cannot update order, booking not found");
        }
        String status = booking.getStatus();
        if (status.equalsIgnoreCase("inProgress") || status.equalsIgnoreCase("complete")) {
            throw new ApiException("Sorry, you cannot cancel this booking.");
        }
        if(status.equalsIgnoreCase("Canceled")){
            throw new ApiException("Your Booking is already Canceled.");
        }
        booking.setNewpoints(0);
        booking.setStatus("Canceled");
        bookRepository.save(booking);

    }
    public double updateOrder(MyUser customer1, DtoBooking dtoBooking, Integer bookingId) {
        Customer customer = customerRepository.findCustomerById(customer1.getId());
        if (customer == null) {
            throw new ApiException("Cannot update order, customer not found");
        }
        Booking order = bookRepository.findBookingById(bookingId);
        if (order == null) {
            throw new ApiException("Cannot update order, order not found");
        }
        if (!order.getCustomer().equals(customer)) {
            throw new ApiException("Cannot update order, order not found or does not belong to customer");
        }

        if (!order.getStatus().equals("new")) {
            throw new ApiException("Cannot update order, order is " + order.getStatus());
        }
        Company company = companyRepository.findCompanyById(dtoBooking.getCompany_Id());
        if (company == null) {
            throw new ApiException("Cannot update order, company not found");
        }
        if (company.getDetails() == null) {
            throw new ApiException("Cannot update order, company has no service details");
        }
        if (company.getStaff() == null) {
            throw new ApiException("Cannot update order, company has no staff assigned");
        }
        Details details = detailsRepository.findDetailsById(dtoBooking.getDetails_id());
        if (details == null) {
            throw new ApiException("Cannot update order, details not found");
        }

        if(!company.getStatus().equalsIgnoreCase("Approved")){
            throw new ApiException("Sorry! Cannot update order to this company");
        }

        List<ReservationDate> Dates = reservationDateRepository.findAllByDetails(details);
        for(int i = 0; i < Dates.size(); i++) {
            if (Dates.get(i).getReservationDate().equals(dtoBooking.getReservationDate())){
                throw new ApiException("Service is already reserved");}
        }
        LocalDateTime localDate = LocalDateTime.now();
        if (localDate.isAfter(dtoBooking.getReservationDate())){
            throw new ApiException("Cannot update order, reservation date has already passed");
        }
        ReservationDate reservationdate = order.getDate();
        reservationdate.setReservationDate(dtoBooking.getReservationDate());
        reservationdate.setDetails(details);

        double totalPrice = details.getPrice();
        int newpoint ;
        if (totalPrice > 500 && totalPrice < 1000) {
            newpoint = 250;
        } else if (totalPrice >= 1000) {
            newpoint = 500;
        } else {
            newpoint = 100;
        }
        int userpoints = customer.getPoints();
        double discountedPrice = calculateDiscount(userpoints, totalPrice);

        order.setTotalprice(discountedPrice);
        order.setNewpoints(newpoint);
        order.setStatus("new");
        order.setDate(reservationdate);
        order.setDetails(details);
        order.setCustomer(customer);

        reservationdate.setBooking(order);

        reservationDateRepository.save(reservationdate);
        detailsRepository.save(details);
        bookRepository.save(order);
        return discountedPrice;
    }
    public int addPoints(MyUser admin, Integer customerId , Integer bookingId) {
        Customer customer1 = customerRepository.findCustomerById(customerId);
        if (customer1 == null) {
            throw new ApiException("Cannot add points, customer not found");
        }
        Booking booking = bookRepository.findBookingById(bookingId);
        if (booking == null) {
            throw new ApiException("Cannot add points, booking not found");
        }
        if (!customer1.equals(booking.getCustomer())) {
            throw new ApiException("Cannot add points, booking does not belong to this customer");
        }
        if (booking.getRate() == null) {
            throw new ApiException("Cannot add points, booking has not been rated yet");
        }
        int newPoints = booking.getNewpoints();
        if (newPoints == 0) {
            throw new ApiException("Cannot add points, booking already has 0 new points");
        }
        int points = customer1.getPoints() + newPoints;
        customer1.setPoints(points);
        booking.setNewpoints(0);
        customerRepository.save(customer1);
        bookRepository.save(booking);
        return points;
    }

    public void deleteBooking ( Integer bookingId){
        Booking orders = bookRepository.findBookingById(bookingId);

        if (orders == null) {
            throw new ApiException("Order not found");
        }


        if (orders.getStatus().equals("inProgress") || orders.getStatus().equals("completed")) {
            throw new ApiException("Cannot delete order, it has already been delivered");
        }

        orders.setCustomer(null);
        orders.setDetails(null);
        Rate rate = orders.getRate();
        ReservationDate date = orders.getDate();
        date.setDetails(null);
        orders.setRate(null);
        orders.setDate(null);

        rateRepository.delete(rate);
        reservationDateRepository.delete(date);
        bookRepository.delete(orders);
    }


    public void changeStatus(MyUser company, String status, Integer bookingId) {
        Company bookingCompany = companyRepository.findCompanyById(company.getId());
        if (bookingCompany == null) {
            throw new ApiException("Company not found");
        }
        List<Booking> bookings = bookRepository.findAllByDetailsCompany(bookingCompany);
        if (bookings == null || bookings.isEmpty()) {
            throw new ApiException("Company has no orders");
        }
        Booking booking = bookRepository.findBookingById(bookingId);
        if (booking == null) {
            throw new ApiException("Order not found");
        }
        if (!booking.getDetails().getCompany().getId().equals(company.getId())) {
            throw new ApiException("Unauthorized");
        }
        booking.setStatus(status);
        bookRepository.save(booking);
    }

//    public List<Booking> getBookingsByCompany(Company company,String status) {
//        if (company == null) {
//            throw new ApiException("Company not found");
//        }
//    List<Booking> bookings = bookRepository.findAllByDetailsCompany(bookingCompany);
//        if (bookings == null || bookings.isEmpty()) {
//        throw new ApiException("Company has no orders");
//    }
//    Booking booking = bookRepository.findBookingById;
//        if (booking == null) {
//        throw new ApiException("Order not found");
//    }
//        if (!booking.getDetails().getCompany().getId().equals(company.getId())) {
//        throw new ApiException("Unauthorized");
//    }
}

