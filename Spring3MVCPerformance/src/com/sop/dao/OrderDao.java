package com.sop.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.sp3.mvc.enums.OrderStatusEnum;
import com.sp3.mvc.models.Order;

public class OrderDao extends BaseDao  {
	
	
	private static Logger logger = Logger.getLogger(OrderDao.class);
	
	public void insertOrder(Order order) throws SQLException, ClassNotFoundException {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ")
		.append(myProps.getProperty("schemaName"))
		.append("ORDERS VALUES('")
		.append(order.getOrderId())
		.append("','")
		.append(order.getUserId())
		.append("',")
		.append(order.getAddressId())
		.append(",'")
		.append(order.getOrderDate())
		.append("',")
		.append(order.getTotalPrice())
		.append(",'")
		.append(order.getStatus())
		.append("');");
		
		String sql = sb.toString();
		logger.debug("SQL Query - "+sql);
		
		int rs = jdbcTemp.update(sql);
		logger.debug("Result = "+rs);
		
		
	}
	
	public Order getOrderByOrderId(String orderId) throws SQLException, ClassNotFoundException {
		
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ")
		.append(myProps.getProperty("schemaName"))
		.append("ORDERS WHERE ORDERID = ?");

		
		String sql = sb.toString();
		logger.debug("SQL Query - "+sql);

		@SuppressWarnings("unchecked")
		Order order = (Order) jdbcTemp.queryForObject(sql,
			    new Object[]{orderId},
			    new RowMapper() {

			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			        	Order order = new Order();
			        	order.setAddressId(rs.getInt("ADDRID"));
						order.setOrderDate(rs.getDate("ORDERDATE"));
						order.setOrderId(rs.getString("ORDERID"));
						order.setStatus(OrderStatusEnum.getEnumByValue(rs.getString("STATUS")));
						order.setTotalPrice(rs.getDouble("TOTALPRICE"));
						order.setUserId(rs.getString("USERID"));
			            return order;
			        }
		});
		
		return order;
	}

	public String getMaxOrderId() throws SQLException, ClassNotFoundException {
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT MAX(CAST(SUBSTR(ORDERID, 7) AS INT)) FROM ")
		.append(myProps.getProperty("schemaName"))
		.append("ORDERS");
		
		String sql = sb.toString();
		logger.debug("SQL Query - "+sql);
		

		Integer maxId = jdbcTemp.queryForInt(sql);
		
		if(maxId == 0) {
			return null;
		} else {
			maxId = maxId+1;
			return "ORDER-"+maxId;
		}

		
	}
	
	/*public static void main(String[] args) throws SQLException, ClassNotFoundException {
		OrderDao dao = new OrderDao();
		Order ord = dao.getOrderByOrderId("O100");
		System.out.println("Order = "+ord);
		System.out.println("Order Status = "+ord.getStatus());
	}*/

}
