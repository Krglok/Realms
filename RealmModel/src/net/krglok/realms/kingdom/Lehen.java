package net.krglok.realms.kingdom;

import net.krglok.realms.core.AbstractSettle;
import net.krglok.realms.core.Bank;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.data.DataInterface;
import net.krglok.realms.data.ServerInterface;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.npc.NpcData;


/**
 * <pre>
 * the Lehen is the Basic object of of a feudal system.
 * The id= 0 is not allowed , because no should have id=0 !
 * The lehen is a settlement with NOBLE citizen, servant and military units.
 * 
 * 
 * @author Windu
 * </pre>
 */

public class Lehen  extends AbstractSettle
{

	private static int lfdID = 0;
	
	private NobleLevel nobleLevel;
	private Owner owner;
	private int KingdomId;
	private int parentId;
	private double sales;
	private LocationData position;
	private int supportId;
	
	
	public Lehen()
	{
		super();
		this.id = 0;
		this.name = "Lehen";
		this.nobleLevel = NobleLevel.COMMONER;
		this.settleType = SettleType.NONE;
		this.ownerId = 0;
		this.owner = null;
		this.KingdomId = 0;
		this.parentId = 0;
		this.supportId = 0;
		this.sales = 0;
		this.age = 0;
		this.position = new LocationData("", 0.0, 0.0, 0.0);
	}

	/**
	 * will be used to create new Lehen in existing kingdom
	 * the id = 0 and will be set at adding to List
	 * hint : the lehen_4 of the kingdom must exist before !!
	 * hint: use Kingdom.checkLehen for verify the Lehen and kingdom settings 
	 *  
	 * @param KingdomId
	 * @param name
	 * @param nobleLevel
	 * @param settleType
	 * @param owner
	 * @param parentId
	 */
	public Lehen(int KingdomId, String name,NobleLevel nobleLevel,SettleType settleType, Owner owner, int parentId, LocationData position)
	{
		this.id = 0;
		this.name = name;
		this.nobleLevel = nobleLevel;;
		this.settleType = settleType;
		this.ownerId = 0;
		this.owner = owner;
		this.KingdomId = KingdomId;
		this.parentId = parentId;
		this.supportId = 0;
		this.sales = 0;
		this.age = 0;
		this.position = position;
	}

	public static int getLfdID()
	{
		return lfdID;
	}

	public static void initLfdID(int iD)
	{
		lfdID = iD;
	}

	public static int nextID()
	{
		lfdID++;
		return lfdID; 
	}
	
	public String getName()
	{
		return this.getName();
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public NobleLevel getNobleLevel()
	{
		return nobleLevel;
	}

	public void setNobleLevel(NobleLevel nobleLevel)
	{
		this.nobleLevel = nobleLevel;
	}

	public Owner getOwner()
	{
		return owner;
	}

	public void setOwner(Owner owner)
	{
		this.owner = owner;
		this.ownerId = owner.getId();
	}

	/**
	 * @return the parentId
	 */
	public int getParentId()
	{
		return parentId;
	}


	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(int parentId)
	{
		this.parentId = parentId;
	}


	/**
	 * @return the kingdomId
	 */
	public int getKingdomId()
	{
		return KingdomId;
	}


	/**
	 * @param kingdomId the kingdomId to set
	 */
	public void setKingdomId(int kingdomId)
	{
		KingdomId = kingdomId;
	}

	/**
	 * @return the ownerId
	 */
	public int getOwnerId()
	{
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(int ownerId)
	{
		this.ownerId = ownerId;
	}


	/**
	 * @return the position
	 */
	public LocationData getPosition()
	{
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(LocationData position)
	{
		this.position = position;
	}

	/**
	 * @return the sales
	 */
	public double getSales()
	{
		return sales;
	}

	/**
	 * @param sales the sales to set
	 */
	public void setSales(double sales)
	{
		this.sales = sales;
	}

	/**
	 * add value to sales 
	 * hint: dont use negative values , better use withdraw
	 * @param value
	 */
	public void depositSales(double value)
	{
		this.sales = this.sales + value;
	}
	
	/**
	 * subtract value from sales.
	 * hint: dont use negative values !
	 * @param value
	 */
	public void withdrawSales(double value)
	{
		this.sales = this.sales - value;
	}


	public int getSupportId()
	{
		return supportId;
	}

	public void setSupportId(int supportId)
	{
		this.supportId = supportId;
	}

	public void doProduce(ServerInterface server, DataInterface data)
	{
		if (supportId > 0)
		{
			this.warehouse = data.getSettlements().getSettlement(supportId).getWarehouse();
		}
		System.out.println("[REALMS] unit consum");
		age++;
		for (NpcData unit :barrack.getUnitList())
		{
			doConsumUnit(unit, data);
		}
		resident.getNpcList().clear();
		resident.setNpcList(barrack.getUnitList().asNpcList());
		resident.doSettlerCalculation(this.buildingList, data);
	}

	/*
	 * <pre>
	 * active tick for the Manager 
	 * the manager is is a finite state machine
	 * </pre>
	 */
	public void run(RealmModel rModel)
	{
		
	}
	
}
