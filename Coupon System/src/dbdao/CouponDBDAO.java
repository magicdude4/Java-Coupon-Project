package dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import beans.Coupon;
import dao.CouponDAO;
import enums.CouponType;
import exceptions.CouponException;
import exceptions.NullResult;
import utility.Methods;

public class CouponDBDAO implements CouponDAO {
	
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	
	public CouponDBDAO() {
	
	}

	@Override
	public Long createCoupon(Coupon coupon) throws SQLException, NullResult, CouponException {
		Long id = null;
		LocalDate lStart = coupon.getStartDate();
		LocalDate lEnd = coupon.getEndDate();
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO `dbcoupon`.`coupon` (`title`, `startDate`, `endDate`, `amount`, `type`, `message`, `price`, `image`, `expirationStatus`, `purchaceStatus`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, coupon.getTitle());
			preparedStatement.setDate(2, java.sql.Date.valueOf(lStart));
			preparedStatement.setDate(3, java.sql.Date.valueOf(lEnd));
			preparedStatement.setInt(4, coupon.getAmount());
			preparedStatement.setString(5, coupon.getType().toString());
			preparedStatement.setString(6, coupon.getMessage());
			preparedStatement.setDouble(7, coupon.getPrice());
			preparedStatement.setString(8, coupon.getImage());
			preparedStatement.setString(9, "ACTIVE");
			preparedStatement.setString(10, "AVAILABLE");
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			resultSet.next();
			id = resultSet.getLong(1);
			System.out.println("Coupon Insert succeeded. New id: " + id);
		}catch (Exception ex) {
			System.err.println("Unable to create coupon, "+ex.getMessage());
		}finally {
			connection.close();
		}
		return id;
	}

	@Override
	public void removeCoupon(Coupon coupon) throws SQLException, NullResult, CouponException {

		try {
		connection = Methods.getConnection();
		preparedStatement = connection.prepareStatement("UPDATE `dbcoupon`.`coupon` SET `purchaceStatus` = 'UNAVAILABLE' WHERE `image` = ?;");
		preparedStatement.setString(1, coupon.getImage());
		preparedStatement.executeUpdate();
		}
		catch (Exception ex) {
			System.err.println("Couldn't remove coupon, "+ex.getMessage());
		}finally {
		connection.close();
		}
	}
	
	public void updateCouponTitle(Long id, String title) throws SQLException, NullResult, CouponException {

		try {
			connection = Methods.getConnection();
		   	preparedStatement = connection.prepareStatement("UPDATE `dbcoupon`.`coupon` SET `title` = ? WHERE `ID` = ?;");
		   	preparedStatement.setString(1, title);
		   	preparedStatement.setLong(2, id);
		    preparedStatement.executeUpdate();
		}
		catch (Exception ex) {
			System.err.println("Couldn't update title, "+ex.getMessage());
		}finally {
		connection.close();
		}
	}
	
	public void updateCouponStartDate(Long id, LocalDate startDate) throws SQLException, NullResult, CouponException {

		try {
			connection = Methods.getConnection();
			java.sql.Date date = java.sql.Date.valueOf(startDate);
		   	preparedStatement = connection.prepareStatement("UPDATE `dbcoupon`.`coupon` SET `startDate` = ? WHERE `ID` = ?;");
		   	preparedStatement.setDate(1, date);
		   	preparedStatement.setLong(2, id);
		    preparedStatement.executeUpdate();
		}
		catch (Exception ex) {
			System.err.println("Couldn't update start date, "+ex.getMessage());
		}finally {
		connection.close();
		}
	}
	
	public void updateCouponEndDate(Long id, LocalDate endDate) throws SQLException, NullResult, CouponException {

		try {
			connection = Methods.getConnection();
			java.sql.Date date = java.sql.Date.valueOf(endDate);
		   	preparedStatement = connection.prepareStatement("UPDATE `dbcoupon`.`coupon` SET `endDate` = ? WHERE `ID` = ?;");
		   	preparedStatement.setDate(1, date);
		   	preparedStatement.setLong(2, id);
		    preparedStatement.executeUpdate();
		}
		catch (Exception ex) {
			System.err.println("Couldn't update end date, "+ex.getMessage());
			}finally {
		connection.close();
		}
	}
	
	public void updateCouponAmount(Long id, int amount) throws SQLException, NullResult, CouponException {

		try {
			connection = Methods.getConnection();
		   	preparedStatement = connection.prepareStatement("UPDATE `dbcoupon`.`coupon` SET `amount` = ? WHERE `ID` = ?;");
		   	preparedStatement.setInt(1, amount);
		   	preparedStatement.setLong(2, id);
		    preparedStatement.executeUpdate();
		}catch (Exception ex) {
			System.err.println("Couldn't update amount, "+ex.getMessage());
		}finally {
		connection.close();
		}
	}
	
	public void updateCouponType(Long id, CouponType type) throws SQLException, NullResult, CouponException {

		try {
			connection = Methods.getConnection();
		   	preparedStatement = connection.prepareStatement("UPDATE `dbcoupon`.`coupon` SET `type` = ? WHERE `ID` = ?;");
		   	preparedStatement.setString(1, type.toString());
		   	preparedStatement.setLong(2, id);
		    preparedStatement.executeUpdate();
		}
		catch (Exception ex) {
			System.err.println("Couldn't update type, "+ex.getMessage());
			}finally {
		connection.close();
		}
	}
	
	public void updateCouponMessage(Long id, String message) throws SQLException, NullResult, CouponException {

		try {
			connection = Methods.getConnection();
		   	preparedStatement = connection.prepareStatement("UPDATE `dbcoupon`.`coupon` SET `message` = ? WHERE `ID` = ?;");
		   	preparedStatement.setString(1, message);
		   	preparedStatement.setLong(2, id);
		    preparedStatement.executeUpdate();
		}
		catch (Exception ex) {
			System.err.println("Couldn't update message, "+ex.getMessage());
			}finally {
		connection.close();
		}
	}
	
	public void updateCouponPrice(Long id, Double price) throws SQLException, NullResult, CouponException {

		try {
			connection = Methods.getConnection();
		   	preparedStatement = connection.prepareStatement("UPDATE `dbcoupon`.`coupon` SET `price` = ? WHERE `ID` = ?;");
		   	preparedStatement.setDouble(1, price);
		   	preparedStatement.setLong(2, id);
		    preparedStatement.executeUpdate();
		}
		catch (Exception ex) {
			System.err.println("Couldn't update price, "+ex.getMessage());
			}finally {
		connection.close();
		}
	}
	
	public void updateCouponImage(Long id, String image) throws SQLException, NullResult, CouponException {

		try {
			connection = Methods.getConnection();
		   	preparedStatement = connection.prepareStatement("UPDATE `dbcoupon`.`coupon` SET `image` = ? WHERE `ID` = ?;");
		   	preparedStatement.setString(1, image);
		   	preparedStatement.setLong(2, id);
		    preparedStatement.executeUpdate();
		}
		catch (Exception ex) {
			System.err.println("Couldn't update image, "+ex.getMessage());
			}finally {
		connection.close();
		}
	}

	@Override
	public Coupon getCoupon(Long id) throws SQLException, NullResult, CouponException {
		Coupon coupon = null;

		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`coupon` WHERE `ID` = ?;");
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			
			if (id.equals(resultSet.getLong("ID"))) {
				java.sql.Date startDate = (java.sql.Date) resultSet.getObject("startDate");
				java.sql.Date endDate = (java.sql.Date) resultSet.getObject("endDate");
				coupon = new Coupon(id, resultSet.getString("title"), startDate.toLocalDate(), endDate.toLocalDate(), resultSet.getInt("amount"), CouponType.valueOf(resultSet.getString("type")), resultSet.getString("message"), resultSet.getDouble("price"), resultSet.getString("image"), resultSet.getString("purchaceStatus"), resultSet.getString("expirationStatus"));
			}			
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}return coupon;
	}

	@Override
	public Collection<Coupon> getAllCoupons() throws SQLException, NullResult, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`coupon`;");
			resultSet = preparedStatement.executeQuery();
			resultSet.next();

			while (resultSet.next()) {
				java.sql.Date startDate = (java.sql.Date) resultSet.getObject("startDate");
				java.sql.Date endDate = (java.sql.Date) resultSet.getObject("endDate");
				Coupon coupon = new Coupon(resultSet.getLong("ID"), resultSet.getString("title"), startDate.toLocalDate(), endDate.toLocalDate(), resultSet.getInt("amount"), CouponType.valueOf(resultSet.getString("type")), resultSet.getString("message"), resultSet.getDouble("price"), resultSet.getString("image"), resultSet.getString("purchaceStatus"), resultSet.getString("expirationStatus"));
				tmpCoup.add(coupon);
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}return tmpCoup;
	}

	@Override
	public Collection<Coupon> getCouponsByTitle(String title) throws SQLException, NullResult, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`coupon` WHERE `title` = ?;");
			preparedStatement.setString(1, title);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				java.sql.Date startDate = (java.sql.Date) resultSet.getObject("startDate");
				java.sql.Date endDate = (java.sql.Date) resultSet.getObject("endDate");
				Coupon coupon = new Coupon(resultSet.getLong("ID"), title, startDate.toLocalDate(), endDate.toLocalDate(), resultSet.getInt("amount"), CouponType.valueOf(resultSet.getString("type")), resultSet.getString("message"), resultSet.getDouble("price"), resultSet.getString("image"), resultSet.getString("purchaceStatus"), resultSet.getString("expirationStatus"));
				tmpCoup.add(coupon);
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}return tmpCoup;
	}
	
	@Override
	public Collection<Coupon> getCouponsByStartDate(LocalDate startDate) throws SQLException, NullResult, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`coupon` WHERE `startDate` = ?;");
			preparedStatement.setObject(1, startDate);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				java.sql.Date endDate = (java.sql.Date) resultSet.getObject("endDate");
				Coupon coupon = new Coupon(resultSet.getLong("ID"), resultSet.getString("title"), startDate, endDate.toLocalDate(), resultSet.getInt("amount"), CouponType.valueOf(resultSet.getString("type")), resultSet.getString("message"), resultSet.getDouble("price"), resultSet.getString("image"), resultSet.getString("purchaceStatus"), resultSet.getString("expirationStatus"));
				tmpCoup.add(coupon);
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}return tmpCoup;
	}
	
	@Override
	public Collection<Coupon> getCouponsByEndDate(LocalDate endDate) throws SQLException, NullResult, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`coupon` WHERE `endDate` = ?;");
			preparedStatement.setObject(1, endDate);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				java.sql.Date startDate = (java.sql.Date) resultSet.getObject("endDate");
				Coupon coupon = new Coupon(resultSet.getLong("ID"), resultSet.getString("title"), startDate.toLocalDate(), endDate, resultSet.getInt("amount"), CouponType.valueOf(resultSet.getString("type")), resultSet.getString("message"), resultSet.getDouble("price"), resultSet.getString("image"), resultSet.getString("purchaceStatus"), resultSet.getString("expirationStatus"));
				tmpCoup.add(coupon);
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}return tmpCoup;
	}
	
	@Override
	public Collection<Coupon> getCouponsByAmount(int amount) throws SQLException, NullResult, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`coupon` WHERE `amount` = ?;");
			preparedStatement.setInt(1, amount);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				java.sql.Date startDate = (java.sql.Date) resultSet.getObject("startDate");
				java.sql.Date endDate = (java.sql.Date) resultSet.getObject("endDate");
				Coupon coupon = new Coupon(resultSet.getLong("ID"), resultSet.getString("title"), startDate.toLocalDate(), endDate.toLocalDate(), amount, CouponType.valueOf(resultSet.getString("type")), resultSet.getString("message"), resultSet.getDouble("price"), resultSet.getString("image"), resultSet.getString("purchaceStatus"), resultSet.getString("expirationStatus"));
				tmpCoup.add(coupon);
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}return tmpCoup;
	}
	
	@Override
	public Collection<Coupon> getCouponsByType(CouponType couponType) throws SQLException, NullResult, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`coupon` WHERE `type` = ?;");
			preparedStatement.setString(1, couponType.toString());
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				java.sql.Date startDate = (java.sql.Date) resultSet.getObject("startDate");
				java.sql.Date endDate = (java.sql.Date) resultSet.getObject("endDate");
				Coupon coupon = new Coupon(resultSet.getLong("ID"), resultSet.getString("title"), startDate.toLocalDate(), endDate.toLocalDate(), resultSet.getInt("amount"), CouponType.valueOf(resultSet.getString("type")), resultSet.getString("message"), resultSet.getDouble("price"), resultSet.getString("image"), resultSet.getString("purchaceStatus"), resultSet.getString("expirationStatus"));
				tmpCoup.add(coupon);
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}return tmpCoup;
	}
	
	@Override
	public Collection<Coupon> getCouponsByPrice(Double price) throws SQLException, NullResult, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`coupon` WHERE `price` = ?;");
			preparedStatement.setDouble(1, price);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				java.sql.Date startDate = (java.sql.Date) resultSet.getObject("startDate");
				java.sql.Date endDate = (java.sql.Date) resultSet.getObject("endDate");
				Coupon coupon = new Coupon(resultSet.getLong("ID"), resultSet.getString("title"), startDate.toLocalDate(), endDate.toLocalDate(), resultSet.getInt("amount"), CouponType.valueOf(resultSet.getString("type")), resultSet.getString("message"), price, resultSet.getString("image"), resultSet.getString("purchaceStatus"), resultSet.getString("expirationStatus"));
				tmpCoup.add(coupon);
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}return tmpCoup;
	}
	
	@Override
	public Collection<Coupon> getCouponsByAvailability(String status) throws SQLException, NullResult, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`coupon` WHERE `purchaceStatus` = ?;");
			preparedStatement.setObject(1, status);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				java.sql.Date startDate = (java.sql.Date) resultSet.getObject("startDate");
				java.sql.Date endDate = (java.sql.Date) resultSet.getObject("endDate");
				Coupon coupon = new Coupon(resultSet.getLong("ID"), resultSet.getString("title"), startDate.toLocalDate(), endDate.toLocalDate(), resultSet.getInt("amount"), CouponType.valueOf(resultSet.getString("type")), resultSet.getString("message"), resultSet.getDouble("price"), resultSet.getString("image"), status, resultSet.getString("expirationStatus"));
				tmpCoup.add(coupon);
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}return tmpCoup;
	}
	
	@Override
	public Collection<Coupon> getCouponsByActivity(String status) throws SQLException, NullResult, CouponException {
		Collection<Coupon> tmpCoup = new ArrayList<>();
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`coupon` WHERE `expirationStatus` = ?;");
			preparedStatement.setObject(1, status);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				java.sql.Date startDate = (java.sql.Date) resultSet.getObject("startDate");
				java.sql.Date endDate = (java.sql.Date) resultSet.getObject("endDate");
				Coupon coupon = new Coupon(resultSet.getLong("ID"), resultSet.getString("title"), startDate.toLocalDate(), endDate.toLocalDate(), resultSet.getInt("amount"), CouponType.valueOf(resultSet.getString("type")), resultSet.getString("message"), resultSet.getDouble("price"), resultSet.getString("image"), resultSet.getString("purchaceStatus"), status);
				tmpCoup.add(coupon);
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}return tmpCoup;
	}
	
	@Override
	public boolean checkIfCouponExists(String image) throws SQLException, NullResult, CouponException {
		boolean condition = false;
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`coupon`;");
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				if(resultSet.getString("image").equals(image)) {
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
	public boolean checkExpirationDate(Long id) throws SQLException, NullResult, CouponException {
		boolean condition = false;
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`coupon` WHERE `ID` =?;");
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			java.sql.Date endDate = resultSet.getDate("endDate");
			LocalDate date = endDate.toLocalDate();
			
			if (date.isBefore(LocalDate.now())) {
				condition = true;
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}
		return condition;
	}
	
	public String checkCouponAvailability(Long id) throws SQLException, NullResult, CouponException {
		String status = null;
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`coupon` WHERE `ID` = ?;");
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			status = resultSet.getString("purchaceStatus");
		}catch (Exception ex) {
			System.err.println("Problem with the DB/requested coupon doesn't exist, "+ex.getMessage());
		}finally {
			connection.close();
		}
		return status;
	}
	
	public Long getCouponDBIdByImage(String image) throws SQLException, NullResult, CouponException {
		Long id = null;
		
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT `ID` FROM `dbcoupon`.`coupon` WHERE `image` = ?");
			preparedStatement.setString(1, image);
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
	
	public boolean checkCouponAmount(Long id) throws SQLException, NullResult, CouponException {
		boolean condition = false;
		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT `amount` FROM `dbcoupon`.`coupon` WHERE `ID` = ?;");
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			
			if (resultSet.getInt("amount")>0) {
				condition = true;
			}
		}catch (Exception e) {
			System.err.println("Problem with the DB/requested coupon doesn't exist, "+e.getMessage());
		}finally {
			connection.close();
		}return condition;
	}
	
	@Override
	public void getCouponDetails(Coupon coupon) throws SQLException, NullResult, CouponException {

		try {
			connection = Methods.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM `dbcoupon`.`coupon` WHERE `image` = ?;");
			preparedStatement.setString(1, coupon.getImage());
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
			System.out.println("Coupon details:\r\n"+
					" ID: "+resultSet.getLong("ID")+".\r\n"+
					" Title: '"+resultSet.getString("title")+"'.\r\n"+
					" Start date: "+resultSet.getDate("startDate")+".\r\n"+
					" End date: "+resultSet.getDate("endDate")+".\r\n"+
					" Amount: "+resultSet.getInt("amount")+".\r\n"+
					" Type: "+CouponType.valueOf(resultSet.getString("type"))+".\r\n"+
					" Message: '"+resultSet.getString("message")+"'.\r\n"+
					" Price: "+resultSet.getDouble("price")+".\r\n"+
					" Image: '"+resultSet.getString("image")+"'.\r\n"+
					" Purchace status: "+resultSet.getString("purchaceStatus")+".\r\n"+
					" Expiration status: "+resultSet.getString("expirationStatus")+".\n");
			}
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
		}finally {
			connection.close();
		}
	}
}
