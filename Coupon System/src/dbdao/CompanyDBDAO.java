package dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import beans.Company;
import beans.Coupon;
import dao.CompanyDAO;
import enums.CouponType;
import exceptions.CouponException;
import exceptions.NullResult;
import utility.Methods;

public class CompanyDBDAO implements CompanyDAO {
	
	Connection connection;
	PreparedStatement preparedStatement, preparedStatement2;
	ResultSet resultSet;	

	public CompanyDBDAO() {
		
	}

	@Override
	public Long createCompany(Company Company) throws SQLException, NullResult {
		Long id = null;
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO `dbcoupon`.`company` (`companyName`, `password`, `email`, `status`) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, Company.getCompanyName());
			preparedStatement.setString(2, Company.getPassword());
			preparedStatement.setString(3, Company.getEmail());
			preparedStatement.setString(4, "ACTIVE");
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			resultSet.next();
			id = resultSet.getLong(1);
			System.out.println("Insert succeeded. New id: " + id);
		}catch (Exception ex) {
			System.err.println("Couldn't create company, "+ex.getMessage());
		}finally {
			connection.close();
		}
		return id;
	}
 
	@Override
	public void removeCompany(Company Company) throws SQLException, NullResult {
		
		try {
		connection = Methods.getConnection();
		preparedStatement = connection.prepareStatement("UPDATE `dbcoupon`.`company` SET `status` = 'INACTIVE' WHERE `companyName` = ?;");
		preparedStatement.setString(1, Company.getCompanyName());
		preparedStatement.executeUpdate();
		}
		catch (Exception ex) {
			System.err.println("Removal unsuccessful. "+ex.getMessage());
		}finally {
		connection.close();
		}
	}

	@Override
	public void updateCompanyName(Long id, String name) throws SQLException, NullResult {

		try {	
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("UPDATE `dbcoupon`.`company` SET `companyName` = ? WHERE `ID` = ?;");
			preparedStatement.setString(1, name);
			preparedStatement.setLong(2, id);
			preparedStatement.executeUpdate();
			System.out.println("Update succeeded.");
		}
		catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
		connection.close();
		}
	}
	
	@Override
	public void updateCompanyPassword(Long id, String password) throws SQLException, NullResult {

		try {	
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("UPDATE `dbcoupon`.`company` SET `password` = ? WHERE `ID` = ?;");
			preparedStatement.setString(1, password);
			preparedStatement.setLong(2, id);
			preparedStatement.executeUpdate();
			System.out.println("Update succeeded, recieved ID: "+id);
		}
		catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
		connection.close();
		}
	}
	
	@Override
	public void updateCompanyEmail(Long id, String emall) throws SQLException, NullResult {

		try {	
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("UPDATE `dbcoupon`.`company` SET `email` = ? WHERE `ID` = ?;");
			preparedStatement.setString(1, emall);
			preparedStatement.setLong(2, id);
			preparedStatement.executeUpdate();
			System.out.println("Update succeeded.");
		}
		catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
		connection.close();
		}
	}

	@Override
	public Company getCompanyById(Long id) throws SQLException, NullResult {
		Company company = null;
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`company` WHERE `ID` = ?;");
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()){					
				company = new Company(resultSet.getLong("ID"), resultSet.getString("companyName"), resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("status"));
			}
		}catch (Exception ex) {
			System.err.println("There is an error in the SQL code, "+ex.getMessage());
		}finally {
			connection.close();
		}return company;
	}
	
	@Override
	public Company getCompanyByName(String name) throws SQLException, NullResult {
		Company company = null;
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`company` WHERE `companyName` = ?;");
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()){
				company = new Company(resultSet.getLong("ID"), name, resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("status"));	
				}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}return company;
	}

	@Override
	public Collection<Company> getAllCompanies() throws SQLException, NullResult {
		Collection<Company> tmpComp = new ArrayList<>();
		Company company = null;
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`company`;");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				company = new Company(resultSet.getLong("ID"), resultSet.getString("companyName"), resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("status"));
				tmpComp.add(company);
			}
		}catch (Exception ex) {
			System.err.println("There was a problem executing the SQL command, "+ex.getMessage());
		}finally {
			connection.close();
		}return tmpComp;
	}

	@Override
	public boolean checkCompanyCredentials(String name, String password) throws SQLException, NullResult {
		boolean condition = false;

		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`company`;");
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				if (name.equals(resultSet.getString("companyName"))&&password.equals(resultSet.getString("password"))) {
					condition = true;
					break;
				}
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}
		return condition;
	}
	
	@Override
	public boolean checkIfCompanyExists(String name) throws SQLException, NullResult {
		boolean condition = false;

		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`company`;");
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				if (name.equals(resultSet.getString("companyName"))) {
					condition = true;
					break;
				}
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}
		return condition;
	}
	
	public boolean checkCompanyPassword(String name, String password) throws SQLException, NullResult {
		boolean condition = false;

		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`company` WHERE `companyName` = ?;");
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				if (resultSet.getString("password").equals(password)) {
					condition = true;
					break;
				}
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}
		return condition;
	}
	
	@Override
	public void getCompanyDetails(Company Company) throws SQLException, NullResult {

		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`company` WHERE `companyName` = ?;");
			preparedStatement.setString(1, Company.getCompanyName());
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
			System.out.println("Company "+resultSet.getString("companyName")+":\r\n"+
					" Password: "+resultSet.getString("password")+".\r\n"+
					" Email: "+resultSet.getString("email")+".\r\n"+
					" Status: "+resultSet.getString("status")+".\n");
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}
	}
	
	public void addCoupon(Long couponID, Long companyID) throws SQLException, NullResult, CouponException {
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO `dbcoupon`.`companycoupons` (`CouponID`, `CompanyID`) VALUES (?, ?);");
			preparedStatement.setLong(1, couponID);
			preparedStatement.setLong(2, companyID);
			preparedStatement.executeUpdate();
			}
			catch (Exception ex) {
				System.err.println(ex.getMessage());
			}finally {
			connection.close();
			}
	}

	@Override
	public Collection<Coupon> getAllCompanyCoupons(Long id) throws SQLException, NullResult, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		Collection<Long> tmpID = new ArrayList<>();
		Long cID = null;
		ResultSet resultSet2 = null;
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT `CouponID` FROM `dbcoupon`.`companycoupons` WHERE `CompanyID` = ?;");
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

	@Override
	public boolean checkCompanyInventory(Long id) throws SQLException, NullResult {
		boolean condition = false;
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT `CouponID` FROM `dbcoupon`.`companycoupons` WHERE `CompanyID` = ?;");
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();	
			resultSet.next();
			
			if (resultSet.getLong("CouponID") != 0) {
				condition = true;
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}return condition;
	}
	
	public Long getCompanyIdByName(String name) throws SQLException, NullResult {
		Long id = null;
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT `ID` FROM `dbcoupon`.`company` WHERE `companyName` = ?");
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
	
	public String getCompanyNameById(Long id) throws SQLException, NullResult {
		String name = null;
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT `companyName` FROM `dbcoupon`.`company` WHERE `ID` = ?");
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();	
			
			if (resultSet.next()) {
				name = resultSet.getString("companyName");
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}return name;
	}
	
	public String checkCompanyStatus(Long id) throws SQLException, NullResult {
		String status = null;

		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`company` WHERE `ID` = ?;");
			preparedStatement.setLong(1,id);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			status = resultSet.getString("status");
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}
		return status;
	}
	
	public void activateCompany(Company company) throws SQLException, NullResult {
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("UPDATE `dbcoupon`.`company` SET `status` = 'ACTIVE' WHERE `companyName` = ?;");
			preparedStatement.setString(1, company.getCompanyName());
			preparedStatement.executeUpdate();
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}
	}
}