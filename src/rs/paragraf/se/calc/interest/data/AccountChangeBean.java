package rs.paragraf.se.calc.interest.data;

import java.io.Serializable;
import java.util.Date;

public class AccountChangeBean implements Serializable,
		Comparable<AccountChangeBean> {
	public static final long serialVersionUID = 6001L;
	private Date date;
	private double amount;
	private String document = "";
	private boolean processed = false;

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the document
	 */
	public String getDocument() {
		return document;
	}

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument(String document) {
		this.document = document;
	}

	public int compareTo(AccountChangeBean change) {
		if (getDate() != null) {
			if (!getDate().equals(change.getDate()))
				return getDate().compareTo(change.getDate());
		} else if (change.getDate() != null)
			return 1;
		if (getAmount() != change.getAmount())
			return new Double(getAmount()).compareTo(new Double(change
					.getAmount()));
		;
		if (!getDocument().equals(change.getDocument()))
			return getDocument().compareTo(change.getDocument());
		return 0;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AccountChangeBean))
			return false;
		AccountChangeBean change = (AccountChangeBean) obj;
		if (getDate() != null) {
			if (!getDate().equals(change.getDate()))
				return false;
		} else if (change.getDate() != null)
			return false;
		if (getAmount() != change.getAmount())
			return false;
		if (!getDocument().equals(change.getDocument()))
			return false;

		return true;
	}

	/**
	 * @param processed
	 *            the processed to set
	 */
	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	/**
	 * @return the processed
	 */
	public boolean isProcessed() {
		return processed;
	}

}