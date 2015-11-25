package org.vmirrow.nta.parser;

import java.io.File;
import java.util.List;

import org.vmirrow.nta.model.TestSuite;

/**
 * The Parser interface must be implemented 
 * in order to convert data from MM to nta model.
 */
public interface Parser {

	/**
	 * A single method that is called by main class to perform an actual parsing 
	 *
	 * @param file - mind map data  
	 * @return the list of test suites
	 */
	List<TestSuite> parse(File file);
}
