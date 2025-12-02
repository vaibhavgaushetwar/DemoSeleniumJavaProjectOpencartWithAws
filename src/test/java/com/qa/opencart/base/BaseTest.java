package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultPage;
import com.qa.opencart.tests.ProductInfoPageTest;

import io.qameta.allure.Description;
import io.qameta.allure.Step;

public class BaseTest {
	DriverFactory df;
	WebDriver driver;
	protected Properties prop;
	protected LoginPage loginPage;
	protected AccountPage accPage;
	protected SearchResultPage searchResultPage;
	protected ProductInfoPage productInfoPage;
	protected SoftAssert softAssert;
	protected RegisterPage regPage;

	
	@Step("setup for the test,initializing browser:{0}")
	@Parameters({"browser"})
	@BeforeTest
	public void setUp(@Optional String browserName) {
		df = new DriverFactory();
		prop = df.initProp();
		
		if(browserName!= null) {
			prop.setProperty("browser", browserName);
		}
		
		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);
		softAssert =new SoftAssert();
	}
	
	@Step("close browser")
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
