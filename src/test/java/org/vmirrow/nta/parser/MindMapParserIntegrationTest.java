package org.vmirrow.nta.parser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.vmirrow.nta.NtaConfig;
import org.vmirrow.nta.model.TestSuite;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = NtaConfig.class)
@Ignore
public class MindMapParserIntegrationTest {
	@Autowired
	Parser parser;
	
    @Test
    public void parseOKRead_1_2() {
    	URL url = this.getClass().getResource("mm_ok_1_2.xml");
    	File file = new File(url.getFile());
    	List<TestSuite> suites =  parser.parse(file);
    	assertEquals(1, suites.size());
    	assertEquals(2, suites.get(0).getTests().size());
    }

	@Test(expected=RuntimeException.class)
    public void parseErrFormat() {
    	URL url = this.getClass().getResource("mm_err_format.xml");
    	File file = new File(url.getFile());
    	parser.parse(file);
    }
	
	@Test()
    public void parseErrData() {
    	URL url = this.getClass().getResource("mm_err_data.xml");
    	File file = new File(url.getFile());
    	List<TestSuite> suites =  parser.parse(file);
    	assertEquals(4, suites.size());
    	assertEquals(2, suites.get(0).getTests().size());
    }

	@Test()
    public void parseSuiteOnly() {
    	URL url = this.getClass().getResource("mm_ok_suite_only.xml");
    	File file = new File(url.getFile());
    	List<TestSuite> suites =  parser.parse(file);
    	assertEquals(4, suites.size());
    	assertEquals(0, suites.get(0).getTests().size());
    }

}
