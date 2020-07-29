package com.anton.api.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.anton.api.dao.CompanyRepository;
import com.anton.api.dao.CouponRepository;
import com.anton.api.dao.CustomerRepository;
import com.anton.api.models.Company;
import com.anton.api.models.Coupon;
import com.anton.api.models.Customer;

@Controller("/company")
public class CompanyController {
	
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CouponRepository couponRepository;
	
	@GetMapping("/customer/{id}")
	public Customer getCustomer(@PathVariable("id") long id) {
		return customerRepository.findById(id);
	}
	
	@GetMapping("/coupon/{id}")
	public Coupon getCoupon(@PathVariable("id") long id) {
		return couponRepository.findById(id);
	}
	
	@GetMapping("/customers")
	public Collection<Customer> getCustomerList() {
		return customerRepository.findAll();
	}
	
	@GetMapping("/company/coupons")
	public Collection<Coupon> getCouponList() {
		return couponRepository.findAll();
	}
	
	@GetMapping("/customer/{name}")
	public Customer getCustomer(@PathVariable("name") String name) {
		return customerRepository.findByName(name);
	}
	
	@PostMapping(path = "/addCustomer", consumes = { "application/json" })
	public void addCustomer(@RequestBody Customer customer) {
		customerRepository.save(customer);
	}
	
	@PostMapping(path = "/addCoupon", consumes = { "application/json" })
	public void addCoupon(@RequestBody Coupon coupon) {
		couponRepository.save(coupon);
	}
	
	@DeleteMapping("/removeCustomer/{id}")
	public void removeCustomer(@PathVariable("id") long id) {
		customerRepository.delete(customerRepository.findById(id));
	}
	
	@DeleteMapping("/removeCoupon/{id}")
	public void removeCoupon(@PathVariable("id") long id) {
		couponRepository.delete(couponRepository.findById(id));
	}
	
	@PutMapping(path = "/updateCustomer", consumes = { "application/json" })
	public void updateCustomer(@RequestBody Customer customer) {
		customerRepository.save(customer);
	}
	
	@PutMapping(path = "/updateCoupon", consumes = { "application/json" })
	public void updateCoupon(@RequestBody Coupon coupon) {
		couponRepository.save(coupon);
	}
	
	@PutMapping(path = "/updateCompany", consumes = { "application/json" })
	public void updateCompany(@RequestBody Company company) {
		companyRepository.save(company);
	}
}
