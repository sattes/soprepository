package com.sop.test;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import com.rabbitmq.client.ConnectionFactory;

public class ReceiveMessageTest1 {
	public static void main(String[] args) {

		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setQueueNames("sample");
		 
		// Instantiate the above container

		MyMessageListener listener = new MyMessageListener();
		container.setMessageListener(listener);
	}
}

class MyMessageListener implements MessageListener{

	@Override
	public void onMessage(Message message) {

		byte[] messageContent = message.getBody();
		System.out.println("Message receied is " + messageContent);
	}
	

}
