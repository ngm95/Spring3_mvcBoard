package com.javalec.mvc_board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.javalec.mvc_board.dto.BDto;

public class BDao {

	DataSource dataSource;
	
	public BDao() {
		Context context;
		try {
			context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<BDto> list() {
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rSet = null;
		
		try {
			conn = dataSource.getConnection();
			String query = "select bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep , bIndent from mvc_board order by bGroup desc, bStep asc";
			pstm = conn.prepareStatement(query);
			rSet = pstm.executeQuery();
			
			while(rSet.next()) {
				int bId = rSet.getInt("bId");
				String bName = rSet.getString("bName");
				String bTitle = rSet.getString("bTitle");
				String bContent = rSet.getString("bContent");
				Timestamp bDate = rSet.getTimestamp("bDate");
				int bHit = rSet.getInt("bHit");
				int bGroup = rSet.getInt("bGroup");
				int bStep = rSet.getInt("bStep");
				int bIndent = rSet.getInt("bIndent");
				
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				dtos.add(dto);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
				try {
					if (rSet != null)
						rSet.close();
					if (pstm != null)
						pstm.close();
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return dtos;
	}
	
	public BDto contentView(String strId) {
		upHit(strId);
		
		BDto dto = null;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rSet = null;
		
		try {
			conn = dataSource.getConnection();
			String query = "select bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep , bIndent from mvc_board order by bGroup desc, bStep asc";
			pstm = conn.prepareStatement(query);
			rSet = pstm.executeQuery();
			
			while(rSet.next()) {
				int bId = rSet.getInt("bId");
				String bName = rSet.getString("bName");
				String bTitle = rSet.getString("bTitle");
				String bContent = rSet.getString("bContent");
				Timestamp bDate = rSet.getTimestamp("bDate");
				int bHit = rSet.getInt("bHit");
				int bGroup = rSet.getInt("bGroup");
				int bStep = rSet.getInt("bStep");
				int bIndent = rSet.getInt("bIndent");
				
				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);				
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
				try {
					if (rSet != null)
						rSet.close();
					if (pstm != null)
						pstm.close();
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return dto; 
	}
	
	public void write(String bName, String bTitle, String bContent) {
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = dataSource.getConnection();
			String query = "insert into mvc_board (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent)"
							+ " values(mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0)";
			pstm = conn.prepareStatement(query);
			pstm.setString(1,  bName);
			pstm.setString(2, bTitle);
			pstm.setString(3, bContent);
			
			int rn = pstm.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void modify(String strId, String bName, String bTitle, String bContent) {
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = dataSource.getConnection();
			String query = "update mvc_board set bName=?, bTitle=?, bContent=? where bId=?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, bName);
			pstm.setString(2, bTitle);
			pstm.setString(3, bContent);
			pstm.setInt(4, Integer.parseInt(strId));
			
			int rn = pstm.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void delete(String strId) {
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = dataSource.getConnection();
			String query = "delete from mvc_board where bId=?";
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, Integer.parseInt(strId));
			
			int rn = pstm.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public BDto reply_view(String strId) {
		BDto dto = null;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rSet = null;
		
		try {
			conn = dataSource.getConnection();
			String query = "select * from mvc_board where bId=?";
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, Integer.parseInt(strId));
			rSet = pstm.executeQuery();
			
			while(rSet.next()) {
				int bId = rSet.getInt("bId");
				String bName = rSet.getString("bName");
				String bTitle = rSet.getString("bTitle");
				String bContent = rSet.getString("bContent");
				Timestamp bDate = rSet.getTimestamp("bDate");
				int bHit = rSet.getInt("bHit");
				int bGroup = rSet.getInt("bGroup");
				int bStep = rSet.getInt("bStep");
				int bIndent = rSet.getInt("bIndent");
				
				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);				
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
				try {
					if (rSet != null)
						rSet.close();
					if (pstm != null)
						pstm.close();
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return dto; 
	}
	
	public void reply(String strId, String bName, String bTitle, String bContent, String bGroup, String bStep, String bIndent) {
		replyShape(bGroup, bStep);
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = dataSource.getConnection();
			String query = "insert into mvc_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent)"
							+ " values(mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, bName);
			pstm.setString(2, bTitle);
			pstm.setString(3, bContent);
			pstm.setInt(4, Integer.parseInt(bGroup));
			pstm.setInt(5, Integer.parseInt(bStep) + 1);
			pstm.setInt(6, Integer.parseInt(bIndent) + 1);
			
			int rn = pstm.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void upHit(String bId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			String query = "update mvc_board set bHit = bHit + 1 where bId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bId);
			
			int rn = preparedStatement.executeUpdate();
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	private void replyShape( String strGroup, String strStep) {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			String query = "update mvc_board set bStep = bStep + 1 where bGroup = ? and bStep > ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(strGroup));
			preparedStatement.setInt(2, Integer.parseInt(strStep));
			
			int rn = preparedStatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
}
