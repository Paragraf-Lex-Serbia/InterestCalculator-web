package rs.paragraf.se.calc.interest.data;

import java.util.Date;

public class AccountResultBean {

	public static final long serialVersionUID = 6002;

	private Date from = null;
	private Date to = null;
	private double method = 0;
	private double amount = 0;
	private double interestRate = 0;
	private double interest = 0;
	private double change = 0;
	private String document = null;
	private Date changeDate = null;
	private double debt = 0;

	/**
	 * @return the from
	 */
public Date getFrom() {
	return from;
}
/**
 * @param from the from to set
 */
public void setFrom(Date from) {
	this.from = from;
}
/**
 * @return the to
 */
public Date getTo() {
	return to;
}
/**
 * @param to the to to set
 */
public void setTo(Date to) {
	this.to = to;
}
/**
 * @return the amount
 */
public double getAmount() {
	return amount;
}
/**
 * @param amount the amount to set
 */
public void setAmount(double amount) {
	this.amount = amount;
}
/**
 * @return the interestRate
 */
public double getInterestRate() {
	return interestRate;
}
/**
 * @param interestRate the interestRate to set
 */
public void setInterestRate(double interestRate) {
	this.interestRate = interestRate;
}
/**
 * @return the interest
 */
public double getInterest() {
	return interest;
}
/**
 * @param interest the interest to set
 */
public void setInterest(double interest) {
	this.interest = interest;
}
/**
 * @return the change
 */
public double getChange() {
	return change;
}
/**
 * @param change the change to set
 */
public void setChange(double change) {
	this.change = change;
}
/**
 * @return the document
 */
public String getDocument() {
	return document;
}
/**
 * @param document the document to set
 */
public void setDocument(String document) {
	this.document = document;
}
/**
 * @return the changeDate
 */
public Date getChangeDate() {
	return changeDate;
}
/**
 * @param changeDate the changeDate to set
 */
public void setChangeDate(Date changeDate) {
	this.changeDate = changeDate;
}
/**
 * @return the debt
 */
public double getDebt() {
	return debt;
}
/**
 * @param debt the debt to set
 */
public void setDebt(double debt) {
	this.debt = debt;
}
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	StringBuffer buffer = new StringBuffer();
	buffer.append(from +"\t");
	buffer.append(to +"\t");
	buffer.append(amount +"\t");
	buffer.append(interestRate +"\t");
	buffer.append(interest +"\t");
	buffer.append(change +"\t");
	buffer.append(changeDate +"\t");
	buffer.append(debt + "\n");

	return buffer.toString();
}
/**
 * @param method the method to set
 */
public void setMethod(double method) {
	this.method = method;
}
/**
 * @return the method
 */
public double getMethod() {
	return method;
}




}
