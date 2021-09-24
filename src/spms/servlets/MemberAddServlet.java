package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MySqlMemberDao;
import spms.vo.Member;

//프런트 컨트롤러 적용
@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setAttribute("viewUrl", "/member/MemberForm.jsp");	
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		try {
			ServletContext sc = this.getServletContext();
			
			MySqlMemberDao memberDao = (MySqlMemberDao) sc.getAttribute("memberDao");
			
			Member member = (Member)request.getAttribute("member");
			memberDao.insert(member);

			request.setAttribute("viewUrl", "redirect:../auth/login.do");
			
		} catch (Exception e) {
			throw new ServletException(e);
		} 
	}
}
