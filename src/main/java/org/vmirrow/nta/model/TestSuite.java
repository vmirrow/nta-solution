package org.vmirrow.nta.model;

import java.util.ArrayList;
import java.util.List;

public class TestSuite {
	private String name;
	private String method;
	private String path;
	private List<TestCase> tests = new ArrayList<>();
	
	public TestSuite(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void addTestCase(TestCase test) {
		test.setParent(this);
		tests.add(test);
	}
	
	public List<TestCase> getTests() {
		return tests;
	}
	
	@Override
	public String toString() {
		return "Suite [name=" + name + ", path=" + path + ", tests=" + tests + "]";
	}
	
	
	
	
	
}
