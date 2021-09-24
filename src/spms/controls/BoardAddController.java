package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.dao.BoardDao;
import spms.vo.Board;
import spms.vo.Member;

public class BoardAddController implements Controller {

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if(model.get("board") == null) {
			return "/board/BoardForm.jsp";
		}
		else {
			HttpSession session = (HttpSession) model.get("session");
			Member member = (Member)session.getAttribute("member");
			
			BoardDao boardDao = (BoardDao) model.get("boardDao");
			Board board = (Board) model.get("board");
			board.setName(member.getName());
			boardDao.insert(board);
			
			return "redirect:list.do";
		}
	}

}
