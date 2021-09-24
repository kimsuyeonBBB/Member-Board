package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

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

@WebServlet("/board/add")
public class BoardAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setAttribute("viewUrl", "/board/BoardForm.jsp");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		HttpSession session = request.getSession();
		
		try {
			ServletContext sc = this.getServletContext();
			
			Member member = (Member)session.getAttribute("member");
			
			MySqlBoardDao boardDao = (MySqlBoardDao) sc.getAttribute("boardDao");
			Board board = (Board)request.getAttribute("board");
			board.setName(member.getName());
			boardDao.insert(board);

			request.setAttribute("viewUrl", "redirect:list.do");
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
