package dao;
import java.sql.SQLException;
import java.util.Collection;

import beans.Company;
import beans.Coupon;
import exceptions.CouponException;
import exceptions.NullResult;


public interface CompanyDAO {
	
	Long createCompany(Company company) throws Exception;
	void removeCompany(Company company) throws SQLException;
	void updateCompanyName(Long id, String name) throws SQLException;
	void updateCompanyPassword(Long id, String password) throws SQLException;
	void updateCompanyEmail(Long id, String emall) throws SQLException;
	Company getCompanyById(Long id) throws Exception;
	Company getCompanyByName(String name) throws Exception;
	Collection<Company> getAllCompanies()throws SQLException, Exception;
	void getCompanyDetails(Company company) throws SQLException;
	Collection<Coupon> getAllCompanyCoupons(Long Id) throws SQLException, NullResult, CouponException;
	boolean checkIfCompanyExists(String name) throws SQLException;
	boolean checkCompanyInventory(Long id) throws SQLException;
	boolean checkCompanyCredentials(String name, String password) throws SQLException;
}
