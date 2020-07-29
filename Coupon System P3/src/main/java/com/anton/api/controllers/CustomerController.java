package com.anton.api.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.anton.api.dao.CouponRepository;
import com.anton.api.models.Coupon;

@Controller("/customer")
public class CustomerController {

	@Autowired
	private CouponRepository couponRepository;

	public Coupon getCoupon(@PathVariable("id") long id) {
		return couponRepository.findById(id);
	}
	
	@GetMapping("/customer/coupons")
	public Collection<Coupon> getCouponList() {
		return couponRepository.findAll();
	}
}
