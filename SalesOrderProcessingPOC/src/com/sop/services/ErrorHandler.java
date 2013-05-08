package com.sop.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Locale;

public class ErrorHandler implements org.springframework.util.ErrorHandler {
	File f = new File("../SalesOrderProcessingPOC/src/logs/SOPError.log");
	FileWriter fw =null;
	@Override
	public void handleError(Throwable arg0) {
		
		try{
			if(fw==null)
			 fw = new FileWriter(f);
			PrintWriter out = new PrintWriter(new BufferedWriter(fw));
			 System.out.println("Inside Error Handler: "+arg0.getCause());
			out.println(Calendar.getInstance(Locale.ENGLISH).getTime()+"-"+ arg0.getCause());
			out.flush();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

}
