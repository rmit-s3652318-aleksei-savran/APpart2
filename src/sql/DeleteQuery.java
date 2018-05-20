package sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Savran Aleksei
 *This class implements deleting of the user
 */
public class DeleteQuery {
public static void userDelete(String name, String surname) {
		
		String url = "jdbc:sqlite:MiniNetDB";
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
			String sql = "Delete * from Users"
					+ "where name = ? and surname = ?;";
			PreparedStatement pstmn = con.prepareStatement(sql);
			pstmn.setString(1, name);
			pstmn.setString(2, surname);
			ResultSet rs = pstmn.executeQuery();
		
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
public static void main(String[] args) {
	String name = "John";
	String surname = "Smeet";
	userDelete(name, surname);
}

}
