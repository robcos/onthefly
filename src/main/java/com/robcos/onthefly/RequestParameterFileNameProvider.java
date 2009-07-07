package com.robcos.onthefly;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ArrayList;

/**
 * @author robcos - robcos@robcos.com
 */
public class RequestParameterFileNameProvider implements FileNameProvider {

	private Log log = LogFactory.getLog(this.getClass());
	private FileProvider filePatternParser;
	private String includePatternParameterName;

	public RequestParameterFileNameProvider(String includePatternParameterName, FileProvider filePatternParser) {
		this.filePatternParser = filePatternParser;
		this.includePatternParameterName = includePatternParameterName;
	}

	public List<String> getFileNames(HttpServletRequest request) {
		String include = request.getParameter(this.includePatternParameterName);
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

	public void setIncludePatternParameterName(String includePatternParameterName) {
		this.includePatternParameterName = includePatternParameterName;
	}

}
