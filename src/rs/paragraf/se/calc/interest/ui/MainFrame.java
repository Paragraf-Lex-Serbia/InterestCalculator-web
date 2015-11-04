package rs.paragraf.se.calc.interest.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import rs.paragraf.se.calc.interest.data.AccountBean;
import rs.paragraf.se.calc.interest.data.ConfigBean;
import rs.paragraf.se.calc.interest.utils.RateManager;
import rs.paragraf.se.calc.interest.utils.ResourceManager;
import rs.paragraf.se.calc.interest.utils.Unpacker;
import rs.paragraf.se.calc.interest.utils.Unpacker.UnpackException;

public class MainFrame extends JFrame {

	public static String RUNTIME_PATH = "";
	public static final SimpleDateFormat sdf = new SimpleDateFormat(
			"dd.MM.yyyy.");
	public static final SimpleDateFormat sdfWithPoints = new SimpleDateFormat(
			"dd.MM.yyyy.");
	public static final SimpleDateFormat sdfMonthley = new SimpleDateFormat(
			"MMM yyyy");
	public static NumberFormat decimalFormater = NumberFormat
			.getInstance(Locale.GERMAN);
	public static NumberFormat rateFormater = NumberFormat
			.getInstance(Locale.GERMAN);

	public static final DefaultFormatterFactory factory = new DefaultFormatterFactory(
			new NumberFormatter(decimalFormater), new NumberFormatter(
					decimalFormater), new NumberFormatter(decimalFormater));

	public static final long milPerDay = 1000 * 60 * 60 * 24;
	/** The path to the font. */
	// public static final String FONT = "c:/windows/fonts/arial.ttf";

	private JToggleButton interestCalculationButton = new JToggleButton();
	private JToggleButton rateOverviewButton = new JToggleButton();
	private JToggleButton rateChangeButton = new JToggleButton();
	private JToggleButton configurationButton = new JToggleButton();
	private JButton helpButton = new JButton();

	private JButton links = new JButton();
	private JButton ecbRatesBtn = new JButton();
	private JButton dbDocuments = new JButton();
	private JButton exit = new JButton();

	private StatusBar statusBar = new StatusBar();

	public JPanel content = new JPanel(new CardLayout());
	// public WelcomePanel welcomePanel = new WelcomePanel();
	// public InterestCalculationPanel interesetCalculationPanel = new
	// InterestCalculationPanel();
	public InterestCalculationPanel interestCalculationPanel;

	public RateOverviewPanel rateOverviewPanel;

	// public JPanel configPanel = new JPanel();
	public ConfigPanel configPanel = new ConfigPanel("Konfiguracija");
	// private static final String VERSION = "15.1.";

	// public static final String WELCOME = "welcome";
	public static final String INTEREST = "interest";
	public static final String RATE_OVERVIEW = "overview";
	public static final String RATE_CHANGE = "change";
	public static final String CONFIG = "config";

	public enum Delimiter {
		POINT, COMMA
	};

	private static Delimiter delimiter = Delimiter.COMMA;

	// public static String welcomeMessage = "Dobar Dan!";

	public static Properties properties = new Properties();
	public static Properties printProperties = new Properties();

	private static MainFrame instance = null;

	public static MainFrame getInstance() {
		if (instance == null)
			instance = new MainFrame();
		return instance;
	}

	public static void setCommaAsDecimalDelimiter(boolean comma) {
		if (comma) {
			decimalFormater = NumberFormat.getInstance(Locale.GERMAN);
			delimiter = Delimiter.COMMA;
		} else {
			decimalFormater = NumberFormat.getInstance(Locale.CANADA);
			delimiter = Delimiter.POINT;
		}
		decimalFormater.setMinimumIntegerDigits(1);
		decimalFormater.setMinimumFractionDigits(2);
		decimalFormater.setMaximumFractionDigits(2);
		decimalFormater.setGroupingUsed(true);

		if (comma) {
			rateFormater = NumberFormat.getInstance(Locale.GERMAN);
		} else {
			rateFormater = NumberFormat.getInstance(Locale.CANADA);
		}
		rateFormater.setMinimumIntegerDigits(1);
		rateFormater.setMinimumFractionDigits(4);
		rateFormater.setMaximumFractionDigits(4);

		// rateFormater.setGroupingUsed(true);

	}

	public MainFrame() throws HeadlessException {
		super();

		decimalFormater.setMinimumIntegerDigits(1);
		decimalFormater.setMinimumFractionDigits(2);
		decimalFormater.setMaximumFractionDigits(2);
		decimalFormater.setGroupingUsed(true);

		rateFormater.setMinimumIntegerDigits(1);
		rateFormater.setMinimumFractionDigits(4);
		rateFormater.setMaximumFractionDigits(4);
		// rateFormater.setGroupingUsed(true);

		this.setTitle(MessageFormat.format(
				properties.getProperty("jws.mainframe.title.label"),
				properties.getProperty("application.version")));
		this.setIconImage(((ImageIcon) ResourceManager
				.getImageIcon("application.icon")).getImage());
		setLayout(new BorderLayout());

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				closing_action();
			}
		});

		ButtonGroup group = new ButtonGroup();
		group.add(interestCalculationButton);
		group.add(rateOverviewButton);
		group.add(rateChangeButton);
		group.add(configurationButton);
		// group.add(helpButton);

		interestCalculationButton.setText(MainFrame.properties
				.getProperty("interest.calculation.navig.button"));
		interestCalculationButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				((CardLayout) content.getLayout()).show(content, INTEREST);
			}
		});
		interestCalculationButton.setSelected(true);
		rateOverviewButton.setText(MainFrame.properties
				.getProperty("rate.overview.navig.button"));
		rateOverviewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				rateOverviewPanel.setEditable(false);
				((CardLayout) content.getLayout()).show(content, RATE_OVERVIEW);
			}
		});
		rateChangeButton.setText(MainFrame.properties
				.getProperty("rate.change.navig.button"));
		rateChangeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				rateOverviewPanel.setSelectedTab(1);
				rateOverviewPanel.setEditable(true);
				((CardLayout) content.getLayout()).show(content, RATE_OVERVIEW);
			}
		});
		configurationButton.setText(MainFrame.properties
				.getProperty("configuration.navig.button"));
		configurationButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				((CardLayout) content.getLayout()).show(content, CONFIG);
			}
		});
		helpButton.setText(MainFrame.properties
				.getProperty("help.navig.button"));
		helpButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					File file = new File(MainFrame.RUNTIME_PATH
							+ "picalc/conf/docs/interesthelp.pdf");
					// Desktop.getDesktop().open(new File(MainFrame.RUNTIME_PATH
					// + "conf/interesthelp.pdf"));
					Desktop.getDesktop()
							.open(new File(file.getCanonicalPath()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		links.setText(properties.getProperty("useful.links"));
		links.setPreferredSize(new Dimension(200, 30));
		links.setMinimumSize(new Dimension(200, 30));
		links.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File file = new File(RUNTIME_PATH + "picalc/conf/docs/links.htm");
					// Desktop.getDesktop().open(new File(RUNTIME_PATH +
					// "conf/links.htm"));
					Desktop.getDesktop()
							.open(new File(file.getCanonicalPath()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		dbDocuments.setText(properties.getProperty("database.documents"));
		dbDocuments.setPreferredSize(new Dimension(180, 30));
		// dbDocuments.setMinimumSize(new Dimension(200, 30));
		dbDocuments.setBackground(new Color(134, 186, 235));
		dbDocuments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File file = new File(RUNTIME_PATH
							+ "picalc/conf/docs/document.htm");
					// Desktop.getDesktop().open(new File( RUNTIME_PATH +
					// "conf/document.htm"));
					Desktop.getDesktop()
							.open(new File(file.getCanonicalPath()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		ecbRatesBtn.setText(properties.getProperty("ecb.rates.button.label"));
		ecbRatesBtn.setPreferredSize(new Dimension(130, 30));
		// dbDocuments.setMinimumSize(new Dimension(200, 30));
		ecbRatesBtn.setBackground(new Color(134, 186, 235));
		ecbRatesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(
							new URI(properties.getProperty("ecb.rates.uri")));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		exit.setText(MainFrame.properties.getProperty("exit.main.button"));
		exit.setPreferredSize(new Dimension(150, 30));
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closing_action();
			}
		});

		((CardLayout) content.getLayout()).show(content, INTEREST);
		JPanel header = new JPanel(new BorderLayout());
		header.add(new JLabel(ResourceManager.getImageIcon("top.header")),
				BorderLayout.NORTH);
		JPanel navigButtons = new JPanel(new GridLayout(1, 5));
		navigButtons.add(ResourceManager.decorateComponent(
				interestCalculationButton, "navig.buttons"));
		navigButtons.add(ResourceManager.decorateComponent(rateOverviewButton,
				"navig.buttons"));
		navigButtons.add(ResourceManager.decorateComponent(rateChangeButton,
				"navig.buttons"));
		navigButtons.add(ResourceManager.decorateComponent(configurationButton,
				"navig.buttons"));
		navigButtons.add(ResourceManager.decorateComponent(helpButton,
				"navig.buttons"));
		header.add(navigButtons, BorderLayout.CENTER);

		add(header, BorderLayout.PAGE_START);

		JScrollPane configPanelScroll = new JScrollPane(configPanel);
		configPanelScroll.setBorder(null);

		ConfigBean user = configPanel.deserializeDataFromFile();
		configPanel.populate(user);

		interestCalculationPanel = new InterestCalculationPanel();
		rateOverviewPanel = new RateOverviewPanel();
		// JScrollPane welcomePanelScroll = new JScrollPane(welcomePanel);
		// welcomePanelScroll.setBorder(null);
		JScrollPane interestCalculationPanelScroll = new JScrollPane(
				interestCalculationPanel);
		interestCalculationPanelScroll.setBorder(null);

		// content.add(welcomePanelScroll, WELCOME);
		content.add(interestCalculationPanelScroll, INTEREST);
		content.add(rateOverviewPanel, RATE_OVERVIEW);
		content.add(configPanelScroll, CONFIG);

		add(content, BorderLayout.CENTER);

		JPanel footerButtonPane = new JPanel();
		footerButtonPane.add(ResourceManager.decorateComponent(dbDocuments,
				"navig.buttons"));
		footerButtonPane.add(ResourceManager.decorateComponent(ecbRatesBtn,
				"navig.buttons"));

		// footerButtonPane.add(ResourceManager.decorateComponent(links,
		// "navig.buttons"));
		footerButtonPane.add(ResourceManager.decorateComponent(exit,
				"navig.buttons"));

		JPanel footer = new JPanel(new BorderLayout());
		// footer.add(ResourceManager.decorateComponent(exit, "main.button"),
		// BorderLayout.EAST);
		footer.add(footerButtonPane, BorderLayout.EAST);
		// add status bar
		footer.add(statusBar);
		add(footer, BorderLayout.PAGE_END);

		setCommaAsDecimalDelimiter(user.getDelimiter() == ',');
		getInterestCalculationPanel().setFormaterFactory();
		getInterestCalculationPanel().populate(
				getInterestCalculationPanel().readForm());
		getRateOverviewPanel().getVariableRatesPane().setFormaterFactory();
		getRateOverviewPanel().getVariableRatesPane().initVariableData();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(new Dimension(Integer.parseInt(properties
				.getProperty("mainframe.width")), Integer.parseInt(properties
				.getProperty("mainframe.heigth"))));
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	protected void closing_action() {
		try {
			configPanel.serializeDataToFile();
			MainFrame.getInstance().getRateOverviewPanel()
					.getVariableRatesPane().saveAction();

			AccountBean infilebean = interestCalculationPanel.loadAccountBean();
			if (infilebean == null)
				infilebean = new AccountBean();

			if (!interestCalculationPanel.readForm().equals(infilebean)) {
				Object[] o = { "Snimi obra\u010Dun", "Iza\u0111i bez snimanja",
						"Odustani" };
				int optionresult = JOptionPane.showOptionDialog(null,
						MainFrame.properties.getProperty("exit.save.message"),
						"Informacija", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, o, o[0]);

				switch (optionresult) {
				case 0:
					interestCalculationPanel.saveAccount();
					break;
				case 2:
					return;
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		this.dispose();
		instance = null;
		System.out.println("Runtime ::" + RUNTIME_PATH);
		if (RUNTIME_PATH.isEmpty())
			System.exit(0);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// System.out.println("TimeZone before :: " + TimeZone.getDefault());
		Locale.setDefault(Locale.US);
		// TimeZone.setDefault(TimeZone.getTimeZone("GMT+8:00"));
		// System.out.println("TimeZone after :: " + TimeZone.getDefault());
		if (args.length > 0)
			RUNTIME_PATH = args[0];

		// System.setProperty("file.encoding", "utf8");
		// System.out.println(RUNTIME_PATH);
		try {
			properties.load(MainFrame.class.getClassLoader()
					.getResourceAsStream(RUNTIME_PATH + "interest.cfg"));
			printProperties.load(MainFrame.class.getClassLoader()
					.getResourceAsStream(RUNTIME_PATH + "printInterest.cfg"));
		} catch (Exception e1) {
			e1.printStackTrace();
			// JOptionPane.showMessageDialog(null,
			// "Konfiguracijoni fajl za Kamate nije odgovaraju\u0107i");
			showMessage(properties.getProperty("configFile.error"),
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!new File(RUNTIME_PATH + "picalc/").exists()) {
			new File(RUNTIME_PATH + "picalc/").mkdirs();
		}
		if (!new File(RUNTIME_PATH + "picalc/obracuni/").exists()) {
			new File(RUNTIME_PATH + "picalc/obracuni/").mkdirs();
		}
		if (!new File(RUNTIME_PATH + "picalc/conf/").exists()) {
			new File(RUNTIME_PATH + "picalc/conf/").mkdirs();
		}
		if (!new File(RUNTIME_PATH + "picalc/conf/docs/").exists()) {
			new File(RUNTIME_PATH + "picalc/conf/docs/").mkdirs();
		}
		try {
			Unpacker.unpackFromClassPath("docs/document.htm",
					"picalc/conf/docs/document.htm");
			Unpacker.unpackFromClassPath("docs/method.htm",
					"picalc/conf/docs/method.htm");
			Unpacker.unpackFromClassPath("docs/links.htm",
					"picalc/conf/docs/links.htm");
			Unpacker.unpackFromClassPath("docs/interesthelp.pdf",
					"picalc/conf/docs/interesthelp.pdf");
		} catch (Unpacker.UnpackException e2) {
			e2.printStackTrace();
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		Toolkit.getDefaultToolkit().setDynamicLayout(true);
		System.setProperty("sun.awt.noerasebackground", "true");
		if (RUNTIME_PATH.isEmpty()) {
			try {
				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			} catch (Exception e) {
				try {
					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			UIManager.put("Button.contentMargins", new InsetsUIResource(0, 0,
					0, 0));
		}
		if ((properties.getProperty("mainframe.intro.message") != null)
				&& (!properties.getProperty("mainframe.intro.message")
						.isEmpty())) {
			showMessage(properties.getProperty("mainframe.intro.message"),
					JOptionPane.PLAIN_MESSAGE);
		}
		RateManager.getInstance();
		getInstance();
	}

	public static Delimiter getDelimiter() {
		return delimiter;
	}

	public static void setDelimiter(Delimiter delimiter) {
		MainFrame.delimiter = delimiter;
	}

	public ConfigPanel getConfigPanel() {
		return configPanel;
	}

	public void setConfigPanel(ConfigPanel configPanel) {
		this.configPanel = configPanel;
	}

	public StatusBar getStatusBar() {
		return statusBar;
	}

	public void setStatusBar(StatusBar statusBar) {
		this.statusBar = statusBar;
	}

	public InterestCalculationPanel getInterestCalculationPanel() {
		return interestCalculationPanel;
	}

	public void setInterestCalculationPanel(
			InterestCalculationPanel interestCalculationPanel) {
		this.interestCalculationPanel = interestCalculationPanel;
	}

	public RateOverviewPanel getRateOverviewPanel() {
		return rateOverviewPanel;
	}

	public void setRateOverviewPanel(RateOverviewPanel rateOverviewPanel) {
		this.rateOverviewPanel = rateOverviewPanel;
	}

	public static void showMessage(String message, int type) {

		Object[] options = { properties.getProperty("button.ok") };

		switch (type) {
		case JOptionPane.INFORMATION_MESSAGE:
			JOptionPane.showOptionDialog(null, message,
					properties.getProperty("messageType.information"),
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			break;

		case JOptionPane.WARNING_MESSAGE:
			JOptionPane.showOptionDialog(null, message,
					properties.getProperty("messageType.warning"),
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
					null, options, options[0]);
			break;

		case JOptionPane.ERROR_MESSAGE:
			JOptionPane.showOptionDialog(null, message,
					properties.getProperty("messageType.error"),
					JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
					null, options, options[0]);
			break;
		case JOptionPane.PLAIN_MESSAGE:
			JOptionPane.showOptionDialog(null, message,
					properties.getProperty("messageType.plain"),
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
					null, options, options[0]);
			break;
		}
	}

	public static void showMessage(String message, int type, Window owner) {

		Object[] options = { properties.getProperty("button.ok") };

		switch (type) {
		case JOptionPane.INFORMATION_MESSAGE:
			JOptionPane.showOptionDialog(owner, message,
					properties.getProperty("messageType.information"),
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			break;

		case JOptionPane.WARNING_MESSAGE:
			JOptionPane.showOptionDialog(owner, message,
					properties.getProperty("messageType.warning"),
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
					null, options, options[0]);
			break;

		case JOptionPane.ERROR_MESSAGE:
			JOptionPane.showOptionDialog(owner, message,
					properties.getProperty("messageType.error"),
					JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
					null, options, options[0]);
			break;
		}
	}

	public static void showMessage(String message, int type, JPanel owner) {

		Object[] options = { properties.getProperty("button.ok") };

		switch (type) {
		case JOptionPane.INFORMATION_MESSAGE:
			JOptionPane.showOptionDialog(owner, message,
					properties.getProperty("global.information"),
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			break;

		case JOptionPane.WARNING_MESSAGE:
			JOptionPane.showOptionDialog(owner, message,
					properties.getProperty("messageType.warning"),
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
					null, options, options[0]);
			break;

		case JOptionPane.ERROR_MESSAGE:
			JOptionPane.showOptionDialog(owner, message,
					properties.getProperty("messageType.error"),
					JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
					null, options, options[0]);
			break;
		}
	}

}