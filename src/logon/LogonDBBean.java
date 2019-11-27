package logon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import 섯다.User;

public class LogonDBBean {
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	public int ck=-1;
	public String money;
	
	private static LogonDBBean instance = new LogonDBBean();
	
	public static LogonDBBean getInstance() {
		return instance;
	}
	
	private LogonDBBean() {
		
	}
	
	public Connection getConn()  {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@net.yjc.ac.kr:1521:orcl";
			String id = "s1501207";
			String pw = "p1501207";
			conn =DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;	
	}
	
	public int Login(String id, String pw) {
		try {
			conn = getConn();
			pstmt = conn.prepareStatement("select * from USERS where USERID = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String userpw = rs.getString("USERPASSWORD");
//				money = rs.getString("USERMONEY");
				if(userpw.equals(pw)) { // 로그인 성공
					ck = 1;
					JOptionPane.showMessageDialog(null, "환영합니다 "+ id + "님");
				} else { // 비밀번호 틀림
					ck = 0;
					JOptionPane.showMessageDialog(null, "비밀번호가 맞지 않습니다.");
				}
			} else {
				ck = -1; // 아이디가 존재 하지 않음
				JOptionPane.showMessageDialog(null, "존재하지 않는 아이디 입니다.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ck;
	}
	
	public int UserCheck(String id) {
		try {
			conn = getConn();
			pstmt = conn.prepareStatement("select * from USERS where USERID=?");
			pstmt.setString(1, id);	
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return 1; 
			}  else {
				return -1;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	
	public void Join(String id, String pw) {
		try {
			conn = getConn();
			pstmt = conn.prepareStatement("insert into USERS VALUES(?,?,1000000)");
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.executeUpdate();
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//회원 정보 가져오기
	public User getUser(String id) {
		conn = getConn();
		User user=null;
		StringBuffer sql = new StringBuffer();
		sql.append("select * from USERS where USERID=?");
		try {
			user = new User();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1,id);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				user.setUserId(rs.getString("USERID"));
				user.setMoney(rs.getString("USERMONEY"));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return user;
	}
	
}
