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

import spms.dao.MySqlMemberDao;
import spms.vo.Member;

//프런트 컨트롤러 적용
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override 
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{		
		
		//삼항 연산자 이용 (조건이 참이면 ? 뒤에 실행, 거짓이면 : 뒤에 실행)
		String cpagenumgg = request.getParameter("pagenum") != null ? request.getParameter("pagenum")  : "1" ;
		int cpagenum = Integer.parseInt(cpagenumgg);
		
		PageMaker pagemaker = new PageMaker();

		try {
			ServletContext sc = this.getServletContext();
			
			//직접 MemberDao 객체를 생성하지 않고 ServletContext에 저장된 Dao 객체를 꺼내 쓰는 것으로 변경하였다.
			MySqlMemberDao memberDao = (MySqlMemberDao) sc.getAttribute("memberDao");
			
			pagemaker.setTotalcount(memberDao.totalCount(cpagenum));
			pagemaker.setPagenum(cpagenum);
			pagemaker.setCurrentblock(cpagenum);
			pagemaker.setLastblock(pagemaker.getTotalcount());
			
			pagemaker.prevnext(cpagenum);
			pagemaker.setStartPage(pagemaker.getCurrentblock());
			pagemaker.setEndPage(pagemaker.getLastblock(),pagemaker.getCurrentblock());

			
			//request에 회원 목록 데이터 보관한다.
			request.setAttribute("members",memberDao.selectList(cpagenum,pagemaker));
			request.setAttribute("page", pagemaker);
			request.setAttribute("viewUrl", "/member/MemberList.jsp");

		} catch (Exception e) {
			//Dao를 실행하다가 오류가 발생한다면, 기존의 오류를 ServletException 객체에 담아서 던지도록 하였다.
			throw new ServletException(e);
		} 
		
	}
	

}