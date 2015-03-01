package org.robert.sharenote.webgui;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeHeaderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(ChangeHeaderServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		logger.info("ChangeHeader servlet called.");

		response.setContentType("text/html");
		response.addHeader("X-Frame-Options", "deny");
		PrintWriter pw;

		try {
			pw = response.getWriter();
			pw.println("<html>");
			pw.println("<head><title>Change Header</title></title>");
			pw.println("<body>");
			pw.println("<h1>Add a header: X-Frame-Options = deny.</h1>");
			pw.println("</body></html>");
			pw.close();
		} catch (Exception e) {
			logger.error("Major error in servlet, can't create output.");
			e.printStackTrace();
		}
	}
}
