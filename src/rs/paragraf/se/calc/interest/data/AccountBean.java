package rs.paragraf.se.calc.interest.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import rs.paragraf.se.calc.interest.ui.MainFrame;
import rs.paragraf.se.calc.interest.utils.RateManager;

/**
 * @author igor
 *
 */
public class AccountBean implements Serializable, Comparable<AccountBean>{

	public static final long serialVersionUID = 6000;


	public static final int LAW_INTEREST_TYPE = 1;
	public static final int PRICE_GROWTH_INTEREST_TYPE = 2;
	public static final int TAX_INTEREST_TYPE = 3;
	public static final int FREE_CONTRACT_INTEREST_TYPE = 4;
	public static final int FIXED_MONHLEY_CONTRACT_INTEREST_TYPE = 5;
	public static final int FIXED_ANUAL_CONTRACT_INTEREST_TYPE = 6;
	public static final int EXCONT_ANUAL_INTEREST_TYPE = 7;
//	public static final int EXCONT_MONTHLEY_INTEREST_TYPE = 8;
	public static final int ECB_INTEREST_TYPE = 9;
	public static final int LAW_INTEREST_TYPE_EUR =10;
	public static final int FIXED_DAILY_CONTRACT_INTEREST_TYPE=11;
	public static final int REFERENT_INTEREST_TYPE = 12;


	public enum CALCULATION_TYPE{
		AMMOUNT_FIRST_CALCULATION_TYPE, INTEREST_FIRST_CALCULATION_TYPE
	}


	public static final int PROPORCIONAL_METHOD_TYPE = 1;
	public static final int CONFORM_METHOD_TYPE = 2;
	public static final int MIXED_METHOD_TYPE = 3;

	private int methodType = PROPORCIONAL_METHOD_TYPE;
	private int interestType = LAW_INTEREST_TYPE;
	private CALCULATION_TYPE calculationType = CALCULATION_TYPE.INTEREST_FIRST_CALCULATION_TYPE;
	private Date from;
	private Date to;
	private double startAmount;
	private String document= "";
	private AccountUserBean creditor = new AccountUserBean();
	private AccountUserBean debtor = new AccountUserBean();
	private List<AccountChangeBean> changes = new ArrayList<AccountChangeBean>() ;

	// filled by calculate function
	transient private double tempBasis = 0;
	transient private double totalInterest = 0;
	transient private double totalChange = 0;
	transient private double totalDebt = 0;
	transient private List<AccountResultBean> results = null;

	public enum ValidForHtml {
		VALID_BEAN,
		INVALID_FROM_DATE,
		INVALID_TO_DATE,
		FROM_DATE_AFTER_TO_DATE,
		INVALID_START_AMOUNT,
		INVALID_CREDITOR_DATA,
		INVALID_DEBTOR_DATA,
		INVALID_ACCOUNT_CHANGE_TABLE
	};

	/**
	 * @return the interestType
	 */
	public int getInterestType() {
		return interestType;
	}

//	public String getInterestTypeByName()
//	{
//		if (getInterestType() == LAW_INTEREST_TYPE)
//			return MainFrame.properties.getProperty("law.interest.type");
//		else if (getInterestType() == PRICE_GROWTH_INTEREST_TYPE)
//			return MainFrame.properties.getProperty("price.growth.interest.type");
//		else if (getInterestType() == TAX_INTEREST_TYPE)
//			return MainFrame.properties.getProperty("tax.interest.type");
//		else if (getInterestType() == FIXED_CONTRACT_INTEREST_TYPE)
//			return MainFrame.properties.getProperty("fixed.contract.interest.type");
//		else if (getInterestType() == FREE_CONTRACT_INTEREST_TYPE)
//			return MainFrame.properties.getProperty("free.contract.interest.type");
//		else if (getInterestType() == EXCONT_INTEREST_TYPE)
//			return MainFrame.properties.getProperty("excont.interest.type");
//		else
//			return new String("");
//	}

	/**
	 * @param interestType the interestType to set
	 */
	public void setInterestType(int interestType) {
		this.interestType = interestType;
	}
	/**
	 * @return the methodType
	 */
	public int getMethodType() {
		return methodType;
	}

	/**
	 * @param methodType the methodType to set
	 */
	public void setMethodType(int methodType) {
		this.methodType = methodType;
	}

	/**
	 * @return the calculationType
	 */
	public CALCULATION_TYPE getCalculationType() {
		return calculationType;
	}

	/**
	 * @param calculationType the calculationType to set
	 */
	public void setCalculationType(CALCULATION_TYPE calculationType) {
		this.calculationType = calculationType;
	}

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
	 * @return the startAmount
	 */
	public double getStartAmount() {
		return startAmount;
	}
	/**
	 * @param startAmount the startAmount to set
	 */
	public void setStartAmount(double startAmount) {
		this.startAmount = startAmount;
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
	 * @return the user1
	 */
	public AccountUserBean getCreditor() {
		return creditor;
	}
	/**
	 * @param user1 the user1 to set
	 */
	public void setCreditor(AccountUserBean creditor) {
		this.creditor = creditor;
	}
	/**
	 * @return the user2
	 */
	public AccountUserBean getDebtor() {
		return debtor;
	}
	/**
	 * @param user2 the user2 to set
	 */
	public void setDebtor(AccountUserBean debtor) {
		this.debtor = debtor;
	}
	/**
	 * @return the changes
	 */
	public List<AccountChangeBean> getChanges() {
		return changes;
	}
	/**
	 * @param changes the changes to set
	 */
	public void setChanges(List<AccountChangeBean> changes) {
		this.changes = changes;
	}

	public void calculate()
	{
		claerResult();
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(this.getFrom());
			cal.add(Calendar.DAY_OF_YEAR, -1);
			this.tempBasis = getStartAmount();
			this.totalDebt =  getStartAmount();
			AccountResultBean previous = new AccountResultBean();
			previous.setDebt(getStartAmount());
			AccountResultBean result = _calculateEndDate(cal.getTime(), /*basis, getStartAmount()*/ previous);
			results.add(result);
			System.out.println(result);
			if (result==null) new Exception();
			this.totalChange += result.getChange();
			this.totalInterest += result.getInterest();
			this.totalDebt = result.getDebt();

			while (getTo().after(result.getTo()))
			{
				cal.setTime(result.getTo());
				//If start date and end date are the SAME and interest is 0  then it is special case of using duplicate date entries
				if (result.getFrom()==result.getTo() && result.getInterest()==0) cal.add(Calendar.DAY_OF_YEAR, -1);
				result = _calculateEndDate(cal.getTime(), /*basis,*/ result);
				if (result==null) throw new Exception();
				results.add(result);
				this.totalChange += result.getChange();
				this.totalInterest += result.getInterest();
				this.totalDebt = result.getDebt();
				System.out.println(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			claerResult();
		}
	}

	public void claerResult() {
		if (changes != null)
			{
			Iterator<AccountChangeBean> itChanges = changes.iterator();
			while (itChanges.hasNext()) itChanges.next().setProcessed(false);
			}
		totalChange = 0;
		totalDebt = 0;
		totalInterest = 0;
		results = new ArrayList<AccountResultBean>();
	}

	// validate bean for HTML preview
	public ValidForHtml validateForHtml() {
		if (getFrom() == null) {
			return ValidForHtml.INVALID_FROM_DATE;
		}
		if (getTo() == null) {
			return ValidForHtml.INVALID_TO_DATE;
		}
		if (getFrom().after(getTo())) {
			return ValidForHtml.FROM_DATE_AFTER_TO_DATE;
		}
		if (getStartAmount() == 0.0D) {
			return ValidForHtml.INVALID_START_AMOUNT;
		}
		if (! validateCreditorForHtml()) {
			return ValidForHtml.INVALID_CREDITOR_DATA;
		}
		if (! validateDebtorForHtml()) {
			return ValidForHtml.INVALID_DEBTOR_DATA;
		}
		if (! MainFrame.getInstance().getInterestCalculationPanel().getAccountPanel().validateAccountChangeTable()) {
			MainFrame.getInstance().getInterestCalculationPanel().getAccountPanel().
				getNumberCellRenderer().setPaintBackground(true);
			MainFrame.getInstance().getInterestCalculationPanel().getAccountPanel().getTable().repaint();
			return ValidForHtml.INVALID_ACCOUNT_CHANGE_TABLE;
		}

		return ValidForHtml.VALID_BEAN;

	}

	public boolean validateCreditorForHtml() {
		// all fields except phone are mandatory!
		if ((getCreditor().getName() == null) || (getCreditor().getName().trim().equals("")) ||
			(getCreditor().getAddress() == null) || (getCreditor().getAddress().trim().equals("")) ||
			(getCreditor().getCity() == null) || (getCreditor().getCity().trim().equals("")) ||
			(getCreditor().getPib() == null) || (getCreditor().getPib().trim().equals(""))) {
			return false;
		} else
			return true;
	}

	public boolean validateDebtorForHtml() {
		// all fields except phone are mandatory!
		if ((getDebtor().getName() == null) || (getDebtor().getName().trim().equals("")) ||
			(getDebtor().getAddress() == null) || (getDebtor().getAddress().trim().equals("")) ||
			(getDebtor().getCity() == null) || (getDebtor().getCity().trim().equals("")) ||
			(getDebtor().getPib() == null) || (getDebtor().getPib().trim().equals(""))) {
			return false;
		} else
			return true;
	}

//	public boolean validateAccountChangeTableForHtml() {
//		for (AccountChangeBean change : getChanges()) {
//			if (change.getDate() == null)
//				return false;
//		}
//		return true;
//	}

//	private double getBasisAmount()
//	{
//		double result = getStartAmount();
//		if (changes != null)
//		{
//		Iterator<AccountChangeBean> itChanges = changes.iterator();
//		while (itChanges.hasNext())
//				{
//					AccountChangeBean change = itChanges.next();
//					if (!change.isProcessed()) continue;
//					result += change.getAmount();
//				}
//		}
//		return result;
//	}
	
	private AccountResultBean _calculateEndDate(Date startDate, /*double ammount,*/ AccountResultBean previous)
	{

		// MIN { interestRateSegment, to, change amount date}
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		AccountResultBean result = new AccountResultBean();
		result.setFrom(cal.getTime());
		result.setTo(getTo()); // default setting to final end period
		RateBean rate = RateManager.getInstance().getRates(cal.getTime(), this.getInterestType());
//		System.out.println("Rate ::" + rate.getFrom() +"-" + rate.getTo());
		Double interest = null;
		if (rate!=null) interest = rate.getRate(this.getInterestType());
		else if (this.getInterestType()==FIXED_ANUAL_CONTRACT_INTEREST_TYPE) interest = RateManager.getInstance().getFixedAnualContractInterest();
		else if (this.getInterestType()==FIXED_MONHLEY_CONTRACT_INTEREST_TYPE) interest = RateManager.getInstance().getFixedMonthleyContractInterest();
		else if (this.getInterestType()==FIXED_DAILY_CONTRACT_INTEREST_TYPE) interest = RateManager.getInstance().getFixedDailyContractInterest();
		else
			{
			MainFrame.showMessage(MainFrame.properties.getProperty("calculation.error.message"), JOptionPane.WARNING_MESSAGE);
			return null;
			}

		if (interest!=null) { result.setInterestRate(interest);	}

		if (rate != null)
			{
			if (rate.getDenomination()!=null && rate.getDenomination().doubleValue()!=0)
				{
				if (rate.getRoundFactorDenomination()<0)
					{
//					previousDebt = previousDebt/rate.getDenomination();
					previous.setDebt(previous.getDebt()/rate.getDenomination());
					tempBasis = tempBasis/rate.getDenomination();
					totalInterest = totalInterest/rate.getDenomination();
					totalChange = totalChange/rate.getDenomination();
					totalDebt = totalDebt/rate.getDenomination();
					result.setDocument(MainFrame.properties.getProperty("denomination.table.label"));
					}
				else
					{
					double factor = Math.pow(10, rate.getRoundFactorDenomination());
//					previousDebt = Math.round(factor * previousDebt/rate.getDenomination())/factor;
					previous.setDebt(Math.round(factor * previous.getDebt()/rate.getDenomination())/factor);
					tempBasis = Math.round(factor * tempBasis/rate.getDenomination())/factor;
					totalInterest = Math.round(factor * totalInterest/rate.getDenomination())/factor;
					totalChange = Math.round(factor * totalChange/rate.getDenomination())/factor;
					totalDebt = Math.round(factor * totalDebt/rate.getDenomination())/factor;
					result.setDocument(MainFrame.properties.getProperty("denomination.table.label"));	
					}
				}
			}

		if (getMethodType()!=MIXED_METHOD_TYPE) result.setMethod(getMethodType());
		else if (rate != null) result.setMethod(rate.getRateMethod(this.getInterestType()));
						
		if (result.getMethod()==CONFORM_METHOD_TYPE) tempBasis=previous.getDebt();
		else if (previous.getMethod()==CONFORM_METHOD_TYPE)
		{ // START SPECIAL CASE SCENARIO
			//IF there is a change in method type conf->prop then special basis should apply
			// basis should be recalcuated as total - interest
			tempBasis = previous.getDebt()-totalInterest;
			
		}
//		else tempBasis=getStartAmount();
		
		result.setAmount(tempBasis);

		// BUDZ
		if (rate!=null && this.getInterestType()==LAW_INTEREST_TYPE && rate.getLawRate()!=null) 
			result.setInterestRate(rate.getLawRate());		
		
		// BUDZ II
		if (rate!=null && this.getInterestType()==LAW_INTEREST_TYPE_EUR && rate.getLawRateEuroPrint()!=null) 
			result.setInterestRate(rate.getLawRateEuroPrint());

		//		System.out.println("Rate :: " + result.getInterestRate());
		if (rate!=null && rate.getTo().before(result.getTo())) result.setTo(rate.getTo()); //if rate to date is lesser then existing end date

		if (changes != null)
				{
				Iterator<AccountChangeBean> itChanges = changes.iterator();
				boolean duplicateDateFlag = false;
				while (itChanges.hasNext())
						{
							AccountChangeBean change = itChanges.next();
							if (change.isProcessed()) continue;

							//set end Date to be just before change
							if (change.getDate().after(result.getFrom()) && !change.getDate().after(result.getTo()))
							{
								Calendar cal1 = Calendar.getInstance();
								cal1.setTime(change.getDate());
								cal1.add(Calendar.DAY_OF_YEAR, -1);
								result.setTo(cal1.getTime());
							}

							// This is a special case when there is a few changes in same day
							if (duplicateDateFlag && change.getDate().equals(result.getFrom()))
							{
								result.setTo(result.getFrom());
								result.setInterestRate(0);
								interest = (double) 0;
								result.setInterest(-1);
								break;
							}

							//Process the change
							if (!duplicateDateFlag && change.getDate().equals(result.getFrom()))
							{
								System.out.println("Change :: " + change.getAmount() + " Ammount :: " + result.getAmount() + " Debt :: " + this.totalDebt);
								if (change.getAmount()>0) tempBasis = result.getAmount()+ change.getAmount();
								else
									{

									//SWITCH calculation types
									CALCULATION_TYPE calcType = getCalculationType();
									if (this.getInterestType()==AccountBean.TAX_INTEREST_TYPE) {
										if  (rate.getTaxCalculationType()==1) calcType = CALCULATION_TYPE.INTEREST_FIRST_CALCULATION_TYPE;
										else calcType = CALCULATION_TYPE.AMMOUNT_FIRST_CALCULATION_TYPE;
									}
									
									if ( calcType==CALCULATION_TYPE.INTEREST_FIRST_CALCULATION_TYPE)
									  {
										if (Math.abs(change.getAmount())>(this.totalDebt-result.getAmount()))
										{
											tempBasis = result.getAmount() + change.getAmount() + (this.totalDebt-result.getAmount());
//											this.totalInterest=0;
										}
									  }
									else
									{
										if (Math.abs(change.getAmount())<result.getAmount())
										{
											tempBasis = result.getAmount() + change.getAmount() ;
//											this.totalInterest=0;
										}
										else
										{
											tempBasis=0;
										}
									}
//									else
//										{
//											tempBasis = result.getAmount();
//											this.totalInterest=change.getAmount() + (this.totalDebt-result.getAmount());
//										}
									}
								this.totalDebt += change.getAmount();
								result.setAmount(tempBasis);
								result.setChange(change.getAmount());
								result.setChangeDate(change.getDate());
								result.setDocument(change.getDocument());
								change.setProcessed(true);
								duplicateDateFlag=true;
//								break;
							}
							else duplicateDateFlag=false;

						}
				}

		int dayDifference = (int) ((result.getTo().getTime()-result.getFrom().getTime()+1.5 * MainFrame.milPerDay)/MainFrame.milPerDay);
		int maxDays=0;
		switch (this.getInterestType()) {
				case FIXED_MONHLEY_CONTRACT_INTEREST_TYPE:
						maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH)-cal.getActualMinimum(Calendar.DAY_OF_MONTH)+1;
						break;
				case FIXED_ANUAL_CONTRACT_INTEREST_TYPE:
						maxDays = cal.getActualMaximum(Calendar.DAY_OF_YEAR)-cal.getActualMinimum(Calendar.DAY_OF_YEAR)+1;
						break;
				case FIXED_DAILY_CONTRACT_INTEREST_TYPE:
						maxDays = 1;
						break;
				//Monthly
				case PRICE_GROWTH_INTEREST_TYPE:
//				case EXCONT_MONTHLEY_INTEREST_TYPE:
						if (rate==null) 
							{
							MainFrame.showMessage(MainFrame.properties.getProperty("calculation.error.message"), JOptionPane.WARNING_MESSAGE);
							return null;
							}
						else maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH)-cal.getActualMinimum(Calendar.DAY_OF_MONTH)+1;
						break;
				//Annual
				case LAW_INTEREST_TYPE:
				case LAW_INTEREST_TYPE_EUR:
				case ECB_INTEREST_TYPE:
				case TAX_INTEREST_TYPE:
				case EXCONT_ANUAL_INTEREST_TYPE:
				case REFERENT_INTEREST_TYPE:
					if (rate==null) 
					{
					MainFrame.showMessage(MainFrame.properties.getProperty("calculation.error.message"), JOptionPane.WARNING_MESSAGE);
					return null;
					}
						else maxDays = cal.getActualMaximum(Calendar.DAY_OF_YEAR)-cal.getActualMinimum(Calendar.DAY_OF_YEAR)+1;
						break;
				default:
//						dayDifference = (int) ((result.getTo().getTime()-result.getFrom().getTime())/(1000*60*60*24))+1;
			try {
				maxDays = (int) ((rate.getTo().getTime()-rate.getFrom().getTime()+1.5 * MainFrame.milPerDay)/MainFrame.milPerDay);
			} catch (Exception e) {
				//JOptionPane.showMessageDialog(null, "Kamata nije definisana u svim \nsegmentima trazenog intervala.\nZa datum " + MainFrame.sdf.format(startDate), "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
				MainFrame.showMessage(MainFrame.properties.getProperty("calculation.error.message"), JOptionPane.WARNING_MESSAGE);
				return  null;
			}
						break;
		}

		double k = interest/100* dayDifference/maxDays;
		System.out.println("ResultRate " + interestType + "  Day difference " + dayDifference + " ##  MaxDays " + maxDays + " ## Basis " + result.getAmount() +" ## k " +k );
//		if (methodType==CONFORM_METHOD_TYPE) 
		if (result.getMethod()==CONFORM_METHOD_TYPE)
			{
			k=Math.pow((1+interest/100),(double)dayDifference/maxDays)-1;
			System.out.println("ResultRate " + interestType + "  Day difference " + dayDifference + " ##  MaxDays " + maxDays + " ## Basis " + result.getAmount() +" ## k " +k );
			// Budzet da se zaobide problem kada se racuna kamata na unose koji su istog dana uneti
			if (result.getInterest()!=-1 && result.getAmount()>0) result.setInterest(AccountBean.roundToDecimals( result.getAmount() * k,2));
			else result.setInterest(0);
			}
		else
			{
			if (result.getInterest()!=-1 && tempBasis>0) result.setInterest(AccountBean.roundToDecimals(tempBasis * k,2));
		    else result.setInterest(0);
			}
//		System.out.println("K " + k);

		if (result.getInterest()==0) result.setDebt(previous.getDebt() + result.getChange());
		else result.setDebt(AccountBean.roundToDecimals(previous.getDebt() + result.getChange() + result.getInterest(),2));
		
		if (result.getMethod()==CONFORM_METHOD_TYPE) tempBasis=result.getDebt();

		return result;
	}

	public static double roundToDecimals(double d, int c) {
		long temp=(long)(d*Math.pow(10,c)+0.5);
		return (double)((temp)/Math.pow(10,c));
		}

	/**
	 * @return the totalInterest
	 */
	public double getTotalInterest() {
		return totalInterest;
	}
	/**
	 * @param totalInterest the totalInterest to set
	 */
	public void setTotalInterest(double totalInterest) {
		this.totalInterest = totalInterest;
	}
	/**
	 * @return the totalChange
	 */
	public double getTotalChange() {
		return totalChange;
	}
	/**
	 * @param totalChange the totalChange to set
	 */
	public void setTotalChange(double totalChange) {
		this.totalChange = totalChange;
	}
	/**
	 * @return the totalDebt
	 */
	public double getTotalDebt() {
		return totalDebt;
	}
	/**
	 * @param totalDebt the totalDebt to set
	 */
	public void setTotalDebt(double totalDebt) {
		this.totalDebt = totalDebt;
	}
	/**
	 * @return the results
	 */
	public List<AccountResultBean> getResults() {
		return results;
	}
	/**
	 * @param results the results to set
	 */
	public void setResults(List<AccountResultBean> results) {
		this.results = results;
	}

	public int compareTo(AccountBean account) {

		if (getFrom()!= null) {if (!getFrom().equals(account.getFrom())) return getFrom().compareTo(account.getFrom());}
		else if (account.getFrom()!=null) return 1;
		if (getMethodType()!= account.getMethodType()) return new Integer(getMethodType()).compareTo(new Integer(account.getMethodType())) ;
		if (getInterestType()!= account.getInterestType()) return new Integer(getInterestType()).compareTo(new Integer(account.getInterestType())) ;
		if (getTo()!= null) {if (!getTo().equals(account.getTo())) return getTo().compareTo(account.getTo());}
		else if (account.getTo()!=null) return 1;		
		if (getStartAmount()!=account.getStartAmount()) return  new Double(getStartAmount()).compareTo(new Double(account.getStartAmount())) ;
		if (getDocument().equals(account.getDocument())) return getDocument().compareTo(account.getDocument());
		if (!getCreditor().equals(account.getCreditor())) return getCreditor().compareTo(account.getCreditor());
		if (!getDebtor().equals(account.getDebtor())) return getDebtor().compareTo(account.getDebtor());

		if (changes!=null)
			{
			if (account==null) return -1;
			if (!getChanges().equals(account.getChanges()))
				{
				List<AccountChangeBean> result = new ArrayList<AccountChangeBean>(account.getChanges());
				if (!result.containsAll(getChanges())) return -1;
				result.removeAll(getChanges());
				if (result.size()>0) return 1;
				}
			}
		else if (account.getChanges()!=null) return 1;
		return 0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof  AccountBean)) return false;
		AccountBean account = (AccountBean) obj;
		if (getFrom()!= null) {if (!getFrom().equals(account.getFrom())) return false;}
		else if (account.getFrom()!=null) return false;
		if (getMethodType()!= account.getMethodType()) return false ;
		if (getInterestType()!= account.getInterestType()) return false ;
		if (getTo()!= null) {if (!getTo().equals(account.getTo())) return false;}
		else if (account.getTo()!=null) return false;
		if (getStartAmount()!=account.getStartAmount()) return  false ;
		if (!getDocument().equals(account.getDocument())) return false;
		if (getCreditor()!= null) {if (!getCreditor().equals(account.getCreditor())) return false;}
		else if (account.getCreditor()!=null) return false;
		if (getDebtor()!= null) {if (!getDebtor().equals(account.getDebtor())) return false;}
		else if (account.getDebtor()!=null) return false;
		if (getChanges()!=null)
			{
			if (account==null) return false;
			if (!getChanges().equals(account.getChanges())) return false;
			}
		else if (account.getChanges()!=null) return false;
		return true;
	}



}
