package my.api.access.work;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class MyTMod extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private Vector<Vector<Object>> data;
	private Vector<String> columnNames;

	private String[] col;
	private Object[][] dat;
	
	// Constructor
	public MyTMod(Vector<Vector<Object>> data, Vector<String> columnNames) {
		this.data = data;
		this.columnNames = columnNames;
		// Convert from vector to array for getValueAt, setValueAt...
		col = columnNames.toArray(new String[columnNames.size()]);
		dat = new Object[data.size()][columnNames.size()];
		for (int i = 0; i < data.size(); i++) {
			//System.out.println(data.get(i));
			dat[i] = data.get(i).toArray(new Object[data.get(i).size()]);
		}
	}
	
	public int getRowCount() {
		return dat.length;
	}
	
	public int getColumnCount() {
        return col.length;
    }
	
	public Object getValueAt(int row, int col) {
		return dat[row][col];
    }
	
	public void setValueAt(String value, int row, int col) {
        dat[row][col] = value;
        fireTableCellUpdated(row, col);
    }
	
	public String getColumnName(int index) {
	    return col[index];
	}
}