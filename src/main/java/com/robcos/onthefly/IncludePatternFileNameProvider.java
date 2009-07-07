package com.robcos.onthefly;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.ArrayList;
import java.io.*;

/**
 * @author robcos - roberto.cosenza@infoflexconnect.se
 */
public class IncludePatternFileNameProvider extends AbstractFileNameProvider {

	private FileProvider filePatternParser;
	private String includePattern;

	public IncludePatternFileNameProvider(String includePatter, FileProvider filePatternParser) {
		this.filePatternParser = filePatternParser;
		this.includePattern = includePatter;
	}

	public List<String> getFileNames() {
		String include = this.includePattern;
		String[] lines = include.split("\n");
		List<String> fileList = new ArrayList<String>();
		for (String line : lines) {
			List<String> list = this.filePatternParser.getFileNames(line);
			for (String file : list) {
				if (!fileList.contains(file)) {
					fileList.add(file);
				}
			}
		}
		return fileList;
	}

	public void setIncludePattern(String includePattern) {
		this.includePattern = includePattern;
	}

}
