//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.08 at 05:14:56 PM IST 
//


package com.sp3.mvc.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element name="disctype">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="REGULAR"/>
 *               &lt;enumeration value="PREFERRED"/>
 *               &lt;enumeration value="CORPORATE"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="discpercent" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "disctype",
    "discpercent"
})
@XmlRootElement(name = "discount")
public class Discount {

    @XmlElement(namespace = "Order", required = true)
    protected String disctype;
    @XmlElement(namespace = "Order")
    protected double discpercent;
    @XmlAttribute(required = true)
    protected int id;

    /**
     * Gets the value of the disctype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisctype() {
        return disctype;
    }

    /**
     * Sets the value of the disctype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisctype(String value) {
        this.disctype = value;
    }

    /**
     * Gets the value of the discpercent property.
     * 
     */
    public double getDiscpercent() {
        return discpercent;
    }

    /**
     * Sets the value of the discpercent property.
     * 
     */
    public void setDiscpercent(double value) {
        this.discpercent = value;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

}