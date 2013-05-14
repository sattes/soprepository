package com.emc.sqlfire.sop.domain;

import java.io.Serializable;
import java.util.Date;

public class CardInfo implements Serializable {
	
	private Long cardNumber;
	private String nameOnCard;
	private Date expiryDate;
	private String cardType;
	private String cardGatewayType;

	
	public Long getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(Long cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getNameOnCard() {
		return nameOnCard;
	}
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardGatewayType() {
		return cardGatewayType;
	}
	public void setCardGatewayType(String cardGatewayType) {
		this.cardGatewayType = cardGatewayType;
	}
	

	

}
