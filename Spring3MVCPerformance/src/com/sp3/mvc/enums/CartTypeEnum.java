package com.sp3.mvc.enums;

public enum CartTypeEnum {
	
	DEBITCARD("DEBITCARD"),
	CREDITCARD("CREDITCARD");
	
	String cardType;
	
	CartTypeEnum(String cardType) {
		this.cardType = cardType;
	}
	
	public String getValue() {
		return cardType;
	}
	
	public static CartTypeEnum getEnumByValue(String cardType) {
		for(CartTypeEnum val : CartTypeEnum.values()) {
			if(val.toString().equals(cardType)){
				return val;
			}
		}
        return null;
    }

}
