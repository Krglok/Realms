package net.krglok.realms.core;

/**
 * @author oduda
 *
 * the bank hold the money of a settlement
 * there is an transaction protocol
 */
public class Bank
{
	private static final String KONTO_TOO_LOW = "konto too low ";
	private static final String WITHDRAW = "Withdraw ";
	private static final String DEPOSIT = "Deposit ";
	private static final String ADD_KONTO = "Add Konto ";
	private Boolean isEnabled;
	private Double konto;
	private LogList transactionList;
	
	public Bank()
	{
		setIsEnabled(false);
		konto = Double.valueOf(0.0);
		transactionList = null;
	}
	
	public Bank(LogList logList)
	{
		setIsEnabled(false);
		konto = Double.valueOf(0.0);
		transactionList = logList;
	}
	

	/**
	 * 
	 * @return value of konto 
	 */
	public Double getKonto()
	{
		return konto;
	}

	/**
	 * initialize konto with value , overwrite
	 * @param konto
	 */
	public void initKonto(Double konto)
	{
		this.konto = konto;
		if (transactionList != null)
		{
			transactionList.addlog("Init Konto "+String.valueOf(konto), "Admin");
		}
	}

	/**
	 * the value is a  signed field 
	 * This is a administrator function 
	 * @param value
	 */
	public void addKonto(Double value)
	{
		konto = konto + value;
		if (transactionList != null)
		{
			transactionList.addlog(ADD_KONTO+String.valueOf(value), "Admin");
		}
	}
	
	/**
	 * deposit value on konto (positive values are deposit)
	 * the value is a  signed field
	 * transaction protocol are written
	 * @param value
	 * @param user
	 * @return new value of konto
	 */
	public Double depositKonto (Double value, String user)
	{
		konto = konto + value;
		if (transactionList != null)
		{
			transactionList.addlog(DEPOSIT+String.valueOf(value), user);
		}
		return konto;
	}

	/**
	 * withdraw value from konto (positive values are withdraw)
	 * the value is a  signed field 
	 * transaction protocol are written
	 * @param value
	 * @param user
	 * @return true if withdraw done otherwise false
	 */
	public Boolean withdrawKonto (Double value, String user)
	{
		if (konto >= value)
		{
			konto = konto - value;
			if (transactionList != null)
			{
				transactionList.addlog(WITHDRAW+String.valueOf(value), user);
			}
			return true;
		}
		if (transactionList != null)
		{
			transactionList.addlog(KONTO_TOO_LOW+String.valueOf(konto)+ "/"+String.valueOf(value), user);
		}
		return false;
	}
	
	
	/**
	 * 
	 * @return  true is Bank is enabled 
	 */
	public Boolean getIsEnabled()
	{
		return isEnabled;
	}

	/**
	 * Set the enabled Status , no check for conditions
	 * @param isEnabled
	 */
	public void setIsEnabled(Boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}

	/**
	 * List of formatted Strings
	 * date / time / user / text
	 * @return entire transactionlist,  
	 */
	public LogList getTransactionList()
	{
		return transactionList;
	}


	public void clearTransactionList()
	{
		if (transactionList != null)
		{
			transactionList.getLogList().clear();
		}
	}
	
}
