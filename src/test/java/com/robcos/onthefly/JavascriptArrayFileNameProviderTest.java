package com.robcos.onthefly;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.List;

/**
 * @author robcos - robcos@robcos.com
 */
public class JavascriptArrayFileNameProviderTest extends TestCase {
	JavascriptArrayFileNameProvider provider;

	@Before
	@Override
	public void setUp() throws Exception {
		provider = new JavascriptArrayFileNameProvider(new ClassPathFileProvider(), "/filelist.js", "myJsfiles");
	}

	@After
	@Override
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetFileNames() {
		List<String> list = provider.getFileNames();
		assertEquals("/firstfile.js", list.get(0));
		assertEquals("/com/secondfile.js", list.get(1));
	}

}
