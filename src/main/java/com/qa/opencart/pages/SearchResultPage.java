package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.util.ElementUtil;
import com.qa.opencart.util.TimeUtil;

public class SearchResultPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	private By searchResult = By.cssSelector("div.product-thumb");

	public SearchResultPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public int getSearchresultCount() {
		List<WebElement> resultList = eleUtil.waitForVisibilityOfElementsLocator(searchResult,
				TimeUtil.DEFAULT_MEDIUM_TIME);
		System.out.println("Result count is ====> " + resultList.size());
		return resultList.size();
	}

	public ProductInfoPage selectProduct(String productName) {
		eleUtil.doClick(By.linkText(productName), TimeUtil.DEFAULT_MEDIUM_TIME);
		return new ProductInfoPage(driver);
	}
}
