package com.emc.sqlfire.sop.batch;

public class Discounts {

	private int discId;

	private String custType;

	private String discountType;
	
	private Double discountPercent;
	
	public Discounts(Integer discId, String custType, String discountType, Double discountPercent) {
		super();
		this.discId = discId;
		this.custType = custType;
		this.discountType = discountType;
		this.discountPercent = discountPercent;
	}
	
	@Override
	public String toString() {
		return "Discounts [discId=" + discId + ", custType=" + custType + ", discountType="
				+ discountType + ", discountPercent=" + discountPercent + "]";
	}

	public int getDiscId() {
		return discId;
	}

	public void setDiscId(int discId) {
		this.discId = discId;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public Double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Double discountPercent) {
		this.discountPercent = discountPercent;
	}
	
	

}
