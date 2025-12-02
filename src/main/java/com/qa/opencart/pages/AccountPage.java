package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.util.ElementUtil;
import com.qa.opencart.util.TimeUtil;

public class AccountPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	public AccountPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	private By logoutLink = By.partialLinkText("Logout");
	private By headers = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchButton = By.cssSelector("div#search button");

	public String getAccountPageTittle() {
		String tittle = driver.getTitle();
		System.out.println("tittle of this AccountPage: " + tittle);
		return tittle;
	}

	public String getAccountPageUrl() {
		String url = driver.getCurrentUrl();
		System.out.println("url of this AccountPage: " + url);
		return url;
	}

	public boolean isLogoutLinkExist() {
		return driver.findElement(logoutLink).isDisplayed();
	}

	public List<String> getAccountPageHeaders() {
		// List<WebElement> headersList = driver.findElements(headers);
		List<WebElement> headersList = eleUtil.waitForVisibilityOfElementsLocator(headers,
				TimeUtil.DEFAULT_MEDIUM_TIME);
		List<String> headersValList = new ArrayList<String>();
		for (WebElement e : headersList) {
			String text = e.getText();
			headersValList.add(text);
		}
		return headersValList;
	}

	public boolean isSearchExist() {
		return driver.findElement(search).isDisplayed();
	}

	public SearchResultPage doSearch(String searchKey) {
		System.out.println(" Searching for product == > " + searchKey);
		if (isSearchExist()) {
			//driver.findElement(search).sendKeys(searchKey);
			eleUtil.doSendKeys(search, searchKey);
			//driver.findElement(searchButton).click();
			eleUtil.doClick(searchButton);
			return new SearchResultPage(driver);
		} else {
			System.out.println("Search field is not present on Account Page");
			return null;
		}
	}
}
