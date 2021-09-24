package spms.servlets;

import java.io.IOException;
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

@WebServlet("/auth/findpwd")
public class FindPwServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setAttribute("viewUrl", "/auth/FindPwdForm.jsp");
	}
	
	@Override 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		try {
			ServletContext sc = this.getServletContext();

			FindDao findDao = (FindDao) sc.getAttribute("findDao");
			
			Member member = findDao.findpw(
					request.getParameter("name"), 
					request.getParameter("email"),
					request.getParameter("id"));
			
			if(member != null) {
				request.setAttribute("member", member);

				request.setAttribute("viewUrl", "../auth/SuccessPWD.jsp");

			} else {
			request.setAttribute("viewUrl", "/auth/FindFail2.jsp");
			}
		} catch(Exception e) {
			throw new ServletException(e);
		} 
	}
}
