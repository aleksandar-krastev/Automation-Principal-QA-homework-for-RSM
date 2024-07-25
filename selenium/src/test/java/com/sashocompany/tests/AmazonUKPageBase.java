package com.sashocompany.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.sashocompany.MyWebDriver;
import com.sashocompany.pages.AmazonUKBasketPage;
import com.sashocompany.pages.AmazonUKItemPage;
import com.sashocompany.pages.AmazonUKSearchResultPage;

public class AmazonUKPageBase {
	private MyWebDriver myWebDriver;

	private static final String ADD_TO_BASKET_XPATH = "//*[@id='add-to-cart-button']";
	private static final String BASKET_XPATH = "//*[@id='nav-cart']";

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

	protected void addToBasket() {
		myWebDriver.click(By.xpath(ADD_TO_BASKET_XPATH));
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

	protected void assertBasket(String title, String paperbackPrice) {
		AmazonUKBasketPage.assertBasket(myWebDriver, title, paperbackPrice);
	}

	protected void assertFirstResult(String title) {
		AmazonUKSearchResultPage.assertFirstResult(myWebDriver, title);

	}

	protected void assertTitleAndPrice(String title, HashMap<String, String> typesOfCoverAndPrices) {
		AmazonUKItemPage.assertTitleAndPrice(myWebDriver, title, typesOfCoverAndPrices);

	}

	protected void clickFirstPaperbackResult() {
		AmazonUKSearchResultPage.clickFirstPaperbackResult(myWebDriver);
	}

	protected void clickThisWillBeAGift() {
		AmazonUKBasketPage.clickThisWillBeAGift(myWebDriver);
	}

	protected String getPaperbackPrice() {
		return AmazonUKItemPage.getPaperbackPrice(myWebDriver);
	}

	protected HashMap<String, String> getPricesOfPaperBackHardCoverAndKindleEdition() {
		return AmazonUKSearchResultPage.getPricesOfPaperBackHardCoverAndKindleEdition(myWebDriver);
	}
}
