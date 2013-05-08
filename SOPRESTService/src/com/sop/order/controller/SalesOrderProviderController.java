package com.sop.order.controller;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sop.message.transform.Order;
import com.sop.message.transform.Payment;
import com.sop.order.services.OrderService;
import com.sop.order.services.PaymentService;

@Controller
@RequestMapping("/sopProvider")
public class SalesOrderProviderController {

	protected static Logger logger = Logger.getLogger(SalesOrderProviderController.class);
	
	@Resource(name="orderService")
	OrderService orderService;
	
	@RequestMapping(value="/order/{orderid}",method=RequestMethod.GET)
	public Order getOrderDetail(@PathVariable("orderid") String orderId, Model model) throws SQLException, ClassNotFoundException{
		logger.debug("SalesOrderProviderController::getOrderDetail");
		Order order = orderService.getOrderDetailFor(orderId);
		logger.debug("Order retrieved: "+order);
		return order;
	}
	
	@RequestMapping(value="/updateorder/{orderid}/{status}",method=RequestMethod.GET)
	public Order updateOrderStatus(@PathVariable("orderid") String orderId, @PathVariable("status") String status){
		logger.debug("SalesOrderProviderController::updateOrderStatus");
		boolean updateFlg = false;
		updateFlg = orderService.updateOrderStatus(orderId, status);
		logger.debug("Order updated: "+updateFlg);
		Order order = new Order();
		order.setOrderId(orderId);
		order.setStatus(status);
		if(updateFlg) {
			order.setUpdated(true);
		}
		return order;
	}
	
}
