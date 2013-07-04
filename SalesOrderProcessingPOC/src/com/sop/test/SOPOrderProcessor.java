package com.sop.test;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

import org.springframework.stereotype.Component;




@Component
@ManagedResource(objectName="com.sop.test:name=SOPOrderProcessor", 
                 description="Sales Order Processing Manager.")
public class SOPOrderProcessor implements SOPOrderProcessorIntf{
	
	private String applicationContextPath ="";
	private ApplicationContext context=null;
	 @ManagedAttribute(description="ApplicationContext File path.")
	public String getApplicationContextPath() {
		return applicationContextPath;
	}
	 @ManagedAttribute(description="ApplicationContext File path.")
	public void setApplicationContextPath(String applicationContextPath) {
		this.applicationContextPath = applicationContextPath;
	}
	public static void main(String args[]) throws Exception{
		SOPOrderProcessor sopOrderProc = new SOPOrderProcessor();
		sopOrderProc.setApplicationContextPath("../SalesOrderProcessingPOC/src/SalesOrderProcessingFlowWithRabbit.xml");
		sopOrderProc.loadApplicationContext();
		sopOrderProc.startEventDrivenConsumers();
     
	}
	@ManagedOperation(description="Load application Context File using File System XML Application Context")
    public  void loadApplicationContext(){
		try{
			System.out.println("loading application context xml...");
		ApplicationContext context = new FileSystemXmlApplicationContext(this.applicationContextPath);
		setContext(context);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("loaded application context xml...");
	}
	 @ManagedAttribute(description="ApplicationContext .")
	public ApplicationContext getContext() {
		return context;
	}
	 @ManagedAttribute(description="ApplicationContext .")
	public void setContext(ApplicationContext context) {
		this.context = context;
	}
	@ManagedOperation(description="Start Event Driven Consumers of the necessary channels.")
   public  void startEventDrivenConsumers(){
		ApplicationContext context = getContext();
		PublishSubscribeChannel psChannel3 =(PublishSubscribeChannel)context.getBean("wsInboundChannel");
		AMQPPubSubMessageHandler psMHandler3 = new AMQPPubSubMessageHandler();
        EventDrivenConsumer edc3= new EventDrivenConsumer(psChannel3, psMHandler3);
        System.out.println("starting event consumer...");
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
        PublishSubscribeChannel dcjdbcPayIn =  (PublishSubscribeChannel)context.getBean("jdbcInboundChannel");
        AMQPPubSubMessageHandler psHandler7 = new AMQPPubSubMessageHandler();
       EventDrivenConsumer edc7 = new EventDrivenConsumer(dcjdbcPayIn, psHandler7);
       edc7.start();
       PublishSubscribeChannel psChannel8 =(PublishSubscribeChannel)context.getBean("jmsCustInboundChannel");
       AMQPPubSubMessageHandler psMHandler8 = new AMQPPubSubMessageHandler();
       EventDrivenConsumer edc8= new EventDrivenConsumer(psChannel8, psMHandler8);
       edc8.start();
       DirectChannel dcErrorCh = (DirectChannel)context.getBean("errorOutBoundChannel");
       ErrorMessageHandler psMHandler9 = new ErrorMessageHandler();
       EventDrivenConsumer edc9= new EventDrivenConsumer(dcErrorCh, psMHandler9);
      edc9.start();
	}
	@ManagedOperation(description="Check the availability of a web service through URL.")
	@ManagedOperationParameters({
        @ManagedOperationParameter(name="serviceURL", description= "Web Service URL for which the service availability need to be checked.")})
	public boolean checkServiceAvailability(String serviceURL){
		boolean servAvailFlg = false;
		
		HttpURLConnection conn = null;
		try {
		    conn = (HttpURLConnection) new URL(serviceURL).openConnection();
		    conn.connect();

		    if(conn.getResponseCode() == 200)
		    	servAvailFlg = true;
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
		    if(conn != null)
		        conn.disconnect();
		}
		return servAvailFlg;
	}
}
