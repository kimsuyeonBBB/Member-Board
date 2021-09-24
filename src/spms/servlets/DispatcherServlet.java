package spms.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spms.controls.BoardAddController;
import spms.controls.BoardDeleteController;
import spms.controls.BoardListController;
import spms.controls.BoardUpdateController;
import spms.controls.Controller;
import spms.controls.FindIdController;
import spms.controls.FindPwdController;
import spms.controls.LoginController;
import spms.controls.LogoutController;
import spms.controls.MemberAddController;
import spms.controls.MemberDeleteController;
import spms.controls.MemberListController;
import spms.controls.MemberUpdateController;
import spms.vo.Board;
import spms.vo.Member;

//애노테이션을 사용하여 프런트 컨트롤러의 배치 URL을 "*.do"로 지정한다.
//이는 클라이언트의 요청 중에서 서블릿 경로 이름이 .do로 끝나는 경우는 DispatcherServlet이 처리하겠다는 의미이다.
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet{

	//doGet() doPost()가 아니라 service() 메서드를 오버라이딩 하고 있다.
	//ServletRequest가 아닌 HttpServletRequest이다. 
	//즉, Servlet 인터페이스에 선언된 메서드가 아니다.  ==> 이 메서드는 서블릿 컨테이너(톰캣)가 직접 호출하지 않는다는 의미이다.
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html; charset=UTF-8");
		String servletPath = request.getServletPath();
		
		String cpagenumgg = request.getParameter("pagenum") != null ? request.getParameter("pagenum")  : "1" ;
		int cpagenum = Integer.parseInt(cpagenumgg);
		
		try {
			//프런트 컨트롤러와 페이지 컨트롤러 사이에 데이터나 객체를 주고 받을 때 사용할 Map 객체를 준비한다.
			//즉, MemberListController가 사용할 객체를 준비하여 Map 객체에 담아 전달해준다.
			ServletContext sc = this.getServletContext();
			HashMap<String,Object> model = new HashMap<String,Object>();
			model.put("memberDao",sc.getAttribute("memberDao"));
			model.put("boardDao", sc.getAttribute("boardDao"));
			model.put("findDao", sc.getAttribute("findDao"));
			model.put("session",request.getSession());

			//페이지 컨트롤러는 Controller의 구현체이기 때문에 인터페이스 타입의 참조 변수를 선언한다.
			Controller pageController = null;

			//회원 목록 요청을 처리할 페이지 컨트롤러를 준비한다.
			if("/member/list.do".equals(servletPath)) {				
				pageController = new MemberListController();
				model.put("cpagenum", cpagenum);
			} else if("/member/add.do".equals(servletPath)){
				pageController = new MemberAddController();
				if(request.getParameter("email") != null) {
					//프런트 컨트롤러의 역할 중 하나는 페이지 컨트롤러가 필요한 데이터를 미리 준비하는 것이다.
					//요청 매개변수의 값을 꺼내서 VO 객체에 담고, "member"라는 키로 ServletRequest에 보관하였다.
					model.put("member", new Member()
						.setEmail(request.getParameter("email"))
						.setId(request.getParameter("id"))
						.setPassword(request.getParameter("password"))
						.setName(request.getParameter("name")));
				}
			} else if("/member/update.do".equals(servletPath)) {
				pageController = new MemberUpdateController();
				if(request.getParameter("email") != null) {
					model.put("member", new Member()
						.setNo(Integer.parseInt(request.getParameter("no")))
						.setEmail(request.getParameter("email"))
						.setId(request.getParameter("id"))
						.setPassword(request.getParameter("password"))
						.setName(request.getParameter("name")));
				} else {
					model.put("no", new Integer(request.getParameter("no")));
				}
			} else if("/member/delete.do".equals(servletPath)) {
				pageController = new MemberDeleteController();
				model.put("no", new Integer(request.getParameter("no")));
			} else if("/auth/login.do".equals(servletPath)) {
				pageController = new LoginController();
				if(request.getParameter("id") != null) {
					model.put("loginInfo", new Member()
							.setId(request.getParameter("id"))
							.setPassword(request.getParameter("password")));
				}
				
			} else if("/auth/logout.do".equals(servletPath)) {
				pageController = new LogoutController();
				
			} else if("/board/list.do".equals(servletPath)) {
				pageController = new BoardListController();
				model.put("cpagenum", cpagenum);
			} else if("/board/add.do".equals(servletPath)) {
				pageController = new BoardAddController();				
				if(request.getParameter("title") != null) {
					model.put("board", new Board()
							.setTitle(request.getParameter("title"))
							.setStory(request.getParameter("story")));
					
				}
			} else if("/board/update.do".equals(servletPath)) {
				pageController = new BoardUpdateController();
				if(request.getParameter("title") != null) {
					model.put("board", new Board()
							.setNo(Integer.parseInt(request.getParameter("no")))
							.setTitle(request.getParameter("title"))
							.setStory(request.getParameter("story")));
				}
				else {
					model.put("no", new Integer(request.getParameter("no")));
				}
			} else if("/board/delete.do".equals(servletPath)) {
				pageController = new BoardDeleteController();
				model.put("no", new Integer(request.getParameter("no")));
			} else if("/auth/findid.do".equals(servletPath)) {
				pageController = new FindIdController();
				if(request.getParameter("name") != null) {					
					model.put("member", new Member()
							.setName(request.getParameter("name"))
							.setEmail(request.getParameter("email")));
				}
			} else if("/auth/findpwd.do".equals(servletPath)) {
				pageController = new FindPwdController();
				if(request.getParameter("name") != null) {
					model.put("member", new Member()
							.setName(request.getParameter("name"))
							.setEmail(request.getParameter("email"))
							.setId(request.getParameter("id")));
				}
			}
			
			//MemberListController가 일반 클래스이기 때문에 메서드를 호출해야 한다. (인터페이스에 정해진대로 execute() 메서드를 호출)
			//execute()를 호출할 때 페이지 컨트롤러를 위해 준비한 Map 객체를 매개변수로 넘긴다.
			//execute()의 반환값은 화면 출력을 수행하는 JSP의 URL이다.
			String viewUrl = pageController.execute(model);
			
			//페이지 컨트롤러의 실행이 끝난 다음 Map 객체에 보관되어 있는 데이터나 객체를 JSP가 사용할 수 있도록 ServletRequest에 복사한다.
			for(String key : model.keySet()) {
				request.setAttribute(key, model.get(key));
			}
			
			if(viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			} else {
				RequestDispatcher rd = request.getRequestDispatcher(viewUrl);
				rd.include(request, response);
			}
		//프런트 컨트롤러에서 오류 처리를 담당하기 때문에 페이지 컨트롤러를 작성할 때는 오류처리 코드를 넣을 필요가 없다.
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}
	
}
