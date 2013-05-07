package com.sop;

import com.sop.request.OrderItemProcessRequest;
import com.sop.response.OrderItemProcessResponse;

public interface OrderProcess {
	public OrderItemProcessResponse orderProcess(OrderItemProcessRequest request) ;

}
