package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spms.dao.FindDao;
import spms.vo.Member;

@WebServlet("/auth/findid")
public class FindIdServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setAttribute("viewUrl", "/auth/FindIdForm.jsp");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{		

		try {
			ServletContext sc = this.getServletContext();
			
			FindDao findDao = (FindDao) sc.getAttribute("findDao");
			
			Member member = findDao.findid(
					request.getParameter("name"),
					request.getParameter("email"));
			
			if(member != null) {
				request.setAttribute("member", member);
	
				request.setAttribute("viewUrl", "../auth/SuccessID.jsp");
				
			} else {
				request.setAttribute("viewUrl", "/auth/FindFail.jsp");
			}
		} catch(Exception e) {
			throw new ServletException(e);
		} 
	}
}
