package test.kms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.BooleanUtils;

import test.kms.cipher.CipherUtils;
import test.kms.management.KeyManagement;
import test.kms.util.StringUtils;

public class DoGetKeyPair extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			response.setContentType("text/html");
			String data = request.getParameter("data");
			data = new String(CipherUtils.decrypt(Base64.getDecoder().decode(
					data)));
			String name = StringUtils.getValueFromDn(data, "name");
			int length = 1024;
			try {
				String strLength = StringUtils.getValueFromDn(data, "length");
				length = Integer.parseInt(strLength);
				if (length != 2048) {
					length = 1024;
				}
			} catch (Exception e) {
				;// ignore
			}
			boolean genNewKey = false;
			try {
				String strGenNewKey = StringUtils.getValueFromDn(data, "gennewkey");
				genNewKey = BooleanUtils.toBoolean(strGenNewKey);
			} catch (Exception e) {
				;// ignore
			}

			String priKey = KeyManagement.getStringPriavteKey(name, length,
					genNewKey, true);
			String pubKey = KeyManagement.getStringPublicKey(name, length,
					false, false);

			PrintWriter out = response.getWriter();

			out.print(Base64.getEncoder().encodeToString(
					CipherUtils.encrypt((priKey + " " + pubKey).getBytes())));
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
