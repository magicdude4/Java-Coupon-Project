package connectionhandling;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import exceptions.CouponException;
import utility.Methods;


/**
 * this class is a singleton that controls and manage all the connections. The singleton initiated using a static block in the class
 * it contains:
 * MAXConNumber int: the number of connections
 * conQ BlockingQueue: a clip of connections that manage the connections. when a connection is needed it takes the first connection and gives it. when it receives a connection it put it back
 * in 1st place to be used. if the list is empty, it waits for a connection to return than send it automatically.
 */
public class ConnectionPool {
	private static ConnectionPool instance;
	private final int MAXConNumber=10;
	private BlockingQueue<Connection> conQ= new LinkedBlockingDeque<>(MAXConNumber); // this parameter saves the connections like in a clip and manage the connections better than set.




	private ConnectionPool() throws SQLException, CouponException {
		try {
			Class.forName(DBData.getDriverData());
		}catch (ClassNotFoundException e){
			e.getMessage();
		}
		String url = DBData.getDBUrl();
		String userName = "root";
		String password = "12345";
		Connection con1 = DriverManager.getConnection(url, userName, password);
		DatabaseMetaData metas;
		ResultSet tables;
		Statement stat;
		metas = con1.getMetaData();
		stat = con1.createStatement();
		tables = metas.getTables(con1.getCatalog(), null, "COMPANY", null);
		if (!tables.next()) {
			//DBData.createTables(con1);
			Methods.buildCompanyDB();
			Methods.buildCouponDB();
			Methods.buildCustomerDB();
			Methods.buildCompanyCouponDB();
			Methods.buildCustomerCouponDB();
		}
		con1.close();
		while (conQ.size() < MAXConNumber) {
			con1 = DriverManager.getConnection(DBData.getDBUrl());
			conQ.offer(con1);
		}
	}

	/**
	 * call the instance of the connectionPool
	 * @return
	 * @throws CouponException 
	 */
	public static ConnectionPool getInstance() throws CouponException{
		if (instance==null){
			try {
				instance=new ConnectionPool();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}

	/**
	 * send a connection to the requester from conQ. this method is synchronised so only one can get to the connection pool at a time.
	 * before sending a connection it set the autocommit to true.
	 * @return a connection
	 * @throws CouponException
	 */
	public synchronized Connection getConnection() throws CouponException {
		try{
			Connection c=conQ.poll();
			c.setAutoCommit(true);
			return c;
		} catch (SQLException e) {
			throw new CouponException("failed to connect server");
		}
	}

	/**
	 * return a connection to conQ. this method is synchronised so only one can get to the connection pool at a time
	 * @param c connection
	 */
	public synchronized void returnConnection (Connection c){
		conQ.offer(c);
	}

	/**
	 * close all the connection available in conQ
	 * @throws CouponException
	 */
	public void closeAllConnections()throws CouponException {
		Connection c;
		while (conQ.peek()!=null){
			c=conQ.poll();
			try {
				c.close();
			} catch (SQLException e) {
				throw new CouponException("Unable to close all connections");
			}
		}
	}

	/**
	 * @return the number of the available connections
	 */
	public int avilabelConnections (){
		return this.conQ.size();
	}
}


