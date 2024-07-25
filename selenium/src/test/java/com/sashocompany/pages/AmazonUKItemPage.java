package com.sashocompany.pages;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.sashocompany.MyWebDriver;

public class AmazonUKItemPage {

	private static final String PRODUCT_TITLE_XPATH = "//*[@id='productTitle']";
	private static final String FOCUSED_COVER_TYPE_XPATH = "//*[@id='tmm-grid-swatch-PAPERBACK' and contains(@class, 'selected')]";
	private static final String FOCUSED_COVER_TYPE_NAME_XPATH = FOCUSED_COVER_TYPE_XPATH + "//a/span[1]/span";
	private static final String FOCUSED_COVER_TYPE_PRICE_XPATH = FOCUSED_COVER_TYPE_XPATH + "//a/span[2]/span";

	private static final String ADD_TO_BASKET_XPATH = "//*[@id='add-to-cart-button']";

	/**
	 * Click on Add to Basket button.
	 * 
	 * @param myWebDriver Instance of MyWebDriver.
	 */
	public static void addToBasket(MyWebDriver myWebDriver) {
		myWebDriver.click(By.xpath(ADD_TO_BASKET_XPATH));
	}

	/**
	 * Assert the title and price of the item are as expected.
	 * 
	 * @param myWebDriver           Instance of MyWebDriver.
	 * @param title                 Title of the item.
	 * @param typesOfCoverAndPrices Expected prices of all available cover types.
	 */
	public static void assertTitleAndPrice(MyWebDriver myWebDriver, String title,
			HashMap<String, String> typesOfCoverAndPrices) {
		WebElement productTitle = myWebDriver.findElement(By.xpath(PRODUCT_TITLE_XPATH));
		WebElement focusedCoverType = myWebDriver.findElement(By.xpath(FOCUSED_COVER_TYPE_NAME_XPATH));
		WebElement focusedCoverTypePrice = myWebDriver.findElement(By.xpath(FOCUSED_COVER_TYPE_PRICE_XPATH));

		assertAll("Grouped Assertions of Product", () -> assertNotNull(productTitle, "There isn't a Product Title"),
				() -> assertTrue(productTitle.getAttribute("innerHTML").contains(title),
						"There product title " + productTitle.getAttribute("innerHTML") + " doesn't match the search."),
				() -> assertNotNull(focusedCoverType, "There isn't a focused cover type"),
				() -> assertEquals("Paperback", focusedCoverType.getAttribute("innerHTML"),
						"There focused cover type is not Paperback."),
				() -> assertNotNull(focusedCoverTypePrice, "There isn't a focused cover type price"),
				// For some weird reason there are intervals before and after the price.
				() -> assertEquals(" " + typesOfCoverAndPrices.get("Paperback") + " ",
						focusedCoverTypePrice.getAttribute("innerHTML")));

	}

	/**
	 * Get the paperback price.
	 * 
	 * @param myWebDriver Instance of MyWebDriver.
	 * @return Price of the Paperback item.
	 */
	public static String getPaperbackPrice(MyWebDriver myWebDriver) {
		return myWebDriver.findElement(By.xpath(FOCUSED_COVER_TYPE_PRICE_XPATH)).getAttribute("innerHTML").strip();
	}

}
