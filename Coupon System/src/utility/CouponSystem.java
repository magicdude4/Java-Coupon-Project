package utility;

import connectionhandling.ConnectionPool;
import dao.CouponClientFacade;
import enums.ClientType;
import exceptions.CouponException;
import facades.AdminFacade;
import facades.CompanyFacade;
import facades.CustomerFacade;

public class CouponSystem  {
	private static CouponSystem couponSystem = new CouponSystem();
	ConnectionPool connection;
	
	public static CouponSystem getInstance() throws CouponException {
		return couponSystem;
	}

	public CouponClientFacade login(String name, String password, ClientType clientType) throws Exception {
		CouponClientFacade client = null;
		
		switch (clientType) {
			case ADMIN:
				client = new AdminFacade();
				break;
			case COMPANY:
				client = new CompanyFacade();
				break;
			case CUSTOMER:
				client = new CustomerFacade();
				break;
		}return client.login(name, password, clientType);
	}

	public void shutdown() throws Exception {
		connection.closeAllConnections();
		DailyCouponExpirationTask.stopTask();
	}
}
