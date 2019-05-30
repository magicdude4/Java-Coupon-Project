package dao;
import java.sql.SQLException;
import java.util.Collection;

import beans.Coupon;
import beans.Customer;
import exceptions.CouponException;
import exceptions.NullResult;

public interface CustomerDAO {
	
	public Long createCustomer(Customer Customer) throws SQLException;
	public void removeCustomer(Long id) throws SQLException, Exception;
	public Customer getCustomerById(Long id) throws SQLException;
	public Customer getCustomerByName(String name) throws SQLException;
	public Collection<Customer> getAllCustomers() throws SQLException;
	public void getCustomerList() throws SQLException;
	void updateCustomer(Customer customer, String password, String email) throws Exception;
	boolean checkCustomerCredentials(String Name, String Password) throws SQLException;
	Collection<Coupon> getAllCustomerCoupons(Long id) throws SQLException, NullResult, CouponException;
	boolean checkIfCustomerExistsById(Long id) throws SQLException;
}
