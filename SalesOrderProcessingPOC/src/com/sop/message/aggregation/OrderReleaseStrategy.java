package com.sop.message.aggregation;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.springframework.integration.Message;

import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.store.MessageGroup;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class OrderReleaseStrategy implements ReleaseStrategy {

	@Override
	public boolean canRelease(MessageGroup arg0) {
		int sqSize = arg0.getSequenceSize();
		boolean flg = arg0.isComplete();
		//System.out.println("SeqSize and Flg"+sqSize+":"+flg);
		//System.out.println("CorrelationKey-JMSMessageId: "+arg0.getOne().getHeaders().get("correlationId"));
		return flg;
	}

	

}
