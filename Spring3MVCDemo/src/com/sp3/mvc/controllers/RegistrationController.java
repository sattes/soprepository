package com.sp3.mvc.controllers;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.validation.Valid;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sop.dao.AddressDao;
import com.sop.dao.CustomerDao;
import com.sop.dao.CustomerRoleDao;
import com.sp3.mvc.dao.DBUtils;
//import com.sop.dao.DBUtils;
import com.sp3.mvc.enums.AddressTypeEnum;
import com.sp3.mvc.enums.CustomerStatusEnum;
import com.sp3.mvc.helper.AMQPMessageHelper;
import com.sp3.mvc.helper.JaxbHelper;
import com.sp3.mvc.helper.MessageHelper;
import com.sp3.mvc.models.Address;
import com.sp3.mvc.models.Customer;
import com.sp3.mvc.models.CustomerRole;

@Controller
public class RegistrationController {
	
	private static Logger logger = Logger.getLogger(RegistrationController.class);
	
	@Resource(name = "myProps")
	private Properties myProps;
	
	@Resource(name = "custDao")
	private CustomerDao custDao;
	
	@Resource(name = "roleDao")
	private CustomerRoleDao roleDao;
	
	@Resource(name = "addrDao")
	private AddressDao addrDao;
	
	@Resource(name="jaxb2Marshaller")
	org.springframework.oxm.jaxb.Jaxb2Marshaller jaxb2Marshaller;
	
	@Resource(name="dataSource")
	DataSource dataSource;
	
	private Marshaller marshal;
    private Unmarshaller unmarshal; 
    
    private String userType;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Marshaller getMarshal() {
		return marshal;
	}

	public void setMarshal(Marshaller marshal) {
		this.marshal = marshal;
	}

	public Unmarshaller getUnmarshal() {
		return unmarshal;
	}

	public void setUnmarshal(Unmarshaller unmarshal) {
		this.unmarshal = unmarshal;
	}

	@RequestMapping(value="/gotoregister", method = RequestMethod.GET)
	public String goToRegisterPage(@ModelAttribute("customer")Customer customer, Model model, HttpServletRequest request,
			@RequestParam("userType") String userType ) {
		logger.debug("Inside goToRegisterPage method...");
		
		logger.debug("userType = "+userType);
		this.setUserType(userType);
		
		return "register";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(@Valid Customer customer, BindingResult result, Model model) throws SQLException, ClassNotFoundException {
		logger.debug("Inside register method...");
		
		logger.info("Fname"+ customer.getFname());
		logger.info("lname"+ customer.getLname());
		logger.info("gender"+ customer.getGender());
		logger.info("custtype"+ customer.getCustType());
		logger.info("Email"+ customer.getEmail());
		logger.info("username"+ customer.getUserName());
		logger.info("passowrd"+ customer.getPassword());
		logger.info("Adress1"+ customer.getCustomerAddress().getAddress1());
		logger.info("Address2"+ customer.getCustomerAddress().getAddress2());
		logger.info("customer Address:-  "+ customer.getCustomerAddress());
		logger.info("contry"+ customer.getCustomerAddress().getCountry());
		logger.info("state"+ customer.getCustomerAddress().getState());
		logger.info("city"+ customer.getCustomerAddress().getCity());
		logger.info("zip"+ customer.getCustomerAddress().getZip());
		logger.info("phone"+ customer.getCustomerAddress().getPhone());
		
		
		
		
		 Address customerAddress=customer.getCustomerAddress();
		 String addr1Message = myProps.getProperty("NotEmpty.customer.address1");
		 logger.debug("addr1Message - "+addr1Message);
		 //logger.debug("result.hasErrors() = "+result.hasErrors());
		 
		if(customerAddress != null) {
		    String address1=customerAddress.getAddress1();
		    logger.info("Address1 name is :- "+ address1);
		 if(address1.equals("")){
			model.addAttribute("addr1Message", addr1Message);
			//return "register";
			customer.setHasErrors(true);
		}
		
		/*String address2=customer.getCustomerAddress().getAddress2();
		logger.info("Address2 name is :- "+ address2);
		if(address2.equals("")){
			model.addAttribute("ad2tmessage", "Address name must be filled");
			//return "register";
		}*/
		
		
		Pattern cp = Pattern.compile("[a-zA-Z]*");
		Matcher cm = cp.matcher(customer.getCustomerAddress().getCity());
		boolean cityMatchFound = cm.matches();
		if (!cityMatchFound) {
			String cityMessage = myProps.getProperty("Pattern.customer.city");
			model.addAttribute("cityMessage", cityMessage);
			customer.setHasErrors(true);
        }
		String cityMessage = myProps.getProperty("NotEmpty.customer.city");
		String city=customer.getCustomerAddress().getCity();
		
	   logger.info("City name is :- "+ city);
		if(city.equals("")){
			
			model.addAttribute("cityMessage", cityMessage);
			customer.setHasErrors(true);
			//return "register";
		}
			String password=customer.getPassword();
		logger.info("Password is :- "+password);
		
		Pattern sp = Pattern.compile("[a-zA-Z]*");
		Matcher sm = sp.matcher(customer.getCustomerAddress().getState());
		boolean stateMatchFound = sm.matches();
		if (!stateMatchFound) {
			String stateMessage = myProps.getProperty("Pattern.customer.state");
			model.addAttribute("stateMessage", stateMessage);
			customer.setHasErrors(true);
        }
		
		String stateMessage = myProps.getProperty("NotEmpty.customer.state");
		String state=customer.getCustomerAddress().getState();
		logger.info("State name is :- "+ state);
		if(state.equals("")){
			model.addAttribute("stateMessage",stateMessage);
			customer.setHasErrors(true);
		}
		
		
		Pattern zp = Pattern.compile("^[1-9]*[0-9][0-9]*$");
		Matcher zm = zp.matcher(customer.getCustomerAddress().getZip());
		boolean zipmatch = zm.matches();
		if (!zipmatch) {
			String zipMessage = myProps.getProperty("Pattern.customer.zip");
			model.addAttribute("zipMessage", zipMessage);
			customer.setHasErrors(true);
		}
		String zipMessage = myProps.getProperty("NotEmpty.customer.zip");
		String zip=customer.getCustomerAddress().getZip();
		
		logger.info("Zip name is :- "+ zip);
		if(zip.equals("")){
			model.addAttribute("zipMessage",zipMessage);
			customer.setHasErrors(true);
		}
		
		
		Pattern contp = Pattern.compile("[a-zA-Z]*");
		Matcher contm = contp.matcher(customer.getCustomerAddress().getCountry());
		boolean countryMatchFound = contm.matches();
		if (!countryMatchFound) {
			String countryMessage = myProps.getProperty("Pattern.customer.country");
			model.addAttribute("countryMessage", countryMessage);
			customer.setHasErrors(true);
		}
		String countryMessage = myProps.getProperty("NotEmpty.customer.country");
		String country=customer.getCustomerAddress().getCountry();
		logger.info("country name is :- "+ country);
		if(country.equals("")){
			model.addAttribute("countryMessage", countryMessage);
			customer.setHasErrors(true);
		}
		
		
		Pattern mp = Pattern.compile("^[7-9][0-9]{9}$");
		Matcher mm = mp.matcher(customer.getCustomerAddress().getPhone());
		boolean phoneMatchFound = mm.matches();
		if (!phoneMatchFound) {
			String phoneMessage = myProps.getProperty("Pattern.customer.phone");
			model.addAttribute("phoneMessage", phoneMessage);
			customer.setHasErrors(true);
		}
		String phoneMessage = myProps.getProperty("NotEmpty.customer.phone");
		String phone=customer.getCustomerAddress().getPhone();
		logger.info("State name is :- "+ phone);
		if(phone.equals("")){
			model.addAttribute("phoneMessage", phoneMessage);
			customer.setHasErrors(true);
			
		}
		
		if(customer.isHasErrors()||result.hasErrors()){
			logger.info("Not able to register as resut has errors.......");
			return "register";
		  }
		}
		
		//customer.setRoleId(this.getRoleId());
		//customer.setRoleName(this.getRoleName());
		logger.info("Registering the user:-  "+customer.getUserName()+ " with password:- "+ customer.getPassword());
		return doRegister(customer, model);
		
	}
	
		
		
		
		/*
		if (result.hasErrors()) {
			return "register";
		}
		
		return doRegister(customer, model);
		
	}*/
	
	private String doRegister(Customer customer, Model model) throws SQLException, ClassNotFoundException {
		Integer addrId=0; 
		logger.debug("Inside doRegister method...");
		logger.debug("First Name = "+customer.getFname());
		logger.debug("Last Name = "+customer.getLname());
		logger.debug("Gender = "+customer.getGender());
		logger.debug("Type of Address = "+customer.getTypeOfAddress());
		logger.debug("email = "+customer.getEmail());
		logger.debug("User Name = "+customer.getUserName());
		logger.debug("Password = "+customer.getPassword());
		logger.debug("Customer Address "+customer.getCustomerAddress().getAddress1());
		logger.debug("Customer Type = "+customer.getCustType());
		
		//logger.debug("Selected Check Box = "+registration.getCb());
		
		model.addAttribute("fname", customer.getFname());
		model.addAttribute("lname", customer.getLname());
		model.addAttribute("gender", customer.getGender());
		model.addAttribute("typeOfAddress", customer.getTypeOfAddress());
		model.addAttribute("email", customer.getEmail());
		model.addAttribute("uname", customer.getUserName());
		
		customer.setStatus(CustomerStatusEnum.ACTIVE);
		customer.setEnabled(0);
		
		addrId = addrDao.getMaxAddressId();
		logger.info("Increasing address id :-" + addrId);
		addrId = addrId + 1;
		logger.info("Increased address id is :- "+ addrId);
		Address customerAddr = customer.getCustomerAddress();
		customerAddr.setUserId(customer.getUserName());
		customerAddr.setAddressId(addrId);
		customerAddr.setAddressType(AddressTypeEnum.PERMANENT);
		CustomerRole gotRole = null;
		
		if(this.getUserType().equals("customer")) {
			logger.debug("ROLE_USER");
			gotRole = roleDao.getRoleByRoleName("ROLE_USER");
			if(customer.getCustType().toString().equals("REGULAR")) {
				customer.setEnabled(1);
			}
		}
		if(this.getUserType().equals("admin")) {
			if(!customer.getUserName().equals("sysadmin")) {
				logger.debug("ROLE_ADMIN");
				gotRole = roleDao.getRoleByRoleName("ROLE_ADMIN");
			}
			if(customer.getUserName().equals("sysadmin")) {
				logger.debug("ROLE_SYSADMIN");
				gotRole = roleDao.getRoleByRoleName("ROLE_SYSADMIN");
				customer.setEnabled(1);
			}
			
		}
		
		customer.setRoleId(gotRole.getRoleId());
		
		CustomerRole custRole = new CustomerRole();
		custRole.setRoleDesc(gotRole.getRoleDesc());
		custRole.setRoleId(gotRole.getRoleId());
		custRole.setRoleName(gotRole.getRoleName());
		customer.setRole(custRole);
		
		// Enable this code for Local Data Base - Start
		/*Connection conn = null;
		try {
			conn = dataSource.getConnection();
			logger.debug("Connection is successful");
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			custDao.insertCustomer(customer);
			addrDao.insertAddress(customerAddr);
			
			conn.commit();
		} catch (SQLException e) {
			logger.error("SQLException got from CustomerDao in insertCustomer(customer)");
			conn.rollback();
			throw e;
		} catch(Exception e) {
			logger.error("Exception occured in doRegister method");
			conn.rollback();
		} finally {
			DBUtils.closeConnection(conn);
		}*/
		// Enable this code for Local Data Base - End
		
		//Enable this code for sending Address xml - Start
		com.sp3.mvc.jaxb.Address jaxbCustAddr = JaxbHelper.getJaxbAddress(customerAddr, customer);
		String textMessage = getMarshalledString(jaxbCustAddr);
		textMessage = textMessage.replaceAll("ns2:", "");
		textMessage = textMessage.replaceAll("ns3:", "");
		textMessage = textMessage.replaceAll("ns4:", "");
		textMessage = textMessage.replaceAll("ns5:", "");
		
		String exchangeName = myProps.getProperty("exchange.name");
		String qName = myProps.getProperty("registration.qname");
		String ipAddress = myProps.getProperty("ip.address");
		AMQPMessageHelper helper = new AMQPMessageHelper();
		helper.sendMessage(textMessage,exchangeName, qName,ipAddress);
		//Enable this code for sending Address xml - End
		
		logger.debug("Registration record inserted successfully...");
		
		return "success";
	}
	
	@RequestMapping(value="/backtoindex", method=RequestMethod.POST)
	public String doBack() {
		logger.debug("Inside doBack method...");
		return "welcomePage";
	}
	
	private String getMarshalledString(com.sp3.mvc.jaxb.Address jaxbAddr) {
		
		final StringWriter out = new StringWriter();
		jaxb2Marshaller.marshal(jaxbAddr, new StreamResult(out));
		String textMessage = out.toString();
		
		return textMessage;
	}

}
