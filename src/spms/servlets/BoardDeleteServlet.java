package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MySqlBoardDao;

@WebServlet("/board/delete")
public class BoardDeleteServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{		
		try {
			ServletContext sc = this.getServletContext();
			
			MySqlBoardDao boardDao = (MySqlBoardDao) sc.getAttribute("boardDao");
			
			boardDao.delete(Integer.parseInt(request.getParameter("no")));
			
			request.setAttribute("viewUrl", "redirect:list.do");
		} catch(Exception e) {
			throw new ServletException(e);
		} 
	}
	

}
