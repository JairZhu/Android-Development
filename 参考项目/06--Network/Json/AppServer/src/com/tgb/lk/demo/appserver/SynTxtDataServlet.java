package com.tgb.lk.demo.appserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SynTxtDataServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(request
				.getInputStream(), "UTF-8"));
		// Êý¾Ý
		String retData = null;
		String responseData = "";
		while ((retData = in.readLine()) != null) {
			responseData += retData;
		}
		in.close();
		
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("POST method");
		out.print(responseData);
		out.flush();
		out.close();
	}

}
