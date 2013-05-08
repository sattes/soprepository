package com.sop.message.transform;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order implements Serializable{

	private String orderNumber;
	private int orderLineItemNumber;
	private String userId;
	private double orderTotal;
	private String orderDate;
	private boolean updated;
	
	
	public boolean isUpdated() {
		return updated;
	}
	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
	private int addrId = 123;
	
	public Order(){
		
	}
	public int getAddrId() {
		return addrId;
	}
	public void setAddrId(int addrId) {
		this.addrId = addrId;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public double getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public int getOrderLineItemNumber() {
		return orderLineItemNumber;
	}
	public void setOrderLineItemNumber(int orderLineItemNumber) {
		this.orderLineItemNumber = orderLineItemNumber;
	}
	private String id;
	private String status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String toString(){
		System.out.println(this.orderDate);
		return "order:id-"+this.id+"order:orderTotal-"+this.orderTotal+"order:OrderDate-"+this.orderDate+"order:status-"+this.status;
	}
}
