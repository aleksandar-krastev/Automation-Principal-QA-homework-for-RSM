package com.sashocompany;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@TestInstance(Lifecycle.PER_CLASS)
class AmazonUKPageTest {

	private MyWebDriver myWebDriver;
	private static String FIRST_SEARCH_RESULT_XPATH = "//div[@data-cel-widget='search_result_1']";
	private static String FIRST_SEARCH_RESULT_TYPE_XPATH = "//div[@data-cel-widget='search_result_1']//*[text()='%s']";
	private static String FIRST_SEARCH_RESULT_PRICE_XPATH = "(//div[@data-cel-widget='search_result_1']//*[text()='Paperback']/following::div[1]//span[@class='a-offscreen'])[1]";

	@BeforeAll
	void initializeDriver() {
		this.myWebDriver = new MyWebDriver();
	}

	@AfterAll
	void clozeDriver() {
		this.myWebDriver.closeDriver();
	}

	public void searchFor(String searchable) {
		WebElement searchBox = this.myWebDriver.findElement(By.id("twotabsearchtextbox"));
		searchBox.sendKeys(searchable);
		WebElement searchButton = this.myWebDriver.findElement(By.id("nav-search-submit-button"));
		searchButton.click();
	}

	public void assertFirstResult() {
		WebElement firstSearchResult = this.myWebDriver.findElement(By.xpath(FIRST_SEARCH_RESULT_XPATH));

		WebElement paperBack = this.myWebDriver
				.findElement(By.xpath(String.format(FIRST_SEARCH_RESULT_TYPE_XPATH, "Paperback")));
		WebElement paperBackPrice = this.myWebDriver
				.findElement(By.xpath(String.format(FIRST_SEARCH_RESULT_PRICE_XPATH, "Paperback")));

		WebElement hardcover = this.myWebDriver
				.findElement(By.xpath(String.format(FIRST_SEARCH_RESULT_TYPE_XPATH, "Hardcover")));
		WebElement hardcoverPrice = this.myWebDriver
				.findElement(By.xpath(String.format(FIRST_SEARCH_RESULT_PRICE_XPATH, "Hardcover")));

		WebElement kindleEdition = this.myWebDriver.findElement(
				By.xpath(String.format(FIRST_SEARCH_RESULT_TYPE_XPATH, "Kindle Edition with Audio/Video")));
		WebElement kindleEditionPrice = this.myWebDriver.findElement(
				By.xpath(String.format(FIRST_SEARCH_RESULT_PRICE_XPATH, "Kindle Edition with Audio/Video")));

		/*
		 * I could probably add even more assertions for presence of picture, whether it says
		 * "Instant delivery" under Kindle Edition, whether the delivery date is in the
		 * future and delivery is to the country where the calls are made from. But all
		 * of those should be added below so that the execution doesn't stop on the
		 * first fail OR Separate tests are added.
		 */
		assertAll("Grouped Assertions of Search result",
				() -> assertNotNull(firstSearchResult, "There aren't any search results."),
				() -> assertNotNull(paperBack, "There aren't any Paperback copies for the search result."),
				() -> assertNotNull(paperBackPrice, "There isn't a price for the Paperback copy."),
				() -> assertNotNull(hardcover, "There aren't any Hardcover copies for the search result."),
				() -> assertNotNull(hardcoverPrice, "There isn't a price for the HardCover copy."),
				() -> assertNotNull(kindleEdition, "There aren't any Kindle Edition copies for the search result."),
				() -> assertNotNull(kindleEditionPrice, "There isn't a price for the Kindle Edition copy."));

		String paperBackPriceString = paperBackPrice.getAttribute("innerHTML");
		String hardcoverPriceString = paperBackPrice.getAttribute("innerHTML");
		String kindleEditionPricString = paperBackPrice.getAttribute("innerHTML");

		assertAll("Grouped Assertions for the prices of each copy.",
				() -> assertTrue(paperBackPriceString.matches("^£[1-9]\\d{0,6}\\.\\d{2}$"),
						"There price " + paperBackPriceString + " for the Paperback copy doesn't match the pattern."),
				() -> assertTrue(hardcoverPriceString.matches("^£[1-9]\\d{0,6}\\.\\d{2}$"),
						"There price " + hardcoverPriceString + " for the Hardcover copy doesn't match the pattern"),
				() -> assertTrue(kindleEditionPricString.matches("^£[1-9]\\d{0,6}\\.\\d{2}$"),
						"There price " + kindleEditionPricString + " for the Kindle Edition doesn't match the pattern."));
	}

	private void clickFirstPaperbackResult() {
		WebElement paperBack = this.myWebDriver
				.findElement(By.xpath(String.format(FIRST_SEARCH_RESULT_TYPE_XPATH, "Paperback")));
		paperBack.click();
	}
	
	@Test
	void searchForHarryPotterShouldProduceResultWithAllAvailableCopyOptions() {
		searchFor("Harry Potter and the Cursed Child");
		assertFirstResult();
		clickFirstPaperbackResult();
	}

}
