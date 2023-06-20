package com.example.finalproject.Controller;

import com.example.finalproject.ApiResponse.ApiResponse;
import com.example.finalproject.Dto.DtoPoints;
import com.example.finalproject.Model.Company;
import com.example.finalproject.Model.Customer;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
@RestController
public class CustomerController {

private final CustomerService customerService;



    @GetMapping("/get-id/{customerId}")
    public ResponseEntity getId(@AuthenticationPrincipal MyUser customer,@PathVariable Integer customerId){
        Customer customer1=customerService.getCustomerById(customer.getId(),customerId);
        return ResponseEntity.status(200).body(customer1);
    }

    @GetMapping("/get-allcustomer")
    public ResponseEntity getcustomer() {
        List<Customer> customers = customerService.getCustomer();
        return ResponseEntity.status(200).body(customers);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@AuthenticationPrincipal MyUser user, @RequestBody Customer customer,@PathVariable Integer id) {
        customerService.updateCustomer(user.getId(), customer,id);
        return ResponseEntity.status(200).body(new ApiResponse("Customer information Updated"));
    }
    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity deleteCompany(@AuthenticationPrincipal MyUser user,@PathVariable Integer customerId){
        customerService.deleteCustomer(customerId);
        return ResponseEntity.status(200).body(new ApiResponse("Customer Deleted"));
    }



}
