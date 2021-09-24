package spms.dao;

import spms.vo.Member;

public interface FindDao {
	Member findid(String name, String email) throws Exception;
	Member findpw(String name, String email, String id) throws Exception;
}
