package com.sashocompany;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DuplicatesCounter {
	public static int countDuplicates(int[] intArray) {
		HashMap<Integer, Boolean> occurences = new HashMap<>();
		int numberOfDuplicate = 0;
		for (int i = 0; i < intArray.length; i++) {
			Boolean isDuplicate = occurences.putIfAbsent(intArray[i], Boolean.FALSE);
			if (Boolean.FALSE.equals(isDuplicate)) {
				occurences.put(intArray[i], Boolean.TRUE);
				numberOfDuplicate++;
			}
		}

		return numberOfDuplicate;
	}
	
	public static int[] createIntArrayFromStandardInput() throws CounterException {
		try (Scanner in = new Scanner(System.in)) {
			int arrayLength = in.nextInt();
			if (arrayLength > 1000 || arrayLength <= 0) {
				throw new CounterException("Array length should be >= 1 and <= 1000.");
			}
			int[] intArray = new int[arrayLength];
			for (int i = 0; i < arrayLength; i++) {
				int nextInt = in.nextInt();
				if (nextInt > 1000 || nextInt <= 0) {
					throw new CounterException("Values should be >= 1 and <= 1000.");
				}
				intArray[i] = nextInt;
			}
			return intArray;
		} catch (InputMismatchException e) {
			throw new CounterException("Doesn't match integer pattern.");
		}
	}

	public static void main(String[] args) throws CounterException {
		int[] intArray = createIntArrayFromStandardInput();
		System.out.println(countDuplicates(intArray));
	}
}
