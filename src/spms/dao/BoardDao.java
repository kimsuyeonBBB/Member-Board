package spms.dao;

import java.util.List;

import spms.servlets.PageMaker;
import spms.vo.Board;
import spms.vo.Member;

public interface BoardDao {
	List<Board> selectList(int cpagenum,String mname) throws Exception;
	int totalCount(String name) throws Exception;
	int insert(Board board) throws Exception;
	int delete(int no) throws Exception;
	Board selectOne(int no) throws Exception;
	int update(Board board) throws Exception;
	
}
