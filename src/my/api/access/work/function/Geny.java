package my.api.access.work.function;

import javax.swing.JTable;

public class Geny {

	public static String gen(String[] v) {
		String value = null;
		if (v.length > 1) {
			value = "(";
			for (int i = 0; i < v.length; i++) {
				value += "'" + v[i] + "'";
				if (i != v.length - 1) {
					value += ", ";
				}
			}
			value += ")";
		}
		else {
			value = "'" + v[0] + "'";
		}
		return value;
	}
	
	public static int genCIndex(JTable table, String colname) {
		int index = -1;
		int colcount = table.getColumnCount();
		for (int i = 0; i < colcount; i++) {
			if (table.getColumnName(i).equals(colname)) {
				index = i;
				break;
			}
		}
		return index;
	}
}
