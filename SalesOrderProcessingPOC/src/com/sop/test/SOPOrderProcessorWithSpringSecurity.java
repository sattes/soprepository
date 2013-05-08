package com.sop.test;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.endpoint.EventDrivenConsumer;

public class SOPOrderProcessorWithSpringSecurity {
	public static void main(String args[]) throws Exception{
		ApplicationContext context = new FileSystemXmlApplicationContext(
	    	"C:/enterprise-integration-1.5.0.RELEASE/newWorkSpace/SalesOrderProcessingPOC/src/SalesOrderProcessingFlowWithSecurity.xml");
		PublishSubscribeChannel psChannel3 =(PublishSubscribeChannel)context.getBean("wsInboundChannel");
		AMQPPubSubMessageHandler psMHandler3 = new AMQPPubSubMessageHandler();
        EventDrivenConsumer edc3= new EventDrivenConsumer(psChannel3, psMHandler3);
        
      
        //ConnectionFactory factory = new ConnectionFactory();
        //factory.setUsername("guest");
        //factory.setPassword("guest");
        //factory.setUri("amqp://guest:guest@localhost:5672//");
        //Connection con = factory.newConnection();
        //con.createChannel().;
      
        edc3.start();
        PublishSubscribeChannel psChannel4 =(PublishSubscribeChannel)context.getBean("wsOutboundChannel");
        AMQPPubSubMessageHandler psMHandler4 = new AMQPPubSubMessageHandler();
        EventDrivenConsumer edc4= new EventDrivenConsumer(psChannel4, psMHandler4);
        edc4.start();
        PublishSubscribeChannel psChannel5 =(PublishSubscribeChannel)context.getBean("jdbcInputChannel");
        AMQPPubSubMessageHandler psMHandler5 = new AMQPPubSubMessageHandler();
        EventDrivenConsumer edc5= new EventDrivenConsumer(psChannel5, psMHandler5);
        edc5.start();
        PublishSubscribeChannel psChannel6 =(PublishSubscribeChannel)context.getBean("jdbcCustInputChannel");
        AMQPPubSubMessageHandler psMHandler6 = new AMQPPubSubMessageHandler();
        EventDrivenConsumer edc6= new EventDrivenConsumer(psChannel6, psMHandler6);
        edc6.start();
        DirectChannel dcjdbcPayIn =  (DirectChannel)context.getBean("jdbcInboundChannel");
        AMQPPubSubMessageHandler psHandler7 = new AMQPPubSubMessageHandler();
       EventDrivenConsumer edc7 = new EventDrivenConsumer(dcjdbcPayIn, psHandler7);
       edc7.start();
       PublishSubscribeChannel psChannel8 =(PublishSubscribeChannel)context.getBean("jmsCustInboundChannel");
       AMQPPubSubMessageHandler psMHandler8 = new AMQPPubSubMessageHandler();
       EventDrivenConsumer edc8= new EventDrivenConsumer(psChannel8, psMHandler8);
       edc8.start();
	}
}
