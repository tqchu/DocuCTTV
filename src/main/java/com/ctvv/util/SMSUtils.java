package com.ctvv.util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.util.Properties;

public class SMSUtils {
	public static final int otpTime = 60*10;
	public static void send(String toNumber, String content) {
		Properties appProps = PropertiesUtil.get("config.properties");

		String accountSID = appProps.getProperty("twilio.sid");
		String authToken = appProps.getProperty("twilio.authToken");
		Twilio.init(accountSID, authToken);
		Message.creator(new PhoneNumber(toNumber),
				new PhoneNumber("+19129375742"),
				content).create();
	}
}