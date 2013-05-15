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

import com.emc.sqlfire.sop.domain.CardInfo;
import com.vmware.sqlfire.procedure.OutgoingResultSet;
import com.vmware.sqlfire.procedure.ProcedureExecutionContext;

public class GetCardInfo {
	
	private static Logger logger = Logger.getLogger(GetAllOrdersForPeriod.class);
	
	public static void execute(String transactionId, ResultSet[] outResultSet, ProcedureExecutionContext context) {
		
		System.out.println("Inside GetCardInfo : execute() method.");
		
		String sql = null;
		StringBuilder sb = new StringBuilder();
		
		sql = "SELECT cardnum, nameoncard, expdate, cardtype, cardgatewaytype FROM SOPV2.cardinfo WHERE transactionid = ?";
			
		
		System.out.println("SQL Query = "+sql);
		
		try {
			Connection conn = context.getConnection();
			System.out.println("conn = "+conn);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, transactionId);
			stmt.execute();
			ResultSet rs  = stmt.getResultSet();
			
			List <CardInfo> cards = new ArrayList<CardInfo>();
			while (rs.next()) {
				CardInfo cardInfo = new CardInfo();
				cardInfo.setCardNumber(rs.getLong("cardnum"));
				cardInfo.setNameOnCard(rs.getString("nameoncard"));
				cardInfo.setExpiryDate(rs.getDate("expdate"));
				cardInfo.setCardType(rs.getString("cardtype"));
				cardInfo.setCardGatewayType(rs.getString("cardgatewaytype"));
				
				
				cards.add(cardInfo);
			}
			
			
		
			OutgoingResultSet outgoingRs = context.getOutgoingResultSet(1);
			//outgoingRs.addColumn("result");
			List<Object> results = new ArrayList<Object>();
			results.add(cards);

			outgoingRs.addRow(results);
			outgoingRs.endResults();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new RuntimeException(sqle);
		} 
		
		System.out.println("Exiting GetCardInfo : execute() method.");

	}
	
	private static String getFormattedExpDateStr(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(d);
		return dateStr;
	}
	
}
