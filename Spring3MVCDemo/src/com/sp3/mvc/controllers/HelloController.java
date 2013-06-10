package com.sp3.mvc.controllers;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.sop.dao.CategoryDao;
import com.sop.dao.CustomerDao;
import com.sp3.mvc.models.CatAndProducts;
import com.sp3.mvc.models.Category;
import com.sp3.mvc.models.Customer;
@Controller
@SessionAttributes("caps")
//@RequestMapping("/hello")
public class HelloController {
	
	private static Logger logger = Logger.getLogger(HelloController.class);
	@Resource(name = "catDao")
	private CategoryDao catDao;
	@Resource(name = "custDao")
	private CustomerDao custDao;



	
	@RequestMapping("/index")
	public String sayHello(Model model,HttpServletRequest request) throws SQLException, ClassNotFoundException {
        logger.debug("Inside sayHello method...");
        model.addAttribute("message", "Welcome to SalesOrderProcessingSystem");
        final String currentUser = SecurityContextHolder.getContext()
                     .getAuthentication().getName();
        if ((currentUser.equals("") || currentUser.equals(null) || currentUser
                     .equalsIgnoreCase("anonymousUser"))) {
               
               logger.info("You are not logged in");
               
        } 
        else{
        		Object loginParam = request.getSession().getAttribute("login");
               if(loginParam == null) {
               Customer dbLogin = null;

               try {

                     dbLogin = custDao.getCustomerByUserId(currentUser);

                     } catch (SQLException e) {

                     logger.error("SQLException got from CustomerDao in goToCustomerHome()");

                     throw e;

                     }

                     request.getSession().setAttribute("login", dbLogin);
 
                     List<Category> categories = null;
                     try {
                            categories = catDao.getAllCategories();
                            CatAndProducts caps = new CatAndProducts();
                            caps.getCategories().addAll(categories);
                            model.addAttribute("caps", caps);
                     } catch (SQLException e) {
                            logger.error("SQLException got from CategoryDao in getAllCategories()");
                            throw e;
                     }
               } else {
                     HttpSession session = request.getSession();
                     if(session != null) {
                             Enumeration<String> sessionAttributes = session.getAttributeNames();
                            while(sessionAttributes.hasMoreElements()) {
                                   String attributeName = sessionAttributes.nextElement();
                                   if(!attributeName.equals("login")) {
                                          session.removeAttribute(attributeName);
                                   }
                            }
                     }
               }
               
        }
        
        return "welcomePage";
 }
	
	/*@RequestMapping(value="/gotoregister")
	public String goToRegisterPage(@ModelAttribute("registration")Registration registration, Model model) {
		logger.debug("Inside goToIndexPage method...");
		return "register";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(@ModelAttribute("registration")Registration registration, Model model) {
		logger.debug("Inside submit method...");
		
		logger.debug("First Name = "+registration.getFname());
		logger.debug("Last Name = "+registration.getLname());
		logger.debug("Gender = "+registration.getGender());
		logger.debug("Type of Address = "+registration.getTypeOfAddress());
		logger.debug("Address = "+registration.getAddr());
		logger.debug("email = "+registration.getEmail());
		logger.debug("Country = "+registration.getCountry());
		logger.debug("User Name = "+registration.getUsername());
		logger.debug("Password = "+registration.getPassword());
		
		//logger.debug("Selected Check Box = "+registration.getCb());
		
		model.addAttribute("fname", registration.getFname());
		model.addAttribute("lname", registration.getLname());
		model.addAttribute("gender", registration.getGender());
		model.addAttribute("typeOfAddress", registration.getTypeOfAddress());
		model.addAttribute("addr", registration.getAddr());
		model.addAttribute("email", registration.getEmail());
		model.addAttribute("country", registration.getCountry());
		model.addAttribute("uname", registration.getUsername());
		
		//model.addAttribute("cb", registration.getCb());
		
		return "success";
	}*/
	

	/*@RequestMapping(value="/login", method = RequestMethod.GET)
	public String goToLogin(@ModelAttribute("registration")Registration registration, Model model) {
		model.addAttribute("registration", new Registration());
		logger.debug("Inside login method...");
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@Valid Login login, BindingResult result, Model model) {
		logger.debug("Inside login method...");
		
		logger.debug("User Name = "+login.getUserName());
		logger.debug("Password = "+login.getPassword());
		
		String userName = "imsubbu";
		String password = "subbu123";
		
		if (result.hasErrors()) {
			return "login";
		}
		
		if (!login.getUserName().equals(userName)
				|| !login.getPassword().equals(password)) {
			return "login";
		}
		//model.put("loginForm", login);
		
		return "viewProducts";
	}*/
		
}
