package net.krglok.realms.core;

import java.util.HashMap;

/**
 * Der Rrder ist kein Gebaeude. Das entsprechnde Gebaeude ist unter buildungs zu finden.
 * Der Trader wickelt die Handelsaktionen ab.
 * Hierzu benutzt er Karawanen. Jede Karawane hat einen Esel zum Transport der Warenkiste
 * Jeder Trader hat 5 Handelskisten. Fuer jede Handelskiste kann eine Handelstransaktion ausgeführt werden . 
 * Dies begrenzt auch gleichzeitig den Umfang der Transaktion (max 1762 Items). 
 * Die tatsaechliche Anzahl der Items ist von der Stacksizr abhaengig. 
 *
 * @author Windu
 *
 */

public class Trader
{
	private int caravanMax;
	private int caravanCount;
	private HashMap<Integer,TradeTransaction> tradeTasks;
	private int buildingId;
	private boolean isEnabled;
	private boolean isActive;
	
	
	
	public Trader()
	{
		caravanMax = 5;
		caravanCount = 0;
		tradeTasks = new HashMap<Integer,TradeTransaction>();
		buildingId = 0;
		isEnabled = false;
		isActive  = false;
		
	}



	public Trader(int caravanMax, int caravanCount,
			HashMap<Integer, TradeTransaction> tradeTasks, int buildingId,
			boolean isEnabled, boolean isActive)
	{
		super();
		this.caravanMax = caravanMax;
		this.caravanCount = caravanCount;
		this.tradeTasks = tradeTasks;
		this.buildingId = buildingId;
		this.isEnabled = isEnabled;
		this.isActive = isActive;
	}



	public int getCaravanMax()
	{
		return caravanMax;
	}



	public void setCaravanMax(int caravanMax)
	{
		this.caravanMax = caravanMax;
	}



	public int getCaravanCount()
	{
		return caravanCount;
	}



	public void setCaravanCount(int caravanCount)
	{
		this.caravanCount = caravanCount;
	}



	public HashMap<Integer, TradeTransaction> getTradeTasks()
	{
		return tradeTasks;
	}



	public void setTradeTasks(HashMap<Integer, TradeTransaction> tradeTasks)
	{
		this.tradeTasks = tradeTasks;
	}



	public int getBuildingId()
	{
		return buildingId;
	}



	public void setBuildingId(int buildingId)
	{
		this.buildingId = buildingId;
	}



	public boolean isEnabled()
	{
		return isEnabled;
	}



	public void setEnabled(boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}



	public boolean isActive()
	{
		return isActive;
	}



	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}
	
	
	
	
	
}
