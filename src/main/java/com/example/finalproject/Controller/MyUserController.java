package com.example.finalproject.Controller;

import com.example.finalproject.ApiResponse.ApiResponse;
import com.example.finalproject.Dto.DtoCompany;
import com.example.finalproject.Dto.DtoCustomer;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Repository.MyUserRepository;
import com.example.finalproject.Service.MyUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/myuser")
@RequiredArgsConstructor
@RestController
public class MyUserController {

    private final MyUserService myUserService;

    @PostMapping("/register-company")
    public ResponseEntity registerInNeedCompany(@Valid @RequestBody DtoCompany dtoCompany) {
        myUserService.registerCompany(dtoCompany);
        return ResponseEntity.status(200).body(new ApiResponse("Registered"));
    }
    @PostMapping("/register-customer")
    public ResponseEntity registerInNeedCompany(@Valid @RequestBody DtoCustomer dtoCustomer) {
        myUserService.registerCustomer(dtoCustomer);
        return ResponseEntity.status(200).body(new ApiResponse("Registered"));
    }
    @PostMapping("/register-admin")
    public ResponseEntity registerInNeedCompany(@Valid @RequestBody MyUser user) {
        myUserService.registerAdmin(user);
        return ResponseEntity.status(200).body(new ApiResponse("Registered admin "));
    }
    @PostMapping("/login")
    public ResponseEntity login() {
        return ResponseEntity.status(200).body(new ApiResponse("login user!"));
    }

    @PostMapping("/logout")
    public ResponseEntity logout() {
        return ResponseEntity.status(200).body(new ApiResponse("logout user!"));

    }
    @GetMapping("/getAll")
    public ResponseEntity getAll(){
        List<MyUser> myuser=myUserService.get();
        return ResponseEntity.status(200).body(myuser);
    }

}
