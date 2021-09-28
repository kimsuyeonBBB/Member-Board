package spms.dao;

import java.util.HashMap;
import java.util.Hashtable;
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
	
	public List<Board> selectList(HashMap<String,Object> paramMap) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try {
			int cpagenum = (int) paramMap.get("cpagenum");
			cpagenum = (cpagenum-1)*5;
			paramMap.put("cpagenum", cpagenum);
			
			return sqlSession.selectList("spms.dao.BoardDao.selectList", paramMap);
			
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
			Board original = sqlSession.selectOne("spms.dao.BoardDao.selectOne", board.getNo());
			
			Hashtable<String,Object> paramMap = new Hashtable<String,Object>();
			
			if(!board.getTitle().equals(original.getTitle())) {
				paramMap.put("title", board.getTitle());
			}
			
			if(!board.getStory().equals(original.getStory())) {
				paramMap.put("story", board.getStory());
			}
			
			if(paramMap.size()>0) {
				paramMap.put("no", board.getNo());
				
				int count = sqlSession.update("spms.dao.BoardDao.update",paramMap);
				sqlSession.commit();
				return count;
			} else {
				return 0;
			}

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
