package spms.controls;

import java.util.Map;

import spms.dao.MySqlBoardDao;
import spms.vo.Board;

public class BoardUpdateController implements Controller {
	MySqlBoardDao boardDao;
	
	public BoardUpdateController setBoardDao(MySqlBoardDao boardDao) {
		this.boardDao = boardDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if(model.get("board") == null) {  //수정폼 요청할 때
			Integer no = (Integer) model.get("no");
			Board board = boardDao.selectOne(no);
			
			model.put("board", board);
			return "/board/BoardUpdateForm.jsp";
		}
		else {   //게시글 수정 요청할 때
			Board board = (Board)model.get("board");
			boardDao.update(board);
			
			return "redirect:list.do";
		}
	}

}
