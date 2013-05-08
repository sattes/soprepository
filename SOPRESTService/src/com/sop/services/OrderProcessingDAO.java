package com.sop.services;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sop.message.transform.Order;

public class OrderProcessingDAO {
	
	protected static Logger logger = Logger.getLogger(OrderProcessingDAO.class);

	JdbcTemplate jdbcTemp;
	
	public Order getOrderDetailFor(String orderid){
		@SuppressWarnings("unchecked")
		Order actor = (Order) jdbcTemp.queryForObject(
			    "select ORDERID, USERID, ADDRID,ORDERDATE,TOTALPRICE,STATUS from sopv2.orders where orderid = ?",
			    new Object[]{orderid},
			    new RowMapper() {

			        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			        	Order actor = new Order();
			            actor.setOrderId(rs.getString("ORDERID"));
			            actor.setAddressId(rs.getInt("ADDRID"));
			            actor.setOrderDate(rs.getDate("ORDERDATE"));
			            actor.setUserId(rs.getString("USERID"));
			            actor.setStatus(rs.getString("STATUS"));
			            actor.setTotalPrice(rs.getDouble("TOTALPRICE"));
			            return actor;
			        }
			    });
		logger.debug("Order retrieved = "+actor);
		return actor;

	}
	
	public boolean updateOrderStatus(String orderId, String status){
		boolean updFlg = false;
		
		int rowsUpdated = jdbcTemp.update("update sopv2.orders set status=? where orderid=?",status,orderId);
		if(rowsUpdated>0)
			updFlg=true;
		logger.debug("Order updated = "+updFlg);
		return updFlg;
	}
	public JdbcTemplate getJdbcTemp() {
		return jdbcTemp;
	}
	public void setJdbcTemp(JdbcTemplate jdbcTemp) {
		this.jdbcTemp = jdbcTemp;
	}
}
