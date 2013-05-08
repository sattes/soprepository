package com.sop.message.transform;

import java.io.StringReader;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.springframework.integration.Message;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.sop.message.transform.Order;

public class XMLToObjectTransformer {
	public Order transform(Message<?> msg) throws Exception {

		Order ord = new Order();

		String payLoadStr = msg.getPayload().toString();
		OrderItem[] orderIt = (OrderItem[]) msg.getHeaders().get("orderItem");
		InputSource is = new InputSource(new StringReader(payLoadStr));
		// System.out.println("Payload String in transformer: "+payLoadStr);
		Document doc = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().parse(is);
		XPath xpath = XPathFactory.newInstance().newXPath();
		String orderId = xpath.evaluate("/order/@orderid", doc);
		String orderTotal = xpath.evaluate("/order/@orderTotal", doc);
		//System.out.println("Order id: " + orderId);
		String status = xpath.evaluate("/order/@status", doc);
		String userid = xpath.evaluate("/order/@userid", doc);
		String addid = xpath.evaluate("/order/@addressId", doc);
		String orderDate = xpath.evaluate("/order/@orderdate", doc);

		ord.setId(orderId);
		ord.setUserId(userid);
		ord.setOrderDate(orderDate);
		ord.setAddrId(Integer.parseInt(addid));
		ord.setOrderTotal(Double.parseDouble(orderTotal));
		ord.setStatus(status);
		//System.out.println(ord);
		return ord;
	}

	public Order transformOrder(Message<?> msg) {
		Order ord = new Order();
		try {
			String payloadType = msg.getPayload().getClass().getName();
			String payLoadStr = "";
			if(payloadType.startsWith("[B"))
				payLoadStr = new String((byte[])msg.getPayload());
			else
				payLoadStr = msg.getPayload().toString();
			OrderItem[] orderIt = (OrderItem[]) msg.getHeaders().get(
					"orderItem");
			InputSource is = new InputSource(new StringReader(payLoadStr));
			// System.out.println("Payload String in transformer: "+payLoadStr);
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(is);
			XPath xpath = XPathFactory.newInstance().newXPath();
			String orderId = xpath.evaluate("/payment/order/@id", doc);
			String status = xpath.evaluate("/payment/order/status", doc);
			String orderTotal=xpath.evaluate("/payment/order/totalprice",doc);
			ord.setId(orderId);
			ord.setOrderTotal(Double.parseDouble(orderTotal));
			ord.setStatus(status);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(ord);
		return ord;
	}

	public Customer transformCustomer(Message<?> msg) throws Exception {
		Customer cust = new Customer();
		String payloadType = msg.getPayload().getClass().getName();
		String payLoadStr = "";
		if(payloadType.startsWith("[B"))
			payLoadStr = new String((byte[])msg.getPayload());
		else
			payLoadStr = msg.getPayload().toString();
		InputSource is = new InputSource(new StringReader(payLoadStr));
		// System.out.println("Payload String in transformer: "+payLoadStr);
		Document doc = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().parse(is);
		XPath xpath = XPathFactory.newInstance().newXPath();
		String userId = xpath.evaluate("//customer/userid", doc);
		String status = xpath.evaluate("//customer/status", doc);
		String password = xpath.evaluate("//customer/password", doc);
		String lastname = xpath.evaluate("//customer/lastname", doc);
		String firstname = xpath.evaluate("//customer/firstname", doc);
		String email = xpath.evaluate("//customer/email", doc);
		String custtype = xpath.evaluate("//customer/custtype", doc);
		String enabled = xpath.evaluate("//customer/enabled",doc);
		String role = xpath.evaluate("//customer/customerRole/roleId",doc);
		cust.setEnabled(Integer.parseInt(enabled));
		cust.setUserRole(role);
		cust.setCusttype(custtype);
		cust.setEmail(email);
		cust.setFirstname(firstname);
		cust.setLastname(lastname);
		cust.setPassword(password);
		cust.setStatus(status);
		cust.setUserid(userId);
		//System.out.println("Customer User id: " + userId);
		return cust;
	}

	public Payment transformPayment(Message<?> msg) {

		Payment pay = new Payment();
		try {
			String payloadType = msg.getPayload().getClass().getName();
			String payLoadStr = "";
			if(payloadType.startsWith("[B"))
				payLoadStr = new String((byte[])msg.getPayload());
			else
				payLoadStr = msg.getPayload().toString();
			InputSource is = new InputSource(new StringReader(payLoadStr));
			// System.out.println("Payload String in transformer: "+payLoadStr);
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(is);
			XPath xpath = XPathFactory.newInstance().newXPath();
			String paymentId = xpath.evaluate("/payment/@paymentid", doc);
			String paymentdate = xpath.evaluate("/payment/paymentdate", doc);
			String totalAmount = xpath.evaluate("/payment/totalamount", doc);
			String status = xpath.evaluate("/payment/status", doc);
			String transactionId = xpath.evaluate(
					"/payment/transaction/transactionid", doc);
			String transamount = xpath.evaluate(
					"/payment/transaction/transamount", doc);
			String transdate = xpath.evaluate("/payment/transaction/transdate",
					doc);
			String transtype = xpath.evaluate("/payment/transaction/transtype",
					doc);
			pay.setPaymentId(paymentId);
			pay.setTotalAmount(Double.parseDouble(totalAmount));
			pay.setPaymentdate(new SimpleDateFormat("yyyy-MM-dd")
					.parse(paymentdate));
			pay.setStatus(status);
			Transaction transact = new Transaction();
			transact.setTransactionAmount(Double.parseDouble(transamount));
			transact.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd")
					.parse(transdate));
			transact.setTransactionId(transactionId);
			transact.setTransactionType(transtype);
			pay.setTransaction(transact);
			if ("NetBanking".equalsIgnoreCase(transtype)
					|| "ECS".equalsIgnoreCase(transtype)) {
				DirectDebit dd = new DirectDebit();
				String accountHolderName = xpath.evaluate(
						"/payment/transaction/directdebit/accholdername", doc);
				dd.setAccountHolderName(accountHolderName);
				String ddId = xpath.evaluate(
						"/payment/transaction/directdebit/debtid", doc);
				dd.setDebtid(ddId);
				String accnum = xpath.evaluate(
						"/payment/transaction/directdebit/accnum", doc);
				dd.setAccountNumber(Integer.parseInt(accnum));
				String bankName = xpath.evaluate(
						"/payment/transaction/directdebit/bankname", doc);
				dd.setBankName(bankName);
				String bankbranch = xpath.evaluate(
						"/payment/transaction/directdebit/bankbranch", doc);
				dd.setBankBranch(bankbranch);
				String ifsccode = xpath.evaluate(
						"/payment/transaction/directdebit/ifsccode", doc);
				dd.setIfscCode(ifsccode);
				String debitAmount = xpath.evaluate(
						"/payment/transaction/directdebit/debtamount", doc);
				dd.setDbtAmount(Double.parseDouble(debitAmount));
				String debitDate = xpath.evaluate(
						"/payment/transaction/directdebit/debtdate", doc);
				dd.setDbtDateTime(new Timestamp((new Date()).getTime()));
				String debtfrequency = xpath.evaluate(
						"/payment/transaction/directdebit/debtfrequency", doc);
				dd.setDbtFrequency(debtfrequency);
				String debtstatus = xpath.evaluate(
						"/payment/transaction/directdebit/debtstatus", doc);
				String accountType = xpath.evaluate(
						"/payment/transaction/directdebit/acctype", doc);
				dd.setDbtStatus(debtstatus);
				dd.setAccountType(accountType);
				transact.setDirectDebit(dd);
				
				
			}else if("CreditCard".equalsIgnoreCase(transtype) || "DebitCard".equalsIgnoreCase(transtype)){
				CardInfo cardInf = new CardInfo();
				 String cardInfId = xpath.evaluate("/payment/transaction/cardinfo/cardinfoid",doc);
				 String cardNumber = xpath.evaluate("/payment/transaction/cardinfo/cardnum",doc);
				 String nameOnCard = xpath.evaluate("/payment/transaction/cardinfo/nameoncard",doc);
				 String expdate = xpath.evaluate("/payment/transaction/cardinfo/expdate",doc);
				 String cardType = xpath.evaluate("/payment/transaction/cardinfo/cardtype",doc);
				 String cardGatewayType = xpath.evaluate("/payment/transaction/cardinfo/cardgatewaytype",doc);
				 String cardCVVNum = xpath.evaluate("/payment/transaction/cardinfo/cardcvvnum",doc);
				 cardInf.setCardInfoId(cardInfId);
				 cardInf.setCardNumber(new BigInteger(cardNumber));
				 cardInf.setExpDate(new SimpleDateFormat("yyyy-mm").parse(expdate));
				 cardInf.setCardcvvNumber(Integer.parseInt(cardCVVNum));
				 cardInf.setNameOnCard(nameOnCard);
				 cardInf.setCardType(cardType);
				 cardInf.setCardGatewayType(cardGatewayType);
				 transact.setCardInf(cardInf);
		
			}else{
				System.out.println("Payment Type is "+transtype);
			}
			Order ord = new Order();
			String orderId = xpath.evaluate("/payment/order/@id", doc);
			String Ordstatus = xpath.evaluate("/payment/order/status", doc);
			String orderTotal=xpath.evaluate("/payment/order/totalprice",doc);
			ord.setId(orderId);
			ord.setOrderTotal(Double.parseDouble(orderTotal));
			ord.setStatus(Ordstatus);
			pay.setOrder(ord);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("After Transaformation of Payment-" + pay);

		return pay;
	}
	
	public Address transformAddress(Message<?> msg){
		Address add = new Address();
		try{
			
			String payloadType = msg.getPayload().getClass().getName();
			String payLoadStr = "";
			if(payloadType.startsWith("[B"))
				payLoadStr = new String((byte[])msg.getPayload());
			else
				payLoadStr = msg.getPayload().toString();
		InputSource is = new InputSource(new StringReader(payLoadStr));
		// System.out.println("Payload String in transformer: "+payLoadStr);
		Document doc = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().parse(is);
		XPath xpath = XPathFactory.newInstance().newXPath();
		String addId = xpath.evaluate("//address/@id",doc);
		String addr1 = xpath.evaluate("//address/addr1", doc);
		String addr2= xpath.evaluate("//address/addr2", doc);
		String addtype = xpath.evaluate("//address/addtype", doc);
		String city = xpath.evaluate("//address/city", doc);
		String state = xpath.evaluate("//address/state", doc);
		String country = xpath.evaluate("//address/country", doc);
		String zip = xpath.evaluate("//address/zip", doc);
		String phone = xpath.evaluate("//address/phone", doc);
		String userid = xpath.evaluate("//customer/userid", doc);
		add.setAddr1(addr1);
		add.setAddr2(addr2);
		add.setAddressId(addId);
		add.setAddressType(addtype);
		add.setCity(city);
		add.setState(state);
		add.setZipCode(zip);
		add.setCountry(country);
		add.setPhoneNumber(phone);
		add.setAddUserId(userid);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return add;
	}
}
