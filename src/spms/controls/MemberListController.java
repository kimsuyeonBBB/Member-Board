package spms.controls;

import java.util.Map;

import spms.dao.MemberDao;
import spms.servlets.PageMaker;

public class MemberListController implements Controller {

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		PageMaker pagemaker = new PageMaker();
		MemberDao memberDao = (MemberDao) model.get("memberDao");
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
