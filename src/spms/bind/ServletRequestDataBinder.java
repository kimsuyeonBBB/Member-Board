package spms.bind;

import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Set;

import javax.servlet.ServletRequest;

//특정 인스턴스의 값을 다루지 않는다면 static으로 선언하여 '클래스 메서드'로 만드는 것이 좋다.
public class ServletRequestDataBinder {		
	//프런트 컨트롤러에서 호출하는 메서드이다.
	//요청 매개변수의 값과 데이터 이름, 데이터 타입을 받아서 데이터 객체를 만드는 일을 한다.
	public static Object bind(ServletRequest request, Class<?> dataType, String dataName) throws Exception{
		if(isPrimitiveType(dataType)) {
			return createValueObject(dataType, request.getParameter(dataName));
		}
		
		//요청 매개변수의 이름 목록을 얻는다.
		Set<String> paramNames = request.getParameterMap().keySet();
		//값을 저장할 객체를 생성한다.
		Object dataObject = dataType.newInstance();
		Method m = null;
		
		//요청 매개변수의 이름 목록이 준비되었으면 for 반목문을 실행한다.
		for(String paramName : paramNames) {
			//데이터 타입 클래스에서 매개변수 이름과 일치하는 프로퍼티(셋터 메서드)를 찾는다.
			m = findSetter(dataType, paramName);
			//셋터 메서드를 찾았으면 이전에 생성한 dataObject에 대해 호출한다.
			if(m != null) {
				//createValueObject() 메서드는 요청 매개변수의 값을 가지고 기본 타입의 객체를 만들어준다.
				m.invoke(dataObject, createValueObject(m.getParameterTypes()[0], request.getParameter(paramName)));
			}
		}
		return dataObject;
	}
	
	//매개변수로 주어진 타입이 기본 타입인지 검사하는 메서드이다.
	private static boolean isPrimitiveType(Class<?> type) {
		if(type.getName().equals("int") || type == Integer.class ||
		   type.getName().equals("long") || type == Long.class ||
		   type.getName().equals("float") || type == Float.class ||
		   type.getName().equals("double") || type == Double.class ||
		   type.getName().equals("boolean") || type == Boolean.class ||
		   type == Date.class || type == String.class) {
			return true;
		}
		return false;
	}
	
	//기본타입의 객체를 생성할 때 호출한다.
	//이 메서드는 셋터로 값을 할당할 수 없는 기본 타입에 대해 객체를 생성하는 메서드이다.
	private static Object createValueObject(Class<?> type, String value) {
		if(type.getName().equals("int") || type == Integer.class) {
			if(value==null) {
				value="1";
			}
			return new Integer(value);
		} else if(type.getName().equals("float") || type == Float.class) {
			return new Float(value);
		} else if(type.getName().equals("double") || type == Double.class) {
			return new Double(value);
		} else if(type.getName().equals("long") || type == Long.class) {
			return new Long(value);
		} else if(type.getName().equals("boolean") || type == Boolean.class) {
			return new Boolean(value);
		} else if(type == Date.class) {
			return java.sql.Date.valueOf(value);
		} else {
			return value;
		}
		
		}
	
	//findSetter()는 클래스(type)을 조사하여 주어진 이름(name)과 일치하는 셋터 메서드를 찾는다.
	private static Method findSetter(Class<?> type, String name) {
		//제일 먼저 데이터 타입에서 메서드 목록을 얻는다.
		Method[] methods = type.getMethods();
		
		String propName = null;
		//메서드 목록을 반복하여 셋터 메서드에 대해서만 작업을 수행한다.
		for(Method m:methods) {
			if(!m.getName().startsWith("set")) continue;
			//셋터 메서드일 경우 요청 매개변수의 이름과 일치하는지 검사한다.(대소문자 구분 x)
			propName = m.getName().substring(3);
			if(propName.toLowerCase().equals(name.toLowerCase())) {
				return m;
			}
		}
		return null;
	}

}
