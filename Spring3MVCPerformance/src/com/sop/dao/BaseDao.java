package com.sop.dao;


import java.util.Properties;

import org.springframework.jdbc.core.JdbcTemplate;

public class BaseDao {
	
	
	JdbcTemplate jdbcTemp;
	Properties myProps;
	public JdbcTemplate getJdbcTemp() {
		return jdbcTemp;
	}
	public void setJdbcTemp(JdbcTemplate jdbcTemp) {
		this.jdbcTemp = jdbcTemp;
	}
	public Properties getMyProps() {
		return myProps;
	}
	public void setMyProps(Properties myProps) {
		this.myProps = myProps;
	}
	
	

}
