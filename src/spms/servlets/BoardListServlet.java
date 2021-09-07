package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spms.vo.Board;
import spms.vo.Member;

@WebServlet("/board/list")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Connection conn = null;
		//Statement stmt = null;
		ResultSet rs = null;
		
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("member");
		
		PreparedStatement stmt = null;
		
		try {
			ServletContext sc = this.getServletContext();
			
			//서블릿은 ServletContext 보관소에서 DB 커넥션을 꺼낸다.
			conn = (Connection) sc.getAttribute("conn");
			stmt = conn.prepareStatement("SELECT MNO,TITLE,CRE_DATE,STORY,MNAME FROM BOARDS WHERE MNAME=?");
			stmt.setString(1,member.getName());
			rs = stmt.executeQuery();	
			
			response.setContentType("text/html; charset=UTF-8");
			//회원목록을 담을 ArrayList 객체를 준비한다.
			ArrayList<Board> boards = new ArrayList<Board>();
			
			//데이터베이스에서 회원정보를 가져와 Member에 담는다.
			//그리고 Member 객체를 ArrayList에 추가한다.
			while(rs.next()) {
				boards.add(new Board()
						.setNo(rs.getInt("MNO"))
						.setTitle(rs.getString("TITLE"))
						.setCreatedDate(rs.getDate("CRE_DATE")) 
						.setStory(rs.getString("STORY"))
						.setName(rs.getString("MNAME")) );
			}
			
			//request에 회원 목록 데이터 보관한다.
			request.setAttribute("boards", boards);
			
			//JSP로 출력을 위임한다.
			//다른 서블릿이나 JSP로 작업을 위임할 때 사용하는 객체가 RequestDispatcher이다.
			RequestDispatcher rd = request.getRequestDispatcher("/board/BoardList.jsp");
			//포워드는 제어권 넘겨주고 돌아오지 않음, 인클루드는 제어권 넘어갔다가 작업 끝나면 다시 돌아옴
			rd.include(request, response);

		} catch (Exception e) {
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		} finally {
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
		}
	}
}
