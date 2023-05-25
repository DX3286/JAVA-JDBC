package my.api.access.work.function;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import my.api.access.main.Main;
import my.api.access.work.JDBC_D;

import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;

public class JDBC_SM extends JFrame {

	private static final long serialVersionUID = 1L;
	private String source = Main.source;
	private JPanel contentPane;
	private JTable table = new JTable();
	private JDBC_D driver = new JDBC_D();
	private JTextField txtEnterId;
	private JTextField txtEnterName;
	private JButton btn_clear_id;
	private JButton btn_clear_name;
	private JButton btn_new;
	private JButton btn_change;
	private JButton btn_del;
	private JLabel lbl_tip_total_num;
	private JCheckBox chkbox_auto;
	private JCheckBox chkbox_idchange;

	public void refresh() {
		String sql = "select * from " + Main.tableA;
		try {
			table.setModel(driver.buildTable(source, sql));
			lbl_tip_total_num.setText(Integer.toString(table.getRowCount()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the frame.
	 */
	public JDBC_SM() {
		// Frame
		setTitle("\u5B78\u751F\u8CC7\u6599\u7DAD\u8B77");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 720, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		// Table
		table.setFont(new Font("標楷體", Font.PLAIN, 24));
		table.setBackground(Color.LIGHT_GRAY);
		table.setRowHeight(24);
		table.setBounds(32, 160, 640, 256);
		contentPane.add(table);
		table.setAutoCreateRowSorter(true);
		
		// Table Listener
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//System.out.println(table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
				if (chkbox_auto.isSelected()) {
					if (table.getSelectedColumn() == Geny.genCIndex(table, Main.std_sid)) {
						txtEnterId.setText(table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString());
					}
					if (table.getSelectedColumn() == Geny.genCIndex(table, Main.std_sname)) {
						txtEnterName.setText(table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString());
					}
				}
			}
		});
		
		// ScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(32, 160, 640, 256);
		contentPane.add(scrollPane);
		table.setFillsViewportHeight(true);

		// Tip ID
		JLabel lbl_tip_id = new JLabel("\u5B78\u865F\uFF1A");
		lbl_tip_id.setFont(new Font("標楷體", Font.BOLD, 24));
		lbl_tip_id.setBounds(32, 24, 80, 32);
		contentPane.add(lbl_tip_id);
		
		// Tip Name
		JLabel lbl_tip_name = new JLabel("\u59D3\u540D\uFF1A");
		lbl_tip_name.setFont(new Font("標楷體", Font.BOLD, 24));
		lbl_tip_name.setBounds(32, 64, 80, 32);
		contentPane.add(lbl_tip_name);
		
		// TextField: ID
		txtEnterId = new JTextField();
		txtEnterId.setToolTipText("");
		txtEnterId.setFont(new Font("標楷體", Font.BOLD, 24));
		txtEnterId.setBounds(112, 24, 240, 32);
		contentPane.add(txtEnterId);
		txtEnterId.setColumns(10);
		
		// TextField: Name
		txtEnterName = new JTextField();
		txtEnterName.setToolTipText("");
		txtEnterName.setFont(new Font("標楷體", Font.BOLD, 24));
		txtEnterName.setColumns(10);
		txtEnterName.setBounds(112, 64, 240, 32);
		contentPane.add(txtEnterName);
		
		// Clear Textfield: ID
		btn_clear_id = new JButton("\u6E05\u9664");
		btn_clear_id.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtEnterId.setText("");
			}
		});
		btn_clear_id.setFont(new Font("標楷體", Font.PLAIN, 18));
		btn_clear_id.setBounds(360, 24, 72, 32);
		contentPane.add(btn_clear_id);
		
		// Clear Textfield: Name
		btn_clear_name = new JButton("\u6E05\u9664");
		btn_clear_name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtEnterName.setText("");
			}
		});
		btn_clear_name.setFont(new Font("標楷體", Font.PLAIN, 18));
		btn_clear_name.setBounds(360, 64, 72, 32);
		contentPane.add(btn_clear_name);
		
		// Button: New data
		btn_new = new JButton("\u65B0\u5EFA");
		btn_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Start
				if (!txtEnterId.getText().trim().equals("")) {
					if (!txtEnterName.getText().trim().equals("")) {
						// Check for same id
						int row = table.getRowCount();
						for (int r = 0; r < row; r++) {
							String t = table.getValueAt(r, Geny.genCIndex(table, Main.std_sid)).toString().trim().toUpperCase();
							if (t.equals(txtEnterId.getText().trim().toUpperCase())) {
								Infobox.error("\u5b78\u865f\u91cd\u8907\u3002");
								txtEnterId.setText("");
								return;
							}
						}
						// SQL
						String[] v = {txtEnterId.getText().trim().toUpperCase(), txtEnterName.getText().trim()};
						String sql = "insert into " + Main.tableA + "(" + Main.std_sid + ", " + Main.std_sname + ") values" + Geny.gen(v);
						try {
							driver.updateFile(source, sql);
							Infobox.message("\u8cc7\u6599\u65b0\u589e\u6210\u529f\u3002");
							txtEnterId.setText("");
							txtEnterName.setText("");
							refresh();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						return;
					}
				}
				Infobox.error("\u4e0d\u80fd\u662f\u7a7a\u7684\u3002");
				// End
			}
		});
		btn_new.setFont(new Font("標楷體", Font.PLAIN, 20));
		btn_new.setBounds(544, 24, 128, 32);
		contentPane.add(btn_new);
		
		// Button: Change data
		btn_change = new JButton("\u4FEE\u6539");
		btn_change.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Start
				if (!txtEnterId.getText().trim().equals("")) {
					if (!txtEnterName.getText().trim().equals("")) {
						// SQL
						String[] id = {txtEnterId.getText().trim()};
						String[] name = {txtEnterName.getText().trim()};
						String sql = "update " + Main.tableA + " set " + Main.std_sname + "=" + Geny.gen(name) + "where " + Main.std_sid + "=" + Geny.gen(id);
						if (chkbox_idchange.isSelected()) {
							sql = "update " + Main.tableA + " set " + Main.std_sid + "=" + Geny.gen(id) + "where " + Main.std_sname + "=" + Geny.gen(name);
						}
						try {
							driver.updateFile(source, sql);
							Infobox.message("\u8cc7\u6599\u4fee\u6539\u6210\u529f\u3002");
							txtEnterId.setText("");
							txtEnterName.setText("");
							refresh();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						return;
					}
				}
				Infobox.error("\u4e0d\u80fd\u662f\u7a7a\u7684\u3002");
				// End
			}
		});
		btn_change.setFont(new Font("標楷體", Font.PLAIN, 20));
		btn_change.setBounds(544, 64, 128, 32);
		contentPane.add(btn_change);
		
		// Button: Delete data
		btn_del = new JButton("\u522A\u9664");
		btn_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Start
				if (!txtEnterId.getText().trim().equals("")) {
					// SQL
					String[] id = {txtEnterId.getText().trim()};
					String sql = "delete from " + Main.tableA + " where " + Main.std_sid + "=" + Geny.gen(id);
					try {
						driver.updateFile(source, sql);
						// Sync only if Main.syncdel = true
						if (Main.syncdel) {
							sql = "delete from " + Main.tableC + " where " + Main.score_sid + "=" + Geny.gen(id);
							driver.updateFile(source, sql);
						}
						//
						Infobox.message("\u8cc7\u6599\u522a\u9664\u6210\u529f\u3002");
						txtEnterId.setText("");
						txtEnterName.setText("");
						refresh();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					return;
				}
				Infobox.error("\u5b78\u865f\u4e0d\u80fd\u662f\u7a7a\u7684\u3002");
				// End
			}
		});
		btn_del.setFont(new Font("標楷體", Font.PLAIN, 20));
		btn_del.setBounds(544, 104, 128, 32);
		contentPane.add(btn_del);
		
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
		
		// Auto Fill
		chkbox_auto = new JCheckBox("\u81EA\u52D5\u586B\u5165");
		chkbox_auto.setFont(new Font("標楷體", Font.PLAIN, 16));
		chkbox_auto.setBounds(152, 136, 96, 18);
		contentPane.add(chkbox_auto);
		
		// Change id
		chkbox_idchange = new JCheckBox("\u4FEE\u6539\u5B78\u865F");
		chkbox_idchange.setFont(new Font("標楷體", Font.PLAIN, 16));
		chkbox_idchange.setBounds(256, 136, 96, 18);
		contentPane.add(chkbox_idchange);
	}
}