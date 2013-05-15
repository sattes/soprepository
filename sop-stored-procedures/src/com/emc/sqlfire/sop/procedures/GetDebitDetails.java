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

import com.emc.sqlfire.sop.domain.DirectDebit;
import com.vmware.sqlfire.procedure.OutgoingResultSet;
import com.vmware.sqlfire.procedure.ProcedureExecutionContext;

public class GetDebitDetails {
	
	private static Logger logger = Logger.getLogger(GetAllOrdersForPeriod.class);
	
	public static void execute(String transactionId, ResultSet[] outResultSet, ProcedureExecutionContext context) {
		
		System.out.println("Inside GetDebitDetails : execute() method.");
		
		String sql = null;
		
		sql = "SELECT accholdername, accnum, acctype, bankname, bankbranch, debtamount, debtdate, debtfrequency, debtstatus FROM SOPV2.directdebit WHERE transactionid = ?";

		System.out.println("SQL Query = "+sql);
		
		try {
			Connection conn = context.getConnection();
			System.out.println("conn = "+conn);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, transactionId);
			stmt.execute();
			ResultSet rs  = stmt.getResultSet();
			
			List <DirectDebit> debits = new ArrayList<DirectDebit>();
			while (rs.next()) {
				DirectDebit directDebit = new DirectDebit();
				directDebit.setAccHolderName(rs.getString("accholdername"));
				directDebit.setAccNumber(rs.getInt("accnum"));
				directDebit.setAccType(rs.getString("acctype"));
				directDebit.setBankName(rs.getString("bankname"));
				directDebit.setBranchName(rs.getString("bankbranch"));
				directDebit.setDebtAmount(rs.getDouble("debtamount"));
				directDebit.setDebtDate(rs.getDate("debtdate"));
				directDebit.setDebtFrequency(rs.getInt("debtfrequency"));
				directDebit.setDebtStatus(rs.getString("debtstatus"));
				
				debits.add(directDebit);
			}
			
			
		
			OutgoingResultSet outgoingRs = context.getOutgoingResultSet(1);
			//outgoingRs.addColumn("result");
			List<Object> results = new ArrayList<Object>();
			results.add(debits);

			outgoingRs.addRow(results);
			outgoingRs.endResults();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new RuntimeException(sqle);
		} 
		
		System.out.println("Exiting GetDebitDetails : execute() method.");

	}
	
	private static String getFormattedExpDateStr(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(d);
		return dateStr;
	}
	
}
