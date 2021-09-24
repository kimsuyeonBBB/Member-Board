package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.dao.MySqlBoardDao;
import spms.dao.MySqlMemberDao;
import spms.servlets.PageMaker;
import spms.vo.Member;

public class BoardListController implements Controller {
	MySqlBoardDao boardDao;
	
	public BoardListController setBoardDao(MySqlBoardDao boardDao) {
		this.boardDao = boardDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		PageMaker pagemaker = new PageMaker();

		HttpSession session = (HttpSession) model.get("session");
		Member member = (Member) session.getAttribute("member");
		
		int cpagenum = (int) model.get("cpagenum");
		
		pagemaker.setTotalcount(boardDao.totalCount(cpagenum,member));
		pagemaker.setPagenum(cpagenum);
		pagemaker.setCurrentblock(cpagenum);
		pagemaker.setLastblock(pagemaker.getTotalcount());
		
		pagemaker.prevnext(cpagenum);
		pagemaker.setStartPage(pagemaker.getCurrentblock());
		pagemaker.setEndPage(pagemaker.getLastblock(),pagemaker.getCurrentblock());
		
		model.put("boards", boardDao.selectList(cpagenum, pagemaker, member));
		model.put("page", pagemaker);
		
		return "/board/BoardList.jsp";
	}

}
