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

import spms.vo.Member;

@WebServlet("/auth/findpwd")
public class FindPwServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher rd = request.getRequestDispatcher("/auth/FindPwdForm.jsp");
		rd.forward(request, response);
	}
	
	@Override 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			ServletContext sc = this.getServletContext();
			conn = (Connection) sc.getAttribute("conn");
			stmt = conn.prepareStatement("SELECT MNAME,PWD FROM MEM_AD" + " WHERE MNAME=? AND EMAIL=? AND ID=?");
			stmt.setString(1, request.getParameter("name"));
			stmt.setString(2, request.getParameter("email"));
			stmt.setString(3, request.getParameter("id"));
			
			rs = stmt.executeQuery();
			if(rs.next()) {
				//만약 이름과 이메일과 ID가 일치하는 회원을 찾는다면 값 객체 Member에 회원 정보를 담는다.
				Member member = new Member()
						.setName(rs.getString("MNAME"))
						.setPassword(rs.getString("PWD"));
				
				response.setContentType("text/html; charset=UTF-8");
				
				request.setAttribute("member", member);
				
				RequestDispatcher rd = request.getRequestDispatcher("../auth/SuccessPWD.jsp");
				//포워드는 제어권 넘겨주고 돌아오지 않음, 인클루드는 제어권 넘어갔다가 작업 끝나면 다시 돌아옴
				rd.include(request, response);

			} else {
			RequestDispatcher rd = request.getRequestDispatcher("/auth/FindFail2.jsp");
			rd.forward(request, response);
			}
		} catch(Exception e) {
			throw new ServletException(e);
			} finally {
				try {if (rs != null) rs.close();} catch(Exception e) {}
				try {if (stmt != null) stmt.close();} catch(Exception e) {}
			}
	}
}
