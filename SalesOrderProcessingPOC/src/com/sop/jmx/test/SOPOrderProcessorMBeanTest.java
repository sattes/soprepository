package com.sop.jmx.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.integration.Message;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.integration.message.MessageBuilder;
import org.springframework.jmx.support.MBeanServerConnectionFactoryBean;

import com.sop.test.AMQPMessageHelper;
import com.sop.test.AMQPPubSubMessageHandler;
import com.sop.test.SOPOrderProcessorIntf;

public class SOPOrderProcessorMBeanTest {
	
	private MBeanServerConnectionFactoryBean clientConnector = new MBeanServerConnectionFactoryBean();
	

	
	@Qualifier("serverManagerProxy")
	private SOPOrderProcessorIntf sopManager ;

	

	public SOPOrderProcessorIntf getSopManager() {
		return sopManager;
	}

	public void setSopManager(SOPOrderProcessorIntf sopManager) {
		this.sopManager = sopManager;
	}

	public void testApplicationContextLoading(SOPOrderProcessorIntf sopManager ) {
	   
	
	    String contextXMLPath = "../SalesOrderProcessingPOC/src/SalesOrderProcessingFlowWithRabbit.xml";
	     sopManager.setApplicationContextPath(contextXMLPath);
	    sopManager.loadApplicationContext();
	    
	    
	}
	
	public static void main(String args[]){
		ApplicationContext context = new FileSystemXmlApplicationContext("../SalesOrderProcessingPOC/src/JMXClientConfig.xml");
	
	}
	
	public boolean checkServiceAvailability(String srvURL,SOPOrderProcessorIntf sopManager){
		boolean availCheckFlg = sopManager.checkServiceAvailability(srvURL);
	    return availCheckFlg;
	}
	
	public String checkServiceAvailabilityFor() throws Exception{
		File serviceURl = new File("../SalesOrderProcessingPOC/src/META-INF/ServicesUrls.txt");
		AMQPMessageHelper msgHelper = new AMQPMessageHelper();
		System.out.println("File Name: "+serviceURl.getName());
		BufferedReader br = new BufferedReader(new FileReader(serviceURl));
		String serviceURL = br.readLine();
		String serviceStatus="";
		
		while(serviceURL!=null){
			
		
		boolean availCheckFlg =  checkServiceAvailability(serviceURL,sopManager);
		if(availCheckFlg){
			serviceStatus =  serviceStatus+ serviceURL +"= available,";
			System.out.println(serviceStatus);
			
			
			
		}else{
			serviceStatus =serviceStatus+ serviceURL +"= unavailable,";
			System.out.println(serviceStatus);
			
			//msgHelper.receiveMessage();
		}
		serviceURL=br.readLine();
		//msgHelper.receiveMessage();
		}
		msgHelper.sendMessage(serviceStatus);
		return serviceStatus;
	}
	             
}
