package spms.listeners;

import java.io.InputStream;
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
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

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
			//SqlSessionFactory 객체를 별도로 생성해서 등록해야 하기 때문에 기존의 방식처럼 객체 생성과 의존 객체 주입을 생성자에서 일괄처리 할 수 없다.
			//따라서 ApplicationContext 객체를 생성할 때 기본 생성자를 호출하도록 코드를 변경하였다.
			applicationContext = new ApplicationContext();
			
			//mybatis 설정파일 'mybatis-config.xml"은 SqlSessionFactory를 생성할 때 사용할 설계도면이다.
			String resource = "spms/dao/mybatis-config.xml";
			//입력 스트림을 얻기 위해 mybatis에서 제공하는 Resources 클래스를 사용하였다.
			InputStream inputStream = Resources.getResourceAsStream(resource);
			//SqlSessionFactory 객체 생성
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			//SqlSessionFactory 객체를 생성했으면 ApplicationContext에 등록해야 한다. 그래야 DAO에 주입할 수 있다.
			applicationContext.addBean("sqlSessionFactory", sqlSessionFactory);
			
			ServletContext sc = event.getServletContext();
			
			//프로퍼티 파일의 이름과 경로 정보도 web.xml 파일로부터 읽어오게 처리하였다.
			//ServletContext의 getInitParameter()를 호출해서 web.xml에 설정된 매개변수 정보를 가져온다.
			String propertiesPath = sc.getRealPath(sc.getInitParameter("contextConfigLocation"));
			
			//프로퍼티 파일의 내용에 따라 객체를 생성하도록 ApplicationContext에 지시한다.
			applicationContext.prepareObjectsByProperties(propertiesPath);
			
			//애노테이션이 붙은 클래스를 찾아 객체를 생성한다.
			applicationContext.prepareObjectsByAnnotation("");
			
			//마지막으로 ApplicationContext에서 관리하는 객체들을 조사하여 의존 객체를 주입한다.
			applicationContext.injectDependency();
			
		} catch(Throwable e) {
			e.printStackTrace();
		}
		
	}
	//웹 애플리케이션이 종료되기 전에 데이터베이스와의 연결을 끊어야 한다.
	//현재는 톰캣 서버에 자동으로 해제하라고 설정되어 있기 때문에 아무것도 작성하지 않았다. (context.xml 파일 확인)
	@Override
	public void contextDestroyed(ServletContextEvent event) {}

}
