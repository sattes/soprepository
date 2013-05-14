package com.sp3.mvc.controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

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
import com.sp3.mvc.dao.CategoryDao;
import com.sp3.mvc.dao.CustomerDao;
import com.sp3.mvc.dao.DiscountDao;
import com.sp3.mvc.dao.OrderDao;
import com.sp3.mvc.dao.OrderItemDao;
import com.sp3.mvc.dao.ProductDao;
import com.sp3.mvc.enums.AddressTypeEnum;
import com.sp3.mvc.enums.OrderStatusEnum;
import com.sp3.mvc.helper.DateUtils;
import com.sp3.mvc.helper.MessageHelper;
import com.sp3.mvc.models.Address;
import com.sp3.mvc.models.Category;
import com.sp3.mvc.models.Customer;
import com.sp3.mvc.models.Discount;
import com.sp3.mvc.models.Login;
import com.sp3.mvc.models.Product;

@Controller
//@RequestMapping("/hello")
public class HelloController {
	
	private static Logger logger = Logger.getLogger(HelloController.class);
	
	private static final String XML_FILE_NAME = "C:\\Test\\order.xml";
	
	private static final String NEW_XML_FILE_NAME = "C:\\Test\\orderNew.xml";
	
	private static final String NEW_XML_FILE_PATH = "C:\\Test\\";	
	
	private static final String NEW_XML_FILE_NAME1 = "order-X";
	
	private static final String NEW_XML_FILE_TYPE = "xml";
	
	
	private Marshaller marshaller;
	
	@Resource(name = "ordDao")
	private OrderDao ordDao;
	
	@Resource(name = "itemDao")
	private OrderItemDao itemDao;
	
	@Resource(name = "addrDao")
	private AddressDao addrDao;
	
	@Resource(name = "custDao")
	private CustomerDao custDao;
	
	@Resource(name = "prodDao")
	private ProductDao prodDao;
	
	@Resource(name = "catDao")
	private CategoryDao catDao;
	
	@Resource(name = "discDao")
	private DiscountDao discDao;
	
	public Marshaller getMarshaller() {
		return marshaller;
	}

	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	private Unmarshaller unmarshaller;

	public Unmarshaller getUnmarshaller() {
		return unmarshaller;
	}

	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}

	@RequestMapping("/index")
	public String sayHello(Model model) {
		logger.debug("Inside sayHello method...");
		model.addAttribute("message", "Welcome to SalesOrderProcessingSystem");
		return "welcomePage";
	}
	
	@RequestMapping("/xmlRead")
	public String readXML(Model model, @RequestParam("xmlMsgCount") Integer count) {
		logger.debug("Inside readXML method...");
		model.addAttribute("message", "Welcome to SalesOrderProcessingSystem");
		System.out.println("File count: "+count);
		try {
			com.sp3.mvc.jaxb.Order jaxbOrder = (com.sp3.mvc.jaxb.Order)convertFromXMLToObject(XML_FILE_NAME);
			System.out.println("UserId from order xml: "+jaxbOrder.getAddress().getCustomer().getUserid());
			try {
				for(int i=0; i<=count-1; i++){
					writeXML(jaxbOrder, i);
				}
				
			} catch (ClassNotFoundException e) {
				System.out.println("readXML:ClassNotFoundException: "+e);
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("readXML:SQLException: "+e);
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.out.println("IOException occured");
			e.printStackTrace();
		}
		return "welcomePage";
	}
	
	public Object convertFromXMLToObject(String xmlfile) throws IOException {
		FileInputStream is = null;
		try {
			is = new FileInputStream(xmlfile);
			return getUnmarshaller().unmarshal(new StreamSource(is));
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}
	
	/*public void convertFromObjectToXML(com.sp3.mvc.jaxb.Order jaxbOrder, String filepath)
			throws IOException {
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(filepath);
			getMarshaller().marshal(jaxbOrder, new StreamResult(os));
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}*/
	
	private String getMarshalledString(com.sp3.mvc.jaxb.Order jaxbOrder) {
		String textMessage = null;
		
		try {
			final StringWriter out = new StringWriter();
			marshaller.marshal(jaxbOrder, new StreamResult(out));
			textMessage = out.toString();
		} catch (IOException e) {
			logger.error("IOException while marshalling Order object ", e);
		} 
		
		return textMessage;
	}
	
	public void writeXML(com.sp3.mvc.jaxb.Order order, int fileExt) throws ClassNotFoundException, SQLException {
		
//		Integer itemId = order.getOrderitem().get(0).getId();
//		Integer addrId = order.getAddress().getId();
//		String orderId = order.getId();
		
		Integer itemId = itemDao.getMaxItemId();
		Integer addrId = addrDao.getMaxAddressId();
		Integer orderId = ordDao.getMaxOrderId();
		String maxOrderId = null;
		
		itemId = itemId +fileExt+1;
		addrId = addrId +fileExt+1;
		
		if(orderId == null) {
			maxOrderId = "ORDER-100";
		} else {
			maxOrderId = "ORDER-"+(orderId+fileExt+1);
		}
		logger.debug("itemId - "+itemId);
		logger.debug("addrId - "+addrId);
		logger.debug("OrderId - "+orderId);
		
		
		com.sp3.mvc.jaxb.Order jaxbOrder4xml = new com.sp3.mvc.jaxb.Order();
		
		com.sp3.mvc.jaxb.Orderitem jaxbItem = new com.sp3.mvc.jaxb.Orderitem();
		com.sp3.mvc.jaxb.Discount disc = new com.sp3.mvc.jaxb.Discount();
//		Discount discount = discDao.getDiscountByCustType(order.getAddress().getCustomer().getCusttype().toString());
		disc.setDiscpercent(order.getOrderitem().get(0).getDiscount().getDiscpercent());
		disc.setDisctype(order.getOrderitem().get(0).getDiscount().getDisctype());
		disc.setId(order.getOrderitem().get(0).getDiscount().getId());
		
		com.sp3.mvc.jaxb.Product prod = new com.sp3.mvc.jaxb.Product();
		
		com.sp3.mvc.jaxb.Category cat = new com.sp3.mvc.jaxb.Category();
//		Category category = catDao.getCategoryByCatId(order.getOrderitem().get(0).getProduct().getCategory().getId());
		cat.setId(order.getOrderitem().get(0).getProduct().getCategory().getId());
		cat.setDesc(order.getOrderitem().get(0).getProduct().getCategory().getDesc());
		cat.setName(order.getOrderitem().get(0).getProduct().getCategory().getName());
		prod.setCategory(cat);
		
//		Product product = prodDao.getProductByProdId(order.getOrderitem().get(0).getProduct().getId());
		prod.setDesc(order.getOrderitem().get(0).getProduct().getDesc());
		prod.setName(order.getOrderitem().get(0).getProduct().getName());
		prod.setId(order.getOrderitem().get(0).getProduct().getId());
		prod.setUnitcost(order.getOrderitem().get(0).getProduct().getUnitcost());
		
		jaxbItem.setDiscount(disc);
		jaxbItem.setId(itemId);
		jaxbItem.setQuantity(order.getOrderitem().get(0).getQuantity());
		
		jaxbItem.setProduct(prod);
		jaxbOrder4xml.getOrderitem().add(jaxbItem);
		
		jaxbOrder4xml.setId(maxOrderId);
		jaxbOrder4xml.setStatus(order.getStatus().toString());
		jaxbOrder4xml.setTotalprice(0.0f);
		jaxbOrder4xml.setOrderdate(order.getOrderdate());
		
		com.sp3.mvc.jaxb.Address shippingAddress = new com.sp3.mvc.jaxb.Address();
		shippingAddress.setAddr1(order.getAddress().getAddr1());
		shippingAddress.setAddr2(order.getAddress().getAddr2());
		shippingAddress.setAddtype(order.getAddress().getAddtype().toString());
		shippingAddress.setCity(order.getAddress().getCity());
		shippingAddress.setCountry(order.getAddress().getCountry());
		shippingAddress.setId(addrId);
		shippingAddress.setState(order.getAddress().getState());
		shippingAddress.setZip(order.getAddress().getZip());
		shippingAddress.setPhone(order.getAddress().getPhone());
		
com.sp3.mvc.jaxb.Customer customer = new com.sp3.mvc.jaxb.Customer();
		
//		Customer cust = custDao.getCustomerByUserId(order.getAddress().getCustomer().getUserid());
		customer.setUserid(order.getAddress().getCustomer().getUserid());
		customer.setStatus(order.getAddress().getCustomer().getStatus().toString());
		customer.setCusttype(order.getAddress().getCustomer().getCusttype().toString());
		customer.setEmail(order.getAddress().getCustomer().getEmail());
		customer.setFirstname(order.getAddress().getCustomer().getFirstname());
		customer.setLastname(order.getAddress().getCustomer().getLastname());
		customer.setPassword(order.getAddress().getCustomer().getPassword());
		
		shippingAddress.setCustomer(customer);
		jaxbOrder4xml.setAddress(shippingAddress);
		
//		String textMessage;
		
//			String finalXMLName = NEW_XML_FILE_PATH + NEW_XML_FILE_NAME1 + fileExt +"." + NEW_XML_FILE_TYPE;
//			convertFromObjectToXML(jaxbOrder4xml, finalXMLName);
		String textMessage = getMarshalledString(jaxbOrder4xml);
			textMessage = textMessage.replaceAll("ns2:", "");
			textMessage = textMessage.replaceAll("ns3:", "");
			textMessage = textMessage.replaceAll("ns4:", "");
			textMessage = textMessage.replaceAll("ns5:", "");
			textMessage = textMessage.replaceAll("ns7:", "");
			logger.debug("Message:" + textMessage);
			
			MessageHelper msgHelper = new MessageHelper();
			msgHelper.sendMessage("sopInboundQueue", textMessage);
			
		 /*catch (IOException e) {
			System.out.println("IOException occurred while writing to new XML:"+e);
			e.printStackTrace();
		}*/
		
		
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
