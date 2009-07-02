package com.robcos.onthefly;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.ArrayList;
import java.io.*;

/**
 * @author robcos - roberto.cosenza@infoflexconnect.se
 */
public class FileNameProvider {

	private Log log = LogFactory.getLog(this.getClass());
	private FileProvider filePatternParser;
	private String includePattern;

	public FileNameProvider(String includePatter, FileProvider filePatternParser) {
		this.filePatternParser = filePatternParser;
		this.includePattern = includePatter;
	}

	protected List<String> getFileNames() {
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

	public BufferedReader getResource(String filename) throws FileNotFoundException {
		return filePatternParser.getResource(filename);
	}
}
