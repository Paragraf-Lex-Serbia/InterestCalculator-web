package rs.paragraf.se.calc.interest.data;

import java.io.Serializable;

public class AccountUserBean implements Serializable,
		Comparable<AccountUserBean> {
	public static final long serialVersionUID = 6003L;
	private String name = "";
	private String address = "";
	private String city = "";
	private String pib = "";
	private String phone = "";

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the pib
	 */
	public String getPib() {
		return pib;
	}

	/**
	 * @param pib
	 *            the pib to set
	 */
	public void setPib(String pib) {
		this.pib = pib;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int compareTo(AccountUserBean user) {
		if (!getName().equals(user.getName()))
			return getName().compareTo(user.getName());
		if (!getAddress().equals(user.getAddress()))
			return getAddress().compareTo(user.getAddress());
		if (!getCity().equals(user.getCity()))
			return getCity().compareTo(user.getCity());
		if (!getPib().equals(user.getPib()))
			return getPib().compareTo(user.getPib());
		if (!getPhone().equals(user.getPhone()))
			return getPhone().compareTo(user.getPhone());
		return 0;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AccountUserBean))
			return false;
		AccountUserBean user = (AccountUserBean) obj;
		if (!getName().equals(user.getName()))
			return false;
		if (!getAddress().equals(user.getAddress()))
			return false;
		if (!getCity().equals(user.getCity()))
			return false;
		if (!getPib().equals(user.getPib()))
			return false;
		if (!getPhone().equals(user.getPhone()))
			return false;

		return true;
	}

}