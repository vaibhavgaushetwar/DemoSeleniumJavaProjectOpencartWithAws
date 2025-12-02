package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.util.ExcelUtil;
import com.qa.opencart.util.StringUtils;

public class RegistrationPageTest extends BaseTest {

	@BeforeClass
	public void regSetUp() {
		regPage = loginPage.navigateToRegisterPage();
	}

	@DataProvider
	public Object[][] userRegTestData() {
		return new Object[][] {
			{"Advik","Goushetwar","9399999991","Vkg@1234","yes"},
			{"Advik1","Goushetwar1","9399999992","Vkg@1234","no"},
			{"Advik2","Goushetwar2","9399999997","Vkg@1234","yes"},
			{"Advik3","Goushetwar3","9499999991","Vkg@1234","yes"},
		};
	}
	
	
	@DataProvider
	public Object[][] userRegTestDataFromSheet() {
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
	}
	
	
	
	
	@Test(dataProvider = "userRegTestDataFromSheet")
	public void userRegistrationTest(String firstName, String lastName,  String telephone, String password,
			String subscribe) {
		Assert.assertTrue(
				regPage.userRegister(firstName, lastName,StringUtils.getRandomEmailId(),telephone, password, subscribe),
				AppError.USER_REG_NOT_DONE);

	}
}
