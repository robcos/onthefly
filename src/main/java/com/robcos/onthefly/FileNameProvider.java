package com.robcos.onthefly;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author robcos - robcos@robcos.com
 */
public interface FileNameProvider {
	public List<String> getFileNames(HttpServletRequest request);
}
