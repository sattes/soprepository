package com.sop.jmx.test;

import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jmx.support.MBeanServerConnectionFactoryBean;

import com.sop.test.SOPOrderProcessorIntf;

public class JMXContextLoader {

	public static void main(String args[]){
		ApplicationContext context = new FileSystemXmlApplicationContext("../SalesOrderProcessingPOC/src/JMXContextConfig.xml");
		//MBeanServerConnection clientConnector = ((MBeanServerConnection)context.getBean("clientConnector"));
		//SOPOrderProcessorIntf sopManager = (SOPOrderProcessorIntf)context.getBean("serverManagerProxy");
		//JMXContextLoader contextLoader = new JMXContextLoader();
		//contextLoader.testApplicationContextLoading(sopManager);
	}
	
	
}
