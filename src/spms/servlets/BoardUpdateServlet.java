package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
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

import spms.dao.MySqlBoardDao;
import spms.vo.Board;

@WebServlet("/board/update")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//보드 상세정보 출력 (정보 불러오기)
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		
		try {
			ServletContext sc = this.getServletContext();
			
			MySqlBoardDao boardDao = (MySqlBoardDao) sc.getAttribute("boardDao");
			Board board = boardDao.selectOne(Integer.parseInt(request.getParameter("no")));
			
			request.setAttribute("board", board);
			
			request.setAttribute("viewUrl", "/board/BoardUpdateForm.jsp");
			
		} catch(Exception e) {
			throw new ServletException(e);
		}		
	}
	
	//게시글 상세정보 변경하기
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	
		request.setCharacterEncoding("UTF-8");
		
		try {
			ServletContext sc = this.getServletContext();

			MySqlBoardDao boardDao = (MySqlBoardDao) sc.getAttribute("boardDao");
			Board board = (Board)request.getAttribute("board");
			boardDao.update(board);

			request.setAttribute("viewUrl", "redirect:list.do");
			
		} catch(Exception e) {
			throw new ServletException(e);
		} 
	}
}
