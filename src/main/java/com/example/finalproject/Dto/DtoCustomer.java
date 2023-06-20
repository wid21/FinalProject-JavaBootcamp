package com.example.finalproject.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class DtoCustomer {

    @NotEmpty(message = "name shouldn't be empty")
    @Size(min = 3,message = "username should have at least 3 letters")
    @Column(columnDefinition = "varchar(25) not null unique")
    private String username;

    @NotEmpty(message = "password cannot be empty")
    @Column(columnDefinition = "varchar(300) not null")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$", message = "Password must contain both letters and digits")
    private String password;

    @NotEmpty(message = "email should not  be empty")
    @Email
    @Column(columnDefinition = "varchar(100) not null")
    private String email;

    @NotEmpty(message = "phone number should not be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String phoneNumber;

    @NotEmpty(message = "Address should not be empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String address;

    private String role;

}
