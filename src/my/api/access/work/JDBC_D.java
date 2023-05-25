package my.api.access.work;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Vector;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import net.ucanaccess.jdbc.JackcessOpenerInterface;

public class JDBC_D {
	
	private Connection conDB = null;
	
	// Build Table (loadall)
	public MyTMod buildTable(String src, String sql) throws SQLException {
		String buildup_path = "jdbc:ucanaccess://" + src;
		
		// Load Driver
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		}
		catch (ClassNotFoundException ex) {
			System.out.print("[Driver] Failed loading driver");
		}
		// Connection
		conDB = DriverManager.getConnection(buildup_path);
		if (conDB != null) {
			//System.out.println("[Driver] Connection created");
		}
		Statement statement = conDB.createStatement();
		if (statement != null) {
			//System.out.println("[Driver] Statement created");
		}
		// ResultSet
		ResultSet rs = statement.executeQuery(sql);
		ResultSetMetaData metaData = rs.getMetaData();
		
		// names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }
	    
	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }	    
	    // Close
		System.out.println("[Driver] Success");
		statement.close();
		conDB = null;
		return new MyTMod(data,columnNames);		
	}
	
	// Update File (executeUpdate)
	public void updateFile(String src, String sql) throws SQLException {
		String buildup_path = "jdbc:ucanaccess://" + src;
		
		// Load Driver
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		}
		catch (ClassNotFoundException ex) {
			System.out.print("[Driver] Failed loading driver");
		}
		// Connection
		conDB = DriverManager.getConnection(buildup_path);
		if (conDB != null) {
			//System.out.println("[Driver] Connection created");
		}
		Statement statement = conDB.createStatement();
		if (statement != null) {
			//System.out.println("[Driver] Statement created");
		}
		// SQL
		statement.executeUpdate(sql);
		System.out.println("[Driver] Success");
		// Close
		conDB.close();
		statement.close();
		conDB = null;
	}
}	