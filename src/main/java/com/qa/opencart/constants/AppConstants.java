package com.qa.opencart.constants;

import java.util.Arrays;
import java.util.List;

public class AppConstants {
	private AppConstants() {
	}

	public static final String CONFIG_PROD_FILE_PATH = "./src/test/resources/config/config.properties";
	public static final String CONFIG_QA_FILE_PATH = "./src/test/resources/config/qa.properties";
	public static final String CONFIG_STAGE_FILE_PATH = "./src/test/resources/config/stage.properties";
	public static final String CONFIG_DEV_FILE_PATH = "./src/test/resources/config/dev.properties";
	public static final String CONFIG_UAT_FILE_PATH = "./src/test/resources/config/uat.properties";
	
	
	public static final String LOGIN_PAGE_TITTLE = "Account Login";
	public static final String MY_ACCOUNT_PAGE_TITTLE = "My Account";
	
	public static final String LOGIN_PAGE_FREACTION_URL = "route=account/login";
	public static final String ACCOUNT_PAGE_FREACTION_URL = "route=account/account";
	
	
	public static final List<String> ACCOUNTS_PAGE_HEADERS_LIST=Arrays.asList("My Account","My Orders","My Affiliate Account","Newsletter");
	//public static final CharSequence USER_REGISTER_SUCCESS_MESSG = null;
	public static final String USER_REGISTER_SUCCESS_MESSG = "Your Account Has Been Created!";
	
	//************************Sheet constants*****************************//
	
	public static final String REGISTER_SHEET_NAME="register";

}
