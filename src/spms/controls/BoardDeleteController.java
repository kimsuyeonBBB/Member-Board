package spms.controls;

import java.util.Map;

import spms.dao.BoardDao;

public class BoardDeleteController implements Controller {

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		BoardDao boardDao = (BoardDao) model.get("boardDao");
		
		Integer no = (Integer) model.get("no");
		boardDao.delete(no);
		
		return "redirect:list.do";
	}

}
