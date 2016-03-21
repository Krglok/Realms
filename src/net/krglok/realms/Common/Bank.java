package net.krglok.realms.Common;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.krglok.realms.core.ConfigBasis;


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
//	private LogList transactionList;
	private ItemList valuables;
	protected ArrayList<String> msg = new ArrayList<String>();
	
//	public Bank(LogList LogList)
//	{
//		setIsEnabled(false);
//		konto = Double.valueOf(0.0);
//		this.transactionList = logList;
//	}
	
	public Bank() //LogList logList)
	{
		setIsEnabled(false);
		konto = Double.valueOf(0.0);
//		transactionList = logList;
		setValuables(ConfigBasis.initValuables());
	}

	public ItemList getValuables() {
		return valuables;
	}

	public void setValuables(ItemList valuables) {
		this.valuables = valuables;
	}

	public ArrayList<String> getMsg() {
		return msg;
	}

	public void setMsg(ArrayList<String> msg) {
		this.msg = msg;
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
		transactionText("Admin", "Init", settleId,konto);
//		if (transactionList != null)
//		{
//			transactionList.addBank("Init Konto ", "Admin", settleId, konto);
//		}
	}

	/**
	 * the value is a  signed field 
	 * This is a administrator function 
	 * @param value
	 */
	public void addKonto(Double value, String text, int settleId)
	{
		konto = konto + value;
//		if (transactionList != null)
//		{
//			transactionList.addBank(ADD_KONTO,"Admin", settleId, value);
//		}
	}
	
	/**
	 * deposit value on konto (positive values are deposit)
	 * the value is a  signed field
	 * transaction protocol are written
	 * @param value
	 * @param user
	 * @return new value of konto
	 */
	public double depositKonto (Double value, String user, int settleId)
	{
		konto = konto + value;
		transactionText(user, "Deposit ", settleId,value);
//		if (transactionList != null)
//		{
//			transactionList.addBank(DEPOSIT,user, SettleId,  value);
//		}
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
			transactionText(user, "Withdraw ", settleId,value);
//			if (transactionList != null)
//			{
//				transactionList.addBank(WITHDRAW, user, settleId, value);
//			}
			return true;
		}
//		if (transactionList != null)
//		{
//			transactionList.addBank(user,KONTO_TOO_LOW, settleId, konto - value);
//		}
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
//	public LogList getTransactionList()
//	{
//		return transactionList;
//	}
//
//
//	public void clearTransactionList()
//	{
//		if (transactionList != null)
//		{
//			transactionList.getLogList().clear();
//		}
//	}

	public void transactionText(String user, String text, int SettleId,double value)
	{
		String DataType = "BANK";
		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
//		String formattedDate = sdf.format(date);
		String transaction = //formattedDate 
				 DataType
				+ ":" + user 
				+ ":" + text
				+":" + String.valueOf(SettleId) 
				+ ":" + ConfigBasis.setStrformat2(value,7)
				+ ":" + ConfigBasis.setStrformat2(konto,10);
		msg.add(transaction);
	}

}
