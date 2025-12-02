package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.exception.BrowserException;
import com.qa.opencart.exception.FrameworkException;

public class DriverFactory {
	WebDriver driver;
	Properties prop;
	InputStream ip;
	OptionsManager optionsManager;	
	
	public static String highlight;
	
	public static ThreadLocal<WebDriver> tlDriver= new  ThreadLocal<WebDriver>();
	/**
	 * This used to initialize the driver given browser name
	 * 
	 * @param browserName
	 */
	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser");
		System.out.println("browser name is - " + browserName);
		highlight =prop.getProperty("highlight");
		
		optionsManager = new OptionsManager(prop);
		
		switch (browserName.trim().toLowerCase()) {
		case "chrome":
			//driver = new ChromeDriver();
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		case "firefox":
			//driver = new FirefoxDriver();
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
		case "edge":
			//driver = new EdgeDriver();
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;

		default:
			throw new BrowserException(AppError.BROWSER_NOT_FOUND);
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}
	
	/*
	 * get the local thread copy of the driver
	 * @return
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
		
	}

	/**
	 * This is used to initialize .config properties
	 * 
	 * @return properties
	 */
	public Properties initProp() {
		prop = new Properties();
		// mvn clean install -Denv="qa"
		String envName = System.getProperty("env");
		System.out.println("running test suite on env ====> " + envName);
		if (envName == null) {
			System.out.println("env name is not given, hence running it on QA environment....");
			try {
				ip = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);	 
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
		} else {

			try {

				switch (envName.toLowerCase().trim()) {
				case "qa":
					//ip = getClass().getClassLoader().getResourceAsStream("config/qa.properties");
					ip = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
					break;
				case "dev":
					ip = new FileInputStream(AppConstants.CONFIG_DEV_FILE_PATH);
					break;
				case "stage":
					ip = new FileInputStream(AppConstants.CONFIG_STAGE_FILE_PATH);
					break;
				case "uat":
					ip = new FileInputStream(AppConstants.CONFIG_UAT_FILE_PATH);
					break;
				case "prod":
					ip = new FileInputStream(AppConstants.CONFIG_PROD_FILE_PATH);
					break;
					
				default:
					System.out.println("please pass the right env name.... " + envName);
					throw new FrameworkException("=== WRONG ENV PASSED ===");

				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		try {
		    prop.load(ip);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch (NullPointerException e) {
		    throw new FrameworkException("❌ Properties file not found or not loaded properly!");
		}
		return prop;

	}
	/**
	 * take screen shot
	 */
	public static String getScreenShot(String methodName) {
	File srcFile =((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
	String path=System.getProperty("user.dir")+"/screenshots/"+methodName+"_"+System.currentTimeMillis()+".png";
	File destination = new File(path);
	try {
		FileHandler.copy(srcFile, destination);
	} catch (IOException e) {
		e.printStackTrace();
	}
	return path;
	}
}
