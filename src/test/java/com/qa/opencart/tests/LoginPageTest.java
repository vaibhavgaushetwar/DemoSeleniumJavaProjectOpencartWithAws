package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.listners.AnnotationTransformer;
import com.qa.opencart.listners.ExtentReportListener;
import com.qa.opencart.listners.TestAllureListener;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;


@Epic("Epic 100: Design Open cart Appilcation with shopping workflow")
@Story("US 101 :Design login page for open cart application ")
@Listeners({ExtentReportListener.class,TestAllureListener.class,AnnotationTransformer.class})
public class LoginPageTest extends BaseTest {

	@Description("Checking login page title--------")
	@Owner("Vaibhav Gaushetwar")
	@Test(priority = 1)
	public void loginPageTittleTest() {
		String tittle = loginPage.getTittle();
		Assert.assertEquals(tittle, AppConstants.LOGIN_PAGE_TITTLE, AppError.TITTLE_NOT_FOUND);
	}

	@Description("Checking login page URL   --------")
	@Owner("Vaibhav Gaushetwar")
	@Test(priority = 2)
	public void loginPageUrlTest() {
		String url = loginPage.getPageUrl();
		Assert.assertTrue(url.contains(AppConstants.LOGIN_PAGE_FREACTION_URL), AppError.URL_NOT_FOUND);
	}

	@Description("Checking forgot Passwrod Exist Link  --------")
	@Owner("Vaibhav Gaushetwar")
	@Test(priority = 3)
	public void forgotPasswrodExistLink() {
		Assert.assertTrue(loginPage.checkFrgtpwdLink(), AppError.ELEMENT_NOT_FOUND);
		System.out.println("===============This test passed=====================");
	}

	@Description("Checking user is able to login succesfully  --------")
	@Owner("Vaibhav Gaushetwar")
	@Test(priority = 4)
	public void loginTest() {
		accPage = loginPage.DoLogin(prop.getProperty("Username"), prop.getProperty("password"));
		Assert.assertEquals	(accPage.getAccountPageTittle(),AppConstants.MY_ACCOUNT_PAGE_TITTLE,AppError.TITTLE_NOT_FOUND);
		// Assert.assertEquals(actTitle, AppConstants.MY_ACCOUNT_PAGE_TITTLE,
		// AppError.TITTLE_NOT_FOUND);
	}
}
