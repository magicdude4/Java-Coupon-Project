package facades;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import beans.Client;
import beans.Coupon;
import dao.CouponClientFacade;
import dbdao.CompanyDBDAO;
import dbdao.CouponDBDAO;
import dbdao.CustomerDBDAO;
import enums.ClientType;
import enums.CouponType;
import exceptions.CouponException;
import exceptions.LoginMismatch;
import exceptions.NullResult;


public class CustomerFacade extends Client implements CouponClientFacade {
	CustomerDBDAO Customer = new CustomerDBDAO();
	CouponDBDAO Coupon = new CouponDBDAO();
	CompanyDBDAO Company = new CompanyDBDAO();
	
	public CustomerFacade() {
		
	}

	public CustomerFacade(Long id, String name, String password, String email, ClientType type) {
		super(id, name, password, email, type);
	}

	public void buyCoupon(Long customerId, Coupon coupon) throws SQLException, NullResult {
		try {
			if(Boolean.TRUE.equals(Coupon.checkIfCouponExists(coupon.getImage()))) {
				Long coupId = Coupon.getCouponDBIdByImage(coupon.getImage());
				if((Coupon.checkCouponAvailability(coupId).equals("AVAILABLE"))&&(Boolean.FALSE.equals(Coupon.checkExpirationDate(coupId)))&&Boolean.TRUE.equals(Coupon.checkCouponAmount(coupId))) {
					Customer.buyCoupon(customerId, coupId);
					Coupon.updateCouponAmount(coupId, coupon.getAmount()-1);
					System.out.println("Coupon purchaced.");
				}else System.err.println("Coupon unavailable for purchace.");
			}else System.err.println("No coupon like that exists.");
        }catch (Exception e) {
        	System.err.println("There was a problem contacting the DB, "+e.getMessage());
        }		
	}
	
	public Collection<Coupon> viewPurchaseHistory(Long id) throws SQLException, NullResult {
		Coupon coupon = new Coupon();
		Collection<Coupon> tmpCoup = new ArrayList<>();
		
		try {
			tmpCoup = Customer.getAllCustomerCoupons(id);
			Iterator<Coupon> iter = tmpCoup.iterator();
			while (iter.hasNext()) {
				coupon = iter.next();
				coupon.toString();
			}
		}catch (Exception e) {
			System.err.println("There was a problem retrieving the coupons, "+e.getMessage());
		}return tmpCoup;
	}
	
	public CouponClientFacade login(String name, String password, ClientType clientType) throws LoginMismatch, SQLException{
		if (Boolean.TRUE.equals(Customer.checkCustomerCredentials(name, password))&&(clientType.equals(ClientType.CUSTOMER))&&(Customer.checkCustomerStatus(Customer.getCustomerIdByName(name))).equals("ACTIVE")) {
			System.out.println("Logged-in as "+name+".\n");
		}else System.err.println("Couldn't log-in as "+clientType+".");
		return new CustomerFacade();
	}
	
	public Collection<Coupon> viewCustomerCouponsByTitle(Long id, String title) throws SQLException, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		Coupon coup = new Coupon();
		
		try {
			tmpCoup = Customer.getAllCustomerCoupons(id);
			Iterator<Coupon> iter = tmpCoup.iterator();
			while (iter.hasNext()) {
				coup = iter.next();
				if (coup.getTitle().equals(title)) {
					coup.toString();
				}else tmpCoup.remove(coup);
			}
		}catch (Exception e) {
			System.err.println("Couldn't retrieve all coupons, "+e.getMessage());
		}return tmpCoup;	 
	}
	
	public Collection<Coupon> viewCustomerCouponsByStartDate(Long id, LocalDate startDate) throws SQLException, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		Coupon coup = new Coupon();
		
		try {
			tmpCoup = Customer.getAllCustomerCoupons(id);
			Iterator<Coupon> iter = tmpCoup.iterator();
			while (iter.hasNext()) {
				coup = iter.next();
				if (coup.getStartDate().equals(startDate)) {
					coup.toString();
				}else tmpCoup.remove(coup);
			}
		}catch (Exception e) {
			System.err.println("Couldn't retrieve all coupons, "+e.getMessage());
		}return tmpCoup;	 
	}
	
	public Collection<Coupon> viewCustomerCouponsByEndDate(Long id,LocalDate endDate) throws SQLException, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		Coupon coup = new Coupon();
		
		try {
			tmpCoup = Customer.getAllCustomerCoupons(id);
			Iterator<Coupon> iter = tmpCoup.iterator();
			while (iter.hasNext()) {
				coup = iter.next();
				if (coup.getEndDate().equals(endDate)) {
					coup.toString();
				}else tmpCoup.remove(coup);
			}
		}catch (Exception e) {
			System.err.println("Couldn't retrieve all coupons, "+e.getMessage());
		}return tmpCoup;	 
	}
	
	public Collection<Coupon> viewCustomerCouponsByAmount(Long id, int amount) throws SQLException, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		Coupon coup = new Coupon();
		
		try {
			tmpCoup = Customer.getAllCustomerCoupons(id);
			Iterator<Coupon> iter = tmpCoup.iterator();
			while (iter.hasNext()) {
				coup = iter.next();
				if (coup.getAmount() == amount) {
					coup.toString();
				}else tmpCoup.remove(coup);
			}
		}catch (Exception e) {
			System.err.println("Couldn't retrieve all coupons, "+e.getMessage());
		}return tmpCoup;	 
	}
	
	public Collection<Coupon> viewCustomerCouponsByType(Long id, CouponType type) throws SQLException, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		Coupon coup = new Coupon();
		
		try {
			tmpCoup = Customer.getAllCustomerCoupons(id);
			Iterator<Coupon> iter = tmpCoup.iterator();
			while (iter.hasNext()) {
				coup = iter.next();
				System.out.println(coup.getType()+", "+type);
				if (coup.getType().equals(type)) {
					coup.toString();
				}else tmpCoup.remove(coup);
			}
		}catch (Exception e) {
			System.err.println("Couldn't retrieve all coupons, "+e.getMessage());
		}return tmpCoup;	 
	}
	
	public Collection<Coupon> viewCustomerCouponsByPrice(Long id, Double price) throws SQLException, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		Coupon coup = new Coupon();
		
		try {
			tmpCoup = Customer.getAllCustomerCoupons(id);
			Iterator<Coupon> iter = tmpCoup.iterator();
			while (iter.hasNext()) {
				coup = iter.next();
				if (coup.getPrice().equals(price)) {
					coup.toString();
				}else tmpCoup.remove(coup);
			}
		}catch (Exception e) {
			System.err.println("Couldn't retrieve all coupons, "+e.getMessage());
		}return tmpCoup;
	}
	
	public void updateCustomerPassword(Long id, String password) throws SQLException, NullResult {
		try {
			Customer.updateCustomer(Customer.getCustomerById(id), password, null);
		} catch (Exception e) {
			System.err.println("Couldn't update password, "+e.getMessage());;
		}
	}
	
	public void updateCustomerEmail(Long id, String email) throws SQLException, NullResult {
		try {
			Customer.updateCustomer(Customer.getCustomerById(id), null, email);
		} catch (Exception e) {
			System.err.println("Couldn't update email, "+e.getMessage());;
		}
	}
}
