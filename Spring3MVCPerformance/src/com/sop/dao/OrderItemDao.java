package com.sop.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sp3.mvc.models.OrderItem;
import com.sp3.mvc.models.Product;

public class OrderItemDao  extends BaseDao  {
	

	
private static Logger logger = Logger.getLogger(OrderItemDao.class);
	
	public void insertOrderItem(OrderItem orderItem) throws SQLException, ClassNotFoundException {
		orderItem.setStatus("OK");
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ")
		.append(myProps.getProperty("schemaName"))
		.append("ORDERITEMS VALUES(")
		.append(orderItem.getItemId())
		.append(",'")
		.append(orderItem.getOrderId())
		.append("','")
		.append(orderItem.getProductId())
		.append("',")
		.append(orderItem.getQuantity())
		.append(",")
		.append(orderItem.getListPrice())
		.append(",")
		.append(orderItem.getUnitPrice())
		.append(",'")
		.append(orderItem.getStatus())
		.append("');");
		
		String sql = sb.toString();
		logger.debug("SQL Query - "+sql);
		
		int rs = jdbcTemp.update(sql);
		logger.debug("Result = "+rs);
		
	}
	
	public Integer getMaxItemId() throws SQLException, ClassNotFoundException {
	
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT MAX(ITEMID) FROM ")
		.append(myProps.getProperty("schemaName"))
		.append("ORDERITEMS");
		
		String sql = sb.toString();
		logger.debug("SQL Query - "+sql);

		Integer maxId = jdbcTemp.queryForInt(sql);

		
		return maxId;

	}
	
	public List<OrderItem> getItemsByOrderId(String orderId) throws SQLException, ClassNotFoundException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ")
		.append(myProps.getProperty("schemaName"))
		.append("ORDERITEMS WHERE ORDERID = ?");
		
		
		String sql = sb.toString();
		logger.debug("SQL Query - "+sql);
		
		@SuppressWarnings("unchecked")  
		List<OrderItem> itemsList = (List<OrderItem>)jdbcTemp.queryForObject(sql,
			    new Object[]{orderId},
			    new RowMapper() {

			 public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				 List<OrderItem> itemsList = new ArrayList<OrderItem>();
		        	do {
		        		OrderItem orderItem = new OrderItem();
						orderItem.setItemId(rs.getInt("ITEMID"));
						orderItem.setOrderId(rs.getString("ORDERID"));
						orderItem.setProductId(rs.getString("PRODUCTID"));
						orderItem.setQuantity(rs.getInt("QUANTITY"));
						orderItem.setListPrice(rs.getDouble("LISTPRICE"));
						orderItem.setUnitPrice(rs.getDouble("UNITCOST"));
						orderItem.setStatus(rs.getString("STATUS"));
						itemsList.add(orderItem);
		        	
		        	}while(rs.next());
		        	 return itemsList;
		        }
	});
		
		
		
		return itemsList;
	}
	
	
}
