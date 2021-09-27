package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spms.dao.MySqlBoardDao;
import spms.vo.Board;
import spms.vo.Member;

@WebServlet("/board/list")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("member");
		
		//삼항 연산자 이용 (조건이 참이면 ? 뒤에 실행, 거짓이면 : 뒤에 실행)
		String cpagenumgg = request.getParameter("pagenum") != null ? request.getParameter("pagenum")  : "1" ;
		int cpagenum = Integer.parseInt(cpagenumgg);	
		
		PageMaker pagemaker = new PageMaker();
		
		try {
			ServletContext sc = this.getServletContext();
			
			MySqlBoardDao boardDao = (MySqlBoardDao) sc.getAttribute("boardDao");

			pagemaker.setTotalcount(boardDao.totalCount(member.getName()));
			pagemaker.setPagenum(cpagenum);
			pagemaker.setCurrentblock(cpagenum);
			pagemaker.setLastblock(pagemaker.getTotalcount());
			
			pagemaker.prevnext(cpagenum);
			pagemaker.setStartPage(pagemaker.getCurrentblock());
			pagemaker.setEndPage(pagemaker.getLastblock(),pagemaker.getCurrentblock());
			
			//List<Board> list = new ArrayList<>(boards.subList((cpagenum * 5)-5,cpagenum * 5));
			//System.out.println(list);
			//request에 회원 목록 데이터 보관한다.
			request.setAttribute("boards", boardDao.selectList(cpagenum, member.getName()));
			request.setAttribute("page", pagemaker);
			request.setAttribute("viewUrl", "/board/BoardList.jsp");

		} catch (Exception e) {
			throw new ServletException(e);
		} 
	}

	
}

