package com.sashocompany.pages;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.sashocompany.MyWebDriver;

public class AmazonUKSearchResultPage {
	private static final String FIRST_SEARCH_RESULT_XPATH = "//div[@data-cel-widget='search_result_1']";
	private static final String FIRST_SEARCH_RESULT_TITLE_XPATH = FIRST_SEARCH_RESULT_XPATH
			+ "//span[@class='a-size-medium a-color-base a-text-normal']";
	private static final String FIRST_SEARCH_RESULT_TYPE_XPATH = FIRST_SEARCH_RESULT_XPATH + "//*[text()='%s']";
	private static final String FIRST_SEARCH_RESULT_PRICE_XPATH = "(" + FIRST_SEARCH_RESULT_TYPE_XPATH
			+ "/following::div[1]//span[@class='a-offscreen'])[1]";

	private static final List<String> TYPES_OF_COVER = Arrays.asList("Paperback", "Hardcover",
			"Kindle Edition with Audio/Video");

	/**
	 * Assert that the first result corresponds to the searched item.
	 * 
	 * @param myWebDriver Instance of MyWebDriver.
	 * @param title       Title of the expected result.
	 */
	public static void assertFirstResult(MyWebDriver myWebDriver, String title) {
		WebElement firstSearchResult = myWebDriver.findElement(By.xpath(FIRST_SEARCH_RESULT_XPATH));
		WebElement firstSearchResultTitle = myWebDriver.findElement(By.xpath(FIRST_SEARCH_RESULT_TITLE_XPATH));

		String titleResult = firstSearchResultTitle.getAttribute("innerHTML");
		assertAll("Grouped Assertions of Search result",
				() -> assertNotNull(firstSearchResult, "There aren't any search results."),
				() -> assertNotNull(firstSearchResultTitle, "There aren't any titles of the first search result."),
				() -> assertTrue(titleResult.startsWith(title),
						"There first result title: " + titleResult + " doesn't match the search " + title));

		for (String cover : TYPES_OF_COVER) {
			WebElement coverElement = myWebDriver
					.findElement(By.xpath(String.format(FIRST_SEARCH_RESULT_TYPE_XPATH, cover)));
			WebElement coverPrice = myWebDriver
					.findElement(By.xpath(String.format(FIRST_SEARCH_RESULT_PRICE_XPATH, cover)));
			assertNotNull(coverElement, "There aren't any " + cover + " copies for the search result.");
			assertNotNull(coverPrice, "There isn't a price for the " + cover + " copy.");
		}

	}

	/**
	 * Click on the first result paperback link.
	 * 
	 * @param myWebDriver Instance of MyWebDriver.
	 */
	public static void clickFirstPaperbackResult(MyWebDriver myWebDriver) {
		WebElement paperBack = myWebDriver
				.findElement(By.xpath(String.format(FIRST_SEARCH_RESULT_TYPE_XPATH, "Paperback")));
		assertNotNull(paperBack, "There aren't any paperback options.");
		paperBack.click();
	}

	/**
	 * Get the prices for the different types of cover.
	 * 
	 * @param myWebDriver Instance of MyWebDriver.
	 * @return HashMap with all available prices for the different types of cover.
	 */
	public static HashMap<String, String> getPricesOfPaperBackHardCoverAndKindleEdition(MyWebDriver myWebDriver) {
		HashMap<String, String> typesOfCoverAndPrices = new HashMap<>();
		for (String cover : TYPES_OF_COVER) {
			String priceString = myWebDriver
					.findElement(By.xpath(String.format(FIRST_SEARCH_RESULT_PRICE_XPATH, cover)))
					.getAttribute("innerHTML");
			assertTrue(priceString.matches("^£\\d+[\\.,]\\d{2}"),
					"There price " + priceString + " for the " + cover + " doesn't match the pattern.");
			typesOfCoverAndPrices.put(cover, priceString);
		}

		return typesOfCoverAndPrices;
	}
}
