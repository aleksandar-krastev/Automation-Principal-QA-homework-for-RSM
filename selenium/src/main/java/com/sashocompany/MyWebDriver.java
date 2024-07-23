package com.sashocompany;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MyWebDriver {
	private WebDriver driver;
	private static String chromeDriverLocation = "C:\\chromedriver-win64\\chromedriver.exe";

	MyWebDriver() {
		// System property webdriver.chrome.driver can be set from the build system.
		if (System.getProperty("webdriver.chrome.driver") == null) {
			System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
		}
		WebDriver webDriver = new ChromeDriver();

		// Wait up to 1 second for element to appear.
		webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

		webDriver.get("https://www.amazon.co.uk/");
		this.driver = webDriver;
	}

	// A constructor with webdriver name as parameter is possible for parameterization.
	
	public void closeDriver() {
		this.driver.close();
	}
	
	public WebElement findElement(By by) {
		return this.driver.findElement(by);
	}
	

}
