package com.robcos.onthefly;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import com.yahoo.platform.yui.compressor.CssCompressor;

/**
 * @author robcos - roberto.cosenza@infoflexconnect.se
 */
public class Fastdeploy {
	private Log log = LogFactory.getLog(this.getClass());
	private FileNameProvider fileNameProvider;
	private String contentType;
	private String cache;
	private boolean useCache = false;
	private long lastModified;
	private boolean useJavaScriptCompression;
	private boolean useCssCompression;
	private static long MS_IN_A_YEAR = 1000L * 60L * 60L * 24L * 365L;

	public long getLastModified() {
		return useCache ? lastModified : -1;
	}

	public void setFileNameProvider(FileNameProvider fileNameProvider) {
		this.fileNameProvider = fileNameProvider;
	}

	public void setUseCssCompression(boolean useCssCompression) {
		this.useCssCompression = useCssCompression;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public boolean isUseCache() {
		return useCache;
	}

	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}

	public void setUseJavaScriptCompression(boolean useJavaScriptCompression) {
		this.useJavaScriptCompression = useJavaScriptCompression;
	}


	private String getContent() throws IOException {
		if (cache != null) {
			log.debug("Returning cached content");
			return cache;
		}
		StringBuffer content = new StringBuffer();
		for (String filename : fileNameProvider.getFileNames()) {
			BufferedReader br = fileNameProvider.getResource(filename);
			String line;
			while ((line = br.readLine()) != null) {
				content.append(line);
				content.append("\n");
			}
		}
		String returnValue = content.toString();
		if (useJavaScriptCompression) {
			returnValue = compressJS(returnValue);
		}
		if (useCssCompression) {
			returnValue = compressCSS(returnValue);
		}

		return returnValue;
	}


	private String compressJS(String returnValue) throws IOException {
		JavaScriptCompressor c = new JavaScriptCompressor(new StringReader(returnValue), new ErrorReporter() {
			public void warning(String s, String s1, int i, String s2, int i1) {
				log.warn("Warning: " + s + ":" + s1 + ":" + i + ":" + s2 + ":" + i1);
			}

			public void error(String s, String s1, int i, String s2, int i1) {
				log.error("Error: " + s + ":" + s1 + ":" + i + ":" + s2 + ":" + i1);
			}

			public EvaluatorException runtimeError(String s, String s1, int i, String s2, int i1) {
				log.error("Runtime Error: " + s + ":" + s1 + ":" + i + ":" + s2 + ":" + i1);
				return new EvaluatorException(s, s1, i);
			}
		});
		StringWriter stringWriter = new StringWriter();
		c.compress(stringWriter, -1, true, true, false, false);
		returnValue = stringWriter.toString();
		return returnValue;
	}

	private String compressCSS(String returnValue) throws IOException {
		CssCompressor c = new CssCompressor(new StringReader(returnValue));
		StringWriter stringWriter = new StringWriter();
		c.compress(stringWriter, -1);
		returnValue = stringWriter.toString();
		return returnValue;
	}

	public void handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		httpServletResponse.setContentType(getContentType());
		if (useCache) {
			httpServletResponse.setDateHeader("Expires", System.currentTimeMillis() + MS_IN_A_YEAR);
			httpServletResponse.setHeader("Cache-Control", "Cache-Control: public, max-age=31536000");
		} else {
			httpServletResponse.setDateHeader("Expires", System.currentTimeMillis() - MS_IN_A_YEAR);
			httpServletResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		}
		servletOutputStream.print(getContent());
		servletOutputStream.close();
	}

	public void init() throws IOException {
		log.trace("init");
		lastModified = System.currentTimeMillis();

		if (useCache) {
			log.info("Filling cache");
			cache = getContent();
		}
	}
}
