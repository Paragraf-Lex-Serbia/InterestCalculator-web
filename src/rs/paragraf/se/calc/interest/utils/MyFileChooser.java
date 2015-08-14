package rs.paragraf.se.calc.interest.utils;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

public class MyFileChooser extends JFileChooser {
	public MyFileChooser(String currentDirectoryPath) {
		super(currentDirectoryPath);
	}

	protected JDialog createDialog(Component parent) throws HeadlessException {
		String title = getUI().getDialogTitle(this);
		getAccessibleContext().setAccessibleDescription(title);

		Window window = MyOptionPane.getWindowForComponent(parent);
		JDialog dialog;

		if ((window instanceof Frame)) {
			dialog = new JDialog((Frame) window, title, true);
		} else {
			dialog = new JDialog((Dialog) window, title, true);
		}
		dialog.setComponentOrientation(getComponentOrientation());
		Container contentPane = dialog.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(this, "Center");
		if (JDialog.isDefaultLookAndFeelDecorated()) {
			boolean supportsWindowDecorations = UIManager.getLookAndFeel()
					.getSupportsWindowDecorations();
			if (supportsWindowDecorations) {
				dialog.getRootPane().setWindowDecorationStyle(1);
			}
		}
		dialog.pack();
		dialog.setLocationRelativeTo(parent);
		return dialog;
	}
}