package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import spms.util.DBConnectionPool;
import spms.vo.Member;

public class MySqlFindDao implements FindDao{
	DataSource ds;
	
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}
	
	public Member findid(String name, String email) throws Exception{
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement("SELECT MNAME,ID FROM MEM_AD" + " WHERE MNAME=? AND EMAIL=?");
			stmt.setString(1, name);
			stmt.setString(2, email);			
			rs = stmt.executeQuery();
			if(rs.next()) {
				return new Member()
						.setName(rs.getString("MNAME"))
						.setId(rs.getString("ID"));
			} else {
				return null;
			}
		} catch(Exception e) { 
			throw e;
		}finally {
			try {if(rs != null) rs.close();} catch(Exception e) {}
			try {if(stmt != null)stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
	}
	
	public Member findpw(String name, String email, String id) throws Exception{
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement("SELECT MNAME,PWD FROM MEM_AD" + " WHERE MNAME=? AND EMAIL=? AND ID=?");
			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setString(3, id);		
			rs = stmt.executeQuery();
			if(rs.next()) {
				return new Member()
						.setName(rs.getString("MNAME"))
						.setPassword(rs.getString("PWD"));
						
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
