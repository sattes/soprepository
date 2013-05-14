package com.sp3.mvc.helper;

import java.text.ParseException;
import java.util.Calendar;

import javax.xml.datatype.DatatypeConfigurationException;

import com.sp3.mvc.enums.PaymentStatusEnum;
import com.sp3.mvc.models.Address;
import com.sp3.mvc.models.Customer;
import com.sp3.mvc.models.Order;
import com.sp3.mvc.models.Payment;

public class JaxbHelper {
	
	public static com.sp3.mvc.jaxb.Address getJaxbAddress(Address address) {
		com.sp3.mvc.jaxb.Address jaxbAddress = new com.sp3.mvc.jaxb.Address();
		jaxbAddress.setAddr1(address.getAddress1());
		jaxbAddress.setAddr2(address.getAddress2());
		jaxbAddress.setAddtype(address.getAddressType().toString());
		jaxbAddress.setCity(address.getCity());
		jaxbAddress.setCountry(address.getCountry());
		jaxbAddress.setId(address.getAddressId());
		jaxbAddress.setPhone(address.getPhone());
		jaxbAddress.setState(address.getState());
		jaxbAddress.setZip(address.getZip());
		return jaxbAddress;
	}
	
	public static com.sp3.mvc.jaxb.Customer getJaxbCustomer(Customer customer) {
		com.sp3.mvc.jaxb.Customer jaxbCustomer = new com.sp3.mvc.jaxb.Customer();
		jaxbCustomer.setCusttype(customer.getCustType().toString());
		jaxbCustomer.setEmail(customer.getEmail());
		jaxbCustomer.setFirstname(customer.getFname());
		jaxbCustomer.setLastname(customer.getLname());
		jaxbCustomer.setPassword(customer.getPassword());
		jaxbCustomer.setStatus(customer.getStatus().toString());
		jaxbCustomer.setUserid(customer.getUserName());
		return jaxbCustomer;
	}
	
	public static com.sp3.mvc.jaxb.Order getJaxbOrder(Order order) {
		com.sp3.mvc.jaxb.Order jaxbOrder = new com.sp3.mvc.jaxb.Order();
		jaxbOrder.setId(order.getOrderId());
		jaxbOrder.setOrderdate(DateUtils.getFormattedDateStr(order.getOrderDate()));
		jaxbOrder.setStatus(order.getStatus().toString());
		jaxbOrder.setTotalprice(order.getTotalPrice());
		return jaxbOrder;
	}
	
	public static com.sp3.mvc.jaxb.Payment getJaxbPayment(Payment payment) throws ParseException, DatatypeConfigurationException {
		com.sp3.mvc.jaxb.Payment jaxbPayment = new com.sp3.mvc.jaxb.Payment();
		jaxbPayment.setStatus(PaymentStatusEnum.PENDING.toString());
		jaxbPayment.setTotalamount(payment.getPaymentAmount());
		jaxbPayment.setPaymentdate(DateUtils.getXMLGregorianCalendar(payment.getPaymentDate()));
		return jaxbPayment;
	}
	
	public static com.sp3.mvc.jaxb.Transaction getJaxbTransaction(Payment payment) throws ParseException, DatatypeConfigurationException {
		com.sp3.mvc.jaxb.Transaction jaxbTransaction = new com.sp3.mvc.jaxb.Transaction();
		jaxbTransaction.setTransamount(payment.getPaymentAmount());
		jaxbTransaction.setTransdate(DateUtils.getXMLGregorianCalendar(payment.getPaymentDate()));
		jaxbTransaction.setTranstype(payment.getPaymentMode());
		return jaxbTransaction;
	}
	
	public static com.sp3.mvc.jaxb.Cardinfo getJaxbCardinfo(Payment payment) throws ParseException, DatatypeConfigurationException {
		com.sp3.mvc.jaxb.Cardinfo jaxbCardinfo = new com.sp3.mvc.jaxb.Cardinfo();
		jaxbCardinfo.setCardcvvnum(payment.getCvvNumber());
		jaxbCardinfo.setCardgatewaytype(payment.getCcType());
		jaxbCardinfo.setCardtype(payment.getPaymentMode());
		jaxbCardinfo.setNameoncard(payment.getNameOnCard());
		jaxbCardinfo.setCardnum(DateUtils.convertLongToBigInt(payment.getCardNumber()));
		Calendar cal = Calendar.getInstance();
		cal.set(payment.getExpiryYear(), payment.getExpiryMonth(), 0);
		jaxbCardinfo.setExpdate(DateUtils.getXMLGregorianCalendar(cal.getTime()));
		return jaxbCardinfo;
	}
	
	public static com.sp3.mvc.jaxb.Directdebit getJaxbDirectdebit(Payment payment) throws ParseException, DatatypeConfigurationException {
		com.sp3.mvc.jaxb.Directdebit jaxbDirectdebit = new com.sp3.mvc.jaxb.Directdebit();
		
		return jaxbDirectdebit;
	}

}
