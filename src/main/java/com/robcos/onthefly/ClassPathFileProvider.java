package com.robcos.onthefly;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

/**
 * @author robcos - robcos@robcos.com
 */
public class ClassPathFileProvider implements FileProvider {
	private PatternMatcher patternMatcher = new PatternMatcher();
	private Log log = LogFactory.getLog(this.getClass());

	public List<String> getFileNames(String filePattern) {
		List<String> result = new ArrayList<String>();
		String[] rows = filePattern.split(",");
		for (String untrimmedRow : rows) {
			final String row = untrimmedRow.trim();
			log.debug("row:" + row);
			if (patternMatcher.isSingleFile(row) || patternMatcher.isSingleFileInNestedDir(row)) {
				log.debug("single file case:" + row);
				result.add(row);
				continue;
			}

			throw new RuntimeException("Pattern not supported for class path loading: " + row);
		}
		return result;
	}

	public BufferedReader getResource(String filename) throws FileNotFoundException {
		log.debug("Trying to get resource " + filename + " from classpath");
		InputStream stream = this.getClass().getResourceAsStream(filename);
		if (stream == null) {
			throw new FileNotFoundException("Could not find file " + filename + " in classpath");
		}
		return new BufferedReader(new InputStreamReader(stream));
	}
}
