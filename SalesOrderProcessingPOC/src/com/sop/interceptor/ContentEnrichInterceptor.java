package com.sop.interceptor;

import static java.lang.String.format;

import java.io.StringReader;
import java.io.StringWriter;

import javax.management.Attribute;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.channel.ChannelInterceptor;
import org.springframework.integration.support.MessageBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.xml.internal.stream.writers.XMLStreamWriterImpl;

public class ContentEnrichInterceptor implements ChannelInterceptor{

	@Override
	public Message<?> postReceive(Message<?> arg0, MessageChannel arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void postSend(Message<?> arg0, MessageChannel arg1, boolean arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preReceive(MessageChannel arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Message<?> preSend(Message<?> arg0, MessageChannel arg1) {
		// TODO Auto-generated method stub
		String payLoadXML="";
		String payloadType = arg0.getPayload().getClass().getName();
		if(payloadType.startsWith("[B"))
			payLoadXML = new String((byte[])arg0.getPayload());
		else
			payLoadXML = arg0.getPayload().toString();
		InputSource is = new InputSource(new StringReader(payLoadXML));
		//System.out.println("Payload String in transformer: "+payLoadStr);
		Message<?> outMsg=null;
		
		try{
			/*XMLInputFactory xof = XMLInputFactory.newFactory();
			XMLStreamReader reader = xof.createXMLStreamReader(is);*/
		Document doc =DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		String orderId = xpath.evaluate("/order/@id", doc);
		String orderDate = xpath.evaluate("/order/orderdate", doc);
		String orderStatus = xpath.evaluate("/order/status", doc);
		
		String userId = xpath.evaluate("/order/address/customer/userid", doc);
		NodeList nodeLst = doc.getElementsByTagName("orderitem");
		NodeList AddnodeLst =doc.getElementsByTagName("address");
		NodeList prodLst = doc.getElementsByTagName("product");
		for(int n=0; n<nodeLst.getLength(); n++){
			Node node= nodeLst.item(n);
			Element e = (Element)node;
			if(node.getNodeName().equals("orderitem")){
				//String itemid = xpath.evaluate("/orderitem/@id", node);
				Element orderIdElement = doc.createElement("orderid");
				Element productIdElement = doc.createElement("id");
				orderIdElement.setTextContent(orderId);
				node.appendChild(orderIdElement);
				Node nItemId = node.getAttributes().getNamedItem("id");
				Element orderItemIdElement = doc.createElement("id");
				//orderItemIdElement.setNodeValue(nItemId.getNodeValue());
				orderItemIdElement.setTextContent(nItemId.getNodeValue());
				e.appendChild(orderItemIdElement);
				Element addEle = doc.createElement("addid");
				String addId = AddnodeLst.item(0).getAttributes().getNamedItem("id").getNodeValue();
				String prodId = prodLst.item(n).getAttributes().getNamedItem("id").getNodeValue();
				productIdElement.setTextContent(prodId);
				addEle.setTextContent(addId);
				//addEle.setAttribute("id",AddnodeLst.item(0).getAttributes().getNamedItem("id").getNodeValue());
				//System.out.println("Address Id:" +AddnodeLst.item(0).getAttributes().getNamedItem("id").getNodeValue());
				NodeList chldNodeLst =  AddnodeLst.item(0).getChildNodes();
				int nodeLen = chldNodeLst.getLength();
				
				Element orderDateEle = doc.createElement("orderdate");
				orderDateEle.setTextContent(orderDate);
				
				Element useridEl = doc.createElement("userid");
				useridEl.setTextContent(userId);
				node.appendChild(addEle);
				prodLst.item(n).appendChild(productIdElement);
				node.appendChild(useridEl);
				node.appendChild(orderDateEle);
				//e.setAttribute("orderid", orderId);
				//e.setAttribute("itemid", itemid);
			}
			
		}
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		String payloadOutput = writer.getBuffer().toString().replaceAll("\n|\r", "");
		outMsg = MessageBuilder.withPayload(payloadOutput).copyHeaders(arg0.getHeaders()).setHeaderIfAbsent("orderstatus", orderStatus).build();
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
			return outMsg;
	}

}
