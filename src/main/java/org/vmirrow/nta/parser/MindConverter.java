package org.vmirrow.nta.parser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;
import org.vmirrow.nta.model.TestCase;
import org.vmirrow.nta.model.TestSuite;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


/**
 * Converts data from Mind Map file to nta model
 * Contains all references to MM data structure
 */
public class MindConverter {
	
    public static final String VALUE_DELIMITER = ": ";
	public static final String NODE = "node";
	public static final String TEXT = "TEXT";
	public static final String PATH = "Path:";
	public static final String METHOD = "Method:";
	public static final String TEST = "Test:";
	public static final String A = "a:";
	public static final String B = "b:";
	public static final String RESULT = "result:";


	private List<TestSuite> suites = new ArrayList<TestSuite>();
	private TestSuite suite;
	private TestCase test;
	private Element node;

	public MindConverter(Element node) {
		super();
		this.node = node;
	}

	public List<TestSuite> getSuites() {
		return suites;
	}

	public Element getNode() {
		return node;
	}

	public boolean isMindNode(Node node) {
		return node.getNodeType() == Node.ELEMENT_NODE && NODE.equals(node.getNodeName());
	}

	public boolean isTestNode(String text) {
		return text.startsWith(TEST);
	}

	/**
	 * It's a tricky part of the convertion algorithm 
	 * and it depends on the depth of MM structure.
	 * depth:
	 * 1 - Top level node that has list of test suites
	 * 2 - Test suite node 
	 * 3 - Test suite data (method, path) and Test Case node
	 * 4 - Test case data (a,b, result) 
	 *  
	 * @param depth
	 * @param node
	 */
	public void convertInDepth(int depth, Node node) {
		this.node = (Element) node;
		Assert.notNull(node.getAttributes().getNamedItem(TEXT), TEXT + " must be defined for all nodes");
		String textValue = node.getAttributes().getNamedItem(TEXT).getNodeValue();
		switch (depth) {
		case 2:
			suite = new TestSuite(textValue);
			suites.add(suite);
			break;
		case 3:
			test = new TestCase(textValue);
			if (isTestNode(textValue))
				suite.addTestCase(this.test);
			break;
		case 4:
			convertSuiteData(textValue);
			convertTestData(textValue);
			break;
		}
	}
	
	private void convertSuiteData (String text) {
		if (text.startsWith(PATH)) this.suite.setPath(getValue(text));
		else if (text.startsWith(METHOD)) this.suite.setMethod(getValue(text));
	}

	private void convertTestData (String text) {
		if (text.startsWith(A)) addTestParameter(text);
		else if (text.startsWith(B)) addTestParameter(text); 
		else if (text.startsWith(RESULT)) this.test.setResult(getValue(text));
	}


	private void addTestParameter (String text) {
		String[] parts = text.split(VALUE_DELIMITER);
		if (parts.length == 2) this.test.addParameter(parts[0], parts[1]); 
	}

	private String getValue (String text) {
		String[] parts = text.split(VALUE_DELIMITER);
		String result = "";
		if (parts.length == 2) result = parts[1]; 
		return result; 
	}
	

}
