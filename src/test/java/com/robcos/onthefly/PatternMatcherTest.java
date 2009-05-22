package com.robcos.onthefly;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import com.robcos.onthefly.PatternMatcher;

/**
 * @author robcos - roberto.cosenza@infoflexconnect.se
 */
public class PatternMatcherTest extends TestCase {
	PatternMatcher patternMatcher = new PatternMatcher();

	@Before
	@Override
	public void setUp() throws Exception {
	}

	@After
	@Override
	public void tearDown() throws Exception {
	}

	@Test
	public void testSingleFile() {
		boolean singleFile = true;
		boolean multipleFileNestedDir = false;
		boolean singleFileNestedDir = false;
		boolean multipleFile = false;
		boolean singleFileMultipleNestedDir = false;
		boolean multipleFileMultipleNestedDir = false;

		match("robCos1234.js", singleFile, multipleFileNestedDir, singleFileNestedDir, multipleFile, singleFileMultipleNestedDir, multipleFileMultipleNestedDir);
	}

	@Test
	public void testMultipleFileNestedDir() {
		boolean singleFile = false;
		boolean multipleFileNestedDir = true;
		boolean singleFileNestedDir = false;
		boolean multipleFile = false;
		boolean singleFileMultipleNestedDir = false;
		boolean multipleFileMultipleNestedDir = false;

		match("myDir123/*.js", singleFile, multipleFileNestedDir, singleFileNestedDir, multipleFile, singleFileMultipleNestedDir, multipleFileMultipleNestedDir);
	}

	@Test
	public void testSingleFileNestedDir() {
		boolean singleFile = false;
		boolean multipleFileNestedDir = false;
		boolean singleFileNestedDir = true;
		boolean multipleFile = false;
		boolean singleFileMultipleNestedDir = false;
		boolean multipleFileMultipleNestedDir = false;

		match("myDir123/roCos123.js", singleFile, multipleFileNestedDir, singleFileNestedDir, multipleFile, singleFileMultipleNestedDir, multipleFileMultipleNestedDir);
	}

	@Test
	public void testMultipleFile() {
		boolean singleFile = false;
		boolean multipleFileNestedDir = false;
		boolean singleFileNestedDir = false;
		boolean multipleFile = true;
		boolean singleFileMultipleNestedDir = false;
		boolean multipleFileMultipleNestedDir = false;

		match("*.js", singleFile, multipleFileNestedDir, singleFileNestedDir, multipleFile, singleFileMultipleNestedDir, multipleFileMultipleNestedDir);
	}

	@Test
	public void testSingleFileMultipleNestedDir() {
		boolean singleFile = false;
		boolean multipleFileNestedDir = false;
		boolean singleFileNestedDir = false;
		boolean multipleFile = false;
		boolean singleFileMultipleNestedDir = true;
		boolean multipleFileMultipleNestedDir = false;

		match("**/robCos123.js", singleFile, multipleFileNestedDir, singleFileNestedDir, multipleFile, singleFileMultipleNestedDir, multipleFileMultipleNestedDir);
	}

	@Test
	public void testMultipleFileMultipleNestedDir() {
		boolean singleFile = false;
		boolean multipleFileNestedDir = false;
		boolean singleFileNestedDir = false;
		boolean multipleFile = false;
		boolean singleFileMultipleNestedDir = false;
		boolean multipleFileMultipleNestedDir = true;

		match("**/*.j123s", singleFile, multipleFileNestedDir, singleFileNestedDir, multipleFile, singleFileMultipleNestedDir, multipleFileMultipleNestedDir);
	}

	private void match(String pattern, boolean singleFile, boolean multipleFileNestedDir, boolean singleFileNestedDir, boolean multipleFile, boolean singleFileMultipleNestedDir, boolean multipleFileMultipleNestedDir) {
		assertEquals(singleFile, patternMatcher.isSingleFile(pattern));
		assertEquals(multipleFile, patternMatcher.isMultipleFile(pattern));
		assertEquals(singleFileMultipleNestedDir, patternMatcher.isSingleFileInMultipleNestedDir(pattern));
		assertEquals(multipleFileMultipleNestedDir, patternMatcher.isMultipleFileInMultipleNestedDir(pattern));
		assertEquals(singleFileNestedDir, patternMatcher.isSingleFileInNestedDir(pattern));
		assertEquals(multipleFileNestedDir, patternMatcher.isMultipleFileInNestedDir(pattern));
	}
}