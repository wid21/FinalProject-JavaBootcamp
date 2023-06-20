package com.example.finalproject.Controller;

import com.example.finalproject.ApiResponse.ApiResponse;
import com.example.finalproject.Dto.DtoRate;
import com.example.finalproject.Model.Details;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Service.DetailsService;
import com.example.finalproject.Service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/rate")
@RequiredArgsConstructor
@RestController
public class RateController {
private final RateService rateService;
private final DetailsService detailsService;

    @PostMapping("/add-rate")
    public ResponseEntity add(@AuthenticationPrincipal MyUser customer, @RequestBody DtoRate rate ){
        rateService.add(customer.getId(),rate);
        return ResponseEntity.status(200).body(new ApiResponse("Thank you for your rating your points will be added soon!"));
    }

    @GetMapping("/get-Rate/{detailId}")
    public ResponseEntity getRate(@PathVariable Integer detailId) {
            double averageRating = rateService.getAverageRatingForDetail(detailId);

        return ResponseEntity.status(200).body("The Average Rate for this service is : " + averageRating);
        }
}
