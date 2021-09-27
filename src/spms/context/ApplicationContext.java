package spms.context;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.reflections.Reflections;

import spms.annotation.Component;

//프로퍼티 파일에 설정된 객체를 준비하는 일을 한다.
//ApplicationContext()를 만든 이유는 페이지 컨트롤러나 DAO가 추가되더라도 ContextLoaderListener를 변경하지 않기 위함이다.
public class ApplicationContext {
	//프로퍼티에 설정된 대로 객체를 준비하면 객체를 저장할 보관소가 필요하다.  (인스턴스 목록 저장)
	//이를 위해 해시 테이블을 준비한다.
	Hashtable<String,Object> objTable = new Hashtable<String,Object>();
	
	//또한 해시 테이블에서 객체를 꺼낼 메서드도 정의한다.   (인스턴스 찾아주는 메서드)
	public Object getBean(String key) {
		return objTable.get(key);
	}
	
	//외부에서 생성한 SqlSessionFactory를 등록할 수 있게 addBean() 메서드 추가
	public void addBean(String name, Object obj) {
		objTable.put(name, obj);
	}
	
	//애노테이션이 붙은 클래스를 찾아서 객체를 준비하는 메서드
	//Reflections라는 오픈 소스 라이브러리 사용
	//애노테이션을 검색할 패키지 이름을 매개변수로 받는다.
	public void prepareObjectsByAnnotation(String basePackage) throws Exception{
		Reflections reflector = new Reflections(basePackage);
		
		Set<Class<?>> list = reflector.getTypesAnnotatedWith(Component.class);
		String key = null;
		
		for(Class<?> clazz : list) {
			//클래스로부터 애노테이션 추출해서 속성값을 꺼낸다.
			key = clazz.getAnnotation(Component.class).value();
			//애노테이션을 통해 알아낸 객체 이름(key)으로 인스턴스를 저장한다.
			objTable.put(key, clazz.newInstance());
		}
	}
	
	//프로퍼티 파일을 로딩했으면 그에 따라 객체를 준비해야 한다.
	//매개변수는 Properties 객체를 직접 받는 대신, 프로퍼티 파일의 경로를 받아서 내부에서 Properties 객체를 생성하도록 변경하였다.
	public void prepareObjectsByProperties(String propertiesPath) throws Exception{
		Properties props = new Properties();
		props.load(new FileReader(propertiesPath));
		
		//먼저 JNDI 객체를 찾을 때 사용할 InitialContext를 준비한다.
		Context ctx = new InitialContext();
		String key = null;
		String value = null;
		
		//반복문을 통해서 프로퍼티에 들어있는 정보를 꺼내서 객체를 생성한다.
		//keySet() 메서드는 Properties에 저장된 키 목록을 반환한다. 
		for(Object item : props.keySet()) {
			key = (String) item;
			value = props.getProperty(key);
			//만약에 프로퍼티의 키가 "jndi."로 시작한다면 객체를 생성하지 않고 InitialContext를 통해 얻는다.
			if(key.startsWith("jndi.")) {
				//InitialContext의 lookup() 메서드는 JNDI 인터페이스를 통해 톰캣 서버에 등록된 객체를 찾는다.
				objTable.put(key, ctx.lookup(value));
			} else {
				//그 밖의 객체는 Class.forName()을 호출하여 클래스를 로딩하고, newInstance()를 사용하여 인스턴스를 생성한다.
				objTable.put(key, Class.forName(value).newInstance());
			}
		}
	}
	
	//톰캣 서버로부터 객체를 가져오거나(DataSource) 직접 객체를 생성했으면(MemberDao) 이제는 각 객체가 필요로 하는 의존 객체를 할당해주어야 한다.
	public void injectDependency() throws Exception{
		for(String key : objTable.keySet()) {
			//나머지 객체에 대해서 셋터 메서드를 호출하였다.
			if(!key.startsWith("jndi.")) {
				callSetter(objTable.get(key));
			}
			//객체 이름이 "jndi."로 시작하는 경우 톰캣 서버에서 제공한 객체이므로 의존 객체를 주입해서는 안된다.
		}
	}
	
	//매개변수로 주어진 객체에 대해 셋터 메서드를 찾아서 호출하는 일을 한다.
	private void callSetter(Object obj) throws Exception{
		Object dependency = null;
		for(Method m : obj.getClass().getMethods()) {
			//먼저 셋터 메서드를 찾는다.
			if(m.getName().startsWith("set")) {
				//셋터 메서드를 찾았으면 셋터 메서드의 매개변수와 타입이 일치하는 객체를 objTable에서 찾는다.
				dependency = findObjectByType(m.getParameterTypes()[0]);
				//의존 객체를 찾았으면 셋터 메서드를 호출한다.
				if(dependency != null) {
					m.invoke(obj, dependency);
				}
			}
		}
	}
	
	//이 메서드는 셋터 메서드를 호출할 때 넘겨줄 의존 객체를 찾는 일을 한다.	
	private Object findObjectByType(Class<?> type) {
		//objTable에 들어있는 객체를 모두 뒤진다.
		for(Object obj : objTable.values()) {
			//만약 셋터 메서드의 매개변수 타입과 일치하는 객체를 찾았다면 그 객체의 주소를 리턴한다.
			if(type.isInstance(obj)) {
				return obj;
			}
		}
		return null;
	}
}
