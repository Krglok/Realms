package net.krglok.realms.core;

import java.util.HashMap;
import java.util.Map;

/**
 * realize a list of all settlements in the model
 * Settlements are the basic elements of the model
 * @author oduda
 *
 */
public class SettlementList
{

	private Map<Integer,Settlement> settlementList;
	

	/**
	 * the ID only set if initCounter > 0 ! 
	 * @param initCounter , set to Settlemnet Class static ID
	 */
	public SettlementList(int initCounter )
	{
		if (initCounter > 0)
		{
			Settlement.initCounter(initCounter);
		}
		setSettlements(new HashMap<Integer,Settlement>());
	}

	/**
	 * 
	 * @return settlements <id, Settlement>
	 */
	public Map<Integer,Settlement> getSettlements()
	{
		return settlementList;
	}

	/**
	 * replace settlementList
	 * @param settlements <id, Settlement>
	 */
	public void setSettlements(Map<Integer,Settlement> settlements)
	{
		this.settlementList = settlements;
	}

	/**
	 * add list of newSettlements to settlementList
	 * caution,  overwrite existing id !
	 * @param newList 
	 */
	public void addSettlements(SettlementList newList)
	{
		for (Settlement settlement : newList.getSettlements().values())
		{
			addSettlement(settlement);
//			settlementList.put(String.valueOf(settlement.getId()), settlement);
//			settlement.initSettlement();
//			setOwner(settlement.getId(), settlement.getOwner());
		}
	}
	
	/**
	 * add settlement to the list
	 * set capital for owner if necessary
	 * @param settlement
	 */
	public void addSettlement(Settlement settlement)
	{
		settlementList.put(settlement.getId(),settlement);
		settlement.initSettlement();
		setOwner(settlement.getId(), settlement.getOwner());
//		setOwnerCapital(settlement.getOwner(), settlement.getId());
	}
	
	/**
	 * set owner of the settlement
	 * @param id
	 * @param owner
	 */
	public void setOwner(int id, String owner)
	{
		Settlement settlement = settlementList.get(id);
		settlement.setOwner(owner);
		settlementList.put(id ,settlement);
	}

	public Settlement setOwner(Settlement settlement, String owner)
	{
		settlement.setOwner(owner);
		settlementList.put(settlement.getId() ,settlement);
		return settlement;
	}
	
	public int count()
	{
		return settlementList.size();
	}
	
	/**
	 * Search for Settlementname
	 * @param settleName
	 * @return true if found otherwise false
	 */
	public Boolean containsName(String settleName)
	{
		for (Settlement settlement : settlementList.values())
		{
			if (settlement.getName().equals(settleName))
			{
				return true;
			}
			
		}
		
		return false;
	}
	
	/**
	 * caution id > 0 
	 * @param id
	 * @return settlement with id otherwise null
	 */
	public Settlement getSettlement(int id)
	{
		return settlementList.get(id); 
	}
	
	
	/**
	 * set Capital refrenz for the Owner if no capital set
	 * no overwrite
	 * @param owner
	 * @param id of settlement
	 */
	public void setOwnerCapital(Owner owner, int id)
	{
		if (owner.getCapital() == 0)
		{
			if (id > 0)
			{
				owner.setCapital(id);
			}
		}
	}
	
	
	/**
	 * overwrite Capital referenz in owner
	 * @param owner
	 * @param id of settlement
	 * @return true if set otherwie false
	 */
	public Boolean updateOwnerCapital(Owner owner, int id)
	{
		if (id > 0)
		{
			owner.setCapital(id);
		}
		return false;
	}

	/**
	 * Search for first SettleType in List
	 * @param regionTypes
	 * @return  SettleType 
	 */
	private static SettleType getSuperRegionSettleType(HashMap<String,String> regionTypes)
	{
		for (String  typeName: regionTypes.values())
		{
			SettleType sType = SettleType.getSettleType(typeName);
			if ( sType != SettleType.SETTLE_NONE)
			{
				return sType;
			}
		}
		
		return SettleType.SETTLE_NONE;
	}
	
	/**
	 * 
	 * @param regionTypes <superRegionName,superRegionTypeName>
	 * @return
	 */
	private static String getSettleName(HashMap<String,String> superRegionTypes)
	{
		for (String keyName : superRegionTypes.keySet())
		{
			if (SettleType.getSettleType(superRegionTypes.get(keyName)) != SettleType.SETTLE_NONE)
			{
				return keyName;
			}
				
		}
		
		return "";
	}
	
	/**
	 * create a new settlementList based on 
	 * - superRegion list , <superegionName , settleTypeName>  
	 * - region list <regionId, regionTypeName>
	 * caution : only first found legal SettlementType will be created
	 * @param superRegionTypes , relevant superRegions 
	 * @param regionTypes , relevant regions 
	 * @param owner , of the settlement
	 * @return new Settlement or null
	 */
	public static SettlementList createSettlement(HashMap<String,String> superRegionTypes, 
			                                      HashMap<String,String> regionTypes, 
			                                      HashMap<String,String> regionBuildings, 
			                                      String owner)
	{
		SettlementList settleList = new SettlementList(0);
		SettleType settleType = getSuperRegionSettleType(superRegionTypes);
		if (settleType != SettleType.SETTLE_NONE)
		{
			String settleName = getSettleName(superRegionTypes); 
			Settlement settlement =  Settlement.createSettlement(settleType, settleName, owner, regionTypes, regionBuildings);
			settleList.addSettlement(settlement);
		}
		return settleList;
	}
	
//	public void produce()
//	{
//		
//	}
//	
//	public void consume()
//	{
//		
//	}
}
