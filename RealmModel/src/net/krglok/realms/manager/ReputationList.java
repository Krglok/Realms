package net.krglok.realms.manager;

import java.util.HashMap;

public class ReputationList extends HashMap<ReputationRef,ReputationData> 
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
	public ReputationList getSettleRep(int settleId)
	{
		ReputationList repList = new ReputationList();
		for (ReputationData repData : this.values())
		{
			if (repData.getSettleId() == settleId)
			{
				repList.put(new ReputationRef(repData.getPlayerName(),repData.getRepTyp()), repData); 
			}
		}
		return repList;
	}
	
}
