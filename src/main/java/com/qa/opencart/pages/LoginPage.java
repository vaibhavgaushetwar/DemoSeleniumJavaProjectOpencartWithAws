package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;
import com.qa.opencart.util.TimeUtil;

import io.qameta.allure.Step;

public class LoginPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	private By username = By.id("input-email");
	private By password = By.id("input-password");
	private By loginbtn = By.xpath("//input[@value='Login']");
	private By frgtPwdLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getTittle() {
		String tittle = eleUtil.waitForTitleToBe(AppConstants.MY_ACCOUNT_PAGE_TITTLE, TimeUtil.DEFAULT_TIME);
		// String tittle=driver.getTitle();
		System.out.println("tittle of this page: " + tittle);
		return tittle;
	}

	public String getPageUrl() {
		
		String url =eleUtil.waitForUrlContain(AppConstants.LOGIN_PAGE_FREACTION_URL,TimeUtil.DEFAULT_TIME );	
		// String url = driver.getCurrentUrl();
		System.out.println("url of this page: " + url);
		return url;
	}

	public boolean checkFrgtpwdLink() {
		return	eleUtil.doIsDisplayed(frgtPwdLink);
		//return driver.findElement(frgtPwdLink).isDisplayed();
	}

	@Step("login to application with username {0} and password {1}")
	public AccountPage DoLogin(String Username, String pwd) {
		
		eleUtil.doSendKeys(username, Username, TimeUtil.DEFAULT_LONG_TIME);
		//driver.findElement(username).sendKeys(Username);
		driver.findElement(password).sendKeys(pwd);
		//driver.findElement(loginbtn).click();
		eleUtil.doClick(loginbtn, 5000);
	
		String actTitle = driver.getTitle();
		System.out.println("Account Page tittle is : " + actTitle);
		return new AccountPage(driver);
		// return actTitle;
	}

	public RegisterPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink, TimeUtil.DEFAULT_TIME );
		return new RegisterPage(driver);
	}
	
}
