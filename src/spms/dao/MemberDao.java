package spms.dao;

import java.util.List;

import spms.servlets.PageMaker;
import spms.vo.Member;

public interface MemberDao {
	List<Member> selectList(int cpagenum) throws Exception;
	int totalCount() throws Exception;
	int insert(Member member) throws Exception;
	int delete(int no) throws Exception;
	Member selectOne(int no) throws Exception;
	int update(Member member) throws Exception;
	Member exist(Member member) throws Exception;
}
