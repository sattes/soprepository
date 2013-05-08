package com.sop.test;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.Message;

import org.springframework.integration.MessageDeliveryException;

import org.springframework.integration.MessageHandlingException;
import org.springframework.integration.MessageRejectedException;


public class AMQPPubSubMessageHandler implements org.springframework.integration.core.MessageHandler {
	


	@Override
	public void handleMessage(Message<?> arg0) throws MessageRejectedException,
			MessageHandlingException, MessageDeliveryException {
		
		System.out.println("Inside AMQP Message Handler - Message type "+arg0.getPayload().getClass());
		String payloadType = arg0.getPayload().getClass().getName();
		if(payloadType.startsWith("[B"))
			System.out.println("Message Payload: "+new String((byte[])arg0.getPayload()));
		else
			System.out.println("Message Payload: "+arg0.getPayload());
		//arg0 = MessageBuilder.withPayload(arg0.getPayload()).setCorrelationId("1234").build();
		System.out.println("Got the subscribed message in handler as"+ arg0.toString());
		
		
	}

}
