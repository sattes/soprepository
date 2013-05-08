package com.sop.interceptor;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.channel.ChannelInterceptor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class WSRequestCreationInterceptor implements ChannelInterceptor{

	@Override
	public Message<?> postReceive(Message<?> arg0, MessageChannel arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void postSend(Message<?> arg0, MessageChannel arg1, boolean arg2) {
		//String payloadXML = arg0.getPayload().toString();
		//System.out.println("Payload in WSRequestCreation Interceptor post send: "+payloadXML);
	}

	@Override
	public boolean preReceive(MessageChannel arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Message<?> preSend(Message<?> arg0, MessageChannel arg1) {
		// TODO Auto-generated method stub
	
		String payLoadXML="";
		String payloadType = arg0.getPayload().getClass().getName();
		if(payloadType.startsWith("[B"))
			payLoadXML = new String((byte[])arg0.getPayload());
		else
			payLoadXML = arg0.getPayload().toString();
		String wsPayload="";
		//System.out.println("Payload in WSRequestCreation Interceptor: "+payloadXML);
		
		Message<?> outMsg=null;
		try{
		//System.out.println("payload substring: "+payloadXML.substring(payloadXML.indexOf("?>")+2));
		wsPayload = "<?xml version='1.0' encoding='UTF-8'?>"+"<orderProcess><OrderItemProcessRequest><orderstatus>"+arg0.getHeaders().get("orderstatus")+"</orderstatus>"+payLoadXML.substring(payLoadXML.indexOf("?>")+2)+"</OrderItemProcessRequest></orderProcess>";
		}catch(Exception e){
			e.printStackTrace();
		}
		Message<?> wsMsg = MessageBuilder.withPayload(wsPayload).copyHeaders(arg0.getHeaders()).build();
		
		//System.out.println(wsMsg);
		
		return wsMsg;
	}

}
