package net.krglok.realms.core;

import java.util.HashMap;
import java.util.Map;

import net.krglok.realms.data.LogList;

import org.bukkit.block.Biome;

/**
 * <pre>
 * the settlementList hold all settlements in the model
 * settlements are the basic elements of the model, realize the urban object
 * 
 * @author oduda
 * </pre>
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
	
	public int checkId(int ref)
	{
		while (settlementList.containsKey(ref))
		{
			ref++;
		}
		Settlement.initCounter(ref);
		return Settlement.getCounter();
	}

	/**
	 * add settlement to the list
	 * set capital for owner if necessary
	 * @param settlement
	 */
	public void addSettlement(Settlement settlement)
	{
		settlement.setId(checkId(settlement.getId()));
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
	 * check for Settlementname
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

	public boolean containsID(int settleID)
	{
		if (settlementList.containsKey(settleID))
		{
			return true;
		}
		return false;
	}

	
	public Settlement findName(String settleName)
	{
		for (Settlement settlement : settlementList.values())
		{
			if (settlement.getName().equals(settleName))
			{
				return settlement;
			}
			
		}
		
		return null;
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
			if ( sType != SettleType.NONE)
			{
				return sType;
			}
		}
		
		return SettleType.NONE;
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
			if (SettleType.getSettleType(superRegionTypes.get(keyName)) != SettleType.NONE)
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
	public static SettlementList createSettlement(
			LocationData position ,
			HashMap<String,String> superRegionTypes, 
			HashMap<String,String> regionTypes, 
			HashMap<String,String> regionBuildings, 
			String owner,
			Biome biome,
			LogList logList)
	{
		SettlementList settleList = new SettlementList(0);
		SettleType settleType = getSuperRegionSettleType(superRegionTypes);
		if (settleType != SettleType.NONE)
		{
			String settleName = getSettleName(superRegionTypes); 
			Settlement settlement =  Settlement.createSettlement(position, settleType, settleName, owner, regionTypes, regionBuildings,biome,logList);
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
