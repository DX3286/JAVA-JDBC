package my.api.access.work.function;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Infobox {

	public static void error(String message)
    {
		JLabel lbl = new JLabel(message);
		lbl.setFont(new Font("標楷體", Font.PLAIN, 20));
        JOptionPane.showMessageDialog(null, lbl, "\u932f\u8aa4", JOptionPane.ERROR_MESSAGE);
    }
	
	public static void message(String message)
    {
		JLabel lbl = new JLabel(message);
		lbl.setFont(new Font("標楷體", Font.PLAIN, 20));
        JOptionPane.showMessageDialog(null, lbl, "\u8a0a\u606f", JOptionPane.INFORMATION_MESSAGE);
    }
}
