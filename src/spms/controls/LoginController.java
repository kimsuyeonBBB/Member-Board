package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlMemberDao;
import spms.vo.Member;

@Component("/auth/login.do")
public class LoginController implements Controller, DataBinding {
	MySqlMemberDao memberDao;
	
	public LoginController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
				"loginInfo",spms.vo.Member.class
		};
	}

	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member loginInfo = (Member) model.get("loginInfo");
		
		if(loginInfo.getId() == null) {
			return "/auth/LogInForm.jsp";

		} else {
			Member member = memberDao.exist(
					loginInfo.getId(),
					loginInfo.getPassword());
			
			if(member != null) {
				HttpSession session = (HttpSession) model.get("session");
				session.setAttribute("member", member);
				return "redirect:../member/list.do";
			} else {
				return "/auth/LogInFail.jsp";
			}
		}
	}

}
