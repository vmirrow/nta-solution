package org.vmirrow.nta.service;

import org.vmirrow.nta.model.TestCase;

/**
 * The Reporter is responsible for test results reporting 
 *  
 */
public interface Reporter {
	
	/**
	 * Print a test result (triggered by the Executor)  
	 *
	 * @param test case
	 */
	void flush(TestCase test);
}
