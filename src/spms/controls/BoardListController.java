package spms.controls;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.BoardDao;
import spms.dao.MySqlBoardDao;
import spms.dao.MySqlMemberDao;
import spms.servlets.PageMaker;
import spms.vo.Member;

@Component("/board/list.do")
public class BoardListController implements Controller,DataBinding {
	BoardDao boardDao;
	
	public BoardListController setBoardDao(BoardDao boardDao) {
		this.boardDao = boardDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
				"orderCond", String.class,
				"pagenum",Integer.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		HttpSession session = (HttpSession) model.get("session");
		Member member = (Member) session.getAttribute("member");
		
		HashMap<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("orderCond", model.get("orderCond"));
		paramMap.put("cpagenum", model.get("pagenum"));
		paramMap.put("mname", member.getName());
		
		PageMaker pagemaker = new PageMaker();
		
		int cpagenum = (int) model.get("pagenum");
		
		pagemaker.setTotalcount(boardDao.totalCount(member.getName()));
		pagemaker.setPagenum(cpagenum);
		pagemaker.setCurrentblock(cpagenum);
		pagemaker.setLastblock(pagemaker.getTotalcount());
		
		pagemaker.prevnext(cpagenum);
		pagemaker.setStartPage(pagemaker.getCurrentblock());
		pagemaker.setEndPage(pagemaker.getLastblock(),pagemaker.getCurrentblock());
		
		model.put("boards", boardDao.selectList(paramMap));
		model.put("page", pagemaker);
		
		return "/board/BoardList.jsp";
	}

	

}
