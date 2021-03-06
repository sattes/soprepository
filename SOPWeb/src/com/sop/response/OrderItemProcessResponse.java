//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-646 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.03 at 03:46:32 PM IST 
//


package com.sop.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{Response}OrderItem"/>
 *         &lt;element name="OrderItemTotal" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="orderstatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "orderItem",
    "orderItemTotal",
    "orderstatus"
})
@XmlRootElement(name = "OrderItemProcessResponse")
public class OrderItemProcessResponse {

    @XmlElement(name = "OrderItem", required = true)
    protected OrderItem orderItem;
    @XmlElement(name = "OrderItemTotal")
    protected double orderItemTotal;
    @XmlElement(required = true)
    protected String orderstatus;

    /**
     * Gets the value of the orderItem property.
     * 
     * @return
     *     possible object is
     *     {@link OrderItem }
     *     
     */
    public OrderItem getOrderItem() {
        return orderItem;
    }

    /**
     * Sets the value of the orderItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderItem }
     *     
     */
    public void setOrderItem(OrderItem value) {
        this.orderItem = value;
    }

    /**
     * Gets the value of the orderItemTotal property.
     * 
     */
    public double getOrderItemTotal() {
        return orderItemTotal;
    }

    /**
     * Sets the value of the orderItemTotal property.
     * 
     */
    public void setOrderItemTotal(double value) {
        this.orderItemTotal = value;
    }

    /**
     * Gets the value of the orderstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderstatus() {
        return orderstatus;
    }

    /**
     * Sets the value of the orderstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderstatus(String value) {
        this.orderstatus = value;
    }

}
