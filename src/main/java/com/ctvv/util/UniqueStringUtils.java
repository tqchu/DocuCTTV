package com.ctvv.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class UniqueStringUtils {
	public static String randomOrderId(){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuMMdd");
		LocalDate localDate = LocalDate.now();
		String strDate = dtf.format(localDate);
		char[] random = new char[8];
		char[] chars = "ABSDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
		Random r = new Random();
		for (int i = 0;  i < 8;  i++) {
			random[i] = chars[r.nextInt(chars.length)];
		}
		String strRandom = new String(random);
		return new String(strDate + strRandom);
	}
}
