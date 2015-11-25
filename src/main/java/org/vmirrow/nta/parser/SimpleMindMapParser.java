package org.vmirrow.nta.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.vmirrow.nta.model.TestSuite;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * The implemented solution builds a DOM model from MM file. 
 * It recursively goes through all nodes performing data convertion.  
 */
@Component
public class SimpleMindMapParser implements Parser {
    
	final Logger logger = LoggerFactory.getLogger(SimpleMindMapParser.class);    
	
	public List<TestSuite> parse(File file) {
		Document doc;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(file);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			logger.error("File parsing error", e);
			throw new RuntimeException("Unable to parse file: " + file.getName(), e);
		}
		MindConverter converter = new MindConverter(doc.getDocumentElement()); 
		parseDoc(converter, 0);
		return converter.getSuites();
	}

	private void parseDoc(MindConverter converter, int depth) {
		final NodeList children = converter.getNode().getChildNodes();
		depth++;
		for (int i = 0; i < children.getLength(); i++) {
			final Node node = children.item(i);
			if (converter.isMindNode(node)) {
				converter.convertInDepth(depth, node);
				parseDoc(converter, depth);
			}
		}
	}
}
