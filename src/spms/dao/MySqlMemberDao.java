package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
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
	
	public List<Member> selectList(HashMap<String, Object> paramMap) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try {
			int cpagenum = (int) paramMap.get("pagenum");
			cpagenum = (cpagenum-1)*5;
			paramMap.put("cpagenum", cpagenum);
			
			return sqlSession.selectList("spms.dao.MemberDao.selectList",paramMap);
			
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
			//먼저 원래의 프로젝트 정보를 가져온다.(이 값을 사용자가 입력한 값과 비교한다.)
			Member original = sqlSession.selectOne("spms.dao.MemberDao.selectOne", member.getNo());
			
			//update문에 전달할 Map 객체를 준비한다. 원래의 값과 사용자가 입력한 값을 비교하여 값이 바뀌었다면 Map 객체에 저장한다.
			Hashtable<String,Object> paramMap = new Hashtable<String,Object>();
			
			if(!member.getName().equals(original.getName())) {
				paramMap.put("name", member.getName());
			}
			if(!member.getEmail().equals(original.getEmail())) {
				paramMap.put("email", member.getEmail());
			}
			if(!member.getId().equals(original.getId())) {
				paramMap.put("id", member.getId());
			}
			if(!member.getPassword().equals(original.getPassword())) {
				paramMap.put("password", member.getPassword());
			}
			
			//Map 객체에 저장된 값이 있다면, 즉 변경된 값이 있다면 UPDATE 문을 실행한다.
			if(paramMap.size() >0) {
				paramMap.put("no", member.getNo());
				
				int count = sqlSession.update("spms.dao.MemberDao.update",paramMap);
				sqlSession.commit();
				return count;
			} else {
				//변경된 값이 없다면 0을 반환한다.
				return 0;
			}
			
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
