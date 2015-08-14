package rs.paragraf.se.calc.interest.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import net.miginfocom.swing.MigLayout;
import rs.paragraf.se.calc.interest.data.RateBean;
import rs.paragraf.se.calc.interest.ui.MainFrame.Delimiter;
import rs.paragraf.se.calc.interest.ui.utilz.DatePickerTableEditor;
import rs.paragraf.se.calc.interest.ui.utilz.NumberCellRenderer;
import rs.paragraf.se.calc.interest.ui.utilz.VariableRatesCellEditor;
import rs.paragraf.se.calc.interest.utils.RateManager;
import rs.paragraf.se.calc.interest.utils.ResourceManager;

public class VariableRatesPanel extends JPanel {

//	private LabeledTextField fixedYRate = new LabeledTextField();
//	private LabeledTextField fixedMRate = new LabeledTextField();
	private JLabel fixedYRateLabel = new JLabel();
	private JFormattedTextField fixedYRate = new JFormattedTextField(MainFrame.rateFormater);
	private JLabel fixedMRateLabel = new JLabel();
	private JFormattedTextField fixedMRate = new JFormattedTextField(MainFrame.rateFormater);
	private JLabel fixedDRateLabel = new JLabel();
	private JFormattedTextField fixedDRate = new JFormattedTextField(MainFrame.rateFormater);

	private JScrollPane scroll;
	private JTable table = new JTable();
	private JPanel buttons = new JPanel();
	private JButton add = new JButton();
	private JButton delete = new JButton();
	private JButton saveRates = new JButton();

	private NumberCellRenderer numberCellRenderer = 
		new NumberCellRenderer(MainFrame.rateFormater);

	public enum VariableRateTableStatus {
		VALID, 
		INVALID_DATA,  
		FROM_DATE_AFTER_TO_DATE
	};

	JPanel northPanel;
	JPanel buttonBag;

	private DefaultTableModel model = new VariableRatesOverviewTableModel(0, 3);

	private boolean editable = false;

	private class VariableRatesOverviewTableModel extends DefaultTableModel {

		public VariableRatesOverviewTableModel(int rowCount, int columnCount) {
			super(rowCount, columnCount);
		}

		public boolean isCellEditable(int row, int column) {
			return isEditable();
		}

		public Class getColumnClass(int col) {
			if (col==2) return Double.class; 
			return String.class;
		}
	}

	public void clearTable() {
		model.setRowCount(1);
	}

	public VariableRatesPanel() {
		super();

		init();
		//loadVariableRatesInTable();
	}

	public void init() {
		setLayout(new BorderLayout());

		table.setIntercellSpacing(new Dimension(1, 1));
		// table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setCellSelectionEnabled(false);
		table.setRowSelectionAllowed(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.setBackground(
				ResourceManager.getBackgroundColor(
						"rate.overview.table.background"));
		table.setSelectionBackground(
				ResourceManager.getBackgroundColor(
						"rate.overview.table.selected.background"));
		table.setSelectionForeground(
				ResourceManager.getFontColor(
						"rate.overview.table"));
		table.setGridColor(
				ResourceManager.getBackgroundColor(
						"rate.overview.table.grid.color"));

		model.setColumnCount(3);
		model.setColumnIdentifiers(new String[] {
				MainFrame.properties.getProperty(
						"rate.overview.variable.table.header.dateFrom"),
				MainFrame.properties.getProperty(
						"rate.overview.variable.table.header.dateTo"),
				MainFrame.properties.getProperty(
						"rate.overview.variable.table.header.contractualRate")
		});

		table.setRowHeight(25);
		// table.getModel()
		table.setShowGrid(true);
		// table.getTableHeader().setReorderingAllowed(true);
		table.setFillsViewportHeight(false);
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setModel(model);

		// Create our cell editor
	    DatePickerTableEditor datePickerCellEditor = new DatePickerTableEditor();
	    // Set the number of mouse clicks needed to activate it.
	    datePickerCellEditor.setClickCountToStart(1);
	    table.getColumnModel().getColumn(0).setCellEditor(datePickerCellEditor);
	    table.getColumnModel().getColumn(1).setCellEditor(datePickerCellEditor);

	    VariableRatesCellEditor variableRatesCellEditor = new VariableRatesCellEditor();
	    table.getColumnModel().getColumn(2).setCellEditor(variableRatesCellEditor);

		//CenterRenderer renderer = new CenterRenderer();
		AlignTableHeaderRenderer headerRenderer = new AlignTableHeaderRenderer();
		numberCellRenderer.setCompareFirstTwoColumnsAsDates(true);
		Enumeration tableColumns = table.getColumnModel().getColumns();
		while (tableColumns.hasMoreElements()) {
			TableColumn tableColumn = (TableColumn) tableColumns.nextElement();
			tableColumn.setCellRenderer(numberCellRenderer);
			tableColumn.setHeaderRenderer(headerRenderer);
		}

		scroll = new JScrollPane(
				ResourceManager.decorateComponent(table, "rate.overview.table"));
		table.setPreferredScrollableViewportSize(table.getPreferredSize());

		// scroll.setPreferredSize(new Dimension(780, 350));

		fixedYRateLabel.setText(MainFrame.properties.getProperty("fixed.year.rate"));
	    fixedYRate.setEditable(false);
	    fixedYRate.setHorizontalAlignment(JTextField.RIGHT);
	    fixedYRate.addKeyListener(new KeyAdapter() {
	    	public void keyReleased(KeyEvent e) {
				JFormattedTextField textField = (JFormattedTextField) e.getSource();
				String text = textField.getText();
				if (MainFrame.getInstance().getDelimiter() == Delimiter.COMMA)
					textField.setText(text.replace('.', ','));
				else if (MainFrame.getInstance().getDelimiter() == Delimiter.POINT) {
					textField.setText(text.replace(',', '.'));
				}
			}

			public void keyTyped(KeyEvent e) {
				// TODO: Do something for the keyTyped event
			}

			public void keyPressed(KeyEvent e) {
				// TODO: Do something for the keyPressed event
			}
		});

		fixedMRateLabel.setText(MainFrame.properties.getProperty("fixed.monthly.rate"));
	    fixedMRate.setEditable(false);
	    fixedMRate.setHorizontalAlignment(JTextField.RIGHT);
	    fixedMRate.addKeyListener(new KeyAdapter() {
	    	public void keyReleased(KeyEvent e) {
				JFormattedTextField textField = (JFormattedTextField) e.getSource();
				String text = textField.getText();
				if (MainFrame.getInstance().getDelimiter() == Delimiter.COMMA)
					textField.setText(text.replace('.', ','));
				else if (MainFrame.getInstance().getDelimiter() == Delimiter.POINT) {
					textField.setText(text.replace(',', '.'));
				}
			}

			public void keyTyped(KeyEvent e) {
				// TODO: Do something for the keyTyped event
			}

			public void keyPressed(KeyEvent e) {
				// TODO: Do something for the keyPressed event
			}
		});

		fixedDRateLabel.setText(MainFrame.properties.getProperty("fixed.daily.rate"));
	    fixedDRate.setEditable(false);
	    fixedDRate.setHorizontalAlignment(JTextField.RIGHT);
	    fixedDRate.addKeyListener(new KeyAdapter() {
	    	public void keyReleased(KeyEvent e) {
				JFormattedTextField textField = (JFormattedTextField) e.getSource();
				String text = textField.getText();
				if (MainFrame.getInstance().getDelimiter() == Delimiter.COMMA)
					textField.setText(text.replace('.', ','));
				else if (MainFrame.getInstance().getDelimiter() == Delimiter.POINT) {
					textField.setText(text.replace(',', '.'));
				}
			}

			public void keyTyped(KeyEvent e) {
				// TODO: Do something for the keyTyped event
			}

			public void keyPressed(KeyEvent e) {
				// TODO: Do something for the keyPressed event
			}
		});

	    
	    JPanel centerPanel = new JPanel(new BorderLayout());

	    JPanel fixedPane = new JPanel(new MigLayout("", "[]4mm![]", ""));
	    fixedPane.add(ResourceManager.decorateComponent(fixedYRateLabel, "fixed.rate.label"));
	    fixedPane.add(ResourceManager.decorateComponent(fixedYRate, "fixed.rate"), "w 30mm!, h 6mm!, sgx group, wrap");
	    fixedPane.add(ResourceManager.decorateComponent(fixedMRateLabel, "fixed.rate.label"));
	    fixedPane.add(ResourceManager.decorateComponent(fixedMRate, "fixed.rate"), "w 30mm!, h 6mm!, sgx group, wrap");
	    fixedPane.add(ResourceManager.decorateComponent(fixedDRateLabel, "fixed.rate.label"));
	    fixedPane.add(ResourceManager.decorateComponent(fixedDRate, "fixed.rate"), "w 30mm!, h 6mm!, sgx group, wrap");

	    centerPanel.add(scroll);
	    centerPanel.add(fixedPane, BorderLayout.SOUTH);

		JPanel savePanel = new JPanel();
		savePanel.add(ResourceManager.decorateComponent(saveRates, "main.button"));

		buttons.setLayout(new GridLayout(3, 1));
		add.setText(MainFrame.properties.getProperty("table.add.button"));
	    add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.addRow(new Vector<String>());
			}
		});
	    buttons.add(ResourceManager.decorateComponent(add, "table.buttons"));
	    delete.setText(MainFrame.properties.getProperty("table.delete.button"));
	    delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() < 0) {
					// do nothing
				} else {
					if (table != null) {
						if (table.getCellEditor() != null)
							table.getCellEditor().stopCellEditing();
						else
							System.out.println("cell editor null");
					}
					model.removeRow(table.getSelectedRow());
				}
			}
		});
	    buttons.add(ResourceManager.decorateComponent(delete, "table.buttons"));
	    saveRates.setText(MainFrame.properties.getProperty("variable.rate.change.save.button"));
	    saveRates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAction();
			}
		});

		//JPanel northPanel = new JPanel(new BorderLayout());
	    northPanel = new JPanel(new BorderLayout());
		//JPanel buttonBag = new JPanel(new BorderLayout());
	    buttonBag = new JPanel(new BorderLayout());
		buttonBag.add(buttons, BorderLayout.NORTH);
		northPanel.add(buttonBag, BorderLayout.EAST);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new MigLayout("insets 0 0 0 0", "[grow, fill]2mm![center]", "[grow]2mm![center]"));
		mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(2)));
		mainPanel.add(centerPanel, "grow");
		mainPanel.add(northPanel, "top, wrap");

		add(mainPanel);
		add(savePanel, BorderLayout.SOUTH);

		//initVariableData();
	}

	public void saveAction() {
		// stop cell editing in variable rates table before saving
		if (table.getCellEditor() != null)
			table.getCellEditor().stopCellEditing();

		VariableRateTableStatus status = validateVariableRatesTable();
		if (status == VariableRateTableStatus.INVALID_DATA) {
			numberCellRenderer.setPaintBackground(true);
			table.repaint();
			MainFrame.showMessage(
					MainFrame.properties.getProperty("variable.rates.table.invalid"),
					JOptionPane.ERROR_MESSAGE);
		} else if (status == VariableRateTableStatus.FROM_DATE_AFTER_TO_DATE) {
			numberCellRenderer.setPaintBackground(true);
			table.repaint();
			MainFrame.showMessage(
					MainFrame.properties.getProperty("variable.rates.table.invalid.from.date.after.to"),
					JOptionPane.ERROR_MESSAGE);
		} else {
			numberCellRenderer.setPaintBackground(false);
			table.repaint();

			SortedSet<RateBean> contractRates =  new TreeSet<RateBean>();

			for (int index = 0; index < table.getRowCount(); ++index) {
				RateBean rate = new RateBean();

				try {
					rate.setFrom(MainFrame.sdf.parse((String) table.getValueAt(index, 0)));
					rate.setTo(MainFrame.sdf.parse((String) table.getValueAt(index, 1)));

					rate.setFreeContractRate((Double) table.getValueAt(index, 2));
					contractRates.add(rate);
				} catch (Exception e1) {
					System.out.println("Ignoring table row :: " + e1.getMessage());
				}
			}
			try {
				if (! fixedYRate.getText().isEmpty()) {
					RateManager.getInstance().setFixedAnualContractInterest(
							MainFrame.rateFormater.parse(fixedYRate.getText()).doubleValue());
				}
			} catch (Exception e1) {
				System.out.println("Ignoring fixed year :: " + e1.getMessage());
			}
			try {
				if (! fixedMRate.getText().isEmpty()) {
					RateManager.getInstance().setFixedMonthleyContractInterest(
							MainFrame.rateFormater.parse(fixedMRate.getText()).doubleValue());
				}
			} catch (Exception e1) {
				System.out.println("Ignoring fixed monthley :: " + e1.getMessage());
			}
			try {
				if (! fixedDRate.getText().isEmpty()) {
					RateManager.getInstance().setFixedDailyContractInterest(
							MainFrame.rateFormater.parse(fixedDRate.getText()).doubleValue());
				}
			} catch (Exception e1) {
				System.out.println("Ignoring fixed daily :: " + e1.getMessage());
			}

			RateManager.getInstance().setContractRates(contractRates);
			RateManager.getInstance().save();

			// maybe another approach
			initVariableData();
		}
	}

	public void setFormaterFactory() {
		DefaultFormatterFactory factory = new DefaultFormatterFactory(new NumberFormatter(MainFrame.rateFormater));
		getFixedMRate().setFormatterFactory(factory);
		getFixedYRate().setFormatterFactory(factory);
		getFixedDRate().setFormatterFactory(factory);
		numberCellRenderer.setFormater(MainFrame.rateFormater);
	}

	public JFormattedTextField getFixedYRate() {
		return fixedYRate;
	}

	public void setFixedYRate(JFormattedTextField fixedYRate) {
		this.fixedYRate = fixedYRate;
	}

	public JFormattedTextField getFixedMRate() {
		return fixedMRate;
	}

	public void setFixedMRate(JFormattedTextField fixedMRate) {
		this.fixedMRate = fixedMRate;
	}
	
	public JFormattedTextField getFixedDRate() {
		return fixedDRate;
	}

	public void setFixedDRate(JFormattedTextField fixedDRate) {
		this.fixedDRate = fixedDRate;
	}

	public void initVariableData()
	{
		fixedYRate.setText(MainFrame.rateFormater.format(RateManager.getInstance().getFixedAnualContractInterest()));
		fixedMRate.setText(MainFrame.rateFormater.format(RateManager.getInstance().getFixedMonthleyContractInterest()));
		fixedDRate.setText(MainFrame.rateFormater.format(RateManager.getInstance().getFixedDailyContractInterest()));

		Iterator<RateBean> it = RateManager.getInstance().getContractRates().iterator();
		model.setRowCount(0);
		while (it.hasNext())
		{
			RateBean rate = it.next();
			Vector values = new Vector();
			values.add(MainFrame.sdf.format(rate.getFrom()));
			values.add(MainFrame.sdf.format(rate.getTo()));
			values.add(rate.getFreeContractRate());
			model.addRow(values);
		}
		if (model.getRowCount()==0) model.setRowCount(0);
	}

	public VariableRateTableStatus validateVariableRatesTable() {
		VariableRateTableStatus status = VariableRateTableStatus.VALID;

		for (int index = 0; index < table.getRowCount(); ++index) {
			status = validateRow(index);
			if (status == VariableRateTableStatus.INVALID_DATA) {
				//System.out.println("invalid data");
				break;
			} else if (status == VariableRateTableStatus.FROM_DATE_AFTER_TO_DATE) {
				//System.out.println("from date after to date");
				break;
			}
		}
		return status;
	}

	public VariableRateTableStatus validateRow(int index) {
		String fromDate = String.valueOf(table.getModel().getValueAt(index, 0));
		String toDate = String.valueOf(table.getModel().getValueAt(index, 1));

		if ((fromDate.trim().equals("null")) || (toDate.trim().equals("null")) || (table.getModel().getValueAt(index, 2) == null) ||
			(fromDate.trim().isEmpty()) || (toDate.trim().isEmpty())) {
			return VariableRateTableStatus.INVALID_DATA;
		}

		try {
			if (MainFrame.sdf.parse(
					table.getModel().getValueAt(index, 0).toString())
						.after(MainFrame.sdf.parse(
								table.getModel().getValueAt(index, 1).toString()))) {
				//table.getModel().setValueAt("", index, 0);
				//table.getModel().setValueAt("", index, 1);

				return VariableRateTableStatus.FROM_DATE_AFTER_TO_DATE;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return VariableRateTableStatus.VALID;
	}

//	public void loadVariableRatesInTable() {
//
//		Random rand = new Random();
//
//		fixedYRate.setText("" + rand.nextDouble() + " %");
//		fixedMRate.setText("" + rand.nextDouble() + " %");
//
//		Vector<String> rateVector;
//		for (int i = 0; i < 5; ++i) {
//			rateVector = new Vector<String>();
//			rateVector.add(MainFrame.sdfWithPoints.format(
//					Calendar.getInstance().getTime()));
//			rateVector.add(MainFrame.sdfWithPoints.format(
//					Calendar.getInstance().getTime()));
//			rateVector.add("" + rand.nextDouble());
//			model.addRow(rateVector);
//		}
//	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		fixedMRate.setEditable(editable);
		fixedYRate.setEditable(editable);
		fixedDRate.setEditable(editable);

//		add.setEnabled(editable);
//		delete.setEnabled(editable);
//		saveRates.setEnabled(editable);

		add.setVisible(editable);
		delete.setVisible(editable);
		saveRates.setVisible(editable);
		northPanel.setVisible(editable);
		buttonBag.setVisible(editable);

//		if (! editable) {
//			this.remove(add);
//			remove(delete);
//			remove(saveRates);
//		}
	}

	public boolean isEditable() {
		return editable;
	}

	class CenterRenderer extends DefaultTableCellRenderer {

		public CenterRenderer() {
			setHorizontalAlignment(CENTER);
		}

        public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        	super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return this;
        }
    }

	public class AlignTableHeaderRenderer extends JList implements TableCellRenderer {

		public AlignTableHeaderRenderer() {
			setOpaque(false);
			setForeground(
					ResourceManager.getFontColor("rate.overview.table.header"));
			setBackground(
					ResourceManager.getBackgroundColor(
							"rate.overview.table.header.background"));
			setBorder(new LineBorder(
					ResourceManager.getBackgroundColor("rate.overview.table.header.color.border"), 1));

			ListCellRenderer renderer = getCellRenderer();
			((JLabel) renderer).setHorizontalAlignment(JLabel.CENTER);
			setCellRenderer(renderer);
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			setFont(table.getFont());
			String str = (value == null) ? "" : value.toString();
//			BufferedReader br = new BufferedReader(new StringReader(str));
//			String line;
			Vector v = new Vector();
			v.addElement(str);
//			try {
//				while ((line = br.readLine()) != null) {
//					v.addElement(line);
//				}
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
			setListData(v);
			return this;
		}
	}

	public JTable getTable() {
		return table;
	}

	public void synchronizeVariableRateTable(JTable table) {
		table.removeAll();
		this.table = table;
	}


}
