package com.emc.sqlfire.sop.domain;

import java.io.Serializable;
import java.util.Date;



public class DirectDebit implements Serializable { 
	
	private String debtId;
	private String accHolderName;
	private Integer accNumber;
	private String accType;
	private String bankName;
	private String branchName;
	private String ifscCode;
	private Double debtAmount;
	private Date debtDate;
	private Integer debtFrequency;
	private String debtStatus;
	
	public String getDebtId() {
		return debtId;
	}
	public void setDebtId(String debtId) {
		this.debtId = debtId;
	}
	public String getAccHolderName() {
		return accHolderName;
	}
	public void setAccHolderName(String accHolderName) {
		this.accHolderName = accHolderName;
	}
	public Integer getAccNumber() {
		return accNumber;
	}
	public void setAccNumber(Integer accNumber) {
		this.accNumber = accNumber;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public Double getDebtAmount() {
		return debtAmount;
	}
	public void setDebtAmount(Double debtAmount) {
		this.debtAmount = debtAmount;
	}
	public Date getDebtDate() {
		return debtDate;
	}
	public void setDebtDate(Date debtDate) {
		this.debtDate = debtDate;
	}
	public Integer getDebtFrequency() {
		return debtFrequency;
	}
	public void setDebtFrequency(Integer debtFrequency) {
		this.debtFrequency = debtFrequency;
	}
	public String getDebtStatus() {
		return debtStatus;
	}
	public void setDebtStatus(String debtStatus) {
		this.debtStatus = debtStatus;
	}

}
