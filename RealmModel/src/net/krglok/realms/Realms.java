package net.krglok.realms;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import multitallented.redcastlemedia.bukkit.herostronghold.HeroStronghold;
import net.citizensnpcs.Citizens;
import net.citizensnpcs.api.CitizensAPI;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.builder.ItemListLocation;
import net.krglok.realms.builder.ItemLocation;
import net.krglok.realms.builder.RegionLocation;
import net.krglok.realms.colonist.Colony;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SignPos;
import net.krglok.realms.core.TradeMarketOrder;
import net.krglok.realms.data.ConfigData;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.data.LogList;
import net.krglok.realms.data.MessageData;
import net.krglok.realms.data.ServerData;
import net.krglok.realms.manager.BiomeLocation;
import net.krglok.realms.manager.BuildManager;
import net.krglok.realms.manager.MapManager;
import net.krglok.realms.manager.NpcManager;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.unit.Regiment;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.nisovin.shopkeepers.*;
import com.nisovin.shopkeepers.shopobjects.DefaultShopObjectTypes;
import com.nisovin.shopkeepers.shopobjects.living.LivingEntityType;
import com.nisovin.shopkeepers.shoptypes.DefaultShopTypes;
import com.nisovin.shopkeepers.shoptypes.NormalPlayerShopkeeper;
import com.nisovin.shopkeepers.shoptypes.NormalPlayerShopkeeper.Cost;


/**
 * <pre>
 * Container for the plugin. Handle the Events.
 * create Task for Tick Handling of the RealmModel. 
 * check interface to HeroStronghold
 * check interface to Vault
 * check Interface to SignChestShop
 * realize onEnable and make initialization
 * realize onDisable and make storage of settlements
 * create Listener for ServerEvents
 * 
 * the use not an automatic update service , only make a message for new versions
 * 
 * @author Windu
 *</pre>
 */
public final class Realms extends JavaPlugin
{
//	 * API Key: 7a99d55450a225335559c13c44607f248a2533e8
//	 * [{"id":75301,"name":"Realms","slug":"realms","stage":"beta"}]
	private final int projectId =  75301;
	private final String apiKey = "7a99d55450a225335559c13c44607f248a2533e8";
	private Logger log = Logger.getLogger("Minecraft"); 
	///This is a separate logfile, data stored as CSV values
	private LogList logList; // = new LogList(this.getDataFolder().getPath());

//	private final CommandKingdom commandKingdom  = new CommandKingdom(this);
//	private final CommandModel commandModel  = new CommandModel(this);
//	private final CommandStronghold commandStronghold = new CommandStronghold(this);
//	private final CommandSettle commandSettle = new CommandSettle(this);
	private final CommandRealms commandRealms = new CommandRealms(this);

	private ConfigData config; // = new ConfigData(this);
	private final ServerData server = new ServerData(this);
	private DataStorage data;

	private RealmModel realmModel;
	
	private TickTask tick = null;
	private TaxTask taxTask = null;
	
	private final MessageData messageData = new MessageData(log);
	public ServerListener serverListener = new ServerListener(this);
	public NpcManager npcManager = new NpcManager(this);
	@SuppressWarnings("unused")
	private Update update; // = new Update(projectId, apiKey);

    public HeroStronghold stronghold = null;
    public ShopkeepersPlugin sk = null;
//    public CitizensAPI npcAPI = null;
    public Citizens npc = null;
//    public Vault vault = null;
    public static Economy economy = null;
    
	
	@Override
	public void onDisable()
	{
		// Store Settlements
        log.info("[Realms] Save TradeMarket .");
        for (TradeMarketOrder order : realmModel.getTradeMarket().values())
        {
        	Settlement settle = realmModel.getSettlements().getSettlement(order.getSettleID());
        	settle.getWarehouse().depositItemValue(order.ItemRef(),order.value());
        }
        log.info("[Realms] Save Transport .");
        for (TradeMarketOrder order : realmModel.getTradeTransport().values())
        {
        	Settlement settle = realmModel.getSettlements().getSettlement(order.getSettleID());
			double cost = order.value() * order.getBasePrice();
        	settle.getBank().addKonto(cost, "Trader "+order.getTargetId(),settle.getId());
        	Settlement target = realmModel.getSettlements().getSettlement(order.getSettleID());
        	target.getWarehouse().depositItemValue(order.ItemRef(),order.value());
        }
        
        log.info("[Realms] Save Settlements .");
		for (Settlement settle : realmModel.getSettlements().getSettlements().values())
		{
			data.writeSettlement(settle);
		}
		// write special Logdata to File
        log.info("[Realms] Save Transacton List");
		logList.run();
		// diable message to console;
		log.info("[Realms] is now disabled !");
	}
	
	@Override
	public void onEnable()
	{
		logList = new LogList(this.getDataFolder().getPath());
		data = new DataStorage(this);
//		log = Logger.getLogger("Minecraft"); 
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(serverListener, this);
		
        Plugin currentPlugin = pm.getPlugin("HeroStronghold");
        if (currentPlugin != null) {
            log.info("[Realms] found HeroStronghold !");
            stronghold = ((HeroStronghold) currentPlugin);
        } else {
            log.warning("[Realms] didnt find HeroStronghold.");
            log.info("[Realms] please install the plugin HeroStronghold .");
            log.info("[Realms] will NOT be Enabled");
            this.setEnabled(false);
            return;
        }
        Plugin vaultPlugin = pm.getPlugin("Vault");
        if (vaultPlugin != null) {
            log.info("[Realms] found Vault Economy !");
            RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
            if (economyProvider != null) {
                economy = economyProvider.getProvider();
            }
        } else {
            log.warning("[Realms] didnt find Vault.");
            log.info("[Realms] please install the plugin Vault .");
            log.info("[Realms] will NOT be Enabled");
            this.setEnabled(false);
            return;
        }
        Plugin npc = pm.getPlugin("Citizens");
        if (npc != null) {
            log.info("[Realms] found Citizens !");
            npc = ((Citizens) npc);
            this.npcManager.setEnabled(true);
        } else {
            log.warning("[Realms] didnt find Citizens.");
            log.info("[Realms] please install the plugin Citizens 2 .");
            log.info("[Realms] will disable NPC Manager");
            this.npcManager.setEnabled(false);
            return;
        }
        Plugin shop = pm.getPlugin("Shopkeepers");
        if(shop != null && shop.isEnabled())
        {
            log.info("[Realms] found Shopkeeper !");
            sk = (ShopkeepersPlugin) shop; //You may never need to use this
        } else {
            log.warning("[Realms] didnt find Shopkeeper.");
            log.warning("[Realms] please install the plugin Shopkeeper .");
            log.warning("[Realms] will be Enabled without Shops");
        }

        boolean isReady = true; // flag for Init contrll
		// Vault economy
        config = new ConfigData(this);
        if (!config.initConfigData())
        {
        	isReady = false;
    		log.info("[Realms] Config not properly read !");
        }
        if (data.initData() == false)
        {
        	isReady = false;
    		log.info("[Realms] Data not properly read !");
        }
        logList.setIsLogList(config.isLogList());

        if (config.isUpdateCheck())
        {
        	update = new Update(projectId, apiKey);
        } else
        {
        	update = null;
        }
        

        realmModel = new RealmModel(config.getRealmCounter(), config.getSettlementCounter(), server, config, data, messageData, logList);
        
        //Setup repeating sync task for calculating model
        long actualTime = this.getServer().getWorlds().get(0).getTime();
        tick = new TickTask(this);
        TickTask.setCounter(actualTime/ConfigBasis.RealmTick);
        // parameter plugin, Runnable , DealyTick to start, Tick to run
        getServer().getScheduler().scheduleSyncRepeatingTask(this, tick, ConfigBasis.DelayTick, ConfigBasis.RealmTick);
        Date date = new Date();
        long timeUntilDay = (TaxTask.DAY_SECONDS + date.getTime() - System.currentTimeMillis()) / TaxTask.TICKTIME;
        TaxTask taxTask = new TaxTask(this);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, taxTask, timeUntilDay, TaxTask.getTAX_SCHEDULE());

        if (isReady)
        {
        	// Enables automatic production cycles 
        	TickTask.setIsProduction(true);
        	// Initialize the Model 
        	realmModel.OnEnable();
    		log.info("[Realms] Model is now enabled !");
        } else
        {
    		log.warning("[Realms] Model is disabled !");
    		log.info("[Realms] Some Data may misted for the Model !");
    		log.info("[Realms] You must manually activate the model");
        	log.info("[Realms] is now ready !");
        }
	}
	
	

	/**
	 *  @param sender is player , Operator or console
	 *  @param command 
	 *  @param label , the used alias of the command
	 *  @param args , command parameter, [0] = SubCommand
	 *  @return the command execution status false = show plugin.yml command description  
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		// hanldes all commands and Subcommands 
		commandRealms.run(sender, command, args);
		return true;
    }
	
	public void setShopPrice(Location position)
	{
//		double chestx = -521.0;
//		double chesty = 67.0;
//		double chestz = -1355.0;
//		position.setX(chestx);
//		position.setY(chesty);
//		position.setZ(chestz);
		Block bs = position.getWorld().getBlockAt(position);
		String shopkeeperId = "73a11c97-b21c-4c3c-b7fe-d4e6053edc14";
		String shopName = "Test";
		Shopkeeper shop = null;
		
		for (Shopkeeper  obj : sk.getActiveShopkeepers() )
		{
			System.out.println("SHopName: "+obj.getName()); 
			if (obj.getName().equalsIgnoreCase(shopName))
			{
				shop = obj;
			}
		}
		
		
		if ( shop != null)
		{
			if (shop.getType().isPlayerShopType())
			{
				NormalPlayerShopkeeper nShop = (NormalPlayerShopkeeper) shop;
		    	System.out.println("PlayerShop found :"+nShop.getCosts().size());
				ItemStack item ;
				Cost cost; 
				ItemStack stock;

				if (nShop != null)
				{
					nShop.setName("NewShop");
			    	System.out.println("Realms New shop");
					int index = 0;
					int maxRecipe = 8;
					
					Map<ItemStack, Cost> costs =  nShop.getCosts();
					
					System.out.println("Costs size: "+nShop.getCosts().size());
					item = new ItemStack(Material.COBBLESTONE);
					
					stock = new ItemStack(Material.COBBLESTONE);
					stock.setAmount(128);
	    	
				}
			}
		}
	}
	
	public void setShop(Player player, Location position, Settlement settle)
	{
		Block bs = position.getWorld().getBlockAt(position);
		Block cs = bs.getRelative(BlockFace.DOWN);
		cs.setType(Material.CHEST);
		Chest chest = (Chest) cs.getState();
		bs.setType(Material.AIR);
		if (sk == null) {System.out.println("Shop not loaded isnull");  return; }
//		sk.getShopTypeRegistry().register(DefaultShopTypes.PLAYER_NORMAL);
//		sk.getShopObjectTypeRegistry().register(LivingEntityType.VILLAGER.getObjectType());
//		System.out.println("Costs size: "+sk.getShopTypeRegistry().numberOfRegisteredTypes());
		ShopType<?> shopType =  sk.getShopTypeRegistry().get("PLAYER_NORMAL");   //getDefaultSelection(player);
		ShopObjectType shopObjType = sk.getShopObjectTypeRegistry().get("VILLAGER");  //getDefaultSelection(player);

		if (player == null) {System.out.println("Player isnull"); }
		if (cs == null ) {System.out.println("CS isnull"); }
		if (position == null ) {System.out.println("CS isnull"); }

		ShopCreationData shopCreationData = new ShopCreationData(player, shopType, cs, position, shopObjType);
		Shopkeeper shop = ShopkeepersPlugin.getInstance().createNewPlayerShopkeeper(shopCreationData ); 
		//(player, cs, position, shopType,shopObjectType);    

		ItemStack item ;
		Cost cost; 
		ItemStack stock;

		if (shop != null)
		{
			shop.setName("NewShop");
			NormalPlayerShopkeeper nShop = (NormalPlayerShopkeeper) shop;
	    	System.out.println("Realms New shop");
	    	ItemList overStock = settle.settleManager().getOverStock(realmModel, settle);
			int index = 0;
			int maxRecipe = 8;
			
			Map<ItemStack, Cost> costs =  nShop.getCosts();
			
			System.out.println("Costs size: "+nShop.getCosts().size());
//			item = new ItemStack(Material.COBBLESTONE);
//			nShop.getCosts().put(item, new NormalPlayerShopkeeper.Cost(64,1)); 
			
			stock = new ItemStack(Material.COBBLESTONE);
			stock.setAmount(128);
//			
//			
//			item = new ItemStack(Material.LOG);
//			
//			stock = new ItemStack(Material.LOG);
//			stock.setAmount(128);
//			chest.getInventory().addItem(stock);
			
//			for (Item stock : overStock.values())
//			{
//		    	System.out.println("Realms Stock "+stock.ItemRef());
//				//(int index = 0; index < scsAPI.getShopInventory(bs, true).getSize(); index++)
//				if (index < shop.getStorage().getSize() )
//				{
//					int amount = stock.value();
//					if (amount > 64)
//					{
//						amount = 64;
//					}
//					ItemStack item = new ItemStack(Material.getMaterial(stock.ItemRef()),amount);
//					if (item != null)
//					{  
//						shop.setItem(index, item);
//				    	System.out.println("Realms Price "+stock.ItemRef()+":"+amount);
//					}
//					index++;
//				}
//			}
			
		}
	}

	
	/**
	 * Fill chest at position with items in itemList
	 * if the block not a chest, nothing will done
	 * @param world
	 * @param iLoc
	 */
	public void setChest(World world, ItemListLocation iLoc)
	{
		Block b = world.getBlockAt((int)iLoc.position().getX(), (int)iLoc.position().getY(),(int) iLoc.position().getZ());
		if (b.getType() == Material.CHEST)
		{
			 Chest chest = (Chest) b.getState();
			 for (Item item : iLoc.getItemList())
			 {
				 chest.getInventory().addItem(new ItemStack(Material.getMaterial(item.ItemRef()), item.value()));
			 }
			 System.out.println("Set Chest at : "+
					 (int)iLoc.position().getX()+":"+
					 (int)iLoc.position().getY()+":"+
					 (int)iLoc.position().getZ()
					 );
		} else
		{
			System.out.println(b.getType().name()+ " Not a chest at "+
					 (int)iLoc.position().getX()+":"+
					 (int)iLoc.position().getY()+":"+
					 (int)iLoc.position().getZ()
					);
		}
		
	}

	protected void setSignText(Location position, String[] signText)
	{
		Block bs = position.getWorld().getBlockAt(position);
		  if ((bs.getType() == Material.WALL_SIGN) || (bs.getType() == Material.SIGN_POST))
		  {
				Sign sBlock =	((Sign) bs.getState());
				for (int i=0; i < 4; i++)
				{
					String text = signText[i];
					text = text.replaceAll("[_]", " ");
					sBlock.setLine(i, text);
					sBlock.update(true);
//					for (int k = 0; k < signText.length; k++)
//					{
//						System.out.println(sBlock.getLines()[k]);
//					}
				}		  
		  } else
		  {
			  System.out.println("No Sign found !");
		  }

	}
	
	protected void setSign(World world, ItemLocation iLoc, String[] signText )
	{
	  if ((iLoc.itemRef() == Material.WALL_SIGN) || (iLoc.itemRef() == Material.SIGN_POST))
	  {
		  System.out.println("Set Sign !");
		  Location position = new Location(world,(int)iLoc.position().getX(), (int)iLoc.position().getY(), (int)iLoc.position().getZ()-1);
		  setSignText( position,  signText);
	  }		
	}
	
	/**
	 * setzt einen Block in die Welt an die Position iLoc
	 * !!! verwendet teilweise alte Methoden fuer Bloecke !!!!
	 * @param world
	 * @param iLoc
	 */
	protected void setBlock(World world, ItemLocation iLoc)
	{
//		System.out.println(iLoc.itemRef());
//		if (iLoc.itemRef() != Material.AIR)
//		{						
//			System.out.println(ConfigBasis.getPlanMaterial(bHome.getCube()[h][r][c]) );
			
			switch (iLoc.itemRef())
			{
			case WOOD_DOOR : 
				System.out.println("SetDoor !");
				Block bottom = world.getBlockAt((int)iLoc.position().getX(), (int)iLoc.position().getY(), (int)iLoc.position().getZ());
				Block top = bottom.getRelative(BlockFace.UP, 1);
				Byte data1 = (0x8); //not sure on this syntax...
				Byte data2 = (0x4); //not sure on this syntax...
				top.setTypeIdAndData(64, data1, false);
				bottom.setTypeIdAndData(64, data2, false);
				break;
			case SEEDS:
			case WHEAT:
//				System.out.println("Wheat/Seeds");
				Block b = world.getBlockAt((int)iLoc.position().getX(), (int)iLoc.position().getY(), (int)iLoc.position().getZ());
//				BlockState ground = b.getRelative(BlockFace.DOWN).getState();
//				ground.setTypeId((byte) 60);
				b.setType(Material.CROPS);
				
				break;
			case SIGN:
			case SIGN_POST:
			case WALL_SIGN:
				
				Block bs = world.getBlockAt((int)iLoc.position().getX(), (int)iLoc.position().getY(), (int)iLoc.position().getZ());
				if (bs.getRelative(BlockFace.SOUTH).getType() != Material.AIR)
				{
					System.out.println("Set WallSign SOUTH");
					bs.setType(Material.WALL_SIGN);
				} else
				{
					if (bs.getRelative(BlockFace.NORTH).getType() != Material.AIR)
					{
						System.out.println("Set WallSign NORTH");
						bs.setType(Material.WALL_SIGN);
					} else
					{
						if (bs.getRelative(BlockFace.EAST).getType() != Material.AIR)
						{
							System.out.println("Set WallSign EAST");
							bs.setType(Material.WALL_SIGN);
						} else
						{
							if (bs.getRelative(BlockFace.WEST).getType() != Material.AIR)
							{
								System.out.println("Set WallSign WEST");
								bs.setType(Material.WALL_SIGN);
							} else
							{
								System.out.println("Set SignPost !");
								bs.setType(Material.SIGN_POST);
							}
						}
					}
				}
				break;
			case BED:
			case BED_BLOCK:
				System.out.println("Set Bed !");
	            BlockState bedFoot = world.getBlockAt((int)iLoc.position().getX(), (int)iLoc.position().getY(), (int)iLoc.position().getZ()).getState();
	            BlockState bedHead = bedFoot.getBlock().getRelative(BlockFace.SOUTH).getState();
				bedFoot.setType(Material.BED_BLOCK);
				bedHead.setType(Material.BED_BLOCK);
				bedFoot.setRawData((byte) 0x0);
				bedHead.setRawData((byte) 0x8);
				bedFoot.update(true, false);
				bedHead.update(true, true);
				break;
			default :
				world.getBlockAt((int)iLoc.position().getX(), (int)iLoc.position().getY(), (int)iLoc.position().getZ()).setType(iLoc.itemRef());
			}
//		}
		
	}

	private void doSuperRequest(World world, RegionLocation rLoc )
	{
		server.createSuperRegion(world, rLoc);
//		Location currentLocation = new Location (
//				world,
//				rLoc.getPosition().getX(),
//				rLoc.getPosition().getY(),
//				rLoc.getPosition().getZ()
//				);
//		String arg2 = rLoc.getRegionType();
//		String arg0 = rLoc.getName();
//		ArrayList<String> arg3= new ArrayList<String>();
//		arg3.add(rLoc.getOwner());
//		Map<String,List<String>> arg4= new HashMap<String,List<String>>();
////public boolean addSuperRegion(String name, Location loc, String type, List<String> owners, Map<String, List<String>> members, int power, double balance) {
//		int arg5 = 10;
//		double arg6 = 10000.0;
//		if (stronghold.getRegionManager().addSuperRegion(arg0, currentLocation, arg2, arg3, arg4,arg5 , arg6))
//		{
//			System.out.println("create SuperRegion"+arg0+" at : "+
//				 (int)currentLocation.getX()+":"+
//				 (int)currentLocation.getY()+":"+
//				 (int)currentLocation.getZ()
//				 );
//			
//		} else
//		{
//			System.out.println("Error on Create SuperRegion "+arg0);
//		}
	}

	/**
	 * create a HeroStronghold Region at position
	 * No checks will be done
	 * @param world
	 * @param rLoc
	 */
	private void doRegionRequest(World world,RegionLocation rLoc )
	{
		Location currentLocation = new Location (
				world,
				rLoc.getPosition().getX(),
				rLoc.getPosition().getY(),
				rLoc.getPosition().getZ()
				);
		String arg1 = rLoc.getRegionType();
		ArrayList<String> arg2= new ArrayList<String>();
		arg2.add(rLoc.getOwner());
		stronghold.getRegionManager().addRegion(currentLocation, arg1, arg2);

		Block currentBlock = currentLocation.getBlock();
        currentBlock.setType(Material.CHEST);

        System.out.println("create Chest at : "+
				 (int)currentLocation.getX()+":"+
				 (int)currentLocation.getY()+":"+
				 (int)currentLocation.getZ()
				 );

	}
	
	/**
	 * arbeitet die buildRequests ab
	 * ALLE  Request pro Settlement und Tick werden erledigt
	 * ALLE Request pro Colony und Tick werden erledigt.
	 * Dadurch bestimmt der RequestSender wieviel bearbeitet wird pro tick!!
	 */
	public void onBuildRequest()
	{
		for (Settlement settle : realmModel.getSettlements().getSettlements().values())
		{
			for (int i=0 ; i < settle.buildManager().getBuildRequest().size(); i++)
			{
				ItemLocation iLoc =  settle.buildManager().getBuildRequest().get(i);
				World world = getServer().getWorld(iLoc.position().getWorld());
				setBlock(world, iLoc);
//				settle.buildManager().getBuildRequest().remove(0);
			}
			settle.buildManager().getBuildRequest().clear();
			
			if (settle.buildManager().getRegionRequest().size() != 0)
			{
				World world = getServer().getWorld(settle.buildManager().getRegionRequest().get(0).getPosition().getWorld());
				RegionLocation rLoc = settle.buildManager().getRegionRequest().get(0);
				doRegionRequest( world, rLoc );
				settle.buildManager().getRegionRequest().remove(0);
			}

			if (settle.buildManager().getChestSetRequest().size() != 0)
			{
				System.out.println("do Chest Set");
				World world = getServer().getWorld(settle.buildManager().getChestSetRequest().get(0).position().getWorld());
				setChest(world, settle.buildManager().getChestSetRequest().get(0));
				settle.buildManager().getChestSetRequest().remove(0);
			}
			if (settle.getMapManager().getBiomeRequest().size() > 0)
			{
				getBiome (settle.getMapManager().getBiomeRequest().get(0));
			}
		}
		
		for (Colony colony : realmModel.getColonys().values())
		{
			for (int i=0; i<colony.buildManager().getBuildRequest().size(); i++)
			{
//				System.out.println("Colony Build request");
				ItemLocation iLoc =  colony.buildManager().getBuildRequest().get(i);
				World world = getServer().getWorld(iLoc.position().getWorld());
				setBlock(world, iLoc);
//				colony.buildManager().getBuildRequest().remove(0);
			}
			colony.buildManager().getBuildRequest().clear();
			
			// Abarbeiten der Region Request zum erstellen von Herostronghold Regions
			if (colony.buildManager().getRegionRequest().size() != 0)
			{
				World world = getServer().getWorld(colony.buildManager().getRegionRequest().get(0).getPosition().getWorld());
				RegionLocation rLoc = colony.buildManager().getRegionRequest().get(0);
				doRegionRequest( world, rLoc );
				colony.buildManager().getRegionRequest().remove(0);
			}
			//Abarbeiten der SetChestRequest zum configurieren der Region
			if (colony.buildManager().getChestSetRequest().size() != 0)
			{
				System.out.println("do Chest Set");
				World world = getServer().getWorld(colony.buildManager().getChestSetRequest().get(0).position().getWorld());
				setChest(world, colony.buildManager().getChestSetRequest().get(0));
				colony.buildManager().getChestSetRequest().remove(0);
			}
			// Abarbeiten der SuperRegionRequest zum create der Superregions
			if (colony.getSuperRequest() != null)
			{
//				System.out.println("SuperRequest");
				World world = getServer().getWorld(colony.getSuperRequest().getPosition().getWorld());
				doSuperRequest(world, colony.getSuperRequest() );
				colony.setSuperRequest(null);
			}
			if (colony.getBiomeRequest().size() > 0)
			{
				getBiome (colony.getBiomeRequest().get(0));
			}
		}

		for (Regiment regiment : realmModel.getRegiments().values())
		{
			for (int i=0; i<regiment.buildManager().getBuildRequest().size(); i++)
			{
//				System.out.println("Colony Build request");
				ItemLocation iLoc =  regiment.buildManager().getBuildRequest().get(i);
				World world = getServer().getWorld(iLoc.position().getWorld());
				setBlock(world, iLoc);
//				colony.buildManager().getBuildRequest().remove(0);
			}
			regiment.buildManager().getBuildRequest().clear();
			
			// Abarbeiten der Region Request zum erstellen von Herostronghold Regions
			if (regiment.buildManager().getRegionRequest().size() != 0)
			{
				World world = getServer().getWorld(regiment.buildManager().getRegionRequest().get(0).getPosition().getWorld());
				RegionLocation rLoc = regiment.buildManager().getRegionRequest().get(0);
				doRegionRequest( world, rLoc );
				regiment.buildManager().getRegionRequest().remove(0);
			}
			//Abarbeiten der SetChestRequest zum configurieren der Region
			if (regiment.buildManager().getChestSetRequest().size() != 0)
			{
				System.out.println("do Regiment Chest Set");
				World world = getServer().getWorld(regiment.buildManager().getChestSetRequest().get(0).position().getWorld());
				setChest(world, regiment.buildManager().getChestSetRequest().get(0));
				regiment.buildManager().getChestSetRequest().remove(0);
			}
			// Abarbeiten der SuperRegionRequest zum create der Superregions
			if (regiment.getSuperRequest() != null)
			{
//				System.out.println("SuperRequest");
				World world = getServer().getWorld(regiment.getSuperRequest().getPosition().getWorld());
				doSuperRequest(world, regiment.getSuperRequest() );
				regiment.setSuperRequest(null);
			}
//			if (regiment.getBiomeRequest().size() > 0)
//			{
//				getBiome (regiment.getBiomeRequest().get(0));
//			}
		}
		
	}
	
	
	/**
	 * schreibt das Ergebnis in den Request !!!
	 * @param iLoc
	 */
	private void getBiome (BiomeLocation iLoc)
	{
		World world = getServer().getWorld(iLoc.position().getWorld());
		Block block ;
		Biome biome ;
		block = world.getBlockAt((int)iLoc.position().getX(), (int)iLoc.position().getY(), (int)iLoc.position().getZ());
		biome = block.getBiome();
		iLoc.setBiome(biome);
	}
	

	private void getDoorBlock(Block block,  BuildManager buildManager, Material mat, Material resulMat)
	{
		if (block.getRelative(BlockFace.UP, 1).getType() == mat)
		{
			block.getRelative(BlockFace.UP, 1).setType(Material.AIR);
			block.getRelative(BlockFace.UP, 2).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
	}
	
	private void getFluid(Block block,  BuildManager buildManager, Material mat, Material resulMat)
	{
		if (block.getRelative(BlockFace.UP, 1).getType() == mat)
		{
			block.getRelative(BlockFace.UP, 1).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.NORTH, 1).getType() == mat)
		{
			block.getRelative(BlockFace.NORTH, 1).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.NORTH_EAST, 1).getType() == mat)
		{
			block.getRelative(BlockFace.NORTH_EAST, 1).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.NORTH_WEST, 1).getType() == mat)
		{
			block.getRelative(BlockFace.NORTH_WEST, 1).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.SOUTH, 1).getType() == mat)
		{
			block.getRelative(BlockFace.SOUTH, 1).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.SOUTH_EAST, 1).getType() == mat)
		{
			block.getRelative(BlockFace.SOUTH_EAST, 1).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.SOUTH_WEST, 1).getType() == mat)
		{
			block.getRelative(BlockFace.SOUTH_WEST, 1).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.EAST, 1).getType() == mat)
		{
			block.getRelative(BlockFace.EAST, 1).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.WEST, 1).getType() == mat)
		{
			block.getRelative(BlockFace.WEST, 1).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}

		if (block.getRelative(BlockFace.UP, 2).getType() == mat)
		{
			block.getRelative(BlockFace.UP, 2).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.NORTH, 2).getType() == mat)
		{
			block.getRelative(BlockFace.NORTH, 2).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.NORTH_EAST, 2).getType() == mat)
		{
			block.getRelative(BlockFace.NORTH_EAST, 2).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.NORTH_WEST, 2).getType() == mat)
		{
			block.getRelative(BlockFace.NORTH_WEST, 2).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.SOUTH, 2).getType() == mat)
		{
			block.getRelative(BlockFace.SOUTH, 2).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.SOUTH_EAST, 2).getType() == mat)
		{
			block.getRelative(BlockFace.SOUTH_EAST, 2).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.SOUTH_WEST, 2).getType() == mat)
		{
			block.getRelative(BlockFace.SOUTH_WEST, 2).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.EAST, 2).getType() == mat)
		{
			block.getRelative(BlockFace.EAST, 2).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.WEST, 2).getType() == mat)
		{
			block.getRelative(BlockFace.WEST, 2).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		
	}

	private void getTorchBlock(Block block,  BuildManager buildManager, Material mat, Material resulMat)
	{
		if (block.getRelative(BlockFace.UP, 1).getType() == mat)
		{
			block.getRelative(BlockFace.UP, 1).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.NORTH, 1).getType() == mat)
		{
			block.getRelative(BlockFace.NORTH, 1).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.SOUTH, 1).getType() == mat)
		{
			block.getRelative(BlockFace.SOUTH, 1).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.EAST, 1).getType() == mat)
		{
			block.getRelative(BlockFace.EAST, 1).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.WEST, 1).getType() == mat)
		{
			block.getRelative(BlockFace.WEST, 1).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
	}

	private void getWallBlock(Block block,  BuildManager buildManager, Material mat, Material resulMat)
	{
		if (block.getRelative(BlockFace.NORTH, 1).getType() == mat)
		{
			block.getRelative(BlockFace.NORTH, 1).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.SOUTH, 1).getType() == mat)
		{
			block.getRelative(BlockFace.SOUTH, 1).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.EAST, 1).getType() == mat)
		{
			block.getRelative(BlockFace.EAST, 1).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.WEST, 1).getType() == mat)
		{
			block.getRelative(BlockFace.WEST, 1).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
	}
	
	private boolean checkPortalBlock(Block block,  BuildManager buildManager, Material mat)
	{
		if (block.getRelative(BlockFace.UP, 1).getType() == mat)
		{
			return true;
		}
		if (block.getRelative(BlockFace.DOWN, 1).getType() == mat)
		{
			return true;
		}
		if (block.getRelative(BlockFace.NORTH, 1).getType() == mat)
		{
			return true;
		}
		if (block.getRelative(BlockFace.NORTH_EAST, 1).getType() == mat)
		{
			return true;
		}
		if (block.getRelative(BlockFace.NORTH_WEST, 1).getType() == mat)
		{
			return true;
		}
		if (block.getRelative(BlockFace.SOUTH, 1).getType() == mat)
		{
			return true;
		}
		if (block.getRelative(BlockFace.SOUTH_EAST, 1).getType() == mat)
		{
			return true;
		}
		if (block.getRelative(BlockFace.SOUTH_WEST, 1).getType() == mat)
		{
			return true;
		}
		if (block.getRelative(BlockFace.EAST, 1).getType() == mat)
		{
			return true;
		}
		if (block.getRelative(BlockFace.EAST_NORTH_EAST, 1).getType() == mat)
		{
			return true;
		}
		if (block.getRelative(BlockFace.EAST_SOUTH_EAST, 1).getType() == mat)
		{
			return true;
		}
		if (block.getRelative(BlockFace.WEST, 1).getType() == mat)
		{
			return true;
		}
		if (block.getRelative(BlockFace.WEST_NORTH_WEST, 1).getType() == mat)
		{
			return true;
		}
		if (block.getRelative(BlockFace.WEST_SOUTH_WEST, 1).getType() == mat)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * <pre>
	 * read MaterialData from World position 
	 * check for special materials and portals
	 * @param world
	 * @param iLoc
	 * @return
	 * </pre>
	 */
	protected Material getBlock(World world, ItemLocation iLoc, BuildManager buildManager)
	{
		Block block ;
		Material mat ;
		block = world.getBlockAt((int)iLoc.position().getX(), (int)iLoc.position().getY(), (int)iLoc.position().getZ());
		// prüfen ob ein Portal betroffen ist
		if (block.getType() == Material.OBSIDIAN)
		{
			// Portale werden nicht automatisch abgebaut !!
			if (checkPortalBlock(block,  buildManager, Material.PORTAL))
			{
				return Material.AIR;
			}
		}
		getDoorBlock(block,  buildManager, Material.WOOD_DOOR, Material.WOODEN_DOOR);
		// lava suchen 
		getFluid(block,  buildManager, Material.LAVA, Material.LAVA_BUCKET);
		// wasser suchen 
		getFluid(block,  buildManager, Material.WATER, Material.WATER_BUCKET);
		// Gravel suchen 
		getFluid(block,  buildManager, Material.GRAVEL, Material.GRAVEL);
		// Sand suchen 
		getFluid(block,  buildManager, Material.SAND, Material.SAND);
		// Torch suchen
		getTorchBlock( block, buildManager, Material.TORCH, Material.TORCH);
		// Sign suchen
		getTorchBlock( block, buildManager, Material.SIGN_POST, Material.SIGN);
		//Wallsign suchen
		getWallBlock(block, buildManager, Material.WALL_SIGN, Material.SIGN);
		// leietern suchen
		getWallBlock(block, buildManager, Material.LADDER, Material.LADDER);
    		
		switch (block.getType())
		{
		case BEDROCK: return Material.AIR;
		case PORTAL : return Material.AIR;
		case WOOD_DOOR : 
			System.out.println("GetDoor !");
			block = world.getBlockAt((int)iLoc.position().getX(), (int)iLoc.position().getY(), (int)iLoc.position().getZ());
			block.getRelative(BlockFace.UP, 1).setType(Material.AIR);
			mat = block.getType();
			break;
		case CHEST :
			Chest chest = (Chest) block.getState();
			if(chest instanceof Chest )
			{
				System.out.println("Clean up : Chest found with "+chest.getInventory().getSize());
				for (int i=0; i < chest.getInventory().getSize(); i++)
				{
					ItemStack item = chest.getInventory().getItem(i);
					if (item != null)
					{
						if (item.getType() != Material.AIR)
						{
							buildManager.resultBlockRequest().add(new ItemLocation(item.getType(), new LocationData(iLoc.position().getWorld(), iLoc.position().getX(),iLoc.position().getY(), iLoc.position().getZ())));
						}
					}
				}
				chest.getInventory().clear();
				mat = block.getType();
			} else
			{				
				DoubleChest dChest = (DoubleChest) block.getState();
				if (dChest instanceof DoubleChest)
				{
					System.out.println("Clean up : DoublChest found with "+dChest.getInventory().getSize());
					for (int i=0; i < dChest.getInventory().getSize(); i++)
					{
						ItemStack item = dChest.getInventory().getItem(i);
						if (item != null)
						{
							if (item.getType() != Material.AIR)
							{
								buildManager.resultBlockRequest().add(new ItemLocation(item.getType(), new LocationData(iLoc.position().getWorld(), iLoc.position().getX(),iLoc.position().getY(), iLoc.position().getZ())));
							}
						}
					}
					chest.getInventory().clear();
				}
				mat = block.getType();
			}
			break;
		case BED_BLOCK:
			System.out.println("Get Bed !");
            block = world.getBlockAt((int)iLoc.position().getX(), (int)iLoc.position().getY(), (int)iLoc.position().getZ());
            block.getRelative(BlockFace.SOUTH).setType(Material.AIR);
    		mat = Material.BED;
    		block.setType(Material.AIR);
			break;
		default :
			block = world.getBlockAt((int)iLoc.position().getX(), (int)iLoc.position().getY(), (int)iLoc.position().getZ());
			mat = block.getType();
		}
		block.setType(Material.AIR);
//	    	System.out.println(block.getType().name()+"/"+mat.name());
		return mat;
		
	}

	private boolean checkEntityinRange(Entity entity, Location position, EntityType eType, double range)
	{
		if (entity.getType() == eType)
		{
			if (entity.getLocation().distanceSquared(position) < 71)
			{
				
			}
		}
		
		return false;
	}
	
	private void getEntityAnimal(Location position)
	{
		for (Entity entity : position.getWorld().getEntities())
		{
			if (checkEntityinRange(entity, position, EntityType.CHICKEN, 71.0))
			{
//				entity.remove();
			}
			if (checkEntityinRange(entity, position, EntityType.COW, 71.0))
			{
				
			}
			if (checkEntityinRange(entity, position, EntityType.PIG, 71.0))
			{
				
			}
			if (checkEntityinRange(entity, position, EntityType.HORSE, 71.0))
			{
				
			}
			if (checkEntityinRange(entity, position, EntityType.SHEEP, 71.0))
			{
				//teletport entity to BuildibgPos
			}
		}
	}

	
	private void getEntityItem(Location position)
	{
		for (Entity entity : position.getWorld().getEntities())
		{
//			entity.
		}
	}
	
	/**
	 * read MaterialData from World Position and write into a readRequest
	 */
	public void onCleanRequest()
	{
		for (Settlement settle : realmModel.getSettlements().getSettlements().values())
		{
//			System.out.println(settle.getId()+": cleanRequest "+settle.buildManager().getCleanRequest().size());
			for (int i=0; i < settle.buildManager().getCleanRequest().size(); i++)
			{
//				System.out.println("Colony Clean request");
				ItemLocation iLoc =  settle.buildManager().getCleanRequest().get(i);
				World world = getServer().getWorld(iLoc.position().getWorld());
				Material mat = getBlock(world, iLoc,settle.buildManager());
				settle.buildManager().resultBlockRequest().add(new ItemLocation(mat, new LocationData(iLoc.position().getWorld(), iLoc.position().getX(),iLoc.position().getY(), iLoc.position().getZ())));
			}
			settle.buildManager().getCleanRequest().clear();
		}

		for (Colony colony : realmModel.getColonys().values())
		{
//			System.out.println(settle.getId()+": cleanRequest "+settle.buildManager().getCleanRequest().size());
			for (int i=0; i < colony.buildManager().getCleanRequest().size(); i++)
			{
//				System.out.println("Colony Clean request");
				ItemLocation iLoc =  colony.buildManager().getCleanRequest().get(i);
				World world = getServer().getWorld(iLoc.position().getWorld());
				Material mat = getBlock(world, iLoc,colony.buildManager());
				colony.buildManager().resultBlockRequest().add(new ItemLocation(mat, new LocationData(iLoc.position().getWorld(), iLoc.position().getX(),iLoc.position().getY(), iLoc.position().getZ())));
			}
			colony.buildManager().getCleanRequest().clear();
			if (colony.getBiomeRequest().size() > 0)
			{
				getBiome(colony.getBiomeRequest().get(0));
//				System.out.println("Biome request "+colony.getBiomeRequest().get(0).getBiome());
			}
		}
		for (Regiment regiment : realmModel.getRegiments().values())
		{
//			System.out.println(settle.getId()+": cleanRequest "+settle.buildManager().getCleanRequest().size());
			for (int i=0; i < regiment.buildManager().getCleanRequest().size(); i++)
			{
//				System.out.println("Colony Clean request");
				ItemLocation iLoc =  regiment.buildManager().getCleanRequest().get(i);
				World world = getServer().getWorld(iLoc.position().getWorld());
				Material mat = getBlock(world, iLoc,regiment.buildManager());
				regiment.buildManager().resultBlockRequest().add(new ItemLocation(mat, new LocationData(iLoc.position().getWorld(), iLoc.position().getX(),iLoc.position().getY(), iLoc.position().getZ())));
			}
			regiment.buildManager().getCleanRequest().clear();
//			if (regiment.getBiomeRequest().size() > 0)
//			{
//				getBiome(regiment.getBiomeRequest().get(0));
////				System.out.println("Biome request "+colony.getBiomeRequest().get(0).getBiome());
//			}
		}

	}
	
	/**
	 * run an update on the registered signs of an settlement
	 * @param settle
	 */
	public void doSignUpdate(Settlement settle)
	{
		for (SignPos signPos : settle.getSignList().values())
		{
			for (Building building : settle.getBuildingList().getBuildingList().values())
			{
			    if (signPos.getText()[0].equalsIgnoreCase(building.getBuildingType().name()))
			    {
				    if (building.getBuildingType() == BuildPlanType.GUARDHOUSE)
				    {
				    	signPos.getText()[1] = building.isEnabled().toString();
				    	signPos.getText()[2] = building.getTrainType().name();
				    	signPos.getText()[3] = String.valueOf(building.getMaxTrain());
				    } else
				    {
					    if (building.getBuildingType() == BuildPlanType.HALL)
					    {
					    	signPos.getText()[1] = "S: "+String.valueOf(settle.getResident().getSettlerCount())+"/"+String.valueOf(settle.getResident().getSettlerMax());
					    	signPos.getText()[2] = "B: "+String.valueOf((int) settle.getBank().getKonto());
					    	signPos.getText()[3] = "U: "+String.valueOf(settle.getBarrack().getUnitList().size())+"/"+String.valueOf(settle.getBarrack().getUnitMax());
					    } else
					    {
					    	signPos.getText()[1] = building.isEnabled().toString();
					    	int index = 2;
					    	ItemList output = server.getRegionOutput(building.getHsRegionType());
					    	for (Item item : output.values())
					    	{
					    		if (index < 4)
					    		{
					    			signPos.getText()[index] = item.ItemRef();
					    			index++;
					    		}
					    	}
					    }
				    }
			    }

			}
			World world = this.getServer().getWorld(signPos.getPosition().getWorld());
			Location position = new Location(world,(int)signPos.getPosition().getX(), (int)signPos.getPosition().getY(), (int)signPos.getPosition().getZ());
			setSignText( position,  signPos.getText());
		}		
	}
	
	/**
	 * set Sign text of registered Sign of a Settlement
	 * will be called on each from TickTask 
	 */
	public void onSignRequest()
	{
		for (Settlement settle : realmModel.getSettlements().getSettlements().values())
		{
			doSignUpdate(settle);
		}
	}
	
     
    public ConfigData getConfigData()
    {
    	return config;
    }

//	/**
//	 * @return the commandRealm
//	 */
//	public CommandKingdom getCommandRealm()
//	{
//		return commandKingdom;
//	}
//
//	/**
//	 * @return the commandModel
//	 */
//	public CommandModel getCommandModel()
//	{
//		return commandModel;
//	}

	/**
	 * @return the messageData
	 */
	public MessageData getMessageData()
	{
		return messageData;
	}
	
	public Logger getLog()
	{
		return log;
	}

	/**
	 * @return the realmModel
	 */
	public RealmModel getRealmModel()
	{
		return realmModel;
	}

	public TickTask getTickTask()
	{
		return tick;
	}

	public DataStorage getData()
	{
		return data;
	}

	public ServerData getServerData()
	{
		return server;
	}

	/**
	 * @return the taxTask
	 */
	public TaxTask getTaxTask()
	{
		return taxTask;
	}

	public CommandRealms getCommandRealms()
	{
		return commandRealms;
	}

	/**
	 * This is a separate logfile, data stored as CSV values
	 * @return the logList
	 */
	public LogList getLogList()
	{
		return logList;
	}

	

}
