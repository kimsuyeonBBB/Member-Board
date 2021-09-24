package spms.controls;

import java.util.Map;

import spms.dao.MySqlFindDao;
import spms.vo.Member;

public class FindIdController implements Controller{
	MySqlFindDao findDao;
	
	public FindIdController setFindDao(MySqlFindDao findDao) {
		this.findDao = findDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if(model.get("member") == null) {
			return "/auth/FindIdForm.jsp";
		}
		else {
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
