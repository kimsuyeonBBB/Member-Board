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

import spms.context.ApplicationContext;
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
	static ApplicationContext applicationContext;
	
	//이 메서드는 ContextLoaderListener에서 만든 ApplicationContext 객체를 얻을 때 사용한다.
	//당장 프런트 컨트롤러에서 사용해야 한다. 클래스 이름만으로 호출할 수 있게 static으로 선언했다.
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	//DB Connection 준비하는 코드 작성
	//ContextLoaderListener의 contextInitialized()는 웹 애플리케이션이 시작될 때 호출되는 메서드이다.
	//여기서 MemberDao가 사용할 의존 객체인 'DataSource'를 주입하고 있다.
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			ServletContext sc = event.getServletContext();
			
			//프로퍼티 파일의 이름과 경로 정보도 web.xml 파일로부터 읽어오게 처리하였다.
			//ServletContext의 getInitParameter()를 호출해서 web.xml에 설정된 매개변수 정보를 가져온다.
			String propertiesPath = sc.getRealPath(sc.getInitParameter("contextConfigLocation"));
			//그리고 ApplicationContext 객체를 생성할 때 생성자의 매개변수로 넘겨준다.
			applicationContext = new ApplicationContext(propertiesPath);
			//이렇게 생성한 ApplicationContext 객체는 프런트 컨트롤러에서 사용할 수 있게 ContextLoaderListener의 클래스 변수 'applicationContext'에 저장된다.
			
			//더이상 이 클래스를 변경할 필요가 없다.
			//페이지 컨트롤러나 DAO 등을 추가할 때는 프로퍼티 파일에 그 클래스에 대한 정보를 한줄 추가하면 자동으로 그 객체가 생성된다.
			
		} catch(Throwable e) {
			e.printStackTrace();
		}
		
	}
	//웹 애플리케이션이 종료되기 전에 데이터베이스와의 연결을 끊어야 한다.
	//현재는 톰캣 서버에 자동으로 해제하라고 설정되어 있기 때문에 아무것도 작성하지 않았다. (context.xml 파일 확인)
	@Override
	public void contextDestroyed(ServletContextEvent event) {}

}
