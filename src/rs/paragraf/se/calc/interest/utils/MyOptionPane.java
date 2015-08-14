package rs.paragraf.se.calc.interest.utils;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Window;
import javax.swing.JOptionPane;

class MyOptionPane extends JOptionPane {
	static Window getWindowForComponent(Component parentComponent)
			throws HeadlessException {
		if (parentComponent == null) {
			return getRootFrame();
		}
		if (((parentComponent instanceof Frame))
				|| ((parentComponent instanceof Dialog))) {
			return (Window) parentComponent;
		}
		return getWindowForComponent(parentComponent.getParent());
	}
}