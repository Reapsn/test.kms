package test.kms.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.BooleanUtils;

import test.kms.management.KeyManagement;

public class DoGetPublicKey extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String name = request.getParameter("name");
		int length = 1024;
		try {
			String strLength = request.getParameter("lenght");
			length = Integer.parseInt(strLength);
			if (length != 2048) {
				length = 1024;
			}
		} catch (Exception e) {
			;// ignore
		}
		boolean autoGenerate = true;
		try {
			String strAutoGenerate = request.getParameter("generate");
			if (strAutoGenerate != null) {				
				autoGenerate = BooleanUtils.toBoolean(strAutoGenerate);
			}
		} catch (Exception e) {
			;// ignore
		}
		String pubKey = KeyManagement.getStringPublicKey(name, length, autoGenerate);
		
		PrintWriter out = response.getWriter();
		out.println(pubKey);
		out.close();
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
