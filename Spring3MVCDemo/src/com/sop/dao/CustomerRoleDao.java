package com.sop.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.sp3.mvc.models.CustomerRole;

public class CustomerRoleDao  extends BaseDao {
	
	private static Logger logger = Logger.getLogger(CustomerRoleDao.class);
	
	
	
	public CustomerRole getRoleByRoleName(String roleName) throws SQLException, ClassNotFoundException {
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ")
		.append(myProps.getProperty("schemaName"))
		.append("CUSTOMERROLES WHERE ROLENAME = ?");
		
		
		String sql = sb.toString();
		logger.debug("SQL Query - "+sql);
		
		
		@SuppressWarnings("unchecked")
		CustomerRole customerRole  = (CustomerRole) jdbcTemp.queryForObject(sql,
			    new Object[]{roleName},
			    new RowMapper() {

			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			        	CustomerRole customerRole = new CustomerRole();
			        	customerRole.setRoleId(rs.getInt("ROLEID"));
						customerRole.setRoleName(rs.getString("ROLENAME"));
						customerRole.setRoleDesc(rs.getString("ROLEDESC"));
			            return customerRole;
			        }
		});
		
		
		
		return customerRole;
	}
	
	public CustomerRole getRoleByRoleId(Integer roleId) throws SQLException, ClassNotFoundException {
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ")
		.append(myProps.getProperty("schemaName"))
		.append("CUSTOMERROLES WHERE ROLEID = ?");

		
		String sql = sb.toString();
		logger.debug("SQL Query - "+sql);
		@SuppressWarnings("unchecked")
		CustomerRole customerRole  = (CustomerRole) jdbcTemp.queryForObject(sql,
			    new Object[]{roleId},
			    new RowMapper() {

			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			        	CustomerRole customerRole = new CustomerRole();
			        	customerRole.setRoleId(rs.getInt("ROLEID"));
						customerRole.setRoleName(rs.getString("ROLENAME"));
						customerRole.setRoleDesc(rs.getString("ROLEDESC"));
			            return customerRole;
			        }
		});
		
		return customerRole;
	}
	
	
}
