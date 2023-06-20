package com.example.finalproject.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@AllArgsConstructor
@Setter
@Getter
@RequiredArgsConstructor
public class MyUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

   @Column(columnDefinition = "varchar(25) not null check (role='admin' or role='customer' or role='company')")
    private String role;



    @OneToOne(mappedBy = "myUser",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Company company;



    @OneToOne(cascade = CascadeType.ALL,mappedBy = "myUser")
    @PrimaryKeyJoinColumn
    private Customer customer;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
