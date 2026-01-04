package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

	private Properties prop;
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;

	public OptionsManager(Properties prop) {
		this.prop = prop;
	}

	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();

		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("====== running test in headless mode ======");
			//co.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			co.addArguments("--incognito");
		}
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
	        co.setCapability("browserName", "chrome");	
	       // co.setCapability("enableVNC", true);
	        }
		return co;
	}

	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();

		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("====== running test in headless mode ======");
			//fo.addArguments("--headless");
			fo.addArguments("-private");    // Firefox private mode
            fo.setAcceptInsecureCerts(true);
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			fo.addArguments("--incognito");
		}
       if(Boolean.parseBoolean(prop.getProperty("remote"))) {
        fo.setCapability("browserName", "firefox");	 
       // fo.setCapability("enableVNC", true);
        }
		return fo;
	}
	
	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();

		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("====== running test in headless mode ======");
		//	eo.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			eo.addArguments("--incognito");
		}
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
	        eo.setCapability("browserName", "edge");	 
	       // eo.setCapability("enableVNC", true);
	        }
		return eo;
	}
}
