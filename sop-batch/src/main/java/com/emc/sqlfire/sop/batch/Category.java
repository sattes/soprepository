package com.emc.sqlfire.sop.batch;

public class Category {
	
	private String catId;
	
	private String name;
	
	private String description;
	
	public Category(String catId, String name, String description) {
		super();
		this.catId = catId;
		this.name = name;
		this.description = description;
	}

	

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
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
	
	@Override
	public String toString() {
		return "Category [catId=" + catId + ", name=" + name + ", description="
				+ description + "]";
	}
	
	

}
