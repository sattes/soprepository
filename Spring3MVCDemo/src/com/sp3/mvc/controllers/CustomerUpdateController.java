package com.sp3.mvc.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sop.dao.CustomerDao;
import com.sp3.mvc.models.Customer;


@Controller
public class CustomerUpdateController {
	
	private static Logger logger = Logger.getLogger(CustomerUpdateController.class);
		
	@Resource(name = "custDao")
	private CustomerDao custDao;
	
	@RequestMapping(method = RequestMethod.GET, value="/getDisabledCustomers")
	public String getDisabledCustomers(HttpServletRequest request) throws SQLException {
		logger.debug("CustomerUpdateController::goToPaymentInfo End...");
		
		try {
			List<Customer> disabledCustomers = custDao.getDisbledCustomers();
			request.getSession().setAttribute("disabledCustomers", disabledCustomers);
		} catch (SQLException e) {
			logger.error("");
			throw e;
		} 
		
		
		logger.debug("CustomerUpdateController::getDisabledCustomers End...");
		return "enableCustomers";
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/enableCustomers")
	public String enableCustomers(@RequestParam(value = "userIds", required = false) String[] userIds, HttpServletRequest request) throws SQLException  {
		logger.debug("CustomerUpdateController::enableCustomers End...");
		StringBuffer sb = new StringBuffer();
		
		for(String userId : userIds) {
			sb.append("'")
			.append(userId)
			.append("',");
		}
		String customers = sb.substring(0, sb.length()-1);
		try {
			custDao.enableCustomers(customers);
		} catch (SQLException e) {
			logger.error("");
			throw e;
		} 
		//request.getSession().removeAttribute("paymentList");
		logger.debug("CustomerUpdateController::enableCustomers End...");
		return "enableCustomerSuccess";
	}
	
	

}
