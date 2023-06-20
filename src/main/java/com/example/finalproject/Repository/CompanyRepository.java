package com.example.finalproject.Repository;

import com.example.finalproject.Model.Company;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository  extends JpaRepository<Company,Integer> {

 Company findCompanyById(Integer company_Id);
 Company findCompanyByName(String name);
 List<Company> findCompaniesByStatus(String status);

}
