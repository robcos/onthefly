package com.robcos.onthefly;

import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeArray;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * A FileNameProvider that reads the file names from a global javascript variable defined in the source file
 *
 * @author robcos - robcos@robcos.com
 */
public class JavascriptArrayFileNameProvider implements FileNameProvider {

	private List<String> fileNames = new ArrayList<String>();


	public JavascriptArrayFileNameProvider(FileProvider fileProvider, String sourceFile, String variableName) throws IOException {

		BufferedReader source = fileProvider.getResource(sourceFile);
		try {
			Context cx = Context.enter();
			Scriptable scope = cx.initStandardObjects();
			cx.evaluateReader(scope, source, sourceFile, 1, null);
			Object x = scope.get(variableName, scope);

			if (!(x instanceof NativeArray)) {
				throw new RuntimeException("Could not find array called " + variableName + " in source file " + sourceFile);
			}
			NativeArray array = (NativeArray) x;
			for (int i = 0; i < array.getLength(); i++) {
				Object o = array.get(i, null);
				if (!(o instanceof String)) {
					throw new RuntimeException("Array contained objects which are not Strings, cannot continue");
				}
				fileNames.add("/" + o);
			}
		} finally {
			Context.exit();
		}
	}

	public List<String> getFileNames() {
		return fileNames;
	}
}
