package com.robcos.onthefly;

/**
 * @author robcos - robcos@robcos.com
 */
public class PatternMatcher {
	private static final String FILE_CHARS = "[0-9A-z\\-_\\.]";

//	**/robcos.js
//	**/*.js
//
//	robcos.js
//	*.js
//
//	mydir/robcos.js
//	mydir/*.js

	public boolean isSingleFileInMultipleNestedDir(String pattern) {
		return pattern.matches("\\*\\*/" + FILE_CHARS + "*");
	}

	public boolean isMultipleFileInMultipleNestedDir(String pattern) {
		return pattern.matches("\\*\\*/" +
				FILE_CHARS +
				"*\\*" +
				FILE_CHARS +
				"*");
	}

	public boolean isSingleFileInNestedDir(String pattern) {
		return pattern.matches("" +
				"[0-9A-z\\-_\\./]" +
				"*/" +
				FILE_CHARS +
				"*");
	}

	public boolean isMultipleFileInNestedDir(String pattern) {
		return pattern.matches("" +
				"[0-9A-z\\-_\\./]" +
				"*/" +
				FILE_CHARS +
				"*\\*" +
				FILE_CHARS +
				"*");
	}

	public boolean isSingleFile(String pattern) {
		return pattern.matches("" +
				FILE_CHARS +
				"*");
	}

	public boolean isMultipleFile(String pattern) {
		return pattern.matches("" +
				FILE_CHARS +
				"*\\*" +
				FILE_CHARS +
				"*");
	}

}
