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
		return new Object[][] { { "Advik", "Goushetwar", "9399999991", "Vkg@1234", "yes" },
				{ "Advik1", "Goushetwar1", "9399999992", "Vkg@1234", "no" },
				{ "Advik2", "Goushetwar2", "9399999997", "Vkg@1234", "yes" },
				{ "Advik3", "Goushetwar3", "9499999991", "Vkg@1234", "yes" }, };
	}

	@DataProvider
	public Object[][] userRegTestDataFromSheet() {
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
	}

	@Test(dataProvider = "userRegTestDataFromSheet")
	public void userRegistrationTest(String firstName, String lastName, String telephone, String password,
			String subscribe) {
		Assert.assertTrue(regPage.userRegister(firstName, lastName, StringUtils.getRandomEmailId(), telephone, password,
				subscribe), AppError.USER_REG_NOT_DONE);

	}

	@Test(dataProvider = "userRegTestData", priority = 1, description = "Register multiple users using inline data provider")
	public void userRegistrationWithInlineData(String firstName, String lastName, String telephone, String password,
			String subscribe) {
		String email = StringUtils.getRandomEmailId();
		Assert.assertTrue(regPage.userRegister(firstName, lastName, email, telephone, password, subscribe),
				AppError.USER_REG_NOT_DONE);
	}

	@DataProvider
	public Object[][] invalidUserRegData() {
		return new Object[][] {

				{ "", "Last", "9399999991", "Vkg@1234", "yes" },

				{ "First", "", "9399999992", "Vkg@1234", "yes" },

				{ "First", "Last", "", "Vkg@1234", "yes" }, };
	}

	@Test(dataProvider = "invalidUserRegData", priority = 2, description = "Negative - required fields missing should fail registration")
	public void userRegistrationNegativeTests(String firstName, String lastName, String telephone, String password,
			String subscribe) {
		String email = StringUtils.getRandomEmailId();

		Assert.assertFalse(regPage.userRegister(firstName, lastName, email, telephone, password, subscribe),
				AppError.USER_REG_NOT_DONE);
	}

	@Test(priority = 3, description = "Duplicate email registration should be rejected")
	public void duplicateEmailRegistrationTest() {
		String duplicateEmail = StringUtils.getRandomEmailId();

		boolean first = regPage.userRegister("Dup", "User", duplicateEmail, "9399999999", "Vkg@1234", "yes");
		Assert.assertTrue(first, "Initial registration with random email should succeed");
		System.out.println("Debuuged changes Duplicate email registration should be rejected " + duplicateEmail);
		boolean second = regPage.userRegister("Dup", "User", duplicateEmail, "9399999998", "Vkg@1234", "yes");
		System.out.println("Debuuged changes2 Duplicate email registration should be rejected " + duplicateEmail);

		Assert.assertFalse(second, "Registration with duplicate email should fail");
	}

}