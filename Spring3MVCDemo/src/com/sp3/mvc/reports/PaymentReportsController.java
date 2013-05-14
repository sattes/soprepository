package com.sp3.mvc.reports;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emc.sqlfire.sop.domain.Payment;
import com.sp3.mvc.dao.DBUtils;
import com.sp3.mvc.models.PaymentReport;

@Controller
public class PaymentReportsController {
	
	private static Logger logger = Logger.getLogger(PaymentReportsController.class);
	
	@Resource(name = "myProps")
	private Properties myProps;
	
	@RequestMapping(value="/gotopaymentreports", method = RequestMethod.GET)
	public String goToPaymentReports(@ModelAttribute("paymentReport")PaymentReport paymentReport, Model model, HttpServletRequest request) throws SQLException, ClassNotFoundException {
		logger.debug("PaymentReportsController::goToPaymentReports Start...");
		logger.debug("session attribute = "+request.getSession().getAttribute("paymentReports"));
		
		if(request.getSession().getAttribute("paymentReports") != null) {
			request.getSession().removeAttribute("paymentReports");
		}
		
		logger.debug("PaymentReportsController::goToPaymentReports End...");
		return "payment_reports";
	}
	
	@RequestMapping(value="/getpayments", method = RequestMethod.POST)
	public String getPayments(@ModelAttribute("paymentReport")PaymentReport paymentReport, Model model, HttpServletRequest request) throws SQLException, ClassNotFoundException {
		logger.debug("PaymentReportsController::getPayments Start...");
		
		logger.debug("OrderId = "+paymentReport.getOrderId());
		logger.debug("PaymentId = "+paymentReport.getPaymentId());
		logger.debug("PaymentStatus = "+paymentReport.getPaymentStatus());
		logger.debug("FromDate = "+paymentReport.getFromDate());
		logger.debug("ToDate = "+paymentReport.getToDate());
		
		Connection connection = DBUtils.getConnection();
		
		StringBuffer sb = new StringBuffer();
		sb.append("{call SOPV2.GET_PAYMENT_DETAILS(?,?,?,?,?)  ON TABLE ")
			.append(myProps.getProperty("schemaName"))
			.append("PAYMENT}");
		CallableStatement stmt = connection.prepareCall(sb.toString());
		
		stmt.setString(1, paymentReport.getPaymentId());
		stmt.setString(2, paymentReport.getOrderId());
		if(paymentReport.getPaymentStatus().equals("Select")) {
			stmt.setString(3, null);
		} else {
			stmt.setString(3, paymentReport.getPaymentStatus());
		}
		if(paymentReport.getFromDate() == null) {
			stmt.setDate(4, null);
		} else {
			Calendar fromcal = Calendar.getInstance();
			fromcal.setTime(paymentReport.getFromDate());
			stmt.setDate(4, new java.sql.Date(fromcal.getTimeInMillis())); 
		}
		if(paymentReport.getToDate() == null) {
			stmt.setDate(5, null);
		} else {
			Calendar tocal = Calendar.getInstance();
			tocal.setTime(paymentReport.getToDate());
			stmt.setDate(5, new java.sql.Date(tocal.getTimeInMillis())); 
		}
		
		stmt.execute();
		ResultSet rs = stmt.getResultSet();
		List<Payment> paymentReports = new ArrayList<Payment>();
		while (rs. next()) {
			String colName = rs.getMetaData().getColumnName(1);
			Object obj  = rs.getObject(colName);
			paymentReports = (ArrayList<Payment>)obj;
			
			break;
		}
		logger.debug("payments size - "+paymentReports.size());
		
		request.getSession().setAttribute("paymentReports", paymentReports);
		logger.debug("PaymentReportsController::getPayments End...");
		return "payment_reports";
	}

}
