package rs.paragraf.se.calc.interest.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import rs.paragraf.se.calc.interest.utils.ResourceManager;

public class CalendarDialog extends JDialog {

	private String[] calendarHeader = { "Ne", "Po", "Ut", "Sr", "\u010Ce",
			"Pe", "Su" };
	private String[] monthNavigation = { "<<", ">>" };
	private String[] yearNavigation = { "<<", ">>" };

	private SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy (MMMM)");
	private final SimpleDateFormat sdfM = new SimpleDateFormat("MMMM");
	private final SimpleDateFormat sdfY = new SimpleDateFormat("yyyy");
	private SimpleDateFormat exitsdf = new SimpleDateFormat("dd.MM.yyyy.");
	private JButton[] btn = new JButton[49];
	private int month = java.util.Calendar.getInstance().get(
			java.util.Calendar.MONTH);
	private int year = java.util.Calendar.getInstance().get(
			java.util.Calendar.YEAR);
	private JLabel lbl = new JLabel("", JLabel.CENTER);
	private JLabel lblY = new JLabel("", JLabel.CENTER);
	private JLabel textdate = new JLabel();

	public CalendarDialog(SimpleDateFormat sdf) {
		super();
		super.setModal(true);
		this.exitsdf = sdf;
		init();
		setSize(new Dimension(270, 230));
		setResizable(false);
		setLocationRelativeTo(null);

		setDates();
	}

	public CalendarDialog()

	{
		super();
		super.setModal(true);

		init();
		setSize(new Dimension(270, 230));
		setResizable(false);
		setLocationRelativeTo(null);

		setDates();
	}

	public void init()

	{
		GridLayout layout = new GridLayout(7, 7);
		// System.out.println(UIManager.getLookAndFeel().getName());
		if (UIManager.getLookAndFeel().getName().equalsIgnoreCase("nimbus")) {
			layout.setHgap(0);
			layout.setVgap(0);
		} else {
			layout.setHgap(2);
			layout.setVgap(2);
		}
		JPanel midPanel = new JPanel(layout);

		for (int x = 0; x < btn.length; ++x) {
			final int selection = x;

			btn[x] = new JButton();
			btn[x].setFocusPainted(false);
			btn[x].setSize(new Dimension(40, 20));
			btn[x].setMargin(new Insets(1, 1, 1, 1));
			if (UIManager.getLookAndFeel().getName().equalsIgnoreCase("nimbus"))
				btn[x].setBorder(BorderFactory.createEmptyBorder(1, 0, 1, 0));

			if (x < 7) {
				btn[x].setText(calendarHeader[x]);
				btn[x].setForeground(Color.gray);
				if (UIManager.getLookAndFeel().getName()
						.equalsIgnoreCase("nimbus"))
					btn[x].setBorderPainted(false);
				btn[x].setFocusPainted(false);
			}

			if (x > 6)
				btn[x].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						displayDatePicked(btn[selection].getActionCommand());
					}
				});

			midPanel.add(ResourceManager.decorateComponent(btn[x],
					"calendar.buttons"));
		}

		JPanel lowPanel = new JPanel(new BorderLayout(0, 0));

		JButton prevBtn = new JButton(monthNavigation[0]);
		prevBtn.setSize(new Dimension(30, 20));
		prevBtn.setMargin(new Insets(1, 1, 0, 0));
		if (UIManager.getLookAndFeel().getName().equalsIgnoreCase("nimbus"))
			prevBtn.setBorder(BorderFactory.createEmptyBorder(1, 7, 1, 7));
		prevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				--month;
				setDates();
			}
		});

		JButton nextBtn = new JButton(monthNavigation[1]);
		nextBtn.setSize(new Dimension(30, 20));
		nextBtn.setMargin(new Insets(1, 1, 0, 0));
		if (UIManager.getLookAndFeel().getName().equalsIgnoreCase("nimbus"))
			nextBtn.setBorder(BorderFactory.createEmptyBorder(1, 7, 1, 7));
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				++month;
				setDates();
			}
		});

		JButton prevYBtn = new JButton(yearNavigation[0]);
		prevYBtn.setSize(new Dimension(20, 15));
		prevYBtn.setMargin(new Insets(1, 1, 0, 0));
		if (UIManager.getLookAndFeel().getName().equalsIgnoreCase("nimbus"))
			prevYBtn.setBorder(BorderFactory.createEmptyBorder(1, 7, 1, 7));
		prevYBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				--year;
				setDates();
			}
		});

		JButton nextYBtn = new JButton(yearNavigation[1]);
		nextYBtn.setSize(new Dimension(20, 15));
		nextYBtn.setMargin(new Insets(1, 1, 0, 0));
		if (UIManager.getLookAndFeel().getName().equalsIgnoreCase("nimbus"))
			nextYBtn.setBorder(BorderFactory.createEmptyBorder(1, 7, 1, 7));
		nextYBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				++year;
				setDates();
			}
		});

		JPanel left = new JPanel(new BorderLayout(0, 0));
		left.setPreferredSize(new Dimension(100, 20));
		left.add(ResourceManager.decorateComponent(prevBtn,
				"calendar.navig.button"), BorderLayout.WEST);
		left.add(
				ResourceManager.decorateComponent(lbl, "calendar.navig.button"),
				BorderLayout.CENTER);
		left.add(ResourceManager.decorateComponent(nextBtn,
				"calendar.navig.button"), BorderLayout.EAST);

		lowPanel.add(left, BorderLayout.WEST);

		JPanel right = new JPanel(new BorderLayout(0, 0));
		right.setPreferredSize(new Dimension(90, 20));
		right.add(ResourceManager.decorateComponent(prevYBtn,
				"calendar.navig.button"), BorderLayout.WEST);
		right.add(ResourceManager.decorateComponent(lblY,
				"calendar.navig.button"), BorderLayout.CENTER);
		right.add(ResourceManager.decorateComponent(nextYBtn,
				"calendar.navig.button"), BorderLayout.EAST);

		lowPanel.add(right, BorderLayout.EAST);

		getContentPane().add(midPanel, BorderLayout.CENTER);
		getContentPane().add(lowPanel, BorderLayout.SOUTH);
		pack();
	}

	public void setDate(Object date) {
		if (date == null)
			return;
		Calendar cal = Calendar.getInstance();
		if (date instanceof Date)
			cal.setTime((Date) date);
		try {
			if (date instanceof String) {
				cal.setTime(exitsdf.parse((String) date));
				textdate.setText((String) date);
			}
		} catch (ParseException e) {
			return;
		}

		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		setDates();
	}

	public void setDates()

	{
		for (int x = 7; x < btn.length; ++x) {
			btn[x].setText("");
			btn[x].setVisible(false);
		}

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1);

		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; ++x, ++day) {
			btn[x].setText("" + day);
			btn[x].setVisible(true);
		}

		// Sundays
		btn[7].setForeground(Color.RED);
		btn[14].setForeground(Color.RED);
		btn[21].setForeground(Color.RED);
		btn[28].setForeground(Color.RED);
		btn[35].setForeground(Color.RED);
		btn[42].setForeground(Color.RED);
		// Sundays

		lbl.setText(sdfM.format(cal.getTime()));
		lblY.setText(sdfY.format(cal.getTime()));
		setTitle("          " + sdf.format(cal.getTime()));
	}

	public void displayDatePicked(String day)

	{
		if (day == null || day.isEmpty())
			return;

		// SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" ) ;
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, Integer.parseInt(day));

		// textField.setText( sdf.format( cal.getTime( ) ) ) ;
		setVisible(false);
		textdate.setText(exitsdf.format(cal.getTime()));
	}

	/**
	 * @param textdate
	 *            the textdate to set
	 */
	public void setTextdate(JLabel textdate) {
		this.textdate = textdate;
	}

	/**
	 * @return the textdate
	 */
	public JLabel getTextdate() {
		return textdate;
	}
}