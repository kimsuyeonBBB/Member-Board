package spms.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//애노테이션 유지정책 지정 (애노테이션 정보를 언제까지 유지할 것인지 설정하는 문법)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
	String value() default "";
}
