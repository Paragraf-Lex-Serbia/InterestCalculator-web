package rs.paragraf.se.calc.interest.ui.utilz;

import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

import rs.paragraf.se.calc.interest.ui.MainFrame;

public class NumberCellRenderer extends DefaultTableCellRenderer {

	public boolean paintBackground = false;
	private NumberFormat formater;

	private boolean compareFirstTwoColumnsAsDates = false;

	/**
	 *
	 */
	public NumberCellRenderer(NumberFormat formater) {
		super();
		setOpaque(true);
		setBorder(new LineBorder(Color.RED, 5, false));
		this.formater = formater;
	}

	public void setValue(Object value) {
		if (value == null) {
			setText("");
			return;
		}
		if (value instanceof Double)
			setText(formater.format((Double) value));
		else
			setText(String.valueOf(value));
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {

		LineBorder lineBorder = null;

		if (paintBackground) {
			if ((String.valueOf(value).trim().equals(""))
					|| (String.valueOf(value).trim().equals("null"))) {
				lineBorder = (LineBorder) BorderFactory.createLineBorder(
						Color.RED, 2);
			}
			if (compareFirstTwoColumnsAsDates) {
				if (col == 0) {
					if (!(String.valueOf(value).trim().equals("null"))
							|| (String.valueOf(
									table.getModel().getValueAt(row, 1)).trim()
									.equals("null"))
							|| (String.valueOf(value).trim().equals(""))
							|| (String.valueOf(
									table.getModel().getValueAt(row, 1)).trim()
									.equals(""))) {
						try {
							if (MainFrame.sdf.parse(
									String.valueOf(value).trim()).after(
									MainFrame.sdf.parse(String.valueOf(table
											.getModel().getValueAt(row, 1))))) {
								lineBorder = (LineBorder) BorderFactory
										.createLineBorder(Color.RED, 2);
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				if (col == 1) {
					if (!(String.valueOf(value).trim().equals("null"))
							|| (String.valueOf(
									table.getModel().getValueAt(row, 0)).trim()
									.equals("null"))
							|| (String.valueOf(value).trim().equals(""))
							|| (String.valueOf(
									table.getModel().getValueAt(row, 0)).trim()
									.equals(""))) {
						try {
							if (MainFrame.sdf.parse(
									String.valueOf(value).trim()).before(
									MainFrame.sdf.parse(String.valueOf(table
											.getModel().getValueAt(row, 0))))) {
								lineBorder = (LineBorder) BorderFactory
										.createLineBorder(Color.RED, 2);
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} else {

			}
		} else {
			// setBackground(ResourceManager.getBackgroundColor("account.change.table.valid.color"));
		}

		JComponent comp = (JComponent) super.getTableCellRendererComponent(
				table, value, isSelected, hasFocus, row, col);
		comp.setBorder(lineBorder);

		return comp;
		// return super.getTableCellRendererComponent(table, value, isSelected,
		// hasFocus, row, col);
	}

	public boolean isPaintBackground() {
		return paintBackground;
	}

	public void setPaintBackground(boolean paintBackground) {
		this.paintBackground = paintBackground;
	}

	public boolean isCompareFirstTwoColumnsAsDates() {
		return compareFirstTwoColumnsAsDates;
	}

	public void setCompareFirstTwoColumnsAsDates(
			boolean compareFirstTwoColumnsAsDates) {
		this.compareFirstTwoColumnsAsDates = compareFirstTwoColumnsAsDates;
	}

	public NumberFormat getFormater() {
		return formater;
	}

	public void setFormater(NumberFormat formater) {
		this.formater = formater;
	}

}
