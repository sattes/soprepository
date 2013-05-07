package com.emc.sqlfire.sop.proc.client;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.emc.sqlfire.sop.domain.Order;
import com.emc.sqlfire.sop.procedures.DBUtils;

public class OrderProcedureClient {

	public static void main(String[] args) {
		String sql = "{call SOPV2.GET_ALL_ORDERS_FOR_PERIOD(?,?,?,?)  ON TABLE SOPV2.ORDERS}";
		Connection connection = null;
		CallableStatement stmt = null;  
	    ResultSet rs = null; 
	    
	    Calendar fromcal = Calendar.getInstance();
		fromcal.set(2013, 03, 01);
		Calendar tocal = Calendar.getInstance();
		tocal.set(2013, 03, 18);
		
		try {
			
			connection = DBUtils.getConnection();

			System.out.println("connection success");

		    stmt = connection.prepareCall(sql);
		    
			stmt.setString(1, null);
			stmt.setString(2, "ORDERED");
			stmt.setDate(3, new java.sql.Date(fromcal.getTimeInMillis())); 
			stmt.setDate(4, new java.sql.Date(tocal.getTimeInMillis())); 
			stmt.execute();
			
			System.out.println("connection success: 2");
			rs = stmt.getResultSet();
			System.out.println("result set size" +rs);
			List<Order> results = new ArrayList<Order>();
			while (rs. next()) {
				String colName = rs.getMetaData().getColumnName(1);
				Object obj  = rs.getObject(colName);
				List<Order> orders = (ArrayList<Order>)obj;
				for(Order order : orders){
					System.out.println("Retrived object : " + order.getOrderId() + " \n");
				}
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	
}
