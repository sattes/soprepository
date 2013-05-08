package com.sop.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Locale;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.FaultMessageResolver;

public class WSGatewayFaultResolver implements FaultMessageResolver {
	File f = new File("../SalesOrderProcessingPOC/src/logs/SOPWSFault.log");
	FileWriter fw =null;
	@Override
	public void resolveFault(WebServiceMessage arg0) throws IOException {
		try{
			
			if(fw==null)
			 fw = new FileWriter(f);
			PrintWriter out = new PrintWriter(new BufferedWriter(fw));
			 System.out.println("Inside Error Handler: "+arg0.getPayloadSource());
			out.println(Calendar.getInstance(Locale.ENGLISH).getTime()+"-"+ arg0.getPayloadResult());
			out.flush();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

}
