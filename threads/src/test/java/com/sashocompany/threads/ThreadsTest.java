package com.sashocompany.threads;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;

class ThreadsTest {
	// failed will be accessed from more than 1 Thread so we need volatile.
	/**
	 * Number of failed calls.
	 */
	volatile int failedCalls = 0;
	// moreThan1SecondCalls will be accessed from more than 1 Thread so we need
	// volatile.
	/**
	 * Number of the calls that took more than 1 second
	 */
	volatile int moreThan1SecondCalls = 0;

	// MockService
	private static MockService mockService = new MockService();

	
	/**
	 * Class that implements Callable which will be used for invokeAll of the executor.
	 */
	class ThreadCallable implements Callable {
		/**
		 * Implementation of the call method which increments moreThan1SecondCalls and failedCalls
		 */
		public Object call() {
			try {
				long currentUnixTime = System.currentTimeMillis();
				mockService.execute();
				long afterExecutionUnixtime = System.currentTimeMillis();
				if (afterExecutionUnixtime - currentUnixTime > 1000) {
					moreThan1SecondCalls++;
				}
			} catch (ThreadException e) {
				failedCalls++;
			}
			return null;
		}
	}

	@Test
	void testConcurency() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(100);
		Collection<Callable<ThreadCallable>> runnables = new LinkedList<>();
		for (int i = 0; i < 1000; i++) {
			runnables.add(new ThreadCallable());
		}
		executor.invokeAll(runnables);

		assertAll("Grouped Assertions for Thread tests",
				() -> assertTrue(failedCalls <= 300, failedCalls + " requests has failed. Which is more than 30%"),
				() -> assertTrue(moreThan1SecondCalls <= 50,
						moreThan1SecondCalls + " requests took more than 1 second to execute. Which is more than 5%"));
	}
}
