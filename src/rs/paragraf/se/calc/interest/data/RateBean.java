package rs.paragraf.se.calc.interest.data;

import java.io.Serializable;
import java.util.Date;

import rs.paragraf.se.calc.interest.utils.RateManager;

/**
 * @author igor
 *
 */
public class RateBean implements Serializable, Comparable<RateBean>,Cloneable{

	public static final long serialVersionUID = 6004;

	private Date from;
	private Date to;
	private Double taxCalculationType;
	private Double lawRateMethod;
	private Double taxRateMethod;
	private Double denomination;
	private int roundFactorDenomination;
	private Double lawRate;
	private Double lawRateEuro;
	private Double lawRateEuroPrint;
	private Double lawRatePrint;
	private Double priceGrowthRate;
	private Double taxYRate;
	private Double ecbRate;
	private Double excontYRate;
	private Double referentRate;
	
	private Double freeContractRate;

	// special method

	/**
	 * @return the rateMethod
	 */
	public Double getRateMethod(int interestType) {
		if (interestType==AccountBean.TAX_INTEREST_TYPE) return taxRateMethod;
		return lawRateMethod;
	}

	public Double getRate(int interestType)
	{
//		System.out.println("Rate type :: " + interestType);
		switch (interestType) {
		case AccountBean.LAW_INTEREST_TYPE: return lawRatePrint;
		case AccountBean.LAW_INTEREST_TYPE_EUR: return lawRateEuroPrint;
		case AccountBean.PRICE_GROWTH_INTEREST_TYPE: return priceGrowthRate;
		case AccountBean.TAX_INTEREST_TYPE: return taxYRate;
		case AccountBean.ECB_INTEREST_TYPE: return ecbRate;
		case AccountBean.EXCONT_ANUAL_INTEREST_TYPE: return excontYRate;
		case AccountBean.REFERENT_INTEREST_TYPE: return referentRate;
		case AccountBean.FREE_CONTRACT_INTEREST_TYPE: return freeContractRate;
		case AccountBean.FIXED_ANUAL_CONTRACT_INTEREST_TYPE: return RateManager.getInstance().getFixedAnualContractInterest();
		case AccountBean.FIXED_MONHLEY_CONTRACT_INTEREST_TYPE: return RateManager.getInstance().getFixedMonthleyContractInterest();
		case AccountBean.FIXED_DAILY_CONTRACT_INTEREST_TYPE: return RateManager.getInstance().getFixedDailyContractInterest();
		default: return null; // unable to find interesttype
		}
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
	 * @return the lawRate
	 */
	public Double getLawRate() {
		return lawRate;
	}
	/**
	 * @param lawRate the lawRate to set
	 */
	public void setLawRate(Double lawRate) {
		this.lawRate = lawRate;
	}
	/**
	 * @return the priceGrowthRate
	 */
	public Double getPriceGrowthRate() {
		return priceGrowthRate;
	}
	/**
	 * @param priceGrowthRate the priceGrowthRate to set
	 */
	public void setPriceGrowthRate(Double priceGrowthRate) {
		this.priceGrowthRate = priceGrowthRate;
	}
	/**
	 * @return the taxYRate
	 */
	public Double getTaxYRate() {
		return taxYRate;
	}
	/**
	 * @param taxYRate the taxYRate to set
	 */
	public void setTaxYRate(Double taxYRate) {
		this.taxYRate = taxYRate;
	}
	/**
	 * @return the excontMRate
	 */
	public Double getEcbRate() {
		return ecbRate;
	}
	/**
	 * @param ecbRate the excontMRate to set
	 */
	public void setEcbRate(Double ecbRate) {
		this.ecbRate = ecbRate;
	}
	/**
	 * @return the excontYRate
	 */
	public Double getExcontYRate() {
		return excontYRate;
	}
	/**
	 * @param excontYRate the excontYRate to set
	 */
	public void setExcontYRate(Double excontYRate) {
		this.excontYRate = excontYRate;
	}
	// it is comparable by the from date
	public int compareTo(RateBean o) {
		return getFrom().compareTo(((RateBean) o).getFrom());
	}
	/**
	 * @return the freeContractRate
	 */
	public Double getFreeContractRate() {
		return freeContractRate;
	}
	/**
	 * @param freeContractRate the freeContractRate to set
	 */
	public void setFreeContractRate(Double freeContractRate) {
		this.freeContractRate = freeContractRate;
	}
	public Double getLawRatePrint() {
		return lawRatePrint;
	}
	public void setLawRatePrint(Double lawRatePrint) {
		this.lawRatePrint = lawRatePrint;
	}
	/**
	 * @param rateMethod the rateMethod to set
	 */
	public void setLawRateMethod(Double rateMethod) {
		this.lawRateMethod = rateMethod;
	}
	/**
	 * @return the rateMethod
	 */
	public Double getLawRateMethod() {
		return lawRateMethod;
	}
	/**
	 * @param taxRateMethod the taxRateMethod to set
	 */
	public void setTaxRateMethod(Double taxRateMethod) {
		this.taxRateMethod = taxRateMethod;
	}
	/**
	 * @return the taxRateMethod
	 */
	public Double getTaxRateMethod() {
		return taxRateMethod;
	}

	/**
	 * @param denomination the denomination to set
	 */
	public void setDenomination(Double denomination) {
		this.denomination = denomination;
	}

	/**
	 * @return the denomination
	 */
	public Double getDenomination() {
		return denomination;
	}

	/**
	 * @param roundFactorDenomination the roundFactorDenomination to set
	 */
	public void setRoundFactorDenomination(int roundFactorDenomination) {
		this.roundFactorDenomination = roundFactorDenomination;
	}

	/**
	 * @return the roundFactorDenomination
	 */
	public int getRoundFactorDenomination() {
		return roundFactorDenomination;
	}

	/**
	 * @return the lawRateEuro
	 */
	public Double getLawRateEuro() {
		return lawRateEuro;
	}

	/**
	 * @param lawRateEuro the lawRateEuro to set
	 */
	public void setLawRateEuro(Double lawRateEuro) {
		this.lawRateEuro = lawRateEuro;
	}

	/**
	 * @return the lawRateEuroPrint
	 */
	public Double getLawRateEuroPrint() {
		return lawRateEuroPrint;
	}

	/**
	 * @param lawRateEuroPrint the lawRateEuroPrint to set
	 */
	public void setLawRateEuroPrint(Double lawRateEuroPrint) {
		this.lawRateEuroPrint = lawRateEuroPrint;
	}
	
	
	/**
	 * @return the referentRate
	 */
	public Double getReferentRate() {
		return referentRate;
	}

	/**
	 * @param referentRate the referentRate to set
	 */
	public void setReferentRate(Double referentRate) {
		this.referentRate = referentRate;
	}

	/**
	 * @return the taxCalculationType
	 */
	public Double getTaxCalculationType() {
		return taxCalculationType;
	}

	/**
	 * @param taxCalculationType the taxCalculationType to set
	 */
	public void setTaxCalculationType(Double taxCalculationType) {
		this.taxCalculationType = taxCalculationType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public RateBean clone() {
	try {
				return (RateBean) super.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
	}
	
	
}
