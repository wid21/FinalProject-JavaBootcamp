package com.example.finalproject.Controller;

import com.example.finalproject.ApiResponse.ApiResponse;
import com.example.finalproject.Model.Details;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Staff;
import com.example.finalproject.Service.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/staff")
@RequiredArgsConstructor
@RestController
public class StaffController {

    private final StaffService staffService;

    @PostMapping("/add-staff")
    public ResponseEntity add(@RequestBody @Valid Staff staff) {
        staffService.addStaff(staff);
        return ResponseEntity.status(200).body(new ApiResponse("Staff added"));
    }
    @DeleteMapping("/delete-staff/{staffId}")
    public ResponseEntity deleteOrder(@AuthenticationPrincipal MyUser company,@PathVariable Integer staffId){
        staffService.deleteStaff(company,staffId);
        return ResponseEntity.status(200).body(new ApiResponse("Staff deleted "));
    }
    @PutMapping("/update-staff/{staffId}")
    public ResponseEntity updateProduct(@AuthenticationPrincipal MyUser company,@Valid @RequestBody Staff staff , @PathVariable Integer staffId){
        staffService.updateStaff(company,staff, staffId);
        return ResponseEntity.status(200).body(new ApiResponse("Staff Updated"));
    }
    @GetMapping("/get-id/{staffId}")
    public ResponseEntity getStaffById(@AuthenticationPrincipal MyUser user,@PathVariable Integer staffId){
        Staff staff=staffService.getStaffById(user.getId(),staffId);
        return ResponseEntity.status(200).body(staff);
    }

    @GetMapping("/get-gender/{staffGender}")
    public ResponseEntity getStaffByGender(@PathVariable String staffGender){
        List<Staff> staff=staffService.getStaffByGender(staffGender);
        return ResponseEntity.status(200).body(staff);
    }

    @GetMapping("/get-nationality/{staffNationality}")
    public ResponseEntity getStaffByNationality(@PathVariable String staffNationality){
        List<Staff> staff=staffService.getStaffByNationality(staffNationality);
        return ResponseEntity.status(200).body(staff);
    }

    @GetMapping("/get-allstaff")
    public ResponseEntity getAllStaff() {
        List<Staff> staff = staffService.getStaff();
        return ResponseEntity.status(200).body(staff);
    }

}