package com.emc.sqlfire.sop.batch;

public class Product {

	private String productId;

	private String catId;

	private Double unitCost;

	private String name;

	private String description;
	
	public Product(String productId, String catId, Double unitCost, String name, String description) {
		super();
		this.productId = productId;
		this.catId = catId;
		this.unitCost = unitCost;
		this.name = name;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", catId=" + catId + ", unitCost="
				+ unitCost + ", name=" + name + ", description=" + description + "]";
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	

}
