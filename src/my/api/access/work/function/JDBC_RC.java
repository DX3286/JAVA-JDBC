package my.api.access.work.function;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import my.api.access.main.Main;
import my.api.access.work.JDBC_D;
import javax.swing.JComboBox;

public class JDBC_RC extends JFrame {

	private static final long serialVersionUID = 1L;
	private String source = Main.source;
	private JPanel contentPane;
	private JTable table = new JTable();
	private JDBC_D driver = new JDBC_D();
	private JTextField txtEnterScore;
	private JButton btn_new, btn_change;
	private JLabel lbl_tip_total_num;
	private JCheckBox chkbox_auto, chkbox_detail;
	private JComboBox<String> combo_name, combo_course;
	private String[][] student_table, course_table;
	private boolean isDetail = false;
	private int selectedCelln = 0, selectedCellc = 0;
	
	public void refresh() {
		String sql = "select * from " + Main.tableC;
		if (isDetail) {
			sql = "select r." + Main.score_sid + ", s." + Main.std_sname + ", r." + Main.score_cid + ", c." + Main.course_cname + ", r." + Main.score_score
					+ " from " + Main.tableC + " r, " + Main.tableA + " s, " + Main.tableB + " c"
					+ " where r." + Main.score_sid + "= s." + Main.std_sid + " and r." + Main.score_cid + "= c." + Main.course_cid;
		}
		try {
			table.setModel(driver.buildTable(source, sql));
			lbl_tip_total_num.setText(Integer.toString(table.getRowCount()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void InitCombo() {
		// Initialize ComboBox
		String[] cn = {Main.std_sid, Main.std_sname};
		String[] cc = {Main.course_cid, Main.course_cname};
		try {
			student_table = GetCombo.getlist(source, "select * from " + Main.tableA, cn);
			course_table = GetCombo.getlist(source, "select * from " + Main.tableB, cc);
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		createcombo(combo_name, student_table);
		createcombo(combo_course, course_table);
	}
	
	private void createcombo(JComboBox<String> combo, String[][] list) {
		combo.removeAllItems();
		for (int i = 0; i < list.length; i++) {
			if (isDetail) {
				combo.addItem(list[i][0] + "," + list[i][1]);
			}
			else {
				combo.addItem(list[i][0]);
			}
		}
	}
	
	private void combotoIndex() {
		combo_name.setSelectedIndex(selectedCelln);
		combo_course.setSelectedIndex(selectedCellc);
	}
	
	private void combotoIndex(JComboBox<String> c, String match) {
		int count = c.getItemCount();
		for (int i = 0; i < count; i++) {
			if (getridDetail(c.getItemAt(i)).equals(match)) {
				c.setSelectedIndex(i);
				break;
			}
		}
	}

	private String getridDetail(String s) {
		String[] tmp = s.split(",");
		return tmp[0];
	}
	
	/**
	 * Create the frame.
	 */
	public JDBC_RC() {
		// Frame
		setTitle("\u6210\u7E3E\u8F38\u5165");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 720, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Mouse wheel
		ComboWheel cw = new ComboWheel();
		this.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				List<JComboBox<String>> combo = List.of(combo_name, combo_course);
				cw.WheelAction(e, combo);
			}
		});
				
		// Table
		table.setFont(new Font("標楷體", Font.PLAIN, 24));
		table.setBackground(Color.LIGHT_GRAY);
		table.setRowHeight(24);
		table.setBounds(32, 160, 640, 256);
		contentPane.add(table);
		table.setAutoCreateRowSorter(true);
		table.setFillsViewportHeight(true);
		
		// Table Listener
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (chkbox_auto.isSelected()) {
					try {
						String name = (String) table.getValueAt(table.getSelectedRow(), Geny.genCIndex(table, Main.score_sid));
						String course = (String) table.getValueAt(table.getSelectedRow(), Geny.genCIndex(table, Main.score_cid));
						Double d = (Double) table.getValueAt(table.getSelectedRow(), Geny.genCIndex(table, Main.score_score));
						int i = d.intValue();
						txtEnterScore.setText(Integer.toString(i));
						combotoIndex(combo_name, name);
						combotoIndex(combo_course, course);
					}
					catch (IndexOutOfBoundsException ex) {
						System.out.println("[Error] Table not initialize");
					}
				}
			}
		});
				
		// ScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(32, 160, 640, 256);
		contentPane.add(scrollPane);

		// ComboBox: Name
		combo_name = new JComboBox<String>();
		combo_name.setFont(new Font("標楷體", Font.BOLD, 21));
		combo_name.setBounds(104, 16, 232, 32);
		contentPane.add(combo_name);
		
		// ComboBox: Course
		combo_course = new JComboBox<String>();
		combo_course.setFont(new Font("標楷體", Font.BOLD, 21));
		combo_course.setBounds(440, 16, 232, 32);
		contentPane.add(combo_course);
		
		// TextField: Score
		txtEnterScore = new JTextField();
		txtEnterScore.setToolTipText("");
		txtEnterScore.setFont(new Font("標楷體", Font.BOLD, 24));
		txtEnterScore.setColumns(10);
		txtEnterScore.setBounds(104, 64, 200, 32);
		contentPane.add(txtEnterScore);
		
		// Button: New data
		btn_new = new JButton("\u65B0\u5EFA");
		btn_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Start
				if (!txtEnterScore.getText().trim().equals("")) {
					String a1 = null, a2 = null;
					String a3 = txtEnterScore.getText().trim();
					// Did ComboBox include anything?
					try {
						a1 = getridDetail(combo_name.getSelectedItem().toString().trim().toUpperCase());
						a2 = getridDetail(combo_course.getSelectedItem().toString().trim().toUpperCase());
					} 
					catch (NullPointerException ex) {
						System.out.println("[Error] Failed to get value from ComboBox");
						return;
					}
					// Check if exists
					int row = table.getRowCount();
					for (int r = 0; r < row; r++) {
						String t0 = table.getValueAt(r, Geny.genCIndex(table, Main.score_sid)).toString().trim().toUpperCase();
						String t1 = table.getValueAt(r, Geny.genCIndex(table, Main.score_cid)).toString().trim().toUpperCase();
						if (t0.equals(a1) && t1.equals(a2)) {
							Infobox.error("\u59d3\u540d\uff0c\u8ab2\u7a0b\u8cc7\u6599\u5df2\u5b58\u5728\u3002" + 
									"\u8acb\u4f7f\u7528\u4fee\u6539\u9375\u3002");
							return;
						}
					}
					// Start executing SQL
					String[] v = {a1, a2, a3};
					String sql = "insert into " + Main.tableC + "(" + Main.score_sid + ", " + Main.score_cid + ", " + Main.score_score + ") values" + Geny.gen(v);
					try {
						driver.updateFile(source, sql);
						Infobox.message("\u8cc7\u6599\u65b0\u589e\u6210\u529f\u3002");
						txtEnterScore.setText("");
						refresh();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					return;
				}
				Infobox.error("\u6210\u7E3E\u4e0d\u80fd\u662f\u7a7a\u7684\u3002");
				// End
			}
		});
		btn_new.setFont(new Font("標楷體", Font.PLAIN, 20));
		btn_new.setBounds(400, 120, 128, 32);
		contentPane.add(btn_new);
		
		// Button: Change data
		btn_change = new JButton("\u4FEE\u6539");
		btn_change.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Start
				if (!txtEnterScore.getText().trim().equals("")) {
					String a1 = null, a2 = null;
					String a3 = txtEnterScore.getText().trim();
					// Did ComboBox include anything?
					try {
						a1 = getridDetail(combo_name.getSelectedItem().toString().trim().toUpperCase());
						a2 = getridDetail(combo_course.getSelectedItem().toString().trim().toUpperCase());
					}
					catch (NullPointerException ex) {
						System.out.println("[Error] Failed to get value from ComboBox");
						return;
					}
					// Check if exists
					int row = table.getRowCount();
					for (int r = 0; r < row; r++) {
						String t0 = table.getValueAt(r, Geny.genCIndex(table, Main.score_sid)).toString().trim().toUpperCase();
						String t1 = table.getValueAt(r, Geny.genCIndex(table, Main.score_cid)).toString().trim().toUpperCase();
						if (t0.equals(a1) && t1.equals(a2)) {
							// If exists
							String[][] v = {{a1}, {a2}, {a3}};
							String sql = "update " + Main.tableC + " set " + Main.score_score + "=" + Geny.gen(v[2]) + " where " + Main.score_sid + "="
											+ Geny.gen(v[0]) + " and " + Main.score_cid + "=" + Geny.gen(v[1]);
							try {
								driver.updateFile(source, sql);
								Infobox.message("\u8cc7\u6599\u4fee\u6539\u6210\u529f\u3002");
								txtEnterScore.setText("");
								refresh();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							return;
						}
					}
					// Not exists
					Infobox.error("\u59d3\u540d\uff0c\u8ab2\u7a0b\u8cc7\u6599\u4e0d\u5b58\u5728\u3002"
							 + "\u8acb\u65b0\u5efa\u4e00\u500b\u3002");
					return;
				}
				Infobox.error("\u6210\u7E3E\u4e0d\u80fd\u662f\u7a7a\u7684\u3002");
				// End
			}
		});
		btn_change.setFont(new Font("標楷體", Font.PLAIN, 20));
		btn_change.setBounds(544, 120, 128, 32);
		contentPane.add(btn_change);
		
		// Auto Fill
		chkbox_auto = new JCheckBox("\u81EA\u52D5\u586B\u5165");
		chkbox_auto.setFont(new Font("標楷體", Font.PLAIN, 16));
		chkbox_auto.setBounds(152, 136, 96, 18);
		contentPane.add(chkbox_auto);
		
		// Detail list
		chkbox_detail = new JCheckBox("\u8a73\u7d30");
		chkbox_detail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedCelln = combo_name.getSelectedIndex();
				selectedCellc = combo_course.getSelectedIndex();
				if (chkbox_detail.isSelected()) {
					isDetail = true;
				}
				else {
					isDetail = false;
				}
				createcombo(combo_name, student_table);
				createcombo(combo_course, course_table);
				refresh();
				combotoIndex();
			}
		});
		chkbox_detail.setFont(new Font("標楷體", Font.PLAIN, 16));
		chkbox_detail.setBounds(256, 136, 96, 18);
		contentPane.add(chkbox_detail);
		
		
		// Tip: Total desc
		JLabel lbl_tip_total = new JLabel("\u7E3D\u8A08\uFF1A");
		lbl_tip_total.setFont(new Font("標楷體", Font.PLAIN, 16));
		lbl_tip_total.setBounds(32, 136, 52, 18);
		contentPane.add(lbl_tip_total);
		
		// Tip: Total num
		lbl_tip_total_num = new JLabel("");
		lbl_tip_total_num.setFont(new Font("標楷體", Font.PLAIN, 16));
		lbl_tip_total_num.setBounds(80, 136, 48, 18);
		contentPane.add(lbl_tip_total_num);
		
		// Tip ID
		JLabel lbl_tip_name = new JLabel("\u59D3\u540D\uFF1A");
		lbl_tip_name.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				combo_name.requestFocus();
			}
		});
		lbl_tip_name.setFont(new Font("標楷體", Font.BOLD, 24));
		lbl_tip_name.setBounds(24, 16, 80, 32);
		contentPane.add(lbl_tip_name);
		
		// Tip Score
		JLabel lbl_tip_score = new JLabel("\u6210\u7E3E\uFF1A");
		lbl_tip_score.setFont(new Font("標楷體", Font.BOLD, 24));
		lbl_tip_score.setBounds(24, 64, 80, 32);
		contentPane.add(lbl_tip_score);
		
		// Tip Course
		JLabel lbl_tip_course = new JLabel("\u8AB2\u7A0B\uFF1A");
		lbl_tip_course.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				combo_course.requestFocus();
			}
		});
		lbl_tip_course.setFont(new Font("標楷體", Font.BOLD, 24));
		lbl_tip_course.setBounds(356, 16, 80, 32);
		contentPane.add(lbl_tip_course);
		
		// Manual sync
		JLabel lbl_sync = new JLabel("<HTML><U>\u624B\u52D5\u540C\u6B65</U></HTML>");
		if (!Main.syncdel) {
			lbl_sync.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lbl_sync.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					ArrayList<String> sidlist, cidlist;
					sidlist = getmatch(student_table, Main.score_sid);
					cidlist = getmatch(course_table, Main.score_cid);
					// Start
					String sql;
					int count = table.getRowCount();
					try {
					// Delete std_id which doesn't exit
					if (!sidlist.isEmpty()) {
						for (int i = 0; i < sidlist.size(); i++) {
							String[] v = {sidlist.get(i)};
							sql = "delete from " + Main.tableC + " where " + Main.score_sid + "=" + Geny.gen(v);
							driver.updateFile(source, sql);
						}	
					}
					// Delete course_id which doesn't exit
					if (!cidlist.isEmpty()) {
						for (int i = 0; i < cidlist.size(); i++) {
							String[] v = {cidlist.get(i)};
							sql = "delete from " + Main.tableC + " where " + Main.score_cid + "=" + Geny.gen(v);
							driver.updateFile(source, sql);
						}
					}
					refresh();
					Infobox.message("\u5df2\u522a\u9664" + (count - table.getRowCount()) + "\u7b46\u8cc7\u6599\u3002");
					}
					catch (SQLException e1) {
						e1.printStackTrace();
					}
					// End
				}
			});
		}
		lbl_sync.setFont(new Font("標楷體", Font.BOLD, 16));
		lbl_sync.setBounds(600, 418, 72, 20);
		contentPane.add(lbl_sync);
	}
	
	// Use only for manual sync
	private ArrayList<String> getmatch(String[][] t, String colname) {
		ArrayList<String> list = new ArrayList<String>();
		boolean match = false;
		for (int i = 0; i < table.getRowCount(); i++) {
			String id = table.getValueAt(i, Geny.genCIndex(table, colname)).toString();
			for (String[] x:t) {
				if (id.equals(x[0])) {
					match = true;
					break;
				}
			}
			if (!match && !list.contains(id)) {
				list.add(id);
			}
			match = false;
		}
		return list;
	}
}
