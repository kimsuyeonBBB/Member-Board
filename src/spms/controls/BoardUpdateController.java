package spms.controls;

import java.util.Map;

import spms.bind.DataBinding;
import spms.dao.MySqlBoardDao;
import spms.vo.Board;

public class BoardUpdateController implements Controller,DataBinding {
	MySqlBoardDao boardDao;
	
	public BoardUpdateController setBoardDao(MySqlBoardDao boardDao) {
		this.boardDao = boardDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
				"no",Integer.class,
				"board",spms.vo.Board.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Board board = (Board)model.get("board");
		
		if(board.getTitle() == null) {  //수정폼 요청할 때
			Integer no = (Integer) model.get("no");
			Board brd = boardDao.selectOne(no);
			
			model.put("board", brd);
			return "/board/BoardUpdateForm.jsp";
		}
		else {   //게시글 수정 요청할 때
			
			boardDao.update(board);
			
			return "redirect:list.do";
		}
	}

}
