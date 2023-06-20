package com.example.finalproject.Confiuration;

//import com.example.finalproject.Service.MyUserDetailsService;
import com.example.finalproject.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringConfig {


    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/myuser/register-company",
                        "/api/v1/myuser/register-customer",
                        "/api/v1/myuser/login","/api/v1/myuser/logout",
                        "/api/v1/company/get-name/{companyName}",
                        "/api/v1/details/get-alldetails",
                        "/api/v1/details/get-price/{detailsPrice}",
                        "/api/v1/details/get-id/{detailsId}",
                        "/api/v1/date/get-reservedates",
                        "/api/v1/customer/get-id/{customerId}",
                        "/api/v1/myuser/register-admin",
                        "/api/v1/booking/get-categorycust/{detailsCategory}",
                        "/api/v1/rate/get-Rate/{detailId}",
                        "/api/v1/company/get-id/{companyId}").permitAll()

                .requestMatchers("/api/v1/staff/add-staff",
                        "/api/v1/staff/delete-staff/{staffId}",
                        "/api/v1/staff/update-staff/{staffId}",
                        "/api/v1/staff/get-id/{staffId}",
                        "/api/v1/staff/get-gender/{staffGender}",
                        "/api/v1/staff/get-nationality/{staffNationality}",
                        "/api/v1/details/add-details",
                        "/api/v1/details/delete-details/{detailsId}",
                        "/api/v1/details/update-details/{detailsId}",
                        "/api/v1/company/update-company",
                        "/api/v1/booking/get-companyOrders",
                        "/api/v1/booking/change-status/{status}/{bookingId}",
                        "/api/v1/booking/get-statuscomp/{status}",
                        "/api/v1/details/add-details-toStaff/{details_id}/{staff_id}").hasAuthority("company")

                .requestMatchers("/api/v1/booking/add-booking",
                        "/api/v1/booking/update-booking/{bookingId}",
                        "/api/v1/booking/cancel-booking/{bookingId}",
                        "/api/v1/customer/update-customer",
                        "/api/v1/booking/get-customerOrders",
                        "/api/v1/booking/get-statuscus/{status}",
                        "/api/v1/rate/add-rate").hasAuthority("customer")

                .requestMatchers("/api/v1/booking/add-points/{customerId}/{bookingId}",
                       "/api/v1/customer/get-allcustomer",
                       "/api/v1/customer/delete/{customerId}",
                       "/api/v1/company/change-status/{status}/{companyId}",
                       "/api/v1/company/delete/{companyId}",
                       "/api/v1/customer/delete/{customerId}",
                       "/api/v1/booking/delete-booking/{bookingId}",
                       "/api/v1/myuser/getAll","/api/v1/booking/get-allbooking",
                       "/api/v1/booking/get-status/{status}",
                       "/api/v1/company/get-status/{status}",
                       "/api/v1/company/get-allcompanies",
                       "/api/v1/customer/get-id/{customerId}",
                       "/api/v1/staff/get-allstaff").hasAuthority("admin")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/myuser/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}


