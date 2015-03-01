package org.robert.tips.stryktips.webstartlaunch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet used to creat a dynamic JNLP file.
 * 
 * @author Robert
 * 
 */
public class LaunchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(LaunchServlet.class);

	/**
	 * Method to receive get requests from the web server (Passes them onto the
	 * doPost method)
	 * 
	 * @param req
	 *            The HttpServletRequest which contains the information
	 *            submitted via get
	 * @param res
	 *            A response containing the required response data for this
	 *            request
	 **/
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String systemname = req.getParameter("systemname");
		logger.debug("LaunchServlet called for system = " + systemname);

		if (systemname == null) {
			throw new ServletException("Missing required parameter, e.g 'systemname'");
		}
		
		// Replace dynamic content.
		String jnlpContent = read();
		jnlpContent = jnlpContent.replace("$$arg1", systemname);

		// Wrap up response
		PrintWriter out = res.getWriter(); // PrintWriter to write text to the
											// response
		out.println(jnlpContent);
		res.setContentType("application/x-java-jnlp-file");
		out.close(); // Close the PrintWriter

	}

	/** Read the contents of the given file. */
	public String read() throws IOException {
		StringBuilder text = new StringBuilder();
		String NL = System.getProperty("line.separator");

		// Get file
		String resource = "stryktipsfinder-desktop.jnlp";
		InputStream is = null;

		try {
			ClassLoader cl = this.getClass().getClassLoader();

			try {
				URL resourceURL = cl.getResource(resource);
				is = resourceURL.openStream();
			} catch (NullPointerException e) {
				System.out
						.println("Fail getting resource by using classloader.getResource().");
				// This happens when the application runs from command-line.
				// There seams not to be any working classloader.
				try {
					File systemFileName = new File(resource);
					FileInputStream fis = new FileInputStream(systemFileName);
					is = fis;
				} catch (java.security.AccessControlException e2) {
					e2.printStackTrace();
					throw new RuntimeException(
							"Error while getting text message resource.");
				}
			}
		} catch (NullPointerException e) {
			throw new RuntimeException("Resource not found: " + resource, e);
		} catch (IOException e) {
			throw new RuntimeException("Resource not found: " + resource, e);
		}

		// Read file
		Scanner scanner = new Scanner(is, "UTF-8");
		try {
			while (scanner.hasNextLine()) {
				text.append(scanner.nextLine() + NL);
			}
		} finally {
			scanner.close();
		}

		String fileContent = text.toString();

		return fileContent;
	}

}
