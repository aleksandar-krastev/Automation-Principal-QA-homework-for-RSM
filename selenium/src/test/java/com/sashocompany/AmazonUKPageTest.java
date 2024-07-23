package com.sashocompany;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class AmazonUKPageTest {
	
	private MyWebDriver myWebDriver; 

	@BeforeAll
	void initializeWebDriver() {
		this.myWebDriver = new MyWebDriver();
	}

	@AfterAll
	void clozeWebDriver() {
		this.myWebDriver.closeDriver();
	}
	
	@Test
	void testSearc() {
		fail("Not yet implemented");
	}

}
