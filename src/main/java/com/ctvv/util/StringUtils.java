package com.ctvv.util;

public class StringUtils {
	public static String phoneNumberBeginWithZero(String phoneNumber){
		if (phoneNumber.startsWith("84")){
			return "0"+phoneNumber.substring(2);
		}
		else if(phoneNumber.startsWith("+84")){
			return "0"+phoneNumber.substring(3);
		}
		else return phoneNumber;
	}
	public static String regionPhoneNumber(String phoneNumber){
		if (phoneNumber.startsWith("0")){
			return "+84" + phoneNumber.substring(1);
		}
		else if (phoneNumber.startsWith("84")){
			return "+"+phoneNumber;
		}
		else{
			return phoneNumber;
		}

	}

}
