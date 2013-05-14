package com.sp3.mvc.helper;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateUtils {
	
	public static String getFormattedDateStr(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(d);
		return dateStr;
	}
	
	public static Date getFormattedDate(Date d) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(d);
		Date dDate = sdf.parse(dateStr);
		return dDate;
	}
	
	public static XMLGregorianCalendar getXMLGregorianCalendar(Date d) throws ParseException, DatatypeConfigurationException {
		Date formattedDate = getFormattedDate(d);
		GregorianCalendar gregory = new GregorianCalendar();
		gregory.setTime(formattedDate);
		XMLGregorianCalendar xgc = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(gregory);
		return xgc;
	}
	
	public static BigInteger convertLongToBigInt(Long longVar ) {
		// Convert Long to String.
		String stringVar =longVar.toString();

		// Convert to byte array
		byte[] byteArrayVar = new byte[ stringVar.length() ];
		byteArrayVar = stringVar.getBytes();

		// Convert to BigInteger
		BigInteger bigIntVar = new BigInteger( byteArrayVar );
		return bigIntVar;
	}

}
