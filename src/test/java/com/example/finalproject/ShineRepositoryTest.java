package com.example.finalproject;

import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ShineRepositoryTest {

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    DetailsRepository detailsRepository;
    @Autowired
    MyUserRepository myUserRepository;
    @Autowired
    RateRepository rateRepository;
    @Autowired
    StaffRepository staffRepository;

    MyUser myUser;
    Booking booking1;
    Company company1;
    Customer customer1;
    Details details1;
    Rate rate1;
    Staff staff1;
    List<Details> detailss;

    @BeforeEach
    void setUp() {

        myUser = new MyUser(null, "Admin", "1234a", "admin@gmail.com", "admin", null, null);

        company1 = new Company(null, "Clean life", "0526282", "Jeddah", "62792", "pending", myUser, null, null);

        staff1 = new Staff(null, "mohammed", "male", 28, "Filipino", company1);

        details1 = new Details(null, "inside home", "Sterilization service", 3, 200, company1, null, null);

        customer1 = new Customer(null, "056633", "Riyadh", 1500, myUser, null);

        booking1 = new Booking(null, 999, 500, "new", details1, customer1, null, null);

        rate1 = new Rate(null, 4, "good", booking1);

        detailss = new ArrayList<>();
        detailss.add(details1);

    }

    @Test
    public void findBookingByIdTest() {
        myUserRepository.save(myUser);
        companyRepository.save(company1);
        staffRepository.save(staff1);
        detailsRepository.save(details1);
        customerRepository.save(customer1);
        bookingRepository.save(booking1);
        Booking booking = bookingRepository.findBookingById(booking1.getId());
        Assertions.assertThat(booking).isEqualTo(booking1);
    }

    @Test
    public void findCompanyByIdTest() {
        myUserRepository.save(myUser);
        companyRepository.save(company1);
        staffRepository.save(staff1);
        detailsRepository.save(details1);
        customerRepository.save(customer1);
        bookingRepository.save(booking1);
        Company company = companyRepository.findCompanyById(company1.getId());
        Assertions.assertThat(company).isEqualTo(company1);
    }

    @Test
    public void findCustomerByIdTest() {
        myUserRepository.save(myUser);
        companyRepository.save(company1);
        staffRepository.save(staff1);
        detailsRepository.save(details1);
        customerRepository.save(customer1);
        bookingRepository.save(booking1);
        Customer customer = customerRepository.findCustomerById(customer1.getId());
        Assertions.assertThat(customer).isEqualTo(customer1);
    }

    @Test
    public void findDetailsByCategoryTest() {
        myUserRepository.save(myUser);
        companyRepository.save(company1);
        staffRepository.save(staff1);
        detailsRepository.save(details1);
        customerRepository.save(customer1);
        bookingRepository.save(booking1);
        List<Details> detailss = detailsRepository.findDetailsByCategory(details1.getCategory());
        Assertions.assertThat(detailss.get(0)).isEqualTo(details1);
    }

    @Test
    public void findStaffByIdTest() {
        myUserRepository.save(myUser);
        companyRepository.save(company1);
        staffRepository.save(staff1);
        detailsRepository.save(details1);
        customerRepository.save(customer1);
        bookingRepository.save(booking1);
        Staff staff = staffRepository.findStaffById(staff1.getId());
        Assertions.assertThat(staff).isEqualTo(staff1);
    }
}