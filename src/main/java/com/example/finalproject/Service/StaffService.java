package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.Model.Company;
import com.example.finalproject.Model.Details;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Staff;
import com.example.finalproject.Repository.CompanyRepository;
import com.example.finalproject.Repository.StaffRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class StaffService {


    private final StaffRepository staffRepository;
    private final CompanyRepository companyRepository;

    public void addStaff(Staff staff){

        staffRepository.save(staff);
    }

    public void updateStaff(MyUser company, Staff staff, Integer staffId) {
        Company company1 = companyRepository.findCompanyById(company.getId());
        if (company1 == null) {
            throw new ApiException("Company not found");
        }
        Staff staff1 = staffRepository.findStaffById(staffId);
        if (staff1 == null) {
            throw new ApiException("Staff not found");
        }
        if (!staff1.getCompany().equals(company1)) {
            throw new ApiException("Staff not associated with your company");
        }

        staff1.setName(staff.getName());
        staff1.setGender(staff.getGender());
        staff1.setAge(staff.getAge());
        staff1.setNationality(staff.getNationality());
        staffRepository.save(staff1);
    }
    public void deleteStaff(MyUser company, Integer staffId) {
        Company company1 = companyRepository.findCompanyById(company.getId());
        if (company1 == null) {
            throw new ApiException("Company not found");
        }
        Staff staff = staffRepository.findStaffById(staffId);
        if (staff == null) {
            throw new ApiException("Staff not found");
        }
        if (company1.getId() != staff.getCompany().getId()) {
            throw new ApiException("Staff not associated with your company");
        }
        company1.getStaff().remove(staff);
        staffRepository.delete(staff);
    }


    public Staff getStaffById(Integer companyId,Integer staffId){
        Staff staff=staffRepository.findStaffById(staffId);
        if(staff==null){
            throw new ApiException("staff not found");
        }
        if(companyId!=staff.getCompany().getId()){
            throw new ApiException("not found");
        }
        return staff;
    }
    public List<Staff> getStaffByGender(String gender){
        List<Staff> staff=staffRepository.findStaffByGender(gender);
        if(staff==null){
            throw new ApiException("Gender not found");
        }
        return staff;
    }
    public List<Staff> getStaffByNationality(String nationality){
        List<Staff> staff=staffRepository.findStaffByNationality(nationality);
        if(staff==null){
            throw new ApiException("Nationality not found");
        }
        return staff;
    }

    public List<Staff> getStaff(){
        return staffRepository.findAll();
    }

}

