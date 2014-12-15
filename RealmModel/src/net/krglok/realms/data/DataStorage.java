package net.krglok.realms.data;

import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;

import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.Tile;
import tiled.core.TileLayer;
import tiled.core.TileSet;
import tiled.io.TMXMapReader;

import net.krglok.realms.Realms;
import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.MemberList;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.KingdomList;
import net.krglok.realms.npc.NPCData;
import net.krglok.realms.science.CaseBook;
import net.krglok.realms.science.CaseBookList;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.RegimentList;

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
//	private static final String NPC_1 = "NPC1";
//	private static final String NPC_2 = "NPC2";
//	private static final String NPC_4 = "NPC4";
//	private static final String PC_3 = "NPC3";
//	private static final String PC_4 = "NPC4";
//	private static final String PC_5 = "NPC5";
	private static final String Realm_1_NPC = "Realm 1 NPC";

	private OwnerList owners ;
	private KingdomList kingdoms ;
	private SettlementList settlements;		// data readed from file
	private RegimentList regiments; 		// data readed from file
	private CaseBookList caseBooks;
	
	private PriceData priceData;
	private ItemPriceList priceList ;
	
	private SettlementData settleData;
	private RegimentData regData;
	private DataStoreCaseBook caseBookData;
	private NPCListData npcListData;

	Boolean isReady = false;
	
	private String path;
//	private Realms plugin;
	
	public DataStorage(String path)
	{
//		this.plugin = plugin;
		this.path = path;
		// Datafile Handler
		settleData = new SettlementData(this.path);
		regData    = new RegimentData(this.path);
		caseBookData = new DataStoreCaseBook(this.path);
		//DataLists
		owners = new OwnerList();
		kingdoms = new KingdomList();
		settlements = new SettlementList(0);
		regiments   = new RegimentList(0);
		caseBooks   = new CaseBookList();
		priceData = new PriceData(this.path);
		priceList = new ItemPriceList();
		npcListData = new NPCListData(this.path, true);
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
		npcOwners();
		npcRealms(owners.getOwner(NPC_0));
		ArrayList<String> settleInit = settleData.readSettleList();
		if (initSettlementData(settleInit) == false)
		{
			System.out.println("Settlement Read FALSE: ");
			isReady = false;
		}
		ArrayList<String> regInit = regData.readRegimentList();
		if (initRegimentData(regInit) == false)
		{
			System.out.println("Regiment Read FALSE: ");
			isReady = false;
		}
		ArrayList<String> bookInit = caseBookData.readDataList();
		if (initCaseBookData(bookInit) == false)
		{
			System.out.println("CaseBook Read FALSE: ");
			isReady = false;
		}
		
		return isReady;
	}
	
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
	
	
	/**
	 * must be done after initOwners
	 * here are the NPC kingdoms are defined
	 */
	public KingdomList npcRealms(Owner owner)
	{
		kingdoms.addKingdom(new Kingdom(1, Realm_1_NPC, owner,true));
		
		return kingdoms; 
	}
	
	/**
	 * read the npc from file 
	 * need no init of dataStorage
	 * 
	 * @return list from file or new empty list
	 */
	public HashMap<Integer, NPCData> readNpcList()
	{
			return npcListData.readNpcList();
	}
	
	/**
	 * the methode write the whole npcList to the file
	 * the npcId is the refenrenz
	 * @param data
	 */
	public void writeNpc(HashMap<Integer, NPCData> dataObject)
	{
		String refId = "0";
		for (NPCData npcData : dataObject.values())
		{
			refId = String.valueOf(npcData.getNpcId());
			npcListData.writeNpc(npcData, refId);
		}
	}
	
	
	/**
	 * initialize the SettlementList
	 * must be done after initOwners and initRealms
	 */
	private boolean initSettlementData(ArrayList<String> settleInit)
	{
		for (String settleId : settleInit)
		{
//			plugin.getMessageData().log("SettleRead: "+settleId );
			settlements.addSettlement(readSettlement(Integer.valueOf(settleId),this.priceList));
		}
		return true;
	}

	/**
	 * write settlement to dataFile
	 * @param settle
	 */
	public void writeSettlement(Settlement settle)
	{
		settleData.writeSettledata(settle);
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
		return settleData.readSettledata(id, priceList);
	}
	
	/**
	 * write regiment to Datafile
	 * @param regiment
	 */
	public void writeRegiment(Regiment regiment)
	{
		regData.writeRegimentData(regiment);
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
		Regiment regiment = regData.readRegimentData(id); 
		regiment.setBuildPlan(readTMXBuildPlan(BuildPlanType.FORT, 4, 0));
		return regiment; 
	}

	/**
	 * read the regiment list from datafile
	 * @param regInit
	 * @return
	 */
	public boolean initRegimentData(ArrayList<String> regInit)
	{
		for (String regId : regInit)
		{
//			plugin.getMessageData().log("RegimentRead: "+regId );
			regiments.addRegiment(readRegiment(Integer.valueOf(regId)));
		}
		return true;
	}
	
	@Override
	public KingdomList initKingdoms()
	{
		// TODO Auto-generated method stub
		return kingdoms;
	}
	

	@Override
	public OwnerList initOwners()
	{
		// TODO Auto-generated method stub
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
	public SettlementList initSettlements()
	{
//		plugin.getMessageData().log("SettleInit: ");
		return settlements;
	}
	
	@Override
	public RegimentList initRegiments()
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
		case 20: return Material.BED;
		case 25: return Material.COBBLESTONE;
		case 26: return Material.BEDROCK;
		case 27: return Material.SAND;
		case 28: return Material.GRAVEL;
		case 29: return Material.LOG;
		case 30: return Material.LOG;
		case 31: return Material.IRON_BLOCK;
		case 32: return Material.GOLD_BLOCK;
		case 33: return Material.DIAMOND_BLOCK;
		case 34: return Material.EMERALD_BLOCK;
		case 35: return Material.REDSTONE_BLOCK;
		case 37: return Material.RED_MUSHROOM;
		case 38: return Material.BROWN_MUSHROOM;
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
		case 91: return Material.SIGN;
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
			System.out.println("TMX File not found :"+filename);
//			e.printStackTrace();
		}
		return buildPlan;
	}
	
	private CaseBook readCaseBook(String id)
	{
		return caseBookData.readData(id);
	}
	
	private boolean initCaseBookData(ArrayList<String> bookInit)
	{
		if (bookInit.size() == 0)
		{
			return true;
		}
		for (String bookId : bookInit)
		{
			System.out.println("Init Casebook READ : "+bookId);
//			plugin.getMessageData().log("CaseBookRead: "+bookId );
//			caseBooks.addBook(readCaseBook(bookId));
//			if (Integer.valueOf(bookId) > CaseBook.getCounter())
//			{
//				CaseBook.initCounter(Integer.valueOf(bookId));
//			}
		}
		return true;
	}

	@Override
	public CaseBookList initCaseBooks()
	{
//		plugin.getMessageData().log("CaseBook Init: " );
		return caseBooks;
	}

	@Override
	public void writeCaseBook(CaseBook caseBook)
	{
		caseBookData.writeData(caseBook, caseBook.getRefId());
	}


}
