package spms.controls;

import java.util.Map;

import spms.bind.DataBinding;
import spms.dao.MySqlMemberDao;
import spms.servlets.PageMaker;

public class MemberListController implements Controller,DataBinding {
	//MemberListController에도 MemberDao를 주입받기 위한 인스턴스 변수와 셋터 메서드를 추가하였다.
	MySqlMemberDao memberDao;
	
	public MemberListController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
			"pagenum",Integer.class	
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
//		System.exit(0);
		PageMaker pagemaker = new PageMaker();

		Integer cpagenum = (Integer) model.get("pagenum");
				
		pagemaker.setTotalcount(memberDao.totalCount(cpagenum));
		pagemaker.setPagenum(cpagenum);
		pagemaker.setCurrentblock(cpagenum);
		pagemaker.setLastblock(pagemaker.getTotalcount());
		
		pagemaker.prevnext(cpagenum);
		pagemaker.setStartPage(pagemaker.getCurrentblock());
		pagemaker.setEndPage(pagemaker.getLastblock(),pagemaker.getCurrentblock());
		
		model.put("members", memberDao.selectList(cpagenum, pagemaker));
		model.put("page", pagemaker);
		return "/member/MemberList.jsp";
	}

	
}
