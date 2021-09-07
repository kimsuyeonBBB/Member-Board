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

import spms.vo.Member;

@SuppressWarnings("serial")
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {
	
	//회원상세 정보 출력 (정보 불러오기)
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			//컨텍스트 초기화 매개변수의 값을 얻으려면 ServletContext 객체가 필요하다.
			//HttpServlet으로부터 상속받은 getServletContext()를 호출하여 ServletContext 객체를 준비한다.
			ServletContext sc = this.getServletContext();
			conn = (Connection) sc.getAttribute("conn");
			stmt = conn.createStatement();
			//요청 매개변수로 넘어온 회원 번호를 가지고 회원 정보를 질의한다.
			rs = stmt.executeQuery("SELECT MNO,MNAME,EMAIL,ID,PWD,CRE_DATE FROM MEM_AD" + " WHERE MNO=" + request.getParameter("no"));
			//단 한명의 회원정보를 가져오기 때문에 next()를 한번만 호출한다.
			
			if(rs.next()) {
				request.setAttribute("member", 
						new Member()
						.setNo(rs.getInt("MNO"))
						.setName(rs.getString("MNAME"))
						.setEmail(rs.getString("EMAIL"))
						.setId(rs.getString("ID"))
						.setPassword(rs.getString("PWD"))
						.setCreatedDate(rs.getDate("CRE_DATE")));
			} else {
				throw new Exception("해당 번호의 회원을 찾을 수 없습니다.");
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberUpdateForm.jsp");
			rd.forward(request, response);
			
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
			
		} finally {
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
		}
	}
	
	//회원정보 변경하기
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Connection conn = null;
		PreparedStatement stmt = null;
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			ServletContext sc = this.getServletContext();
			conn = (Connection) sc.getAttribute("conn");
			stmt = conn.prepareStatement("UPDATE MEM_AD SET MNAME=?,EMAIL=?,ID=?,PWD=?, MOD_DATE=now()" + " WHERE MNO=?");
			stmt.setString(1, request.getParameter("name"));
			stmt.setString(2, request.getParameter("email"));
			stmt.setString(3, request.getParameter("id"));
			stmt.setString(4, request.getParameter("password"));
			stmt.setInt(5, Integer.parseInt(request.getParameter("no")));
			
			stmt.executeUpdate();
			
			response.sendRedirect("list");
			
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
			
		} finally {
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
		}
	}
}
