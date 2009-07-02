package com.robcos.onthefly;

import com.robcos.onthefly.Fastdeploy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author robcos - roberto.cosenza@infoflexconnect.se
 */
public class FastdeployServlet extends HttpServlet {
	private Log log = LogFactory.getLog(this.getClass());
	private Fastdeploy fastdeploy;

	@Override
	public void init() throws ServletException {
		fastdeploy = new Fastdeploy();
		fastdeploy.setContentType(getInitParameter(Parameters.CONTENT_TYPE, "text/javascript"));
		String rootDir = getInitParameter(Parameters.ROOT_DIR, getServletContext().getRealPath(""));
		boolean useClasspath = getInitParameter(Parameters.USE_CLASSPATH, false);
		FileProvider filePatternParser;
		if (useClasspath) {
			System.err.println("ClassPathFilePatternParser");
			filePatternParser = new ClassPathFileProvider();
		} else {
			System.err.println("FileSystemPatternParser on " + rootDir);
			filePatternParser = new FileSystemFileProvider(rootDir);
		}
		FileNameProvider fileNameProvider = new FileNameProvider(getInitParameter(Parameters.INCLUDE_PATTERN), filePatternParser);
		fastdeploy.setFileNameProvider(fileNameProvider);
		try {
			fastdeploy.init();
		} catch (IOException e) {
			throw new ServletException("Could not init fastdeploy", e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
		fastdeploy.handleRequest(httpServletRequest, httpServletResponse);
	}


	protected String getInitParameter(String key, String defaultValue) {
		return getInitParameter(key) != null ? getInitParameter(key) : defaultValue;
	}

	protected boolean getInitParameter(String key, boolean defaultValue) {
		String value = getInitParameter(key);
		log.debug("parameter " + key + " is " + value);
		if (value == null) {
			return defaultValue;
		}
		return "true".equals(value);
	}


}
