package com.tgb.lk.demo.appserver;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SynDataServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("---------get-------------");
		// 处理中文乱码问题解决办法
		String name = new String(request.getParameter("name").getBytes(
				"iso-8859-1"), "UTF-8");
		String age = request.getParameter("age");
		String classes = new String(request.getParameter("classes").getBytes(
				"iso-8859-1"), "UTF-8");
		System.out.println("-------" + name + age + classes + "--------");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("GET method ");
		out.print("name=" + name + ",age=" + age + ",classes=" + classes);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("---------post-------------");
		String name = new String(request.getParameter("name").getBytes(
				"iso-8859-1"), "UTF-8");
		String age = request.getParameter("age");
		String classes = new String(request.getParameter("classes").getBytes(
				"iso-8859-1"), "UTF-8");
		System.out.println("--------" + name + age + classes + "---------");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("POST method");
		out.print("name=" + name + ",age=" + age + ",classes=" + classes);
		out.flush();
		out.close();
	}

}
