package com.emc.sqlfire.sop.procedures;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.emc.sqlfire.sop.domain.Order;
import com.vmware.sqlfire.procedure.OutgoingResultSet;
import com.vmware.sqlfire.procedure.ProcedureExecutionContext;

public class GetAllOrdersForPeriod {
	
	private static Logger logger = Logger.getLogger(GetAllOrdersForPeriod.class);
	
	public static void execute(String orderId, String status, Date fromDate, Date toDate, ResultSet[] outResultSet, ProcedureExecutionContext context) {
		
		System.out.println("Inside GetAllOrdersForPeriod : execute() method.");
		
		String sql = null;
		StringBuilder sb = new StringBuilder();
		if(orderId != null && !orderId.isEmpty()) {
			sb.append("ORDERID = '")
			.append(orderId)
			.append("'");
		}
		if(status != null && !status.isEmpty()) {
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
			Connection conn = context.getConnection();
			System.out.println("conn = "+conn);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.execute();
			ResultSet rs  = stmt.getResultSet();
			//outResultSet[0] = rs;
			
			List <Order> orders = new ArrayList<Order>();
			while (rs.next()) {
				Order order = new Order();
				order.setOrderId(rs.getString("ORDERID"));
				order.setUserId(rs.getString("USERID"));
				order.setAddrId(rs.getInt("ADDRID"));
				order.setOrderDate(rs.getDate("ORDERDATE"));
				order.setTotalPrice(rs.getDouble("TOTALPRICE"));
				order.setOrderStatus(rs.getString("STATUS"));
				orders.add(order);
			}
			
			System.out.println("Orders size from Proc: "+orders.size());

			OutgoingResultSet outgoingRs = context.getOutgoingResultSet(1);
			//outgoingRs.addColumn("result");
			List<Object> results = new ArrayList<Object>();
			results.add(orders);

			outgoingRs.addRow(results);
			outgoingRs.endResults();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new RuntimeException(sqle);
		} 
		
		System.out.println("Exiting GetAllOrdersForPeriod : execute() method.");

	}
	
	private static String getFormattedExpDateStr(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(d);
		return dateStr;
	}
	
}
