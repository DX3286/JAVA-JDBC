package my.api.access.work.function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class GetCombo {

	// Get combo list
	public static String[][] getlist(String src, String sql, String[] tolist) throws SQLException {
		String buildup_path = "jdbc:ucanaccess://" + src;
		Connection conDB = null;
		
		// Load Driver
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		}
		catch (ClassNotFoundException ex) {
			System.out.print("[Driver] Failed loading driver");
		}
		// Connection
		conDB = DriverManager.getConnection(buildup_path);
		Statement statement = conDB.createStatement();
		statement.execute(sql);
		ResultSet rs = statement.getResultSet();
		Vector<Object> v = new Vector<Object>();
		String[] v2 = new String[tolist.length];
		while (rs.next()) {
			for (int i = 0; i < v2.length; i++) {
				v2[i] = rs.getString(tolist[i]);
			}
			if (!v.contains(v2)) {
				v.add(v2);
				v2 = new String[tolist.length];
			}
		}
	    // Close
		statement.close();
		conDB.close();
		// Convert Vector<Object>: <{"A123","Who"}, {"A321","Who"}> to String[][]: {{"A123","Who"}, {"A321","Who"}}
		String[][] list = new String[v.size()][v2.length];
		for (int i = 0; i < v.size() ; i++) {
			String[] s = (String[]) v.get(i);
			for (int i2 = 0; i2 < v2.length; i2++) {				
				list[i][i2] = s[i2] ;
			}			
		}
		return list;
	}
}
