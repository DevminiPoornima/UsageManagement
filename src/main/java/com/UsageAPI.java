package com;

import com.Usage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UsageAPI
 */
@WebServlet("/UsageAPI")

public class UsageAPI extends HttpServlet {
		private static final long serialVersionUID = 1L;
	       
		Usage usageObj = new Usage();
		
		private static Map getParasMap(HttpServletRequest request) {
	  		Map<String, String> map = new HashMap<String, String>();

	  		try {
	  			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
	  			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
	  			scanner.close();

	  			String[] params = queryString.split("&");

	  			for (String param : params) {
	  				String[] p = param.split("=");
	  				map.put(p[0], p[1]);
	  			}
	  		} catch (Exception e) {

	  		}

	  		return map;
	  	}
		
	    public UsageAPI() {
	        super();
	        // TODO Auto-generated constructor stub
	    }

		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			response.getWriter().append("Served at: ").append(request.getContextPath());
		}

		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			
			String output = usageObj.insertUsage(      
					request.getParameter("RefNo"),
					request.getParameter("Units"),
					request.getParameter("Month"), 
					request.getParameter("Amount"));
					
		 
			response.getWriter().write(output);
		}

		/**
		 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
		 */
		protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub

			Map paras = getParasMap(request); 
			
			String output = usageObj.updateUsage(paras.get("hidUsageIDSave").toString(),
					paras.get("RefNo").toString(),     
					paras.get("Units").toString(),
					paras.get("Month").toString(),  
					paras.get("Amount").toString());
					
			 	 
			response.getWriter().write(output);
		}

		/**
		 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
		 */
		protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
					
			Map paras = getParasMap(request); 
			 
			String output = usageObj.deleteUsage(paras.get("UsageId").toString());  
			 
			response.getWriter().write(output);
		}
		
		
		

	}


