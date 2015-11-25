package org.vmirrow.nta.service;

import java.util.List;

import org.vmirrow.nta.model.TestSuite;

/**
 * The Executor interface must be implemented in order to 
 * executes test suites / test cases.
 */
public interface Executor {
	
	/**
	 * Execute all test suites from the list.
	 * It is called by the main method
	 *  
	 * @param list of suites
	 */
	void executeAll(List<TestSuite> suites);
	
	/**
	 * Execute all test cases from a single test suite.
	 * @param test suite
	 */
	void executeSuite(TestSuite suite);

}
