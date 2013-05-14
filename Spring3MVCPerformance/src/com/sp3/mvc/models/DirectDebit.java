package com.sp3.mvc.models;

import java.util.Date;

import com.sp3.mvc.enums.DebtStatusEnum;

public class DirectDebit { 
	
	private String debtId;
	private String accHolderName;
	private Integer accNumber;
	private String accType;
	private String bankName;
	private String branchName;
	private String ifscCode;
	private Double debtAmount;
	private Date debtDate;
	private String debtFrequency;
	private DebtStatusEnum debtStatus;
	
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
	public String getDebtFrequency() {
		return debtFrequency;
	}
	public void setDebtFrequency(String debtFrequency) {
		this.debtFrequency = debtFrequency;
	}
	public DebtStatusEnum getDebtStatus() {
		return debtStatus;
	}
	public void setDebtStatus(DebtStatusEnum debtStatus) {
		this.debtStatus = debtStatus;
	}

}
