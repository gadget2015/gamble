package org.robert.tips.stryktips.webstartlaunch;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;

import org.junit.Test;

public class LaunchServletTest {
	@Test
	public void shouldReadFile() throws Exception {
		// Given
		LaunchServlet servlet = new LaunchServlet();

		// When
		String data = servlet.read();

		// Then
		Assert.assertNotNull(data);
	}

	@Test
	public void shouldGetRequestParameters() throws IOException {
		// Given
		LaunchServlet servlet = new LaunchServlet();
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse respone = mock(HttpServletResponse.class);
		when(request.getParameter("systemname")).thenReturn("R-4-4");
		StringWriter writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		when(respone.getWriter()).thenReturn(printWriter);

		// When
		try {
			servlet.doGet(request, respone);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		// Then
		String responeText = writer.getBuffer().toString();
		Assert.assertTrue("Should contain system name.", responeText.contains("R-4-4"));
	}
}
