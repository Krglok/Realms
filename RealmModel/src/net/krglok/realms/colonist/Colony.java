package net.krglok.realms.colonist;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import net.krglok.realms.CmdSettleCreate;
import net.krglok.realms.builder.BuildPlanColony;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.builder.BuildPosition;
import net.krglok.realms.builder.BuildStatus;
import net.krglok.realms.builder.ItemLocation;
import net.krglok.realms.builder.RegionLocation;
import net.krglok.realms.builder.SettleSchema;
import net.krglok.realms.core.Bank;
import net.krglok.realms.core.BoardItemList;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.Warehouse;
import net.krglok.realms.data.MessageText;
import net.krglok.realms.manager.BiomeLocation;
import net.krglok.realms.manager.BuildManager;
import net.krglok.realms.model.McmdCreateSettle;
import net.krglok.realms.model.RealmModel;
import net.minecraft.server.v1_7_R1.BiomeRiver;


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
		STARTUP,		// Der Bauauftrag startet
		PREPARE,		// Vorbereitung des Bauplatzes
		PREBUILD,		// der Bauauftrag startet und bereitet die Baustelle vor
		MARKUP,			// der Bauplatz wird markiert
		READY,			// der Builder bereitet das Materiallager vor 
		STARTLIST, 		// der Builder baut nach BuildPlan
		BUILDLIST, 		// der Builder baut nach BuildPlan
		NEXTLIST,		// der Builder baut nach BuildPlan
		POSTBUILD,		// der Builder erstellt die Superregion
		NEWSETTLE, 		// erstellt das neue Settlement, schliesst den Auftrag ab
		REINFORCE,		// entfernt die Colony
		DONE,			// der Builder beendet den Auftrag.
		FULFILL,		// Auftrag vollstaendig erledigt
		WAIT,			// der Builder wartet auf Material
		WAITBUILD		// wartet auf den BuildManager
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
	private ColonyStatus nextStatus;
	
	private BuildPlanType center = BuildPlanType.COLONY;
	
	private BuildManager buildManager = new BuildManager();
	
	private RegionLocation newSuperRegion;
	private RegionLocation superRequest;
	
	private int markUpStep;
	
	private boolean isPrepared ;
	private int prepareLevel;
	private int prepareRow;
	private int prepareCol;
	private int prepareRadius;
	private int prepareOffset;
	
	private Biome biome;
	private ArrayList<BiomeLocation> biomeRequest;
	
	public Colony (String name, LocationData position, String owner)
	{
		COUNTER++;
		id = COUNTER;
		this.cStatus = ColonyStatus.NONE;
		this.nextStatus = ColonyStatus.NONE;
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
		this.newSuperRegion = new RegionLocation("Siedlung", position, owner, name);
		this.superRequest = null;
		this.isPrepared = false;
		this.prepareLevel = 41;
		this.prepareRow = 0;
		this.prepareCol = 0;
		this.prepareRadius= 21;
		this.prepareOffset= 0;
		this.biome = null;
		this.biomeRequest = new ArrayList<BiomeLocation>();
		
	}

	/**
	 * creaze a new Colony with preset Wareouse
	 * 
	 * @param name
	 * @param position
	 * @param owner
	 */
	public static Colony newColony(String name, LocationData position, String owner)
	{
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
		colony.getWarehouse().depositItemValue(Material.COBBLESTONE.name(),500);
		colony.getWarehouse().depositItemValue(Material.WOOD_DOOR.name(), 8);
		colony.getWarehouse().depositItemValue(Material.BEDROCK.name(), 1);
		colony.getWarehouse().depositItemValue(Material.CHEST.name(), 40);
		colony.getWarehouse().depositItemValue(Material.BOOKSHELF.name(), 4);
		colony.getWarehouse().depositItemValue(Material.WOOD.name(), 500);
		return colony;
		
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
	 * startUp buildProcess
	 * set the World for the relative BuildPosition
	 * @param name
	 */
	public void startUpBuild(String name, boolean isCleanUp)
	{
		if (cStatus == ColonyStatus.NONE)
		{
			this.name = name;
			if (isCleanUp)
			{
				System.out.println("Start Colony Clean "+id);
				cStatus = ColonyStatus.STARTUP;
			} else
			{
				cStatus = ColonyStatus.PREBUILD;
				System.out.println("Start Colony Build "+id);
			}
			// set the World for the relative BuildPosition
			for (BuildPosition aPos : settleSchema.getbPositions())
			{
			  aPos.getPosition().setWorld(this.position.getWorld());
			}
			biomeRequest.add(new BiomeLocation(null, position));
		} else
		{
			System.out.println("Colonist Command Build , disabled, Colonist is already working !!! ");
		}
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
				nextStatus = ColonyStatus.READY;
				this.cStatus = ColonyStatus.WAITBUILD;
				this.markUpStep++;
			}
			break;
		case 2 :
			if (buildManager.getStatus() == BuildStatus.NONE)
			{
				System.out.println("Markup "+markUpStep);
				corner = new LocationData(position.getWorld(), position.getX()-this.settleSchema.getRadius()+1, position.getY(), position.getZ()+this.settleSchema.getRadius()-1);
				buildManager.newBuild(BuildPlanType.PILLAR, corner);
				nextStatus = ColonyStatus.READY;
				this.cStatus = ColonyStatus.WAITBUILD;
				this.markUpStep++;
			}
			break;
		case 3 :
			if (buildManager.getStatus() == BuildStatus.NONE)
			{
				System.out.println("Markup "+markUpStep);
				corner = new LocationData(position.getWorld(), position.getX()+this.settleSchema.getRadius()-1, position.getY(), position.getZ()+this.settleSchema.getRadius()-1);
				buildManager.newBuild(BuildPlanType.PILLAR, corner);
				nextStatus = ColonyStatus.READY;
				this.cStatus = ColonyStatus.WAITBUILD;
				this.markUpStep++;
			}
			break;
		case 4 :
			if (buildManager.getStatus() == BuildStatus.NONE)
			{
				System.out.println("Markup "+markUpStep);
				corner = new LocationData(position.getWorld(), position.getX()+this.settleSchema.getRadius()-1, position.getY(), position.getZ()-this.settleSchema.getRadius()+1);
				buildManager.newBuild(BuildPlanType.PILLAR, corner);
				nextStatus = ColonyStatus.READY;
				this.cStatus = ColonyStatus.WAITBUILD;
				this.markUpStep++;
			}
			break;
			
		default :
			System.out.println("Markup "+markUpStep);
			this.markUpStep++;
			break;
		}
	}
	
	private void doPostBuild(RealmModel rModel)
	{
		superRequest = newSuperRegion;
	}
	
	private void doPrepareArea()
	{
		int radius = prepareRadius; 
		int edge =  radius * 2 -1;
		// erzeuge CleanRequest 
		if (this.prepareLevel < radius)
		{
			for (int i = 0; i < edge; i++)
			{

				if (this.prepareCol < edge)
				{
					LocationData l = new LocationData(position.getWorld(), position.getX(), position.getY(),position.getZ());
					l.setX(l.getX()-radius); 
					l.setY(l.getY()); 
					l.setZ(l.getZ()-radius);

					l.setX(l.getX()+prepareCol); 
					l.setY(l.getY()+prepareLevel); 
					l.setZ(l.getZ()+prepareRow);
					buildManager.getCleanRequest().add(new ItemLocation(Material.AIR ,l));
					
				}
				this.prepareCol++;
			}
			if (this.prepareCol > edge)
			{
				this.prepareRow++;
				this.prepareCol = 0;
			}
			if (this.prepareRow > edge)
			{
				this.prepareLevel++;
				System.out.println(id+"CleanUp  Next Level "+this.prepareLevel);
				this.prepareRow = 0;
			}
		} else
		{
			this.prepareLevel++;
		}
	}
	
	/**
	 * 
	 */
	public void run (RealmModel rModel, Warehouse warehouse)
	{
		LocationData newPos;

		switch (cStatus)
		{
		case STARTUP:
			if (buildManager.getStatus() == BuildStatus.NONE)
			{
				//initialisieren PrePare Modus zum freir�umen des Bauplatzes
				this.isPrepared = false;
				this.prepareLevel = prepareOffset;
				this.prepareRow = 0;
				this.prepareCol = 0;
				this.cStatus = ColonyStatus.PREPARE;
				System.out.println(id+" Prepare  "+this.position.getX()+":"+this.position.getY()+":"+this.position.getZ());

			} else
			{
				System.out.println("Colonist Build canceled");
			}
			break;
		case PREPARE:
			doPrepareArea();
			
			if ((prepareLevel > prepareRadius) && (buildManager.getCleanRequest().isEmpty()))
			{
				isPrepared = true;
			}
			if (isPrepared)
			{
				this.cStatus = ColonyStatus.PREBUILD;

				System.out.println("Colonist set Warehouse");
				for (ItemLocation iLoc   : buildManager.resultBlockRequest())
				{
					if (iLoc.itemRef() != Material.AIR)
					{
						warehouse.depositItemValue(iLoc.itemRef().name(), 1);
					}
				}
				System.out.println("Colonist Prepare  ended normally");
			}
			break;
		case PREBUILD:		// der Bauauftrag startet und bereitet die Baustelle vor
			System.out.println(id+" Build Center "+this.position.getX()+":"+this.position.getY()+":"+this.position.getZ());
			buildManager.newBuild(BuildPlanType.COLONY, this.position);
			nextStatus = ColonyStatus.READY;
			this.cStatus = ColonyStatus.WAITBUILD;
			break;
		case READY :		// der Builder bereitet das Materiallager vor
			if (markUpStep < 5)
			{
				if (biomeRequest.isEmpty() == false)
				{
					if (biomeRequest.get(0).getBiome() == null)
					{
						biome = Biome.SKY;
					} else
					{
						biome = biomeRequest.get(0).getBiome(); 
					}
				}
				markUpSettleSchema();
			} else
			{
				this.cStatus = ColonyStatus.STARTLIST;
			}
			break;
		case STARTLIST: 	// der Builder baut nach BuildPlan
			System.out.println(id+" Build List Start ");
			actualBuildPos = settleSchema.getbPositions().get(0);
			buildPosIndex=0;
			this.cStatus = ColonyStatus.BUILDLIST;
			break;
		case BUILDLIST: 	// der Builder baut nach BuildPlan
			if (buildManager.getStatus() == BuildStatus.NONE)
			{
				actualBuildPos.getPosition().setWorld(this.position.getWorld());
				newPos = new LocationData(position.getWorld(), 
						position.getX()+actualBuildPos.getPosition().getX(), 
						position.getY()+actualBuildPos.getPosition().getY(), 
						position.getZ()+actualBuildPos.getPosition().getZ()
						);
				System.out.println(id+" Build List "+actualBuildPos.getbType()+":"+buildPosIndex);
				buildManager.newBuild(actualBuildPos.getbType(),newPos);
				nextStatus = ColonyStatus.NEXTLIST;
				this.cStatus = ColonyStatus.WAITBUILD;
			}
			break;
		case NEXTLIST:		// der Builder baut nach BuildPlan
			if (buildManager.getStatus() == BuildStatus.NONE)
			{
				buildPosIndex++;
				if (buildPosIndex < settleSchema.getbPositions().size())
				{
					System.out.println(id+" Build List Next ");
					actualBuildPos = settleSchema.getbPositions().get(buildPosIndex);
					this.cStatus = ColonyStatus.BUILDLIST;
				} else
				{
					this.cStatus = ColonyStatus.POSTBUILD;
				}
			}
			break;
		case POSTBUILD:		// der Builder schliesst den Auftrag ab
			//Setzt den request zum create der SuperRegion
			doPostBuild(rModel);
			this.cStatus = ColonyStatus.NEWSETTLE;
			break;
		case NEWSETTLE:
			if (superRequest == null)
			{
				McmdCreateSettle msCreate = new McmdCreateSettle(rModel, name, owner, SettleType.SETTLE_HAMLET,biome);
				rModel.OnCommand(msCreate);
				this.cStatus = ColonyStatus.REINFORCE;
				this.isPrepared = false;
				this.prepareLevel = prepareOffset;
				this.prepareRow = 0;
				this.prepareCol = 0;
				this.prepareRadius = 5;
				System.out.println(id+" Reinforce Colony  "+this.position.getX()+":"+this.position.getY()+":"+this.position.getZ());
			}
			break;
		case REINFORCE:
			doPrepareArea();
			if ((prepareLevel > prepareRadius) && (buildManager.getCleanRequest().isEmpty()))
			{
				isPrepared = true;
			}
			if (isPrepared)
			{
				this.cStatus = ColonyStatus.DONE;
				System.out.println("Colonist Reinforce ended normally");
			}
			break;
		case DONE:			// der Builder beendet den Auftrag.
			Settlement settle = rModel.getSettlements().findName(name);
			if (settle != null)
			{
				for (Item item : warehouse.getItemList().values())
				{
					settle.getWarehouse().depositItemValue(item.ItemRef(), item.value());
				}
				System.out.println("Build FULLFILL ");
				this.cStatus = ColonyStatus.FULFILL;
			} else
			{
				System.out.println("Wait for Create Settlement");
			}
			break;
		case WAIT:			// der Builder wartet auf Material
			this.cStatus = ColonyStatus.NONE;
			break;
		case WAITBUILD:
			if (buildManager.getStatus() == BuildStatus.NONE)
			{
				System.out.println("WaitBuild ENDE ");
				cStatus = nextStatus;
				nextStatus = ColonyStatus.NONE;
			}
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
	public static void initCOUNTER(int cOUNTER)
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

	public String getStatus()
	{
		return cStatus.name();
	}

	public RegionLocation getSuperRequest()
	{
		return superRequest;
	}

	public void setSuperRequest(RegionLocation superRequest)
	{
		this.superRequest = superRequest;
	}

	public ArrayList<BiomeLocation> getBiomeRequest()
	{
		return biomeRequest;
	}
	
}