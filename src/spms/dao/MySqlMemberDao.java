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

import spms.annotation.Component;
import spms.servlets.PageMaker;
import spms.util.DBConnectionPool;
import spms.vo.Member;

@Component("memberDao")
public class MySqlMemberDao implements MemberDao{
	//외부로부터 Connection 객체를 주입 받기 위한 셋터 메서드와 인스턴스 변수 준비 (의존성 주입/DI)
	DataSource ds;
	
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}
	
	public List<Member> selectList(int cpagenum,PageMaker pagemaker) throws Exception{
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		
		ArrayList<Member> members = new ArrayList<Member>();
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement("SELECT MNO,MNAME,EMAIL,CRE_DATE" +
									" FROM MEM_AD" +
									" ORDER BY MNO ASC" + 
									" LIMIT ?,?");
			stmt.setInt(1,(cpagenum-1)*5 );
			stmt.setInt(2, pagemaker.getContentnum());
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				members.add(new Member()
						.setNo(rs.getInt("MNO"))
						.setName(rs.getString("MNAME"))
						.setEmail(rs.getString("EMAIL"))
						.setCreatedDate(rs.getDate("CRE_DATE")));
			}

			
			return members;
		}catch(Exception e) {
			throw e;
		}finally {
			try {if(rs != null) rs.close();} catch(Exception e) {}
			try {if(stmt != null)stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
	}
	
	public int totalCount(int cpagenum) throws Exception{
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
	
		ArrayList<Member> totalCount = new ArrayList<Member>();

		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement("SELECT MNO,MNAME,EMAIL,CRE_DATE" +
					" FROM MEM_AD" +
					" ORDER BY MNO ASC");
			rs = stmt.executeQuery();

			while(rs.next()) {
				totalCount.add(new Member()
						.setNo(rs.getInt("MNO"))
						.setName(rs.getString("MNAME"))
						.setEmail(rs.getString("EMAIL"))
						.setCreatedDate(rs.getDate("CRE_DATE")));
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
	
	public int insert(Member member) throws Exception{
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement("INSERT INTO MEM_AD(MNAME, EMAIL,ID, PWD, CRE_DATE, MOD_DATE)" + 
												" VALUES (?,?,?,?,NOW(),NOW())");
			stmt.setString(1, member.getName());
			stmt.setString(2, member.getEmail());
			stmt.setString(3, member.getId());
			stmt.setString(4, member.getPassword());
			return stmt.executeUpdate();
		} catch(Exception e) {
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
			return stmt.executeUpdate("DELETE FROM MEM_AD WHERE MNO=" + no);
		} catch(Exception e) {
			throw e;
		} finally {
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
		
	}
	
	public Member selectOne(int no) throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			//요청 매개변수로 넘어온 회원 번호를 가지고 회원 정보를 질의한다.
			rs = stmt.executeQuery("SELECT MNO,MNAME,EMAIL,ID,PWD,CRE_DATE FROM MEM_AD" + " WHERE MNO=" + no);
			//단 한명의 회원정보를 가져오기 때문에 next()를 한번만 호출한다.
			
			if(rs.next()) {
				return new Member()
						.setNo(rs.getInt("MNO"))
						.setName(rs.getString("MNAME"))
						.setEmail(rs.getString("EMAIL"))
						.setId(rs.getString("ID"))
						.setPassword(rs.getString("PWD"))
						.setCreatedDate(rs.getDate("CRE_DATE"));
			} else {
				throw new Exception("해당 번호의 회원을 찾을 수 없습니다.");
			}
			
		} catch(Exception e) { 
			throw e;
		}finally {
			try {if(rs != null) rs.close();} catch(Exception e) {}
			try {if(stmt != null)stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
		
	}
	
	public int update(Member member) throws Exception{
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement("UPDATE MEM_AD SET MNAME=?,EMAIL=?,ID=?,PWD=?, MOD_DATE=now()" + " WHERE MNO=?");
			stmt.setString(1, member.getName());
			stmt.setString(2, member.getEmail());
			stmt.setString(3, member.getId());
			stmt.setString(4, member.getPassword());
			stmt.setInt(5, member.getNo());
			
			return stmt.executeUpdate();
		} catch(Exception e) {
			throw e;
		} finally {
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
		
	}
	
	public Member exist(String id, String password) throws Exception{
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement("SELECT MNAME,EMAIL FROM MEM_AD" + " WHERE ID=? AND PWD=?");
			stmt.setString(1, id);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if(rs.next()) {
				//만약 아이디과 암호가 일치하는 회원을 찾는다면 값 객체 Member에 회원 정보를 담는다.
				return new Member()
					.setEmail(rs.getString("EMAIL"))
					.setName(rs.getString("MNAME"));
			} else {
				return null;
			}
			
		} catch(Exception e) { 
			throw e;
		} finally {
			try {if(rs != null) rs.close();} catch(Exception e) {}
			try {if(stmt != null)stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
		
	}
	
	
}
