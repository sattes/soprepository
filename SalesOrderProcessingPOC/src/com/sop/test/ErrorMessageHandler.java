package com.sop.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Locale;

import org.springframework.integration.Message;

import org.springframework.integration.MessageDeliveryException;

import org.springframework.integration.MessageHandlingException;
import org.springframework.integration.MessageRejectedException;


public class ErrorMessageHandler implements org.springframework.integration.core.MessageHandler {

	File f = new File("../SalesOrderProcessingPOC/src/logs/SOPError.log");
	FileWriter fw =null;
	@Override
	public void handleMessage(Message<?> arg0) throws MessageRejectedException,
			MessageHandlingException, MessageDeliveryException {
		
		String payloadType = arg0.getPayload().getClass().getName();
		String errorMsg ="";
		if(payloadType.startsWith("[B")){
			 errorMsg = new String((byte[])arg0.getPayload());
			System.out.println("Error Occured: "+errorMsg);
			
		}
		else{
			errorMsg =arg0.getPayload().toString();
			System.out.println("Error Occured: "+arg0.getPayload());
			System.out.println("Got the error message in error handler as"+ arg0.toString());
		}
		
		try{
			if(fw==null)
			 fw = new FileWriter(f);
			PrintWriter out = new PrintWriter(new BufferedWriter(fw));
			 
			out.println(Calendar.getInstance(Locale.ENGLISH).getTime()+"-"+ errorMsg);
			out.flush();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}/*finally{
			try{
				
			fw.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}*/
	}

}
