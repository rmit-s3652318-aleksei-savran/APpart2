package sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Savran Aleksei
 *This class creates the table with the following arguments: id, name, surname, image, status, sex, age, state. 
 *It's used just for creating a table and not used anywhere in the app.
 */
public class TableClass {
	public static void createTable() {
		String sql = "create table Profiles (id integer primary key, name text not null, surname text not null, "
				+ "image BLOB, status text not null, sex text not null, age int not null, state text not null);";
		String sql2 = "CREATE TABLE relations (id INTEGER REFERENCES Profiles (id) NOT NULL PRIMARY KEY, friendlist STRING, "
				+ "colleagues STRING, spouse STRING, dependants STRING) WITHOUT ROWID;";
		String url = "jdbc:sqlite:MiniDB.db";
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
			Statement stmn = con.createStatement();
			stmn.executeQuery(sql);
			Statement stmn2 = con.createStatement();
			stmn2.executeQuery(sql2);
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
		createTable();
	}

}
