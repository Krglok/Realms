package net.krglok.realms.core;

import java.util.ArrayList;

import net.krglok.realms.kingdom.LehenList;
import net.krglok.realms.science.Achivement;
import net.krglok.realms.science.AchivementList;
import net.krglok.realms.science.AchivementName;
import net.krglok.realms.science.AchivementType;


/**
 * <pre>
 * owner data for the settlement, kingdom 
 * all activitys and permissions based on the ownership and the the level of ownnership 
 * - nobleLevel are significant for Kingdoms and feudal
 * - commonLevel are significant for Settlements
 * the key for a owner is th uuid of the player. in case of npc the name or none 
 * for a NPC the UUID is the NpcId !
 * NPC0 ist NONE, no NPC no Player
 * 
 * @author oduda
 * </pre>
 */
public class Owner
{
	private static int ID = 0; 
	
	private int id;
	private String uuid;
	private NobleLevel nobleLevel;
	private CommonLevel commonLevel;
	private int capital;
	private int kingdomId; 
	private String playerName;
	private Boolean isNPC;
	private ArrayList<String> msg;
	private AchivementList achivList;
	private double sales;
	
	public Boolean isUser;
//	public String lastIp ;
	public String firstLogin;
	public String lastLogin;
	public String lastposition;
	public String lastLogOff;

	/**
	 * instanziert einen owner als NPC durch.
	 * zuweisen eines playerOwner wird der Typ
	 * geändert
	 */
	public Owner()
	{
		setId(0);
		setLevel(NobleLevel.COMMONER);
		setCapital(0);
		setPlayerName("");
		setKingdomId(0);
		setIsNPC(true);
		setUuid("");
		sales = 0;
		msg = new ArrayList<String>();
		achivList = new AchivementList();
	}

	public Owner(String playerName,	Boolean isNPC)
	{
		id = 0;
		nobleLevel = NobleLevel.COMMONER;
		capital = 0;
		setPlayerName(playerName);
		kingdomId = 0;
		setIsNPC(isNPC);
		setUuid("");
		sales = 0;
		msg = new ArrayList<String>();
		achivList = new AchivementList();
	}
	
	public Owner(			
			int id,
			NobleLevel level,
			int capital,
			String playerName,
			int kingdomID,
			Boolean isNPC,
			String uuid
			)
	{
		setId(id);
		setLevel(level);
		setCapital(capital);
		setPlayerName(playerName);
		setKingdomId(kingdomID);
		setIsNPC(isNPC);
		setUuid(uuid);
		sales = 0;
		msg = new ArrayList<String>();
		achivList = new AchivementList();
	}
	
	public static Owner initDefaultOwner()
	{
		return new Owner(0, NobleLevel.COMMONER, 0, ConfigBasis.NPC_0, 0, true,ConfigBasis.NPC_0);
	}

	public void initColonist()
	{
		this.getAchivList().add(new Achivement(AchivementType.BOOK, AchivementName.TECH0, true));
		this.setCommonLevel(CommonLevel.COLONIST);
	}

	public void initSettler()
	{
		initColonist();
		this.getAchivList().add(new Achivement(AchivementType.BOOK, AchivementName.TECH1, true));
		this.getAchivList().add(new Achivement(AchivementType.BOOK, AchivementName.TECH2, true));
		this.getAchivList().add(new Achivement(AchivementType.BOOK, AchivementName.TECH3, true));
		this.setCommonLevel(CommonLevel.SETTLER);
	}
	
	public void initMayor()
	{
		initColonist();
		initSettler();
		this.getAchivList().add(new Achivement(AchivementType.BOOK, AchivementName.TECH4, true));
		this.setCommonLevel(CommonLevel.MAYOR);
	}	

	public void initCouncilor()
	{
		initColonist();
		initSettler();
		initMayor();
		this.getAchivList().add(new Achivement(AchivementType.BOOK, AchivementName.TECH5, true));
		this.getAchivList().add(new Achivement(AchivementType.BOOK, AchivementName.TECH6, true));
		this.setCommonLevel(CommonLevel.COUNCILOR);
	}	
	
	/**
	 * @return the iD
	 */
	public static int getID()
	{
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public static void initID(int iD)
	{
		ID = iD;
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
	public NobleLevel getLevel()
	{
		return nobleLevel;
	}

	/**
	 * set the owner MemberLevel , without check any conditions
	 * @param level
	 */
	public void setLevel(NobleLevel level)
	{
		this.nobleLevel = level;
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
	 * @param playerName
	 */
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}

	/**
	 * Set the owner and change the flag isNPC = false
	 * @param owner
	 */
	public void setOwnerPlayer(String playerName, String uuid)
	{
		this.playerName = playerName;
		this.uuid = uuid;
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
		nobleLevel.levelUp();
	}

	/**
	 * rankDown the MemberLevel of the owner in the member hierarchie
	 */
	public void rankDown()
	{
		nobleLevel.levelDown();
	}

	public Boolean isNPC()
	{
		return isNPC;
	}

	public void setIsNPC(Boolean isNPC)
	{
		this.isNPC = isNPC;
	}

	public int getKingdomId()
	{
		return kingdomId;
	}

	public void setKingdomId(int kingdomId)
	{
		this.kingdomId = kingdomId;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid()
	{
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}
	
	/**
	 * add the values to existing msg list
	 * 
	 * @param values
	 */
	public void setMsg(ArrayList<String> values)
	{
		this.msg.addAll(values);
	}

	public ArrayList<String> getMsg()
	{
		return this.msg;
	}
	
	/**
	 * add msg to msg list of owner/player
	 * will be automatic send to real players during plugin
	 * 
	 * @param msg
	 */
	public void sendMessage(String msg)
	{
		this.msg.add(msg);
	}

	/**
	 * @return the commonLevel
	 */
	public CommonLevel getCommonLevel()
	{
		return commonLevel;
	}

	/**
	 * @param commonLevel the commonLevel to set
	 */
	public void setCommonLevel(CommonLevel commonLevel)
	{
		this.commonLevel = commonLevel;
	}

	public NobleLevel getNobleLevel()
	{
		return nobleLevel;
	}

	public void setNobleLevel(NobleLevel nobleLevel)
	{
		this.nobleLevel = nobleLevel;
	}


	public Boolean getIsNPC()
	{
		return isNPC;
	}

	/**
	 * @return the achivList
	 */
	public AchivementList getAchivList()
	{
		return achivList;
	}

	/**
	 * @param achivList the achivList to set
	 */
	public void setAchivList(AchivementList achivList)
	{
		this.achivList = achivList;
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
	
}
