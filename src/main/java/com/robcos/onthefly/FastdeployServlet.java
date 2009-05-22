package com.robcos.onthefly;

import com.robcos.onthefly.Fastdeploy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author robcos - roberto.cosenza@infoflexconnect.se
 */
public class FastdeployServlet extends HttpServlet {

	private Fastdeploy fastdeploy;

	@Override
	public void init() throws ServletException {
		fastdeploy = new Fastdeploy();
		fastdeploy.setContentType(getInitParameter(Parameters.CONTENT_TYPE, "text/javascript"));
		fastdeploy.setIncludePattern(getInitParameter(Parameters.INCLUDE_PATTERN));
		fastdeploy.setRootDir(getInitParameter(Parameters.ROOT_DIR, getServletContext().getRealPath("")));
	}

	@Override
	protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
		fastdeploy.handleRequest(httpServletRequest, httpServletResponse);
	}


	protected String getInitParameter(String key, String defaultValue) {
		return getInitParameter(key) != null ? getInitParameter(key) : defaultValue;
	}


}
