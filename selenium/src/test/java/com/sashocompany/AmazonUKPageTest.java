package com.sashocompany;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@TestInstance(Lifecycle.PER_CLASS)
class AmazonUKPageTest {

	private MyWebDriver myWebDriver;
	private static String FIRST_SEARCH_RESULT_XPATH = "//div[@data-cel-widget='search_result_1']";
	private static String FIRST_SEARCH_RESULT_TITLE_XPATH = FIRST_SEARCH_RESULT_XPATH
			+ "//span[@class='a-size-medium a-color-base a-text-normal']";
	private static String FIRST_SEARCH_RESULT_TYPE_XPATH = FIRST_SEARCH_RESULT_XPATH + "//*[text()='%s']";
	private static String FIRST_SEARCH_RESULT_PRICE_XPATH = "(" + FIRST_SEARCH_RESULT_TYPE_XPATH
			+ "/following::div[1]//span[@class='a-offscreen'])[1]";
	private static String PRODUCT_TITLE_XPATH = "//*[@id='productTitle']";
	private static String FOCUSED_COVER_TYPE_XPATH = "//*[@id='tmm-grid-swatch-PAPERBACK' and contains(@class, 'selected')]";
	private static String FOCUSED_COVER_TYPE_NAME_XPATH = FOCUSED_COVER_TYPE_XPATH + "//a/span[1]/span";
	private static String FOCUSED_COVER_TYPE_PRICE_XPATH = FOCUSED_COVER_TYPE_XPATH + "//a/span[2]/span";
	private static List<String> typesOfCover = Arrays.asList("Paperback", "Hardcover",
			"Kindle Edition with Audio/Video");

	@BeforeAll
	void initializeDriver() {
		this.myWebDriver = new MyWebDriver();
	}

	@AfterAll
	void clozeDriver() {
		this.myWebDriver.closeDriver();
	}

	public void assertFirstResult(String title) {
		WebElement firstSearchResult = this.myWebDriver.findElement(By.xpath(FIRST_SEARCH_RESULT_XPATH));
		WebElement firstSearchResultTitle = this.myWebDriver.findElement(By.xpath(FIRST_SEARCH_RESULT_TITLE_XPATH));

		/*
		 * I could probably add even more assertions for presence of picture, whether it
		 * says "Instant delivery" under Kindle Edition, whether the delivery date is in
		 * the future and delivery is to the country where the calls are made from. But
		 * all of those should be added below so that the execution doesn't stop on the
		 * first fail OR new tests are added.
		 */
		assertAll("Grouped Assertions of Search result",
				() -> assertNotNull(firstSearchResult, "There aren't any search results."),
				() -> assertNotNull(firstSearchResultTitle, "There aren't any titles of the first search result."),
				() -> assertTrue(firstSearchResultTitle.getAttribute("innerHTML").startsWith(title),
						"There first result title doesn't match the search."));

		/*
		 * I added check for the cover types. I didn't find a way to include the
		 * following checks in the assertAll. I tried () -> { ... } but only the first
		 * assert in the ... was checked. I also tried individually listing the elements
		 * for the different covers but the code was too repetitive.
		 */
		for (String cover : typesOfCover) {
			WebElement coverElement = this.myWebDriver
					.findElement(By.xpath(String.format(FIRST_SEARCH_RESULT_TYPE_XPATH, cover)));
			WebElement coverPrice = this.myWebDriver
					.findElement(By.xpath(String.format(FIRST_SEARCH_RESULT_PRICE_XPATH, cover)));
			assertNotNull(coverElement, "There aren't any " + cover + " copies for the search result.");
			assertNotNull(coverPrice, "There isn't a price for the " + cover + " copy.");
		}

	}

	private void assertTitleAndPrice(String title, HashMap<String, String> typesOfCoverAndPrices) {
		WebElement productTitle = this.myWebDriver.findElement(By.xpath(PRODUCT_TITLE_XPATH));
		WebElement focusedCoverType = this.myWebDriver.findElement(By.xpath(FOCUSED_COVER_TYPE_NAME_XPATH));
		WebElement focusedCoverTypePrice = this.myWebDriver.findElement(By.xpath(FOCUSED_COVER_TYPE_PRICE_XPATH));

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

	private void clickFirstPaperbackResult() {
		WebElement paperBack = this.myWebDriver
				.findElement(By.xpath(String.format(FIRST_SEARCH_RESULT_TYPE_XPATH, "Paperback")));
		paperBack.click();
	}

	private HashMap<String, String> getPricesOfPaperBackHardCoverAndKindleEdition() {
		HashMap<String, String> typesOfCoverAndPrices = new HashMap<>();
		for (String cover : typesOfCover) {
			String priceString = this.myWebDriver
					.findElement(By.xpath(String.format(FIRST_SEARCH_RESULT_PRICE_XPATH, cover)))
					.getAttribute("innerHTML");
			assertTrue(priceString.matches("^£\\d+[\\.,]\\d{2}"),
					"There price " + priceString + " for the " + cover + " doesn't match the pattern.");
			typesOfCoverAndPrices.put(cover, priceString);
		}

		return typesOfCoverAndPrices;
	}

	public void searchFor(String searchable) {
		WebElement searchBox = this.myWebDriver.findElement(By.id("twotabsearchtextbox"));
		searchBox.sendKeys(searchable);
		WebElement searchButton = this.myWebDriver.findElement(By.id("nav-search-submit-button"));
		searchButton.click();
	}

	@ParameterizedTest
	@ValueSource(strings = { "Harry Potter and the Cursed Child - Parts One and Two" })
	void whenSearchForAnItemThenItShowsResultWithAllAvailableCopyOptions(String title) {
		searchFor(title);
		assertFirstResult(title);
		HashMap<String, String> typesOfCoverAndPrices = getPricesOfPaperBackHardCoverAndKindleEdition();
		clickFirstPaperbackResult();
		assertTitleAndPrice(title, typesOfCoverAndPrices);
	}

	@ParameterizedTest
	@Disabled("Not implemented")
	@ValueSource(strings = { "Harry Potter and the Cursed Child - Parts One and Two" })
	void whenNavigateToTheFirstResultForAnItemThenItShowsProperContent(String title) {
		/*
		 * This test shows that the navigating to
		 * "https://www.amazon.co.uk/Harry-Potter-Cursed-Child-Playscript/dp/0751565369/
		 * ref=sr_1_1?keywords=Harry+Potter+and+the+Cursed+Child+-+Parts+One+and+Two"
		 * will lead to a page that has the following information: 
		 * - Title, 
		 * - Prices for the three cover types - Paperback, Hardcover and Kindle Edition
		 * - Add to Basket button
		 * - Buy Now button
		 * - Author(s)
		 * - Frequently bought together
		 * - Popular titles by this author
		 * - Customers who viewed this item also viewed
		 * - Product description
		 * - Product details
		 * - About the authors
		 * - Products related to this item
		 * - Customer reviews
		 * - Customers who bought this item also bought
		 * - Customers who viewed items in your browsing history also viewed
		 * - Related to items you've viewed
		 */
	}

}
