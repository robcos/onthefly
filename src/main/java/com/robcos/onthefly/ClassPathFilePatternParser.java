package com.robcos.onthefly;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.ArrayList;

/**
 * @author robcos - roberto.cosenza@infoflexconnect.se
 */
public class ClassPathFilePatternParser implements FilePatternParser {
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


}