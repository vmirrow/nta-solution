package org.vmirrow.nta.model;

import java.util.HashMap;
import java.util.Map;

public class TestCase {
	private String name;
	private Map<String, String> params = new HashMap<>();
	private String expectedResult = "";
	private String actualResult = "";
	private TestSuite parent;

	public TestCase(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getResult() {
		return expectedResult;
	}

	public void setResult(String result) {
		this.expectedResult = result;
	}

	public TestSuite getParent() {
		return parent;
	}

	public void setParent(TestSuite parent) {
		this.parent = parent;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void addParameter(String name, String value) {
		this.params.put(name, value);
	}

	public String getActualResult() {
		return actualResult;
	}

	public void setActualResult(String actualResult) {
		this.actualResult = actualResult;
	}

	public boolean isTestPassed() {
		return !actualResult.isEmpty() && !expectedResult.isEmpty() && actualResult.contains(expectedResult);
	}

	@Override
	public String toString() {
		return "TestCase [name=" + name + ", params=" + params + ", expectedResult=" + expectedResult
				+ ", actualResult=" + actualResult + "]";
	}
	
}
