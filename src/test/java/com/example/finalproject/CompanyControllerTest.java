package com.example.finalproject;
import com.example.finalproject.Controller.CompanyController;
import com.example.finalproject.Model.*;
import com.example.finalproject.Service.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CompanyController.class , excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class CompanyControllerTest {
    @MockBean
    BookingService bookingService;
    @MockBean
    CompanyService companyService;
    @MockBean
    CustomerService customerService ;
    @MockBean
    DetailsService detailsService ;
    @MockBean
    RateService rateService ;
    @MockBean
    StaffService staffService;
    @MockBean
    ReservationDateService reservationDateService;
    @MockBean
    MyUserService myUserService;

    @Autowired
    MockMvc mockMvc;
    MyUser myUser1;
    Company company1,company2;
    Customer customer1;
    Details details1,details2;
    Booking booking1,booking2;
    Rate rate1;
    Staff staff1,staff2;
    List<Booking> bookings;
    List<MyUser> myUsers;
    List<Company> companies;

    @BeforeEach
    void setUp() {

        myUser1=new MyUser(null,"Admin","admin@gmail.com","123","Admin",null,null);
        myUsers = Arrays.asList(myUser1);

        company1=new Company(null,"Clean life","0526282","Jeddah","62792","pending",myUser1,null,null);
        companies = Arrays.asList(company1);

        staff1=new Staff(null,"mohammed","male",28,"Filipino",company1);
        staff2=new Staff(null,"sara","female",31,"Filipino",company2);

        details1=new Details(null,"inside home","Sterilization service",3,200,company1,null,null);
        details2=new Details(null,"outside home","Clean Garden",2,499,company2,null,null);

        customer1=new Customer(null,"056633","Riyadh",1500,myUser1,null);

        booking1=new Booking(null,999,500,"new",details1,customer1,null,null);
        booking2=new Booking(null,2700,300,"completed",details2,customer1,null,null);

        rate1=new Rate(null,4,"good",booking1);

        bookings = new ArrayList<>();
        bookings.add(booking1);
        bookings.add(booking2);
    }

    @Test
    void getAllCompaniesTest() throws Exception {
        Mockito.when(companyService.getCompany()).thenReturn(companies);
        mockMvc.perform(get("/api/v1/company/get-allcompanies"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Clean life"));
    }
}


