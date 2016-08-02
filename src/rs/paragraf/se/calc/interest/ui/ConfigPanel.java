package rs.paragraf.se.calc.interest.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import net.miginfocom.swing.MigLayout;
import rs.paragraf.se.calc.interest.data.AccountUserBean;
import rs.paragraf.se.calc.interest.data.ConfigBean;
import rs.paragraf.se.calc.interest.utils.ResourceManager;

public class ConfigPanel extends JPanel {
	private JLabel title = new JLabel();
	private JLabel nameLabel = new JLabel();
	private JTextField name = new JTextField();
	private JLabel addressLabel = new JLabel();
	private JTextField address = new JTextField();
	private JLabel cityLabel = new JLabel();
	private JTextField city = new JTextField();
	private JLabel pibLabel = new JLabel();
	private JTextField pib = new JTextField();
	private JLabel phoneLabel = new JLabel();
	private JTextField phone = new JTextField();

	private JLabel numberDelimiter = new JLabel();
	private JRadioButton pointDelimiter = new JRadioButton();
	private JRadioButton commaDelimiter = new JRadioButton();
	private JLabel saveConfigWarningLabel = new JLabel();
	private JCheckBox useUserConfigCheckBox = new JCheckBox();

	private JButton saveConfig = new JButton();

	/**
	 *
	 */
	public ConfigPanel(String label) {
		super();
		title.setText(label);
		init();
	}

	private void init() {
		// setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(3)));
		setLayout(new MigLayout("insets 15 30 30 30", "[]25mm![]", ""));
		setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createBevelBorder(3)));
		setPreferredSize(new Dimension(260, 170));
		// setMinimumSize(new Dimension(260,150));
		nameLabel.setText(MainFrame.properties.getProperty("name.user.field"));
		addressLabel.setText(MainFrame.properties
				.getProperty("address.user.field"));
		cityLabel.setText(MainFrame.properties.getProperty("city.user.field"));
		pibLabel.setText(MainFrame.properties.getProperty("pib.user.field"));
		phoneLabel
				.setText(MainFrame.properties.getProperty("phone.user.field"));
		numberDelimiter.setText(MainFrame.properties
				.getProperty("number.delimiter.field"));
		pointDelimiter.setText(MainFrame.properties
				.getProperty("number.delimiter.point"));
		// pointDelimiter.addMouseListener(new MouseListener() {
		//
		// public void mouseReleased(MouseEvent e) {
		// // TODO Auto-generated method stub
		// //onDelimiterChangeAction();
		// }
		//
		// public void mousePressed(MouseEvent e) {
		// // TODO Auto-generated method stub
		// }
		//
		// public void mouseExited(MouseEvent e) {
		// // TODO Auto-generated method stub
		// }
		//
		// public void mouseEntered(MouseEvent e) {
		// // TODO Auto-generated method stub
		// }
		//
		// public void mouseClicked(MouseEvent e) {
		// // TODO Auto-generated method stub
		// onDelimiterChangeAction();
		// }
		// });
		// pointDelimiter.addChangeListener(new ChangeListener() {
		// public void stateChanged(ChangeEvent e) {
		// // TODO Auto-generated method stub
		// //onDelimiterChangeAction();
		// // change decimal and rate formatter
		// MainFrame.setCommaAsDecimalDelimiter(commaDelimiter.isSelected());
		// }
		// });

		commaDelimiter.setText(MainFrame.properties
				.getProperty("number.delimiter.comma"));
		// commaDelimiter.addMouseListener(new MouseListener() {
		//
		// public void mouseReleased(MouseEvent e) {
		// // TODO Auto-generated method stub
		// //onDelimiterChangeAction();
		// }
		//
		// public void mousePressed(MouseEvent e) {
		// // TODO Auto-generated method stub
		// }
		//
		// public void mouseExited(MouseEvent e) {
		// // TODO Auto-generated method stub
		// }
		//
		// public void mouseEntered(MouseEvent e) {
		// // TODO Auto-generated method stub
		// }
		//
		// public void mouseClicked(MouseEvent e) {
		// // TODO Auto-generated method stub
		// onDelimiterChangeAction();
		// }
		// });
		// commaDelimiter.addChangeListener(new ChangeListener() {
		// public void stateChanged(ChangeEvent e) {
		// // TODO Auto-generated method stub
		// // change decimal and rate formatter
		// MainFrame.setCommaAsDecimalDelimiter(commaDelimiter.isSelected());
		// }
		// });

		useUserConfigCheckBox.setText(MainFrame.properties
				.getProperty("save.config.is.user.mandadory"));
		saveConfigWarningLabel.setText(MainFrame.properties
				.getProperty("save.config.warning.label"));

		saveConfig.setText(MainFrame.properties
				.getProperty("save.config.label"));
		saveConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				serializeDataToFile();
				// MainFrame.getInstance().closing_action();
			}
		});

		JPanel userDataPanel = new JPanel();
		MigLayout layout = new MigLayout("fillx, filly, insets 15 30 15 30",
				"[]5mm![grow, fill]", "[]7mm![]7mm![]7mm![]7mm![]");
		userDataPanel.setLayout(layout);
		userDataPanel.add(ResourceManager.decorateComponent(nameLabel,
				"conf.panel.users.label"), "");
		userDataPanel.add(ResourceManager.decorateComponent(name,
				"conf.panel.users.label"), "w 60mm!, h 7mm!, wrap");
		userDataPanel.add(ResourceManager.decorateComponent(addressLabel,
				"conf.panel.users.label"), "");
		userDataPanel.add(ResourceManager.decorateComponent(address,
				"conf.panel.users.label"), "w 60mm!, h 7mm!, wrap");
		userDataPanel.add(ResourceManager.decorateComponent(cityLabel,
				"conf.panel.users.label"), "");
		userDataPanel.add(ResourceManager.decorateComponent(city,
				"conf.panel.users.label"), "w 60mm!, h 7mm!, wrap");
		userDataPanel.add(ResourceManager.decorateComponent(pibLabel,
				"conf.panel.users.label"), "");
		userDataPanel.add(ResourceManager.decorateComponent(pib,
				"conf.panel.users.label"), "w 60mm!, h 7mm!, wrap");
		userDataPanel.add(ResourceManager.decorateComponent(phoneLabel,
				"conf.panel.users.label"), "");
		userDataPanel.add(ResourceManager.decorateComponent(phone,
				"conf.panel.users.label"), "w 60mm!, h 7mm!, wrap");
		userDataPanel.setBorder(BorderFactory.createTitledBorder(
				new LineBorder(new Color(Integer.parseInt(MainFrame.properties
						.getProperty("config.user.panel.border.color"))), 1),
				MainFrame.properties.getProperty("config.user.panel.title")));

		JPanel delimiterPanel = new JPanel();
		MigLayout layoutDelimiter = new MigLayout("insets 15 30 15 30",
				"[]3mm![]", "[]3mm![]5mm![]");
		delimiterPanel.setLayout(layoutDelimiter);
		delimiterPanel.add(ResourceManager.decorateComponent(numberDelimiter,
				"conf.panel.users.label"), "h 7mm!, span 2, wrap");
		delimiterPanel.add(ResourceManager.decorateComponent(pointDelimiter,
				"conf.panel.users.label"), "h 7mm!");
		delimiterPanel.add(ResourceManager.decorateComponent(commaDelimiter,
				"conf.panel.users.label"), "h 7mm!, wrap");
		delimiterPanel.add(ResourceManager.decorateComponent(
				useUserConfigCheckBox, "conf.panel.users.label"),
				"span 2, wrap");
		delimiterPanel.add(ResourceManager.decorateComponent(
				saveConfigWarningLabel, "conf.panel.users.label"), "span 2");

		ButtonGroup group = new ButtonGroup();
		group.add(pointDelimiter);
		group.add(commaDelimiter);

		JPanel savePanel = new JPanel();
		MigLayout layoutSaveConfig = new MigLayout("", "", "");
		savePanel.setLayout(layoutSaveConfig);
		savePanel.add(ResourceManager.decorateComponent(saveConfig,
				"conf.panel.users.label"));

		add(userDataPanel, "center");
		add(delimiterPanel, "top, wrap");
		add(savePanel, "span 2, alignx center");
	}

	public void populate(ConfigBean bean) {
		if (bean == null)
			return;
		name.setText(bean.getUser().getName());
		address.setText(bean.getUser().getAddress());
		city.setText(bean.getUser().getCity());
		pib.setText(bean.getUser().getPib());
		phone.setText(bean.getUser().getPhone());
		if (bean.getDelimiter().equals(',')) {
			commaDelimiter.setSelected(true);
		} else {
			pointDelimiter.setSelected(true);
		}
		useUserConfigCheckBox.setSelected(bean.isMandatoryUser());
	}

	// public void onDelimiterChangeAction() {
	// System.out.println("On delimiter change action...");
	//
	// AccountBean account =
	// MainFrame.getInstance().getInterestCalculationPanel().readForm();
	//
	// MainFrame.setCommaAsDecimalDelimiter(commaDelimiter.isSelected());
	//
	// MainFrame.getInstance().getInterestCalculationPanel().setFormaterFactory();
	// MainFrame.getInstance().getRateOverviewPanel().getVariableRatesPane().setFormaterFactory();
	//
	// MainFrame.getInstance().getInterestCalculationPanel().populate(account);
	// MainFrame.getInstance().getRateOverviewPanel().getVariableRatesPane().initVariableData();
	// }

	public ConfigBean readForm() {
		ConfigBean user = new ConfigBean();
		user.getUser().setName(name.getText());
		user.getUser().setAddress(address.getText());
		user.getUser().setCity(city.getText());
		user.getUser().setPib(pib.getText());
		user.getUser().setPhone(phone.getText());
		if (commaDelimiter.isSelected())
			user.setDelimiter(',');
		else
			user.setDelimiter('.');
		return user;
	}

	public void clear() {
		name.setText("");
		address.setText("");
		city.setText("");
		pib.setText("");
		phone.setText("");
	}

	public void serializeDataToFile() {

		ObjectOutput output = null;
		try {
			OutputStream file = new FileOutputStream(MainFrame.USER_PATH
					+ "/picalc/conf/userData.cfg");
			OutputStream buffer = new BufferedOutputStream(file);
			output = new ObjectOutputStream(buffer);

			ConfigBean userBean = new ConfigBean();
			userBean.getUser().setName(name.getText());
			userBean.getUser().setAddress(address.getText());
			userBean.getUser().setCity(city.getText());
			userBean.getUser().setPib(pib.getText());
			userBean.getUser().setPhone(phone.getText());
			if (commaDelimiter.isSelected())
				userBean.setDelimiter(',');
			else
				userBean.setDelimiter('.');
			userBean.setMandatoryUser(useUserConfigCheckBox.isSelected());
			output.writeObject(userBean);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@SuppressWarnings("finally")
	public ConfigBean deserializeDataFromFile() {
		ConfigBean user = new ConfigBean();
		if (!new File(MainFrame.USER_PATH + "/picalc/conf/userData.cfg")
				.exists())
			return user;
		else {
			// deserialize the userData.ser file
			try {
				// use buffering
				InputStream file = new FileInputStream(MainFrame.USER_PATH
						+ "/picalc/conf/userData.cfg");
				InputStream buffer = new BufferedInputStream(file);
				ObjectInput input = new ObjectInputStream(buffer);
				try {
					// user.setName((String) input.readObject());
					// user.setAddress((String) input.readObject());
					// user.setCity((String) input.readObject());
					// user.setPib((String) input.readObject());
					// user.setPhone((String) input.readObject());
					user = (ConfigBean) input.readObject();
				} finally {
					input.close();
				}
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				return user;
			}
		}
	}

	public boolean isUsingUserConfing() {
		return useUserConfigCheckBox.isSelected();
	}
}