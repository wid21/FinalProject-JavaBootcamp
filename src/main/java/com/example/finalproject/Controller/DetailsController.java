package com.example.finalproject.Controller;

import com.example.finalproject.ApiResponse.ApiResponse;
import com.example.finalproject.Model.Details;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Staff;
import com.example.finalproject.Service.DetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/details")
@RequiredArgsConstructor
@RestController
public class DetailsController {

    private final DetailsService detailsService;

    @PostMapping("/add-details")
    public ResponseEntity add(@RequestBody @Valid Details details) {
        detailsService.add(details);
        return ResponseEntity.status(200).body(new ApiResponse("Service Added"));
    }

    @PutMapping("/update-details/{detailsId}")
    public ResponseEntity updateProduct(@AuthenticationPrincipal MyUser company, @Valid @RequestBody Details details  , @PathVariable Integer detailsId){
        detailsService.updateDetails(company,details, detailsId);
        return ResponseEntity.status(200).body(new ApiResponse("Service Updated"));
    }
    @GetMapping("/get-alldetails")
    public ResponseEntity getAllDetails() {
        List<Details> details = detailsService.getDetails();
        return ResponseEntity.status(200).body(details);
    }
    @DeleteMapping("/delete-details/{detailsId}")
    public ResponseEntity deleteDetails(@AuthenticationPrincipal MyUser company, @PathVariable Integer detailsId){
        detailsService.deleteDetails(company,detailsId);
        return ResponseEntity.status(200).body(new ApiResponse("Service Deleted "));
    }
//check auth
    @PostMapping("/add-details-toStaff/{details_id}/{staff_id}")
    public ResponseEntity addDetailsTostaff(@AuthenticationPrincipal MyUser company, @PathVariable Integer details_id,@PathVariable Integer staff_id){
        detailsService.addDetailsToStaffToCompany(company,details_id,staff_id);
        return ResponseEntity.status(200).body(new ApiResponse("Service and Staff are assigned to this company "));
    }
    @GetMapping("/get-id/{detailsId}")
    public ResponseEntity getDetailsById(@AuthenticationPrincipal MyUser company,@PathVariable Integer detailsId){
        Details details=detailsService.getDetailsById(company.getId(),detailsId);
        return ResponseEntity.status(200).body(details);
    }
    @GetMapping("/get-categorycust/{detailsCategory}")
    public ResponseEntity getDetailsByCategory(@AuthenticationPrincipal MyUser customer,@PathVariable String detailsCategory){
        List<Details> details=detailsService.getDetailsByCategory(customer,detailsCategory);
        return ResponseEntity.status(200).body(details);
    }

    @GetMapping("/get-price/{detailsPrice}")
    public ResponseEntity getDetailsByCategory(@PathVariable Integer detailsPrice){
        List<Details> details=detailsService.getDetailsByPrice(detailsPrice);
        return ResponseEntity.status(200).body(details);
    }




}
