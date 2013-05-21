package com.sp3.mvc.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.sp3.mvc.dao.AddressDao;
import com.sp3.mvc.dao.CategoryDao;
import com.sp3.mvc.dao.CustomerDao;
import com.sp3.mvc.dao.DiscountDao;
import com.sp3.mvc.dao.OrderDao;
import com.sp3.mvc.dao.OrderItemDao;
import com.sp3.mvc.dao.ProductDao;
import com.sp3.mvc.enums.AddressTypeEnum;
import com.sp3.mvc.enums.OrderStatusEnum;
import com.sp3.mvc.helper.AMQPMessageHelper;
import com.sp3.mvc.helper.DateUtils;
import com.sp3.mvc.helper.MessageHelper;
import com.sp3.mvc.models.Address;
import com.sp3.mvc.models.Category;
import com.sp3.mvc.models.Customer;
import com.sp3.mvc.models.Discount;
import com.sp3.mvc.models.Order;
import com.sp3.mvc.models.OrderItem;
import com.sp3.mvc.models.Product;

@Controller
@SessionAttributes("caps")
public class OrderController {
	
	private static Logger logger = Logger.getLogger(OrderController.class);
	
	@Resource(name = "myProps")
	private Properties myProps;
	
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
	
	private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }
	
	@RequestMapping(value="/submitcart", method = RequestMethod.POST)
	public String submitCart(@ModelAttribute("order")Order order, Model model, HttpServletRequest request, @RequestParam String viewcart) throws SQLException, ClassNotFoundException, FileNotFoundException {
		logger.debug("Inside OrderController::submitCart method...");
		logger.debug("Request Param viewcart - "+viewcart);
		logger.debug("Shipping Address - "+order.getShippingAddress());
		if(viewcart.equals("SubmitOrder")) {
			return doSubmitCart(order,request);
		} else if(viewcart.equals("Back")) {
			return doBack(request);
		} else {
			return null;
		}
	}
	
	private String doBack(HttpServletRequest request) {
		logger.debug("Inside OrderController::doBack method...");
		
		//List<Product> selectedList = (ArrayList<Product>)request.getSession().getAttribute("selectedProducts");
		
		return "viewProducts";
	}
	
	public String doSubmitCart(Order order, HttpServletRequest request) throws SQLException, ClassNotFoundException {
		logger.debug("Inside OrderController::doSubmitCart method...");
		List<Product> selectedProductList = (ArrayList<Product>)request.getSession().getAttribute("selectedProducts");
		Customer login = (Customer)request.getSession().getAttribute("login");
		
		logger.debug("getEmail "+login.getEmail());
		logger.debug("getFname "+login.getFname());
		logger.debug("getCustType "+login.getCustType());
		logger.debug("getUserName "+login.getUserName());
		
		Integer itemId = itemDao.getMaxItemId();
		Integer addrId = addrDao.getMaxAddressId();
		Integer orderId = ordDao.getMaxOrderId();
		
		String maxOrderId = null;


		if(orderId == null) {

		maxOrderId = "ORDER-100";

		} else {

		maxOrderId = "ORDER-"+(orderId+1);

		}

		logger.debug("itemId - "+itemId);
		logger.debug("addrId - "+addrId);
		logger.debug("OrderId - "+orderId);
		
		Address addr = addrDao.getAddressByUserId(login.getUserName());
		
		order.setAddressId(addr.getAddressId());
		order.setOrderDate(new Date());
		order.setOrderId(maxOrderId);
		order.setStatus(OrderStatusEnum.ORDERED);
		order.setUserId(login.getUserName());
		order.setTotalPrice(0.0d);
		order.getShippingAddress().setAddressId(addrId+1);
		order.getShippingAddress().setAddressType(AddressTypeEnum.SHIPPING);
		order.getShippingAddress().setUserId(login.getUserName());
		
		com.sp3.mvc.jaxb.Order jaxbOrder = new com.sp3.mvc.jaxb.Order();
		
		for(Product selectedProd : selectedProductList){
			itemId = itemId + 1;
			logger.debug("ProductId - "+selectedProd.getProductId()+", Category - "+selectedProd.getCategory()+", Quantity - "+selectedProd.getQuantity()+", UnitPrice - "+selectedProd.getUnitCost());
			OrderItem item  = new OrderItem();
			item.setItemId(itemId);
			//item.setListPrice(listPrice);
			item.setOrderId(order.getOrderId());
			item.setProductId(selectedProd.getProductId());
			item.setQuantity(selectedProd.getQuantity());
			item.setStatus("Available");
			item.setUnitPrice(selectedProd.getUnitCost());
			order.getOrderItemList().add(item);
			
			com.sp3.mvc.jaxb.Orderitem jaxbItem = new com.sp3.mvc.jaxb.Orderitem();
			com.sp3.mvc.jaxb.Discount disc = new com.sp3.mvc.jaxb.Discount();
			Discount discount = discDao.getDiscountByCustType(login.getCustType().toString());
			disc.setDiscpercent(discount.getDiscPercent());
			disc.setDisctype(discount.getDiscType());
			disc.setId(discount.getDiscId());
			
			com.sp3.mvc.jaxb.Product prod = new com.sp3.mvc.jaxb.Product();
			
			com.sp3.mvc.jaxb.Category cat = new com.sp3.mvc.jaxb.Category();
			Category category = catDao.getCategoryByCatId(selectedProd.getCategory());
			cat.setId(category.getCatId());
			cat.setDesc(category.getDescription());
			cat.setName(category.getName());
			prod.setCategory(cat);
			
			Product product = prodDao.getProductByProdId(selectedProd.getProductId());
			prod.setDesc(product.getDescription());
			prod.setName(product.getName());
			prod.setId(product.getProductId());
			prod.setUnitcost(product.getUnitCost());
			
			jaxbItem.setDiscount(disc);
			jaxbItem.setId(itemId);
			jaxbItem.setQuantity(selectedProd.getQuantity());
			
			jaxbItem.setProduct(prod);
			jaxbOrder.getOrderitem().add(jaxbItem);
		}
		
		jaxbOrder.setId(order.getOrderId());
		jaxbOrder.setStatus(order.getStatus().toString());
		jaxbOrder.setTotalprice(0.0f);
		jaxbOrder.setOrderdate(DateUtils.getFormattedDateStr(order.getOrderDate()));
		
		com.sp3.mvc.jaxb.Address shippingAddress = new com.sp3.mvc.jaxb.Address();
		shippingAddress.setAddr1(order.getShippingAddress().getAddress1());
		shippingAddress.setAddr2(order.getShippingAddress().getAddress2());
		shippingAddress.setAddtype(order.getShippingAddress().getAddressType().toString());
		shippingAddress.setCity(order.getShippingAddress().getCity());
		shippingAddress.setCountry(order.getShippingAddress().getCountry());
		shippingAddress.setId(order.getShippingAddress().getAddressId());
		shippingAddress.setState(order.getShippingAddress().getState());
		shippingAddress.setZip(order.getShippingAddress().getZip());
		shippingAddress.setPhone(order.getShippingAddress().getPhone());
		
		com.sp3.mvc.jaxb.Customer customer = new com.sp3.mvc.jaxb.Customer();
		
		Customer cust = custDao.getCustomerByUserId(order.getUserId());
		customer.setUserid(cust.getUserName());
		customer.setStatus(cust.getStatus().toString());
		customer.setCusttype(cust.getCustType().toString());
		customer.setEmail(cust.getEmail());
		customer.setFirstname(cust.getFname());
		customer.setLastname(cust.getLname());
		customer.setPassword(cust.getPassword());
		
		shippingAddress.setCustomer(customer);
		jaxbOrder.setAddress(shippingAddress);
		String textMessage = getMarshalledString(jaxbOrder);
		textMessage = textMessage.replaceAll("ns2:", "");
		textMessage = textMessage.replaceAll("ns3:", "");
		textMessage = textMessage.replaceAll("ns4:", "");
		textMessage = textMessage.replaceAll("ns5:", "");
		textMessage = textMessage.replaceAll("ns6:", "");
		textMessage = textMessage.replaceAll("ns7:", "");
		textMessage = textMessage.replaceAll("ns8:", "");
		logger.debug("Message:" + textMessage);
		
		String exchangeName = myProps.getProperty("exchange.name");
		String qName = myProps.getProperty("order.qname");
		String ipAddress = myProps.getProperty("ip.address");
		AMQPMessageHelper helper = new AMQPMessageHelper();
		helper.sendMessage(textMessage,exchangeName, qName,ipAddress);
		
		request.getSession().setAttribute("orderId", maxOrderId);
		return "orderInfo";
	}
	
	/*private com.sp3.mvc.jaxb.Order getJaxbOrder(List<Product> selectedProductList, Order order, String custType) {
		Integer itemId = itemDao.getMaxItemId();
		com.sp3.mvc.jaxb.Order jaxbOrder = new com.sp3.mvc.jaxb.Order();
		for(Product selectedProd : selectedProductList){
			itemId = itemId + 1;
			logger.debug("ProductId - "+selectedProd.getProductId()+", Category - "+selectedProd.getCategory()+", Quantity - "+selectedProd.getQuantity()+", UnitPrice - "+selectedProd.getUnitCost());
			OrderItem item  = new OrderItem();
			item.setItemId(itemId);
			//item.setListPrice(listPrice);
			item.setOrderId(order.getOrderId());
			item.setProductId(selectedProd.getProductId());
			item.setQuantity(selectedProd.getQuantity());
			item.setStatus("Available");
			item.setUnitPrice(selectedProd.getUnitCost());
			order.getOrderItemList().add(item);
			
			com.sp3.mvc.jaxb.Orderitem jaxbItem = new com.sp3.mvc.jaxb.Orderitem();
			com.sp3.mvc.jaxb.Discount disc = new com.sp3.mvc.jaxb.Discount();
			Discount discount = discDao.getDiscountByCustType(custType);
			disc.setDiscpercent(discount.getDiscPercent());
			disc.setDisctype(discount.getDiscType());
			disc.setId(discount.getDiscId());
			
			com.sp3.mvc.jaxb.Product prod = new com.sp3.mvc.jaxb.Product();
			
			com.sp3.mvc.jaxb.Category cat = new com.sp3.mvc.jaxb.Category();
			Category category = catDao.getCategoryByCatId(selectedProd.getCategory());
			cat.setId(category.getCatId());
			cat.setDesc(category.getDescription());
			cat.setName(category.getName());
			prod.setCategory(cat);
			
			Product product = prodDao.getProductByProdId(selectedProd.getProductId());
			prod.setDesc(product.getDescription());
			prod.setName(product.getName());
			prod.setId(product.getProductId());
			prod.setUnitcost(product.getUnitCost());
			
			jaxbItem.setDiscount(disc);
			jaxbItem.setId(itemId);
			jaxbItem.setQuantity(selectedProd.getQuantity());
			
			jaxbItem.setProduct(prod);
			jaxbOrder.getOrderitem().add(jaxbItem);
		}
		
		jaxbOrder.setId(order.getOrderId());
		jaxbOrder.setStatus(order.getStatus().toString());
		jaxbOrder.setTotalprice(0.0f);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date d = order.getOrderDate();
		String dateStr = sdf.format(d);
		
		jaxbOrder.setOrderdate(dateStr);
		
		com.sp3.mvc.jaxb.Address shippingAddress = new com.sp3.mvc.jaxb.Address();
		shippingAddress.setAddr1(order.getShippingAddress().getAddress1());
		shippingAddress.setAddr2(order.getShippingAddress().getAddress2());
		shippingAddress.setAddtype(order.getShippingAddress().getAddressType().toString());
		shippingAddress.setCity(order.getShippingAddress().getCity());
		shippingAddress.setCountry(order.getShippingAddress().getCountry());
		shippingAddress.setId(order.getShippingAddress().getAddressId());
		shippingAddress.setState(order.getShippingAddress().getState());
		shippingAddress.setZip(order.getShippingAddress().getZip());
		shippingAddress.setPhone(order.getShippingAddress().getPhone());
		
		com.sp3.mvc.jaxb.Customer customer = new com.sp3.mvc.jaxb.Customer();
		
		Customer cust = custDao.getCustomerByUserId(order.getUserId());
		customer.setUserid(cust.getUserName());
		customer.setStatus(cust.getStatus().toString());
		customer.setCusttype(cust.getCustType().toString());
		customer.setEmail(cust.getEmail());
		customer.setFirstname(cust.getFname());
		customer.setLastname(cust.getLname());
		customer.setPassword(cust.getPassword());
		
		shippingAddress.setCustomer(customer);
		jaxbOrder.setAddress(shippingAddress);
		return jaxbOrder;
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

}
