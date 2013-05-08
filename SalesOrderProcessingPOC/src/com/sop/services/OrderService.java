package com.sop.services;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.sop.message.transform.Order;
import com.sop.message.transform.OrderItem;
import com.sop.message.transform.Payment;

public class OrderService {

	@Autowired
	JdbcTemplate jdbcTemp;
	
	public String getOrderItemInsQuery() {
		return orderItemInsQuery;
	}

	public void setOrderItemInsQuery(String orderItemInsQuery) {
		this.orderItemInsQuery = orderItemInsQuery;
	}

	String orderItemInsQuery;
	String orderStatusUpdateQuery;
	public String getOrderStatusUpdateQuery() {
		return orderStatusUpdateQuery;
	}

	public void setOrderStatusUpdateQuery(String orderStatusUpdateQuery) {
		this.orderStatusUpdateQuery = orderStatusUpdateQuery;
	}

	public Message<?> updateOrderBasedonPaymentStatus(Message<?> m){
		
		Order order = (Order)m.getPayload();
		final String orderId = order.getId();
		final String status = order.getStatus();
		jdbcTemp.execute(orderStatusUpdateQuery, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException{
				ps.setString(2, orderId);
				ps.setString(1, status);
				
				return ps.execute();
			}
		});
		m = MessageBuilder.withPayload(order.toString()).setHeader("PaymentStatus", "Success").build();
		return m;
	}
	
	public void insertOrderItems(Message<?> orderMsg){
		OrderItem[] orderItems= (OrderItem[])orderMsg.getHeaders().get("orderItem");
		
		for (OrderItem ordIt : orderItems){
			final int orderItemId = ordIt.getItemId();
			final String ordId = ordIt.getOrderId();
			final String prodId = ordIt.getProdId();
			final int quantity = ordIt.getQuantity();
			final String status = ordIt.getStatus();
			final double unitPrice = ordIt.getUnitPrice();
			final double listPrice = ordIt.getListPrice();
			
			jdbcTemp.execute(orderItemInsQuery, new PreparedStatementCallback<Boolean>() {
				@Override
				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException{
					ps.setInt(1, orderItemId);
					ps.setString(3, prodId);
					ps.setString(2, ordId);
					ps.setInt(4,quantity);
					ps.setString(7, status);
					ps.setDouble(6, unitPrice);
					ps.setDouble(5,listPrice);
					
					return ps.execute();
				}
			});
		}
		
	}
}
