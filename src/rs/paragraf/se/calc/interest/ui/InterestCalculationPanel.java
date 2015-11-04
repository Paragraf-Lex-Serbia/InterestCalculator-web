package rs.paragraf.se.calc.interest.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import net.miginfocom.swing.MigLayout;
import rs.paragraf.se.calc.interest.data.AccountBean;
import rs.paragraf.se.calc.interest.data.AccountChangeBean;
import rs.paragraf.se.calc.interest.data.AccountUserBean;
import rs.paragraf.se.calc.interest.ui.utilz.DatePickerTableEditor;
import rs.paragraf.se.calc.interest.ui.utilz.NumberCellRenderer;
import rs.paragraf.se.calc.interest.ui.utilz.VariableRatesCellEditor;
import rs.paragraf.se.calc.interest.utils.ItextPrintUtil;
import rs.paragraf.se.calc.interest.utils.MyFileChooser;
import rs.paragraf.se.calc.interest.utils.ResourceManager;

public class InterestCalculationPanel extends JPanel {
	private File accountFile = null;
	private MyFileChooser fileChooser = new MyFileChooser(new File(
			MainFrame.RUNTIME_PATH + "picalc/obracuni/").getAbsolutePath());
	private MainPanel mainPanel = new MainPanel();
	private UsersPanel usersPanel = new UsersPanel();
	private AccountPanel accountPanel = new AccountPanel();

	private JButton overviewAccount = new JButton();
	private JButton printAccount = new JButton();
	private JButton newAccount = new JButton();
	private JButton loadAccount = new JButton();
	private JButton saveAccount = new JButton();
	private JButton saveAsAccount = new JButton();

	// private JButton exit = new JButton();

	/**
	 *
	 */
	public InterestCalculationPanel() {
		super();
		init();
	}

	private void init() {
		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(3)));
		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.NORTH);
		JPanel expandableRegion = new JPanel();
		expandableRegion.setLayout(
				new BoxLayout(expandableRegion,BoxLayout.Y_AXIS));
		expandableRegion.add(usersPanel);
		expandableRegion.add(accountPanel);
		add(expandableRegion, BorderLayout.CENTER);

		fileChooser.setMultiSelectionEnabled(false);
//		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setApproveButtonText(MainFrame.properties.getProperty("filechooser.open.label"));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "Paragraf kamatni obra\u010Dun (.pko)";
			}

			@Override
			public boolean accept(File f) {
			    if (f.isDirectory()) return true;
				if (f.getName().endsWith(".pko")) return true;
				return false;
			}
		});

		overviewAccount.setText(
				MainFrame.properties.getProperty("overview.main.button"));
		printAccount.setText(
				MainFrame.properties.getProperty("print.main.button"));
		newAccount.setText(
				MainFrame.properties.getProperty("new.main.button"));
		loadAccount.setText(
				MainFrame.properties.getProperty("load.main.button"));
		saveAccount.setText(
				MainFrame.properties.getProperty("save.main.button"));
		saveAsAccount.setText(
				MainFrame.properties.getProperty("saveas.main.button"));

		overviewAccount.setPreferredSize(new Dimension(
							Integer.parseInt(MainFrame.properties.getProperty("x.size.main.button")),
							Integer.parseInt(MainFrame.properties.getProperty("y.size.main.button"))));
		printAccount.setPreferredSize(new Dimension(
				Integer.parseInt(MainFrame.properties.getProperty("x.size.main.button")),
				Integer.parseInt(MainFrame.properties.getProperty("y.size.main.button"))));
		newAccount.setPreferredSize(new Dimension(
				Integer.parseInt(MainFrame.properties.getProperty("x.size.main.button")),
				Integer.parseInt(MainFrame.properties.getProperty("y.size.main.button"))));
		loadAccount.setPreferredSize(new Dimension(
				Integer.parseInt(MainFrame.properties.getProperty("x.size.main.button")),
				Integer.parseInt(MainFrame.properties.getProperty("y.size.main.button"))));
		saveAccount.setPreferredSize(new Dimension(
				Integer.parseInt(MainFrame.properties.getProperty("x.size.main.button")),
				Integer.parseInt(MainFrame.properties.getProperty("y.size.main.button"))));
		saveAsAccount.setPreferredSize(new Dimension(
				Integer.parseInt(MainFrame.properties.getProperty("x.size.main.button")),
				Integer.parseInt(MainFrame.properties.getProperty("y.size.main.button"))));

		overviewAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				overviewAccount();
			}
		});
		printAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				printAccount();
			}
		});
		newAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newAccount();
			}
		});
		loadAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadAccount();
			}
		});
		saveAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveAccount();
			}
		});
		saveAsAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveAsAccount();
			}
		});

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(6, 1));
		buttons.add(ResourceManager.decorateComponent(overviewAccount,"main.button"));
		buttons.add(ResourceManager.decorateComponent(printAccount,"main.button"));

		buttons.add(ResourceManager.decorateComponent(loadAccount,"main.button"));
		buttons.add(ResourceManager.decorateComponent(saveAccount,"main.button"));
		buttons.add(ResourceManager.decorateComponent(saveAsAccount,"main.button"));
		buttons.add(ResourceManager.decorateComponent(newAccount, "main.button"));
		JPanel buttonsBagPanel = new JPanel(new BorderLayout());
		buttonsBagPanel.add(buttons, BorderLayout.NORTH);

		add(buttonsBagPanel, BorderLayout.EAST);
	}

	protected void newAccount() {
		this.clear();
	}

	protected void overviewAccount() {
		ItextPrintUtil.getInstance().printPreview();
	}

	protected void printAccount() {
		ItextPrintUtil.getInstance().printHTML();
	}

	protected void loadAccount() {
		fileChooser.setDialogTitle(MainFrame.properties.getProperty("load.main.button"));
		if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
			return;
		accountFile = fileChooser.getSelectedFile();
		MainFrame.getInstance().getStatusBar().setMessage(MainFrame.properties.getProperty("prefix.status.bar") +accountFile.getName());

		populate(loadAccountBean());
	}

	public AccountBean loadAccountBean()
	{
		if (accountFile==null) return null;
		AccountBean account = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(accountFile);
			in = new ObjectInputStream(fis);

			account = (AccountBean) in.readObject();
			in.close();

			return account;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	protected void saveAccount() {
		if (accountFile == null)
			{
			saveAsAccount();
			return;
			}

		if (getAccountPanel().getTable().getCellEditor() != null)
			getAccountPanel().getTable().getCellEditor().stopCellEditing();

		AccountBean account = this.readForm();
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(accountFile);
			out = new ObjectOutputStream(fos);
			out.writeObject(account);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	protected void saveAsAccount() {
		fileChooser.setDialogTitle(MainFrame.properties.getProperty("saveas.main.button"));
		if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
			return;

		accountFile = fileChooser.getSelectedFile();
		if (accountFile.getName().lastIndexOf('.')==-1) accountFile = new File(accountFile.getAbsolutePath() +".pko");
		saveAccount();
		MainFrame.getInstance().getStatusBar().setMessage(MainFrame.properties.getProperty("prefix.status.bar") + accountFile.getName());

	}

	public void clear() {
		mainPanel.clear();
		usersPanel.clear();
		accountPanel.clear();
		accountFile = null;
		MainFrame.getInstance().getStatusBar().setMessage(MainFrame.properties.getProperty("new.document.status.bar"));
	}

	public void setFormaterFactory() {
		DefaultFormatterFactory factory = new DefaultFormatterFactory(new NumberFormatter(MainFrame.decimalFormater));
		getMainPanel().getStartAmount().setFormatterFactory(factory);
		getMainPanel().getTotal().setFormatterFactory(factory);
		getMainPanel().getInterest().setFormatterFactory(factory);
		getAccountPanel().getNumberCellRenderer().setFormater(MainFrame.decimalFormater);
	}

	public void populate(AccountBean account) {
		if (account==null) return;
		mainPanel.populate(account);
		usersPanel.populate(account.getCreditor(), account.getDebtor());
		accountPanel.populate(account.getChanges());
	}

	public AccountBean readForm() {
		AccountBean account = mainPanel.readForm();
		usersPanel.readForm(account);
		account.setChanges(accountPanel.readForm());
		return account;
	}

	public AccountPanel getAccountPanel() {
		return accountPanel;
	}

	public void setAccountPanel(AccountPanel accountPanel) {
		this.accountPanel = accountPanel;
	}

	public MainPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public class MainPanel extends JPanel {

		private JRadioButton lawInterestType = new JRadioButton();
		private JRadioButton lawInterestTypeEur = new JRadioButton();
		private JRadioButton priceGrowthInterestType = new JRadioButton();
		private JRadioButton taxInterestType = new JRadioButton();
		private JRadioButton freeContractInterestType = new JRadioButton();

		private JRadioButton fixedAnualContractInterestType = new JRadioButton();
		private JRadioButton fixedMonthleyContractInterestType = new JRadioButton();
		private JRadioButton fixedDailyContractInterestType = new JRadioButton();
		private JRadioButton excontAnualInterestType = new JRadioButton();
		private JRadioButton referentInterestType = new JRadioButton();
		private JRadioButton ecbInterestType = new JRadioButton();//???

		private JRadioButton conformMethod = new JRadioButton();
		private JRadioButton proporcionalMethod = new JRadioButton();
		private JRadioButton mixedMethod = new JRadioButton();

		private JRadioButton calculationTypeInterestFirst = new JRadioButton();
		private JRadioButton calculationTypeAmmountFirst = new JRadioButton();

		private CalendarPicker fromDate;
		private CalendarPicker toDate;

		private JLabel startAmountLabel = new JLabel();
		private JFormattedTextField startAmount = new JFormattedTextField(MainFrame.decimalFormater);

		private JLabel documentLabel = new JLabel();
		private JTextField document = new JTextField();

		private JButton calculate = new JButton();

		private JLabel interestLabel = new JLabel();
		private JFormattedTextField interest = new JFormattedTextField(MainFrame.decimalFormater);

		private JLabel totalLabel = new JLabel();
		//private JFormattedTextField total = new JFormattedTextField(MainFrame.factory);
		private JFormattedTextField total = new JFormattedTextField(MainFrame.decimalFormater);
		private JButton methodsInfoButton = new JButton();

		/**
			 *
			 */
		public MainPanel() {
			super();
			init();
		}

		public void clear() {
			setInterestType(AccountBean.LAW_INTEREST_TYPE);
//			setMethodType(AccountBean.PROPORCIONAL_METHOD_TYPE);
			lawInterestType.getActionListeners()[0].actionPerformed(null);
			setCalculationType(AccountBean.CALCULATION_TYPE.INTEREST_FIRST_CALCULATION_TYPE);
			fromDate.setText("");
			toDate.setText("");
			startAmount.setText("");
			document.setText("");
			interest.setText("");
			total.setText("");
		}

		private void init() {
			setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createBevelBorder(5)));

			lawInterestType.setSelected(true);
			enablePropConfMethod(false);
			mixedMethod.setSelected(true);
			lawInterestType.setText(
					MainFrame.properties.getProperty("law.interest.type"));
			lawInterestTypeEur.setText(
					MainFrame.properties.getProperty("law.eur.interest.type"));
			priceGrowthInterestType.setText(
					MainFrame.properties.getProperty("price.growth.interest.type"));
			taxInterestType.setText(
					MainFrame.properties.getProperty("tax.interest.type"));
			fixedAnualContractInterestType.setText(
					MainFrame.properties.getProperty("fixed.anual.contract.interest.type"));
			fixedMonthleyContractInterestType.setText(
					MainFrame.properties.getProperty("fixed.monthley.contract.interest.type"));
			fixedDailyContractInterestType.setText(
					MainFrame.properties.getProperty("fixed.daily.contract.interest.type"));
			freeContractInterestType.setText(
					MainFrame.properties.getProperty("free.contract.interest.type"));
			excontAnualInterestType.setText(
					MainFrame.properties.getProperty("excont.anual.interest.type"));
			referentInterestType.setText(
					MainFrame.properties.getProperty("referent.interest.type"));
			ecbInterestType.setText(
					MainFrame.properties.getProperty("ecb.interest.type"));

//			disableConformAction();
//
			lawInterestType.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					enableMixMethod(true);
					enablePropConfMethod(false);
					enableCalculationType(true);
					mixedMethod.setSelected(true);
				}
			});

			lawInterestTypeEur.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					enableMixMethod(false);
					enablePropConfMethod(true);
					enableCalculationType(true);
					proporcionalMethod.setSelected(true);
				}
			});
			priceGrowthInterestType.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					enableMixMethod(false);
					enablePropConfMethod(true);
					enableCalculationType(true);
				}
			});
			taxInterestType.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					enableCalculationType(false);
					enablePropConfMethod(true);
					enableMixMethod(true);
					mixedMethod.setSelected(true);
				}
			});
			fixedAnualContractInterestType.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					enableMixMethod(false);
					enablePropConfMethod(true);
					enableCalculationType(true);
				}
			});
			fixedMonthleyContractInterestType.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					enableMixMethod(false);
					enablePropConfMethod(true);
					enableCalculationType(true);
				}
			});
			fixedDailyContractInterestType.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					enableMixMethod(false);
					enablePropConfMethod(true);
					enableCalculationType(true);
				}
			});
			freeContractInterestType.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					enableMixMethod(false);
					enablePropConfMethod(true);
					enableCalculationType(true);
				}
			});
			excontAnualInterestType.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					enableMixMethod(false);
					enablePropConfMethod(true);
					enableCalculationType(true);
				}
			});
			referentInterestType.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					enableMixMethod(false);
					enablePropConfMethod(true);
					enableCalculationType(true);
				}
			});
			ecbInterestType.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					enableMixMethod(false);
					enablePropConfMethod(true);
					enableCalculationType(true);
					popupMessage_action();
				}
			});

			ButtonGroup group = new ButtonGroup();
			group.add(lawInterestType);
			group.add(lawInterestTypeEur);
			group.add(priceGrowthInterestType);
			group.add(taxInterestType);
			group.add(ecbInterestType);
			group.add(fixedAnualContractInterestType);
			group.add(fixedMonthleyContractInterestType);
			group.add(fixedDailyContractInterestType);
			group.add(freeContractInterestType);
			group.add(excontAnualInterestType);
			group.add(referentInterestType);


			JPanel radioButtonsPanel = new JPanel(
					new MigLayout("insets 0 0 0 0", "[]1mm![]", "[]2mm![]2mm![]2mm![]2mm![]"));
			radioButtonsPanel.setBorder(BorderFactory.createTitledBorder(MainFrame.properties.getProperty("interest.type.title")));
			radioButtonsPanel.add(ResourceManager.decorateComponent(
					lawInterestType, "interest.type"));
			radioButtonsPanel.add(ResourceManager.decorateComponent(
					fixedAnualContractInterestType, "interest.type"), "wrap");
			radioButtonsPanel.add(ResourceManager.decorateComponent(
					lawInterestTypeEur, "interest.type"));
			radioButtonsPanel.add(ResourceManager.decorateComponent(
					fixedMonthleyContractInterestType, "interest.type"), "wrap");
			radioButtonsPanel.add(ResourceManager.decorateComponent(
					priceGrowthInterestType, "interest.type"));
			radioButtonsPanel.add(ResourceManager.decorateComponent(
					fixedDailyContractInterestType, "interest.type"), "wrap");
			radioButtonsPanel.add(ResourceManager.decorateComponent(
					taxInterestType, "interest.type"));
			radioButtonsPanel.add(ResourceManager.decorateComponent(
					excontAnualInterestType, "interest.type"), "wrap");
			radioButtonsPanel.add(ResourceManager.decorateComponent(
					ecbInterestType, "interest.type"));
			radioButtonsPanel.add(ResourceManager.decorateComponent(
					referentInterestType, "interest.type"), "wrap");
			radioButtonsPanel.add(ResourceManager.decorateComponent(
					freeContractInterestType, "interest.type"));


			JLabel title = new JLabel(
					MainFrame.properties.getProperty("title.input.fields"));
				fromDate = new CalendarPicker(MainFrame.sdf);
			fromDate.setLabelText(
					MainFrame.properties.getProperty("from.input.fields"));
			fromDate.getTextFiled().setPreferredSize(new Dimension(82, 28));

				toDate = new CalendarPicker(MainFrame.sdf);
			toDate.setLabelText(
					MainFrame.properties.getProperty("to.input.fields"));
			toDate.getTextFiled().setPreferredSize(new Dimension(82, 28));

			startAmountLabel.setText(
					MainFrame.properties.getProperty("start.amount.input.fields"));
			documentLabel.setText(
					MainFrame.properties.getProperty("document.input.fields"));

			JPanel inputFields = new JPanel(
					new MigLayout("insets 0 0 0 0", "[]1mm![]", "[]2mm![]2mm![]2mm![]2mm![]"));
			inputFields.add(ResourceManager.decorateComponent(
					title, "main.panel.input.fields.label"), "span 2, wrap");
			inputFields.add(ResourceManager.decorateComponent(
					fromDate, "main.panel.input.fields.label"));
			// decorate component will decorate both label and text with same font, but we need another font for text field!
			fromDate.getTextFiled().setFont(ResourceManager.getFont("main.panel.input.fields"));
			inputFields.add(ResourceManager.decorateComponent(
					toDate, "main.panel.input.fields.label"), "wrap");
			toDate.getTextFiled().setFont(ResourceManager.getFont("main.panel.input.fields"));
			inputFields.add(ResourceManager.decorateComponent(
					startAmountLabel, "main.panel.input.fields.label"));
			inputFields.add(ResourceManager.decorateComponent(
					startAmount, "main.panel.input.fields"), "w 45mm!, h 7mm!, wrap");
			inputFields.add(ResourceManager.decorateComponent(
					documentLabel, "main.panel.input.fields.label"));
			inputFields.add(ResourceManager.decorateComponent(
					document, "main.panel.input.fields"), "w 45mm!, h 7mm!, wrap");

			calculate.setText(
					MainFrame.properties.getProperty("calculate.button"));
			calculate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					calculate_action();
				}
			});
			interestLabel.setText(
					MainFrame.properties.getProperty("interest.amount"));
			totalLabel.setText(
					MainFrame.properties.getProperty("total.amount"));
				total.setHorizontalAlignment(JTextField.RIGHT);
				startAmount.setHorizontalAlignment(JTextField.RIGHT);
				interest.setHorizontalAlignment(JTextField.RIGHT);
				calculate.setBackground(new Color (235,226,0));

				calculationTypeAmmountFirst.setText(
						MainFrame.properties.getProperty("calculation.ammount.first.type"));
				calculationTypeInterestFirst.setText(
						MainFrame.properties.getProperty("calculation.interest.first.type"));
				calculationTypeInterestFirst.setSelected(true);


				mixedMethod.setText(
						MainFrame.properties.getProperty("mixed.method.type"));
				mixedMethod.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						popupMessage_action();

					}
				});


				proporcionalMethod.setText(
						MainFrame.properties.getProperty("proporcional.method.type"));
				//proporcionalMethod.setSelected(true);

				proporcionalMethod.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						popupMessage_action();

					}
				});

				conformMethod.setText(
						MainFrame.properties.getProperty("conform.method.type"));

				conformMethod.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						popupMessage_action();

					}
				});

//				conformMethod.setSelected(true);

				ButtonGroup methodGroup = new ButtonGroup();
				methodGroup.add(proporcionalMethod);
				methodGroup.add(conformMethod);
				methodGroup.add(mixedMethod);

				ButtonGroup calculationTypeGroup = new ButtonGroup();
				calculationTypeGroup.add(calculationTypeAmmountFirst);
				calculationTypeGroup.add(calculationTypeInterestFirst);


				methodsInfoButton.setPreferredSize(new Dimension(390,30));
				methodsInfoButton.setMinimumSize(new Dimension(390,30));

				methodsInfoButton.setText(
						MainFrame.properties.getProperty("methods.info.button"));
				methodsInfoButton.setBackground(new Color( 134,186,235));
				methodsInfoButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							File file = new File(MainFrame.RUNTIME_PATH + "picalc/conf/docs/method.htm");
						    //Desktop.getDesktop().open(new File(RUNTIME_PATH + "conf/links.htm"));
							Desktop.getDesktop().open(new File(file.getCanonicalPath()));
						} catch (IOException e1) {
						    // TODO Auto-generated catch block
						    e1.printStackTrace();
						}
					}
				});

				JPanel calculationTypePanel = new JPanel(
						new MigLayout("insets 0 0 0 0", "[]25mm![]", "[]"));
				calculationTypePanel.setBorder(BorderFactory.createTitledBorder(MainFrame.properties.getProperty("calculation.type.title")));
				calculationTypePanel.add(ResourceManager.decorateComponent(calculationTypeAmmountFirst, "interest.type"));
				calculationTypePanel.add(ResourceManager.decorateComponent(calculationTypeInterestFirst, "interest.type"),"wrap");

				JPanel methodsPanel = new JPanel(
						new MigLayout("insets 0 0 0 0", "[]8mm![]8mm![]", "[]2mm![]"));
				methodsPanel.setBorder(BorderFactory.createTitledBorder(MainFrame.properties.getProperty("method.type.title")));
				methodsPanel.add(ResourceManager.decorateComponent(proporcionalMethod, "interest.type"));
				methodsPanel.add(ResourceManager.decorateComponent(conformMethod, "interest.type"));
				methodsPanel.add(ResourceManager.decorateComponent(mixedMethod, "interest.type"),"wrap");

				methodsPanel.add(ResourceManager.decorateComponent(methodsInfoButton, "interest.type"),"span 3, alignx center");

				calculate.setPreferredSize(new Dimension(330,35));
				calculate.setMinimumSize(new Dimension(330,35));

			JPanel totalPanel = new JPanel(
						new MigLayout("insets 0 0 0 0", "", ""));
//			totalPanel.add(ResourceManager.decorateComponent(
//					calculate, "main.panel.results.label"), "wrap");
			JPanel textFieldPanel = new JPanel(
					new MigLayout("insets 0 0 0 0", "[]1mm![]", "[]2mm![]"));
			textFieldPanel.add(ResourceManager.decorateComponent(
					interestLabel, "main.panel.results.label"));
			textFieldPanel.add(ResourceManager.decorateComponent(
					interest, "main.panel.results"), "w 50mm!, h 7mm!, wrap");
			textFieldPanel.add(ResourceManager.decorateComponent(
					totalLabel, "main.panel.results.label"));
			textFieldPanel.add(ResourceManager.decorateComponent(
					total, "main.panel.results"), "w 50mm!, h 7mm!");
			totalPanel.add(textFieldPanel);

			setLayout(new MigLayout("insets 0 0 0 0", "[fill]0.5mm![]", "[]0mm![]0mm![]"));
			add(radioButtonsPanel, "top ");
			add(inputFields, " alignx right, wrap");
			add(calculationTypePanel, " alignx center");
			add(calculate,"wrap");
			add(methodsPanel, " alignx right");
			add(totalPanel, " alignx right");

		}

		protected void enableMixMethod(boolean enabled) {
			mixedMethod.setEnabled(enabled);
			if (mixedMethod.isSelected()) proporcionalMethod.setSelected(true);
		}

		protected void enablePropConfMethod(boolean enabled) {
			proporcionalMethod.setEnabled(enabled);
			conformMethod.setEnabled(enabled);
			if (!enabled) mixedMethod.setSelected(true);
		}

		protected void enableCalculationType(boolean enabled) {

			calculationTypeAmmountFirst.setEnabled(enabled);
			calculationTypeInterestFirst.setEnabled(enabled);
		}

		protected void popupMessage_action() {
			if ((!mixedMethod.isSelected()) && (lawInterestType.isSelected() || taxInterestType.isSelected()))
				JOptionPane.showMessageDialog(this,
						MainFrame.properties.getProperty("conform.method.warning.message"),
						MainFrame.properties.getProperty("messageType.warning"),
						JOptionPane.WARNING_MESSAGE, ResourceManager.getImageIcon("application.icon"));
			if ((mixedMethod.isSelected() || conformMethod.isSelected()) && (lawInterestTypeEur.isSelected() ))
				JOptionPane.showMessageDialog(this,
						MainFrame.properties.getProperty("proporcional.method.warning.message"),
						MainFrame.properties.getProperty("messageType.warning"),
						JOptionPane.WARNING_MESSAGE, ResourceManager.getImageIcon("application.icon"));
			if (ecbInterestType.isSelected())
				JOptionPane.showMessageDialog(this,
						MainFrame.properties.getProperty("ecb.method.info.message"),
						MainFrame.properties.getProperty("messageType.information"),
						JOptionPane.WARNING_MESSAGE, ResourceManager.getImageIcon("application.icon"));
			}

//		protected void disableConformAction() {
//			proporcionalMethod.setSelected(true);
//			conformMethod.setEnabled(false);
//		}
//
//		protected void enableConformAction() {
//			conformMethod.setEnabled(true);
//		}

		private boolean validateForm() {
			Date from, to = null;

			if (fromDate.getText().trim().isEmpty()) {
				MainFrame.showMessage(
						MainFrame.properties.getProperty("from.date.empty"),
						JOptionPane.ERROR_MESSAGE, this);
				return false;
			}
			try {
				from = fromDate.getPickedDate();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				MainFrame.showMessage(
						MainFrame.properties.getProperty("from.date.format.invalid"),
						JOptionPane.ERROR_MESSAGE, this);
				return false;
			}

			if (toDate.getText().trim().isEmpty()) {
				MainFrame.showMessage(
						MainFrame.properties.getProperty("to.date.empty"),
						JOptionPane.ERROR_MESSAGE, this);
				return false;
			}
			try {
				to = toDate.getPickedDate();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				MainFrame.showMessage(
						MainFrame.properties.getProperty("to.date.format.invalid"),
						JOptionPane.ERROR_MESSAGE, this);
				return false;
			}

			if (from.after(to)) {
				MainFrame.showMessage(
						MainFrame.properties.getProperty("from.date.after.to.date"),
						JOptionPane.ERROR_MESSAGE, this);
				return false;
			}
			double cal = 0;
			try {
				startAmount.commitEdit();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			try {
//				System.out.println(startAmount.getValue() + " :: " + startAmount.getText()  + " :: " + MainFrame.decimalFormater.parse(startAmount.getText()));
				if (!startAmount.getText().isEmpty())
						{
						if (startAmount.getValue()!=null) {
							cal = Double.valueOf(String.valueOf(startAmount.getValue()));
						}
						else if (MainFrame.decimalFormater.parse(startAmount.getText())!=null)
							cal = MainFrame.decimalFormater.parse(startAmount.getText()).doubleValue();
						}
				} catch (Exception e) { e.printStackTrace(); }

			if (startAmount.getText().trim().isEmpty() || cal == 0) {
				MainFrame.showMessage(
						MainFrame.properties.getProperty("start.amount.empty"),
						JOptionPane.ERROR_MESSAGE, this);
				return false;
			}

			if (!accountPanel.validateAccountChangeTable()) {
				accountPanel.numberCellRenderer.setPaintBackground(true);
				accountPanel.table.repaint();
				MainFrame.showMessage(
						MainFrame.properties.getProperty("account.table.invalid"),
						JOptionPane.ERROR_MESSAGE, this);
				return false;
			}

			return true;
		}

		protected void calculate_action() {
			// stop cell editing in account change table
			if (getAccountPanel().getTable().getCellEditor() != null)
				getAccountPanel().getTable().getCellEditor().stopCellEditing();

			// validate form
			if (validateForm()) {
				accountPanel.numberCellRenderer.setPaintBackground(false);
				AccountBean account =
					MainFrame.getInstance().interestCalculationPanel.readForm();
				System.out.println("Account ::" + account);
				account.calculate();
				interest.setText(
						MainFrame.decimalFormater.format(account.getTotalInterest()));
				total.setText(
						MainFrame.decimalFormater.format(account.getTotalDebt()));
			} else {
				// form invalid
				System.out.println("Form is not valid!");
			}
		}

		public void setMethodType(int type) {
			System.out.println("METOD ::" + type);
			switch (type) {
				default:
				case AccountBean.CONFORM_METHOD_TYPE: conformMethod.setSelected(true); break;
				case AccountBean.PROPORCIONAL_METHOD_TYPE: proporcionalMethod.setSelected(true); break;
				case AccountBean.MIXED_METHOD_TYPE: mixedMethod.setSelected(true); break;
				}
			}

		public int getMethodType() {
			if (proporcionalMethod.isSelected())
				return AccountBean.PROPORCIONAL_METHOD_TYPE;
			if (conformMethod.isSelected())
				return AccountBean.CONFORM_METHOD_TYPE;

			return AccountBean.MIXED_METHOD_TYPE;
		}

		public AccountBean.CALCULATION_TYPE getCalculationType() {
			if (calculationTypeAmmountFirst.isSelected())
				return AccountBean.CALCULATION_TYPE.AMMOUNT_FIRST_CALCULATION_TYPE;
			return AccountBean.CALCULATION_TYPE.INTEREST_FIRST_CALCULATION_TYPE;
		}

		public void setCalculationType(AccountBean.CALCULATION_TYPE claculationType) {

			switch (claculationType) {
			case AMMOUNT_FIRST_CALCULATION_TYPE:
				calculationTypeAmmountFirst.setSelected(true);
				return;
			case INTEREST_FIRST_CALCULATION_TYPE:
				calculationTypeInterestFirst.setSelected(true);
				return;
			}
		}

		public void setInterestType(int type) {
			switch (type) {
			case AccountBean.LAW_INTEREST_TYPE:
				lawInterestType.setSelected(true);
				break;
			case AccountBean.LAW_INTEREST_TYPE_EUR:
				lawInterestTypeEur.setSelected(true);
				break;
			case AccountBean.PRICE_GROWTH_INTEREST_TYPE:
				priceGrowthInterestType.setSelected(true);
				break;
			case AccountBean.TAX_INTEREST_TYPE:
				taxInterestType.setSelected(true);
				break;
			case AccountBean.FIXED_MONHLEY_CONTRACT_INTEREST_TYPE:
				fixedMonthleyContractInterestType.setSelected(true);
				break;
			case AccountBean.FIXED_ANUAL_CONTRACT_INTEREST_TYPE:
				fixedAnualContractInterestType.setSelected(true);
				break;
			case AccountBean.FREE_CONTRACT_INTEREST_TYPE:
				freeContractInterestType.setSelected(true);
				break;
//			case AccountBean.EXCONT_MONTHLEY_INTEREST_TYPE:
//				excontMonthleyInterestType.setSelected(true);
//				break;
			case AccountBean.ECB_INTEREST_TYPE:
				ecbInterestType.setSelected(true);
				break;
			case AccountBean.EXCONT_ANUAL_INTEREST_TYPE:
				excontAnualInterestType.setSelected(true);
				break;
			case AccountBean.REFERENT_INTEREST_TYPE:
				referentInterestType.setSelected(true);
				break;
			default:
				JOptionPane.showMessageDialog(this,
						MainFrame.properties.getProperty("nosuch.interesttype.warning.message"),
						MainFrame.properties.getProperty("messageType.warning"),
						JOptionPane.WARNING_MESSAGE, null);
				break;
			}
		}

		public int getInterestType() {
			if (lawInterestType.isSelected())
				return AccountBean.LAW_INTEREST_TYPE;
			if (lawInterestTypeEur.isSelected())
				return AccountBean.LAW_INTEREST_TYPE_EUR;
			if (priceGrowthInterestType.isSelected())
				return AccountBean.PRICE_GROWTH_INTEREST_TYPE;
			if (taxInterestType.isSelected())
				return AccountBean.TAX_INTEREST_TYPE;
			if (fixedAnualContractInterestType.isSelected())
				return AccountBean.FIXED_ANUAL_CONTRACT_INTEREST_TYPE;
			if (fixedDailyContractInterestType.isSelected())
				return AccountBean.FIXED_DAILY_CONTRACT_INTEREST_TYPE;
			if (fixedMonthleyContractInterestType.isSelected())
				return AccountBean.FIXED_MONHLEY_CONTRACT_INTEREST_TYPE;
			if (freeContractInterestType.isSelected())
				return AccountBean.FREE_CONTRACT_INTEREST_TYPE;
			if (excontAnualInterestType.isSelected())
				return AccountBean.EXCONT_ANUAL_INTEREST_TYPE;
			if (referentInterestType.isSelected())
				return AccountBean.REFERENT_INTEREST_TYPE;
			if (ecbInterestType.isSelected())
				return AccountBean.ECB_INTEREST_TYPE;
//			if (excontMonthleyInterestType.isSelected())
//				return AccountBean.EXCONT_MONTHLEY_INTEREST_TYPE;
			return AccountBean.LAW_INTEREST_TYPE; //DEFAULT
		}

		public void populate(AccountBean account) {
			setInterestType(account.getInterestType());
			setMethodType(account.getMethodType());
			if (account.getFrom() != null)
				fromDate.setText(MainFrame.sdf.format(account.getFrom()));
			if (account.getTo() != null)
				toDate.setText(MainFrame.sdf.format(account.getTo()));

			startAmount.setText(
					MainFrame.decimalFormater.format(account.getStartAmount()));
			document.setText(account.getDocument());
			if (getMethodType()!=AccountBean.MIXED_METHOD_TYPE &&
					(getInterestType()==AccountBean.TAX_INTEREST_TYPE ||
							getInterestType()==AccountBean.LAW_INTEREST_TYPE))
				popupMessage_action();
		}

		public AccountBean readForm() {
			AccountBean account = new AccountBean();
			account.setInterestType(getInterestType());
			account.setMethodType(getMethodType());
			account.setCalculationType(getCalculationType());
			try {
				account.setFrom(MainFrame.sdf.parse(fromDate.getText()));
			} catch (ParseException e) {
			}

			try {
				account.setTo(MainFrame.sdf.parse(toDate.getText()));
			} catch (ParseException e) {
			}

			try {
//				System.out.println(startAmount.getValue() + " :: " + startAmount.getText()  + " :: " + MainFrame.decimalFormater.parse(startAmount.getText()));
				if (!startAmount.getText().isEmpty())
						{
						if (startAmount.getValue()!=null) {
							account.setStartAmount(Double.valueOf(String.valueOf(startAmount.getValue())));
						}
						else if (MainFrame.decimalFormater.parse(startAmount.getText())!=null) account.setStartAmount(MainFrame.decimalFormater.parse(startAmount.getText()).doubleValue());
						}
				} catch (Exception e) { e.printStackTrace();
			}

			account.setDocument(document.getText());

			return account;
		}

		public JFormattedTextField getStartAmount() {
			return startAmount;
		}

		public void setStartAmount(JFormattedTextField startAmount) {
			this.startAmount = startAmount;
		}

		public JFormattedTextField getInterest() {
			return interest;
		}

		public void setInterest(JFormattedTextField interest) {
			this.interest = interest;
		}

		public JFormattedTextField getTotal() {
			return total;
		}

		public void setTotal(JFormattedTextField total) {
			this.total = total;
		}
	}

	class UsersPanel extends JPanel {
		private JToggleButton toggle = new JToggleButton();
		private JPanel users = new JPanel();
		private UserPanel creditor;
		private UserPanel debtor;

		/**
		 *
		 */
		public UsersPanel() {
			super();
			init();
		}

		public void clear() {
			this.creditor.clear();
			this.debtor.clear();

		}

		private void init() {
			setLayout(new BorderLayout(0, 0));

			toggle.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					users.setVisible(toggle.isSelected());
				}
			});
			toggle.setText(MainFrame.properties.getProperty("users.label"));

			users.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createBevelBorder(5)));
			users.setVisible(false);
			users.setLayout(new MigLayout("insets 0 0 0 0", "[]5mm![]", ""));
			creditor = new UserPanel(
					MainFrame.properties.getProperty("creditor.label"));
			debtor = new UserPanel(
					MainFrame.properties.getProperty("debtor.label"));
			users.add(creditor);
			users.add(debtor);

			add(ResourceManager.decorateComponent(
					toggle, "toggle.button.label"), BorderLayout.NORTH);
			add(users, BorderLayout.CENTER);
		}

		public void populate(AccountUserBean creditor, AccountUserBean debtor) {
			this.creditor.populate(creditor);
			this.debtor.populate(debtor);
		}

		public AccountBean readForm(AccountBean inBean) {
			inBean.setCreditor(this.creditor.readForm());
			inBean.setDebtor(this.debtor.readForm());
			return inBean;
		}

	}

	public class AccountPanel extends JPanel {
		private JToggleButton toggle = new JToggleButton();
		private JScrollPane scroll;
		private JTable table = new JTable();

		private DefaultTableModel model = new AccountTableModel(0, 3);
		private JPanel buttons = new JPanel();
		private JButton add = new JButton();
		// private JButton edit = new JButton();
		private JButton delete = new JButton();
		private NumberCellRenderer numberCellRenderer = new NumberCellRenderer(MainFrame.decimalFormater);

		private class AccountTableModel extends DefaultTableModel {

			public AccountTableModel(int rowCount, int columnCount) {
				super(rowCount, columnCount);
			}

			public boolean isCellEditable(int row, int column) {
				return true;
			}

			public Class getColumnClass(int col) {
				if (col == 1)
					return Double.class;
				return String.class;
			}
		}

		/**
		 *
		 */
		public AccountPanel() {
			super();
			init();
		}

		public void clear() {
			model.setRowCount(0);
		}

		private void init() {
			setLayout(new BorderLayout());

			toggle.setText(
					MainFrame.properties.getProperty("account.changes.label"));
			toggle.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// if (toggle.isSelected()) { creditor.setVisible(true);
					// debtor.setVisible(true); }
					// else { creditor.setVisible(false);
					// debtor.setVisible(false); }
					table.setVisible(toggle.isSelected());
					scroll.setVisible(toggle.isSelected());
					buttons.setVisible(toggle.isSelected());

				}
			});
			add(ResourceManager.decorateComponent(
					toggle, "toggle.button.label"), BorderLayout.NORTH);
			add.setText(MainFrame.properties.getProperty("table.add.button"));
			add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					model.addRow(new Vector<String>());
				}
			});
			// edit.setText(MainFrame.properties.getProperty("table.edit.button"));
			delete.setText(
					MainFrame.properties.getProperty("table.delete.button"));
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
			buttons.setVisible(false);
			buttons.setLayout(new GridLayout(3, 1));
			buttons.add(ResourceManager.decorateComponent(add, "table.buttons"));
			// buttons.add(ResourceManager.decorateComponent(edit, "table.buttons"));
			buttons.add(ResourceManager.decorateComponent(delete, "table.buttons"));
			JPanel buttonsBagPanel = new JPanel(new BorderLayout());
			buttonsBagPanel.add(buttons, BorderLayout.NORTH);
			add(buttonsBagPanel, BorderLayout.EAST);
			table.setVisible(false);
			table.setIntercellSpacing(new Dimension(1, 1));
			table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			//table.setCellSelectionEnabled(true);
			table.setRowSelectionAllowed(true);
			// Create our cell editor
			DatePickerTableEditor datePickerCellEditor = new DatePickerTableEditor();

			// Set the number of mouse clicks needed to activate it.
			datePickerCellEditor.setClickCountToStart(1);

			// Set it for the appropriate table column.

			model.setColumnCount(3);
			model.setColumnIdentifiers(new String[] {
					MainFrame.properties.getProperty(
							"account.panel.table.header.changeDate"),
					MainFrame.properties.getProperty(
							"account.panel.table.header.changeAmount"),
					MainFrame.properties.getProperty(
							"account.panel.table.header.document") });
			table.setRowHeight(28);
			table.setShowGrid(true);
			table.getTableHeader().setReorderingAllowed(true);

			table.setBackground(ResourceManager.getBackgroundColor("account.panel.table.background"));
			table.setSelectionBackground(ResourceManager.getBackgroundColor("account.panel.table.selected.background"));
			table.setSelectionForeground(ResourceManager.getFontColor("account.panel.table"));
			table.setGridColor(ResourceManager.getBackgroundColor("account.panel.table.grid.color"));

			table.setFillsViewportHeight(false);

			// table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF ) ;
			table.setModel(model);
			table.getColumnModel().getColumn(0).setCellEditor(datePickerCellEditor);

			VariableRatesCellEditor variableRatesCellEditor = new VariableRatesCellEditor();
		    table.getColumnModel().getColumn(1).setCellEditor(variableRatesCellEditor);

			table.getColumnModel().getColumn(0).setCellRenderer(numberCellRenderer);
			table.getColumnModel().getColumn(1).setCellRenderer(numberCellRenderer);

			scroll = new JScrollPane(ResourceManager.decorateComponent(table, "account.change.table"));
			scroll.setPreferredSize(new Dimension(480, 150));
			scroll.setVisible(false);
			add(scroll, BorderLayout.CENTER);
		}

		public void populate(List<AccountChangeBean> changes) {
			if (changes == null)
				return;
			Iterator<AccountChangeBean> it = changes.iterator();
			model.setRowCount(0);
			while (it.hasNext()) {
				AccountChangeBean accountChange = it.next();
				Vector data = new Vector();
				if (accountChange.getDate() != null)
					data.add(MainFrame.sdf.format(accountChange.getDate()));
				data.add(accountChange.getAmount());
				data.add(accountChange.getDocument());

				model.addRow(data);
			}
		}

		public List<AccountChangeBean> readForm() {
			List<AccountChangeBean> list = new ArrayList<AccountChangeBean>();
			for (int index = 0; index < table.getRowCount(); ++index) {
				AccountChangeBean accountChange = new AccountChangeBean();
				try {
					accountChange.setDate(MainFrame.sdf.parse(
							table.getValueAt(index, 0).toString()));
					accountChange.setAmount(Double.parseDouble(
							table.getValueAt(index, 1).toString()));
					if (table.getValueAt(index, 2) != null)
						accountChange.setDocument(table.getValueAt(index, 2).toString());
					list.add(accountChange);
				} catch (Exception e) { /* e.printStackTrace(); */
				}

			}
			Collections.sort(list);
			return list;
		}

		public boolean validateAccountChangeTable() {
			boolean tableValid = true;
			for (int index = 0; index < table.getRowCount(); ++index) {
				tableValid = validateRow(index);
				if (!tableValid) {
					//System.out.println("Table invalid");
					break;
				}
			}
			return tableValid;
		}

		public boolean validateRow(int index) {
			String changeDate = String.valueOf( table.getModel().getValueAt(index, 0));
			String changeAmount = String.valueOf( table.getModel().getValueAt(index, 1));

			if ((changeDate.trim().equals("null")) || (changeAmount.trim().equals("null")) ||
				(changeDate.trim().equals("")) || (changeAmount.trim().equals(""))) {
				//System.out.println("change date or amount null!!!");
				return false;
			}
			if (changeDate.trim().isEmpty() || changeAmount.trim().isEmpty()) {
				return false;
			}

			return true;
		}

		public NumberCellRenderer getNumberCellRenderer() {
			return numberCellRenderer;
		}

		public void setNumberCellRenderer(NumberCellRenderer numberCellRenderer) {
			this.numberCellRenderer = numberCellRenderer;
		}

		public JTable getTable() {
			return table;
		}

		public void setTable(JTable table) {
			this.table = table;
		}

	}
}
