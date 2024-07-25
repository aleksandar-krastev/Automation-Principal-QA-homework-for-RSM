package com.sashocompany.selenium.tests;

import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;

import com.sashocompany.selenium.MyWebDriver;
import com.sashocompany.selenium.pages.AmazonUKBasketPage;
import com.sashocompany.selenium.pages.AmazonUKHomePage;
import com.sashocompany.selenium.pages.AmazonUKItemPage;
import com.sashocompany.selenium.pages.AmazonUKSearchResultPage;

public class AmazonUKPageBase {
	private MyWebDriver myWebDriver;

	private static final String DECLINE_XPATH = "//*[@id='sp-cc-rejectall-link']";

	@BeforeEach
	void initializeDriver() {
		this.myWebDriver = new MyWebDriver();
		this.myWebDriver.click(By.xpath(DECLINE_XPATH));
		this.myWebDriver.waitForElementToDisapear(By.xpath(DECLINE_XPATH));
	}

	@AfterEach
	void quitDriver() {
		this.myWebDriver.quitDriver();
	}

	/**
	 * Navigates to URL.
	 * 
	 * @param url Url to navigate to.
	 */
	protected void navigateToUrl(String url) {
		this.myWebDriver.navigateToUrl(url);
	}

	/**
	 * Wrapper method that adds item to the basket.
	 */
	protected void addToBasket() {
		AmazonUKItemPage.addToBasket(myWebDriver);
	}

	/**
	 * Wrapper method that navigates to the basket.
	 */
	protected void navigateToBasket() {
		AmazonUKHomePage.navigateToBasket(myWebDriver);
	}

	/**
	 * Wrapper method that search for an item in the 'Search' bar.
	 * 
	 * @param searchable Item to be searched for.
	 */
	protected void searchFor(String searchable) {
		AmazonUKHomePage.searchFor(myWebDriver, searchable);
	}

	/**
	 * Wrapper method that asserts whether the basket has items and the intermediate
	 * and final price.
	 * 
	 * @param title          Title of the item.
	 * @param paperbackPrice Price of the item.
	 */
	protected void assertBasket(String title, String paperbackPrice) {
		AmazonUKBasketPage.assertBasket(myWebDriver, title, paperbackPrice);
	}

	/**
	 * Wrapper method that asserts that the first result corresponds to the searched
	 * item.
	 * 
	 * @param title Title of the expected result.
	 */
	protected void assertFirstResult(String title) {
		AmazonUKSearchResultPage.assertFirstResult(myWebDriver, title);

	}

	/**
	 * Wrapper method that asserts the title and price of the item are as expected.
	 * 
	 * @param title                 Title of the item.
	 * @param typesOfCoverAndPrices Expected prices of all available cover types.
	 */
	protected void assertTitleAndPrice(String title, HashMap<String, String> typesOfCoverAndPrices) {
		AmazonUKItemPage.assertTitleAndPrice(myWebDriver, title, typesOfCoverAndPrices);

	}

	/**
	 * Wrapper method that clicks on the first result paperback link.
	 */
	protected void clickFirstPaperbackResult() {
		AmazonUKSearchResultPage.clickFirstPaperbackResult(myWebDriver);
	}

	/**
	 * Wrapper method that clicks in the 'Mark as gift' input.
	 */
	protected void clickThisWillBeAGift() {
		AmazonUKBasketPage.clickThisWillBeAGift(myWebDriver);
	}

	/**
	 * Get the paperback price.
	 * 
	 * @return Price of the Paperback item.
	 */
	protected String getPaperbackPrice() {
		return AmazonUKItemPage.getPaperbackPrice(myWebDriver);
	}

	/**
	 * Get the prices for the different types of cover.
	 * 
	 * @return HashMap with all available prices for the different types of cover.
	 */
	protected HashMap<String, String> getPricesOfPaperBackHardCoverAndKindleEdition() {
		return AmazonUKSearchResultPage.getPricesOfPaperBackHardCoverAndKindleEdition(myWebDriver);
	}
}
