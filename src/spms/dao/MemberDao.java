package spms.dao;

import java.util.List;

import spms.servlets.PageMaker;
import spms.vo.Member;

public interface MemberDao {
	List<Member> selectList(int cpagenum,PageMaker pagemaker) throws Exception;
	int totalCount(int cpagenum) throws Exception;
	int insert(Member member) throws Exception;
	int delete(int no) throws Exception;
	Member selectOne(int no) throws Exception;
	int update(Member member) throws Exception;
	Member exist(String id, String password) throws Exception;
}