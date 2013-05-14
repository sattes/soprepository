package com.sp3.mvc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DBUtils {
	
	private static Logger logger = Logger.getLogger(DBUtils.class);
	
	/*public static void loadDriver() throws ClassNotFoundException {
		try {
			Class.forName("com.vmware.sqlfire.jdbc.ClientDriver");
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException while  loading the driver."+e);
			throw e;
		}
	}*/
	
	public static Connection getConnection() throws SQLException,ClassNotFoundException {
		Connection conn = null;
		try {
			Class.forName("com.vmware.sqlfire.jdbc.ClientDriver");
			//conn = DriverManager.getConnection("jdbc:sqlfire://INGSSATTESL2C:1527/");
			//conn = DriverManager.getConnection("jdbc:sqlfire://10.30.134.58:1528/"); //karuna
			conn = DriverManager.getConnection("jdbc:sqlfire://10.30.135.56:1527/"); //kiran
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException while  loading the driver."+e);
			throw e;
		} catch (SQLException e) {
			logger.error("SQLException while  getting the connection."+e);
			throw e;
		}
		//conn.setAutoCommit(false);
		return conn;
	}
	
	public static void closeConnection(Connection conn) throws SQLException {
		if(conn != null) {
			try {
				if(!conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error("SQLException while  closing the connection."+e);
				throw e;
			}
		}
	}

}
