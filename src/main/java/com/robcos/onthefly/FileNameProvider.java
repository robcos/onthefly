package com.robcos.onthefly;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author robcos - roberto.cosenza@infoflexconnect.se
 */
public interface FileNameProvider {
	public List<String> getFileNames();
}
