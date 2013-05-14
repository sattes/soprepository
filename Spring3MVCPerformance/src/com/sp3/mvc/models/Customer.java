package com.sp3.mvc.models;

import java.io.Serializable;

import com.sp3.mvc.enums.CustomerStatusEnum;
import com.sp3.mvc.enums.CustomerTypeEnum;

public class Customer implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String userName;
    private String password;
    private String fname;
    private String lname;
    private String gender;
    private String country;
    private String address;
    private String cb;
    private String typeOfAddress;
    private String email;
    private CustomerTypeEnum custType;
    private CustomerStatusEnum status;
    
    private Address customerAddress;
    //private Address shippingAddress;
    
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCb() {
		return cb;
	}
	public void setCb(String cb) {
		this.cb = cb;
	}
	public String getTypeOfAddress() {
		return typeOfAddress;
	}
	public void setTypeOfAddress(String typeOfAddress) {
		this.typeOfAddress = typeOfAddress;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public CustomerTypeEnum getCustType() {
		return custType;
	}
	public void setCustType(CustomerTypeEnum custType) {
		this.custType = custType;
	}
	public CustomerStatusEnum getStatus() {
		return status;
	}
	public void setStatus(CustomerStatusEnum status) {
		this.status = status;
	}
	public Address getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(Address customerAddress) {
		this.customerAddress = customerAddress;
	}
	/*public Address getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}*/
}
