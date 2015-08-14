package rs.paragraf.se.calc.interest.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Set;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import rs.paragraf.se.calc.interest.data.RateBean;
import rs.paragraf.se.calc.interest.utils.RateManager;
import rs.paragraf.se.calc.interest.utils.ResourceManager;

public class FixedRatesPanel extends JPanel {

	private JScrollPane scroll;
	private JTable table = new JTable();
	private DefaultTableModel model = new FixedRatesOverviewTableModel(0, 9);
	private boolean editable = false;

//	static final long milPerDay = 1000*60*60*24;

	private class FixedRatesOverviewTableModel extends DefaultTableModel {

		public FixedRatesOverviewTableModel(int rowCount, int columnCount) {
			super(rowCount, columnCount);
		}

		public boolean isCellEditable(int row, int column) {
			return isEditable();
		}

		public Class getColumnClass(int col) {
			return String.class;
		}
	}

	public FixedRatesPanel() {
		super();
		init();
		loadFixedRatesInTable();
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

		model.setColumnCount(9);
		model.setColumnIdentifiers(new String[] {
				MainFrame.properties.getProperty(
						"rate.overview.fixed.table.header.dateFrom1") + "\n" + 
				MainFrame.properties.getProperty(
						"rate.overview.fixed.table.header.dateFrom2"),
				MainFrame.properties.getProperty(
						"rate.overview.fixed.table.header.dateTo1") + "\n" + 
				MainFrame.properties.getProperty(
						"rate.overview.fixed.table.header.dateTo2"),
				MainFrame.properties.getProperty(
						"rate.overview.fixed.table.header.days1") + "\n" + 
				MainFrame.properties.getProperty(
						"rate.overview.fixed.table.header.days2"),
				MainFrame.properties.getProperty(
						"rate.overview.fixed.table.header.lawRate1") + "\n" + 
				MainFrame.properties.getProperty(
						"rate.overview.fixed.table.header.lawRate2"),
				MainFrame.properties.getProperty(
						"rate.overview.fixed.table.header.lawRateEuro1") + "\n" + 
				MainFrame.properties.getProperty(
						"rate.overview.fixed.table.header.lawRateEuro2"),
				MainFrame.properties.getProperty(
						"rate.overview.fixed.table.header.priceGrowth1") + "\n" + 
				MainFrame.properties.getProperty(
						"rate.overview.fixed.table.header.priceGrowth2"),
				MainFrame.properties.getProperty(
						"rate.overview.fixed.table.header.taxYear1") + "\n" + 
				MainFrame.properties.getProperty(
						"rate.overview.fixed.table.header.taxYear2"),
				MainFrame.properties.getProperty(
						"rate.overview.fixed.table.header.ecb1") + "\n" + 
				MainFrame.properties.getProperty(
						"rate.overview.fixed.table.header.ecb2"),
				MainFrame.properties.getProperty(
						"rate.overview.fixed.table.header.discountYear1") + "\n" + 
				MainFrame.properties.getProperty(
						"rate.overview.fixed.table.header.discountYear2"),
				MainFrame.properties.getProperty(
						"rate.overview.fixed.table.header.referentYear1") + "\n" + 
				MainFrame.properties.getProperty(
						"rate.overview.fixed.table.header.referentYear2")
		});

		table.setRowHeight(20);
		// table.getModel()
		table.setShowGrid(true);
		// table.getTableHeader().setReorderingAllowed(true);
		table.setFillsViewportHeight(false);
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setModel(model);
		MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer();
		Enumeration tableColumns = table.getColumnModel().getColumns();
		while (tableColumns.hasMoreElements()) {
			((TableColumn) tableColumns.nextElement()).setHeaderRenderer(renderer);
		}

		scroll = new JScrollPane(ResourceManager.decorateComponent(table,"rate.overview.table"));
		// scroll.setPreferredSize(new Dimension(780, 350));

		add(scroll, BorderLayout.CENTER);
	}

	public void loadFixedRatesInTable() {
		RateManager rateManager = RateManager.getInstance();
//		rateManager.load();
		Set<RateBean> rates = rateManager.getFixedRates();

		Vector<String> rateVector;
		for (RateBean rate : rates) {
			rateVector = new Vector<String>();
			rateVector.add(MainFrame.sdfWithPoints.format(rate.getFrom()));
			rateVector.add(MainFrame.sdfWithPoints.format(rate.getTo()));
			rateVector.add("" + (rate.getTo().getTime() - rate.getFrom().getTime() + MainFrame.milPerDay) / MainFrame.milPerDay);
			
//			if (rate.getLawRatePrint()==null) rateVector.add("");
//			else rateVector.add(MainFrame.rateFormater.format(rate.getLawRatePrint()));
			
			if (rate.getLawRate()==null) rateVector.add("");
			else rateVector.add(MainFrame.rateFormater.format(rate.getLawRate()));
			
			if (rate.getLawRateEuroPrint()==null) rateVector.add("");
			else rateVector.add(MainFrame.rateFormater.format(rate.getLawRateEuroPrint()));
			if (rate.getPriceGrowthRate()==null) rateVector.add("");
			else rateVector.add(MainFrame.rateFormater.format(rate.getPriceGrowthRate()));
			if (rate.getTaxYRate()==null) rateVector.add("");
			else rateVector.add(MainFrame.rateFormater.format(rate.getTaxYRate()));
			if (rate.getEcbRate()==null) rateVector.add("");
			else rateVector.add(MainFrame.rateFormater.format(rate.getEcbRate()));
			if (rate.getExcontYRate()==null) rateVector.add("");
			else rateVector.add(MainFrame.rateFormater.format(rate.getExcontYRate()));
			
			if (rate.getReferentRate()==null) rateVector.add("");
			else rateVector.add(MainFrame.rateFormater.format(rate.getReferentRate()));
			
			model.addRow(rateVector);
		}
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isEditable() {
		return editable;
	}

	public class MultiLineHeaderRenderer extends JList implements TableCellRenderer {

		public MultiLineHeaderRenderer() {
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
			BufferedReader br = new BufferedReader(new StringReader(str));
			String line;
			Vector v = new Vector();
			try {
				while ((line = br.readLine()) != null) {
					v.addElement(line);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			setListData(v);
			return this;
		}
	}
}
