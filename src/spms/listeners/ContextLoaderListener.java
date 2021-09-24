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

import spms.dao.BoardDao;
import spms.dao.FindDao;
import spms.dao.MemberDao;
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
			
			MemberDao memberDao = new MemberDao();
			BoardDao boardDao = new BoardDao();
			FindDao findDao = new FindDao();
			memberDao.setDataSource(ds);
			boardDao.setDataSource(ds);
			findDao.setDataSource(ds);
			
			sc.setAttribute("memberDao", memberDao);
			sc.setAttribute("boardDao", boardDao);
			sc.setAttribute("findDao", findDao);
		} catch(Throwable e) {
			e.printStackTrace();
		}
		
	}
	//웹 애플리케이션이 종료되기 전에 데이터베이스와의 연결을 끊어야 한다.
	//현재는 톰캣 서버에 자동으로 해제하라고 설정되어 있기 때문에 아무것도 작성하지 않았다. (context.xml 파일 확인)
	@Override
	public void contextDestroyed(ServletContextEvent event) {}

}
