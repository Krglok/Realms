package net.krglok.realms.core;

import java.io.Serializable;
import java.util.ArrayList;

import net.krglok.realms.data.LogList;

/**
 * @author oduda
 *
 * the bank hold the money of a settlement
 * there is an transaction protocol
 */
public class Bank  implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4885560720493966878L;
	private static final String KONTO_TOO_LOW = "konto too low ";
	private static final String WITHDRAW = "Withdraw ";
	private static final String DEPOSIT = "Deposit ";
	private static final String ADD_KONTO = "Add Konto ";
	private Boolean isEnabled;
	private Double konto;
	private LogList transactionList;
	private ItemList valuables;
	
//	public Bank(LogList LogList)
//	{
//		setIsEnabled(false);
//		konto = Double.valueOf(0.0);
//		this.transactionList = logList;
//	}
	
	public Bank(LogList logList)
	{
		setIsEnabled(false);
		konto = Double.valueOf(0.0);
		transactionList = logList;
		valuables = ConfigBasis.initValuables();
	}

	public ArrayList<String> setKredit(String itemRef, int amount, ItemPriceList pricelist, ItemList inventory, int settleId)
	{
		ArrayList<String> msg = new ArrayList<String>();

		double price = pricelist.getBasePrice(itemRef);
		if( price > 0.0)
		{
			int stock = inventory.getValue(itemRef);
			if (stock >= amount)
			{
				double value = amount * price;
				addKonto(value, "Credit with "+itemRef, settleId);
				inventory.withdrawItem(itemRef, amount);
				msg.add("Credit to Bank "+itemRef+"with Value: "+value);
				msg.add("");
				return msg;
			}
			msg.add("No stock for Item "+itemRef+" max: "+stock);
			msg.add("");
			return msg;
		}
		msg.add("No price for Item "+itemRef);
		msg.add("");
		return msg;
	}

	/**
	 * 
	 * @return value of konto 
	 */
	public double getKonto()
	{
		return konto;
	}

	/**
	 * initialize konto with value , overwrite
	 * @param konto
	 */
	public void initKonto(Double konto, int settleId)
	{
		this.konto = konto;
		if (transactionList != null)
		{
			transactionList.addBank("Init Konto ", "Admin", settleId, konto);
		}
	}

	/**
	 * the value is a  signed field 
	 * This is a administrator function 
	 * @param value
	 */
	public void addKonto(Double value, String text, int settleId)
	{
		konto = konto + value;
		if (transactionList != null)
		{
			transactionList.addBank(ADD_KONTO,"Admin", settleId, value);
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
	public double depositKonto (Double value, String user, int SettleId)
	{
		konto = konto + value;
		if (transactionList != null)
		{
			transactionList.addBank(DEPOSIT,user, SettleId,  value);
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
	public Boolean withdrawKonto (Double value, String user, int settleId)
	{
		if (konto >= value)
		{
			konto = konto - value;
			if (transactionList != null)
			{
				transactionList.addBank(WITHDRAW, user, settleId, value);
			}
			return true;
		}
		if (transactionList != null)
		{
			transactionList.addBank(user,KONTO_TOO_LOW, settleId, konto - value);
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
