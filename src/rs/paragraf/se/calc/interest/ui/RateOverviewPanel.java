package rs.paragraf.se.calc.interest.ui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class RateOverviewPanel extends JPanel {

	private JTabbedPane rateReviewTabbedPane;
	private FixedRatesPanel fixedRatesPane;
	private VariableRatesPanel variableRatesPane;

	public RateOverviewPanel() {
		fixedRatesPane = new FixedRatesPanel();
		fixedRatesPane.setEditable(false);
		variableRatesPane = new VariableRatesPanel();

		rateReviewTabbedPane = new JTabbedPane();
		rateReviewTabbedPane.insertTab(MainFrame.properties
				.getProperty("rate.overview.fixedRate.title"), null,
				fixedRatesPane, MainFrame.properties
						.getProperty("rate.overview.fixedRate.toolTip"), 0);
		rateReviewTabbedPane.insertTab(MainFrame.properties
				.getProperty("rate.overview.variableRate.title"), null,
				variableRatesPane, MainFrame.properties
						.getProperty("rate.overview.variableRate.toolTip"), 1);
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createBevelBorder(3)));
		add(rateReviewTabbedPane, BorderLayout.CENTER);
	}

	public void setEditable(boolean isEditable) {
		variableRatesPane.setEditable(isEditable);
		// if (isEditable) {
		// rateReviewTabbedPane.removeTabAt(0);
		// } else {
		// rateReviewTabbedPane.insertTab(
		// MainFrame.properties.getProperty("rate.overview.fixedRate.title"),
		// null,
		// fixedRatesPane, MainFrame.properties.getProperty(
		// "rate.overview.fixedRate.toolTip"), 0);
		// }
		rateReviewTabbedPane.setEnabledAt(0, !isEditable);
	}

	public void setSelectedTab(int tabIndex) {
		rateReviewTabbedPane.setSelectedIndex(tabIndex);
	}

	public VariableRatesPanel getVariableRatesPane() {
		return variableRatesPane;
	}

	public void setVariableRatesPane(VariableRatesPanel variableRatesPane) {
		this.variableRatesPane = variableRatesPane;
	}

}