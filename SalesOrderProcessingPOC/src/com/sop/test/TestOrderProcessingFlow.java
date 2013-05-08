package com.sop.test;

import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.integration.xml.DefaultXmlPayloadConverter;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;



public class TestOrderProcessingFlow {

	public static void main(String args[]) throws Exception{
		ApplicationContext context = new FileSystemXmlApplicationContext(
	    	"C:/enterprise-integration-1.5.0.RELEASE/newWorkSpace/SalesOrderProcessingPOC/src/SalesOrderProcessingFlow.xml");
		PublishSubscribeChannel psChannel3 =(PublishSubscribeChannel)context.getBean("wsInboundChannel");
        ErrorMessageHandler psMHandler3 = new ErrorMessageHandler();
        EventDrivenConsumer edc3= new EventDrivenConsumer(psChannel3, psMHandler3);
  
        //ConnectionFactory factory = new ConnectionFactory();
        //factory.setUsername("guest");
        //factory.setPassword("guest");
        //factory.setUri("amqp://guest:guest@localhost:5672//");
        //Connection con = factory.newConnection();
        //con.createChannel().;
      
        edc3.start();
        PublishSubscribeChannel psChannel4 =(PublishSubscribeChannel)context.getBean("wsOutboundChannel");
        ErrorMessageHandler psMHandler4 = new ErrorMessageHandler();
        EventDrivenConsumer edc4= new EventDrivenConsumer(psChannel4, psMHandler4);
        edc4.start();
        PublishSubscribeChannel psChannel5 =(PublishSubscribeChannel)context.getBean("jdbcInputChannel");
        ErrorMessageHandler psMHandler5 = new ErrorMessageHandler();
        EventDrivenConsumer edc5= new EventDrivenConsumer(psChannel5, psMHandler5);
        edc5.start();
        PublishSubscribeChannel psChannel6 =(PublishSubscribeChannel)context.getBean("jdbcCustInputChannel");
        ErrorMessageHandler psMHandler6 = new ErrorMessageHandler();
        EventDrivenConsumer edc6= new EventDrivenConsumer(psChannel6, psMHandler6);
        edc6.start();
        DirectChannel dcjdbcPayIn =  (DirectChannel)context.getBean("jdbcInboundChannel");
        AMQPPubSubMessageHandler psHandler7 = new AMQPPubSubMessageHandler();
       EventDrivenConsumer edc7 = new EventDrivenConsumer(dcjdbcPayIn, psHandler7);
       edc7.start();
	}
}
