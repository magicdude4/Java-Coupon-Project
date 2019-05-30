package dao;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

import beans.Coupon;
import enums.CouponType;
import exceptions.CouponException;
import exceptions.NullResult;

public interface CouponDAO {

	Long createCoupon(Coupon coupon) throws SQLException, NullResult, CouponException;
	void removeCoupon(Coupon Coupon) throws SQLException, Exception;
	Coupon getCoupon(Long id) throws SQLException, CouponException;
	boolean checkExpirationDate(Long id) throws SQLException, NullResult, CouponException;
	boolean checkIfCouponExists(String image) throws SQLException, NullResult, CouponException;
	void getCouponDetails(Coupon coupon) throws SQLException, NullResult, CouponException;
	Collection<Coupon> getAllCoupons() throws SQLException, NullResult, CouponException;
	Collection<Coupon> getCouponsByTitle(String title) throws SQLException, NullResult, CouponException;
	Collection<Coupon> getCouponsByStartDate(LocalDate startDate) throws SQLException, NullResult, CouponException;
	Collection<Coupon> getCouponsByEndDate(LocalDate endDate) throws SQLException, NullResult, CouponException;
	Collection<Coupon> getCouponsByAmount(int amount) throws SQLException, NullResult, CouponException;
	Collection<Coupon> getCouponsByType(CouponType couponType) throws SQLException, NullResult, CouponException;
	Collection<Coupon> getCouponsByPrice(Double price) throws SQLException, NullResult, CouponException;
	Collection<Coupon> getCouponsByAvailability(String status) throws SQLException, NullResult, CouponException;
	Collection<Coupon> getCouponsByActivity(String status) throws SQLException, NullResult, CouponException;
	
}
