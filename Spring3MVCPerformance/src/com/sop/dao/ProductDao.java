package com.sop.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sp3.mvc.models.Customer;
import com.sp3.mvc.models.Product;

public class ProductDao extends BaseDao {
	
		
	private static Logger logger = Logger.getLogger(ProductDao.class);
	
	public List<Product> getProductsByCatId(String catId) throws SQLException, ClassNotFoundException {
	
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ")
		.append(myProps.getProperty("schemaName"))
		.append("PRODUCT WHERE CATID = ?");
	

		String sql = sb.toString();
		logger.debug("SQL Query - "+sql);

		//List<Product> products = new ArrayList<Product>();
		@SuppressWarnings("unchecked")
		List<Product> products = (List<Product>)jdbcTemp.queryForObject(sql,
			    new Object[]{catId},
			    new RowMapper() {

			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			        	List<Product> products = new ArrayList<Product>();
			        	do {
			        	Product prod = new Product();
			        	prod.setCategory(rs.getString("CATID"));
						prod.setDescription(rs.getString("DESCN"));
						prod.setName(rs.getString("NAME"));
						prod.setProductId(rs.getString("PRODUCTID"));
						prod.setUnitCost(rs.getDouble("UNITCOST"));
			        	
			        	products.add(prod);
			        	
			        	}while(rs.next());
			        	 return products;
			        }
		});
			        	
			
		return products;
	}
	
	public Product getProductByProdId(String prodId) throws ClassNotFoundException, SQLException {
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ")
		.append(myProps.getProperty("schemaName"))
		.append("PRODUCT WHERE PRODUCTID = ?");

		
		String sql = sb.toString();
		logger.debug("SQL Query - "+sql);
		
		@SuppressWarnings("unchecked")
		Product product = (Product)jdbcTemp.queryForObject(sql,
			    new Object[]{prodId},
			    new RowMapper() {

			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			        	Product prod = new Product();
			        	prod.setCategory(rs.getString("CATID"));
						prod.setDescription(rs.getString("DESCN"));
						prod.setName(rs.getString("NAME"));
						prod.setProductId(rs.getString("PRODUCTID"));
						prod.setUnitCost(rs.getDouble("UNITCOST"));

			        	 return prod;
			        }
		});
		
	
		return product;
		
	}
	
	

}
