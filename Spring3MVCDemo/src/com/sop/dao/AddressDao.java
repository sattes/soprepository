package com.sop.dao;



import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import com.sp3.mvc.enums.AddressTypeEnum;
import com.sp3.mvc.models.Address;

public class AddressDao extends BaseDao{
	

	private static Logger logger = Logger.getLogger(AddressDao.class);
	
	public void insertAddress(Address address) throws DataAccessException {
		
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ")
		.append(myProps.getProperty("schemaName"))
		.append("ADDRESS VALUES(")
		.append(address.getAddressId())
		.append(",'")
		.append(address.getUserId())
		.append("','")
		.append(address.getAddressType())
		.append("','")
		.append(address.getAddress1())
		.append("','")
		.append(address.getAddress2())
		.append("','")
		.append(address.getCity())
		.append("','")
		.append(address.getState())
		.append("','")
		.append(address.getZip())
		.append("','")
		.append(address.getCountry())
		.append("','")
		.append(address.getPhone())
		.append("');");
		
		String sql = sb.toString();
		logger.debug("SQL Query - "+sql);
		
		int rs = jdbcTemp.update(sql);
		logger.debug("Result = "+rs);
		
		
	}
	
	public Integer getMaxAddressId() throws DataAccessException {
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT MAX(ADDRID) FROM ")
		.append(myProps.getProperty("schemaName"))
		.append("ADDRESS");
		
		String sql = sb.toString();
		logger.debug("SQL Query - "+sql);
		

		int maxId = jdbcTemp.queryForInt(sql);
System.out.println("AddressId:- " + maxId);
		
		return maxId;
	}
	
	
	public Address getAddressByUserId(String userId) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ")
		.append(myProps.getProperty("schemaName"))
		.append("ADDRESS WHERE USERID = ?");

		String sql = sb.toString();
		logger.debug("SQL Query - "+sql);
		
		
		
		@SuppressWarnings("unchecked")
		Address address = (Address) jdbcTemp.queryForObject(sql,
			    new Object[]{userId},
			    new RowMapper() {

			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			        	Address address = new Address();
						address.setAddressId(rs.getInt("ADDRID"));
						address.setUserId(rs.getString("USERID"));
						address.setAddressType(AddressTypeEnum.getEnumByValue(rs.getString("ADDRTYPE")));
						address.setAddress1(rs.getString("ADDR1"));
						address.setAddress2(rs.getString("ADDR2"));
						address.setCity(rs.getString("CITY"));
						address.setState(rs.getString("STATE"));
						address.setZip(rs.getString("ZIP"));
						address.setCountry("COUNTRY");
						address.setPhone(rs.getString("PHONE"));
			            return address;
			        }
		});
		
		
	
		return address;
	}
	
	public Address getAddressByAddrId(Integer addrId) throws SQLException, ClassNotFoundException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ")
		.append(myProps.getProperty("schemaName"))
		.append("ADDRESS WHERE ADDRID = ?");
		
		String sql = sb.toString();
		logger.debug("SQL Query - "+sql);
		
		@SuppressWarnings("unchecked")
		Address address = (Address) jdbcTemp.queryForObject(sql,
			    new Object[]{addrId},
			    new RowMapper() {

			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			        	Address address = new Address();
						address.setAddressId(rs.getInt("ADDRID"));
						address.setUserId(rs.getString("USERID"));
						address.setAddressType(AddressTypeEnum.getEnumByValue(rs.getString("ADDRTYPE")));
						address.setAddress1(rs.getString("ADDR1"));
						address.setAddress2(rs.getString("ADDR2"));
						address.setCity(rs.getString("CITY"));
						address.setState(rs.getString("STATE"));
						address.setZip(rs.getString("ZIP"));
						address.setCountry("COUNTRY");
						address.setPhone(rs.getString("PHONE"));
			            return address;
			        }
		});
		
			return address;
	}

}
