package net.krglok.realms.colonist;

import net.krglok.realms.core.Bank;
import net.krglok.realms.core.BoardItemList;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Warehouse;
import net.krglok.realms.data.MessageText;
import net.krglok.realms.manager.BuildManager;


/**
 * <pre>
 * the colony has the special task to buildup a settlement 
 * this is a reduced settlement with a warehouse and a BuildManager
 * the colony can be moved
 * the colony has a command to start the build
 * then the colony buildup the given settlement schema and destroid afterward.
 * the colony will be stored in a YML file for persistence
 * </pre>
 * @author Windu
 *
 */
public class Colony
{
	
	/**
	 * private status for the Colony, used for state machine
	 * 
	 * @author Windu
	 *
	 */
	private enum ColonyStatus
	{
		NONE,
		PREBUILD,		// der Bauauftrag startet und bereitet die Baustelle vor
		READY,			// der Builder bereitet das Materiallager vor 
		STARTLIST, 		// der Builder baut nach BuildPlan
		BUILDLIST, 		// der Builder baut nach BuildPlan
		NEXTLIST,		// der Builder baut nach BuildPlan
		POSTBUILD,		// der Builder schliesst den Auftrag ab
		DONE,			// der Builder beendet den Auftrag.
		WAIT			// der Builder wartet auf Material
		;
	}

	private static int COUNTER;	/// instance counter for unique referenz id
	

	private int id;
	private ColonyStatus cStatus ;
	private LocationData position;
	private String name;
	private String owner;
	private Warehouse warehouse ;
	private Bank bank;
	private ItemList requiredItems;
	
	private Boolean isEnabled;
	private Boolean isActive;
	
	private BuildManager buildManager = new BuildManager();
	
	
	public Colony (String name, LocationData position, String owner)
	{
		this.cStatus = ColonyStatus.NONE;
		this.name = name;
		this.position = position;
		this.owner = owner;
		this.bank = new Bank();
		this.isEnabled = true;
		this.isActive  = true;
		this.warehouse = new Warehouse(defaultItemMax());
		this.requiredItems = new ItemList();
		this.buildManager = new BuildManager();
		
	}
	
	public int defaultItemMax()
	{
		return 20 * MessageText.CHEST_STORE;
	}

	public void startUpBuild(String name)
	{
		this.name = name;
	}
	
	public void moveTo(LocationData position)
	{
		
		this.position = position;
	}

	public void run ()
	{
		
	}
	
	/**
	 * @return the cOUNTER
	 */
	public static int getCOUNTER()
	{
		return COUNTER;
	}

	/**
	 * @param cOUNTER the cOUNTER to set
	 */
	public static void setCOUNTER(int cOUNTER)
	{
		COUNTER = cOUNTER;
	}
	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public LocationData getPosition()
	{
		return position;
	}

	public void setPosition(LocationData position)
	{
		this.position = position;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getOwner()
	{
		return owner;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public Boolean isEnabled()
	{
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}

	public Boolean isActive()
	{
		return isActive;
	}

	public void setIsActive(Boolean isActive)
	{
		this.isActive = isActive;
	}

	public Warehouse getWarehouse()
	{
		return warehouse;
	}

	public Bank getBank()
	{
		return bank;
	}

	public ItemList getRequiredItems()
	{
		return requiredItems;
	}


	public BuildManager getBuildManager()
	{
		return buildManager;
	}
	
}
