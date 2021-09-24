package spms.controls;

import java.util.Map;

import spms.dao.MySqlMemberDao;
import spms.servlets.PageMaker;

public class MemberListController implements Controller {
	//MemberListController에도 MemberDao를 주입받기 위한 인스턴스 변수와 셋터 메서드를 추가하였다.
	MySqlMemberDao memberDao;
	
	public MemberListController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		PageMaker pagemaker = new PageMaker();
		
		int cpagenum = (int) model.get("cpagenum");
		
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
