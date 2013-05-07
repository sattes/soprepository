package com.sop;

import com.sop.request.Orderitem;
import com.sop.request.OrderItemProcessRequest;
import com.sop.request.Product;
import com.sop.response.OrderItemProcessResponse;

public class OrderProcessImpl implements OrderProcess {

	@Override
	public OrderItemProcessResponse orderProcess(OrderItemProcessRequest request) {
		// TODO Auto-generated method stub
		System.out.println("in orderProcess method");
		OrderItemProcessResponse response = new OrderItemProcessResponse();
		com.sop.response.OrderItem ordItem =new  com.sop.response.OrderItem();
		Orderitem orderItem = request.getOrderitem();;
		Product prod= orderItem.getProduct();
		ordItem.setItemId(orderItem.getId());
		ordItem.setOrderId(orderItem.getOrderid());
		ordItem.setProdId(prod.getId());
		ordItem.setUserid(orderItem.getUserid());
		ordItem.setAddid(orderItem.getAddid());
		ordItem.setOrderdate(orderItem.getOrderdate());
		response.setOrderstatus(request.getOrderstatus());
		//com.sop.response.Address resAddress = ordItem.getAddress();
		//setAddress(orderItem.getAddress(), resAddress);
		//ordItem.setAddress(resAddress);
	
		try{
			
			
			int quantity= orderItem.getQuantity();
			System.out.println("quantity:" + quantity);
			double unitcost= prod.getUnitcost();
			double discPercent= orderItem.getDiscount().getDiscpercent();
		
			double orderItemTotal= quantity * unitcost;
			double discount = orderItemTotal * ( discPercent / 100);
			orderItemTotal = orderItemTotal - discount;
			
			System.out.println("orderItemTotal:" + orderItemTotal);
			ordItem.setStatus("Success");
			response.setOrderItemTotal(orderItemTotal);
			ordItem.setQuantity(quantity);
			ordItem.setUnitcost(unitcost);
			response.setOrderItem(ordItem);
			return response;
		} catch(Exception e) {
			System.out.println("Exception:" + e);
			ordItem.setStatus("Failed");
			response.setOrderItem(ordItem);
			return response;
		}
	}
	
	/*private void setAddress(com.sop.request.Address reqAddress, com.sop.response.Address resAddress) {
		resAddress.setAddr1(reqAddress.getAddr1());
		resAddress.setAddr2(reqAddress.getAddr2());
		resAddress.setAddtype(reqAddress.getAddtype());
		resAddress.setCity(reqAddress.getCity());
		resAddress.setId(reqAddress.getId());
		resAddress.setCountry(reqAddress.getCountry());
		resAddress.setPhone(reqAddress.getPhone());
		resAddress.setState(reqAddress.getState());
		resAddress.setZip(reqAddress.getZip());
		com.sop.request.Customer reqCust = reqAddress.getCustomer();
		com.sop.response.Customer resCust = resAddress.getCustomer();
		resCust.setCusttype(reqCust.getCusttype());
		resCust.setEmail(reqCust.getEmail());
		resCust.setFirstname(reqCust.getFirstname());
		resCust.setLastname(reqCust.getLastname());
		resCust.setPassword(reqCust.getPassword());
		resCust.setStatus(reqCust.getStatus());
		resCust.setUserid(reqCust.getUserid());
		resAddress.setCustomer(resCust);
		
	}*/

}
