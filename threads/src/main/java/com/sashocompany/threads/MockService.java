package com.sashocompany.threads;

import java.util.Random;

public class MockService {
	private static Random randomGenerator = new Random();

	public boolean execute() {
		// since 0  is inclusive and 1200 is exclusive we need to add 1.
		int sleepDuration = randomGenerator.nextInt(1200) + 1;
		try {
			Thread.sleep(sleepDuration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int percentFailureThreshold = randomGenerator.nextInt(10);
		return percentFailureThreshold >= 3;
	}
}
