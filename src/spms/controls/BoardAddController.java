package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlBoardDao;
import spms.vo.Board;
import spms.vo.Member;

@Component("/board/add.do")
public class BoardAddController implements Controller,DataBinding {
	MySqlBoardDao boardDao;
	
	public BoardAddController setBoardDao(MySqlBoardDao boardDao) {
		this.boardDao = boardDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
				"board",spms.vo.Board.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Board board = (Board) model.get("board");
		
		if(board.getTitle() == null) {
			return "/board/BoardForm.jsp";
		}
		else {
			HttpSession session = (HttpSession) model.get("session");
			Member member = (Member)session.getAttribute("member");
						
			board.setName(member.getName());
			boardDao.insert(board);
			
			return "redirect:list.do";
		}
	}

	
}
