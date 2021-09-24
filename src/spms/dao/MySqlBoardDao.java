package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import spms.servlets.PageMaker;
import spms.util.DBConnectionPool;
import spms.vo.Board;
import spms.vo.Member;

public class MySqlBoardDao implements BoardDao{
	//외부로부터 Connection 객체를 주입 받기 위한 셋터 메서드와 인스턴스 변수 준비 (의존성 주입/DI)
	DataSource ds;
	
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}
	
	public List<Board> selectList(int cpagenum, PageMaker pagemaker,Member member) throws Exception{
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		ArrayList<Board> boards = new ArrayList<Board>();
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement("SELECT MNO,TITLE,CRE_DATE,STORY,MNAME FROM BOARDS WHERE MNAME=? LIMIT ?, ?");
			stmt.setString(1,member.getName());
			stmt.setInt(2, (cpagenum-1)*5);
			stmt.setInt(3, pagemaker.getContentnum());
			rs = stmt.executeQuery();	
			
			//데이터베이스에서 회원정보를 가져와 Member에 담는다.
			//그리고 Member 객체를 ArrayList에 추가한다.
			while(rs.next()) {
				boards.add(new Board()
						.setNo(rs.getInt("MNO"))
						.setTitle(rs.getString("TITLE"))
						.setCreatedDate(rs.getDate("CRE_DATE")) 
						.setStory(rs.getString("STORY"))
						.setName(rs.getString("MNAME")) );
			}
			return boards;
		} catch(Exception e) {
			throw e;
		} finally {
			try {if(rs != null) rs.close();} catch(Exception e) {}
			try {if(stmt != null)stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
	}
	
	public int totalCount(int cpagenum, Member member) throws Exception{
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		
		ArrayList<Board> totalCount = new ArrayList<Board>();
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement("SELECT MNO,TITLE,CRE_DATE,STORY,MNAME FROM BOARDS WHERE MNAME=?");
			stmt.setString(1,member.getName());
			rs = stmt.executeQuery();	
			
			while(rs.next()) {
				totalCount.add(new Board()
						.setNo(rs.getInt("MNO"))
						.setTitle(rs.getString("TITLE"))
						.setCreatedDate(rs.getDate("CRE_DATE")) 
						.setStory(rs.getString("STORY"))
						.setName(rs.getString("MNAME")) );
			}
			return totalCount.size();
		} catch(Exception e) { 
			throw e;
		}finally {
			try {if(rs != null) rs.close();} catch(Exception e) {}
			try {if(stmt != null)stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
	}
	
	public int insert(Board board) throws Exception{
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement("INSERT INTO BOARDS(MNAME,TITLE,STORY,CRE_DATE)" + " VALUES (?,?,?,NOW())");
			stmt.setString(1, board.getName());
			stmt.setString(2, board.getTitle());
			stmt.setString(3, board.getStory());
			return stmt.executeUpdate();
		}catch(Exception e) {
			throw e;
		} finally {
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
	}
	
	public int delete(int no) throws Exception{
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			return stmt.executeUpdate("DELETE FROM BOARDS WHERE MNO=" + no);
			
		} catch(Exception e) {
			throw e;
		} finally {
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
	}
	
	public Board selectOne(int no) throws Exception{
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT MNO,TITLE,STORY FROM BOARDS" + " WHERE MNO=" + no);
			
			if(rs.next()) {
				return new Board()
					.setNo(rs.getInt("MNO"))
					.setTitle(rs.getString("TITLE"))
					.setStory(rs.getString("STORY"));
			} else {
				throw new Exception("해당 번호의 게시글을 찾을 수 없습니다.");
			}
		} catch(Exception e) { 
			throw e;
		}finally {
			try {if(rs != null) rs.close();} catch(Exception e) {}
			try {if(stmt != null)stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
	}
	
	public int update(Board board) throws Exception{
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement("UPDATE BOARDS SET TITLE=?, STORY=?" + " WHERE MNO=?");
			stmt.setString(1, board.getTitle());
			stmt.setString(2, board.getStory());
			stmt.setInt(3, board.getNo());			
			return stmt.executeUpdate();
		} catch(Exception e) {
			throw e;
		} finally {
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
	}	
	
}
