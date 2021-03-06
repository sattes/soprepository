package com.sp3.mvc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

public class TransactionDao {
	
	private static Logger logger = Logger.getLogger(TransactionDao.class);
	
	private Properties myProps;
	
	public TransactionDao() {
		
	}
	
	public TransactionDao(Properties myProps) {
		this.myProps = myProps;
	}
	
	public String getMaxTxnId() throws SQLException, ClassNotFoundException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT MAX(TRANSACTIONID) FROM ")
		.append(myProps.getProperty("schemaName"))
		.append("SOPTRANSACTION");
		
		String sql = sb.toString();
		logger.debug("SQL Query - "+sql);
		
		Connection con = null;
		String maxId = "";
		
		try {
			con  = DBUtils.getConnection();
			logger.debug("Got the connection...");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			logger.debug("Result = "+rs);
			while(rs.next()) {
				maxId = rs.getString(1);
			}
			
		} catch (SQLException e) {
			logger.error("SQLException occured while getting MAX(TRANSACTIONID)."+e);
			throw e;
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException while  loading the driver."+e);
			throw e;
		} finally {
			try {
				DBUtils.closeConnection(con);
			} catch (SQLException e) {
				logger.error("SQLException occured while closing connection."+e);
			}
		}
		
		return maxId;
	}

}
