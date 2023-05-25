package my.api.access.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import my.api.access.work.JDBC_Filter;
import my.api.access.work.function.Infobox;
import my.api.access.work.function.JDBC_CM;
import my.api.access.work.function.JDBC_RC;
import my.api.access.work.function.JDBC_SM;
import my.api.access.work.function.JDBC_Search;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	public static String source = "C:/Users/Administrator/Documents/JavaProject/StudentData.accdb";
	public static String tableA = "Student", tableB = "Course", tableC = "Score";
	public static String std_sid = "std_id", std_sname = "std_name";
	public static String course_cid = "course_id", course_cname = "course_name";
	public static String score_sid = "std_id", score_cid = "course_id", score_score = "score";
	public static boolean syncdel = true;
	
	private JPanel contentPane;
	private JTextField textField_linkfile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setTitle("\u7BA1\u7406\u7CFB\u7D71");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 319, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// File Chooser
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(new JDBC_Filter("accdb", ".accdb (Access)"));
		
		// Button: Student management
		JButton btn_student_manage = new JButton("\u5B78\u751F\u8CC7\u6599\u7DAD\u8B77");
		btn_student_manage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDBC_SM sm = new JDBC_SM();
				sm.setResizable(false);
				sm.setLocationRelativeTo(null);
				sm.refresh();
				sm.setVisible(true);
			}
		});
		btn_student_manage.setFont(new Font("標楷體", Font.BOLD, 24));
		btn_student_manage.setBounds(32, 32, 240, 48);
		contentPane.add(btn_student_manage);
		
		// Button: Course management
		JButton btn_course_manage = new JButton("\u8AB2\u7A0B\u8CC7\u6599\u7DAD\u8B77");
		btn_course_manage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDBC_CM cm = new JDBC_CM();
				cm.setResizable(false);
				cm.setLocationRelativeTo(null);
				cm.refresh();
				cm.setVisible(true);
			}
		});
		btn_course_manage.setFont(new Font("標楷體", Font.BOLD, 24));
		btn_course_manage.setBounds(32, 104, 240, 48);
		contentPane.add(btn_course_manage);
		
		// Button: Score input
		JButton btn_student_score = new JButton("\u6210\u7E3E\u8F38\u5165");
		btn_student_score.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDBC_RC rc = new JDBC_RC();
				rc.setResizable(false);
				rc.setLocationRelativeTo(null);
				rc.InitCombo();
				rc.refresh();
				rc.setVisible(true);
			}
		});
		btn_student_score.setFont(new Font("標楷體", Font.BOLD, 24));
		btn_student_score.setBounds(32, 176, 240, 48);
		contentPane.add(btn_student_score);
		
		// Button: Search
		JButton btn_search = new JButton("\u67E5\u8A62");
		btn_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDBC_Search s = new JDBC_Search();
				s.setResizable(false);
				s.setLocationRelativeTo(null);
				s.InitCombo();
				s.setVisible(true);
			}
		});
		btn_search.setFont(new Font("標楷體", Font.BOLD, 24));
		btn_search.setBounds(32, 248, 240, 48);
		contentPane.add(btn_search);
		
		// Tip: Current file location
		textField_linkfile = new JTextField();
		textField_linkfile.setFont(new Font("Consolas", Font.PLAIN, 16));
		textField_linkfile.setEditable(false);
		textField_linkfile.setBackground(Color.LIGHT_GRAY);
		textField_linkfile.setText("Default Location");
		textField_linkfile.setBounds(10, 410, 256, 24);
		contentPane.add(textField_linkfile);
		textField_linkfile.setColumns(10);
		
		// Tip: Current file location
		JLabel lbl_tip_fileloc = new JLabel("\u6A94\u6848\u8DEF\u5F91\uFF1A");
		lbl_tip_fileloc.setFont(new Font("標楷體", Font.BOLD, 16));
		lbl_tip_fileloc.setBounds(10, 382, 128, 24);
		contentPane.add(lbl_tip_fileloc);
		
		// Button: Open file browser
		JButton btn_browsefile = new JButton("...");
		btn_browsefile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc.showOpenDialog(null);
				String file = fc.getSelectedFile().toString();
				int fidx = file.lastIndexOf(".");
				if (fidx > 0 && fidx < file.length() - 1) {
					if (file.substring(fidx + 1).equals("accdb")) {
						System.out.println("File selected: " + file);
						source = file;
						textField_linkfile.setText(source);
					}
				}
			}
		});
		btn_browsefile.setFont(new Font("Consolas", Font.BOLD, 12));
		btn_browsefile.setBounds(270, 410, 24, 24);
		contentPane.add(btn_browsefile);
		
		// Set Sync state (default true)
		JCheckBox chkbox_sync = new JCheckBox("\u8CC7\u6599\u540C\u6B65");
		chkbox_sync.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkbox_sync.isSelected()) {
					syncdel = true;
					return;
				}
				syncdel = false;
			}
		});
		chkbox_sync.setSelected(true);
		chkbox_sync.setFont(new Font("標楷體", Font.BOLD, 16));
		chkbox_sync.setBounds(32, 302, 96, 24);
		contentPane.add(chkbox_sync);
		
		// Setting Windows
		JLabel lbl_settings = new JLabel("<HTML><U>\u8A2D\u5B9A...</U></HTML>");
		lbl_settings.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Settings set = new Settings();
				set.setResizable(false);
				set.setLocationRelativeTo(null);
				set.setVisible(true);
				set.refresh();
			}
		});
		lbl_settings.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lbl_settings.setFont(new Font("標楷體", Font.BOLD, 16));
		lbl_settings.setBounds(208, 302, 64, 24);
		contentPane.add(lbl_settings);
		
		// Info
		JLabel lbl_info = new JLabel("A6631285@2019 Ver 1.0");
		lbl_info.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Infobox.message("\u4f5c\u8005\uff1a\u5468\u5b9a\u61b2");
			}
		});
		lbl_info.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lbl_info.setFont(new Font("Consolas", Font.ITALIC, 16));
		lbl_info.setForeground(new Color(180, 180, 180));
		lbl_info.setBounds(4, 4, 200, 16);
		contentPane.add(lbl_info);
	}
}