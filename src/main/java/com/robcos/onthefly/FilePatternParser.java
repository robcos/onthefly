package com.robcos.onthefly;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.ArrayList;

/**
 * @author robcos - roberto.cosenza@infoflexconnect.se
 */
public class FilePatternParser {
	private PatternMatcher patternMatcher = new PatternMatcher();

	/**
	 * Returns a list of file names which satisfies a ant-like pattern
	 * <code>**\/*.js all js files in all directories</code>
	 * <code>myfile.js myfile.js in the root directories</code>
	 * <code>**\/myfile.js myfile.js in any subdirectory</code>
	 *
	 * @param filePattern
	 * @param root
	 * @return
	 */
	public List<String> getFileNames(String filePattern, String root) {
		List<String> result = new ArrayList<String>();
		String[] rows = filePattern.split(",");
		File rootFile = new File(root);
		for (String untrimmedRow : rows) {
			final String row = untrimmedRow.trim();
			System.out.println("row:" + row);
			if (patternMatcher.isMultipleFileInMultipleNestedDir(row)
					|| patternMatcher.isSingleFileInMultipleNestedDir(row)) {
				String pattern = convertPatternToRegexp(row);
				List<File> files = getFiles(pattern, rootFile);
				for (File f : files) {
					result.add(f.getAbsolutePath());
				}
				continue;
			}


			if (patternMatcher.isSingleFileInNestedDir(row)
					|| patternMatcher.isMultipleFileInNestedDir(row)) { // mydir/*.js
				// mydir/robcos.js
				final String newRow = row.substring(row.lastIndexOf("/") + 1);
				final String newRoot = rootFile.getAbsolutePath() + "/" + row.substring(0, row.lastIndexOf("/"));
				return getFileNames(newRow, newRoot);
			}

			if (patternMatcher.isMultipleFile(row)) {  // *.js
				final String pattern = convertPatternToRegexp(row);

				File[] files = rootFile.listFiles(new FilenameFilter() {
					public boolean accept(File dir, String file) {
						return file.matches(pattern);
					}
				});
				if (files != null) {
					for (File f : files) {
						result.add(f.getAbsolutePath());
					}
				}
				continue;
			}

			if (patternMatcher.isSingleFile(row)) { // robcos.js
				System.out.println("single file case:" + row);
				File candidate = new File(root + "/" + row);
				if (candidate.exists()) {
					result.add(candidate.getAbsolutePath());
				}
				continue;
			}


			throw new RuntimeException("Pattern not currently supported: " + row);
		}
		return result;
	}

	private String convertPatternToRegexp(String row) {
		row = row.replaceAll("\\*\\*/", "*");
		row = row.replaceAll("\\.", "\\\\.");
		row = row.replaceAll("\\*", ".*");
		return row;
	}


	private List<File> getFiles(String match, File file) {
		final ArrayList<File> files = new ArrayList<File>();
		final boolean matching = file.getAbsolutePath().matches(match);
		if (file.isFile() && matching) {
			files.add(file);
			return files;
		}
		final File[] children = file.listFiles();
		if (children != null) {
			for (File child : children) {
				files.addAll(getFiles(match, child));
			}
		}
		return files;
	}
}
