package com.sop.message.aggregation;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.sop.message.transform.OrderItem;


public class OrderItemTotalAggregator {

	double orderTotal=0;
	
	public Message<?> computeOrderTotal(List<Message<?>> orderItemTotalMsg) throws Exception{
		 orderTotal=0;
		Iterator<?> msgIt= orderItemTotalMsg.iterator();
		String orderId="";
		String orderStatus="";
		String addressId ="";
		String userid="";
		String orderDate ="";
		String itemId="";
		String prodId="";
		String quantity="";
		String unitPrice = "1";
		String itemStatus="";
		OrderItem[] orderItems = new OrderItem[orderItemTotalMsg.size()];
		int i=0;
		while(msgIt.hasNext()){
			String orderItemMsgPayloadStr="";
			Message<?> msg = (Message<?>)msgIt.next();
			String payloadType = msg.getPayload().getClass().getName();
			if(payloadType.startsWith("[B"))
				orderItemMsgPayloadStr = new String((byte[])(msg).getPayload());
			else
				orderItemMsgPayloadStr =msg.getPayload().toString();
		InputSource is = new InputSource(new StringReader(orderItemMsgPayloadStr));
		//System.out.println("Payload String in transformer: "+payLoadStr);
		
		Document doc =DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
		XPath xpath = XPathFactory.newInstance().newXPath();
		OrderItem orderIt = new OrderItem();
		String orderItemtotal = xpath.evaluate("/orderProcessResponse/orderProcessReturn/orderItemTotal", doc);
		orderId = xpath.evaluate("/orderProcessResponse/orderProcessReturn/orderItem/orderId", doc);
		itemId= xpath.evaluate("/orderProcessResponse/orderProcessReturn/orderItem/itemId", doc);
		prodId = xpath.evaluate("/orderProcessResponse/orderProcessReturn/orderItem/prodId", doc);
		quantity= xpath.evaluate("/orderProcessResponse/orderProcessReturn/orderItem/quantity",doc);
		unitPrice=xpath.evaluate("/orderProcessResponse/orderProcessReturn/orderItem/unitcost",doc);
		itemStatus = xpath.evaluate("/orderProcessResponse/orderProcessReturn/orderItem/status",doc);
		orderStatus = xpath.evaluate("/orderProcessResponse/orderProcessReturn/orderstatus", doc);
		addressId = xpath.evaluate("/orderProcessResponse/orderProcessReturn/orderItem/addid",doc);
		userid= xpath.evaluate("/orderProcessResponse/orderProcessReturn/orderItem/userid",doc);
		orderDate= xpath.evaluate("/orderProcessResponse/orderProcessReturn/orderItem/orderdate",doc);
		
		orderIt.setItemId(Integer.parseInt(itemId));
		orderIt.setOrderId(orderId);
		orderIt.setProdId(prodId);
		orderIt.setQuantity(Integer.parseInt(quantity));
		orderIt.setUnitPrice(Double.parseDouble(unitPrice));
		orderIt.setStatus(itemStatus);
		orderIt.setListPrice(Double.parseDouble(orderItemtotal));
		
		if(!orderItemtotal.equals(""))
		orderTotal= orderTotal + Double.parseDouble(orderItemtotal);
		
		orderItems[i]=orderIt;
		i++;
		}
		String AggpayloadStr = "<order orderid='"+orderId+"' orderTotal='"+orderTotal+"' status='"+orderStatus+"' addressId='"+addressId+"' userid='"+userid+"' orderdate='"+orderDate+"'></order>";
		Message<?> aggMsg = MessageBuilder.withPayload(AggpayloadStr).setHeaderIfAbsent("orderItem", orderItems).build();
		//System.out.println("Aggregated Message: "+aggMsg);
		
		return aggMsg;
	}
}
