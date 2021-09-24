package spms.controls;

import java.util.Map;

import spms.dao.FindDao;
import spms.vo.Member;

public class FindPwdController implements Controller {

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if(model.get("member") == null) {
			return "/auth/FindPwdForm.jsp";
		}
		else {
			FindDao findDao = (FindDao) model.get("findDao");
			
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
