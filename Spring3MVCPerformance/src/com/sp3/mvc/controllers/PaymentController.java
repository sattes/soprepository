package com.sp3.mvc.controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sop.dao.AddressDao;
import com.sop.dao.CardInfoDao;
import com.sop.dao.CustomerDao;
import com.sop.dao.OrderDao;
import com.sop.dao.PaymentDao;
import com.sop.dao.TransactionDao;
import com.sp3.mvc.enums.PaymentStatusEnum;
import com.sp3.mvc.helper.AMQPMessageHelper;
import com.sp3.mvc.helper.DateUtils;
import com.sp3.mvc.helper.JaxbHelper;
import com.sp3.mvc.helper.MessageHelper;
import com.sp3.mvc.models.Address;
import com.sp3.mvc.models.Customer;
import com.sp3.mvc.models.Order;
import com.sp3.mvc.models.Payment;

@Controller
public class PaymentController {
	
	private static Logger logger = Logger.getLogger(PaymentController.class);
	
	@Resource(name = "myProps")
	private Properties myProps;
	
	@Resource(name = "ordDao")
	private OrderDao ordDao;
	
	@Resource(name = "addrDao")
	private AddressDao addrDao;
	
	@Resource(name = "custDao")
	private CustomerDao custDao;
	
	@Resource(name = "payDao")
	private PaymentDao payDao;
	
	@Resource(name = "cardDao")
	private CardInfoDao cardDao;
	
	@Resource(name = "txnDao")
	private TransactionDao txnDao;
	
	private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }
	
	@RequestMapping(value="/gotopayment", method = RequestMethod.POST)
	public String goToPayment(@ModelAttribute("payment")Payment payment, HttpServletRequest request, @RequestParam String payNow) throws SQLException, ClassNotFoundException {
		logger.debug("PaymentController::goToPayment Start...");
		String orderId = (String)request.getSession().getAttribute("orderId");
		logger.debug("payNow = "+payNow);
		Order order = ordDao.getOrderByOrderId(orderId);
		//Order order = new Order();
		//order.setTotalPrice(1000.50d);
		payment.setOrderId(orderId);
		payment.setPaymentAmount(order.getTotalPrice());
		payment.setPaymentDate(new Date());
		
		if(payNow != null && payNow.equals("Pay Now")) {
			return payNow(payment);
		}
		
		if(payNow != null && payNow.equals("Make Payment")) {
			return makePayment(payment);
		}
		
		logger.debug("PaymentController::goToPayment End...");
		return "payment";
	}
	
	//@RequestMapping(value="/paynow", method = RequestMethod.POST)
	public String payNow(Payment payment) {
		logger.debug("PaymentController::payNow Start...");
		logger.debug("Payment Mode = "+payment.getPaymentMode());
		logger.debug("CCType = "+payment.getCcType());
		
		logger.debug("PaymentController::payNow End...");
		return "visa_ccpay";
	}
	
	public String makePayment(Payment payment) {
		logger.debug("PaymentController::makePayment Start...");
		
		logger.debug("Card Number = "+payment.getCardNumber());
		logger.debug("Name on Card = "+payment.getNameOnCard());
		logger.debug("Expiry Month = "+payment.getExpiryMonth());
		logger.debug("Expiry Year = "+payment.getExpiryYear());
		logger.debug("CVV Number = "+payment.getCvvNumber());
		com.sp3.mvc.jaxb.Payment jaxbPayment = null;
		try {
			jaxbPayment = getJaxbPayment(payment);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String textMessage = getMarshalledString(jaxbPayment);
		
		String exchangeName = myProps.getProperty("exchange.name");
		String qName = myProps.getProperty("payment.qname");
		String ipAddress = myProps.getProperty("ip.address");
		AMQPMessageHelper helper = new AMQPMessageHelper();
		helper.sendMessage(textMessage,exchangeName, qName,ipAddress);
		
		logger.debug("PaymentController::makePayment End...");
		return "visa_ccpay";
	}
	
	private String getMarshalledString(com.sp3.mvc.jaxb.Payment jaxbPayment) {
		String textMessage = null;
		
		try {
			final StringWriter out = new StringWriter();
			marshaller.marshal(jaxbPayment, new StreamResult(out));
			textMessage = out.toString();
		} catch (IOException e) {
			logger.error("IOException while marshalling Order object ", e);
		} 
		
		return textMessage;
	}
	
	private com.sp3.mvc.jaxb.Payment getJaxbPayment(Payment payment) throws SQLException, ClassNotFoundException, ParseException, DatatypeConfigurationException {
		String paymentId = payDao.getMaxPaymentId();
		
		if(paymentId == null || paymentId.equals("")) {
			paymentId = "PAYMENT-100";
		} else {
			String[] arr = paymentId.split("-");
			int maxId = Integer.parseInt(arr[1]);
			paymentId = "PAYMENT-"+(maxId+1);
		}
		
		String cardInfoId = cardDao.getMaxCardId();
		
		if(cardInfoId == null || cardInfoId.equals("")) {
			paymentId = "CARD-100";
		} else {
			String[] arr = cardInfoId.split("-");
			int maxId = Integer.parseInt(arr[1]);
			paymentId = "CARD-"+(maxId+1);
		}
		
		String txnId = txnDao.getMaxTxnId();
		
		if(txnId == null || txnId.equals("")) {
			txnId = "TXN-100";
		} else {
			String[] arr = txnId.split("-");
			int maxId = Integer.parseInt(arr[1]);
			txnId = "CARD-"+(maxId+1);
		}

		com.sp3.mvc.jaxb.Payment jaxbPayment = JaxbHelper.getJaxbPayment(payment);
		com.sp3.mvc.jaxb.Cardinfo jaxbCard = JaxbHelper.getJaxbCardinfo(payment);
		com.sp3.mvc.jaxb.Directdebit jaxbDebit = JaxbHelper.getJaxbDirectdebit(payment);
		com.sp3.mvc.jaxb.Transaction jaxbTxn = JaxbHelper.getJaxbTransaction(payment);
		jaxbCard.setCardinfoid(cardInfoId);
		jaxbPayment.setPaymentid(paymentId);
		jaxbPayment.setStatus(PaymentStatusEnum.PENDING.toString());
		jaxbPayment.setPaymentdate(DateUtils.getXMLGregorianCalendar(payment.getPaymentDate()));
		Order order = ordDao.getOrderByOrderId(payment.getOrderId());
		com.sp3.mvc.jaxb.Order jaxbOrder = JaxbHelper.getJaxbOrder(order);
		Address address = addrDao.getAddressByAddrId(order.getAddressId());
		com.sp3.mvc.jaxb.Address jaxbAddress = JaxbHelper.getJaxbAddress(address);
		Customer customer = custDao.getCustomerByUserId(address.getUserId());
		jaxbAddress.setCustomer(JaxbHelper.getJaxbCustomer(customer));
		jaxbOrder.setAddress(jaxbAddress);
		jaxbPayment.setOrder(jaxbOrder);
		jaxbTxn.setCardinfo(jaxbCard);
		jaxbTxn.setDirectdebit(jaxbDebit);
		jaxbTxn.setPaymentid(paymentId);
		jaxbTxn.setTransactionid(txnId);
		jaxbPayment.getTransaction().add(jaxbTxn);
		return jaxbPayment;
	}

}
