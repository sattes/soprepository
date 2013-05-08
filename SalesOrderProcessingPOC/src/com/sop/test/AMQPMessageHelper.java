package com.sop.test;



import java.util.Scanner;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;






public class AMQPMessageHelper {

	private String queueName = "sopCustInbound";
	private  String EXCHANGE_NAME = "amq.direct";
	String amqpRabbitMQAddresses = "10.30.135.103:5672,10.30.135.101:5672";

	public void sendMessage(String msg) {
		try {
			CachingConnectionFactory factory = new CachingConnectionFactory();
	        factory.setAddresses(amqpRabbitMQAddresses);
	        org.springframework.amqp.rabbit.connection.Connection connection = factory.createConnection();
	        Channel channel = connection.createChannel(false);

	        //channel.exchangeDeclare(EXCHANGE_NAME, "direct");

	        

	        channel.basicPublish(EXCHANGE_NAME, queueName, null, msg.getBytes());
	        System.out.println(" [x] Sent '" + msg + "'");

	        channel.close();
	        connection.close();

			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void receiveMessage(){
		try{
			CachingConnectionFactory factory = new CachingConnectionFactory();
	        factory.setAddresses(amqpRabbitMQAddresses);
	        org.springframework.amqp.rabbit.connection.Connection connection = factory.createConnection();
	        Channel channel = connection.createChannel(false);

       // channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //ring queueName = channel.queueDeclare().getQueue();
        //channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());

            System.out.println(" [x] Received '" + message + "'");
        }
		}catch(Exception e ){
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		AMQPMessageHelper msgHelper = new AMQPMessageHelper();
		
		Scanner in = new Scanner(System.in);
		String s ="";
	      
	      while (!s.equals("exit")) {
	    	  System.out.println("Enter a string");
	    	  s = in.nextLine();
	    	  if(s.equals("exit"))
	    		  System.exit(0);
		msgHelper.sendMessage(s);
		//msgHelper.receiveMessage();
	      }
		
	}
}
