package rs.paragraf.se.calc.interest.ui;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import rs.paragraf.se.calc.interest.ui.MainFrame.Delimiter;
import rs.paragraf.se.calc.interest.utils.ResourceManager;

public class CustomTextField extends JTextField {
	public CustomTextField() {
		super();

		this.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();

				if (!Character.isDigit(c) && (c != ',') && (c != '.')
						&& (c != '-')) {
					// If not then beep and consume the event.
					if ((c != KeyEvent.VK_BACK_SPACE)
							&& (c != KeyEvent.VK_DELETE))
						Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
				JTextField textField = (JTextField) e.getSource();
				String text = textField.getText();
				if (MainFrame.getInstance().getDelimiter() == Delimiter.COMMA)
					textField.setText(text.replace('.', ','));
				else if (MainFrame.getInstance().getDelimiter() == Delimiter.POINT) {
					textField.setText(text.replace(',', '.'));
				}
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
			}
		});

		init();
	}

	public void init() {
		ResourceManager.decorateComponent(this, "main.panel.input.fields");
	}

	public Double getDouble() throws NumberFormatException {
		String text = this.getText();
		text = text.replace(',', '.');

		return Double.parseDouble(text);
	}

	public void setDouble(Double d) {
		String text = String.valueOf(d);
		if (MainFrame.getInstance().getDelimiter() == Delimiter.COMMA)
			text = text.replace('.', ',');
		setText(text);
	}

}
