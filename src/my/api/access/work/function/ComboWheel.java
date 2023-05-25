package my.api.access.work.function;

import java.awt.event.MouseWheelEvent;
import java.util.List;

import javax.swing.JComboBox;

public class ComboWheel {

	public void WheelAction(MouseWheelEvent e, List<JComboBox<String>> combo) {
		// -1 = UP 1 = DOWN
		if (e.getWheelRotation() == 1) {
			for (JComboBox<String> c:combo) {
				if (c.isFocusOwner()) {
					if (c.getSelectedIndex() == c.getItemCount() -1) {
						return;
					}
					c.setSelectedIndex(Math.min(c.getSelectedIndex() + 1, c.getItemCount() - 1));
				}
			}
		}else if (e.getPreciseWheelRotation() == -1) {
			for (JComboBox<String> c:combo) {
				if (c.isFocusOwner()) {
					if (c.getSelectedIndex() <= 0) {
						return;
					}
					c.setSelectedIndex(Math.max(c.getSelectedIndex() - 1, 0));
				}
			}
		}
	}
}
