package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlMemberDao;
import spms.vo.Member;

@Component("/member/update.do")
public class MemberUpdateController implements Controller, DataBinding {
	MySqlMemberDao memberDao;
	
	public MemberUpdateController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
				"no", Integer.class,
				"member", spms.vo.Member.class
		};
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member member = (Member) model.get("member");
		Integer no = (Integer) model.get("no");
		
		if(member.getEmail() == null) {  //수정폼을 요청할 때		
			Member mem = memberDao.selectOne(no);
			model.put("member", mem);
			return "/member/MemberUpdateForm.jsp";
		} else {  //회원 수정을 요청할 때
			
			memberDao.update(member);
			return "redirect:list.do";
		}
	}

	
}
