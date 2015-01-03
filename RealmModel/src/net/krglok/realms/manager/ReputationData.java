package net.krglok.realms.manager;

import java.util.HashMap;

/**
 * hold the reputation value for a player and a specific reputationType
 * has a function for make a reference name
 * the detail values (itemValues) are only valid after a serverstart. they will not stored in datafile
 * so every player can donate the same items again after a restart of server. 
 * 
 * @author Krglok
 *
 */
public class ReputationData
{

	private ReputationType repTyp;
	private String playerName;
	private int value;
	private HashMap<String, Integer> itemValues;
	private boolean isDailySet = false;
	
	public ReputationData(ReputationType repTyp, String playerName, String itemName, int value)
	{
		super();
		this.repTyp = repTyp;
		this.playerName = playerName;
		this.value = value;
		this.itemValues = new  HashMap<String, Integer>();
		addItemValue(itemName, value);
	}

	public ReputationData(String TypeName, String playerName, int value)
	{
		super();
		this.repTyp = ReputationType.valueOf(TypeName);
		this.playerName = playerName;
		this.value = value;
		this.itemValues = new  HashMap<String, Integer>();
	}
	
	
	public String getName()
	{
		return getRefName(playerName, repTyp);
	}

	/**
	 * return a formatted string for referenz name
	 * the separator is "_"
	 * 
	 * @param playerName
	 * @param repTyp
	 * @return
	 */
	
	public static String getRefName(String playerName, ReputationType repTyp)
	{
		return playerName+"_"+repTyp.name();
	}

	public static String getRefName(String playerName, String repTypName)
	{
		return playerName+"_"+repTypName;
	}

	public ReputationType getRepTyp()
	{
		return repTyp;
	}


	public void setRepTyp(ReputationType repTyp)
	{
		this.repTyp = repTyp;
	}


	public String getPlayerName()
	{
		return playerName;
	}


	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}


	public int getValue()
	{
		return value;
	}


	public void setValue(int value)
	{
		this.value = value;
	}

	public boolean isDailySet()
	{
		return isDailySet;
	}
	
	public void setIsDailySet(boolean value)
	{
		this.isDailySet = value;
	}

	public  HashMap<String, Integer> getItemValues()
	{
		return itemValues;
	}

	/**
	 * add item one time per day
	 * @param itemName
	 * @param value
	 */
	public void addItemValue(String itemName, int value)
	{
		if (itemValues.containsKey(itemName) == false)
		{
			itemValues.put(itemName, value);
			this.value = this.value + value;
		} else
		{
			if (isDailySet == false)
			{
				isDailySet = true;
				if (this.value < repTyp.getValue())
				{
					itemValues.put(itemName, itemValues.get(itemName)+value);
					this.value = this.value + value;
				}
			}
		}
	}
	
	public static String splitNameTyp(String value)
	{
        String[] params = value.split("_");

		return params[1];
	}

	public static String splitNameName(String value)
	{
        String[] params = value.split("_");

		return params[0];
	}
	
	
}
