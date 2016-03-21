package net.krglok.realms.manager;

import java.util.HashMap;

/**
 * list of Reputation data.
 * the key is playerName , repType
 * 
 * @author Windu
 *
 */
public class ReputationList extends HashMap<String,ReputationData> 
{
	private static final long serialVersionUID = -7014279458050306068L;

	public ReputationList()
	{
	}
	
	/**
	 * find settleReputations in the List
	 * @param settleId
	 * @return subList of ReputationData correspond to settleId
	 */

	
	public boolean addValue (ReputationType repTyp,String playerName, String itemName, int value)
	{
		String key = ReputationData.getRefName(playerName, repTyp);
		if (this.containsKey(key) == false)
		{
			ReputationData repData = new ReputationData(repTyp, playerName, itemName, value);
			this.put(key, repData);
			return true;
			
		} else
		{
			this.get(key).addItemValue(itemName, value);
			return true;
		}
	}

	public void addValue (ReputationData repData)
	{
		String key = ReputationData.getRefName(repData.getPlayerName(), repData.getRepTyp());
		this.put(key, repData);
	}
	
	public int getReputation(String playerName)
	{
		int sum = 0;
		for (ReputationData repData : this.values())
		{
			if (repData.getPlayerName().equalsIgnoreCase(playerName))
			{
				sum = sum + repData.getValue();
			}
		}
		return sum;
	}
	
	/**
	 * set isDaily = false
	 */
	public void resetDaily()
	{
		for (ReputationData repData : this.values())
		{
			repData.setIsDailySet(false);
		}
		
	}
}
