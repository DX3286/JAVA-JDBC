package my.api.access.work.function;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import my.api.access.main.Main;
import my.api.access.work.JDBC_D;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JDBC_Search extends JFrame {

	private static final long serialVersionUID = 1L;
	private String source = Main.source;
	private JPanel contentPane;
	private JTable table = new JTable();
	private JDBC_D driver = new JDBC_D();
	private JButton btn_avg;
	private JLabel lbl_tip_total_num;
	private JComboBox<String> combo_name, combo_course;
	private String[][] student_table, course_table;
	private String def = "\u8acb\u9078\u64c7";
	private int isSetUp = 0;
	
	private void refresh() {
		String sql = "";
		String sel_std = getridDetail(combo_name.getSelectedItem().toString());
		String sel_course = getridDetail(combo_course.getSelectedItem().toString());
		// Select course but haven't select std
		if (!sel_course.equals(def) && sel_std.equals(def)) {
			sql = "select r." + Main.score_sid + ", s." + Main.std_sname + ", c." + Main.course_cname + ", r." + Main.score_score
			+ " from " + Main.tableC + " r, " + Main.tableA + " s, " + Main.tableB + " c"
			+ " where "
			+ "r." + Main.score_sid + "= s." + Main.std_sid + " and "
			+ "r." + Main.score_cid + "= c." + Main.course_cid + " and "
			+ "r." + Main.score_cid + "=" + Geny.gen(new String[] {sel_course});
		}
		// Select std but haven't select course
		if (sel_course.equals(def) && !sel_std.equals(def)) {
			sql = "select c." + Main.course_cname + ", r." + Main.score_score
			+ " from " + Main.tableB + " c, " + Main.tableC + " r"
			+ " where r." + Main.score_cid + "= c." + Main.course_cid + " and"
			+ " r." + Main.score_sid + "=" + Geny.gen(new String[] {sel_std});
		}
		// Select course and select std
		if (!sel_course.equals(def) && !sel_std.equals(def)) {
			sql = "select c." + Main.course_cname + ", r." + Main.score_score
			+ " from " + Main.tableB + " c, " + Main.tableC + " r"
			+ " where r." + Main.score_cid + "= c." + Main.course_cid + " and"
			+ " r." + Main.score_sid + "=" + Geny.gen(new String[] {sel_std}) + " and"
			+ " c." + Main.course_cid + "=" + Geny.gen(new String[] {sel_course});
		}
		// Neither course and std are selected
		if (sel_course.equals(def) && sel_std.equals(def)) {
			table.setModel(new DefaultTableModel());
		}
		// Execute SQL
		if (!sql.isEmpty()) {
			try {
				table.setModel(driver.buildTable(source, sql));
				lbl_tip_total_num.setText(Integer.toString(table.getRowCount()));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void refresh(boolean isAVG) {
		// List all students average score
		String sql = "select r." + Main.score_sid + ", s." + Main.std_sname
				+ ", ROUND(AVG(r." + Main.score_score + "), 1) as \u5e73\u5747\u6210\u7e3e"
				+ " from " + Main.tableC + " r, " + Main.tableA + " s, " + Main.tableB + " c"
				+ " where r." + Main.score_sid + "= s." + Main.std_sid + " and"
				+ " r." + Main.score_cid + "= c." + Main.course_cid
				+ " group by r." + Main.score_sid + ", s." + Main.std_sname;
		try {
			table.setModel(driver.buildTable(source, sql));
			lbl_tip_total_num.setText(Integer.toString(table.getRowCount()));
		}
		catch (SQLException ex) {
			ex.printStackTrace();
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
		combo.addItem(def);
		for (int i = 0; i < list.length; i++) {
			combo.addItem(list[i][0] + "," + list[i][1]);
		}
		isSetUp++;
	}
	
	private String getridDetail(String s) {
		String[] tmp = s.split(",");
		return tmp[0];
	}
	
	/**
	 * Create the frame.
	 */
	public JDBC_Search() {
		// Frame
		setTitle("\u67e5\u8a62");
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
				
		// ScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(32, 160, 640, 256);
		contentPane.add(scrollPane);

		// ComboBox: Name
		combo_name = new JComboBox<String>();
		combo_name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isSetUp >= 2)
					refresh();
			}
		});
		combo_name.setFont(new Font("標楷體", Font.BOLD, 21));
		combo_name.setBounds(104, 16, 232, 32);
		contentPane.add(combo_name);
		
		// ComboBox: Course
		combo_course = new JComboBox<String>();
		combo_course.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isSetUp >= 2)
					refresh();
			}
		});
		combo_course.setFont(new Font("標楷體", Font.BOLD, 21));
		combo_course.setBounds(440, 16, 232, 32);
		contentPane.add(combo_course);
		
		// Button: AVG
		btn_avg = new JButton("\u6240\u6709\u4eba\u7684\u5e73\u5747\u6210\u7e3e");
		btn_avg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh(true);
			}
		});
		btn_avg.setFont(new Font("標楷體", Font.PLAIN, 20));
		btn_avg.setBounds(460, 120, 212, 32);
		contentPane.add(btn_avg);		
		
		// Tip: Total desc
		JLabel lbl_tip_total = new JLabel("\u7E3D\u8A08\uFF1A");
		lbl_tip_total.setFont(new Font("標楷體", Font.PLAIN, 16));
		lbl_tip_total.setBounds(32, 136, 52, 18);
		contentPane.add(lbl_tip_total);
		
		// Tip: Total num
		lbl_tip_total_num = new JLabel("0");
		lbl_tip_total_num.setFont(new Font("標楷體", Font.PLAIN, 16));
		lbl_tip_total_num.setBounds(80, 136, 48, 18);
		contentPane.add(lbl_tip_total_num);
		
		// Tip ID
		JLabel lbl_tip_name = new JLabel("\u5b78\u751f\uff1a");
		lbl_tip_name.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				combo_name.requestFocus();
			}
		});
		lbl_tip_name.setFont(new Font("標楷體", Font.BOLD, 24));
		lbl_tip_name.setBounds(24, 16, 80, 32);
		contentPane.add(lbl_tip_name);
		
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
	}
}
