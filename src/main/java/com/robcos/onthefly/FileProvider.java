package com.robcos.onthefly;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author robcos - robcos@robcos.com
 */
public interface FileProvider {
	List<String> getFileNames(String filePattern);

	public BufferedReader getResource(String filename) throws FileNotFoundException;
}
