package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlFindDao;
import spms.vo.Member;

@Component("/auth/findpwd.do")
public class FindPwdController implements Controller,DataBinding {
	MySqlFindDao findDao;
	
	public FindPwdController setFindDao(MySqlFindDao findDao) {
		this.findDao = findDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
			"member",spms.vo.Member.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member member = (Member) model.get("member");
		
		if(member.getName() == null) {
			return "/auth/FindPwdForm.jsp";
		}
		else {
			Member result = findDao.findpw(member.getName(),member.getEmail(), member.getId());
			
			if(result != null) {
				model.put("member", result);
				
				return "../auth/SuccessPWD.jsp";
			} 
			else {
				return "/auth/FindFail2.jsp";
			}
		}
	}

}
