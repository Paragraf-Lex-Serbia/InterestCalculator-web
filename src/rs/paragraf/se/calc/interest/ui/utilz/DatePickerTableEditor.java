package rs.paragraf.se.calc.interest.ui.utilz;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;

import rs.paragraf.se.calc.interest.ui.CalendarDialog;
import rs.paragraf.se.calc.interest.ui.MainFrame;

public class DatePickerTableEditor extends DefaultCellEditor {
	public DatePickerTableEditor() {
		super(new JTextField()); // not really relevant - sets a text field as
		DatePickerComponent picker = new DatePickerComponent();
		delegate = (EditorDelegate) picker;
		editorComponent = picker.datePicker.getTextdate();
	}

	class DatePickerComponent extends EditorDelegate {

		public CalendarDialog datePicker = new CalendarDialog(MainFrame.sdf);

		public DatePickerComponent() {
		}

		public void setValue(Object value) {
			datePicker.setDate(value);
			datePicker.setVisible(true);
		}

		public Object getCellEditorValue() {
			return datePicker.getTextdate().getText();
		}

	}
}