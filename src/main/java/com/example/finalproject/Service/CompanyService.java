package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.Dto.DtoCompany;
import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final MyUserRepository myUserRepository;
    private final DetailsRepository detailsRepository;
    private final StaffRepository staffRepository;
    private final BookingRepository bookingRepository;

    public List<Company> getCompany(){
        return companyRepository.findAll();
    }

    public void updateCompany(Integer userId, Company company) {
        Company company1=companyRepository.findCompanyById(userId);
        if(company1==null){
            throw new ApiException("not found");
        }
        if(userId!=company1.getMyUser().getId()){
            throw new ApiException("unauthorized");
        }
        company1.setName(company.getName());
        company1.setAddress(company.getAddress());
        company1.setPhoneNumber(company.getPhoneNumber());
        company1.setLicenceNum(company.getLicenceNum());
        companyRepository.save(company1);
    }

    public void deleteCompany(MyUser company, Integer companyId) {
        Company companyEntity = companyRepository.findCompanyById(companyId);

        Set<Staff> staffs = companyEntity.getStaff();
        Set<Details> details = companyEntity.getDetails();
        for (Details detail : details) {
            Set<Booking> bookings = detail.getBookings();
            for (Booking booking : bookings) {
                if (booking.getStatus().equalsIgnoreCase("new")||booking.getStatus().equalsIgnoreCase("in Progress")) {
                    //bookingRepository.delete(booking);
                    throw new ApiException("Can not delete company there still bookings not completed");
                }
            }
            detailsRepository.delete(detail);
        }
        for (Staff staff : staffs) {
            staffRepository.delete(staff);
        }
        companyRepository.delete(companyEntity);
        myUserRepository.delete(company);
    }
    public void changeStatus(String status, Integer companyId){
        Company company  =companyRepository.findCompanyById(companyId);
        if (company == null){
            throw new ApiException("Company Not Found");
        }
        company.setStatus(status);
        companyRepository.save(company);
    }

    public Company getCompanyById(Integer companyId) {
        Company company1 = companyRepository.findCompanyById(companyId);
        if (company1 == null) {
            throw new ApiException("Company not found");
        }
        return company1;
    }
public Company getCompanyByName(String companyName) {
    Company company = companyRepository.findCompanyByName(companyName);
    if (company == null) {
        throw new ApiException("Company not found");
    }
    return company;
}

    public List<Company> getCompanyByStatus(String status){
        List<Company> company1=companyRepository.findCompaniesByStatus(status);
        if(company1==null){
            throw new ApiException("not found");
        }
        return company1;
    }



}

