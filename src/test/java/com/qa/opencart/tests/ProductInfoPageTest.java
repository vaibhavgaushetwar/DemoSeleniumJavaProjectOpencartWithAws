package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.errors.AppError;

public class ProductInfoPageTest extends BaseTest {

	@BeforeClass
	public void productInfoPageSetup() {
		accPage = loginPage.DoLogin(prop.getProperty("Username"), prop.getProperty("password"));
	}

	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] { { "macbook", "MacBook Pro" }, { "imac", "iMac" },
				{ "samsung", "Samsung SyncMaster 941BW" }, { "samsung", "Samsung Galaxy Tab 10.1" },
				{ "canon", "Canon EOS 5D" } };
	}

	@Test(dataProvider = "getProductData")
	public void productHeaderTest(String searchKey, String productName) {
		searchResultPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductHeader(), productName, AppError.HEADER_NOT_FOUND);
	}

	@DataProvider
	public Object[][] getProductImageData() {
		return new Object[][] { { "macbook", "MacBook Pro", 4 }, { "imac", "iMac", 3 },
				{ "samsung", "Samsung SyncMaster 941BW", 1 }, { "samsung", "Samsung Galaxy Tab 10.1", 7 },
				{ "canon", "Canon EOS 5D", 3 } };
	}

	@Test(dataProvider = "getProductImageData")
	public void productImagesCountTest(String searchKey, String productName, int imageCount) {
		searchResultPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getImageCount(), imageCount, AppError.IMAGES_COUNT_MISMATCHED);
	}

	/*
	 * productname=MacBook Pro, productmagescount=4, Brand=Apple, Product
	 * Code=Product 18, Reward Points=800, Availability=Out Of Stock,
	 * productPrice=$2,000.00, exTaxPrice=$2,000.00
	 */

	@Test
	public void productInfoTest() {
		searchResultPage = accPage.doSearch("macbook");
		productInfoPage = searchResultPage.selectProduct("MacBook Pro");
		Map<String, String> productInfoMap = productInfoPage.getProductInfoMap();
		System.out.println("=======================Product information=============");
		System.out.println(productInfoMap);
		softAssert.assertEquals(productInfoMap.get("productname"), "MacBook Pro");
		softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(productInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(productInfoMap.get("Reward Points"), "800");
		softAssert.assertEquals(productInfoMap.get("Availability"), "Out Of Stock");
		softAssert.assertEquals(productInfoMap.get("productPrice"), "$2,000.00");
		softAssert.assertEquals(productInfoMap.get("exTaxPrice"), "$2,000.00");
		// softAssert.assertEquals(productInfoMap.get("productPrice"), "$2,000.00");
		softAssert.assertAll();
	}

}
