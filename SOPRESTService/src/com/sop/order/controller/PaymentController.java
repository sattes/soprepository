package com.sop.order.controller;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sop.message.transform.Payment;
import com.sop.order.services.PaymentService;

@Controller
@RequestMapping("/sopProvider")
public class PaymentController {

	protected static Logger logger = Logger.getLogger(PaymentController.class);
	
	
	
	@Resource(name="paymentService")
	private PaymentService paymentService;
	
	
	@RequestMapping(value="/payment/{paymentid}",method=RequestMethod.GET)
	public Payment getPaymentDetail(@PathVariable("paymentid") String paymentId, Model model) throws SQLException, ClassNotFoundException{
		logger.debug("PaymentController::getPaymentDetail");
		Payment payment = paymentService.getPaymentDetailFor(paymentId);
		logger.debug("Payment retrieved: "+payment);
		return payment;
	}
	
	
	
	@RequestMapping(value="/updatepayment/{paymentid}/{status}",method=RequestMethod.GET)
	public Payment updatePaymentStatus(@PathVariable("paymentid") String paymentId, @PathVariable("status") String status){
		logger.debug("PaymentController::updatePaymentStatus");
		boolean updateFlg = false;
		updateFlg = paymentService.updatePaymentStatus(paymentId, status);
		logger.debug("Order updated: "+updateFlg);
		Payment payment = new Payment();
		payment.setPaymentId(paymentId);
		payment.setPaymentStatus(status);
		if(updateFlg) {
			payment.setUpdated(true);
		}
		return payment;
	}
	
}
