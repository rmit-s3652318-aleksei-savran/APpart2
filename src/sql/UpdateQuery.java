package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Savran Aleksei
 *Not finished
 */
public class UpdateQuery {
	public static void updateProfile(String name, String surname, String status, String sex, int age, String state) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		String url = "jdbc:sqlite:MiniDB.db";
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
			String sql = "update profiles set status = ?, sex = ?, age = ?, state = ? where name = ? and surname = ?;";
			PreparedStatement pstmn = con.prepareStatement(sql);
			pstmn.setString(1, status);
			pstmn.setString(2, sex);
			pstmn.setInt(3, age);
			pstmn.setString(4, state);
			pstmn.setString(5, name);
			pstmn.setString(6, surname);
			
			pstmn.execute();
			con.setAutoCommit(true);
			
		} catch (SQLException e) {
			System.out.println(e.getMessage()); 
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e2) {			
					System.out.println(e2.getMessage()); 
				}
			}
		}
	}
	
	
	
	public static void main(String[] args) throws ClassNotFoundException {
		String name = "Gollum";
		String surname = "Smeagol";
		String status = "Looking for my precious";
		String sex = "M";
		Integer age = 500;
		String state = "Shire";
		updateProfile(name, surname, status, sex, age, state);
	}
}
