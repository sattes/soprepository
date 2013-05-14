package com.sp3.mvc.helper;

import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;


public class MessageHelper {
	
//	private static String url = "failover://(tcp://10.30.134.48:61616,tcp://10.30.134.43:61616)?randomize=false";
	
//	private static String url = "failover://(tcp://10.30.134.48:61616,tcp://10.30.134.43:61616)?randomize=true";
	
	private static String url = "failover://(tcp://10.30.135.105:61616,tcp://10.30.135.106:61616)?randomize=false";
	
	


			//ActiveMQConnection.DEFAULT_BROKER_URL;

    // Name of the queue we will be sending messages to
    /*private String subject = "sopInboundQueue";

    public void setSubject(String subject){
    	this.subject = subject;
    }*/
    public void sendMessage(String qName, String textMessage){
    	// Getting JMS connection from the server and starting it
    	Connection connection=null;
    	try{
        ConnectionFactory connectionFactory =
            new ActiveMQConnectionFactory(url);
         connection = connectionFactory.createConnection();
        connection.start();

        // JMS messages are sent and received using a Session. We will
        // create here a non-transactional session object. If you want
        // to use transactions you should set the first parameter to 'true'
        Session session = connection.createSession(false,
            Session.AUTO_ACKNOWLEDGE);

        // Destination represents here our queue 'TESTQUEUE' on the
        // JMS server. You don't have to do anything special on the
        // server to create it, it will be created automatically.
        Destination destination = session.createQueue(qName);

        // MessageProducer is used for sending messages (as opposed
        // to MessageConsumer which is used for receiving them)
        MessageProducer producer = session.createProducer(destination);

        // We will send a small text message saying 'Hello' in Japanese
        TextMessage message = session.createTextMessage(textMessage);

        // Here we are sending the message!
        producer.send(message);
        System.out.println("Sent message '" + message.getText() + "'");

        
    	}catch(Exception e){
    		Logger.getLogger(MessageHelper.class.getName()).log(new LogRecord(java.util.logging.Level.SEVERE,e.getMessage()));
    	}finally{
    		try{
    			connection.close();
    		}catch(Exception e){
    			Logger.getLogger(MessageHelper.class.getName()).log(new LogRecord(java.util.logging.Level.SEVERE,e.getMessage()));
    		}
    	}
    }

}
