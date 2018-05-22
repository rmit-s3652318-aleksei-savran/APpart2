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
public static void userDelete(String name) {
		
		String url = "jdbc:sqlite:MiniDB.db";
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
			String sql = "Delete from Profiles where name = ?;";
			PreparedStatement pstmn = con.prepareStatement(sql);
			pstmn.setString(1, name);
			String sql2 = "Delete from relations where profile1 = ? or profile2 = ?";
			PreparedStatement pstmn2 = con.prepareStatement(sql2);
			pstmn2.setString(1, name);
			pstmn2.setString(2, name);
			pstmn.execute();
			pstmn2.execute();
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


}
