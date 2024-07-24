package com.sashocompany;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DuplicatesCounterCreateArrayTest {
	private final InputStream systemIn = System.in;

	private ByteArrayInputStream testIn;

	@AfterEach
	public void restoreSystemInputOutput() {
		System.setIn(systemIn);
	}

	private void provideInput(String data) {
		testIn = new ByteArrayInputStream(data.getBytes());
		System.setIn(testIn);
	}

	@Test
	void testCreateIntArrayFromStandardInputReturnArrayWhenCorrectDataIsEntered() {
		String testData = "8\r\n1\r\n3\r\n1\r\n4\r\n5\r\n6\r\n3\r\n2\r\n";
		provideInput(testData);
		try {
			int[] expectedArray = new int[] { 1, 3, 1, 4, 5, 6, 3, 2 };
			int[] actualArray = DuplicatesCounter.createIntArrayFromStandardInput();
			assertTrue(Arrays.equals(expectedArray, actualArray),
					"Expected: " + expectedArray + " but received" + actualArray);
		} catch (CounterException e) {
			e.printStackTrace();
		}
	}

	@ParameterizedTest
	@ValueSource(strings = { "8\r\n1\r\n3\r\n1.5\r\n4\r\n5\r\n6\r\n3\r\n2\r\n",
			"8\r\n1\r\n3\r\n1001\r\n4\r\n5\r\n6\r\n3\r\n2\r\n", "8\r\n1\r\n3\r\n0\r\n4\r\n5\r\n6\r\n3\r\n2\r\n",
			"1001\r\n1\r\n3\r\n1\r\n4\r\n5\r\n6\r\n3\r\n2\r\n", "0\r\n1\r\n3\r\n1\r\n4\r\n5\r\n6\r\n3\r\n2\r\n" })
	void testCreateIntArrayFromStandardInputThrowsCounterException(String testData) {
		provideInput(testData);
		assertThrows(CounterException.class, () -> DuplicatesCounter.createIntArrayFromStandardInput());
	}

}
