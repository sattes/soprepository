package com.sop.message.transform;

import java.util.Date;

public class Payment {

	private String paymentId;
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	private Date paymentdate;
	private double totalAmount;
	private String status;
	private Order order;
	private Transaction transaction;
	public Date getPaymentdate() {
		return paymentdate;
	}
	public void setPaymentdate(Date paymentdate) {
		this.paymentdate = paymentdate;
	}
	public Transaction getTransaction() {
		return transaction;
	}
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public String toString(){
		return this.paymentId+":"+this.totalAmount+":"+this.getStatus()+":"+this.getPaymentdate()+":"+this.getTransaction();
	}
	
	
}
