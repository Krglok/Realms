package net.krglok.realms.data;

import java.awt.Rectangle;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.krglok.realms.Common.ItemPriceList;
import net.krglok.realms.Common.PriceData;
import net.krglok.realms.Common.SQliteConnection;
import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.KingdomList;
import net.krglok.realms.kingdom.Lehen;
import net.krglok.realms.kingdom.LehenList;
import net.krglok.realms.manager.CampPosition;
import net.krglok.realms.manager.CampPositionList;
import net.krglok.realms.npc.EthnosType;
import net.krglok.realms.npc.GenderType;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.npc.NpcAction;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.npc.NpcList;
import net.krglok.realms.npc.NpcNamen;
import net.krglok.realms.science.CaseBook;
import net.krglok.realms.science.CaseBookList;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.RegimentList;
import net.krglok.realms.unit.UnitType;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;

import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.Tile;
import tiled.core.TileLayer;
import tiled.core.TileSet;
import tiled.io.TMXMapReader;

/**
 * <pre>
 * read and write model data to data file. make the model data persistent. 
 * the data are stored in object list. this lists are read from the model to fill with data.
 * the data must be read before initialize the model.
 * Each datalist has a separate data handler.
 * Some write data periodically some only once. 
 * 
 * the initData must be done before initialize the realmModel
 * 
 * @author Windu
 *</pre>
 */
public class DataStorage implements DataInterface
{
	private static final String NPC_0 = "NPC0";

//	private LogList logList;
	private SQliteConnection sql; // = new SQliteConnection(pathName);

	public DBWriteCache writeCache;
	private OwnerList owners ;
	private KingdomList kingdoms ;
	private SettlementList settlements;		// data readed from file
	private RegimentList regiments; 		// data readed from file
	private CaseBookList caseBooks;
    private LehenList lehenList;
    private BuildingList buildings;
    private NpcList npcs;
    private NpcNamen npcNamen;
    private CampPositionList campList;
	
	private PriceData priceData;
	private ItemPriceList priceList ;
	
	private SettlementData settleData;
	private DataStoreRegiment regimentData;
	private DataStoreCaseBook caseBookData;
	private DataStoreOwner ownerData;
	private DataStoreKingdom kingdomData;
	private DataStoreLehen lehenData;
	private DataStoreBuilding buildingData;
	private DataStoreBuilding buildingDataConvert;
	private DataStoreSettlement settlementData;
	private DataStoreNpc npcDataStore;
	private DataStoreNpc npcDataConvert;
	private DataStoreNpcName nameDataStore;
	private DataStoreCampPos campDataStore;

	Boolean isReady = false;
//	Boolean isNpcReady = false;
	
	private String path;
//	private Realms plugin;
	
	public DataStorage(String path) //, LogList logList)
	{
		this.sql = new SQliteConnection(path);
		try
		{
			System.out.println("[REALMS] SQLite database open ");
			if (sql.open() == false)
			{
				System.out.println("[REALMS] SQLite database not initialize !!!");
				isReady = false;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			isReady = false;
		}
		
		this.writeCache = new DBWriteCache(this);
		this.path = path;
		// Datafile Handler
		settleData = new SettlementData(this.path);
		regimentData    = new DataStoreRegiment(this.path);
		caseBookData = new DataStoreCaseBook(this.path);
		ownerData = new DataStoreOwner(this.path);
		kingdomData = new DataStoreKingdom(this.path);
		lehenData = new DataStoreLehen(this.path);
		buildingData = new DataStoreBuilding(this.path, sql);
		buildingDataConvert = new DataStoreBuilding(this.path, null);
		settlementData = new DataStoreSettlement(this.path);
		priceData = new PriceData(this.path);
		nameDataStore = new DataStoreNpcName(this.path);
		campDataStore = new DataStoreCampPos(this.path);
		//DataLists
		priceList = new ItemPriceList();
		campList = new CampPositionList();
		settlements = new SettlementList(0);
		regiments   = new RegimentList(0);
		caseBooks   = new CaseBookList();
		npcDataStore = new DataStoreNpc(this.path, sql);
		npcDataConvert = new DataStoreNpc(this.path, null);

	}
	
	/**
	 * read data from file and store them in the appropriate object list
	 * 
	 * @return  true when all data read
	 */
	public boolean initData()
	{
		isReady = true;
		priceList = priceData.readPriceData();
//		npcOwners();
		initNpcName();
		initCampPos();
		initNpcList();
		initOwnerList();
		initBuildingList();
		
		// converter settlements / buildings
//		if (settlementData.checkSettlements() == false)
//		{
//			System.out.println("[REALMS] Read old Settlement and Convert");
//			initSettleData();
//			settlementData.convertSettlements(settlements);
//			convertBuildings(settlements);
//		} else
		{
			initSettlementList();
		}
		initRegimentList();
		initLehenList(owners);
		initKingdomList(owners);		//		npcRealms(owners.getOwner(NPC_0));
		initCaseBookData(); 
		
		return isReady;
	}
	
//	public Boolean IsNpcReady()
//	{
//		return isNpcReady;
//	}
//
//	public void setIsNpcReady(Boolean isNpcReady)
//	{
//		this.isNpcReady = isNpcReady;
//	}

	/**
	 * write pricelist to datafile
	 * 
	 */
	public void writePriceList()
	{
		priceData.writePriceData(priceList);
	}
	
	/**
	 * must be done at first init for realmModel
	 * here are the NPC owner will be defined
	 */
	public OwnerList npcOwners()
	{
		
		owners.addOwner(new Owner(0, NobleLevel.COMMONER, 0, NPC_0, 1, true,""));
		
		return owners; 
	}
	
	
//	/**
//	 * must be done after initOwners
//	 * here are the NPC kingdoms are defined
//	 */
//	public KingdomList npcRealms(Owner owner)
//	{
//		kingdoms.addKingdom(new Kingdom(1, Realm_1_NPC, owner,true));
//		
//		return kingdoms; 
//	}
		
	private void initSettlementList()
	{
		settlements = new SettlementList();
		ArrayList<String> refList = settlementData.readDataList();
		Settlement settle;
		Owner owner;
		for (String ref : refList)
		{
//			System.out.println("[REALMS read settle :"+ref);
			settle = settlementData.readData(ref);
//			settle.setLogList(logList);
			settle.setBuildingList(buildings.getSubList(settle.getId()));
			settle.initSettlement(priceList);
			if (npcs.isEmpty() == false)
			{
				settle.getResident().setNpcList(this.getNpcs().getSubListSettle(settle.getId()));
				settle.getBarrack().setUnitList(this.getNpcs().getSubListSettleUnits(settle.getId()));
			}
			settlements.putSettlement(settle);
			int ownerId = settle.getOwnerId();
			String ownerName = settle.getOwnerName();
			owner = owners.getOwner(ownerId);
			
			if (ownerName.equalsIgnoreCase(owner.getPlayerName()) == false)
			{
				Owner nOwner = owners.findPlayername(ownerName);
				if (nOwner != null)
				{
					owner = nOwner;
				}
			}
			settle.setOwner(owner);
		}
		// respawn NPC
		if (npcs.isEmpty())
		{
			System.out.println("[REALMS] recalculate NPC list !");
			CreateSettlementNPC();
			for (Settlement settlement : settlements.values())
			{
				settlement.getResident().setNpcList(this.getNpcs().getSubListSettle(settlement.getId()));
			}
		}
		System.out.println("[REALMS] Settlement read :"+settlements.size());

	}

	
	/**
	 * initialize the SettlementList (old version)
	 * must be done after initOwners 
	 * and before initRealms
	 * initialize missed owners in ownerList
	 */
//	private void initSettleData()
//	{
//		ArrayList<String> settleInit = settleData.readSettleList();
//		Settlement settle;
//		Owner owner;
//		for (String settleId : settleInit)
//		{
//			settle = readSettlement(Integer.valueOf(settleId),this.priceList);
//			settle.initSettlement(priceList);
////			plugin.getMessageData().log("SettleRead: "+settleId );
//			System.out.println("[REALMS] read Settle"+settle.getId()+" OwnerId "+"NPC_0");
//			owner = owners.findPlayername(ConfigBasis.NPC_0);
//			settle.setOwner(owner);
//			settle.setOwner(owner);
////			System.out.println("read Settle"+settle.getId()+" OwnerId "+ref);
//			settlements.addSettlement(settle);
//			settle.getResident().setNpcList(this.getNpcs().getSubListSettle(settle.getId()));
//		}
//	}

	/**
	 * write settlement to dataFile
	 * @param settle
	 */
	public void writeSettlement(Settlement settle)
	{
		settlementData.writeData(settle, String.valueOf(settle.getId()));
	}
	
	public void removeSettlement(Settlement settle)
	{
		settlements.removeSettlement(settle.getId());
		settlementData.removeData(settle.getId());
	}

	/**
	 * <pre>
	 * Read Settlement from File
	 * always return a settlement. if id not found a empty settlement are returned
	 *  
	 * @param id
	 * @return the settlement
	 * </pre>
	 */
	private Settlement readSettlement(int id, ItemPriceList priceList)
	{
		return settleData.readSettledata(id, priceList, buildings); //, logList);
	}
	
	/**
	 * write regiment to Datafile
	 * @param regiment
	 */
	public void writeRegiment(Regiment regiment)
	{
		regimentData.writeData(regiment,regiment.getId());
	}
	
	/**
	 * <pre>
	 * read the single Regiment from datafile
	 * read buildPlan from file
	 * always return a regiment. 
	 * if id not found a empty settlement are returned 
	 * @param id
	 * @return the settlement
	 * </pre>
	 */
	private Regiment readRegiment(int id)
	{
		Regiment regiment = regimentData.readData(id);
		if (regiment != null)
		{
			regiment.setBuildPlan(readTMXBuildPlan(BuildPlanType.FORT, 4, 0));
		}
		return regiment; 
	}
	
	/**
	 * read CampPosition from datafile
	 */
	private void initCampPos()
	{
		ArrayList<String> campInit = campDataStore.readDataList();
		for (String refId : campInit)
		{
			CampPosition campPos = campDataStore.readData(refId);
			if (campPos != null)
			{
				campList.putCampPos(campPos);
			}
		}
	}


	public CampPositionList getCampList()
	{
		return campList;
	}

	public void writeCampPosition(CampPosition campPos)
	{
		campDataStore.writeData(campPos, campPos.getId());
	}
	
	public void removeCampPosition(CampPosition campPos)
	{
		campDataStore.removeData(campPos.getId());
	}

	/**
	 * read the regiment list from datafile
	 */
	public void initRegimentList()
	{
		ArrayList<String> regInit = regimentData.readDataList();
		for (String regId : regInit)
		{
//			plugin.getMessageData().log("RegimentRead: "+regId );
			Regiment regiment = readRegiment(Integer.valueOf(regId));
			if (regiment != null)
			{
				regiment.getBarrack().setUnitList(npcs.getSubListRegiment(regiment.getId()));
				regiments.addRegiment(regiment);
			}
		}
	}
	
	
	@Override
	public KingdomList getKingdoms()
	{
		return kingdoms;
	}
	

	@Override
	public OwnerList getOwners()
	{
		return owners;
	}

	@Override
	public ItemPriceList getPriceList()
	{
		return priceList;
	}

	@Override
	public void addPrice(String itemRef, Double price)
	{
		priceList.add(itemRef, price);
		priceData.writePriceData(priceList);
	}

	@Override
	public SettlementList getSettlements()
	{
//		plugin.getMessageData().log("SettleInit: ");
		return settlements;
	}
	
	@Override
	public RegimentList getRegiments()
	{
//		plugin.getMessageData().log("RegimentInit: ");
		return regiments;
	}

	
	
	/**
	 * not all tile ids will converted
	 * not found id return 0 = AIR 
	 * the tileset of the tmx must be mc_tileset.tsx
	 * the tileset image must be mc_tileset.png
	 * @param id
	 * @return
	 */
	private Material tmxToMaterial(int id)
	{
		switch (id)
		{
			case 1: return Material.GRASS;
			case 2: return Material.STONE;
			case 3: return Material.DIRT;
			case 4: return Material.GRASS;
			case 5: return Material.WOOD;
			case 6: return Material.DOUBLE_STEP;
			case 71: return Material.CLAY;
			case 8: return Material.BRICK;
			case 9: return Material.TNT;
			case 12: return Material.WEB;
			case 13: return Material.RED_ROSE;
			case 14: return Material.YELLOW_FLOWER;
			case 16: return Material.SAPLING;
			case 19: return Material.STEP;
			case 20: return Material.BED_BLOCK;
			case 25: return Material.COBBLESTONE;
			case 26: return Material.BEDROCK;
			case 27: return Material.SAND;
			case 28: return Material.GRAVEL;
			case 29: return Material.LOG;
			case 30: return Material.LOG;
			case 31: return Material.WOOL;
			case 32: return Material.GOLD_BLOCK;
			case 33: return Material.DIAMOND_BLOCK;
			case 34: return Material.EMERALD_BLOCK;
			case 35: return Material.REDSTONE_BLOCK;
			case 37: return Material.RED_MUSHROOM;
			case 38: return Material.BROWN_MUSHROOM;
			case 42: return Material.WOOD_STEP;
			case 43: return Material.STONE_SLAB2;
			case 44: return Material.CAKE_BLOCK;
			case 45: return Material.PORTAL;
			case 46: return Material.FENCE;
			case 47: return Material.NETHER_FENCE;
			case 49: return Material.GOLD_ORE;
			case 50: return Material.IRON_ORE;
			case 51: return Material.COAL_ORE;
			case 52: return Material.BOOKSHELF;
			case 53: return Material.MOSSY_COBBLESTONE;
			case 54: return Material.OBSIDIAN;
			case 57: return Material.LONG_GRASS;
			case 60: return Material.WORKBENCH;
			case 61: return Material.FURNACE;
			case 62: return Material.FURNACE;
			case 63: return Material.DISPENSER;
			case 64: return Material.DISPENSER;
			case 65: return Material.REDSTONE_TORCH_ON;
			case 67: return Material.DIODE_BLOCK_OFF;
			case 68: return Material.WOOD_BUTTON;
			case 69: return Material.TORCH;
			case 70: return Material.LEVER;
			case 72: return Material.DIODE_BLOCK_OFF;
			case 73: return Material.SPONGE;
			case 74: return Material.GLASS;
			case 75: return Material.DIAMOND_ORE;
			case 76: return Material.REDSTONE_ORE;
			case 77: return Material.LEAVES;
			case 78: return Material.LEAVES_2;
			case 82: return Material.DAYLIGHT_DETECTOR;
			case 84: return Material.WORKBENCH;
			case 85: return Material.WORKBENCH;
			case 86: return Material.FURNACE;
			case 87: return Material.FURNACE;
			case 88: return Material.SAPLING;
			case 91: return Material.WALL_SIGN;
			case 92: return Material.FIRE;
			case 94: return Material.LAVA;
			case 95: return Material.WATER;
			case 97: return Material.WOOL;
			case 99: return Material.SNOW_BLOCK;
			case 100: return Material.ICE;
			case 101: return Material.SOIL;
			case 102: return Material.CACTUS;
			case 103: return Material.CACTUS;
			case 105: return Material.CLAY;
			case 106: return Material.SUGAR_CANE_BLOCK;
			case 107: return Material.NOTE_BLOCK;
			case 108: return Material.NOTE_BLOCK;
			case 109: return Material.WATER_LILY;
			case 110: return Material.MYCEL;
			case 111: return Material.MYCEL;
			case 118: return Material.WEB;
			case 121: return Material.TORCH;
			case 122: return Material.WOOD_DOOR;
			case 123: return Material.IRON_DOOR_BLOCK;
			case 124: return Material.LADDER;
			case 125: return Material.TRAP_DOOR;
			case 126: return Material.IRON_BARDING;
			case 129: return Material.WHEAT;
			case 130: return Material.WHEAT;
			case 131: return Material.WHEAT;
			case 132: return Material.WHEAT;
			case 133: return Material.WHEAT;
			case 134: return Material.WHEAT;
			case 135: return Material.WHEAT;
			case 136: return Material.WHEAT;
			case 137: return Material.BREWING_STAND;
			case 139: return Material.FENCE_GATE;
			case 140: return Material.LEAVES;
			case 141: return Material.LEAVES_2;
			case 142: return Material.CHEST;
			case 143: return Material.ENDER_CHEST;
			case 145: return Material.LEVER;
			case 146: return Material.WOOD_DOOR;
			case 147: return Material.IRON_DOOR_BLOCK;
			case 151: return Material.PUMPKIN;
			case 152: return Material.NETHERRACK;
			case 153: return Material.SOUL_SAND;
			case 154: return Material.GLOWSTONE;
			case 161: return Material.COCOA;
			case 162: return Material.CHEST;
			case 163: return Material.CHEST;
			case 164: return Material.CHEST;
			case 165: return Material.CHEST;
			case 166: return Material.CHEST;
			case 167: return Material.CHEST;
			case 168: return Material.CHEST;
			case 412: return Material.THIN_GLASS;

		
		default : return Material.AIR;
		}
		
	}

    private int getGid(Tile tile) {
        TileSet tileset = tile.getTileSet();
        if (tileset != null) {
            return tile.getId() + 1; //getFirstGidForTileset(tileset);
        }
        return tile.getId();
    }

    /**
     * the tileset of the tmx must be mc_tileset.tsx
	 * the tileset image must be mc_tileset.png
	 * the tsx and the image file must stay in the some 
	 * directory as the tmx files 
	 * errorHandling: if file not exist, empty map returned
     * @param bType
     * @param radius
     * @param offSet
     * @param path
     * @return
     */
	public BuildPlanMap readTMXBuildPlan(BuildPlanType bType, int radius, int offSet)
	{
//		String path = plugin.getDataFolder().getPath();
        File tmxFolder = new File(path, "buildplan");
        File tmxFile = new File(tmxFolder.getAbsoluteFile(), bType.name()+".tmx");
		BuildPlanMap buildPlan = new BuildPlanMap(bType,radius , offSet);
		TMXMapReader mapReader = new TMXMapReader();
		String filename =  tmxFile.getAbsolutePath();
		Map tmxMap = null;
		try
		{
			tmxMap =  mapReader.readMap(filename);
			String sOffSet = tmxMap.getProperties().getProperty("offset");
//			System.out.println(tmxMap.getFilename()+":"+tmxMap.getHeight()+":"+tmxMap.getWidth()+":"+sOffSet);
			if (sOffSet != null)
			{
				offSet = Integer.valueOf(sOffSet);
			}
			radius = (tmxMap.getWidth()+1) / 2; 
			buildPlan.setRadius(radius);
			buildPlan.setOffsetY(offSet);
			byte [][][] newCube = buildPlan.initCube(tmxMap.getWidth());
			int level = 0;
	 		for (MapLayer layer :tmxMap.getLayers())
			{
	 			
//				System.out.println(layer.getName()+":"+level+":"+layer.getHeight()+":"+layer.getWidth());
		        Rectangle bounds = layer.getBounds();
				TileLayer tl = (TileLayer) layer;
	            for (int y = 0; y < layer.getHeight(); y++) 
	            {
	                for (int x = 0; x < layer.getWidth(); x++) 
	                {
	                    Tile tile = tl.getTileAt(x + bounds.x, y + bounds.y);
	                    int gid = 0;
	
	                    if (tile != null) 
	                    {
	                        gid = getGid(tile);
	                    }
	                    newCube[level][y][x] = ConfigBasis.getMaterialId(tmxToMaterial(gid));
	                }
	            }
	            level++;
				
			}
			buildPlan.setCube(newCube);
		} catch (Exception e)
		{
			System.out.println("[REALMS] TMX File not found :"+filename);
//			e.printStackTrace();
		}
		return buildPlan;
	}
	
	private CaseBook readCaseBook(String id)
	{
		return caseBookData.readData(id);
	}
	
	private void initCaseBookData()
	{
		
		caseBooks = new CaseBookList(); 
		ArrayList<String> sList = caseBookData.readDataList();
		for (String refId : sList)
		{
			CaseBook caseBook = caseBookData.readData(refId);
			caseBooks.addBook(caseBook); 
		}
	}
	
	/**
	 * 
	 * read buildings from yml or sql
	 * initiate convert yml > sql
	 * 
	 */
	private void initBuildingList()
	{
		
		buildings = new BuildingList();
		Building building;
		if (buildingData.isSql)
		{
			ResultSet result = buildingData.readDataList(0);
			try
			{
				// check for conversion
				if (result.next() == false)
				{
					System.out.println("[REALMS] Buildings Datbase Convert started ");
					ArrayList<String> convertList = buildingDataConvert.readDataList();
					if (convertList.size() > 0)
					{
						for (String ref : convertList)
						{
							building = buildingDataConvert.readData(ref);
							if (building != null)
							{
								buildings.putBuilding(building);
								writeCache.addCache(DBCachType.BUILDING, building.getId());
							}
						}
					} else
					{
						System.out.println("[REALMS] Building Datbase Convert NOT necessary ");
					}
				} else
				{
					// convert sql field to MemorySection
					buildingData.config.loadFromString(result.getString(2));
					ConfigurationSection section = buildingData.config.getRoot();
					// make instance from MemorySection
					building = buildingData.initDataObject(section);
					buildings.putBuilding(building);
					while (result.next())
					{
						buildingData.config.loadFromString(result.getString(2));
						section = buildingData.config.getRoot();
						building = buildingData.initDataObject(section);
						buildings.putBuilding(building);
					}
					System.out.println("[REALMS] Building Datbase read "+buildings.size());
				} 
			} catch (SQLException | InvalidConfigurationException e)
			{
				e.printStackTrace();
			}
		} else
		{
			ArrayList<String> refList = buildingData.readDataList();
			for (String ref : refList)
			{
				building = buildingData.readData(ref);
				if (building.getPosition().getY() == 0.0)
				{
					System.out.println("[REALMS] Wrong position building :"+building.getId());
				}
				buildings.putBuilding(building);
			}
			System.out.println("[REALMS] Building read "+buildings.size());
		}
	}

	private void initOwnerList()
	{
		owners = new OwnerList();
		Owner owner ;
		
		ArrayList<String> refList = ownerData.readDataList();
		
		for (String ref : refList)
		{
			owner = ownerData.readData(ref);
			owners.addOwner(owner);
		}
		if (owners.getOwner(ConfigBasis.NPC_0) == null)
		{
			owner = Owner.initDefaultOwner();
			owner.initMayor();
			owners.addOwner(owner);
			writeOwner(owner);
		}
	}

	public void initLehenList(OwnerList owners)
	{
		lehenList = new LehenList();
		Lehen lehen;
		ArrayList<String> refList = lehenData.readDataList();
		for (String ref : refList)
		{
			lehen = lehenData.readData(ref);
			lehen.setOwner(owners.getOwner(lehen.getOwnerId()));
			lehen.setBuildingList(buildings.getSubList(lehen));
			lehen.initLehen(priceList);
			if (npcs.isEmpty() == false)
			{
				lehen.getResident().setNpcList(this.getNpcs().getSubListLehen(lehen.getId()));
//				System.out.println("Lehen "+lehen.getId()+"  resident: "+lehen.getResident().getNpcList().size());
				lehen.getBarrack().setUnitList(this.getNpcs().getSubListLehenUnits(lehen.getId()));
			}

			lehenList.putLehen(lehen);
		}
	}


	private void initKingdomList(OwnerList owners)
	{
		kingdoms = new KingdomList();
		Kingdom kingdom;
		
		ArrayList<String> refList = kingdomData.readDataList();
		
		for (String ref : refList)
		{
			kingdom = kingdomData.readData(ref);
			kingdom.setOwner(owners.getOwner(kingdom.getOwnerId()));
			kingdom.initMembers(owners);
			kingdoms.addKingdom(kingdom);
		}
		
		if (kingdoms.getKingdom(0) == null)
		{
			kingdom = Kingdom.initDefaultKingdom(owners);
			kingdoms.addKingdom(kingdom);
			kingdomData.writeData(kingdom, String.valueOf(kingdom.getId()));
			System.out.println("[REALMS] Kingdom 0 init ");
		}
	}

	/*
	 * read buildings from yml or sql
	 * initiate convert yml > sql
	 * 
	 */
	private void initNpcList()
	{
		npcs = new NpcList();
		NpcData npc;
		if (npcDataStore.isSql)
		{
			ResultSet result = npcDataStore.readDataList(0);
			try
			{
				if (result.next() == false)
				{
					System.out.println("[REALMS] Npc Datbase Convert started ");
					ArrayList<String> convertList = npcDataConvert.readDataList();
					if (convertList.size() > 0)
					{
						for (String ref : convertList)
						{
							npc = npcDataConvert.readData(ref);
							if (npc != null)
							{
								npcs.putNpc(npc);
								writeCache.addCache(DBCachType.NPC, npc.getId());
							}
						}
					} else
					{
						System.out.println("[REALMS] Npc Datbase Convert NOT necessary ");
					}
				} else
				{
					npcDataStore.config.loadFromString(result.getString(2));
					ConfigurationSection section = npcDataStore.config.getRoot();
					npc = npcDataStore.initDataObject(section);
					npcs.putNpc(npc);
					while (result.next())
					{
						npcDataStore.config.loadFromString(result.getString(2));
						section = npcDataStore.config.getRoot();
						npc = npcDataStore.initDataObject(section);
						npcs.putNpc(npc);
					}
					System.out.println("[REALMS] Npc Database read :"+npcs.size());
				} 
			} catch (SQLException | InvalidConfigurationException e)
			{
				e.printStackTrace();
			}
		} else
		{
			ArrayList<String> refList = npcDataStore.readDataList();
			for (String ref : refList)
			{
				npc = npcDataStore.readData(ref);
				npcs.putNpc(npc);
			}
		}
	}
	

	/**
	 * read npc names from yml or initialize default name list
	 */
	private void initNpcName()
	{
		npcNamen = nameDataStore.readData();
		if (npcNamen == null)
		{
			npcNamen = new NpcNamen();
		}
		if (npcNamen.getManNames().isEmpty())
		{
			List<String> womanNames = new ArrayList<String>();
			List<String> manNames = new ArrayList<String>();

			womanNames.add("Brorda");
			womanNames.add("Brunhild");
			womanNames.add("Bryce");
			womanNames.add("Clotilde");
			womanNames.add("Ceawlin");
			womanNames.add("Cedric");
			womanNames.add("Cenred");
			womanNames.add("Cenwealh");
			womanNames.add("Cenwulf");
			womanNames.add("Creoda");
			womanNames.add("Cristiana");
			womanNames.add("Cutha");
			womanNames.add("Dimia");
			womanNames.add("Eawa");
			womanNames.add("Ecgfrith");

			manNames.add("Burghred");
			manNames.add("Cadfil");
			manNames.add("Carac");
			manNames.add("Cassius");
			manNames.add("Catrain");
			manNames.add("Ceolwulf");
			manNames.add("Cerdic");
			manNames.add("Cuthwine");
			manNames.add("Cynric");
			manNames.add("Dain");
			manNames.add("Destrian");
			manNames.add("Donald");
			manNames.add("Doran");
			manNames.add("Eadbald");
			manNames.add("Eadberht");
			manNames.add("Eadred");
			manNames.add("Eadric");
			manNames.add("Eadwig");
			manNames.add("Eanulf");
			npcNamen.setManNames(manNames);
			npcNamen.setWomanNames(womanNames);
			nameDataStore.writeData(npcNamen);
		}
	}
	
	@Override
	public CaseBookList getCaseBooks()
	{
//		plugin.getMessageData().log("CaseBook Init: " );
		return caseBooks;
	}

	@Override
	public void writeCaseBook(CaseBook caseBook)
	{
		caseBookData.writeData(caseBook, caseBook.getRefId());
	}

	@Override
	public BuildingList getBuildings()
	{
		return buildings;
	}

	@Override
	public LehenList getLehen()
	{
		return lehenList;
	}

	public void writeCache(DBCachRef ref)
	{
		long time1 = System.nanoTime();
		long time2 ;
		switch(ref.getRef())
		{
		case NPC:
			NpcData npc = npcs.get(ref.getId());
			if (npc != null)
			{
				npcDataStore.writeData(npc,ref.getId());
			}
			time2 = System.nanoTime();
				
//		    System.out.println("CacheWrite Npc: "+writeCache.size()+" Time [ms]: "+(time2 - time1)/1000000);
			break;
		case SETTLEMENT:
			Settlement settle = settlements.getSettlement(ref.getId());
			if (settle != null)
			{
				settlementData.writeData(settle,ref.getId());
			}
			time2 = System.nanoTime();
		    System.out.println("[REALMS] CacheWrite Settle: "+writeCache.size()+" Time [ms]: "+(time2 - time1)/1000000);
			break;
		case BUILDING:
			Building building = buildings.getBuilding(ref.getId());
			if (building != null)
			{
				buildingData.writeData(building,ref.getId());
			}
			time2 = System.nanoTime();
//		    System.out.println("CacheWrite Building: "+writeCache.size()+" Time [ms]: "+(time2 - time1)/1000000);
			break;
		default:
			break;
		}

	}
	
	private void printBuildingRow(Building building)
	{
		System.out.print(building.getId()+" | "+building.getHsRegion()+" | "+building.getBuildingType()+" | "+building.getSettleId());
	}
	
	public void convertBuildings(SettlementList sList)
	{
		System.out.println("[REALMS] Convert BuildingList : ["+sList.size()+"]");
		for (Settlement settle : sList.values())
		{
			System.out.println("Settle :"+settle.getId()+" | "+settle.getName());
			for (Building building : settle.getBuildingList().values())
			{
				building.setSettleId(settle.getId());
				building.setOwnerId(settle.getOwnerId());
				printBuildingRow(building);
				writeBuilding(building);
			}
		}
		if (npcs.isEmpty())
		{
			System.out.println("[REALMS] recalculate NPC list !");
			CreateSettlementNPC();
			for (Settlement settlement : settlements.values())
			{
				settlement.getResident().setNpcList(this.getNpcs().getSubListSettle(settlement.getId()));
			}
		}
		
	}
	
	@Override
	public void writeBuilding(Building building)
	{
		if (buildingData.isSql)
		{
			writeCache.addCache(DBCachType.BUILDING, building.getId());
		} else
		{
			buildingData.writeData(building, String.valueOf(building.getId()));
		}
	}
	
	public void removeBuilding(Building building)
	{
		buildingData.removeData(building.getId());
		buildings.removeBuilding(building);
	}

	@Override
	public void writeKingdom(Kingdom kingdom)
	{
		kingdomData.writeData(kingdom, String.valueOf(kingdom.getId()));
	}

	@Override
	public void writeLehen(Lehen lehen)
	{
		lehenData.writeData(lehen, String.valueOf(lehen.getId()));
	}

	@Override
	public void writeOwner(Owner owner)
	{
		ownerData.writeData(owner, String.valueOf(owner.getId()));
	}

	@Override
	public NpcList getNpcs()
	{
		return npcs;
	}

	@Override
	public void writeNpc(NpcData npc)
	{
		if (npcDataStore.isSql)
		{
			writeCache.addCache(DBCachType.NPC, npc.getId());
		} else
		{
			npcDataStore.writeData(npc,  String.valueOf(npc.getId()));
		}
	}

	@Override
	public NpcNamen getNpcName()
	{
		return npcNamen;
	}

	private int makeOlder()
	{
		int maxValue = 9;
		int index =  (int) Math.rint(Math.random() * maxValue);
		return index;
	}

	private int makeChildOlder()
	{
		int maxValue = 13;
		int index =  (int) Math.rint(Math.random() * maxValue);
		return index;
	}
	
	private void makePair(NpcData man, NpcData woman)
	{
		man.setMaried(true);
		man.setNpcHusband(woman.getId());
		woman.setMaried(true);
		woman.setNpcHusband(man.getId());
	}
	
	private NpcData makeFather(NpcNamen npcNameList)
	{
		NpcData npc = new NpcData();
		String npcName = npcNameList.findName(GenderType.MAN);
		npc.setNpcType(NPCType.SETTLER);
		npc.setName(npcName);
		npc.setGender(GenderType.MAN);
		npc.setAge(20+makeOlder());
		return npc;
	}

	private NpcData makeMother(NpcNamen npcNameList)
	{
		NpcData npc = new NpcData();
		String npcName = npcNameList.findName(GenderType.WOMAN);
		npc.setNpcType(NPCType.SETTLER);
		npc.setName(npcName);
		npc.setGender(GenderType.WOMAN);
		npc.setAge(20+makeOlder());
		return npc;
	}
	
	
	/**
	 * create a complete family for a urban settlement home
	 * This methode is for creating new urban settlement uesd
	 * 
	 * @param building
	 * @param npcNameList
	 * @param maxChild
	 */
	public void makeFamily(Building building, NpcNamen npcNameList, int maxChild)
	{
		int max = ConfigBasis.getDefaultSettler(building.getBuildingType());
		NpcData father = makeFather(npcNameList);
		father.setSettleId(building.getSettleId());
		father.setHomeBuilding(building.getId());
		father.setNpcAction(NpcAction.NONE);
		this.getNpcs().add(father);
		NpcData mother = makeMother(npcNameList);
		mother.setSettleId(building.getSettleId());
		mother.setHomeBuilding(building.getId());
		mother.setNpcAction(NpcAction.NONE);
		makePair(father, mother);
		this.getNpcs().add(mother);
		this.writeNpc(father);
		this.writeNpc(mother);
		NpcData child1;
		NpcData child2;
		NpcData child3;
		
		switch(maxChild)
		{
		case 0:
			break;
		case 2 :
			child1 = NpcData.makeChild(npcNameList,father.getId(),mother.getId());
			child1.setAge(1+makeChildOlder()+1);
			child1.setSettleId(building.getSettleId());
			child1.setHomeBuilding(building.getId());
			child1.setNpcAction(NpcAction.NONE);
			this.getNpcs().add(child1);
			this.writeNpc(child1);
			child2 = NpcData.makeChild(npcNameList,father.getId(),mother.getId());
			child2.setAge(1+makeChildOlder()+2);
			child2.setSettleId(building.getSettleId());
			child2.setHomeBuilding(building.getId());
			child2.setNpcAction(NpcAction.NONE);
			this.getNpcs().add(child2);
			this.writeNpc(child2);
		break;
		case 3 :
			child1 = NpcData.makeChild(npcNameList,father.getId(),mother.getId());
			child1.setAge(1+makeChildOlder()+1);
			child1.setSettleId(building.getSettleId());
			child1.setHomeBuilding(building.getId());
			child1.setNpcAction(NpcAction.NONE);
			this.getNpcs().add(child1);
			this.writeNpc(child1);
			child2 = NpcData.makeChild(npcNameList,father.getId(),mother.getId());
			child2.setAge(1+makeChildOlder()+2);
			child2.setSettleId(building.getSettleId());
			child2.setHomeBuilding(building.getId());
			child2.setNpcAction(NpcAction.NONE);
			this.getNpcs().add(child2);
			this.writeNpc(child2);
			child3 = NpcData.makeChild(npcNameList,father.getId(),mother.getId());
			child3.setSettleId(building.getSettleId());
			child3.setHomeBuilding(building.getId());
			child3.setNpcAction(NpcAction.NONE);
			this.getNpcs().add(child3);
			this.writeNpc(child3);
		break;
		default :
			child1 = NpcData.makeChild(npcNameList,father.getId(),mother.getId());
			child1.setAge(1+makeChildOlder()+1);
			child1.setSettleId(building.getSettleId());
			child1.setHomeBuilding(building.getId());
			child1.setNpcAction(NpcAction.NONE);
			this.getNpcs().add(child1);
			this.writeNpc(child1);
		break;
		}
	}
	
	/**
	 * create the 5 Managers for a urban Settlement.
	 * This methode is for creating new urban settlement uesd
	 * 
	 * @param building
	 * @param npcNameList
	 */
	public void makeManager(Building building, NpcNamen npcNameList)
	{
		int max = 5;
		NpcData father = makeFather(npcNameList);
		father.setName("Elder "+father.getName());
		father.setImmortal(true);
		father.setNpcAction(NpcAction.NONE);
		father.setNpcType(NPCType.MANAGER);
		father.setSettleId(building.getSettleId());
		father.setHomeBuilding(building.getId());
		this.getNpcs().add(father);
		this.writeNpc(father);

		father = makeFather(npcNameList);
		father.setImmortal(true);
		father.setNpcAction(NpcAction.NONE);
		father.setNpcType(NPCType.BUILDER);
		father.setName("Elder "+father.getName());
		father.setSettleId(building.getSettleId());
		father.setHomeBuilding(building.getId());
		this.getNpcs().add(father);
		this.writeNpc(father);
		
		NpcData mother = makeMother(npcNameList);
		mother.setImmortal(true);
		mother.setNpcAction(NpcAction.NONE);
		mother.setNpcType(NPCType.CRAFTSMAN);
		mother.setName("Elder "+mother.getName());
		mother.setSettleId(building.getSettleId());
		mother.setHomeBuilding(building.getId());
		this.getNpcs().add(mother);
		this.writeNpc(mother);

		mother = makeMother(npcNameList);
		mother.setImmortal(true);
		mother.setNpcAction(NpcAction.NONE);
		father.setNpcType(NPCType.FARMER);
		mother.setName("Elder "+mother.getName());
		mother.setSettleId(building.getSettleId());
		mother.setHomeBuilding(building.getId());
		this.getNpcs().add(mother);
		this.writeNpc(mother);

		mother = makeMother(npcNameList);
		mother.setImmortal(true);
		mother.setNpcAction(NpcAction.NONE);
		father.setNpcType(NPCType.MAPMAKER);
		mother.setName("Elder "+mother.getName());
		mother.setSettleId(building.getSettleId());
		mother.setHomeBuilding(building.getId());
		this.getNpcs().add(mother);
		this.writeNpc(mother);
		
	}

	public void makeVillager(Building building,NpcNamen npcNameList)
	{
		NpcData npc = new NpcData();
		String npcName = npcNameList.findName(GenderType.MAN);
		npc.setNpcType(NPCType.SETTLER);
		npc.setName(npcName);
		npc.setEthno(EthnosType.VILLAGER);
		npc.setGender(GenderType.MAN);
		npc.setAge(20+makeOlder());
		npc.setSettleId(building.getSettleId());
		npc.setHomeBuilding(building.getId());
		this.getNpcs().add(npc);
		this.writeNpc(npc);
	}
	
	public void makeVillageManager(Building building, NpcNamen npcNameList)
	{
		int max = 5;
		NpcData father = makeFather(npcNameList);
		father.setName("Elder "+father.getName());
		father.setEthno(EthnosType.VILLAGER);
		father.setImmortal(true);
		father.setNpcAction(NpcAction.NONE);
		father.setNpcType(NPCType.MANAGER);
		father.setSettleId(building.getSettleId());
		father.setHomeBuilding(building.getId());
		this.getNpcs().add(father);
		this.writeNpc(father);
	}
	
	/**
	 * create a noble family with the given nobleLevel.
	 * No children are generated
	 * create a noble Commoner as a servant
	 * This methode is for creating new Lehen uesd
	 * 
	 * @param building
	 * @param npcNameList
	 * @param nobleLevel
	 */
	public void makeNobleFamily(Building building, NpcNamen npcNameList, NobleLevel nobleLevel )
	{
//		int max = ConfigBasis.getDefaultSettler(building.getBuildingType());
		NpcData father = makeFather(npcNameList);
		father.setLehenId(building.getLehenId());
		father.setHomeBuilding(building.getId());
		father.setNpcAction(NpcAction.NONE);
		father.setNpcType(NPCType.NOBLE);
		father.setNoble(nobleLevel);
		father.setMoney(1000.0);
		this.getNpcs().add(father);
		NpcData mother = makeMother(npcNameList);
		mother.setLehenId(building.getLehenId());
		mother.setHomeBuilding(building.getId());
		mother.setNpcType(NPCType.NOBLE);
		mother.setNoble(nobleLevel);
		mother.setNpcAction(NpcAction.NONE);
		mother.setMoney(1000.0);

		makePair(father, mother);
		this.getNpcs().add(mother);
		this.writeNpc(father);
		this.writeNpc(mother);
		
		NpcData servant = makeFather(npcNameList);
		servant.setGender(NpcData.findGender());
		servant.setName(npcNameList.findName(servant.getGender()));
		servant.setNpcType(NPCType.SETTLER);
		// the nobleLevel always COMMONER
		servant.setNoble(NobleLevel.COMMONER);
		servant.setMoney(10.0);
		this.getNpcs().add(servant);
		
	}
	
	private int checkBuildingNpc(Building building, NpcNamen npcNameList)
	{
		int max = 0;
		int child = 0;
		max = ConfigBasis.getDefaultSettler(building.getBuildingType());
		if((BuildPlanType.getBuildGroup(building.getBuildingType()) == 100 )
			|| (BuildPlanType.getBuildGroup(building.getBuildingType()) == 200 )
			)
		{
			if (max > 0)
			{
				child = (max - 2) / 2;
				makeFamily(building, npcNameList, child); 
				return (2 + child);
			}
		}
		if ((building.getBuildingType() == BuildPlanType.HALL)
			|| (building.getBuildingType() == BuildPlanType.TOWNHALL)
			)
		{
			makeManager(building, npcNameList);
			return 5;
		}
		return 0;
	}

	/**
	 * setup new npc for all settlements
	 */
	public void CreateSettlementNPC()
	{
		int counter = 0;
		int settlerCount = 0;
		int bcount = 0;
		for (Settlement settle : settlements.values())
		{
			counter = 0;
//			counter = counter + (settle.getResident().getSettlerCount()/4*3);
			bcount = bcount + settle.getBuildingList().size();
			for (Building building : settle.getBuildingList().values())
			{
				if ((building.getBuildingType() != BuildPlanType.HALL)
					&& (building.getBuildingType() != BuildPlanType.TOWNHALL)
					)
				{
					if (settle.getResident().oldPopulation >  (counter+3))
					{
						// erstellt die Siedler
						counter = counter + checkBuildingNpc(building, npcNamen);
					}
				} else
				{
					// erstellt die Verwalter
					counter = counter + checkBuildingNpc(building, npcNamen);				}
			}
			settlerCount = settlerCount + counter;
		}
	}

	/**
	 * generate 10 MILITIA for a new regiment
	 * @param regiment
	 */
	public void makeRaiderUnits(Regiment regiment)
	{
		for (int i=0; i< 10; i++)
		{
			makeRaiderUnit( UnitType.MILITIA, regiment);
		}
		
	}
	
	/**
	 * </pre>
	 * create a unit by type:
	 * - MILITIA
	 * - ARCHER
	 * add unit to the npc list and trhe unitlist of regiment
	 * 
	 * @param nextUnit
	 * @param regiment
	 * @return
	 * </pre>
	 */
	private void makeRaiderUnit(UnitType nextUnit, Regiment regiment)
	{
    	ArrayList<String> msg = new ArrayList<String>();
    	NpcData settleNpc = null;
    	String npcName = "";
    	switch(nextUnit)
    	{
    	case MILITIA:
			settleNpc = new NpcData();
			settleNpc.setGender(GenderType.MAN);
			npcName = npcNamen.findName(settleNpc.getGender());
			settleNpc.setName(npcName);
			settleNpc.setNpcType(NPCType.MILITARY);
			settleNpc.setUnitType(nextUnit);
			settleNpc.setSettleId(0);
			settleNpc.setHomeBuilding(0);
			settleNpc.setRegimentId(regiment.getId());
			settleNpc.setAge(21);
			settleNpc.setMoney(10.0);
			npcs.add(settleNpc);
			regiment.getBarrack().setUnitList(npcs.getSubListRegiment(regiment.getId()));
			writeNpc(settleNpc);
    		break;
    	case ARCHER:
			settleNpc = new NpcData();
			settleNpc.setGender(GenderType.MAN);
			npcName = npcNamen.findName(settleNpc.getGender());
			settleNpc.setName(npcName);
			settleNpc.setNpcType(NPCType.MILITARY);
			settleNpc.setUnitType(nextUnit);
			settleNpc.setSettleId(0);
			settleNpc.setHomeBuilding(0);
			settleNpc.setRegimentId(regiment.getId());
			settleNpc.setAge(21);
			settleNpc.setMoney(10.0);
			npcs.add(settleNpc);
			regiment.getBarrack().setUnitList(npcs.getSubListRegiment(regiment.getId()));
			writeNpc(settleNpc);
    		break;
    	default:
    		break;
    	}
	}
	
}
