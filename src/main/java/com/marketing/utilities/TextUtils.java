package com.marketing.utilities;

/**
 * This utility class will implement all out of the box String methods
 * 
 * @author Sumit Dang
 *
 */
public class TextUtils {

	public static String generatePhoneNumber(String serie, String number) {
		Integer zeroesNeeded = Constants.PHONE_LENGTH - serie.length() - number.length();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < zeroesNeeded; i++) {
			sb.append("0");
		}
		String zeroes = sb.toString();
		String phoneNumber = serie + zeroes + number;
		return phoneNumber;
	}
}
