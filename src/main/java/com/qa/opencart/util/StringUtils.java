package com.qa.opencart.util;

public class StringUtils {

	public static String getRandomEmailId() {
		String emailId = "vaibhavAuto" + System.currentTimeMillis() + "@opencart.com";
		System.out.println("User Email id is : " +emailId);
		return emailId;
	}

}
