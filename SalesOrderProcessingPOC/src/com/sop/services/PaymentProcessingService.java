package com.sop.services;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.sop.message.transform.CardInfo;
import com.sop.message.transform.DirectDebit;
import com.sop.message.transform.Payment;
import com.sop.message.transform.Transaction;

public class PaymentProcessingService {
	@Autowired
	JdbcTemplate jdbcTemp;
	String paymentInsQuery;
	String transQuery;
	String ddInsQuery;
	String cardInfInsQuery;
	public String getCardInfInsQuery() {
		return cardInfInsQuery;
	}
	public void setCardInfInsQuery(String cardInfInsQuery) {
		this.cardInfInsQuery = cardInfInsQuery;
	}
	public String getDdInsQuery() {
		return ddInsQuery;
	}
	public void setDdInsQuery(String ddInsQuery) {
		this.ddInsQuery = ddInsQuery;
	}
	public String getTransQuery() {
		return transQuery;
	}
	public void setTransQuery(String transQuery) {
		this.transQuery = transQuery;
	}
	public void processPayment(Message<?> m){
		Payment payment = (Payment)m.getPayload();
		final String paymentId = payment.getPaymentId();
		final String orderId = payment.getOrder().getId();
		final String status = payment.getStatus();
		final double totalAmountPaid = payment.getTotalAmount();
		final Date paymentDate = payment.getPaymentdate();
		
		Boolean queryExeStatus = jdbcTemp.execute(paymentInsQuery, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException{
				ps.setString(1,paymentId);
				ps.setString(2, orderId);
				ps.setDouble(3, totalAmountPaid);
				ps.setString(4,status);
				
				ps.setDate(5, new java.sql.Date(paymentDate.getTime()));
				
				
				return ps.execute();
			}
		});
		
		Transaction trans = payment.getTransaction();
		
		final String transId = trans.getTransactionId();
		final double transAmount = trans.getTransactionAmount();
		final String transType = trans.getTransactionType();
		final Date transDate = trans.getTransactionDate();
		Boolean flgTransQueryExe=null;
		
			flgTransQueryExe  = jdbcTemp.execute(transQuery, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException{
				ps.setString(1,transId);
				ps.setString(2, paymentId);
				ps.setDouble(3, transAmount);
				ps.setString(5,transType);
				
				ps.setDate(4, new java.sql.Date(transDate.getTime()));
				
				
				return ps.execute();
			}
		});
		
		
			if("ECS".equalsIgnoreCase(transType) || "NetBanking".equalsIgnoreCase(transType)){
				DirectDebit directDebt =  trans.getDirectDebit();
				final String accntHolderName = directDebt.getAccountHolderName();
				final int acctNum = directDebt.getAccountNumber();
				final String bankName  = directDebt.getBankName();
				final String bankBranch =directDebt.getBankBranch();
				final String ifscCode = directDebt.getIfscCode();
				final double debitAmt = directDebt.getDbtAmount();
				final String dbtFrequency = directDebt.getDbtFrequency();
				final String debitStatus = directDebt.getDbtStatus();
				final Timestamp debtDateTime = directDebt.getDbtDateTime();
				final String dbtId = directDebt.getDebtid();
				final String acctType = directDebt.getAccountType();
				
				
				flgTransQueryExe  = jdbcTemp.execute(ddInsQuery, new PreparedStatementCallback<Boolean>() {
					@Override
					public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException{
						ps.setString(1,dbtId);
						ps.setString(2, transId);
						ps.setString(3, accntHolderName);
						ps.setInt(4, acctNum);
						ps.setString(5, acctType);
						ps.setString(6,bankName);
						ps.setString(7, bankBranch);
						ps.setString(8, ifscCode);
						ps.setDouble(9, debitAmt);
						
						
						ps.setDate(10, new java.sql.Date(debtDateTime.getTime()));
						ps.setString(11, dbtFrequency);
						ps.setString(12, debitStatus);
						
						return ps.execute();
					}
				});
				
				
			}else if("CreditCard".equalsIgnoreCase(transType) || "DebitCard".equalsIgnoreCase(transType)){
				CardInfo cardInf =  trans.getCardInf();
				final String cardInfId = cardInf.getCardInfoId();
				final BigInteger cardNum = cardInf.getCardNumber();
				final int ccvNum = cardInf.getCardcvvNumber();
				final String nameOnCard = cardInf.getNameOnCard();
				final String cardType = cardInf.getCardType();
				final String cardGWType = cardInf.getCardGatewayType();
				final Date expDate = cardInf.getExpDate();
				final String transactionId = trans.getTransactionId();
				jdbcTemp.execute(cardInfInsQuery, new PreparedStatementCallback<Boolean>() {
					@Override
					public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException{
						ps.setString(1,cardInfId);
						ps.setString(2, transactionId);
						ps.setLong(3, cardNum.longValue());
						ps.setString(4,nameOnCard);
						
						ps.setDate(5, new java.sql.Date(expDate.getTime()));
						
						ps.setString(6, cardType);
						ps.setString(7, cardGWType);
						ps.setInt(8, ccvNum);
						return ps.execute();
					}
				});
			}else{
				System.out.println("Payment Type is "+transType);
			}
			
			
		
		
		//m = MessageBuilder.fromMessage(m).setHeader("PaymentProcess", "SUCCESS").build();
		
		//return m;
	}
	public String getPaymentInsQuery() {
		return paymentInsQuery;
	}
	public void setPaymentInsQuery(String paymentInsQuery) {
		this.paymentInsQuery = paymentInsQuery;
	}
	
}
