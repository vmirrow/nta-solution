package org.vmirrow.nta.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.vmirrow.nta.model.TestCase;
import org.vmirrow.nta.model.TestSuite;
import org.vmirrow.nta.parser.SimpleMindMapParser;

/**
 * The default executor implementation: 
 * Iterates through all test suites / test cases and calls remote API 
 * After test execution, it is reported straight away. 
 */
@Service
public class DefaultTestSuiteExecutor implements Executor {
	//TODO property or cli parameter
	private static final String URI = "http://calculator.neueda.lv";
	final Logger logger = LoggerFactory.getLogger(SimpleMindMapParser.class);
	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private Reporter reporter;

	public void executeAll(List<TestSuite> suites) {
		//TODO parallelStream()
		suites.stream().forEach(suite -> executeSuite(suite));
	}

	public void executeSuite(TestSuite suite) {
		if (HttpMethod.GET.name().equals(suite.getMethod())) {
			suite.getTests().stream().forEach(test -> executeTest(test));
		} else {
			logger.error("Only GET is supported in this version");
		}
	}

	public void executeTest(TestCase test) {
		try {
			test.setActualResult(restTemplate.getForObject(buildGetRequest(test), String.class));
		} catch (HttpClientErrorException e) {
			logger.error("Unable to request data from the remote endpoint", e);
		}
		reporter.flush(test);
	}

	private String buildGetRequest(TestCase test) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URI);
		builder.path(test.getParent().getPath());
		test.getParams().keySet().stream().forEach(key -> builder.queryParam(key, test.getParams().get(key)));
		return builder.toUriString();
	}

}
