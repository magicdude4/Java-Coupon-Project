package com.anton.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anton.api.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	Customer findById(long id);
	
	List<Customer> findAll();

	Customer findByName(String name);
}
