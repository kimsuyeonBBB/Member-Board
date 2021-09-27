package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import spms.annotation.Component;
import spms.servlets.PageMaker;
import spms.util.DBConnectionPool;
import spms.vo.Member;

@Component("memberDao")
public class MySqlMemberDao implements MemberDao{
	SqlSessionFactory sqlSessionFactory;
	
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<Member> selectList(int cpagenum) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try {
			cpagenum = (cpagenum-1)*5;
			
			return sqlSession.selectList("spms.dao.MemberDao.selectList",cpagenum);
			
		} finally {
			sqlSession.close();
		}
	}
	
	public int totalCount() throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try {
			return sqlSession.selectList("spms.dao.MemberDao.totalCount").size();

		} finally {
			sqlSession.close();
		}
	}
	
	public int insert(Member member) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try {
			int count = sqlSession.insert("spms.dao.MemberDao.insert",member);
			sqlSession.commit();
			return count;
			
		} finally {
			sqlSession.close();
		}
		
	}
	
	public Member selectOne(int no) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try {
			return sqlSession.selectOne("spms.dao.MemberDao.selectOne", no);
			
		} finally {
			sqlSession.close();
		}
		
	}
	
	public int update(Member member) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();

		try {
			int count = sqlSession.update("spms.dao.MemberDao.update",member);
			sqlSession.commit();
			return count;
			
		} finally {
			sqlSession.close();
		}
		
	}
	
	public int delete(int no) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try {			
			int count = sqlSession.delete("spms.dao.MemberDao.delete",no);
			sqlSession.commit();
			return count;
			
		} finally {
			sqlSession.close();
		}
		
	}
	
	public Member exist(Member member) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();

		try {
			return sqlSession.selectOne("spms.dao.MemberDao.exist",member);
			
		} finally {
			sqlSession.close();
		}
		
	}
	
	
}
