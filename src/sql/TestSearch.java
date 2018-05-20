package sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Savran Aleksei
 * ignore this class
 * for test purpose
 */
public class TestSearch {
public static void userSearch(String name, String surname) {
		
		String url = "jdbc:sqlite:MiniNetDB";
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
			String sql = "Select name, surname, age, status"
						+ "from users"
						+ "where Users.name =" + name + "and Users.surname =" + surname + ";";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString("name") +
				rs.getString("surname") +
				rs.getInt("age") +
				rs.getString("status"));
//				rs.getString("name");
//				rs.getString("surname");
//				rs.getInt("age");
//				rs.getString("status");
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
public static void main(String[] args) {
	String name = "John";
	String surname = "Smeet";
	userSearch(name, surname);
}

}
