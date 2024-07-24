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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AmazonUKPageBase {
	private MyWebDriver myWebDriver;

	private static final String ADD_TO_BASKET_XPATH = "//*[@id='add-to-cart-button']";
	private static final String BASKET_XPATH = "//*[@id='nav-cart']";
	private static final String THIS_WILL_BE_A_GIFT_XPATH = "//input[@type='checkbox']";
	private static final String THE_ORDER_CONTAINS_A_GIFT_XPATH = "//*[@id='sc-buy-box-gift-checkbox']";

	private static final String DECLINE_XPATH = "//*[@id='sp-cc-rejectall-link']";

	private static final String FIRST_SEARCH_RESULT_XPATH = "//div[@data-cel-widget='search_result_1']";
	private static final String FIRST_SEARCH_RESULT_TITLE_XPATH = FIRST_SEARCH_RESULT_XPATH
			+ "//span[@class='a-size-medium a-color-base a-text-normal']";
	private static final String FIRST_SEARCH_RESULT_TYPE_XPATH = FIRST_SEARCH_RESULT_XPATH + "//*[text()='%s']";
	private static final String FIRST_SEARCH_RESULT_PRICE_XPATH = "(" + FIRST_SEARCH_RESULT_TYPE_XPATH
			+ "/following::div[1]//span[@class='a-offscreen'])[1]";

	private static final String PRODUCT_TITLE_XPATH = "//*[@id='productTitle']";
	private static final String FOCUSED_COVER_TYPE_XPATH = "//*[@id='tmm-grid-swatch-PAPERBACK' and contains(@class, 'selected')]";
	private static final String FOCUSED_COVER_TYPE_NAME_XPATH = FOCUSED_COVER_TYPE_XPATH + "//a/span[1]/span";
	private static final String FOCUSED_COVER_TYPE_PRICE_XPATH = FOCUSED_COVER_TYPE_XPATH + "//a/span[2]/span";

	private static final String SUBTOTAL_CHECKOUT_PRICE_XPATH = "//*[@id='sc-subtotal-amount-buybox']/span";
	private static final String SUBTOTAL_ACTIVE_CART_PRICE_XPATH = "//*[@id='sc-subtotal-amount-activecart']/span";

	private static final List<String> TYPES_OF_COVER = Arrays.asList("Paperback", "Hardcover",
			"Kindle Edition with Audio/Video");

	@BeforeAll
	void initializeDriver() {
		this.myWebDriver = new MyWebDriver();
		this.myWebDriver.click(By.xpath(DECLINE_XPATH));
	}

	@AfterAll
	void clozeDriver() {
		this.myWebDriver.closeDriver();
	}

	protected void addToBasket() {
		myWebDriver.click(By.xpath(ADD_TO_BASKET_XPATH));
	}

	protected void assertBasket(String title, String paperbackPrice) {
		WebElement basketContainsTitle = this.myWebDriver
				.findElement(By.xpath("//*[contains(text(),'" + title + "')]"));
		WebElement basketContainsPrice = this.myWebDriver
				.findElement(By.xpath("//*[contains(text(),'" + paperbackPrice + "')]"));
		WebElement subtotalCheckoutPrice = this.myWebDriver.findElement(By.xpath(SUBTOTAL_CHECKOUT_PRICE_XPATH));
		WebElement subtotalActiveCartPrice = this.myWebDriver.findElement(By.xpath(SUBTOTAL_ACTIVE_CART_PRICE_XPATH));
		assertAll("Grouped Assertions of Search result",
				() -> assertNotNull(basketContainsTitle, "The title " + title + " is NOT present in the basket."),
				() -> assertNotNull(basketContainsPrice,
						"The price " + paperbackPrice + " is NOT present in the basket."),
				() -> assertNotNull(subtotalCheckoutPrice,
						"The total price is NOT present in the basket."),
				() -> assertNotNull(subtotalActiveCartPrice,
						"The active cart price is NOT present in the basket."),
				() -> assertEquals(paperbackPrice, subtotalCheckoutPrice.getAttribute("innerHTML"),
						"The price is not the same as the Subtotal Checkout price."),
				() -> assertEquals(paperbackPrice, subtotalActiveCartPrice.getAttribute("innerHTML"),
						"The price is not the same as the Subtotal Active Cart price."));
	}

	protected void assertFirstResult(String title) {
		WebElement firstSearchResult = this.myWebDriver.findElement(By.xpath(FIRST_SEARCH_RESULT_XPATH));
		WebElement firstSearchResultTitle = this.myWebDriver.findElement(By.xpath(FIRST_SEARCH_RESULT_TITLE_XPATH));

		String titleResult = firstSearchResultTitle.getAttribute("innerHTML");
		assertAll("Grouped Assertions of Search result",
				() -> assertNotNull(firstSearchResult, "There aren't any search results."),
				() -> assertNotNull(firstSearchResultTitle, "There aren't any titles of the first search result."),
				() -> assertTrue(titleResult.startsWith(title),
						"There first result title: " + titleResult + " doesn't match the search " + title));

		for (String cover : TYPES_OF_COVER) {
			WebElement coverElement = this.myWebDriver
					.findElement(By.xpath(String.format(FIRST_SEARCH_RESULT_TYPE_XPATH, cover)));
			WebElement coverPrice = this.myWebDriver
					.findElement(By.xpath(String.format(FIRST_SEARCH_RESULT_PRICE_XPATH, cover)));
			assertNotNull(coverElement, "There aren't any " + cover + " copies for the search result.");
			assertNotNull(coverPrice, "There isn't a price for the " + cover + " copy.");
		}

	}

	protected void assertTitleAndPrice(String title, HashMap<String, String> typesOfCoverAndPrices) {
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

	protected void clickFirstPaperbackResult() {
		WebElement paperBack = this.myWebDriver
				.findElement(By.xpath(String.format(FIRST_SEARCH_RESULT_TYPE_XPATH, "Paperback")));
		assertNotNull(paperBack, "There aren't any paperback options.");
		paperBack.click();
	}

	protected void clickThisWillBeAGift() {
		WebElement thisWillBeAGift = this.myWebDriver.findElement(By.xpath(THIS_WILL_BE_A_GIFT_XPATH));
		assertNotNull(thisWillBeAGift, "There aren't any 'Mark as gift' options.");
		thisWillBeAGift.click();
		assertEquals(Boolean.TRUE, thisWillBeAGift.isSelected(), "'Mark as gift' is not selected");
		WebElement theOrderContainsAGift = this.myWebDriver.findElement(By.xpath(THE_ORDER_CONTAINS_A_GIFT_XPATH));
		assertEquals(Boolean.TRUE, theOrderContainsAGift.isSelected(), "'The order contains a gift' is not selected");
	}

	protected String getPaperbackPrice() {
		return this.myWebDriver.findElement(By.xpath(FOCUSED_COVER_TYPE_PRICE_XPATH)).getAttribute("innerHTML").strip();
	}

	protected HashMap<String, String> getPricesOfPaperBackHardCoverAndKindleEdition() {
		HashMap<String, String> typesOfCoverAndPrices = new HashMap<>();
		for (String cover : TYPES_OF_COVER) {
			String priceString = this.myWebDriver
					.findElement(By.xpath(String.format(FIRST_SEARCH_RESULT_PRICE_XPATH, cover)))
					.getAttribute("innerHTML");
			assertTrue(priceString.matches("^£\\d+[\\.,]\\d{2}"),
					"There price " + priceString + " for the " + cover + " doesn't match the pattern.");
			typesOfCoverAndPrices.put(cover, priceString);
		}

		return typesOfCoverAndPrices;
	}

	protected void navigateToBasket() {
		WebElement navigateToBasket = this.myWebDriver.findElement(By.xpath(BASKET_XPATH));
		assertNotNull(navigateToBasket, "There isn't a 'Basket' button");
		navigateToBasket.click();
	}

	protected void navigateToUrl(String url) {
		this.myWebDriver.navigateToUrl(url);
	}

	protected void searchFor(String searchable) {
		WebElement searchBox = this.myWebDriver.findElement(By.id("twotabsearchtextbox"));
		assertNotNull(searchBox, "There isn't a 'Search' bar");
		searchBox.sendKeys(searchable);
		WebElement searchButton = this.myWebDriver.findElement(By.id("nav-search-submit-button"));
		assertNotNull(searchButton, "There isn't a 'Search' button");
		searchButton.click();
	}
}
