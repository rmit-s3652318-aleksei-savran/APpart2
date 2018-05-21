package sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import gui.Adult;

/**
 * @author Savran Aleksei
 *This class implements method to show all users
 */
public class ShowAllUsers {
public static ArrayList<Adult> userShowAll() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		String url = "jdbc:sqlite:MiniDB.db";
		Connection con = null;
		ArrayList<Adult> adultsSQL = new ArrayList<>();
		try {
			con = DriverManager.getConnection(url);
			String sql = "Select * from Profiles;";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				if (rs.getInt(6) > 16) {
					adultsSQL.add(new Adult(rs.getString(2), rs.getString(2) + ".jpg", rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7))) ;
				}
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
		return adultsSQL;
	}
public static void main(String[] args) throws ClassNotFoundException {
	userShowAll();
}

}
