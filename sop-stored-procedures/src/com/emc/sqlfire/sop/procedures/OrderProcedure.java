package com.emc.sqlfire.sop.procedures;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class OrderProcedure {
	
	private static Logger logger = Logger.getLogger(OrderProcedure.class);
	
	public static void execute(String orderId, String status, Date fromDate, Date toDate, ResultSet[] outResultSet) {
		
		System.out.println("Inside GetAllOrdersForPeriod : execute() method Start.");
		
		String sql = null;
		StringBuilder sb = new StringBuilder();
		if(orderId != null) {
			sb.append("ORDERID = '")
			.append(orderId)
			.append("'");
		}
		if(status != null) {
			sb.append(sb.length()==0?"":" AND ")
			.append("STATUS = '")
			.append(status)
			.append("'");
		}
		if(fromDate != null && toDate == null) {
			sb.append(sb.length()==0?"":" AND ")
			.append("ORDERDATE >= '")
			.append(getFormattedExpDateStr(fromDate))
			.append("'");
		}
		if(fromDate == null && toDate != null) {
			sb.append(sb.length()==0?"":" AND ")
			.append("ORDERDATE <= '")
			.append(getFormattedExpDateStr(toDate))
			.append("'");
		}
		if(fromDate != null && toDate != null) {
			sb.append(sb.length()==0?"":" AND ")
			.append("ORDERDATE >= '")
			.append(getFormattedExpDateStr(fromDate))
			.append("'")
			.append(sb.length()==0?"":" AND ")
			.append("ORDERDATE <= '")
			.append(getFormattedExpDateStr(toDate))
			.append("'");
		}
		if(sb.length() == 0) {
			sql = "SELECT * FROM SOPV2.ORDERS";
		} else {
			sql = "SELECT * FROM SOPV2.ORDERS WHERE "+sb.toString();
		}
		System.out.println("SQL Query = "+sql);
		try {
			Connection conn = DBUtils.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.executeQuery();
			ResultSet rs  = stmt.getResultSet();
			outResultSet[0] = rs;
			System.out.println("ResultSet = "+outResultSet[0]);
			
			while (rs. next()) {
				System.out.println("----------------------------------");
				System.out.println("ORDERID = "+rs.getString("ORDERID"));
				System.out.println("ORDERDATE = "+rs.getDate("ORDERDATE"));
				System.out.println("ADDRID = "+rs.getInt("ADDRID"));
				System.out.println("STATUS = "+rs.getString("STATUS"));
				System.out.println("TOTALPRICE = "+rs.getDouble("TOTALPRICE"));
				System.out.println("USERID = "+rs.getString("USERID"));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new RuntimeException(sqle);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		System.out.println("Exiting GetAllOrdersForPeriod : execute() method end.");

	}
	
	private static String getFormattedExpDateStr(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(d);
		return dateStr;
	}
	
	public static void main(String args[]) {
		ResultSet[] set = new ResultSet[1];
		Calendar fromcal = Calendar.getInstance();
		fromcal.set(2013, 03, 01);
		Calendar tocal = Calendar.getInstance();
		tocal.set(2013, 03, 18);
		execute(null,"ORDERED",fromcal.getTime(),tocal.getTime(),set);
	}

}
