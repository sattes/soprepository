package com.sop.order.services;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sop.message.transform.Order;
import com.sop.services.OrderProcessingDAO;

@Service("orderService") 

public class OrderService {
	
	protected static Logger logger = Logger.getLogger(OrderService.class);
	
	@Autowired
	OrderProcessingDAO opd ;
	
	public Order getOrderDetailFor(String orderId) throws SQLException, ClassNotFoundException{
		
		Order order = opd.getOrderDetailFor(orderId);
		logger.debug("Order retrieved = "+order);
		return order;
	}
	
	public boolean updateOrderStatus(String orderId, String status){
		boolean updFlg = opd.updateOrderStatus(orderId, status);
		logger.debug("Order updated: "+updFlg);
		return updFlg;
	}
	
}
