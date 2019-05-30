package dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import beans.Coupon;
import beans.Customer;
import dao.CustomerDAO;
import enums.CouponType;
import exceptions.CouponException;
import exceptions.NullResult;
import utility.Methods;

public class CustomerDBDAO implements CustomerDAO {
	
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	
	
	public CustomerDBDAO() {
		
	}

	@Override
	public Long createCustomer(Customer Customer) throws SQLException {
		Long id = null;
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO `dbcoupon`.`customer` (`username`, `password`, `email`, `status`) VALUES (?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, Customer.getCustomerName());
			preparedStatement.setString(2, Customer.getPassword());
			preparedStatement.setString(3, Customer.getEmail());
			preparedStatement.setString(4, "ACTIVE");
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			resultSet.next();
			id = resultSet.getLong(1);
			System.out.println("Insert succeeded. New id: " + id);
		}catch (Exception ex) {
			System.out.println(ex.getMessage());
		}finally {
			connection.close();
		}
		return id;
	}

	@Override
	public void removeCustomer(Long id) throws Exception {

		try {
		connection = Methods.getConnection();
		preparedStatement = connection.prepareStatement("UPDATE `dbcoupon`.`customer` SET `status` = 'INACTIVE' WHERE `ID` ="+id+";");
		preparedStatement.executeUpdate();
		System.out.println("Customer status changed to 'INACTIVE'.");
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}finally {
		connection.close();
		}
	}

	@Override
	public void updateCustomer(Customer customer, String password, String email) throws Exception {

		try {	
			connection = Methods.getConnection();
			if ((password != null)&&(email == null)) {
				preparedStatement = connection.prepareStatement("UPDATE `dbcoupon`.`customer` (`password`) VALUES (?) WHERE `ID` ="+customer.getId()+";");
				preparedStatement.setString(1, password);
			}else if ((email != null)&&(password == null)) {
				preparedStatement = connection.prepareStatement("UPDATE `dbcoupon`.`customer` (`email`) VALUES (?) WHERE `ID` ="+customer.getId()+";");
				preparedStatement.setString(1, email);
			}else if ((email != null)&&(password != null)) {
				preparedStatement = connection.prepareStatement("UPDATE `dbcoupon`.`customer` (`password`, `email`) VALUES (?, ?) WHERE `ID` ="+customer.getId()+";");
				preparedStatement.setString(1, password);
				preparedStatement.setString(2, email);
			}
			preparedStatement.executeUpdate();
			System.out.println("Update succeeded.");
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}finally {
		connection.close();
		}
	}
	
	public void buyCoupon(Long customerId, Long couponId) throws SQLException, NullResult {

		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO `dbcoupon`.`customercoupons` (`CustomerID`, `CouponID`) VALUES (?, ?);");
			preparedStatement.setLong(1, customerId);
			preparedStatement.setLong(2, couponId);
			preparedStatement.executeUpdate();
			
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}
	}

	@Override
	public Customer getCustomerById(Long id) throws SQLException, NullResult {
		Customer customer = new Customer();

		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`customer` WHERE `ID` ="+id+";");
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				while (resultSet.next()) {
					customer = new Customer(id, resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("status"));
				}
			}
		}catch (Exception ex) {
			System.out.println(ex.getMessage());
		}finally {
			connection.close();
		}return customer;
	}

	@Override
	public Customer getCustomerByName(String name) throws SQLException, NullResult {
		Customer customer = new Customer();
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`customer` WHERE `username` ="+name+";");
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				while (resultSet.next()) {
					customer = new Customer(resultSet.getLong("ID"), name, resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("status"));
				}
			}
		}catch (Exception ex) {
			System.out.println(ex.getMessage());
		}finally {
			connection.close();
		}return customer;
	}
	
	@Override
	public Collection<Customer> getAllCustomers() throws SQLException, NullResult {
		Collection<Customer> tmpCust = new ArrayList<>();

		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`customer`;");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Customer customer = new Customer(resultSet.getLong("ID"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("status"));
				tmpCust.add(customer);
			}
		}catch (Exception ex) {
			System.out.println(ex.getMessage());
		}finally {
			connection.close();
		}return tmpCust;
	}

	@Override
	public Collection<Coupon> getAllCustomerCoupons(Long id) throws SQLException, NullResult, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		Collection<Long> tmpID = new ArrayList<>();
		Long cID = null;
		ResultSet resultSet2 = null;
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT `CouponID` FROM `dbcoupon`.`customercoupons` WHERE `CustomerID` = ?;");
			preparedStatement.setLong(1, id);
			ResultSet resultSet1 = preparedStatement.executeQuery();
			 
			while (resultSet1.next()) {
				cID = resultSet1.getLong("CouponID");
				tmpID.add(cID);
			}
			Iterator<Long> iter = tmpID.iterator();
			while (iter.hasNext()) {
				PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT * FROM `dbcoupon`.`coupon` WHERE `ID` = ?;");
				preparedStatement2.setLong(1, iter.next());
				resultSet2 = preparedStatement2.executeQuery();
			    resultSet2.next();			
				java.sql.Date startDate = (java.sql.Date) resultSet2.getObject("startDate");
				java.sql.Date endDate = (java.sql.Date) resultSet2.getObject("endDate");
				Coupon coupon = new Coupon(resultSet2.getLong("ID"), resultSet2.getString("title"), startDate.toLocalDate(), endDate.toLocalDate(), resultSet2.getInt("amount"), CouponType.valueOf(resultSet2.getString("type")), resultSet2.getString("message"), resultSet2.getDouble("price"), resultSet2.getString("image"), resultSet2.getString("purchaceStatus"), resultSet2.getString("expirationStatus"));
				tmpCoup.add(coupon);
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}
		return tmpCoup;
	}
	
	public Collection<Coupon> getAllCurrentCustomerCoupons(Long id) throws SQLException, NullResult, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		Long cID = null;
		ResultSet resultSet2 = null;
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT `CouponID` FROM `dbcoupon`.`customercoupons` WHERE `CustomerID` = ?;");
			preparedStatement.setLong(1, id);
			ResultSet resultSet1 = preparedStatement.executeQuery();
			
			if (resultSet1.next()) {
				cID = resultSet1.getLong("CouponID");
				PreparedStatement ps = connection.prepareStatement("SELECT * FROM `dbcoupon`.`coupon` WHERE `ID` = ?;");
				ps.setLong(1, cID);
				resultSet2 = ps.executeQuery();
			}
			
			while (resultSet2.next()) {
					java.sql.Date startDate = (java.sql.Date) resultSet2.getObject("startDate");
					java.sql.Date endDate = (java.sql.Date) resultSet2.getObject("endDate");
					if (resultSet2.getString("expirationStatus").equals("ACTIVE")&&resultSet2.getString("purchaceStatus").equals("UNAVAILABLE")) {
						Coupon coupon = new Coupon(resultSet2.getLong("ID"), resultSet2.getString("title"), startDate.toLocalDate(), endDate.toLocalDate(), resultSet2.getInt("amount"), CouponType.valueOf(resultSet2.getString("type")), resultSet2.getString("message"), resultSet2.getDouble("price"), resultSet2.getString("image"), "ACTIVE", "UNAVAILABLE");
						tmpCoup.add(coupon);
					}else continue;
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}
		return tmpCoup;
	}

	@Override
	public boolean checkCustomerCredentials(String name, String password) throws SQLException, NullResult {
		boolean condition = false;

		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`customer`;");
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				if (name.equals(resultSet.getString("username"))&&password.equals(resultSet.getString("password"))) {
					condition = true;
					break;
				}
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}return condition;
	}

	@Override
	public void getCustomerList() throws SQLException, NullResult {
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`customer`;");
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				System.out.println("Customer "+resultSet.getString("username")+":\r\n"+
						" Password: "+resultSet.getString("password")+" .\r\n"+
						" Email: "+resultSet.getString("email")+" .\r\n"+
						" Status: "+resultSet.getString("status")+" .\n");
			}
		}catch (Exception ex) {
			System.out.println(ex.getMessage());
		}finally {
			connection.close();
		}
	}
	
	@Override
	public boolean checkIfCustomerExistsById(Long id) throws SQLException, NullResult {
		boolean condition = false;

		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`customer` WHERE `ID` = ?;");
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				condition = true;
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}return condition;
	}
	
	public boolean checkIfCustomerExistsByName(String name) throws SQLException, NullResult {
		boolean condition = false;

		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`customer`;");
			resultSet = preparedStatement.executeQuery();
		
			while (resultSet.next()) {
				if (resultSet.getString("username").equals(name)) {
					condition = true;
					break;
				}
			}
		}catch (Exception ex) {
			System.out.println(ex.getMessage());
		}finally {
			connection.close();
		}return condition;
	}
	
	public String checkCustomerStatus(Long id) throws SQLException, NullResult {
		String status = null;

		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`customer` WHERE `ID` =?;");
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			status = resultSet.getString("status");
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}return status;
	}
	
	public Long getCustomerIdByName(String name) throws SQLException, NullResult {
		Long id = null;
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT `ID` FROM `dbcoupon`.`customer` WHERE `username` =?");
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();	
			
			if (resultSet.next()) {
				id = resultSet.getLong("ID");
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}return id;
	}

}
