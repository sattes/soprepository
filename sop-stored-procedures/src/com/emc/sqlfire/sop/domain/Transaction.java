package com.emc.sqlfire.sop.domain;

import java.io.Serializable;
import java.util.Date;



public class Transaction implements Serializable {
	
	
	
	private String transactionId;
	private Double transAmount;
	private String transType;
	private Date transDate;
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Double getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(Double transAmount) {
		this.transAmount = transAmount;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	

	
	
	

}
