package com.sop.message.transform;

import java.sql.Timestamp;
import java.util.Date;

public class DirectDebit {

	private String debtid;
	private String accountHolderName;
	private int accountNumber;
	private String accountType;
	private String bankName;
	private String bankBranch;
	private String ifscCode;
	private double dbtAmount;
	private Timestamp dbtDateTime;
	private String dbtFrequency;
	private String dbtStatus;
	public String getDebtid() {
		return debtid;
	}
	public void setDebtid(String debtid) {
		this.debtid = debtid;
	}
	public String getAccountHolderName() {
		return accountHolderName;
	}
	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public double getDbtAmount() {
		return dbtAmount;
	}
	public void setDbtAmount(double dbtAmount) {
		this.dbtAmount = dbtAmount;
	}
	public Timestamp getDbtDateTime() {
		return dbtDateTime;
	}
	public void setDbtDateTime(Timestamp dbtDateTime) {
		this.dbtDateTime = dbtDateTime;
	}
	public String getDbtFrequency() {
		return dbtFrequency;
	}
	public void setDbtFrequency(String dbtFrequency) {
		this.dbtFrequency = dbtFrequency;
	}
	public String getDbtStatus() {
		return dbtStatus;
	}
	public void setDbtStatus(String dbtStatus) {
		this.dbtStatus = dbtStatus;
	}
	
	
}
