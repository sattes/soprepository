package com.sop.message.transform;

import java.util.Date;

public class Transaction {

	private String transactionId;
	private double transactionAmount;
	private Date transactionDate;
	private String transactionType;
	private CardInfo cardInf;
	private DirectDebit directDebit;
	public CardInfo getCardInf() {
		return cardInf;
	}
	public void setCardInf(CardInfo cardInf) {
		this.cardInf = cardInf;
	}
	public DirectDebit getDirectDebit() {
		return directDebit;
	}
	public void setDirectDebit(DirectDebit directDebit) {
		this.directDebit = directDebit;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
}
