package com.sp3.mvc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DBUtils {
	
	private static Logger logger = Logger.getLogger(DBUtils.class);
	
	public static Connection getConnection() throws SQLException,ClassNotFoundException {
		Connection conn = null;
		try {
			Class.forName("com.vmware.sqlfire.jdbc.ClientDriver");
			//conn = DriverManager.getConnection("jdbc:sqlfire://INGSSATTESL2C:1528/"); //Mine  INGSSATTESL2C
			conn = DriverManager.getConnection("jdbc:sqlfire://10.5.128.128:1527/"); //Sreedhar
			
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException while  loading the driver."+e);
			throw e;
		} catch (SQLException e) {
			logger.error("SQLException while  getting the connection."+e);
			throw e;
		}
		
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
