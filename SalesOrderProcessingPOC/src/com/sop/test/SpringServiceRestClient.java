package com.sop.test;





import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import com.sop.message.transform.Order;
@Component
public class SpringServiceRestClient {
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	RestTemplate restTemplate;

	
    public void fetchRESTObject() throws RestClientException,Exception{
    	//List<HttpMessageConverter<?>> defaultConverters =    restTemplate.getMessageConverters();
    		//defaultConverters.add(new FormHttpMessageConverter());
    		//HttpMessageConverter<?>[] converters = new HttpMessageConverter[defaultConverters.size()];
    		//restTemplate.setMessageConverters(Arrays.asList( defaultConverters.toArray(converters)));

    		

        

    	
    }

    public static void main(String[] args){
    	ApplicationContext context = new FileSystemXmlApplicationContext(
    	"C:/enterprise-integration-1.5.0.RELEASE/newWorkSpace/SalesOrderProcessingPOC/src/SalesOrderProcessingFlow.xml");
    	 RestTemplate restTemplate = context.getBean("restTemplate", RestTemplate.class);
    	try{
    		String url = "http://localhost:8081/SalesOrderProcessingRESTService/sopProvider/order/{orderid}.xml";
            Order order = (Order) restTemplate.getForObject(url, Order.class, "Karun11022092");
            System.out.println("order - id:" + order.getId()+", order - status:"+order.getStatus());
             url = "http://localhost:8081/SalesOrderProcessingRESTService/sopProvider/orderUpdate/{orderid}/{status}.xml";
              Order order1 = (Order) restTemplate.getForObject(url, Order.class, "Karun11022092","Shipped");
              System.out.println("order - id:" + order1.getId()+", order - status:"+order1.getStatus()+"UpdateFlag: "+order1.isUpdated());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

}
