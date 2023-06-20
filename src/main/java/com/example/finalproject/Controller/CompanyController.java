package com.example.finalproject.Controller;

import com.example.finalproject.ApiResponse.ApiResponse;
import com.example.finalproject.Model.Booking;
import com.example.finalproject.Model.Company;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Staff;
import com.example.finalproject.Service.BookingService;
import com.example.finalproject.Service.CompanyService;
import com.example.finalproject.Service.MyUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
@RestController
public class CompanyController {

    private final MyUserService myUserService;
    private final CompanyService companyService;
    private final BookingService bookingService;

    @GetMapping("/get-allcompanies")
    public ResponseEntity getAllComanies() {
        List<Company> companies = companyService.getCompany();
        return ResponseEntity.status(200).body(companies);
    }
    @GetMapping("/get-id/{companyId}")
    public ResponseEntity getId(@PathVariable Integer companyId){
        Company company1= companyService.getCompanyById(companyId);
        return ResponseEntity.status(200).body(company1);
    }

    @GetMapping("/get-name/{companyName}")
    public ResponseEntity getName(@PathVariable String companyName){
        Company company1= companyService.getCompanyByName(companyName);
        return ResponseEntity.status(200).body(company1);
    }

    @GetMapping("/get-status/{status}")
    public ResponseEntity getStatus(@PathVariable String status){
        List<Company> company1= companyService.getCompanyByStatus(status);
        return ResponseEntity.status(200).body(company1);
    }


    @PutMapping("/change-status/{status}/{companyId}")
    public ResponseEntity changeStatus(@Valid @PathVariable String status, @PathVariable Integer  companyId) {
        companyService.changeStatus(status,companyId);
        return ResponseEntity.status(200).body(new ApiResponse("Status changed successfully "));
    }

    @PutMapping("/update/{companyId}")
    public ResponseEntity updateCompany(@AuthenticationPrincipal MyUser company1,@RequestBody Company company) {
        companyService.updateCompany(company1.getId(), company);
        return ResponseEntity.status(200).body(new ApiResponse("Company Updated"));
    }

    @DeleteMapping("/delete/{companyId}")
    public ResponseEntity deleteCompany(@AuthenticationPrincipal MyUser user,@PathVariable Integer companyId){
        companyService.deleteCompany(user,companyId);
        return ResponseEntity.status(200).body(new ApiResponse("Company Deleted"));
    }
}
