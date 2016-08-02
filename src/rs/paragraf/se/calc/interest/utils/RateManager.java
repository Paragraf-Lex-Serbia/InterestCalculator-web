package rs.paragraf.se.calc.interest.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import rs.paragraf.se.calc.interest.data.AccountBean;
import rs.paragraf.se.calc.interest.data.RateBean;
import rs.paragraf.se.calc.interest.ui.MainFrame;

/**
 * @author igor
 *
 */
public class RateManager {
//		private static final int FIXED_DEFAULT_VALUE=-1;
		private SortedSet<RateBean> fixedRates = new TreeSet<RateBean>();
		private SortedSet<RateBean> contractRates = new TreeSet<RateBean>();

		private double fixedAnualContractInterest ;
		private double fixedMonthleyContractInterest ;
		private double fixedDailyContractInterest ;
		private String message= "";

		private static RateManager instance = null;
		private RateManager()
		{
			load();
		}

		public static RateManager getInstance()
		{
			if (instance==null) instance=new RateManager();
			return instance;
		}

		public static void clear()
		{
			instance = null;
		}

		public void load()
		{
			try {
	//			BufferedReader reader = new BufferedReader( new FileReader(new File(MainFrame.RUNTIME_PATH + "conf/fixedInterest.dat")));
			BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(MainFrame.RUNTIME_PATH + "fixedInterest.dat"), "utf8"));
	            setMessage(reader.readLine());
			    String line;
			    while ((line = reader.readLine() )!= null)
			    {

			    	if (line.startsWith("#")) continue;
			    	String[] fields = line.split(";");
			    	if (fields.length<16) continue;
			    	RateBean rate = new RateBean();
			    	try {
						rate.setFrom(MainFrame.sdf.parse(fields[0].trim()));
						rate.setTo(MainFrame.sdf.parse(fields[1].trim()));
						try {
							rate.setTaxCalculationType(Double.valueOf(fields[2].trim()));
						} catch (NumberFormatException e) { /*Ignoring field if it is not a parsable Decimal value*/}
						try {
							rate.setLawRateMethod(Double.valueOf(fields[3].trim()));
						} catch (NumberFormatException e) { /*Ignoring field if it is not a parsable Decimal value*/}
						try {
							rate.setTaxRateMethod(Double.valueOf(fields[4].trim()));
						} catch (NumberFormatException e) { /*Ignoring field if it is not a parsable Decimal value*/}
						try {
							rate.setDenomination(Double.valueOf(fields[5].trim()));
						} catch (NumberFormatException e) { /*Ignoring field if it is not a parsable Decimal value*/}
						try {
							rate.setRoundFactorDenomination(Integer.valueOf(fields[6].trim()));
						} catch (NumberFormatException e) { rate.setRoundFactorDenomination(-1);}
						try {
							rate.setLawRatePrint(Double.valueOf(fields[7].trim()));
						} catch (NumberFormatException e) { /*Ignoring field if it is not a parsable Decimal value*/}
						try {
							rate.setLawRate(Double.valueOf(fields[8].trim()));
						} catch (NumberFormatException e) { /*Ignoring field if it is not a parsable Decimal value*/}
						try {
							rate.setLawRateEuroPrint(Double.valueOf(fields[9].trim()));
						} catch (NumberFormatException e) { /*Ignoring field if it is not a parsable Decimal value*/}
						try {
							rate.setLawRateEuro(Double.valueOf(fields[10].trim()));
						} catch (NumberFormatException e) { /*Ignoring field if it is not a parsable Decimal value*/}
						try {
							rate.setPriceGrowthRate(Double.valueOf(fields[11].trim()));
						} catch (NumberFormatException e) { /*Ignoring field if it is not a parsable Decimal value*/}
						try {
							rate.setTaxYRate(Double.valueOf(fields[12].trim()));
						} catch (NumberFormatException e) { /*Ignoring field if it is not a parsable Decimal value*/}
						try {
							rate.setEcbRate(Double.valueOf(fields[13].trim()));
						} catch (NumberFormatException e) { /*Ignoring field if it is not a parsable Decimal value*/}
						try {
							rate.setExcontYRate(Double.valueOf(fields[14].trim()));
						} catch (NumberFormatException e) { /*Ignoring field if it is not a parsable Decimal value*/}
						try {
							rate.setReferentRate(Double.valueOf(fields[15].trim()));
						} catch (NumberFormatException e) { /*Ignoring field if it is not a parsable Decimal value*/}
						fixedRates.add(rate);
					} catch (ParseException e) { e.printStackTrace();	}

			    }
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
			File file = new File(MainFrame.USER_PATH + "/picalc/conf/contractInterest.dat");
				if (file.exists())
				{
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(MainFrame.USER_PATH + "/picalc/conf/contractInterest.dat"), "utf8"));
	//				InputStreamReader reader = new InputStreamReader(new FileInputStream(MainFrame.RUNTIME_PATH + "conf/fixedInterest.dat"),"utf8");
				    String line = reader.readLine() ;
				    String[] fixedInterest = line.split(";");
				    if (fixedInterest.length>= 2)
				    {
				    	try {
							fixedAnualContractInterest = Double.valueOf(fixedInterest[0]);
							fixedMonthleyContractInterest = Double.valueOf(fixedInterest[1]);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    }
				    if (fixedInterest.length>=3) fixedDailyContractInterest = Double.valueOf(fixedInterest[2]);
				    else fixedDailyContractInterest=0; // to keep it safe from old config files

				    while ((line = reader.readLine() )!= null)
				    {

				    	if (line.startsWith("#")) continue;
				    	String[] fields = line.split(";");
				    	if (fields.length<3) continue;
				    	RateBean rate = new RateBean();
				    	try {
							rate.setFrom(MainFrame.sdf.parse(fields[0].trim()));
							rate.setTo(MainFrame.sdf.parse(fields[1].trim()));
							rate.setFreeContractRate(Double.valueOf(fields[2].trim()));
							contractRates.add(rate);
						} catch (ParseException e) { e.printStackTrace();	}

				    }
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void save()
		{
			try {
			File file = new File(MainFrame.USER_PATH + "/picalc/conf/contractInterest.dat");
				if (!file.exists()) file.createNewFile();
				BufferedWriter writer = new BufferedWriter( new FileWriter(file));
				Iterator<RateBean> it = contractRates.iterator();
				writer.write(fixedAnualContractInterest + ";" +  fixedMonthleyContractInterest  + ";" +  fixedDailyContractInterest + "\n");
				while (it.hasNext())
					{
					RateBean rate = it.next();
					writer.write(MainFrame.sdf.format(rate.getFrom()) + ";" +  MainFrame.sdf.format(rate.getTo())+ ";" +rate.getFreeContractRate() +"\n");
					}
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/**
		 * @return the contractRates
		 */
		public SortedSet<RateBean> getContractRates() {
			return contractRates;
		}

		/**
		 * @param contractRates the contractRates to set
		 */
		public void setContractRates(SortedSet<RateBean> contractRates) {
			this.contractRates = contractRates;
		}

		/**
		 * @param rate the RateBean to be appended
		 */
		public void addContractRate(RateBean rate) {
			this.contractRates.add(rate);
		}

		/**
		 * @return the rates
		 */
		public Set<RateBean> getFixedRates() {
			return fixedRates;
		}

		private boolean _equalRates(RateBean left, RateBean right, int interestType)
		{
			switch (interestType)
			{
			case AccountBean.LAW_INTEREST_TYPE:	return left.getLawRatePrint().equals(right.getLawRatePrint());
			case AccountBean.LAW_INTEREST_TYPE_EUR:	return left.getLawRateEuroPrint().equals(right.getLawRateEuroPrint());
			default: return left.getRate(interestType).equals(right.getRate(interestType));
			}
		}

		public RateBean getRates(Date date, int interestType)
		{
			switch (interestType)
			{
			default:
					{
					Iterator<RateBean> it = fixedRates.iterator();
					RateBean rate = null;
					RateBean result = null;
					RateBean previousRate = null;
					while (it.hasNext())
						{
							rate = it.next();
							if (rate.getRate(interestType)==null) continue;
							//sum up the rates with same value to form a larger block
							if (result ==null && date.before(rate.getFrom()))
								{
								result=previousRate;
								if (result==null) return null;
								}
							if (result!=null)
							{
	//							if(date.before(rate.getFrom()) && _equalRates(result, rate, interestType)) {
	//								if (interestType==AccountBean.LAW_INTEREST_TYPE )  {
	//									int dayDifference = (int) ((rate.getTo().getTime()-rate.getFrom().getTime()+1.5 * MainFrame.milPerDay)/MainFrame.milPerDay);
	//									Calendar cal = Calendar.getInstance();
	//									cal.setTime(rate.getFrom());
	//									int maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH)-cal.getActualMinimum(Calendar.DAY_OF_MONTH)+1;
	//									result.setLawRate(result.getLawRate()+(rate.getLawRate()*dayDifference/maxDays));
	//								}
	//							    if ( interestType==AccountBean.LAW_INTEREST_TYPE_EUR) result.setLawRateEuro(result.getLawRateEuro()+rate.getLawRateEuro());
								if (result.getRate(interestType).equals(rate.getRate(interestType)) &&
									result.getRateMethod(interestType).equals(rate.getRateMethod(interestType)) &&
									result.getTaxCalculationType().equals(rate.getTaxCalculationType()) ) result.setTo(rate.getTo());
								else return result;
							}
							previousRate = rate.clone();
						}
//					// Workaround for last interval
					if (result ==null && previousRate!=null && !date.before(previousRate.getFrom()) && date.before(previousRate.getTo()) ) return previousRate;
						
					
					if (result!=null && (!date.before(result.getFrom() ) && date.before(result.getTo()))) return result;
					return null;
					}
			case AccountBean.FIXED_ANUAL_CONTRACT_INTEREST_TYPE:
			case AccountBean.FIXED_MONHLEY_CONTRACT_INTEREST_TYPE:
			case AccountBean.FIXED_DAILY_CONTRACT_INTEREST_TYPE:
					return null;
			case AccountBean.FREE_CONTRACT_INTEREST_TYPE:
					{
						Iterator<RateBean> it = contractRates.iterator();
						RateBean rate = null;
						RateBean previousRate = null;
						while (it.hasNext())
						{
							rate = it.next();
//							if (rate.getRate(interestType)==FIXED_DEFAULT_VALUE) continue;
							if (date.before(rate.getFrom())) 	return previousRate;
							previousRate = rate;
						}
						if ((!date.before(previousRate.getFrom() )&& date.before(previousRate.getTo()))) return previousRate;
						else return null;
					}
			}


		}

		/**
		 * @return the fixedAnualContractInterest
		 */
		public double getFixedAnualContractInterest() {
			return fixedAnualContractInterest;
		}

		/**
		 * @param fixedAnualContractInterest the fixedAnualContractInterest to set
		 */
		public void setFixedAnualContractInterest(double fixedAnualContractInterest) {
			this.fixedAnualContractInterest = fixedAnualContractInterest;
		}

		/**
		 * @return the fixedMonthleyContractInterest
		 */
		public double getFixedMonthleyContractInterest() {
			return fixedMonthleyContractInterest;
		}

		/**
		 * @param fixedMonthleyContractInterest the fixedMonthleyContractInterest to set
		 */
		public void setFixedMonthleyContractInterest(
				double fixedMonthleyContractInterest) {
			this.fixedMonthleyContractInterest = fixedMonthleyContractInterest;
		}


		/**
		 * @return the fixedDailyContractInterest
		 */
		public double getFixedDailyContractInterest() {
			return fixedDailyContractInterest;
		}

		/**
		 * @param fixedDailyContractInterest the fixedDailyContractInterest to set
		 */
		public void setFixedDailyContractInterest(double fixedDailyContractInterest) {
			this.fixedDailyContractInterest = fixedDailyContractInterest;
		}

		/**
		 * @param message the message to set
		 */
		private void setMessage(String message) {
			this.message = message;
		}

		/**
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}

}
