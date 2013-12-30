package net.krglok.realms.core;

/**
 * 
 * @author oduda
 * 
 */
public class Owner
{

	private int id;
	private MemberLevel level;
	private int capital;
	private String playerName;
	private int realmID;
	private Boolean isNPC;

	/**
	 * instanziert einen owner als NPC durch zuweisen eines playerOwner wird der Typ
	 * geändert
	 */
	public Owner()
	{
		setId(0);
		setLevel(MemberLevel.MEMBER_NONE);
		setCapital(0);
		setPlayerName("");
		setRealmID(0);
		setIsNPC(true);
	}

	public Owner(			
			int id,
			MemberLevel level,
			int capital,
			String playerName,
			int realmID,
			Boolean isNPC)

	{
		// TODO Auto-generated constructor stub
		setId(id);
		setLevel(level);
		setCapital(capital);
		setPlayerName(playerName);
		setRealmID(realmID);
		setIsNPC(isNPC);
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * 
	 * @return the MemberLevel of owner the member hierarchie
	 */
	public MemberLevel getLevel()
	{
		return level;
	}

	/**
	 * set the owner MemberLevel , without check any conditions
	 * @param level
	 */
	public void setLevel(MemberLevel level)
	{
		this.level = level;
	}

	/**
	 * normally the capital settlement is the first settlemnet of the owner
	 * @return  referenz of the capital settlement
	 */
	public int getCapital()
	{
		return capital;
	}

	/**
	 * set the capital refrenz, without check any conditions
	 * @param capital
	 */
	public void setCapital(int capital)
	{
		this.capital = capital;
	}

	/**
	 * in case  of playerOwner this is the playername 
	 * @return  referenz of the owner
	 */
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * Set the owner without change the isNPC flag
	 * @param owner
	 */
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}

	/**
	 * Set the owner and change the flag isNPC = false
	 * @param owner
	 */
	public void setOwnerPlayer(String playerName)
	{
		this.playerName = playerName;
		this.isNPC = false;
	}

	/**
	 * Set the owner and change the flag isNPC = true
	 * @param owner
	 */
	public void setOwnerNPC(String playerName)
	{
		this.playerName = playerName;
		this.isNPC = true;
	}
	
	/**
	 * rankup the MemberLevel of the owner in the member hierarchie
	 */
	public void rankUp()
	{
		level.levelUp();
	}

	/**
	 * rankDown the MemberLevel of the owner in the member hierarchie
	 */
	public void rankDown()
	{
		level.levelDown();
	}

	public Boolean isNPC()
	{
		return isNPC;
	}

	public void setIsNPC(Boolean isNPC)
	{
		this.isNPC = isNPC;
	}

	public int getRealmID()
	{
		return realmID;
	}

	public void setRealmID(int realmID)
	{
		this.realmID = realmID;
	}

}
