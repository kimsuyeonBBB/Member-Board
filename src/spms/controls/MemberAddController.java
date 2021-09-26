package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlMemberDao;
import spms.vo.Member;

@Component("/member/add.do")
//클라이언트가 보낸 데이터를 프런트 컨트롤러로부터 받아야하기 때문에 정의한 규칙에 따라 DataBinding 인터페이스를 구현한다.
public class MemberAddController implements Controller, DataBinding{
	MySqlMemberDao memberDao;
	
	public MemberAddController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	//프런트 컨트롤러는 Object 배열에 지정된대로 Member 인스턴스를 준비하여 "member"라는 이름으로 Map 객체에 저장하고 
	//execute()를 호출할 때 매개변수로 이 Map 객체를 넘길것이다.
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
				"member", spms.vo.Member.class
		};
	}

	//이제부터는 getDataBinder()에서 지정한대로 프런트 컨트롤러가 VO 객체를 무조건 생성하기 때문에 Member의 존재 유무로 판단하면 안된다.
	//대신 다음과 같이 Member에 이메일이 들어있는지 여부를 검사해야한다.
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member member = (Member) model.get("member");
		
		if(member.getEmail() == null) {     //입력폼을 요청할 때
			return "/member/MemberForm.jsp";
		} else {     //회원 등록을 요청할 때
			memberDao.insert(member);
			
			return "redirect:../auth/login.do";
		}
	}

	
}
