package spms.controls;

import java.util.Map;

import spms.dao.MySqlBoardDao;

public class BoardDeleteController implements Controller {
	MySqlBoardDao boardDao;
	
	public BoardDeleteController setBoardDao(MySqlBoardDao boardDao) {
		this.boardDao = boardDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Integer no = (Integer) model.get("no");
		boardDao.delete(no);
		
		return "redirect:list.do";
	}

}
