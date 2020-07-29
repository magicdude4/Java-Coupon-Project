package com.anton.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.anton.api.models.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	Coupon findById(long id);

	@Query(value = "select * from soupon_system.Coupons where title = 1", nativeQuery = true)
	Coupon findByTitle(String title);
	
	List<Coupon> findAll();
}
