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
import com.emc.sqlfire.sop.domain.Payment;
import com.emc.sqlfire.sop.domain.SerializeResultSet;
import com.vmware.sqlfire.procedure.OutgoingResultSet;
import com.vmware.sqlfire.procedure.ProcedureExecutionContext;

public class GetPaymentDetails {
	
	private static Logger logger = Logger.getLogger(GetAllOrdersForPeriod.class);
	
	public static void execute(String paymentId,String orderId, String status, Date fromDate, Date toDate, ResultSet[] outResultSet, ProcedureExecutionContext context) {
		
		System.out.println("Inside GetPaymentDetails : execute() method.");
		
		String sql = null;
		StringBuilder sb = new StringBuilder();
		if(paymentId != null && !paymentId.isEmpty()) {
			sb.append("P.paymentid = '")
			.append(paymentId)
			.append("'");
		}
		if(orderId != null && !orderId.isEmpty()) {
			sb.append(sb.length()==0?"":" AND ")
			.append("ORDERID = '")
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
			.append("paymentdate >= '")
			.append(getFormattedExpDateStr(fromDate))
			.append("'");
		}
		if(fromDate == null && toDate != null) {
			sb.append(sb.length()==0?"":" AND ")
			.append("paymentdate <= '")
			.append(getFormattedExpDateStr(toDate))
			.append("'");
		}
		if(fromDate != null && toDate != null) {
			sb.append(sb.length()==0?"":" AND ")
			.append("paymentdate >= '")
			.append(getFormattedExpDateStr(fromDate))
			.append("'")
			.append(sb.length()==0?"":" AND ")
			.append("paymentdate <= '")
			.append(getFormattedExpDateStr(toDate))
			.append("'");
		}
		if(sb.length() == 0) {
			sql = "SELECT * FROM SOPV2.PAYMENT";
		} else {
			sql = "SELECT * FROM SOPV2.PAYMENT WHERE "+sb.toString();
		}
		System.out.println("SQL Query = "+sql);
		
		try {
			Connection conn = context.getConnection();
			System.out.println("conn = "+conn);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.execute();
			ResultSet rs  = stmt.getResultSet();
			
			List <Payment> payments = new ArrayList<Payment>();
			while (rs.next()) {
				Payment payment = new Payment();
				payment.setOrderId(rs.getString("ORDERID"));
				payment.setPaymentId(rs.getString("PAYMENTID"));
				payment.setPaymentAmount(rs.getDouble("TOTALAMOUNT"));
				payment.setPaymentDate(rs.getDate("PAYMENTDATE"));
				payment.setPaymentStatus(rs.getString("STATUS"));
				
				
				payments.add(payment);
			}
			
			
		
			OutgoingResultSet outgoingRs = context.getOutgoingResultSet(1);
			//outgoingRs.addColumn("result");
			List<Object> results = new ArrayList<Object>();
			results.add(payments);

			outgoingRs.addRow(results);
			outgoingRs.endResults();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new RuntimeException(sqle);
		} 
		
		System.out.println("Exiting GetPaymentDetails : execute() method.");

	}
	
	private static String getFormattedExpDateStr(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(d);
		return dateStr;
	}
	
}
