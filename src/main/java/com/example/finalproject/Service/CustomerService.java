package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.Dto.DtoCompany;
import com.example.finalproject.Dto.DtoCustomer;
import com.example.finalproject.Dto.DtoPoints;
import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.BookingRepository;
import com.example.finalproject.Repository.CustomerRepository;
import com.example.finalproject.Repository.DetailsRepository;
import com.example.finalproject.Repository.MyUserRepository;
import jakarta.persistence.criteria.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class CustomerService {

    private final MyUserRepository myUserRepository;
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    private final DetailsRepository detailsRepository;

    public List<Customer> getCustomer() {

        return customerRepository.findAll();
    }
    public Customer getCustomerById(Integer customer, Integer customerId) {
        Customer customer1 = customerRepository.findCustomerById(customerId);
        if (customer1 == null) {
            throw new ApiException("Customer not found");
        }
        if (!customer.equals(customer1.getMyUser().getId())) {
            throw new ApiException("Customer not found");
        }
        return customer1;
    }

    public void updateCustomer(Integer userId,Customer customer,Integer customerId){
        Customer customer1 = customerRepository.findCustomerById(customerId);
        if(customer1==null){
            throw new ApiException("id not found");
        }
        if(userId!=customer1.getMyUser().getId()){
            throw new ApiException("unauthorized");
        }
        customer1.setAddress(customer.getAddress());
        customer1.setPhoneNumber(customer.getPhoneNumber());
        customerRepository.save(customer1);
    }

    public void deleteCustomer(Integer customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        Set<Booking> orders = customer.getBookings();

        MyUser user = customer.getMyUser();

        for (Booking booking : orders) {
            if (booking.getStatus().equalsIgnoreCase("new") || booking.getStatus().equalsIgnoreCase("in Progress")) {
                throw new ApiException("Can not delete customer there are booking is not completed");
            }

        }
        customerRepository.delete(customer);
        myUserRepository.delete(user);
    }

}