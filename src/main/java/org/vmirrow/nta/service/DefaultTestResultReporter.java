package org.vmirrow.nta.service;

import org.springframework.stereotype.Service;
import org.vmirrow.nta.model.TestCase;

@Service
public class DefaultTestResultReporter implements Reporter {
	
	/**
	 * A very simple reporting implementation,
	 * simply prints test results on the console.
	 * Test case should know how to validate an actual result
	 * 
	 */
	public void flush(TestCase test) {
		System.out.println((test.isTestPassed()?"GREEN ":"RED ") + test.toString());
	}
}
