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
import com.emc.sqlfire.sop.domain.Transaction;
import com.vmware.sqlfire.procedure.OutgoingResultSet;
import com.vmware.sqlfire.procedure.ProcedureExecutionContext;

public class GetTransactionDetails {
	
	private static Logger logger = Logger.getLogger(GetAllOrdersForPeriod.class);
	
	public static void execute(String paymentId, ResultSet[] outResultSet, ProcedureExecutionContext context) {
		
		System.out.println("Inside GetTransactionDetails : execute() method.");
		
		String sql = "SELECT * FROM SOPV2.SOPTRANSACTION WHERE PAYMENTID = ?";

		System.out.println("SQL Query = "+sql);
		
		try {
			Connection conn = context.getConnection();
			System.out.println("conn = "+conn);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, paymentId);
			stmt.execute();
			ResultSet rs  = stmt.getResultSet();
			
			List <Transaction> transactions = new ArrayList<Transaction>();
			while (rs.next()) {
				Transaction trans = new Transaction();
				
				trans.setTransactionId(rs.getString("TRANSACTIONID"));
				trans.setTransAmount(rs.getDouble("TRANSAMOUNT"));
				trans.setTransDate(rs.getDate("TRANSDATE"));
				trans.setTransType(rs.getString("TRANSTYPE"));
				
				transactions.add(trans);
			}
			
			
		
			OutgoingResultSet outgoingRs = context.getOutgoingResultSet(1);
			//outgoingRs.addColumn("result");
			List<Object> results = new ArrayList<Object>();
			results.add(transactions);

			outgoingRs.addRow(results);
			outgoingRs.endResults();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new RuntimeException(sqle);
		} 
		
		System.out.println("Exiting GetTransactionDetails : execute() method.");

	}
	
	private static String getFormattedExpDateStr(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(d);
		return dateStr;
	}
	
}
