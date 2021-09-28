package spms.controls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MemberDao;
import spms.dao.MySqlMemberDao;
import spms.servlets.PageMaker;
import spms.vo.Member;

@Component("/member/list.do")
public class MemberListController implements Controller,DataBinding {
	//MemberListController에도 MemberDao를 주입받기 위한 인스턴스 변수와 셋터 메서드를 추가하였다.
	MemberDao memberDao;
	
	public MemberListController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
			"orderCond", String.class,
			"pagenum", Integer.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		HashMap<String,Object> paramMap = new HashMap<String,Object>();
		
		paramMap.put("orderCond", model.get("orderCond"));
		paramMap.put("pagenum", model.get("pagenum"));
		
		PageMaker pagemaker = new PageMaker();

		Integer cpagenum = (Integer) model.get("pagenum");

		pagemaker.setTotalcount(memberDao.totalCount());
		pagemaker.setPagenum(cpagenum);
		pagemaker.setCurrentblock(cpagenum);
		pagemaker.setLastblock(pagemaker.getTotalcount());
		
		pagemaker.prevnext(cpagenum);
		pagemaker.setStartPage(pagemaker.getCurrentblock());
		pagemaker.setEndPage(pagemaker.getLastblock(),pagemaker.getCurrentblock());
		
		model.put("members", memberDao.selectList(paramMap));
		model.put("page", pagemaker);
		return "/member/MemberList.jsp";
	}

	
}
