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

import spms.dao.MySqlMemberDao;
import spms.vo.Member;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet{
	
	//웹 브라우저로부터 GET 요청이 들어오면 doGet()이 호출되어 LogInForm으로 포워딩 된다.
	@Override 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher rd = request.getRequestDispatcher("/auth/LogInForm.jsp");
		//JSP에서 다시 서블릿으로 돌아올 필요가 없기 때문에 인클루딩 대신 포워딩으로 처리한다.
		rd.forward(request, response);
	}
	
	//사용자 이메일과 암호를 입력한 후 POST 요청을 하면 doPost()가 호출된다.
	//doPost()에서는 데이터베이스로부터 회원정보를 조회한다.
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		try {
			ServletContext sc = this.getServletContext();
			
			MySqlMemberDao memberDao = (MySqlMemberDao) sc.getAttribute("memberDao");
			Member loginInfo = (Member) sc.getAttribute("loginInfo");
			
			Member member = memberDao.exist(loginInfo);
			
			if(member != null) {
				HttpSession session = request.getSession();
				session.setAttribute("member", member);

				request.setAttribute("viewUrl", "redirect:../member/list.do");
			} else {
				request.setAttribute("viewUrl", "/auth/LogInFail.jsp");
			}
		} catch(Exception e) {
			throw new ServletException(e);
		} 
	}

}
