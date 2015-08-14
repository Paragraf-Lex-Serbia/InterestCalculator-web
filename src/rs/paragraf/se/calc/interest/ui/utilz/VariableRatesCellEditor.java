package rs.paragraf.se.calc.interest.ui.utilz;

import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;

import rs.paragraf.se.calc.interest.ui.CustomTextField;
import rs.paragraf.se.calc.interest.ui.MainFrame;
import rs.paragraf.se.calc.interest.ui.MainFrame.Delimiter;

public class VariableRatesCellEditor extends DefaultCellEditor {
	public VariableRatesCellEditor() {
		super(new CustomTextField());
		setClickCountToStart(1);
		VariableRatesCellComponent ratePicker = new VariableRatesCellComponent();
		delegate = (EditorDelegate) ratePicker;
		editorComponent = ratePicker.cTextField;
	}

	class VariableRatesCellComponent extends EditorDelegate {

		public CustomTextField cTextField = new CustomTextField();

		public VariableRatesCellComponent() {
		}

		public void setValue(Object value) {
			if ((value != null) && (value instanceof Double))
				if (MainFrame.getInstance().getDelimiter() == Delimiter.COMMA)
					cTextField.setText(String.valueOf(value).replace('.', ','));
				else
					cTextField.setText(String.valueOf(value).replace(',', '.'));
			else
				cTextField.setText("");
		}

		public Object getCellEditorValue() {
			try {
				return cTextField.getDouble();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Object[] options = { MainFrame.properties
						.getProperty("button.ok") };
				JOptionPane.showOptionDialog(null,
						"Nije uneta vrednost u selektovano polje!",
						MainFrame.properties.getProperty("messageType.error"),
						JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
						null, options, options[0]);
				return null;
			}
		}
	}
}