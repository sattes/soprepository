package com.sop.message.transform;

import java.math.BigInteger;
import java.util.Date;

public class CardInfo {

	private String cardInfoId;
	private BigInteger cardNumber;
	private String nameOnCard;
	private Date expDate;
	private String cardType;
	private String cardGatewayType;
	private int cardcvvNumber;
	public int getCardcvvNumber() {
		return cardcvvNumber;
	}
	public void setCardcvvNumber(int cardcvvNumber) {
		this.cardcvvNumber = cardcvvNumber;
	}
	public String getCardInfoId() {
		return cardInfoId;
	}
	public void setCardInfoId(String cardInfoId) {
		this.cardInfoId = cardInfoId;
	}
	public BigInteger getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(BigInteger cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getNameOnCard() {
		return nameOnCard;
	}
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	public Date getExpDate() {
		return expDate;
	}
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
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
