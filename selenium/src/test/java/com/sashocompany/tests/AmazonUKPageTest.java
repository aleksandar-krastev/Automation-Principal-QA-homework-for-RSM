package com.sashocompany.tests;

import java.util.HashMap;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@TestInstance(Lifecycle.PER_CLASS)
class AmazonUKPageTest extends AmazonUKPageBase {

	@ParameterizedTest
	@ValueSource(strings = { "Harry Potter and the Cursed Child" })
	void whenSearchForAnItemThenItShowsResultWithAllAvailableCopyOptions(String title) {
		searchFor(title);
		assertFirstResult(title);
		/*
		 * #AdditionalChecks Assert for presence of picture, whether it says
		 * "Instant delivery" under Kindle Edition, whether the delivery date is in the
		 * future and delivery is to the country where the calls are made from. But all
		 * of those should be added in assertAll so that the execution doesn't stop on
		 * the first fail OR new tests are added.
		 */
		HashMap<String, String> typesOfCoverAndPrices = getPricesOfPaperBackHardCoverAndKindleEdition();
		clickFirstPaperbackResult();
		assertTitleAndPrice(title, typesOfCoverAndPrices);
		// #AdditionalChecks similar checks can be added for the cover types , Hardcover
		// and
		// KindleEdition.
	}

	@ParameterizedTest
	@Disabled("Not implemented")
	@ValueSource(strings = { "Harry Potter and the Cursed Child" })
	void whenNavigateToTheFirstResultForAnItemThenItShowsProperContent(String title) {
		/*
		 * #AdditionalChecks This tests whether navigating to
		 * "https://www.amazon.co.uk/Harry-Potter-Cursed-Child-Playscript/dp/0751565369/
		 * ref=sr_1_1?keywords=Harry+Potter+and+the+Cursed+Child+-+Parts+One+and+Two"
		 * will lead to a page that has the following information: Title, Prices for the
		 * three cover types, Paperback, Hardcover and Kindle Edition, Add to Basket
		 * button, Buy Now button, Author(s), Frequently bought together, Popular titles
		 * by this author, Customers who viewed this item also viewed, Product
		 * description, Product details, About the authors, Products related to this
		 * item, Customer reviews, Customers who bought this item also bought, Customers
		 * who viewed items in your browsing history also viewed, Related to items
		 * you've viewed.
		 */
	}

	@ParameterizedTest
	@CsvSource({
			"https://www.amazon.co.uk/Harry-Potter-Cursed-Child-Playscript/dp/0751565369/ref=sr_1_1?keywords=Harry+Potter+and+the+Cursed+Child, Harry Potter and the Cursed Child" })
	void whenAddItemToTheBasketAndNavigateToTheBasketThenItIsPresentThere(String url, String title) {
		navigateToUrl(url);
		String paperbackPrice = getPaperbackPrice();
		addToBasket();
		// #AdditionalChecks Automatically redirected to 'Added to Basket' screen.
		navigateToBasket();
		assertBasket(title, paperbackPrice);
		// #AdditionalChecks Quantity is 1, Delete, Save for Later, See more like, Share
		// links are available.
		clickThisWillBeAGift();
	}

	@ParameterizedTest
	@ValueSource(strings = { "Harry Potter and the Cursed Child" })
	void whenSearchForAnItemAndAddToBasketThenTheTitleAndThePriceAreCorrect(String title) {
		searchFor(title);
		HashMap<String, String> typesOfCoverAndPrices = getPricesOfPaperBackHardCoverAndKindleEdition();
		clickFirstPaperbackResult();
		addToBasket();
		navigateToBasket();
		assertBasket(title, typesOfCoverAndPrices.get("Paperback"));
	}
}
