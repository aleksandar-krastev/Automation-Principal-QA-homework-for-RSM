package com.sashocompany.selenium;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyWebDriver {
	private WebDriver driver;
	private WebDriverWait wait;

	private static String chromeDriverLocation = "C:\\chromedriver-win64\\chromedriver.exe";

	public MyWebDriver() {
		// System property webdriver.chrome.driver can be set from the build system.
		if (System.getProperty("webdriver.chrome.driver") == null) {
			System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
		}
		WebDriver webDriver = new ChromeDriver();

		// Wait up to 2 seconds for element to appear.
		webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

		webDriver.get("https://www.amazon.co.uk/");
		this.driver = webDriver;
		this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
	}

	// A constructor with webdriver name as parameter is possible for
	// parameterization.

	/**
	 * Quits the driver.
	 **/
	public void quitDriver() {
		this.driver.quit();
	}

	/**
	 * Deletes cache.
	 **/
	public void deleteCache() {
		this.driver.manage().deleteAllCookies();
	}

	/**
	 * The following serves double purpose: Exceptions are difficult to handle
	 * especially in assertAll. At report level having 2 stacktraces - AssertError
	 * and NoSuchElementException is ridiculous. On top it is easier to have a
	 * meaningful message which includes some human readable information of the
	 * element that you are looking for.
	 * 
	 * @param by Mechanism used to locate elements within a document.
	 * @return null or the element matching the By criteria
	 **/
	public WebElement findElement(By by) {
		try {
			return this.driver.findElement(by);
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	/**
	 * @param by Mechanism used to locate elements within a document.
	 * @return list of elements matching the By criteria
	 **/
	public List<WebElement> findElements(By by) {
		return this.driver.findElements(by);
	}

	public void click(By by) {
		Actions actions = new Actions(this.driver);
		wait.until(ExpectedConditions.elementToBeClickable(by));
		WebElement element = findElement(by);
		actions.moveToElement(element).click().build().perform();
	}

	/**
	 * 
	 * @param by Mechanism used to locate elements within a document.
	 */
	public void waitForElementToDisapear(By by) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	/**
	 * @return current URL
	 **/
	public String getURL() {
		return this.driver.getCurrentUrl();
	}

	/**
	 * @param url URL to navigate to.
	 **/
	public void navigateToUrl(String url) {
		this.driver.navigate().to(url);
	}

}
