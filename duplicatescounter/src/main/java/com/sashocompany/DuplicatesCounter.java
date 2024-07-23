package com.sashocompany;

import java.util.Collection;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DuplicatesCounter {
	public static int countDuplicates(int[] intArray) {
		HashMap<Integer, Boolean> occurences = new HashMap<>();
		for (int i = 0; i < intArray.length; i++) {
			if (occurences.containsKey(intArray[i])) {
				occurences.put(intArray[i], Boolean.TRUE);
			} else {
				occurences.put(intArray[i], Boolean.FALSE);
			}
		}
		int numberOfDuplicate = 0;
		Collection<Boolean> isDuplicates = occurences.values();
		for (Boolean isDuplicate : isDuplicates) {
			if (isDuplicate) {
				numberOfDuplicate++;
			}
		}
		return numberOfDuplicate;
	}

	public static void main(String[] args) throws CounterException {
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
			}
			System.out.println(countDuplicates(intArray));
		} catch (InputMismatchException e) {
			throw new CounterException("Doesn't match integer pattern.");
		}
	}
}
