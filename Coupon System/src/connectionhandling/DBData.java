package connectionhandling;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

public class DBData {

	public static String getDriverData() {
		return "com.mysql.cj.jdbc.Driver";
	}

	public static String getDBUrl() {
		return "jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&user=root&password=12345";
	}

	public static void createTables(Connection con1) throws SQLException {
		Statement statement = (Statement) con1.createStatement();
		String sql;
		
		sql = "";
		((java.sql.Statement) statement).executeUpdate(sql);
		System.out.println("Success: "+sql);
	}
}
