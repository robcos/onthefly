import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.io.File;
import java.util.List;

/**
 * @author robcos - roberto.cosenza@infoflexconnect.se
 */
public class FilePatternParserTest extends TestCase {
	FilePatternParser parser = new FilePatternParser();
	File file = null;
	File file2 = null;
	File fileInNestedDir = null;
	File file2InNestedDir = null;
	File nestedDir;
	File rootDir;

	@Before
	@Override
	public void setUp() throws Exception {
		File tempFile = File.createTempFile("something", "");
		tempFile.delete(); //just to get a pointer to the temp dir

		rootDir = new File(tempFile.getAbsolutePath());
		nestedDir = new File(tempFile.getAbsolutePath() + "/a/b/c");
		nestedDir.mkdirs();

		file = new File(rootDir, "myfile.js");
		file2 = new File(rootDir, "myfile2.js");
		fileInNestedDir = new File(nestedDir, "myfileInNestedDir.js");
		file2InNestedDir = new File(nestedDir, "myfile2InNestedDir.js");

		file.createNewFile();
		file2.createNewFile();
		fileInNestedDir.createNewFile();
		file2InNestedDir.createNewFile();

		file.deleteOnExit();
		file2.deleteOnExit();
		fileInNestedDir.deleteOnExit();
		file2InNestedDir.deleteOnExit();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		nestedDir.delete();
		nestedDir.getParentFile().delete();
		nestedDir.getParentFile().getParentFile().delete();
		nestedDir.getParentFile().getParentFile().getParentFile().delete();
	}

	@Test
	public void testSingleFileInRoot() {
		List<String> strings = parser.getFileNames("myfile.js", rootDir.getAbsolutePath());
		assertEquals("1 file", 1, strings.size());
		assertEquals("path matches", file.getAbsolutePath(), strings.get(0));
	}


	@Test
	public void testSingleFileInNestedDir() {
		List<String> strings = parser.getFileNames("a/b/c/myFileInNestedDir.js", rootDir.getAbsolutePath());
		assertEquals("1 file", 1, strings.size());
		assertEquals("path matches", nestedDir.getAbsolutePath() + "/myFileInNestedDir.js", strings.get(0));
	}

	@Test
	public void testSingleFileInNestedDirNonExisting() {
		List<String> strings = parser.getFileNames("a/b/c/this_file_does_not_exist.js", rootDir.getAbsolutePath());
		assertEquals("0 file", 0, strings.size());
	}

	@Test
	public void testSingleFileInRootNonExisting() {
		List<String> strings = parser.getFileNames("this_file_does_not_exist.js", rootDir.getAbsolutePath());
		assertEquals("0 file", 0, strings.size());
	}

	@Test
	public void testMultipleFileInRoot() {
		List<String> files = parser.getFileNames("myfile*.js", rootDir.getAbsolutePath());
		assertEquals("2 files", 2, files.size());
		for (String file : files) {
			boolean match = this.file.getAbsolutePath().equals(file) ||
					file2.getAbsolutePath().equals(file);
			assertTrue("path matches", match);
		}
	}

	//TODO add support for pattern **/myFile*Nested*.js
	@Test
	public void testMultipleFileInMultipleNestedDir() {
		List<String> files = parser.getFileNames("**/myfile*NestedDir.js", rootDir.getAbsolutePath());
		assertEquals("2 files", 2, files.size());
		for (String file : files) {
			boolean match = file2InNestedDir.getAbsolutePath().equals(file) ||
					fileInNestedDir.getAbsolutePath().equals(file);
			assertTrue("path matches " + file, match);
		}
	}

	@Test
	public void testSingleFileInMultipleNestedDir() {
		List<String> files = parser.getFileNames("**/myfileInNestedDir.js", rootDir.getAbsolutePath());
		assertEquals("1 files", 1, files.size());
		assertEquals("path matches", fileInNestedDir.getAbsolutePath(), files.get(0));
	}

	@Test
	public void testSingleFileInMultipleNestedDir_nonExisting() {
		List<String> files = parser.getFileNames("**/nonexistingfile.js", rootDir.getAbsolutePath());
		assertEquals("0 files", 0, files.size());
	}


	@Test
	public void testMultipleFileInNestedDir() {
		List<String> files = parser.getFileNames("a/b/c/myfile*.js", rootDir.getAbsolutePath());
		assertEquals("2 files", 2, files.size());
		for (String file : files) {
			boolean match =
					fileInNestedDir.getAbsolutePath().equals(file) ||
							file2InNestedDir.getAbsolutePath().equals(file);
			assertTrue("path matches", match);
		}
	}

	@Test
	public void testMultipleFileInNestedDirNonExisting() {
		List<String> files = parser.getFileNames("non_existing_dir/*.js", rootDir.getAbsolutePath());
		assertEquals("0 files", 0, files.size());
	}

}
