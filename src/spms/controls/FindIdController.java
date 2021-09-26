package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlFindDao;
import spms.vo.Member;

@Component("/auth/findid.do")
public class FindIdController implements Controller, DataBinding{
	MySqlFindDao findDao;
	
	public FindIdController setFindDao(MySqlFindDao findDao) {
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
			return "/auth/FindIdForm.jsp";
		}
		else {
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
