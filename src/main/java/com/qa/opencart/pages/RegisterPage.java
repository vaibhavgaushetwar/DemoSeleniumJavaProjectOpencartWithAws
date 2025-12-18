package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;
import com.qa.opencart.util.TimeUtil;

import io.qameta.allure.Step;

public class RegisterPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	public RegisterPage(WebDriver driver) {

		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmpassword = By.id("input-confirm");

	private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[position()=1]/input[@type='radio']");
	private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[position()=2]/input[@type='radio']");

	private By agreeCheckBox = By.name("agree");
	private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");

	private By successMessg = By.cssSelector("div#content h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
 @Step("User registration with firstName: {0}, lastName: {1}, email: {2}, telephone: {3}, password: {4}, subscribe: {5}")
	public boolean userRegister(String firstName, String lastName, String email, String telephone, String password,
			String subscribe) {

		eleUtil.doSendKeys(this.firstName, firstName, TimeUtil.DEFAULT_MEDIUM_TIME);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmpassword, password);

		if (subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		} else {
			eleUtil.doClick(subscribeNo);
		}
try {
			Thread.sleep(1500); // 1.5 seconds pause
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
}
		// click agree checkbox and give a short pause so UI can settle before submitting
if (!eleUtil.isElementSelected(agreeCheckBox)) {
    System.out.println("Agree checkbox not selected, clicking...");
    eleUtil.doClick(agreeCheckBox);
} else {
    System.out.println("Agree checkbox already selected");
}
		
		try {
			Thread.sleep(1500); // 1.5 seconds pause
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}

		eleUtil.doClick(continueButton,TimeUtil.DEFAULT_MEDIUM_TIME);

		String successMesg = eleUtil.waitForElementVisibility(successMessg, TimeUtil.DEFAULT_MEDIUM_TIME).getText();

		System.out.println(successMesg);

		if (successMesg.contains(AppConstants.USER_REGISTER_SUCCESS_MESSG)) {
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			return true;
		} else {
			return false;
		}

	}

}
