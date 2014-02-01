package net.krglok.realms.colonist;

import org.bukkit.Material;

import net.krglok.realms.builder.BuildPlanColony;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.builder.BuildPosition;
import net.krglok.realms.builder.BuildStatus;
import net.krglok.realms.builder.ItemLocation;
import net.krglok.realms.builder.SettleSchema;
import net.krglok.realms.core.Bank;
import net.krglok.realms.core.BoardItemList;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.SettleType;
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
	private int settler;
	private SettleSchema settleSchema;
	private BuildPosition actualBuildPos;
	private int buildPosIndex;
	private Boolean isEnabled;
	private Boolean isActive;
	
	private BuildPlanType center = BuildPlanType.COLONY;
	
	private BuildManager buildManager = new BuildManager();
	
	private int markUpStep;
	
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
		this.settler = 5;
		this.settleSchema = SettleSchema.initDefaultHamlet();
		this.markUpStep = 0;
		this.buildPosIndex = 0;
		
	}

	/**
	 * Bauterial for ALL 
		BED_BLOCK:4
		WOOL:103
		LOG:231
		WHEAT:48
		TORCH:11
		STONE:331
		WORKBENCH:5
		DIRT:97
		WATER:2
		WALL_SIGN:7
		COBBLESTONE:365
		WOOD_DOOR:7
		BEDROCK:1
		CHEST:33
		BOOKSHELF:4
		WOOD:476

	 * @param name
	 * @param position
	 * @param owner
	 */
	public static void newColony(String name, LocationData position, String owner)
	{
		COUNTER++;
		Colony colony = new Colony ( name,  position,  owner);
		colony.getWarehouse().depositItemValue(Material.BED_BLOCK.name(), 5);
		colony.getWarehouse().depositItemValue(Material.WOOL.name(), 120);
		colony.getWarehouse().depositItemValue(Material.LOG.name(), 250);
		colony.getWarehouse().depositItemValue(Material.WHEAT.name(), 100);
		colony.getWarehouse().depositItemValue(Material.TORCH.name(), 20);
		colony.getWarehouse().depositItemValue(Material.STONE.name(), 400);
		colony.getWarehouse().depositItemValue(Material.WORKBENCH.name(), 6);
		colony.getWarehouse().depositItemValue(Material.DIRT.name(), 100);
		colony.getWarehouse().depositItemValue(Material.WATER.name(), 10);
		colony.getWarehouse().depositItemValue(Material.COBBLESTONE.name(),400);
		colony.getWarehouse().depositItemValue(Material.WOOD_DOOR.name(), 8);
		colony.getWarehouse().depositItemValue(Material.BEDROCK.name(), 1);
		colony.getWarehouse().depositItemValue(Material.CHEST.name(), 40);
		colony.getWarehouse().depositItemValue(Material.BOOKSHELF.name(), 4);
		colony.getWarehouse().depositItemValue(Material.WOOD.name(), 500);
		
	}
	
	
	/**
	 * 
	 * @return
	 */
	private int defaultItemMax()
	{
		return 20 * MessageText.CHEST_STORE;
	}

	/**
	 * 
	 * @param name
	 */
	public void startUpBuild(String name)
	{
		this.name = name;
		cStatus = ColonyStatus.PREBUILD;
		System.out.println("Start Colony Build");
	}
	
	/**
	 * 
	 * @param position
	 */
	public void moveTo(LocationData position)
	{
		
		this.position = position;
	}

	
	/**
	 * 
	 */
	private void markUpSettleSchema()
	{
		LocationData corner ;
		System.out.println("MarkUp Builder "+buildManager.getStatus()+":"+markUpStep);
		switch (this.markUpStep)
		{
		case 1 :
			if (buildManager.getStatus() == BuildStatus.NONE)
			{
				System.out.println("Markup "+markUpStep);
				corner = new LocationData(position.getWorld(), position.getX()-this.settleSchema.getRadius()+1, position.getY(), position.getZ()-this.settleSchema.getRadius()+1);
				buildManager.newBuild(BuildPlanType.PILLAR, corner);
				this.markUpStep++;
			}
			break;
		case 2 :
			if (buildManager.getStatus() == BuildStatus.NONE)
			{
				System.out.println("Markup "+markUpStep);
				corner = new LocationData(position.getWorld(), position.getX()-this.settleSchema.getRadius()+1, position.getY(), position.getZ()+this.settleSchema.getRadius()-1);
				buildManager.newBuild(BuildPlanType.PILLAR, corner);
				this.markUpStep++;
			}
			break;
		case 3 :
			if (buildManager.getStatus() == BuildStatus.NONE)
			{
				System.out.println("Markup "+markUpStep);
				corner = new LocationData(position.getWorld(), position.getX()+this.settleSchema.getRadius()-1, position.getY(), position.getZ()+this.settleSchema.getRadius()-1);
				buildManager.newBuild(BuildPlanType.PILLAR, corner);
				this.markUpStep++;
			}
			break;
		case 4 :
			if (buildManager.getStatus() == BuildStatus.NONE)
			{
				System.out.println("Markup "+markUpStep);
				corner = new LocationData(position.getWorld(), position.getX()+this.settleSchema.getRadius()-1, position.getY(), position.getZ()-this.settleSchema.getRadius()+1);
				buildManager.newBuild(BuildPlanType.PILLAR, corner);
				this.markUpStep++;
			}
			break;
			
		default :
			System.out.println("Markup "+markUpStep);
			this.markUpStep++;
			break;
		}
	}
	
	/**
	 * 
	 */
	public void run (Warehouse warehouse)
	{
		switch (cStatus)
		{
		case PREBUILD:		// der Bauauftrag startet und bereitet die Baustelle vor
			if (buildManager.getStatus() == BuildStatus.NONE)
			{
				System.out.println("Build Center ");
				buildManager.newBuild(BuildPlanType.COLONY, this.position);
				this.cStatus = ColonyStatus.DONE;
			}
			break;
		case READY :		// der Builder bereitet das Materiallager vor
			if (markUpStep < 5)
			{
				markUpSettleSchema();
			} else
			{
				this.cStatus = ColonyStatus.STARTLIST;
			}
			break;
		case STARTLIST: 	// der Builder baut nach BuildPlan
			System.out.println("Build List Start ");
			actualBuildPos = settleSchema.getbPositions().get(0);
			buildPosIndex=1;
			break;
		case BUILDLIST: 	// der Builder baut nach BuildPlan
			if (buildManager.getStatus() == BuildStatus.NONE)
			{
				System.out.println("Build List "+actualBuildPos.getbType());
				buildManager.newBuild(actualBuildPos.getbType(), actualBuildPos.getPosition());
				this.cStatus = ColonyStatus.NEXTLIST;
			}
			break;
		case NEXTLIST:		// der Builder baut nach BuildPlan
			if (buildManager.getStatus() == BuildStatus.NONE)
			{
				if (buildPosIndex < settleSchema.getbPositions().size())
				{
					System.out.println("Build List Next ");
					buildPosIndex++;
					this.cStatus = ColonyStatus.BUILDLIST;
				} else
				{
					this.cStatus = ColonyStatus.POSTBUILD;
				}
			}
			break;
		case POSTBUILD:		// der Builder schliesst den Auftrag ab
			this.cStatus = ColonyStatus.DONE;
			break;
		case DONE:			// der Builder beendet den Auftrag.
			System.out.println("Build DONE ");
			this.cStatus = ColonyStatus.NONE;
			break;
		case WAIT:			// der Builder wartet auf Material
			this.cStatus = ColonyStatus.NONE;
			break;
		default :
		}
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


	public BuildManager buildManager()
	{
		return buildManager;
	}
	
}
