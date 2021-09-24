package spms.listeners;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import spms.controls.BoardAddController;
import spms.controls.BoardDeleteController;
import spms.controls.BoardListController;
import spms.controls.BoardUpdateController;
import spms.controls.FindIdController;
import spms.controls.FindPwdController;
import spms.controls.LoginController;
import spms.controls.LogoutController;
import spms.controls.MemberAddController;
import spms.controls.MemberDeleteController;
import spms.controls.MemberListController;
import spms.controls.MemberUpdateController;
import spms.dao.MySqlBoardDao;
import spms.dao.MySqlFindDao;
import spms.dao.MySqlMemberDao;
import spms.util.DBConnectionPool;

//AppInitServlet이 하는 일이 모두 여기로 이관되었다.
@WebListener
public class ContextLoaderListener implements ServletContextListener {
	
	//DB Connection 준비하는 코드 작성
	//ContextLoaderListener의 contextInitialized()는 웹 애플리케이션이 시작될 때 호출되는 메서드이다.
	//여기서 MemberDao가 사용할 의존 객체인 'DataSource'를 주입하고 있다.
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			ServletContext sc = event.getServletContext();
			
			//톰캣 서버에서 자원을 찾기 위해 InitialContext 객체 생성
			InitialContext initialContext = new InitialContext();
			DataSource ds = (DataSource)initialContext.lookup("java:comp/env/jdbc/memberadmin");
			
			MySqlMemberDao memberDao = new MySqlMemberDao();
			MySqlBoardDao boardDao = new MySqlBoardDao();
			MySqlFindDao findDao = new MySqlFindDao();
			memberDao.setDataSource(ds);
			boardDao.setDataSource(ds);
			findDao.setDataSource(ds);
			
			//생성된 페이지 컨트롤러를 ServletContext에 저장한다. (서블릿 요청 URL을 키로 하여 저장한다.)
			//로그아웃은 MemberDao가 필요 없기 때문에 셋터 메서드를 호출하지 않는다.
			sc.setAttribute("/auth/login.do", new LoginController().setMemberDao(memberDao));
			sc.setAttribute("/auth/logout.do", new LogoutController());
			sc.setAttribute("/member/list.do", new MemberListController().setMemberDao(memberDao));
			sc.setAttribute("/member/add.do", new MemberAddController().setMemberDao(memberDao));
			sc.setAttribute("/member/update.do", new MemberUpdateController().setMemberDao(memberDao));
			sc.setAttribute("/member/delete.do", new MemberDeleteController().setMemberDao(memberDao));
			
			sc.setAttribute("/board/list.do", new BoardListController().setBoardDao(boardDao));
			sc.setAttribute("/board/add.do", new BoardAddController().setBoardDao(boardDao));
			sc.setAttribute("/board/update.do", new BoardUpdateController().setBoardDao(boardDao));
			sc.setAttribute("/board/delete.do", new BoardDeleteController().setBoardDao(boardDao));
			
			sc.setAttribute("/auth/findid.do", new FindIdController().setFindDao(findDao));
			sc.setAttribute("/auth/findpwd.do", new FindPwdController().setFindDao(findDao));
			
		} catch(Throwable e) {
			e.printStackTrace();
		}
		
	}
	//웹 애플리케이션이 종료되기 전에 데이터베이스와의 연결을 끊어야 한다.
	//현재는 톰캣 서버에 자동으로 해제하라고 설정되어 있기 때문에 아무것도 작성하지 않았다. (context.xml 파일 확인)
	@Override
	public void contextDestroyed(ServletContextEvent event) {}

}
