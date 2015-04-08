package test.kms.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public TestServlet() {
		super();
	}

	@Override
	public void destroy() {
		super.destroy();
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String uri = request.getRequestURI();
		out.println("<BODY BGCOLOR=\"#FDF5E6\">\n" + "<H2>URI: " + uri
				+ "</H2>\n" + "</BODY></HTML>");
		out.close();
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
