package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;

public class AccountPageTest extends BaseTest {

	
	@BeforeClass
	public void accSetup() {
		accPage = loginPage.DoLogin(prop.getProperty("Username"), prop.getProperty("password"));
	}
	
	@Test
	public void getAccountPageTitleTest() {
		Assert.assertEquals(accPage.getAccountPageTittle(),AppConstants.MY_ACCOUNT_PAGE_TITTLE ,AppError.TITTLE_NOT_FOUND);
	}
	@Test
	public void getAccountPageUrlTest() {
		String Url=accPage.getAccountPageUrl();
		Assert.assertTrue(Url.contains(AppConstants.ACCOUNT_PAGE_FREACTION_URL),AppError.URL_NOT_FOUND);
	}
	@Test
	public void accountPageHeadersTest() {
		List <String>	accPageHeadersList=accPage.getAccountPageHeaders();
		Assert.assertEquals(accPageHeadersList,AppConstants.ACCOUNTS_PAGE_HEADERS_LIST ,AppError.LIST_NOT_MATCHED);
	}
	
	@DataProvider
	public Object[][] getSearchData() {
		return new Object[][] {
			{"macbook",3},
			{"imac",1},
			{"samsung",2},
			{"Airtel",0}
		};
	}
	
	@Test(dataProvider = "getSearchData")
	public void searchTest(String searchKey,int resultCount) {
		searchResultPage=accPage.doSearch(searchKey);
		Assert.assertEquals(searchResultPage.getSearchresultCount(), resultCount,AppError.RESULT_COUNT_MISMATCHED);
		
	}
	
}
