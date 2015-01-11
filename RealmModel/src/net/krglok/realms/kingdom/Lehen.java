package net.krglok.realms.kingdom;

import net.krglok.realms.core.Bank;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.SettleType;


/**
 * <pre>
 * the Lehen is the Basic object of of a feudal system.
 * The id= 0 is not allowed , because no should have id=0 !
 * 
 * @author Windu
 * </pre>
 */

public class Lehen
{

	private static int ID;
	
	private int id;
	private String name;
	private NobleLevel nobleLevel;
	private SettleType settleType;
	private String ownerId;
	private Owner owner;
	private int KingdomId;
	private int parentId;
	private Bank bank;
	private double sales;
	private LocationData position;
	private BuildingList buildings;
	
	
	public Lehen()
	{
		this.id = 0;
		this.name = "Lehen";
		this.nobleLevel = NobleLevel.COMMONER;
		this.settleType = SettleType.NONE;
		this.ownerId = "";
		this.owner = null;
		this.KingdomId = 0;
		this.parentId = 0;
		this.setBank(new Bank());
		this.sales = 0;
		this.position = new LocationData("", 0.0, 0.0, 0.0);
		this.buildings = new BuildingList();
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
		this.ownerId = "";
		this.owner = owner;
		this.KingdomId = KingdomId;
		this.parentId = parentId;
		this.setBank(new Bank());
		this.sales = 0;
		this.position = position;
		this.buildings = new BuildingList();
	}

	public static int getID()
	{
		return ID;
	}

	public static void initID(int iD)
	{
		ID = iD;
	}

	public static int nextID()
	{
		ID++;
		return ID; 
	}
	
	public String getName()
	{
		return name;
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

	public SettleType getSettleType()
	{
		return settleType;
	}

	public void setSettleType(SettleType settleType)
	{
		this.settleType = settleType;
	}

	public Owner getOwner()
	{
		return owner;
	}

	public void setOwner(Owner owner)
	{
		this.owner = owner;
		this.ownerId = owner.getPlayerName();
	}

	public int getId()
	{
		return id;
	}
	
	public void setId(int value)
	{
		this.id = value;
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
	public String getOwnerId()
	{
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(String ownerId)
	{
		this.ownerId = ownerId;
	}

	/**
	 * @return the bank
	 */
	public Bank getBank()
	{
		return bank;
	}

	/**
	 * @param bank the bank to set
	 */
	public void setBank(Bank bank)
	{
		this.bank = bank;
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

	/**
	 * @return the buildings
	 */
	public BuildingList getBuildings()
	{
		return buildings;
	}

	/**
	 * @param buildings the buildings to set
	 */
	public void setBuildings(BuildingList buildings)
	{
		this.buildings = buildings;
	}
	
	
	
}
