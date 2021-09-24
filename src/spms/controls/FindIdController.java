package spms.controls;

import java.util.Map;

import spms.dao.FindDao;
import spms.vo.Member;

public class FindIdController implements Controller{

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if(model.get("member") == null) {
			return "/auth/FindIdForm.jsp";
		}
		else {
			FindDao findDao = (FindDao) model.get("findDao");
			
			Member member = (Member) model.get("member");
			Member result = findDao.findid(member.getName(), member.getEmail());
			if(result != null) {
				model.put("member", result);
				return "../auth/SuccessID.jsp";
			} else {
				return "/auth/FindFail.jsp";
			}
		}
		
		
	}

}
