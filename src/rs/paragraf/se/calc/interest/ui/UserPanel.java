package rs.paragraf.se.calc.interest.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import rs.paragraf.se.calc.interest.data.AccountUserBean;
import rs.paragraf.se.calc.interest.data.ConfigBean;
import rs.paragraf.se.calc.interest.utils.ResourceManager;

class UserPanel extends JPanel {
	private JLabel title = new JLabel();
	private JRadioButton usePersonalData = new JRadioButton();
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

	/**
	 *
	 */
	public UserPanel(String label) {
		super();
		title.setText(label);
		init();
	}

	private void init() {
		setLayout(new MigLayout("insets 0 0 0 0", "", ""));

		nameLabel.setText(MainFrame.properties.getProperty("name.user.field"));
		addressLabel.setText(MainFrame.properties
				.getProperty("address.user.field"));
		cityLabel.setText(MainFrame.properties.getProperty("city.user.field"));
		pibLabel.setText(MainFrame.properties.getProperty("pib.user.field"));
		phoneLabel
				.setText(MainFrame.properties.getProperty("phone.user.field"));
		usePersonalData.setText(MainFrame.properties
				.getProperty("personal.data.field"));

		JPanel firstLinePanel = new JPanel(new MigLayout("insets 0 0 0 0", "",
				""));
		firstLinePanel.add(ResourceManager.decorateComponent(title,
				"users.label"));
		firstLinePanel.add(ResourceManager.decorateComponent(usePersonalData,
				"users.label"));

		JPanel userDataPanel = new JPanel();
		MigLayout layout = new MigLayout("insets 0 0 0 0", "[]5mm![]",
				"[]2mm![]2mm![]2mm![]2mm![]");
		userDataPanel.setLayout(layout);
		userDataPanel.add(ResourceManager.decorateComponent(nameLabel,
				"users.data.label"));
		userDataPanel.add(
				ResourceManager.decorateComponent(name, "users.data"),
				"w 50mm!, h 7mm!, wrap");
		userDataPanel.add(ResourceManager.decorateComponent(addressLabel,
				"users.data.label"), "");
		userDataPanel.add(
				ResourceManager.decorateComponent(address, "users.data"),
				"w 50mm!, h 7mm!, wrap");
		userDataPanel.add(ResourceManager.decorateComponent(cityLabel,
				"users.data.label"), "");
		userDataPanel.add(
				ResourceManager.decorateComponent(city, "users.data"),
				"w 50mm!, h 7mm!, wrap");
		userDataPanel
				.add(ResourceManager.decorateComponent(pibLabel,
						"users.data.label"), "");
		userDataPanel.add(ResourceManager.decorateComponent(pib, "users.data"),
				"w 50mm!, h 7mm!, wrap");
		userDataPanel.add(ResourceManager.decorateComponent(phoneLabel,
				"users.data.label"), "");
		userDataPanel.add(
				ResourceManager.decorateComponent(phone, "users.data"),
				"w 50mm!, h 7mm!, wrap");

		add(firstLinePanel, "wrap");
		add(userDataPanel);

		usePersonalData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (usePersonalData.isSelected()) {
					// TODO Auto-generated method stub
					ConfigBean user = MainFrame.getInstance().getConfigPanel()
							.deserializeDataFromFile();
					name.setText(user.getUser().getName());
					address.setText(user.getUser().getAddress());
					city.setText(user.getUser().getCity());
					pib.setText(user.getUser().getPib());
					phone.setText(user.getUser().getPhone());
				} else {
					name.setText("");
					address.setText("");
					city.setText("");
					pib.setText("");
					phone.setText("");
				}
			}
		});
	}

	public void populate(AccountUserBean bean) {
		if (bean == null)
			return;
		name.setText(bean.getName());
		address.setText(bean.getAddress());
		city.setText(bean.getCity());
		pib.setText(bean.getPib());
		phone.setText(bean.getPhone());
	}

	public AccountUserBean readForm() {
		AccountUserBean user = new AccountUserBean();
		user.setName(name.getText());
		user.setAddress(address.getText());
		user.setCity(city.getText());
		user.setPib(pib.getText());
		user.setPhone(phone.getText());
		return user;
	}

	public void clear() {
		name.setText("");
		address.setText("");
		city.setText("");
		pib.setText("");
		phone.setText("");
		usePersonalData.setSelected(false);
	}

}