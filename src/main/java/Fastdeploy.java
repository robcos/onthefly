import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * @author robcos - roberto.cosenza@infoflexconnect.se
 */
class Fastdeploy {
	private FilePatternParser filePatternParser = new FilePatternParser();
	private String contentType;
	private String includePattern;
	private String rootDir;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getIncludePattern() {
		return includePattern;
	}

	public void setIncludePattern(String includePattern) {
		this.includePattern = includePattern;
	}

	public String getRootDir() {
		return rootDir;
	}

	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}


	protected List<String> getFileNames() {
		String include = getIncludePattern();
		String[] lines = include.split("\n");
		List<String> fileList = new ArrayList<String>();
		for (String line : lines) {
			List<String> list = this.filePatternParser.getFileNames(line, getRootDir());
			for (String file : list) {
				if (!fileList.contains(file)) {
					fileList.add(file);
				}
			}
		}
		return fileList;
	}

	private String getContent() throws IOException {
		StringBuffer content = new StringBuffer();
		for (String filename : getFileNames()) {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				content.append(line);
				content.append("\n");
			}
		}
		return content.toString();
	}

	protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		httpServletResponse.setContentType(getContentType());
		servletOutputStream.print(getContent());
		servletOutputStream.close();
	}
}
