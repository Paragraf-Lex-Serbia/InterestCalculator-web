package rs.paragraf.se.calc.interest.data;

import java.io.Serializable;

public class ConfigBean implements Serializable {
	public static final long serialVersionUID = 6005L;
	private AccountUserBean user = new AccountUserBean();
	private Character delimiter = Character.valueOf(',');
	private boolean mandatoryUser = false;

	public ConfigBean() {

	}

	public void setUser(AccountUserBean user) {
		this.user = user;
	}

	public AccountUserBean getUser() {
		return user;
	}

	public void setDelimiter(Character delimiter) {
		this.delimiter = delimiter;
	}

	public Character getDelimiter() {
		return delimiter;
	}

	/**
	 * @param mandatoryUser
	 *            the mandatoryUser to set
	 */
	public void setMandatoryUser(boolean mandatoryUser) {
		this.mandatoryUser = mandatoryUser;
	}

	/**
	 * @return the mandatoryUser
	 */
	public boolean isMandatoryUser() {
		return mandatoryUser;
	}

}