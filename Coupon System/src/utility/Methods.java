package utility;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import connectionhandling.DBData;

public class Methods {
	static PreparedStatement preparedStatement;
	static Connection connection;
	
	
	public static Connection getConnection() throws Exception {

		try {
			String url = DBData.getDBUrl();
			String userName = "root";
			String password = "12345";
			Class.forName(DBData.getDriverData());
			
			connection = DriverManager.getConnection(url, userName, password);
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
	    return connection;
	}
	
	
	
	
	// -------------------------יצירת טבלה-----------------------------
	public static void buildCompanyDB() throws SQLException {
		
		try {
			connection = getConnection();
			String sql = "CREATE TABLE `dbcoupon`.`company` (\r\n" + 
					"  `ID` int(11) NOT NULL AUTO_INCREMENT,\r\n" + 
					"  `companyName` varchar(50) NOT NULL,\r\n" + 
					"  `password` varchar(50) NOT NULL,\r\n" + 
					"  `email` varchar(50) NOT NULL,\r\n" +
					"  `status` ENUM('ACTIVE','INACTIVE') NOT NULL,\r\n" +
					"  PRIMARY KEY (`ID`));";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeUpdate(sql);
			System.out.println("Company table has been created.");
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		} finally {
			connection.close();
		}
	}
	
	public static void buildCustomerDB() throws SQLException {
		
		try {
			connection = getConnection();
			String sql = "CREATE TABLE `dbcoupon`.`customer` (\r\n" + 
					"  `ID` INT NOT NULL AUTO_INCREMENT,\r\n" + 
					"  `username` VARCHAR(16) NOT NULL,\r\n" + 
					"  `email` VARCHAR(255) NOT NULL,\r\n" + 
					"  `password` VARCHAR(32) NOT NULL,\r\n" +
					"  `status` ENUM('ACTIVE','INACTIVE') NOT NULL,\r\n" +
					"  PRIMARY KEY (`ID`));";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeUpdate(sql);
			System.out.println("Customer table has been created.");
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		} finally {
			connection.close();
		}
	}
	
	public static void buildCouponDB() throws SQLException {
		
		try {
			connection = getConnection();
			String sql = "CREATE TABLE `dbcoupon`.`coupon` (\r\n" + 
					"  `ID` INT NOT NULL AUTO_INCREMENT,\r\n" + 
					"  `title` VARCHAR(45) NOT NULL,\r\n" + 
					"  `startDate` DATE NOT NULL,\r\n" + 
					"  `endDate` DATE NOT NULL,\r\n" + 
					"  `amount` INT NOT NULL,\r\n" + 
					"  `type` ENUM('STUDIES', 'RESTAURANTS', 'HOTELS', 'HEALTH', 'SPORTS', 'CAMPING', 'TRAVELING', 'BULKPURCHACE', 'ELECTRICITY') NOT NULL,\r\n" + 
					"  `message` VARCHAR(200) NOT NULL,\r\n" + 
					"  `price` DOUBLE NOT NULL,\r\n" + 
					"  `image` VARCHAR(200) NOT NULL,\r\n" +
					"  `purchaceStatus` ENUM('AVAILABLE','UNAVAILABLE') NOT NULL,\r\n" +
					"  `expirationStatus` ENUM('ACTIVE','INACTIVE') NOT NULL,\r\n" +
					"  PRIMARY KEY (`ID`));";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeUpdate(sql);
			System.out.println("Coupon table has been created.");
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		} finally {
			connection.close();
		}
	}
	
	public static void buildCustomerCouponDB() throws SQLException {
		
		try {
			connection = getConnection();
			String sql =  "CREATE TABLE `dbcoupon`.`customercoupons` (\r\n" + 
					"  `CouponID` INT NOT NULL,\r\n" + 
					"  `CustomerID` INT NOT NULL,\r\n" +  
					//"  PRIMARY KEY (`CustomerID`),\r\n" +
					"  FOREIGN KEY (`CouponID`)\r\n" +
				    "  REFERENCES `dbcoupon`.`coupon`(`ID`)\r\n" +
				    "  ON DELETE CASCADE ON UPDATE CASCADE);";	
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeUpdate(sql);
			System.out.println("Customer-coupons table has been created.");
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		} finally {
			connection.close();
		}
	}
	
	public static void buildCompanyCouponDB() throws SQLException {
		
		try {
			connection = getConnection();
			String sql = "CREATE TABLE `dbcoupon`.`companycoupons` (\r\n" + 
					"  `CouponID` INT NOT NULL,\r\n" + 
					"  `CompanyID` INT NOT NULL,\r\n" +  
					//"  PRIMARY KEY (`CompanyID`),\r\n" +
					"  FOREIGN KEY (`CouponID`)\r\n" +
				    "  REFERENCES `dbcoupon`.`coupon`(`ID`)\r\n" +
				    "  ON DELETE CASCADE ON UPDATE CASCADE);";	
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeUpdate(sql);
			System.out.println("Company-coupons table has been created.");
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		} finally {
			connection.close();
		}
	}
	
	public static void deleteDBs(String value) throws SQLException {

		try {
			connection = getConnection();
			String sql1 = "DROP TABLE `dbcoupon`.`company`;";
			String sql2 = "DROP TABLE `dbcoupon`.`coupon`;";
			String sql3 = "DROP TABLE `dbcoupon`.`customer`;";
			String sql4 = "DROP TABLE `dbcoupon`.`companycoupons`;";
			String sql5 = "DROP TABLE `dbcoupon`.`customercoupons`;";
			Statement statement = connection.createStatement();
			
			switch(value) {
			case "comp":
				statement.executeUpdate("DROP TABLE `dbcoupon`.`company`;");
				System.out.println("Companies table has been deleted.");
				break;
			case "coup":
				statement.executeUpdate("DROP TABLE `dbcoupon`.`coupon`;");
				System.out.println("Coupons table has been deleted.");
				break;
			case "cust":	
				statement.executeUpdate("DROP TABLE `dbcoupon`.`customer`;");
				System.out.println("Customers table has been deleted.");
				break;
			case "compc":
				statement.executeUpdate("DROP TABLE `dbcoupon`.`companycoupons`;");
				System.out.println("Company-coupons table has been deleted.");
				break;
			case "custc":
				statement.executeUpdate("DROP TABLE `dbcoupon`.`customercoupons`;");
				System.out.println("Customer-coupons table has been deleted.");
				break;
			case "all":
				statement.executeUpdate(sql5);
				statement.executeUpdate(sql4);
				statement.executeUpdate(sql3);
				statement.executeUpdate(sql2);
				statement.executeUpdate(sql1);
				System.out.println("All tables have been deleted.");
				break;
			}
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		} finally {
			connection.close();
		}
	}
	
	public static boolean emailValidation(String email) {
		String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
		Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = emailPat.matcher(email);
		return matcher.find();
	}
	
	public static void main(String[] args) throws Exception {
	
		//buildCompanyDB();
		//buildCouponDB();
		//buildCustomerDB();
		//buildCompanyCouponDB();
		//buildCustomerCouponDB();
		//deleteDBs("compc");
		//deleteDBs("custc");
		//deleteDBs("cust");
		//deleteDBs("coup");
		//deleteDBs("comp");
		deleteDBs("all");
	}
}	
