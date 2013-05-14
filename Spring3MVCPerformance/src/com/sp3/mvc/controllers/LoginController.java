package com.sp3.mvc.controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.sp3.mvc.dao.CategoryDao;
import com.sp3.mvc.dao.CustomerDao;
import com.sp3.mvc.models.CatAndProducts;
import com.sp3.mvc.models.Category;
import com.sp3.mvc.models.Customer;
import com.sp3.mvc.models.Login;

@Controller
@SessionAttributes("caps")
public class LoginController {
	
	private static Logger logger = Logger.getLogger(LoginController.class);
	
	/*@Resource(name = "myProps")
	private Properties myProps;*/
	
	@Resource(name = "custDao")
	private CustomerDao custDao;
	
	@Resource(name = "catDao")
	private CategoryDao catDao;
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String goToLogin(@ModelAttribute("login")Login login, Model model) {
		logger.debug("Inside login method...");
		
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@Valid Login login, BindingResult result, Model model, HttpServletRequest request) throws SQLException, ClassNotFoundException {
		logger.debug("Inside login method...");
		
		String userName = login.getUserName();
		String password = login.getPassword();
		
		logger.debug("User Name = "+userName);
		logger.debug("Password = "+password);
		
		if (result.hasErrors()) {
			return "login";
		}
		
		Customer dbLogin = null;
		try {
			dbLogin = custDao.getCustomerByUserId(userName);
		} catch (SQLException e) {
			logger.error("SQLException got from CustomerDao in getCustomerByUserId(userName)");
			throw e;
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException got from CustomerDao in getCustomerByUserId(userName)");
			throw e;
		}
		
		if(dbLogin == null) {
			return "login";
		}
		
		String dbUserName = dbLogin.getUserName();
		String dbPassword = dbLogin.getPassword();
		
		logger.debug("DB User Name = "+dbUserName);
		logger.debug("DB Password = "+dbPassword);
		
		request.getSession().setAttribute("login", dbLogin);
		
		//Get all the Categories
		List<Category> categories = null;
		try {
			categories = catDao.getAllCategories();
			CatAndProducts caps = new CatAndProducts();
			caps.getCategories().addAll(categories);
			model.addAttribute("caps", caps);
		} catch (SQLException e) {
			logger.error("SQLException got from CategoryDao in getAllCategories()");
			throw e;
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException got from CategoryDao in getAllCategories()");
			throw e;
		}
		
		//request.getSession().setAttribute("categories", categories);
		
		return "viewProducts";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(@ModelAttribute("login")Login login, HttpServletRequest request) {
		logger.debug("Inside logout method...");
		
		if(request.getSession() != null) {
			request.getSession().invalidate();
		}
		logger.debug("request.getSession() categories - "+request.getSession().getAttribute("categories"));
		return "welcomePage";
	}

}
