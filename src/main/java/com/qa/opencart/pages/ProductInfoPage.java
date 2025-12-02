package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.util.ElementUtil;
import com.qa.opencart.util.TimeUtil;

public class ProductInfoPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	private By productHeader = By.cssSelector("div#content h1");
	private By productImagesCount = By.cssSelector("div#content a.thumbnail");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPricingData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	private Map<String, String> productMap;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getProductHeader() {
		String header = eleUtil.doGetText(productHeader);
		System.out.println("product header =======> " + header);
		return header;
	}

	public int getImageCount() {
		int imagesCount = eleUtil.waitForVisibilityOfElementsLocator(productImagesCount, TimeUtil.DEFAULT_MEDIUM_TIME)
				.size();
		System.out.println("total images====> " + imagesCount);
		return imagesCount;
	}

	public Map<String, String> getProductInfoMap() {
		productMap = new HashMap<String, String>();
		//productMap = new LinkedHashMap<String, String>();
		
		productMap.put("productname", getProductHeader());
		//getProductMetaData();
		productMap.put("productmagescount", String.valueOf(getImageCount()) );
		getProductMetaData();
		getProductData();
		return productMap;
	}

	private void getProductMetaData() {
		//productMap = new HashMap<String, String>();
		List<WebElement> metaList = eleUtil.getElements(productMetaData);
		for (WebElement e : metaList) {
			String metaData = e.getText();
			String[] meta = metaData.split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productMap.put(metaKey, metaValue);
		}
	}

	private void getProductData() {
		List<WebElement> priceList = eleUtil.getElements(productPricingData);
		String productPrice = priceList.get(0).getText();
		String exTaxPrice = priceList.get(1).getText().split(":")[1].trim();
		productMap.put("productPrice", productPrice);
		productMap.put("exTaxPrice", exTaxPrice);

	}
}
