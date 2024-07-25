package com.sashocompany.pages;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.sashocompany.MyWebDriver;

public class AmazonUKHomePage {
	private static final String BASKET_XPATH = "//*[@id='nav-cart']";

	/**
	 * Navigate to Basket page.
	 * 
	 * @param myWebDriver Instance of MyWebDriver.
	 */
	public static void navigateToBasket(MyWebDriver myWebDriver) {
		WebElement navigateToBasket = myWebDriver.findElement(By.xpath(BASKET_XPATH));
		assertNotNull(navigateToBasket, "There isn't a 'Basket' button");
		navigateToBasket.click();
	}

	/**
	 * Search for an item in the 'Search' bar.
	 * 
	 * @param myWebDriver Instance of MyWebDriver.
	 * @param searchable  Item name.
	 */
	public static void searchFor(MyWebDriver myWebDriver, String searchable) {
		WebElement searchBox = myWebDriver.findElement(By.id("twotabsearchtextbox"));
		assertNotNull(searchBox, "There isn't a 'Search' bar");
		searchBox.sendKeys(searchable);
		WebElement searchButton = myWebDriver.findElement(By.id("nav-search-submit-button"));
		assertNotNull(searchButton, "There isn't a 'Search' button");
		searchButton.click();
	}
}
