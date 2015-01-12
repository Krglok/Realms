package net.krglok.realms.core;

import java.util.HashMap;
import java.util.Map;

import net.krglok.realms.kingdom.Kingdom;

import org.bukkit.block.Biome;

/**
 * <pre>
 * the settlementList hold all settlements in the model
 * settlements are the basic elements of the model, realize the urban object
 * 
 * @author oduda
 * </pre>
 */
public class SettlementList extends HashMap<Integer,Settlement>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8788396743935180527L;
	
//	private Map<Integer,Settlement> settlementList;

	public SettlementList()
	{
		
	}
	

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
	}


	/**
	 * add list of Settlements to settlementList
	 * caution,  overwrite existing id !
	 * @param newList 
	 */
	public void addSettlements(SettlementList newList)
	{
		for (Settlement settlement : newList.values())
		{
			put(settlement.getId(), settlement);
		}
	}
	
	
	/**
	 * 
	 * @param ref
	 * @return
	 */
	public int checkId(int ref)
	{
		if (ref == 0) { ref = 1; }
		while (this.containsKey(ref))
		{
			ref++;
		}
		Settlement.initCounter(ref);
		return Settlement.getCounter();
	}

	/**
	 * add settlement to the list
	 * set capital for owner if necessary
	 * set new id of the settlement
	 * 
	 * @param settlement
	 */
	public void addSettlement(Settlement settlement)
	{
		settlement.setId(checkId(settlement.getId()));
		this.put(settlement.getId(),settlement);
	}
	
	public void putSettlement(Settlement settlement)
	{
		if (settlement != null)
		{
			this.put(settlement.getId(),settlement);
		}
	}
	
	public int count()
	{
		return this.size();
	}
	
	/**
	 * check for Settlementname
	 * @param settleName
	 * @return true if found otherwise false
	 */
	public Boolean containsName(String settleName)
	{
		for (Settlement settlement : this.values())
		{
			if (settlement.getName().equalsIgnoreCase(settleName))
			{
				return true;
			}
			
		}
		
		return false;
	}

	public boolean containsID(int settleID)
	{
		if (this.containsKey(settleID))
		{
			return true;
		}
		return false;
	}

	
	public Settlement findName(String settleName)
	{
		for (Settlement settlement : this.values())
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
		return this.get(id); 
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
	 * Used for testing 
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
			int ownerId,
			Biome biome, ItemPriceList priceList)//	LogList logList)
	{
		SettlementList settleList = new SettlementList(0);
		SettleType settleType = getSuperRegionSettleType(superRegionTypes);
		if (settleType != SettleType.NONE)
		{
			String settleName = getSettleName(superRegionTypes); 
			Settlement settlement =  Settlement.createSettlement(position, settleType, settleName, ownerId, regionTypes, regionBuildings,biome); //,logList);
			settleList.addSettlement(settlement);
		}
		return settleList;
	}

	/**
	 * remove settlement from List , do not destroy the settlement !
	 * 
	 * @param settleId
	 */
	public void removeSettlement(int settleId)
	{
		if (containsID(settleId))
		{
			this.remove(settleId);
		}
	}
	
	
	public SettlementList getSubList(Owner owner)
	{
		SettlementList subList = new SettlementList();
		for (Settlement settle : this.values())
		{
			if (settle.getOwner() != null) 
			{
				if (settle.getOwnerId() == owner.getId()) 
				{
					subList.put(settle.getId(), settle);
				}
			}
		}
		return subList;
	}
	
	/**
	 * scan settlement list for kingdom id and add settlement to settlement list of kingdom
	 * hint: the settle dont have a kingdomId , only the owner has a kingdomId 
	 * 
	 * @param kingdom
	 * @param settlements
	 */
	public SettlementList getSubList(Kingdom kingdom)
	{
		SettlementList subList = new SettlementList(); 
		for (Settlement settle : this.values())
		{
			if (settle.getOwner() != null)
			{
				int ki = kingdom.getId();
				int soi = settle.getOwner().getKingdomId();
				if(ki == soi)
//				if (kingdom.getId() == settle.getOwner().getKingdomId())
				{
					subList.addSettlement(settle);
				}
			}
		}
		return subList;
	}
	

	public SettlementList getSubList(String worldName)
	{
		SettlementList subList = new SettlementList(); 
		for (Settlement settle : this.values())
		{
			if (settle.getPosition().getWorld().equalsIgnoreCase(worldName))
			{
				subList.addSettlement(settle);
			}
		}
		return subList;
	}

}
