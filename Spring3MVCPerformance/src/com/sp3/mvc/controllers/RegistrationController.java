package com.sp3.mvc.controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.Resource;
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

import com.sp3.mvc.dao.AddressDao;
import com.sp3.mvc.dao.CustomerDao;
import com.sp3.mvc.dao.DBUtils;
import com.sp3.mvc.enums.AddressTypeEnum;
import com.sp3.mvc.enums.CustomerStatusEnum;
import com.sp3.mvc.helper.MessageHelper;
import com.sp3.mvc.models.Address;
import com.sp3.mvc.models.Customer;
import com.sp3.mvc.service.RegistrationService;

@Controller
@RequestMapping("register.htm")
public class RegistrationController {
	
	private static Logger logger = Logger.getLogger(RegistrationController.class);
	
	/*@Resource(name = "myProps")
	private Properties myProps;*/
	
	@Resource(name = "custDao")
	private CustomerDao custDao;
	
	@Resource(name = "addrDao")
	private AddressDao addrDao;
	
	@Resource(name="jaxb2Marshaller")
	org.springframework.oxm.jaxb.Jaxb2Marshaller jaxb2Marshaller;
	
	private Marshaller marshal;
    private Unmarshaller unmarshal;

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

	@RequestMapping(/*value="/gotoregister",*/ method = RequestMethod.GET)
	public String goToRegisterPage(@ModelAttribute("registration")Customer registration, Model model) {
		//logger.debug("Inside goToIndexPage method..."+myProps);
		
		return "register";
	}
	
	/*@RequestMapping(method=RequestMethod.POST)
	public String register(@Valid Customer registration, BindingResult result, Model model) {
		logger.debug("Inside submit method...");
		
		logger.debug("First Name = "+registration.getFname());
		logger.debug("Last Name = "+registration.getLname());
		logger.debug("Gender = "+registration.getGender());
		logger.debug("Type of Address = "+registration.getTypeOfAddress());
		logger.debug("Address = "+registration.getAddress());
		logger.debug("email = "+registration.getEmail());
		logger.debug("Country = "+registration.getCountry());
		logger.debug("User Name = "+registration.getUserName());
		logger.debug("Password = "+registration.getPassword());
		
		//logger.debug("Selected Check Box = "+registration.getCb());
		
		model.addAttribute("fname", registration.getFname());
		model.addAttribute("lname", registration.getLname());
		model.addAttribute("gender", registration.getGender());
		model.addAttribute("typeOfAddress", registration.getTypeOfAddress());
		model.addAttribute("addr", registration.getAddress());
		model.addAttribute("email", registration.getEmail());
		model.addAttribute("country", registration.getCountry());
		model.addAttribute("uname", registration.getUserName());
		
	
		StringBuffer message = new StringBuffer();
		message.append("<p:customer><userid>")
		.append(registration.getUserName())
		.append("</userid><status>AC</status><password>")
		.append(registration.getPassword())
		.append("</password><lastname>")
		.append(registration.getLname())
		.append("</lastname><firstname>")
		.append(registration.getFname())
		.append("</firstname><email>")
		.append(registration.getEmail())
		.append("</email><custtype>")
		.append("REGULAR")
		.append("</custtype></p:customer>");
		
		String textMessage = message.toString();
		logger.debug("Message:" + textMessage);
		RegistrationMessageHelper regMessageHelper = new RegistrationMessageHelper();
		regMessageHelper.sendMessage(textMessage);
		
		logger.debug("Registration record inserted successfully...");
		
		return "success";
	}*/
	
	@RequestMapping(/*value="/register",*/ method=RequestMethod.POST)
	public String register(@Valid Customer customer, BindingResult result, Model model, @RequestParam String register) throws SQLException, ClassNotFoundException {
		logger.debug("Inside register method...");
		
		logger.debug("Request Param register = "+register);
		
		if(register.equals("Register")) {
			return doRegister(customer, model);
		} else if(register.equals("Back")) {
			return doBack();
		} else {
			return null;
		}
	}
	
	private String doRegister(Customer customer, Model model) throws SQLException, ClassNotFoundException {
		logger.debug("Inside doRegister method...");
		logger.debug("First Name = "+customer.getFname());
		logger.debug("Last Name = "+customer.getLname());
		logger.debug("Gender = "+customer.getGender());
		logger.debug("Type of Address = "+customer.getTypeOfAddress());
		logger.debug("Address = "+customer.getAddress());
		logger.debug("email = "+customer.getEmail());
		logger.debug("Country = "+customer.getCountry());
		logger.debug("User Name = "+customer.getUserName());
		logger.debug("Password = "+customer.getPassword());
		logger.debug("Customer Address "+customer.getCustomerAddress().getAddress1());
		//logger.debug("Shipping Address = "+customer.getShippingAddress().getAddress1());
		logger.debug("Customer Type = "+customer.getCustType());
		
		//logger.debug("Selected Check Box = "+registration.getCb());
		
		model.addAttribute("fname", customer.getFname());
		model.addAttribute("lname", customer.getLname());
		model.addAttribute("gender", customer.getGender());
		model.addAttribute("typeOfAddress", customer.getTypeOfAddress());
		model.addAttribute("addr", customer.getAddress());
		model.addAttribute("email", customer.getEmail());
		model.addAttribute("country", customer.getCountry());
		model.addAttribute("uname", customer.getUserName());
		
		customer.setStatus(CustomerStatusEnum.ACTIVE);
		
		//CustomerDao custDao = new CustomerDao(myProps);
		//AddressDao addressDao = new AddressDao(myProps);
		Integer addrId = addrDao.getMaxAddressId();
		addrId = addrId + 1;
		
		Address customerAddr = customer.getCustomerAddress();
		customerAddr.setUserId(customer.getUserName());
		customerAddr.setAddressId(addrId);
		customerAddr.setAddressType(AddressTypeEnum.PERMANENT);
		
		/*addrId = addrId + 1;
		Address shippingAddr = customer.getShippingAddress();
		shippingAddr.setUserId(customer.getUserName());
		shippingAddr.setAddressId(addrId);
		shippingAddr.setAddressType(AddressTypeEnum.SHIPPING);*/
		// Enable this code for Local Data Base - Start
		/*Connection conn = null;
		try {
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			custDao.insertCustomer(conn,customer);
			addrDao.insertAddress(conn, customerAddr);
			conn.commit();
		} catch (SQLException e) {
			logger.error("SQLException got from CustomerDao in insertCustomer(customer)");
			conn.rollback();
			throw e;
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException got from CustomerDao in insertCustomer(customer)");
			throw e;
		} catch(Exception e) {
			logger.error("Exception occured in doRegister method");
			conn.rollback();
		} finally {
			DBUtils.closeConnection(conn);
		}*/
		// Enable this code for Local Data Base - End
		
		//Enable this code for sending Address xml - Start
		com.sp3.mvc.jaxb.Address jaxbCustAddr = getJaxbAddress(customerAddr, customer);
		String customerAddressStr = getMarshalledString(jaxbCustAddr);
		customerAddressStr = customerAddressStr.replaceAll("ns2:", "");
		customerAddressStr = customerAddressStr.replaceAll("ns3:", "");
		customerAddressStr = customerAddressStr.replaceAll("ns4:", "");
		customerAddressStr = customerAddressStr.replaceAll("ns5:", "");
		logger.debug("customerAddressStr:" + customerAddressStr);
		
		MessageHelper helper = new MessageHelper();
		helper.sendMessage("sopCustInbound", customerAddressStr);
		//Enable this code for sending Address xml - End
		
		logger.debug("Registration record inserted successfully...");
		
		return "success";
	}
	
	private String doBack() {
		logger.debug("Inside doBack method...");
		return "welcomePage";
	}
	
	private com.sp3.mvc.jaxb.Address getJaxbAddress(Address customerAddr, Customer customer) {
		com.sp3.mvc.jaxb.Address jaxbAddress = new com.sp3.mvc.jaxb.Address();
		jaxbAddress.setAddr1(customerAddr.getAddress1());
		jaxbAddress.setAddr2(customerAddr.getAddress2());
		jaxbAddress.setAddtype(customerAddr.getAddressType().toString());
		jaxbAddress.setCity(customerAddr.getCity());
		jaxbAddress.setCountry(customerAddr.getCountry());
		jaxbAddress.setId(customerAddr.getAddressId());
		jaxbAddress.setPhone(customerAddr.getPhone());
		jaxbAddress.setState(customerAddr.getState());
		jaxbAddress.setZip(customerAddr.getZip());
		jaxbAddress.setCustomer(getJaxbCustomer(customer));
		
		return jaxbAddress;
	}
	
	private com.sp3.mvc.jaxb.Customer getJaxbCustomer(Customer customer) {
		com.sp3.mvc.jaxb.Customer jaxbCustomer = new com.sp3.mvc.jaxb.Customer();
		jaxbCustomer.setCusttype(customer.getCustType().toString());
		jaxbCustomer.setEmail(customer.getEmail());
		jaxbCustomer.setFirstname(customer.getFname());
		jaxbCustomer.setLastname(customer.getLname());
		jaxbCustomer.setPassword(customer.getPassword());
		jaxbCustomer.setStatus(customer.getStatus().toString());
		jaxbCustomer.setUserid(customer.getUserName());
		
		return jaxbCustomer;
	}
	
	private String getMarshalledString(com.sp3.mvc.jaxb.Address jaxbAddr) {
		
		final StringWriter out = new StringWriter();
		jaxb2Marshaller.marshal(jaxbAddr, new StreamResult(out));
		String textMessage = out.toString();
		
		return textMessage;
	}

}
