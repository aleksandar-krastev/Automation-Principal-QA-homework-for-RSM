package com.sashocompany.threads;

import java.util.Random;

public class MockService {
	private static Random randomGenerator = new Random();

	/**
	 * The method finishes in between 1 and 1200 milliseconds and fail about 30% of
	 * the time with an exception.
	 * 
	 * @throws ThreadException 30% of the time.
	 */
	public void execute() throws ThreadException {
		// since 0 is inclusive and 1200 is exclusive we need to add 1.
		int sleepDuration = randomGenerator.nextInt(1200) + 1;
		try {
			Thread.sleep(sleepDuration);
		} catch (InterruptedException e) {
			int percentFailureThreshold = randomGenerator.nextInt(10);
			if (percentFailureThreshold < 3) {
				throw new ThreadException("Call failed");
			} else {
				return;
			}
		}
		int percentFailureThreshold = randomGenerator.nextInt(10);
		if (percentFailureThreshold < 3) {
			throw new ThreadException("Call failed");
		}
	}
}
