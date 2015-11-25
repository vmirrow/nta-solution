package org.vmirrow.nta;

import java.io.File;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.vmirrow.nta.model.TestSuite;
import org.vmirrow.nta.parser.Parser;
import org.vmirrow.nta.service.Executor;

/**
 * MtaBoot - main executable class.
 */
public class MtaBoot {

	public static final String FILE_PROPERTY = "file";

	
	public static void main(String[] args) {
		// TODO consider spring shell or appache cli
		CommandLinePropertySource<?> clps = new SimpleCommandLinePropertySource(args);
		if (!clps.containsProperty(FILE_PROPERTY)) {
			System.out.println("Missing parameters.\nUsage example: --" + FILE_PROPERTY + "=/tmp/demo.mm");
			return;
		}
		File file = new File(clps.getProperty(FILE_PROPERTY));
		if (!file.exists()) {
			System.out.println("Unable to locate file: " + clps.getProperty(FILE_PROPERTY));
			return;
		}

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MtaConfig.class);
		Parser parser = ctx.getBean(Parser.class);
		List<TestSuite> suites = parser.parse(file);
		Executor executor = ctx.getBean(Executor.class);
		executor.executeAll(suites);
		ctx.close();
	}

}
