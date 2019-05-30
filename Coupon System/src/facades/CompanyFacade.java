package facades;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import beans.Client;
import beans.Company;
import beans.Coupon;
import dao.CouponClientFacade;
import dbdao.CompanyDBDAO;
import dbdao.CouponDBDAO;
import enums.ClientType;
import enums.CouponType;
import exceptions.CouponException;
import exceptions.LoginMismatch;
import exceptions.NullResult;

public class CompanyFacade extends Client implements CouponClientFacade {
	CompanyDBDAO company = new CompanyDBDAO();
	CouponDBDAO coupon = new CouponDBDAO();

	public CompanyFacade() {
		
	}
	
	public CompanyFacade(Long id, String name, String password, String email, ClientType type) {
		super(id, name, password, email, type);
	}
	
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) throws LoginMismatch, NullResult, SQLException {
		if (Boolean.TRUE.equals(company.checkCompanyCredentials(name, password))&&(clientType.equals(ClientType.COMPANY))&&company.checkCompanyStatus(company.getCompanyIdByName(name)).equals("ACTIVE")) {
			System.out.println("Logged-in as "+name+" company.\n");
		}else System.err.println("Couldn't log-in as "+name+" company.");
		return new CompanyFacade();
	}
	
	public void addCoupon(Coupon Coupon, Long companyId) throws SQLException, NullResult {
		try {
			if(Boolean.TRUE.equals(coupon.checkIfCouponExists(Coupon.getImage()))) {
				Long coupId = coupon.getCouponDBIdByImage(Coupon.getImage());
				if((coupon.checkCouponAvailability(coupId).equals("AVAILABLE"))&&(Boolean.FALSE.equals(coupon.checkExpirationDate(coupId)))) {
					company.addCoupon(coupId, companyId);
					System.out.println("This coupon has been added to "+company.getCompanyNameById(companyId)+" company's inventory.");
				}else if((coupon.checkCouponAvailability(coupId).equals("AVAILABLE"))&&(Boolean.TRUE.equals(coupon.checkExpirationDate(coupId)))) {
					System.err.println("This coupon has expired.");
				}else if((coupon.checkCouponAvailability(coupId).equals("UNAVAILABLE"))&&(Boolean.FALSE.equals(coupon.checkExpirationDate(coupId)))) {
					System.err.println("This coupon has been bought.");
				}else if((coupon.checkCouponAvailability(coupId).equals("UNAVAILABLE"))&&(Boolean.TRUE.equals(coupon.checkExpirationDate(coupId)))) {
					System.err.println("This coupon has been used.");
				}
			}else System.err.println("No coupon like that exists.");
        }catch (Exception e) {
        	System.err.println("There was a problem contacting the DB, "+e.getMessage());
        }		
	}
	
	public void removeCoupon(Coupon Coupon) throws SQLException, NullResult, CouponException {
		if (Boolean.TRUE.equals(coupon.checkIfCouponExists(Coupon.getImage()))) {
			coupon.removeCoupon(Coupon);
			System.out.println("Coupon availability status changed to 'UNAVAILABLE'.");
		}else System.err.println("No coupon like that exists.");
	}
	
	public void updateCoupon(Long id, Object value, String indicator) throws Exception {
		
		try {
			switch (indicator) {
				case "Title":
					coupon.updateCouponTitle(id, (String)value);
				break;
				case "StartDate":
					coupon.updateCouponStartDate(id, (LocalDate)value);
				break;
				case "EndDate":
					coupon.updateCouponEndDate(id, (LocalDate)value);
				break;
				case "Amount":
					coupon.updateCouponAmount(id, (int)value);
				break;
				case "Type":
					coupon.updateCouponType(id, (CouponType)value);
				break;
				case "Message":
					coupon.updateCouponMessage(id, (String)value);
				break;
				case "Price":
					coupon.updateCouponPrice(id, (Double)value);
				break;
				case "Image":
					coupon.updateCouponImage(id, (String)value);
			}
		}catch (NullResult e) {
			System.err.println("Couldn't update coupon. "+e.getMessage());
		}
	}
	
	public void viewCompanyDetails(Company Company) throws SQLException, CouponException {
		CompanyDBDAO company = new CompanyDBDAO();
		if (Boolean.TRUE.equals(company.checkIfCompanyExists(Company.getCompanyName()))) {
			company.getCompanyDetails(Company);
		}else System.err.println("No company like that exists.");	
	}
	
	public Collection<Coupon> viewAllCompanyCoupons(Long id) throws SQLException, NullResult {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		Coupon coup = new Coupon();
		
		try {
			tmpCoup = company.getAllCompanyCoupons(id);
			Iterator<Coupon> iter = tmpCoup.iterator();
			while (iter.hasNext()) {
				coup = iter.next();
				coup.toString();
			}
		}catch (Exception e) {
			System.err.println("Couldn't retrieve all coupons, "+e.getMessage());
		}return tmpCoup;
	}
	
	public Collection<Coupon> viewCompanyCouponsByTitle(Long id, String title) throws SQLException, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		Coupon coup = new Coupon();
		
		try {
			tmpCoup = company.getAllCompanyCoupons(id);
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
	
	public Collection<Coupon> viewCompanyCouponsByStartDate(Long id, LocalDate startDate) throws SQLException, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		Coupon coup = new Coupon();
		
		try {
			tmpCoup = company.getAllCompanyCoupons(id);
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
	
	public Collection<Coupon> viewCompanyCouponsByEndDate(Long id,LocalDate endDate) throws SQLException, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		Coupon coup = new Coupon();
		
		try {
			tmpCoup = company.getAllCompanyCoupons(id);
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
	
	public Collection<Coupon> viewCompanyCouponsByAmount(Long id, int amount) throws SQLException, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		Coupon coup = new Coupon();
		
		try {
			tmpCoup = company.getAllCompanyCoupons(id);
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
	
	public Collection<Coupon> viewCompanyCouponsByType(Long id, CouponType type) throws SQLException, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		Coupon coup = new Coupon();
		
		try {
			tmpCoup = company.getAllCompanyCoupons(id);
			Iterator<Coupon> iter = tmpCoup.iterator();
			while (iter.hasNext()) {
				coup = iter.next();
				if (coup.getType().equals(type)) {
					coup.toString();
				}else tmpCoup.remove(coup);
			}
		}catch (Exception e) {
			System.err.println("Couldn't retrieve all coupons, "+e.getMessage());
		}return tmpCoup;	 
	}
	
	public Collection<Coupon> viewCompanyCouponsByPrice(Long id, Double price) throws SQLException, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		Coupon coup = new Coupon();
		
		try {
			tmpCoup = company.getAllCompanyCoupons(id);
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
	
	public Collection<Coupon> viewCompanyCouponsByAvailability(Long id, String status) throws SQLException, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		Coupon coup = new Coupon();
		
		try {
			tmpCoup = company.getAllCompanyCoupons(id);
			Iterator<Coupon> iter = tmpCoup.iterator();
			while (iter.hasNext()) {
				coup = iter.next();
				if (coup.getPurchaceStatus().equals(status)) {
					coup.toString();
				}else tmpCoup.remove(coup);
			}
		}catch (Exception e) {
			System.err.println("Couldn't retrieve all coupons, "+e.getMessage());
		}return tmpCoup;	 
	}
	
	public Collection<Coupon> viewCompanyCouponsByActivity(Long id, String status) throws SQLException, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		Coupon coup = new Coupon();
		
		try {
			tmpCoup = company.getAllCompanyCoupons(id);
			Iterator<Coupon> iter = tmpCoup.iterator();
			while (iter.hasNext()) {
				coup = iter.next();
				if (coup.getExpirationStatus().equals(status)) {
					coup.toString();
				}else tmpCoup.remove(coup);
			}
		}catch (Exception e) {
			System.err.println("Couldn't retrieve all coupons, "+e.getMessage());
		}return tmpCoup;	 
	}

	public void viewCouponDetails(Coupon Coupon) throws SQLException, NullResult, CouponException {
		if (Boolean.TRUE.equals(coupon.checkIfCouponExists(Coupon.getImage()))) {
			coupon.getCouponDetails(Coupon);
		}else System.err.println("A coupon like that doesn't exist.");
	}
}
