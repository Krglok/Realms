package net.krglok.realms;


import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import multitallented.redcastlemedia.bukkit.stronghold.Stronghold;
import multitallented.redcastlemedia.bukkit.stronghold.region.Region;
import net.aufdemrand.sentry.Sentry;
import net.citizensnpcs.Citizens;
import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.ItemList;
import net.krglok.realms.Common.LocationData;
import net.krglok.realms.Common.MessageData;
import net.krglok.realms.Common.RealmsPermission;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.builder.ItemListLocation;
import net.krglok.realms.builder.ItemLocation;
import net.krglok.realms.builder.RegionLocation;
import net.krglok.realms.colonist.Colony;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SignPos;
import net.krglok.realms.core.TradeMarketOrder;
import net.krglok.realms.data.ConfigData;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.data.ServerData;
import net.krglok.realms.kingdom.NobleTrait;
import net.krglok.realms.manager.BiomeLocation;
import net.krglok.realms.manager.BuildManager;
import net.krglok.realms.manager.CampPosition;
import net.krglok.realms.manager.HeightAnalysis;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.npc.SettlerTrait;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.UnitTrait;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*; //. CreatureType;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.bukkit.DynmapPlugin;



/**
 * <pre>
 * Container for the plugin. Handle the Events.
 * create Task for Tick Handling of the RealmModel. 
 * check interface to Stronghold
 * check interface to Vault
 * check Interface to SignChestShop
 * realize onEnable and make initialization
 * realize onDisable and make storage of settlements
 * create Listener for ServerEvents
 * 
 * the use not an automatic update service , only make a message for new versions
 * 
 * @author Krglok
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
//	private LogList logList; // = new LogList(this.getDataFolder().getPath());
	protected FileConfiguration configFile;

//	private final CommandKingdom commandKingdom  = new CommandKingdom(this);
//	private final CommandModel commandModel  = new CommandModel(this);
//	private final CommandStronghold commandStronghold = new CommandStronghold(this);
//	private final CommandSettle commandSettle = new CommandSettle(this);
	private final CommandList commandRealms = new CommandList(this);

	private ConfigData configData; // = new ConfigData(this);
	private final ServerData server = new ServerData(this);
	private DataStorage data;

	private RealmModel realmModel;
	
	private TickTask tick = null;
	private TaxTask taxTask = null;
	private CacheTask cacheTask = null;
	
	private final MessageData messageData = new MessageData(log);
	public ServerListener serverListener; // = new ServerListener(this);
	public NpcManager npcManager = new NpcManager(this);
	public UnitManager unitManager = new UnitManager(this);
	public NobleManager nobleManager = new NobleManager(this);
	
	@SuppressWarnings("unused")
	private Update update; // = new Update(projectId, apiKey);

    public Stronghold stronghold = null;
//    public ShopkeepersPlugin sk = null;
//    public CitizensAPI npcAPI = null;
    public Citizens npc = null;
    public Sentry npcSentry;
//    public Vault vault = null;
    public Economy economy = null;

    public DynmapPlugin dynmap;
	
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
		for (Settlement settle : realmModel.getSettlements().values())
		{
			data.writeSettlement(settle);
		}
        Plugin citizensPlugin = getServer().getPluginManager().getPlugin("Citizens");
        if (citizensPlugin != null) 
        {
            log.info("[Realms] found Citizens !");
        }

		// write NPCData/Trait to file
        log.info("[Realms] Save Npc List");
		for (NpcData npc : data.getNpcs().values())
		{	
			data.writeNpc(npc);
			if (npc.isSpawned)
			{
				npcManager.removeNPC(npc);
			}
		}
		// write special Logdata to File
        log.info("[Realms] Save Transacton List");
//		logList.run();
		// diable message to console;
		log.info("[Realms] is now disabled !");
	}
	
	@Override
	public void onEnable()
	{
		getPluginConfig();
//		logList = new LogList(this.getDataFolder().getPath());
		data = new DataStorage(this.getDataFolder().getPath()); //,logList);
        serverListener = new ServerListener(this);

//		log = Logger.getLogger("Minecraft"); 
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(serverListener, this);
		
        Plugin currentPlugin = pm.getPlugin("Stronghold");
        if (currentPlugin != null) {
            log.info("[Realms] found Stronghold !");
            stronghold = ((Stronghold) currentPlugin);
        } else {
            log.warning("[Realms] didnt find Stronghold.");
            log.info("[Realms] please install the plugin Stronghold .");
            log.info("[Realms] will NOT Started");
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
        Plugin citizensPlugin = pm.getPlugin("Citizens");
        if (citizensPlugin != null) {
            log.info("[Realms] found Citizens !");
            npc = ((Citizens) citizensPlugin);
    		//Register your trait with Citizens.        
    		net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(SettlerTrait.class).withName("settler"));	
    		net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(UnitTrait.class).withName("unit"));	
    		net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(NobleTrait.class).withName("noble"));	
            this.npcManager.setEnabled(true);
            this.unitManager.setEnabled(true);
            this.nobleManager.setEnabled(true);
        } else {
            log.warning("[Realms] didnt find Citizens.");
            log.info("[Realms] please install the plugin Citizens 2 .");
            log.info("[Realms] will disable NPC Manager");
            this.npcManager.setEnabled(false);
            this.unitManager.setEnabled(false);
            this.nobleManager.setEnabled(false);
        }

        Plugin sentryPlugin = pm.getPlugin("Sentry");
        if (sentryPlugin != null) {
            log.info("[Realms] found Sentry !");
    		//Register your trait with Citizens.  
            npcSentry = (Sentry) sentryPlugin;
//            this.npcManager.setEnabled(true);
        } else {
            log.warning("[Realms] didnt find Sentry.");
            log.info("[Realms] please install the plugin Sentry for Citizen2 .");
            log.info("[Realms] will disable NPC Manager");
            this.npcManager.setEnabled(false);
            this.unitManager.setEnabled(false);
            this.nobleManager.setEnabled(false);

        }
        
        Plugin dynmapPlugin = pm.getPlugin("dynmap");
        if (dynmapPlugin != null) {
            log.info("[Realms] found Dynmap ! ");
            dynmap = (DynmapPlugin) dynmapPlugin;
    		//Register your trait with Citizens.        
        } else {
            log.warning("[Realms] didnt find Dynmap.");
        }
        
       
        boolean isReady = true; // flag for Init contrll
		// Vault economy
        if (stronghold != null)
        {
        	server.initRegionConfig();
        	server.initSuperRegionConfig();
    		log.info("[Realms] Stronghold RegionConfig succesfull loaded !");
        } else
        {
        	isReady = false;
    		log.info("[Realms] Stronghold not properly read !");
        }

        
        configData = new ConfigData(this.configFile);
        if (configData == null)
        {
        	log.log(Level.SEVERE, "[Realms] The configData are null!!");
        }
        
        if (configData.initConfigData() == false)
        {
        	isReady = false;
    		log.info("[Realms] Config not properly read !");
        }
        // read Realms Data
        if (data.initData() == false)
        {
        	isReady = false;
    		log.info("[Realms] Data not properly read !");
        }
        if (isReady && configData.isInitBuildingPos())
        {
        	for (Building building: data.getBuildings().values())
        	{
        		Region region = stronghold.getRegionManager().getRegionByID(building.getHsRegion());
        		if (region != null)
        		{
        			System.out.println("[REALMS] reset building pos: "+building.getId()+":"+region.getID());
        			LocationData position = makeLocationData(region.getLocation());
					building.setPosition(position);
					data.writeBuilding(building);
        		}
        	}
        }
//        logList.setIsLogList(configData.isLogList());

        if (configData.isUpdateCheck())
        {
        	update = new Update(projectId, apiKey);
        } else
        {
        	update = null;
        }
        
        // realm model instance
        realmModel = new RealmModel(configData.getRealmCounter(), configData.getSettlementCounter(), server, configData, data, messageData); //, logList);
        
        //Setup repeating sync task for calculating model
        long actualTime = this.getServer().getWorlds().get(0).getTime();
        tick = new TickTask(this);
        // parameter plugin, Runnable , DealyTick to start, Tick to run
        getServer().getScheduler().scheduleSyncRepeatingTask(this, tick, ConfigBasis.DelayTick, ConfigBasis.RealmTick);
        Date date = new Date();
        long timeUntilDay = (TaxTask.DAY_SECONDS + date.getTime() - System.currentTimeMillis()) / TaxTask.TICKTIME;
        TaxTask taxTask = new TaxTask(this);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, taxTask, timeUntilDay, TaxTask.getTAX_SCHEDULE());

        NpcTask npcTask = new NpcTask(this);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, npcTask, npcTask.DELAY_SCHEDULE, npcTask.NPC_SCHEDULE);
        UnitTask unitTask = new UnitTask(this);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, unitTask, unitTask.DELAY_SCHEDULE, unitTask.UNIT_SCHEDULE);
        NobleTask nobleTask = new NobleTask(this);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, nobleTask, nobleTask.DELAY_SCHEDULE, nobleTask.NOBLE_SCHEDULE);
        
        cacheTask = new CacheTask(this);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, cacheTask, cacheTask.DELAY_SCHEDULE, cacheTask.CACHE_SCHEDULE);
        
        
        
        // realm model init
        if (isReady)
        {
        	// Enables automatic production cycles 
        	TickTask.setIsProduction(true);
        	// Initialize the Model 
        	realmModel.OnEnable();
        	npcManager.initNpc();
        	unitManager.initNpc();
        	nobleManager.initNoble();
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
		if (sender.isOp() == false)
		{
			if (sender.hasPermission(RealmsPermission.ADMIN.getValue().toLowerCase()) == false)
			{
				if (sender.hasPermission(RealmsPermission.USER.getValue().toLowerCase()) == false)
				{
					sender.sendMessage(ChatColor.RED+"You not have permission realms.user !");
					sender.sendMessage(ChatColor.YELLOW+"Contact the OP or ADMIN for setup permission.");
					return true;
				}
			}
		}
		commandRealms.run(sender, command, args);
		return true;
    }
	
	public void setShopPrice(Location position)
	{
	}
	
	public void setShop(Player player, Location position, Settlement settle)
	{
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
	
	public void setSign(World world, ItemLocation iLoc, String[] signText )
	{
	  if ((iLoc.itemRef() == Material.WALL_SIGN) || (iLoc.itemRef() == Material.SIGN_POST))
	  {
//		  System.out.println("Set Sign !");
		  Location position = new Location(world,(int)iLoc.position().getX(), (int)iLoc.position().getY(), (int)iLoc.position().getZ()-1);
		  setSignText( position,  signText);
	  }		
	}
	
	public byte determineDataOfDirection(BlockFace bf)
	{
	     if(bf.equals(BlockFace.NORTH))
	            return (byte)3;
//	            return (byte)2;
	     else if(bf.equals(BlockFace.SOUTH))
	            return (byte)2;
//	             return (byte)3;
	     else if(bf.equals(BlockFace.WEST))
	            return (byte)5;
//	            return (byte)4;
	     else if(bf.equals(BlockFace.EAST))
	            return (byte)4;
//	            return (byte)5;
	     return (byte)3;
	}
	
	public void setWallBase(Block providedSign)
	{
		BlockFace[] blockFaces = {BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH};
		for(BlockFace bf : blockFaces) 
		{
		    Block bu = providedSign.getRelative(bf);
		    if((bu.getType() != Material.AIR)) 
		    {
//		        providedSign.setData(determineDataOfDirection(bf));
		    	
//		    	byte faceData = determineDataOfDirection(providedSign.getFace(bu));
		    	byte faceData = determineDataOfDirection(bf);
		    	System.out.println("[REALMS] set Wallbase "+bf+":"+faceData);
		        providedSign.setType(Material.WALL_SIGN);
		        providedSign.setData(faceData, false);
		        //		        providedSign.update(); //Add this line
		        return;
		    }
		}
        providedSign.setType(Material.SIGN_POST);
	}

	/**
	 * setzt einen Block in die Welt an die Position iLoc
	 * !!! verwendet teilweise alte Methoden fuer Bloecke !!!!
	 * @param world
	 * @param iLoc
	 */
	public void setBlock(World world, ItemLocation iLoc)
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
				Byte data2 = (0x1); //not sure on this syntax...0x1 = north/closed
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
//				Sign sign = (Sign) bs.getState();
				setWallBase(bs);
//				if (bs.getRelative(BlockFace.SOUTH).getType() != Material.AIR)
//				{
//					bs.setData(data, applyPhysics)
//					System.out.println("Set WallSign SOUTH");
//					bs.setType(Material.WALL_SIGN);
//				} else
//				{
//					if (bs.getRelative(BlockFace.NORTH).getType() != Material.AIR)
//					{
//						System.out.println("Set WallSign NORTH");
//						bs.setType(Material.WALL_SIGN);
//					} else
//					{
//						if (bs.getRelative(BlockFace.EAST).getType() != Material.AIR)
//						{
//							System.out.println("Set WallSign EAST");
//							bs.setType(Material.WALL_SIGN);
//						} else
//						{
//							if (bs.getRelative(BlockFace.WEST).getType() != Material.AIR)
//							{
//								System.out.println("Set WallSign WEST");
//								bs.setType(Material.WALL_SIGN);
//							} else
//							{
//								System.out.println("Set SignPost !");
//								bs.setType(Material.SIGN_POST);
//							}
//						}
//					}
//				}
				break;
			case BED:
			case BED_BLOCK:
//				System.out.println("Set Bed !");
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
	}

	public void spawnRegionAnimal(String regionType, Location spawnLocation, World world )
	{
    	if (regionType.equalsIgnoreCase("RABBITBARN"))
    	{
        	spawnLocation.setX(spawnLocation.getX()-1);
        	 world.spawnEntity(spawnLocation, EntityType.RABBIT);
        	spawnLocation.setX(spawnLocation.getY()-1);
        	world.spawnEntity(spawnLocation, EntityType.RABBIT);
//        	currentLocation.setX(currentLocation.getX()+3);
//        	world.spawnCreature(currentLocation, CreatureType.RABBIT);
//        	currentLocation.setX(currentLocation.getY()+3);
//        	world.spawnCreature(currentLocation, CreatureType.RABBIT);
    	} else
    	if (regionType.equalsIgnoreCase("CHICKENHOUSE"))
    	{
        	spawnLocation.setX(spawnLocation.getX()-1);
        	world.spawnEntity(spawnLocation, EntityType.CHICKEN);
        	spawnLocation.setX(spawnLocation.getY()-1);
        	world.spawnEntity(spawnLocation, EntityType.CHICKEN);
    	} else
    	if (regionType.equalsIgnoreCase("SHEPHERD"))
    	{
        	spawnLocation.setX(spawnLocation.getX()-1);
        	world.spawnEntity(spawnLocation, EntityType.SHEEP);
        	spawnLocation.setX(spawnLocation.getY()-1);
        	world.spawnEntity(spawnLocation, EntityType.SHEEP);
    	} else
    	if (regionType.equalsIgnoreCase("PIGPEN"))
    	{
        	spawnLocation.setX(spawnLocation.getX()-1);
        	world.spawnEntity(spawnLocation, EntityType.PIG);
        	spawnLocation.setX(spawnLocation.getY()-1);
        	world.spawnEntity(spawnLocation, EntityType.PIG);
    	} else
    	if (regionType.equalsIgnoreCase("SPIDERSHED"))
    	{
        	spawnLocation.setX(spawnLocation.getX()-1);
        	world.spawnEntity(spawnLocation, EntityType.SPIDER);
        	spawnLocation.setX(spawnLocation.getY()-1);
        	world.spawnEntity(spawnLocation, EntityType.SPIDER);
    	}
	}
	
	/**
	 * create a Stronghold Region at position
	 * No checks will be done
	 * @param world
	 * @param rLoc
	 */
	public void doRegionRequest(World world,RegionLocation rLoc )
	{
		Location currentLocation = new Location (
				world,
				rLoc.getPosition().getX(),
				rLoc.getPosition().getY(),
				rLoc.getPosition().getZ()
				);
//        if (this.configData.isSpawnAnimal() == true)
//        {
//        	System.out.println("[REALMS] Spawn new Animals!");
//        	Location spawnLocation = new Location(world,currentLocation.getX(),currentLocation.getY(),currentLocation.getZ());
//        	spawnRegionAnimal(rLoc.getRegionType(),  spawnLocation, world );
//        }

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
        if (this.configData.isSpawnAnimal() == true)
        {
        	System.out.println("[REALMS] Spawn new Animals!");
        	Location spawnLocation = new Location(world,currentLocation.getX(),currentLocation.getY(),currentLocation.getZ());
        	spawnRegionAnimal(rLoc.getRegionType(),  spawnLocation, world );
        }
	}
	
	/**
	 * arbeitet die buildRequests ab
	 * ALLE  Request pro Settlement und Tick werden erledigt
	 * ALLE Request pro Colony und Tick werden erledigt.
	 * ALLE Request pro Regiment und Tick werden erledigt
	 * ALLE ..
	 * Dadurch bestimmt der RequestSender wieviel bearbeitet wird pro tick!!
	 */
	public void doBuildRequest()
	{
		for (Settlement settle : realmModel.getSettlements().values())
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
			
			// Abarbeiten der Region Request zum erstellen von Stronghold Regions
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
				System.out.println("[REALMS] SuperRequest");
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
			
			// Abarbeiten der Region Request zum erstellen von Stronghold Regions
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

	private void getBedBlock(Block block,  BuildManager buildManager, Material mat, Material resulMat)
	{
		if (block.getRelative(BlockFace.UP, 1).getType() == mat)
		{
			block.getRelative(BlockFace.UP, 1).setType(Material.AIR);
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.SOUTH, 1).getRelative(BlockFace.UP, 1).getType() == mat)
		{
			block.getRelative(BlockFace.SOUTH, 1).getRelative(BlockFace.UP, 1).setType(Material.AIR);
		}
		if (block.getRelative(BlockFace.WEST, 1).getRelative(BlockFace.UP, 1).getType() == mat)
		{
			block.getRelative(BlockFace.WEST, 1).getRelative(BlockFace.UP, 1).setType(Material.AIR);
		}
		if (block.getRelative(BlockFace.EAST, 1).getRelative(BlockFace.UP, 1).getType() == mat)
		{
			block.getRelative(BlockFace.EAST, 1).getRelative(BlockFace.UP, 1).setType(Material.AIR);
		}
		if (block.getRelative(BlockFace.NORTH, 1).getRelative(BlockFace.UP, 1).getType() == mat)
		{
			block.getRelative(BlockFace.NORTH, 1).getRelative(BlockFace.UP, 1).setType(Material.AIR);
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
		if (world == null)
		{
			return null;
		}
		if (iLoc == null)
		{
			return null;
		}
		Block block ;
		Material mat ;
		block = world.getBlockAt((int)iLoc.position().getX(), (int)iLoc.position().getY(), (int)iLoc.position().getZ());
		// pr�fen ob ein Portal betroffen ist
		if (block.getType() == Material.OBSIDIAN)
		{
			// Portale werden nicht automatisch abgebaut !!
			if (checkPortalBlock(block,  buildManager, Material.PORTAL))
			{
				return Material.AIR;
			}
		}
		getDoorBlock(block,  buildManager, Material.WOOD_DOOR, Material.WOODEN_DOOR);
		//look for bedblocks
		getBedBlock(block,  buildManager, Material.WOOD_DOOR, Material.BED_BLOCK);
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
//			System.out.println("GetDoor !");
			block = world.getBlockAt((int)iLoc.position().getX(), (int)iLoc.position().getY(), (int)iLoc.position().getZ());
			block.getRelative(BlockFace.UP, 1).setType(Material.AIR);
			mat = block.getType();
			break;
		case CHEST :
			Chest chest = (Chest) block.getState();
			if(chest instanceof Chest )
			{
//				System.out.println("Clean up : Chest found with "+chest.getInventory().getSize());
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
//					System.out.println("Clean up : DoublChest found with "+dChest.getInventory().getSize());
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

	
//	private void getEntityItem(Location position)
//	{
//		for (Entity entity : position.getWorld().getEntities())
//		{
////			entity.
//		}
//	}
	
	/**
	 * read MaterialData from World Position and write into a readRequest
	 */
	public void doCleanRequest()
	{
		for (Settlement settle : realmModel.getSettlements().values())
		{
//			System.out.println(settle.getId()+": cleanRequest "+settle.buildManager().getCleanRequest().size());
			for (int i=0; i < settle.buildManager().getCleanRequest().size(); i++)
			{
//				System.out.println("Colony Clean request");
				ItemLocation iLoc =  settle.buildManager().getCleanRequest().get(i);
				World world = getServer().getWorld(iLoc.position().getWorld());
				if (world != null)
				{	
					Material mat = getBlock(world, iLoc,settle.buildManager());
					if (mat != null)
					{
						settle.buildManager().resultBlockRequest().add(new ItemLocation(mat, new LocationData(iLoc.position().getWorld(), iLoc.position().getX(),iLoc.position().getY(), iLoc.position().getZ())));
					}
				}
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
			for (Building building : settle.getBuildingList().values())
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
	public void doSignRequest()
	{
		for (Settlement settle : realmModel.getSettlements().values())
		{
			doSignUpdate(settle);
		}
	}

	
	/**
	 * Spawn a Model based Minecraft Villager
	 * 
	 * 
	 * @param world
	 * @param position
	 * @param profession
	 * @return
	 */
	public Entity doVillagerSpawn(World world, Location position, Profession profession)
	{
		Entity e = 	world.spawnEntity(position, EntityType.VILLAGER);
		Villager v = (Villager) e;
		v.setProfession(profession);
		v.setRemoveWhenFarAway(false);
		switch(profession)
		{
		case LIBRARIAN:
			v.setCustomName("Verwalter");
			break;
		default:
			v.setCustomName("Settler");
			break;
		}
		v.setCustomNameVisible(true);
		return e;
	}
     
	/**
	 * 
	 * @return the plugin config data
	 */
    public ConfigData getConfigData()
    {
    	return configData;
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
	 * the message Data has special methods to printout pages
	 * and predifined errormessages
	 * 
	 * @return the messageData
	 */
	public MessageData getMessageData()
	{
		return messageData;
	}
	
	/**
	 * the logger has status relevant out ERROR, WARNING, INFO etc. 
	 * @return the logger 
	 */
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

	/**
	 * be carefull with this object
	 * normally used to change the task status or parameter
	 * 
	 * @return tickTask object
	 */
	public TickTask getTickTask()
	{
		return tick;
	}

	/**
	 * 
	 * @return the persistent data for the model
	 */
	public DataStorage getData()
	{
		return data;
	}
	
	/**
	 * 
	 * @return the server object
	 */
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


	/**
	 * 
	 * @return the list of valid commands
	 */
	public CommandList getCommandRealms()
	{
		return commandRealms;
	}

	/**
	 * This is a separate logfile, data stored as CSV values
	 * hint: the logfiles are large and hold many rows, when activated
	 * @return the logList
	 */
//	public LogList getLogList()
//	{
//		return logList;
//	}

	/**
	 * read config from plugin interface 
	 * check if valid, otherwise store default config 
	 * 
	 */
	private void getPluginConfig()
	{
		// read config from plugin 
		this.configFile =  this.getConfig();
		String nameValue = configFile.getString(ConfigBasis.CONFIG_PLUGIN_NAME,"");
		if (!nameValue.equalsIgnoreCase(ConfigBasis.PLUGIN_NAME))
		{
			this.getConfig().options().copyDefaults(true);
			this.saveConfig();
			nameValue = configFile.getString(ConfigBasis.CONFIG_PLUGIN_NAME,"");
		}
		this.getLog().info("[Realms] new configname : "+nameValue);
		
	}

	public Location makeLocation(LocationData position)
	{
		try
		{
			if (position.getWorld() == null) { return null; };
			if (position.getWorld() == "") { return null; };
			World world = this.getServer().getWorld(position.getWorld());
			if (world != null)
			{
				return new Location(world, position.getX(), position.getY(), position.getZ());
			} else
			{
				return null;
			}
			
		} catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * convert LocationData to LOcation
	 * 
	 * @param world
	 * @param position
	 * @return 
	 */
	public Location makeLocation(World world, LocationData position)
	{
		return new Location(world, position.getX(), position.getY(), position.getZ());
	}

	/**
	 * convert Location to LocationData 
	 * @param position
	 * @return
	 */
	public LocationData makeLocationData(Location position)
	{
		return new LocationData(position.getWorld().getName(), position.getX(), position.getY(), position.getZ());
	}
	
	/**
	 * get the block of LocationData or return null
	 * @param position
	 * @return
	 */
	public Block getLocationBlock(LocationData position)
	{
		try
		{
			Location location = makeLocation(position);
			if (location != null)
			{
				return location.getBlock();
			}
			
		} catch (Exception e)
		{
			return null;
		}
		return null;
	}

	/**
	 * <pre>
	 * realize Block scan of a worldMap
	 * - regiment campPosition
	 * 
	 * </pre>
	 */
	public void doScanRequest()
	{
		for (CampPosition campPos : getData().getCampList().values())
		{
			if (campPos.isActiv())
			{
				doNewCampScan(campPos);
				campPos.setActiv(false);
				return;
			}
		}
	}
	
	/**
	 * 
	 * @return list of BlockFace for scan
	 */
	private BlockFace[] getFaceList()
	{
		BlockFace[] checkFaces = new BlockFace[] 
				{BlockFace.NORTH, 
				BlockFace.NORTH_EAST, 
				BlockFace.NORTH_WEST, 
				BlockFace.EAST, 
				BlockFace.SOUTH_EAST, 
				BlockFace.SOUTH_WEST, 
				BlockFace.SOUTH,
				BlockFace.WEST
				};
		return checkFaces;
	}
	
	/**
	 * Scan for ground block in neighborhood of block
	 *  
	 * @param block
	 * @return
	 */
	private Block scanNeibour(Block block, HeightAnalysis analysis)
	{
		BlockFace[] faces = getFaceList();
		for (BlockFace face : faces)
		{
			Block faceBlock = block.getRelative(face);
			Block near = block.getWorld().getHighestBlockAt(faceBlock.getLocation());
//			System.out.println(face.name()+":"+near.getType().name());
			if (analysis.checkGround(near.getType()) == true)
			{
				return near;
			}
		}
		return null;
	}
	
	/**
	 * find nearby blocks not LOG and not LEAVES 
	 * @param position
	 * @param analysis
	 * @return
	 */
	private Location findGround(Location position, HeightAnalysis analysis)
	{
		Location groundPos = position.clone();
		Block block = groundPos.getBlock().getRelative(BlockFace.DOWN);
		if (analysis.checkGround(block.getType()))
		{
//			System.out.println("Ground found");
			return block.getLocation();
		}
		if (scanNeibour(block, analysis) == null)
		{
			for (BlockFace face : getFaceList())
			{
				Block near = scanNeibour(block.getRelative(face),analysis);
				if (near != null)
				{
//					System.out.println("Ground found");
					return near.getLocation(); 
				}
			}
		}
//		System.out.println("Ground NOT found");
		return groundPos;
	}

	
	/**
	 * do Scan analysis, make height correction, make groung calculation for center
	 * radius = 12 blocks
	 * @param campPos
	 */
	public void doNewCampScan( CampPosition campPos)
	{
		if (campPos.getSettleId() == 0)
		{
			System.out.println("NO camp scan for settle "+campPos.getSettleId());
			return;
		}
		if (campPos.isActiv() == false)
		{
			System.out.println("Camp scan not activ "+campPos.getId());
			return;
		}
		// scan position , no height correction
		LocationData stayPos = campPos.getPosition(); 
		Location stayLoc = makeLocation(stayPos);
		// get Highest block for height correction
		int mesHigh = stayLoc.getWorld().getHighestBlockAt(stayLoc).getY();
		stayPos.setY(mesHigh);
//		System.out.println("Base : "+stayPos.toString());
		Location newPos = makeLocation(stayPos);
		int radius =  10;  // radius of analysis 
		HeightAnalysis analysis = campPos.getAnalysis();
		// calculate height correction based on ground block (no LOG and no SPRUCE)
		Location center = findGround(newPos, analysis);
		// make height correction to +1 over ground, othererwise go deeper and deeper on rescan
		center.getBlock().setType(Material.GLASS);
		center.setY(center.getY()+1);
		campPos.setPosition(makeLocationData(center));
		doPositionScan(campPos,center, radius);
	}

	/**
	 * make height analysis on location,
	 *  
	 * @param campPos
	 * @param center location
	 * @param radius of scan
	 */
	public void doPositionScan( CampPosition campPos, Location center, int radius)
	{
//		System.out.println("Start: "+campPos.getPosition().toString());
		long time1 = System.nanoTime();
		Location newPos = makeLocation(campPos.getPosition());
		HeightAnalysis analysis = campPos.getAnalysis();
		int left = (int) (center.getZ()-radius);
		int edge = (radius*2)-1;
		analysis.setStart((int) center.getY());
		center.setX(center.getX()-radius);
		center.setZ(left);
		for(int i=0; i<edge; i++)
		{
			center.setX(center.getX()+1);
			center.setZ(left);
			String line =ConfigBasis.setStrright(i, 2)+"|";
			for(int j=0; j<edge; j++)
			{
				center.setZ(center.getZ()+1);
				Block block = center.getWorld().getHighestBlockAt(center).getRelative(BlockFace.DOWN); 
				if (analysis.checkGround(block.getType()) == true)
				{
					analysis.setIsGround(true);
					int high = block.getY();
//					System.out.println(block.getType().name());
					analysis.addValue(high);
				}
//				block.setType(Material.GLASS);
			}
		}
		campPos.setActiv(false);
		
		if (serverListener.findRegionAtPosition(this, newPos) != null)
		{
			analysis.setRegion(true);
		}
		if (serverListener.findSuperRegionAtPosition(this, newPos ) != null)
		{
			analysis.setSettlement(true);
		}
		
		campPos.setValid(campPos.getAnalysis().isValid());
	    long time2 = System.nanoTime();
	    System.out.println("[REALMS] World Scan Settle "+campPos.getSettleId()+":"+campPos.getFace().name()+" Valid : "+analysis.isValid()+" analysis Time [ms]: "+(time2 - time1)/1000000);
		
	}	

//    public List<Village> getCoreVillagesNearLocation(Location location, int maxDistance) {
//        WorldServer worldServer = ((CraftWorld) location.getWorld()).getHandle();
//        List<Village> nearby = new ArrayList<>();
//        double closestDistance = maxDistance * maxDistance;
//
//        // 
//        List<PersistentVillage> pVillages = worldServer.ae().getVillages();
//        List<Village> coreVillages = worldServer.ae().getVillages();
//        for (Village coreVillage : coreVillages) 
//        {
//            double centerToEdgeDistanceSquared = coreVillage.getSize() * coreVillage.getSize();
//            double distance = location.distanceSquared(new Location(location.getWorld(), coreVillage.getCenter().x, coreVillage.getCenter().y, coreVillage.getCenter().z)) - centerToEdgeDistanceSquared;
//            if (distance < closestDistance) {
//                closestDistance = distance;
//                nearby.add(coreVillage);
//            }
//        }
//        return nearby;
//        //return worldServer.villages.getClosestVillage(location.getBlockX(), location.getBlockY(), location.getBlockZ(), maxDistance);
//    }
	
	public void scanVillages(Location center)
	{
		Block block = center.getBlock();
//		getServer().get
	}
	
}
