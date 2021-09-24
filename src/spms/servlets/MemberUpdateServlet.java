package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MySqlMemberDao;
import spms.vo.Member;

@SuppressWarnings("serial")
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {
	
	//회원상세 정보 출력 (정보 불러오기)
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		try {
			//컨텍스트 초기화 매개변수의 값을 얻으려면 ServletContext 객체가 필요하다.
			//HttpServlet으로부터 상속받은 getServletContext()를 호출하여 ServletContext 객체를 준비한다.
			ServletContext sc = this.getServletContext();

			MySqlMemberDao memberDao = (MySqlMemberDao) sc.getAttribute("memberDao");
			
			Member member = memberDao.selectOne(Integer.parseInt(request.getParameter("no")));
			
			request.setAttribute("member", member);
			
			request.setAttribute("viewUrl", "/member/MemberUpdateForm.jsp");
			
		} catch(Exception e) {
			throw new ServletException(e);
			
		} 
	}
	
	//회원정보 변경하기
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		try {
			ServletContext sc = this.getServletContext();
			
			MySqlMemberDao memberDao = (MySqlMemberDao) sc.getAttribute("memberDao");
			
			Member member = (Member)request.getAttribute("member");
			memberDao.update(member);

			request.setAttribute("viewUrl", "redirect:list.do");
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}
}
