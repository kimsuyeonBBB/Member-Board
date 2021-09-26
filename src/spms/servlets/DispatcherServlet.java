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

import spms.bind.DataBinding;
import spms.bind.ServletRequestDataBinder;
import spms.context.ApplicationContext;
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
import spms.listeners.ContextLoaderListener;
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
		Integer cpagenum = Integer.parseInt(cpagenumgg);
		
		try {
			//ContextLoaderListener의 getApplicationContext()를 호출해서 ApplicationContext 객체를 꺼낸다.
			ApplicationContext ctx = ContextLoaderListener.getApplicationContext();
			
			HashMap<String,Object> model = new HashMap<String,Object>();			
			model.put("session",request.getSession());

			//페이지 컨트롤러는 Controller의 구현체이기 때문에 인터페이스 타입의 참조 변수를 선언한다.
			Controller pageController = (Controller)ctx.getBean(servletPath);
			
			if(pageController == null) {
				throw new Exception("요청한 서비스를 찾을 수 없습니다.");
			}

			//DataBinding을 구현했는지 여부를 검사하여, 해당 인터페이스를 구현한 경우에만 prepareRequestData()를 호출하여 페이지 컨트롤러를 위한 데이터를 준비했다.
			if(pageController instanceof DataBinding) {
				//데이터 준비를 자동으로 수행하는 prepareRequestData()를 호출한다.
				prepareRequestData(request, model, (DataBinding)pageController);
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
	
	private void prepareRequestData(HttpServletRequest request, HashMap<String, Object> model, DataBinding dataBinding) throws Exception{
		//먼저 페이지 컨트롤러에게 필요한 데이터가 뭔지 묻는다.
		//getDataBinders() 메서드가 반환하는 것은 Object 배열이다.
		Object[] dataBinders = dataBinding.getDataBinders();
		
		//배열을 반복하기 전에 배열에서 꺼낸 값을 보관할 임시 변수를 준비한다.
		String dataName = null;
		Class<?> dataType = null;
		Object dataObj = null;
		
		for (int i=0; i<dataBinders.length; i+=2) {
			dataName = (String)dataBinders[i];
			dataType = (Class<?>) dataBinders[i+1];
			dataObj = ServletRequestDataBinder.bind(request, dataType, dataName);
			//bind() 메서드가 반환한 데이터 객체는 Map 객체에 담는다.
			//이 작업을 통해 페이지 컨트롤러가 사용할 데이터를 준비한다.
			model.put(dataName, dataObj);
		}
	}
	
}
