package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.Dto.DtoBooking;
import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class DetailsService {

    private final DetailsRepository detailsRepository;
    private final CompanyRepository companyRepository;
    private final StaffRepository staffRepository;
    private final ReservationDateRepository reservationDateRepository;
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;


    public void add(Details details){

        detailsRepository.save(details);
}
    public void addDetailsToStaffToCompany(MyUser company1, Integer details_id, Integer staff_id) {
        Company company = companyRepository.findCompanyById(company1.getId());
        Details details = detailsRepository.findDetailsById(details_id);
        Staff staff = staffRepository.findStaffById(staff_id);

        if (company == null) {
            throw new ApiException("Cannot add staff, company not found");
        }
        if (staff == null) {
            throw new ApiException("There is no staff to add");
        }
        if (details == null) {
            throw new ApiException("There is no details to add staff in to it ");
        }


        Company existingCompany2 = details.getCompany();
        if (existingCompany2 != null && !existingCompany2.equals(company)) {
            throw new ApiException("This details  is already assigned to another company");
        }
        if (existingCompany2 != null && existingCompany2.equals(company)) {
            throw new ApiException("This details is already assigned to this company");
        }
        Company existingCompany = staff.getCompany();
        if (existingCompany != null && !existingCompany.equals(company)) {
            throw new ApiException("This staff is already assigned to another company");
        }
        if (existingCompany != null && existingCompany.equals(company)) {
            throw new ApiException("This staff is already assigned to this company");
        }

        staff.setCompany(company);
        staffRepository.save(staff);
        if (details.getCompany() == null) {
            details.setCompany(company);
            detailsRepository.save(details);
        }
 }
    public void deleteDetails(MyUser company, Integer detailsId) {
        Company company1 = companyRepository.findCompanyById(company.getId());
        if (company1 == null) {
            throw new ApiException("Company not found");
        }
        Details details = detailsRepository.findDetailsById(detailsId);
        if (details == null) {
            throw new ApiException("Details not found");
        }
        if (company1.getId() != details.getCompany().getId()) {
            throw new ApiException("Details not associated with your company");
        }

        Set<Booking> bookings = details.getBookings();
        for (Booking booking : bookings) {
            if (booking.getStatus().equalsIgnoreCase("new")||booking.getStatus().equalsIgnoreCase("in Progress")) {
                //bookingRepository.delete(booking);
                throw new ApiException("Can not delete company there still bookings not completed");
            }
            booking.setDetails(null);
        }
        Set<ReservationDate> dates = details.getReservationDates();
        for (ReservationDate date : dates) {
            date.setDetails(null);
        }
        details.setCompany(null);
        detailsRepository.delete(details);
    }

    public void updateDetails(MyUser company,Details details, Integer detailsId){
        Company company1 = companyRepository.findCompanyById(company.getId());
        if (company1 == null) {
            throw new ApiException("Company not found");
        }
        Details details1=detailsRepository.findDetailsById(detailsId);
        if (details1 == null){
            throw new ApiException("details Not Found");
        }
        if (!details1.getCompany().equals(company1)) {
            throw new ApiException("service not associated with your company");
        }
        details1.setPrice(details.getPrice());
        details1.setHours(details.getHours());
        details1.setCategory(details.getCategory());
        details1.setDescription(details.getDescription());
        detailsRepository.save(details1);
    }
    public List<Details> getDetails(){

        return detailsRepository.findAll();
    }

    public Details getDetailsById(Integer companyId, Integer detailsId) {
        Details details = detailsRepository.findDetailsById(detailsId);
        if (details == null) {
            throw new ApiException("Service not found");
        }
        return details;
    }
    public List<Details> getDetailsByCategory(MyUser customer, String category){
        Customer customer1 = customerRepository.findCustomerById(customer.getId());
        List<Details> details=detailsRepository.findDetailsByCategory(category);

        if(details==null){
            throw new ApiException("not found");
        }
        if(customer1.getId()==null){
            throw new ApiException("customer not found");
        }
        return details;
    }

    public List<Details> getDetailsByPrice(double price){
        List<Details> details=detailsRepository.findDetailsByPrice(price);
        if(details==null){
            throw new ApiException("Price not found");
        }
        return details;
    }


    }



