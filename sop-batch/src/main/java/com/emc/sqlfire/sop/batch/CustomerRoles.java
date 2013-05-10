package com.emc.sqlfire.sop.batch;

public class CustomerRoles {
	
	private int roleId;
	
	private String roleDesc;
	
	private String roleName;
	
	public CustomerRoles(int roleId, String roleDesc, String roleName) {
		super();
		this.roleId = roleId;
		this.roleDesc = roleDesc;
		this.roleName = roleName;
	}

	
	
	public int getRoleId() {
		return roleId;
	}



	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}



	public String getRoleDesc() {
		return roleDesc;
	}



	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}



	public String getRoleName() {
		return roleName;
	}



	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}



	@Override
	public String toString() {
		return "CustomerRoles [roleId=" + roleId + ", roleDesc=" + roleDesc + ", roleName="
				+ roleName + "]";
	}
	
	

}
