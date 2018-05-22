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

import gui.*;

/**
 * @author Savran Aleksei
 *This class implements search function
 */
public class SearchQueries {
public static Profile userSearch(String name) throws Exception {
		Profile pr = null;
		String url = "jdbc:sqlite:MiniDB.db";
		Connection con = null;
		FileOutputStream fos = null;
		try {
			con = DriverManager.getConnection(url);
			String sql = "Select * from Profiles where name = ? ";
					
			PreparedStatement pstmn = con.prepareStatement(sql);
			pstmn.setString(1, name);
			
			ResultSet rs = pstmn.executeQuery();
			
			String filename = "temp.jpg";
			File f = new File(filename);
			fos = new FileOutputStream(f);
			
			while (rs.next()) {
				String prName = rs.getString(2);
				String prStatus = rs.getString(4);
				String prSex = rs.getString(5);
				Integer prAge = rs.getInt(6);
				String prState = rs.getString(7);
				if (prAge > 16) {
					pr = new Adult(prName, "temp.jpg", prStatus, prSex, prAge, prState);
				} if (prAge > 2 && prAge < 17) {
					pr = new Child(prName, "temp.jpg", prStatus, prSex, prAge, prState, null, null);// modify
				} if (prAge < 3) {
					pr = new YoungChild(prName, "temp.jpg", prStatus, prSex, prAge, prState, null, null);// modify
				}
				
			}
			
			
			
			while (rs.next()) {
				InputStream is = rs.getBinaryStream("image");
				byte[] buffer = new byte[1024];
				while(is.read(buffer) > 0) {
					fos.write(buffer);
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
		//System.out.println(pr.getname() + " " + pr.get_state());
		return pr;
	}

public static Profile userSearchImage(String name) throws Exception {
	Profile pr = null;
	String url = "jdbc:sqlite:MiniDB.db";
	Connection con = null;
	FileOutputStream fos = null;
	try {
		con = DriverManager.getConnection(url);
		String sql = "Select * from Profiles where name = ? ";
				
		PreparedStatement pstmn = con.prepareStatement(sql);
		pstmn.setString(1, name);
		
		ResultSet rs = pstmn.executeQuery();
		
		String filename = "temp.jpg";
		File f = new File(filename);
		fos = new FileOutputStream(f);
		
		while (rs.next()) {
			String prName = rs.getString(2);
			String prStatus = rs.getString(4);
			String prSex = rs.getString(5);
			Integer prAge = rs.getInt(6);
			String prState = rs.getString(7);
			if (prAge > 16) {
				pr = new Adult(prName, "temp.jpg", prStatus, prSex, prAge, prState);
			} if (prAge > 2 && prAge < 17) {
				pr = new Child(prName, "temp.jpg", prStatus, prSex, prAge, prState, null, null);// modify
			} if (prAge < 3) {
				pr = new YoungChild(prName, "temp.jpg", prStatus, prSex, prAge, prState, null, null);// modify
			}
			
		}
		
		
		
		while (rs.next()) {
			InputStream is = rs.getBinaryStream("image");
			byte[] buffer = new byte[1024];
			while(is.read(buffer) > 0) {
				fos.write(buffer);
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
	//System.out.println(pr.getname() + " " + pr.get_state());
	return pr;
}

public static int getUserID(String name) throws ClassNotFoundException {
	Class.forName("org.sqlite.JDBC");
	String url = "jdbc:sqlite:MiniDB.db";
	Connection con = null;
	int usID = 0;
	try {
		
		con = DriverManager.getConnection(url);
		String getId = "select id from Profiles where name = ?";
		PreparedStatement pstmID = con.prepareStatement(getId);
		ResultSet rs = pstmID.executeQuery();
		con.commit();
		while (rs != null) {
			usID = rs.getInt("id");
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
	return usID;
	
	
}
public static void main(String[] args) throws Exception {
	String name = "Han Solo";
	userSearch(name);
}

}
