package my.api.access.main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import my.api.access.work.function.Infobox;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Settings extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ButtonGroup group = new ButtonGroup();
	private JRadioButton rbtn_student, rbtn_course, rbtn_score;
	private JTextField txt_form_name, txt_colA, txt_colB, txt_colC;
	private JButton btn_save;
	
	public void refresh(){
		// TableA
		if (rbtn_student.isSelected()) {
			txt_form_name.setText(Main.tableA);
			txt_colA.setText(Main.std_sid);
			txt_colB.setText(Main.std_sname);
			txt_colC.setForeground(new Color(255,0,0));
			txt_colC.setText("\u4e0d\u4f7f\u7528");
			txt_colC.setEditable(false);
		}
		// TableB
		if (rbtn_course.isSelected()) {
			txt_form_name.setText(Main.tableB);
			txt_colA.setText(Main.course_cid);
			txt_colB.setText(Main.course_cname);
			txt_colC.setForeground(new Color(255,0,0));
			txt_colC.setText("\u4e0d\u4f7f\u7528");
			txt_colC.setEditable(false);		
		}
		// TableC
		if (rbtn_score.isSelected()) {
			txt_form_name.setText(Main.tableC);
			txt_colA.setText(Main.score_sid);
			txt_colB.setText(Main.score_cid);
			txt_colC.setForeground(Color.BLACK);
			txt_colC.setText(Main.score_score);
			txt_colC.setEditable(true);		
		}
	}
	
	private void apply() {
		// TableA
		if (rbtn_student.isSelected()) {
			Main.tableA = txt_form_name.getText().trim();
			Main.std_sid = txt_colA.getText().trim();
			Main.std_sname = txt_colB.getText().trim();
		}
		// TableB
		if (rbtn_course.isSelected()) {
			Main.tableB = txt_form_name.getText().trim();
			Main.course_cid = txt_colA.getText().trim();
			Main.course_cname = txt_colB.getText().trim();
		}
		// TableC
		if (rbtn_score.isSelected()) {
			Main.tableC = txt_form_name.getText().trim();
			Main.score_sid = txt_colA.getText().trim();
			Main.score_cid = txt_colB.getText().trim();
			Main.score_score = txt_colC.getText().trim();	
		}
		Infobox.message("\u8a2d\u5b9a\u5df2\u4fdd\u5b58\u3002");
		System.out.println("TableA: " + Main.tableA + "/" + Main.std_sid + "/" + Main.std_sname);
		System.out.println("TableB: " + Main.tableB + "/" + Main.course_cid + "/" + Main.course_cname);
		System.out.println("TableC: " + Main.tableC + "/" + Main.score_sid + "/" + Main.score_cid + "/" + Main.score_score);
	}
	
	/**
	 * Create the frame.
	 */
	public Settings() {
		setTitle("\u8A2D\u5B9A");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// RadioButton: TableA (Student)
		rbtn_student = new JRadioButton("Student");
		rbtn_student.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		rbtn_student.setSelected(true);
		rbtn_student.setFont(new Font("新細明體", Font.BOLD | Font.ITALIC, 16));
		rbtn_student.setBounds(16, 8, 80, 24);
		contentPane.add(rbtn_student);
		
		// RadioButton: TableB (Course)
		rbtn_course = new JRadioButton("Course");
		rbtn_course.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		rbtn_course.setFont(new Font("新細明體", Font.BOLD | Font.ITALIC, 16));
		rbtn_course.setBounds(128, 10, 80, 24);
		contentPane.add(rbtn_course);
		
		// RadioButton: TableC (Record)
		rbtn_score = new JRadioButton("Record");
		rbtn_score.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		rbtn_score.setFont(new Font("新細明體", Font.BOLD | Font.ITALIC, 16));
		rbtn_score.setBounds(240, 10, 80, 24);
		contentPane.add(rbtn_score);		
		group.add(rbtn_student);
		group.add(rbtn_course);
		group.add(rbtn_score);
		
		// Text: Table Name
		txt_form_name = new JTextField();
		txt_form_name.setFont(new Font("標楷體", Font.BOLD, 20));
		txt_form_name.setColumns(10);
		txt_form_name.setBackground(SystemColor.controlHighlight);
		txt_form_name.setForeground(Color.BLACK);
		txt_form_name.setBounds(128, 48, 192, 32);
		contentPane.add(txt_form_name);
		
		// Text: ColumnA
		txt_colA = new JTextField();
		txt_colA.setFont(new Font("標楷體", Font.BOLD, 20));
		txt_colA.setColumns(10);
		txt_colA.setBackground(SystemColor.controlHighlight);
		txt_colA.setForeground(Color.BLACK);
		txt_colA.setBounds(128, 96, 192, 32);
		contentPane.add(txt_colA);
		
		// Text: ColumnB
		txt_colB = new JTextField();
		txt_colB.setFont(new Font("標楷體", Font.BOLD, 20));
		txt_colB.setColumns(10);
		txt_colB.setBackground(SystemColor.controlHighlight);
		txt_colB.setForeground(Color.BLACK);
		txt_colB.setBounds(128, 144, 192, 32);
		contentPane.add(txt_colB);
		
		// Text: ColumnC
		txt_colC = new JTextField();
		txt_colC.setFont(new Font("標楷體", Font.BOLD, 20));
		txt_colC.setColumns(10);
		txt_colC.setBackground(SystemColor.controlHighlight);
		txt_colC.setForeground(Color.BLACK);
		txt_colC.setBounds(128, 192, 192, 32);
		contentPane.add(txt_colC);
		
		// Apply Button
		btn_save = new JButton("\u5957\u7528");
		btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				apply();
			}
		});
		btn_save.setFont(new Font("標楷體", Font.BOLD, 16));
		btn_save.setBounds(240, 248, 80, 24);
		contentPane.add(btn_save);
		
		// Tip
		JLabel lbl_form_name = new JLabel("\u8868\u55ae\u540d\u7a31\uff1a");
		lbl_form_name.setFont(new Font("標楷體", Font.ITALIC, 20));
		lbl_form_name.setBounds(16, 48, 108, 32);
		contentPane.add(lbl_form_name);
		
		// Tip
		JLabel lbl_form_colA = new JLabel("\u7B2C\u4E00\u5217\u540D\uFF1A");
		lbl_form_colA.setFont(new Font("標楷體", Font.ITALIC, 20));
		lbl_form_colA.setBounds(16, 96, 108, 32);
		contentPane.add(lbl_form_colA);
		
		// Tip
		JLabel lbl_form_colB = new JLabel("\u7B2C\u4E8C\u5217\u540D\uFF1A");
		lbl_form_colB.setFont(new Font("標楷體", Font.ITALIC, 20));
		lbl_form_colB.setBounds(16, 144, 108, 32);
		contentPane.add(lbl_form_colB);
		
		// Tip
		JLabel lbl_form_colC = new JLabel("\u7B2C\u4E09\u5217\u540D\uFF1A");
		lbl_form_colC.setFont(new Font("標楷體", Font.ITALIC, 20));
		lbl_form_colC.setBounds(16, 192, 108, 32);
		contentPane.add(lbl_form_colC);
	}
}
