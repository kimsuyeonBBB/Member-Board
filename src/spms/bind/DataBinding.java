package spms.bind;

public interface DataBinding {
	//getDataBinders()의 반환값은 데이터의 이름과 타입 정보를 담은 Object 배열이다.
	//배열의 작성 형식 : new Object[]{"데이터 이름",데이터 타입, "데이터 이름", 데이터 타입, ...}
	Object[] getDataBinders();
}
