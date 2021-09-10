package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.vo.Member;

@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override 
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{		
		Connection conn = null;
		//Statement stmt = null;
		ResultSet rs = null;
		
		//삼항 연산자 이용 (조건이 참이면 ? 뒤에 실행, 거짓이면 : 뒤에 실행)
		String cpagenumgg = request.getParameter("pagenum") != null ? request.getParameter("pagenum")  : "1" ;
		int cpagenum = Integer.parseInt(cpagenumgg);
		System.out.println(cpagenum);
		
		//회원목록을 담을 ArrayList 객체를 준비한다.
		ArrayList<Member> members = new ArrayList<Member>();
		ArrayList<Member> totalCount = new ArrayList<Member>();
		PageMaker pagemaker = new PageMaker();
		
		PreparedStatement stmt = null;
		try {
			ServletContext sc = this.getServletContext();
			
			//서블릿은 ServletContext 보관소에서 DB 커넥션을 꺼낸다.
			conn = (Connection) sc.getAttribute("conn");
			stmt = conn.prepareStatement("SELECT MNO,MNAME,EMAIL,CRE_DATE FROM MEM_AD ORDER BY MNO ASC LIMIT ?,?");
			stmt.setInt(1, (cpagenum-1)*5);
			stmt.setInt(2, pagemaker.getContentnum());
			rs = stmt.executeQuery();
			
			response.setContentType("text/html; charset=UTF-8");
			
			//데이터베이스에서 회원정보를 가져와 Member에 담는다.
			//그리고 Member 객체를 ArrayList에 추가한다.
			while(rs.next()) {
				members.add(new Member()
						.setNo(rs.getInt("MNO"))
						.setName(rs.getString("MNAME"))
						.setEmail(rs.getString("EMAIL")) 
						.setCreatedDate(rs.getDate("CRE_DATE")) );
			}
			
			conn = (Connection) sc.getAttribute("conn");
			stmt = conn.prepareStatement("SELECT MNO,MNAME,EMAIL,CRE_DATE FROM MEM_AD ORDER BY MNO ASC");
			rs = stmt.executeQuery();
						
			//데이터베이스에서 회원정보를 가져와 Member에 담는다.
			//그리고 Member 객체를 ArrayList에 추가한다.
			while(rs.next()) {
				totalCount.add(new Member()
						.setNo(rs.getInt("MNO"))
						.setName(rs.getString("MNAME"))
						.setEmail(rs.getString("EMAIL")) 
						.setCreatedDate(rs.getDate("CRE_DATE")) );
			}
			
			/*---------페이지 객체에 새로운 정보 다시 지정해주는 부분------------------*/
			int totalnum = totalCount.size();
			System.out.println(totalnum);
			pagemaker.setTotalcount(totalnum);
			pagemaker.setPagenum(cpagenum);
			pagemaker.setCurrentblock(cpagenum);
			pagemaker.setLastblock(pagemaker.getTotalcount());
			
			pagemaker.prevnext(cpagenum);
			pagemaker.setStartPage(pagemaker.getCurrentblock());
			pagemaker.setEndPage(pagemaker.getLastblock(),pagemaker.getCurrentblock());
			
			System.out.println("cpagenum:" + cpagenum);
			System.out.println("pagenum:" + pagemaker.getPagenum());
			
			//request에 회원 목록 데이터 보관한다.
			request.setAttribute("members", members);
			request.setAttribute("page", pagemaker);
			
			//JSP로 출력을 위임한다.
			//다른 서블릿이나 JSP로 작업을 위임할 때 사용하는 객체가 RequestDispatcher이다.
			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberList.jsp");
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
