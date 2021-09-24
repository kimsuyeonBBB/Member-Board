package spms.controls;

import java.util.Map;

import spms.dao.MySqlFindDao;
import spms.vo.Member;

public class FindPwdController implements Controller {
	MySqlFindDao findDao;
	
	public FindPwdController setFindDao(MySqlFindDao findDao) {
		this.findDao = findDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if(model.get("member") == null) {
			return "/auth/FindPwdForm.jsp";
		}
		else {
			Member member = (Member) model.get("member");
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
