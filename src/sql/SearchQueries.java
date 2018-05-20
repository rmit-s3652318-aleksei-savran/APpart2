package sql;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Savran Aleksei
 *This class implements search function
 */
public class SearchQueries {
public static void userSearch(String name, String surname) throws IOException {
		
		String url = "jdbc:sqlite:MiniDB.db";
		Connection con = null;
		FileOutputStream fos = null;
		try {
			con = DriverManager.getConnection(url);
			String sql = "Select * from Profiles where "
					+ "name = ? "
					+ "and surname = ?";
			PreparedStatement pstmn = con.prepareStatement(sql);
			pstmn.setString(1, name);
			pstmn.setString(2, surname);
			
			ResultSet rs = pstmn.executeQuery();
			
			String filename = "temp.jpg";
			File f = new File(filename);
			fos = new FileOutputStream(f);
			
			
			while (rs.next()) {
				InputStream is = rs.getBinaryStream("image");
				byte[] buffer = new byte[1024];
				while(is.read(buffer) > 0) {
					fos.write(buffer);
				}
				System.out.println(rs.getString("name") + "\t" +
				rs.getString("surname") + "\t" +
				rs.getInt("age") + "\t" +
				rs.getString("status"));
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
public static void main(String[] args) throws IOException {
	String name = "Han";
	String surname = "Solo";
	userSearch(name, surname);
}

}
