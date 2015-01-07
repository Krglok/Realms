package net.krglok.realms.kingdom;

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
	private int ownerId;
	private Owner owner;
	private int KingdomId;
	private int parentId;

	
	public Lehen()
	{
		this.id = 0;
		this.name = "Lehen";
		this.nobleLevel = NobleLevel.COMMONER;
		this.settleType = SettleType.NONE;
		this.ownerId = 0;
		this.owner = null;
		this.KingdomId = 0;
		this.parentId = 0;
	}

	/**
	 * will be used to create new Lehen in existing kingdom
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
	public Lehen(int KingdomId, String name,NobleLevel nobleLevel,SettleType settleType, Owner owner, int parentId)
	{
		this.id = 0;
		this.name = name;
		this.nobleLevel = nobleLevel;;
		this.settleType = settleType;
		this.ownerId = 0;
		this.owner = owner;
		this.KingdomId = KingdomId;
		this.parentId = parentId;
	}

	/**
	 * will be used by read Lehen from file
	 * 
	 * @param id
	 * @param KingdomId
	 * @param name
	 * @param nobleLevel
	 * @param settleType
	 * @param owner
	 * @param parentId
	 */
	public Lehen(int id, int KingdomId, String name,NobleLevel nobleLevel,SettleType settleType, Owner owner, int parentId)
	{
		this.id = id;
		this.name = name;
		this.nobleLevel = nobleLevel;;
		this.settleType = settleType;
		this.owner = owner;
		this.ownerId = 0;
		this.KingdomId = KingdomId;
		this.parentId = parentId;
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

}
