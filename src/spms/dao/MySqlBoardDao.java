package spms.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import spms.annotation.Component;
import spms.vo.Board;

@Component("boardDao")
public class MySqlBoardDao implements BoardDao{
	SqlSessionFactory sqlSessionFactory;
	
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public List<Board> selectList(int cpagenum,String mname) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try {
			MyParameter param = new MyParameter();
			cpagenum = (cpagenum-1)*5;
			param.setCpagenum(cpagenum);
			param.setMname(mname);
			
			List<Board> result = sqlSession.selectList("spms.dao.BoardDao.selectList", param);
			return result;
			
		} finally {
			sqlSession.close();
		}
	}
	
	public int totalCount(String name) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try {
			return sqlSession.selectList("spms.dao.BoardDao.totalCount",name).size();
			
		}finally {
			sqlSession.close();
		}
	}
	
	public int insert(Board board) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try {			
			int count = sqlSession.insert("spms.dao.BoardDao.insert", board);
			sqlSession.commit();
			return count;
			
		} finally {
			sqlSession.close();
		}
	}
	
	public Board selectOne(int no) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try {			
			return sqlSession.selectOne("spms.dao.BoardDao.selectOne",no);
			
		} finally {
			sqlSession.close();
		}
	}
	
	public int update(Board board) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try {
			int count = sqlSession.update("spms.dao.BoardDao.update",board);
			sqlSession.commit();
			return count;
			
		} finally {
			sqlSession.close();
		}
	}	
	
	public int delete(int no) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try {			
			int count = sqlSession.delete("spms.dao.BoardDao.delete",no);
			sqlSession.commit();
			return count;
			
		} finally {
			sqlSession.close();
		}
	}
	
}
