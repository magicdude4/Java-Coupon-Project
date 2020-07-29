package com.anton.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.anton.api.models.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	Company findById(long id);
	
	//@Query(value = "select * from coupon_system.companies where name = 1", nativeQuery = true)
	Company findByName(String name);
}
