package dao;

import java.sql.SQLException;

import enums.ClientType;
import exceptions.CouponException;

public interface CouponClientFacade {
	
	public CouponClientFacade login(String name, String password, ClientType clientType) throws SQLException, CouponException, Exception;
}
