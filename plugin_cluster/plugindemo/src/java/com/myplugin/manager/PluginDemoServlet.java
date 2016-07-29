package com.myplugin.manager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PluginDemoServlet extends HttpServlet{

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        System.out.println("console info: 请求SampleServlet GET Method");
        out.println("GET Method: these info are transmitted into client.");
        out.flush();
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // super.doPost(request, response); // should be commented out
        
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        System.out.println("请求 PluginDemoServlet POST Method");
        out.print("请求 PluginDemoServlet POST Method");
        out.flush();
    }

	@Override
	public void init() throws ServletException {
		System.out.println("servlet init successfully.");
	}
}
