package sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Savran Aleksei
 *This class implements method to show all users
 */
public class ShowAllUsers {
public static void userShowAll() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		String url = "jdbc:sqlite:MiniDB.db";
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
			String sql = "Select * from Profiles;";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString(2)+ "\t" + rs.getString(3) + "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(8) + "\t" + rs.getInt(7));
			}
		
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
	userShowAll();
}

}
