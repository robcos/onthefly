import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author robcos - roberto.cosenza@infoflexconnect.se
 */
public class FastdeployServlet extends HttpServlet {

	private FilePatternParser filePatternParser = new FilePatternParser();

	@Override
	protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		httpServletResponse.setContentType(getContentType());
		servletOutputStream.print(getContent());
		servletOutputStream.close();
	}

	private String getContent() throws IOException {
		StringBuffer content = new StringBuffer();
		for (String filename : getFileNames()) {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				content.append(line);
			}
			content.append("\n");
		}
		return content.toString();
	}

	private String getContentType() {
		return getInitParameter(Parameters.CONTENT_TYPE, "text/javascript");
	}

	protected String getInitParameter(String key, String defaultValue) {
		return getInitParameter(key) != null ? getInitParameter(key) : defaultValue;
	}

	protected List<String> getFileNames() {
		String include = getInitParameter(Parameters.INCLUDE_PATTERN);
		String[] lines = include.split("\n");
		List<String> files = new ArrayList<String>();
		for (String line : lines) {
			files.addAll(filePatternParser.getFileNames(line, getServletContext().getRealPath("")));
		}
		return files;
	}
}
