package com.sashocompany.duplicatescounter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DuplicatesCounterTest {

	@Test
	void testCountDuplicatesReturn0WhenThereArentAnyDuplicates() {
		int[] testData = new int[] { 1, 2, 3, 4, 9 };
		assertEquals(0, DuplicatesCounter.countDuplicates(testData));
		testData = new int[] { 1 };
		assertEquals(0, DuplicatesCounter.countDuplicates(testData));
	}

	@Test
	void testCountDuplicatesReturn1WhenThereIsOnlyOneDuplicate() {
		int[] testData = new int[] { 1, 2, 2, 2, 4, 3, 9 };
		assertEquals(1, DuplicatesCounter.countDuplicates(testData));
		testData = new int[] { 1, 1 };
		assertEquals(1, DuplicatesCounter.countDuplicates(testData));
	}

	@Test
	void testCountDuplicatesReturn2WhenThereAre2Duplicates() {
		int[] testData = new int[] { 1, 1, 2, 2, 2, 4, 3, 9 };
		assertEquals(2, DuplicatesCounter.countDuplicates(testData));
		testData = new int[] { 1, 1, 9, 9 };
		assertEquals(2, DuplicatesCounter.countDuplicates(testData));
		testData = new int[] { 1, 3, 1, 4, 5, 6, 3, 2 };
		assertEquals(2, DuplicatesCounter.countDuplicates(testData));
	}

	@Test
	void testCountDuplicatesReturn3WhenThereAre3Duplicates() {
		int[] testData = new int[] { 1, 1, 2, 2, 2, 3, 4, 3, 9 };
		assertEquals(3, DuplicatesCounter.countDuplicates(testData));
		testData = new int[] { 1, 1, 9, 9, 1000, 1000 };
		assertEquals(3, DuplicatesCounter.countDuplicates(testData));
	}
}
