package com.robcos.onthefly;

import java.util.List;

/**
 * @author robcos - roberto.cosenza@infoflexconnect.se
 */
public interface FilePatternParser {
	List<String> getFileNames(String filePattern);
}
