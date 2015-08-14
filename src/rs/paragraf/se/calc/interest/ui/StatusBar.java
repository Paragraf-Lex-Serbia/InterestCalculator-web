package rs.paragraf.se.calc.interest.ui;

import java.awt.Dimension;

import javax.swing.JLabel;

public class StatusBar extends JLabel {
	/** Creates a new instance of StatusBar */
	public StatusBar() {
		super();
		super.setPreferredSize(new Dimension(100, 16));
		setMessage(MainFrame.properties.getProperty("new.document.status.bar"));
	}

	public void setMessage(String message) {
		setText(" " + message);
	}

	public String getMessage() {
		return getText();
	}
}