package com.sashocompany.pages;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.sashocompany.MyWebDriver;

public class AmazonUKBasketPage {
	private static final String THIS_WILL_BE_A_GIFT_XPATH = "//input[@type='checkbox']";
	private static final String THE_ORDER_CONTAINS_A_GIFT_XPATH = "//*[@id='sc-buy-box-gift-checkbox']";
	private static final String SUBTOTAL_CHECKOUT_PRICE_XPATH = "//*[@id='sc-subtotal-amount-buybox']/span";
	private static final String SUBTOTAL_ACTIVE_CART_PRICE_XPATH = "//*[@id='sc-subtotal-amount-activecart']/span";


	public static void assertBasket(MyWebDriver myWebDriver, String title, String paperbackPrice) {
		WebElement basketContainsTitle = myWebDriver
				.findElement(By.xpath("//*[contains(text(),'" + title + "')]"));
		WebElement basketContainsPrice = myWebDriver
				.findElement(By.xpath("//*[contains(text(),'" + paperbackPrice + "')]"));
		WebElement subtotalCheckoutPrice = myWebDriver.findElement(By.xpath(SUBTOTAL_CHECKOUT_PRICE_XPATH));
		WebElement subtotalActiveCartPrice = myWebDriver.findElement(By.xpath(SUBTOTAL_ACTIVE_CART_PRICE_XPATH));
		assertAll("Grouped Assertions of Search result",
				() -> assertNotNull(basketContainsTitle, "The title " + title + " is NOT present in the basket."),
				() -> assertNotNull(basketContainsPrice,
						"The price " + paperbackPrice + " is NOT present in the basket."),
				() -> assertNotNull(subtotalCheckoutPrice, "The total price is NOT present in the basket."),
				() -> assertNotNull(subtotalActiveCartPrice, "The active cart price is NOT present in the basket."),
				() -> assertEquals(paperbackPrice, subtotalCheckoutPrice.getAttribute("innerHTML"),
						"The price is not the same as the Subtotal Checkout price."),
				() -> assertEquals(paperbackPrice, subtotalActiveCartPrice.getAttribute("innerHTML"),
						"The price is not the same as the Subtotal Active Cart price."));
	}
	
	public static void clickThisWillBeAGift(MyWebDriver myWebDriver) {
		WebElement thisWillBeAGift = myWebDriver.findElement(By.xpath(THIS_WILL_BE_A_GIFT_XPATH));
		assertNotNull(thisWillBeAGift, "There aren't any 'Mark as gift' options.");
		thisWillBeAGift.click();
		assertEquals(Boolean.TRUE, thisWillBeAGift.isSelected(), "'Mark as gift' is not selected");
		WebElement theOrderContainsAGift = myWebDriver.findElement(By.xpath(THE_ORDER_CONTAINS_A_GIFT_XPATH));
		assertEquals(Boolean.TRUE, theOrderContainsAGift.isSelected(), "'The order contains a gift' is not selected");
	}
}
