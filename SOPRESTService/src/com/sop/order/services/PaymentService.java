package com.sop.order.services;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sop.message.transform.Payment;
import com.sop.services.PaymentProcessingDAO;

@Service("paymentService") 

public class PaymentService {
	
	protected static Logger logger = Logger.getLogger(PaymentService.class);
	
	@Autowired
	private PaymentProcessingDAO ppd;
	
	public Payment getPaymentDetailFor(String paymentId) throws SQLException, ClassNotFoundException{
		
		Payment payment = ppd.getPaymentDetailFor(paymentId);
		logger.debug("Payment retrieved = "+payment);
		return payment;
	}
	
	public boolean updatePaymentStatus(String paymentId, String status){
		boolean updFlg = ppd.updatePaymentStatus(paymentId, status);
		logger.debug("Order updated: "+updFlg);
		return updFlg;
	}
	
}
