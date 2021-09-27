package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import spms.annotation.Component;
import spms.util.DBConnectionPool;
import spms.vo.Member;

@Component("findDao")
public class MySqlFindDao implements FindDao{
	SqlSessionFactory sqlSessionFactory;
	
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public Member findid(String name, String email) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try {
			MyParameter param = new MyParameter();
			param.setFname(name);
			param.setEmail(email);
			
			return sqlSession.selectOne("spms.dao.FindDao.findid",param);
			
		} finally {
			sqlSession.close();
		}
	}
	
	public Member findpw(String name, String email, String id) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();

		try {
			MyParameter param = new MyParameter();
			param.setFname(name);
			param.setEmail(email);
			param.setId(id);
			
			return sqlSession.selectOne("spms.dao.FindDao.findpw",param);
		} finally {
			sqlSession.close();
		}
	}
}
