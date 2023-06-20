package com.example.finalproject;

import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.*;
import com.example.finalproject.Service.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShineServiceTest {

    @InjectMocks
    BookingService bookingService;
    @InjectMocks
    CompanyService companyService;
    @InjectMocks
    DetailsService detailsService;
    @Mock
    BookingRepository bookingRepository;
    @Mock
    CompanyRepository companyRepository;
    @Mock
    DetailsRepository detailsRepository;

    MyUser myUser;
    Booking booking1,booking2;
    Company company1,company2;
    Customer customer1;
    Details details1,details2;
    Rate rate1;
    Staff staff1,staff2;
    List<Booking> bookings;
    List<Details> detailss;

    List<Company> companies;

    @BeforeEach
    void setUp() {

        myUser=new MyUser(1,"Admin","admin@gmail.com","123","Admin",null,null);

        company1=new Company(null,"Clean life","0526282","Jeddah","62792","pending",myUser,null,null);
        company2=new Company(null,"Uni Clean ","058975","Riyadh","93673","Approved",myUser,null,null);

        staff1=new Staff(null,"mohammed","male",28,"Filipino",company1);
        staff2=new Staff(null,"sara","female",31,"Filipino",company2);

        details1=new Details(null,"inside home","Sterilization service",3,200,company1,null,null);
        details2=new Details(null,"outside home","Clean Garden",2,499,company2,null,null);

        customer1=new Customer(null,"056633","Riyadh",1500,myUser,null);

        booking1=new Booking(null,999,500,"new",details1,customer1,null,null);
        booking2=new Booking(null,2700,300,"completed",details2,customer1,null,null);

        rate1=new Rate(null,4,"good",booking1);

        bookings = new ArrayList<>();
        bookings.add(booking1);
        bookings.add(booking2);

    }

    @Test
    public void getAllBookingByTest(){
        when(bookingRepository.findAll()).thenReturn(bookings);
        List<Booking> bookings1 =bookingService.getallbooking();
        Assertions.assertThat(bookings1).isEqualTo(bookings);
        verify(bookingRepository,times(1)).findAll();
    }

    @Test
    public void getAllCompanyTest(){
        when(companyRepository.findAll()).thenReturn(companies);
        List<Company> companies1 =companyService.getCompany();
        Assertions.assertThat(companies1).isEqualTo(companies);
        verify(companyRepository,times(1)).findAll();
    }

    @Test
    public void changeBookingStatusTest(){
        when(companyRepository.findCompanyById(company2.getId())).thenReturn(company2);
        companyService.changeStatus(company2.getStatus(),company2.getId());
        verify(companyRepository,times(1)).findCompanyById(company2.getId());
        verify(companyRepository, times(1)).save(company2);
    }

    @Test
    public void addDetailsTest(){
        detailsService.add(details1);
        verify(detailsRepository,times(1)).save(details1);
    }

    @Test
    public void getDetailsByIdTest(){
        when(detailsRepository.findAll()).thenReturn(detailss);
        List<Details> details =detailsService.getDetails();
        Assertions.assertThat(details).isEqualTo(detailss);
        verify(detailsRepository,times(1)).findAll();
    }


}