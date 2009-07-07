package com.robcos.onthefly;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author robcos - robcos@robcos.com
 */
public abstract class AbstractFileNameProvider implements com.robcos.onthefly.FileNameProvider {
	private Log log = LogFactory.getLog(this.getClass());

	public List<String> getFileNames(HttpServletRequest request) {
		return getFileNames();
	}

	protected List<String> getFileNames() {
		throw new RuntimeException("Method not implemented. You must implement getFileNames() or getFileNames(HttpServletRequest)");
	}

}
