package net.krglok.realms;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import multitallented.redcastlemedia.bukkit.herostronghold.HeroStronghold;
import net.krglok.realms.builder.ItemListLocation;
import net.krglok.realms.builder.ItemLocation;
import net.krglok.realms.builder.RegionLocation;
import net.krglok.realms.colonist.Colony;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.ConfigData;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.data.MessageData;
import net.krglok.realms.data.ServerData;
import net.krglok.realms.manager.BiomeLocation;
import net.krglok.realms.manager.BuildManager;
import net.krglok.realms.manager.MapManager;
import net.krglok.realms.model.RealmModel;
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
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Container for the plugin. Handle the Events.
 * create Task for Tick Handling of the RealmModel. 
 * 
 * @author Windu
 *
 */
public final class Realms extends JavaPlugin
{
	private Logger log = Logger.getLogger("Minecraft"); 
//	private final CommandKingdom commandKingdom  = new CommandKingdom(this);
//	private final CommandModel commandModel  = new CommandModel(this);
//	private final CommandStronghold commandStronghold = new CommandStronghold(this);
//	private final CommandSettle commandSettle = new CommandSettle(this);
	private final CommandRealms commandRealms = new CommandRealms(this);

	private ConfigData config; // = new ConfigData(this);
	private final ServerData server = new ServerData(this);
	private final DataStorage data  = new DataStorage(this);

	private RealmModel realmModel;
	
	private TickTask tick = null;
	private TaxTask taxTask = null;
	
	private final MessageData messageData = new MessageData(log);
	private final ServerListener serverListener = new ServerListener(this);
	@SuppressWarnings("unused")
	private final Update update = new Update();

    public HeroStronghold stronghold = null;
//    public Vault vault = null;
    public static Economy economy = null;
    
    
	
	@Override
	public void onDisable()
	{
		// Store Settlements
        log.info("[Realms] Save Settlements .");
		for (Settlement settle : realmModel.getSettlements().getSettlements().values())
		{
			data.writeSettlement(settle);
		}
//		log = Logger.getLogger("Minecraft");
		log.info("[Realms] is now disabled !");
	}
	
	@Override
	public void onEnable()
	{
		
//		log = Logger.getLogger("Minecraft"); 
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(serverListener, this);
		
        Plugin currentPlugin = pm.getPlugin("HeroStronghold");
        if (currentPlugin != null) {
            log.info("[Realms] found HeroStronghold !");
            stronghold = ((HeroStronghold) currentPlugin);
        } else {
            log.warning("[Realms] didnt find HeroStronghold.");
            log.info("[Realms] please install the plugin HerStronghold .");
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
        boolean isReady = true; // flag for Init contrll
		// Vault economy
        config = new ConfigData(this);
        if (!config.initConfigData())
        {
        	isReady = false;
    		log.info("[Realms] Config not properly read !");
        }
        if (!data.initData())
        {
        	isReady = false;
    		log.info("[Realms] Data not properly read !");
        }
        new ServerListener(this);

        realmModel = new RealmModel(config.getRealmCounter(), config.getSettlementCounter(), server, config, data, messageData);
        
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
	
	/**
	 * Fill chest at position with items in itemList
	 * if the block not a chest, nothing will done
	 * @param world
	 * @param iLoc
	 */
	public void setChest(World world, ItemListLocation iLoc)
	{
		Block b = world.getBlockAt((int)iLoc.position().getX()+1, (int)iLoc.position().getY(),(int) iLoc.position().getZ()+1);
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
			case WALL_SIGN:
				world.getBlockAt((int)iLoc.position().getX(), (int)iLoc.position().getY(), (int)iLoc.position().getZ()).setType(iLoc.itemRef());
				break;
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
		Location currentLocation = new Location (
				world,
				rLoc.getPosition().getX(),
				rLoc.getPosition().getY(),
				rLoc.getPosition().getZ()
				);
		String arg2 = rLoc.getRegionType();
		String arg0 = rLoc.getName();
		ArrayList<String> arg3= new ArrayList<String>();
		arg3.add(rLoc.getOwner());
		Map<String,List<String>> arg4= new HashMap<String,List<String>>();
//public boolean addSuperRegion(String name, Location loc, String type, List<String> owners, Map<String, List<String>> members, int power, double balance) {
		int arg5 = 10;
		double arg6 = 10000.0;
		if (stronghold.getRegionManager().addSuperRegion(arg0, currentLocation, arg2, arg3, arg4,arg5 , arg6))
		{
			System.out.println("create SuperRegion"+arg0+" at : "+
				 (int)currentLocation.getX()+":"+
				 (int)currentLocation.getY()+":"+
				 (int)currentLocation.getZ()
				 );
			
		} else
		{
			System.out.println("Error on Create SuperRegion "+arg0);
		}
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
	

	private void getFluid(Block block,  BuildManager buildManager, Material mat, Material resulMat)
	{
		if (block.getRelative(BlockFace.UP, 1).getType() == mat)
		{
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.NORTH, 1).getType() == mat)
		{
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.NORTH_EAST, 1).getType() == mat)
		{
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.NORTH_WEST, 1).getType() == mat)
		{
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.SOUTH, 1).getType() == mat)
		{
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.SOUTH_EAST, 1).getType() == mat)
		{
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.SOUTH_WEST, 1).getType() == mat)
		{
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.EAST, 1).getType() == mat)
		{
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.WEST, 1).getType() == mat)
		{
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		
	}

	private void getTorchBlock(Block block,  BuildManager buildManager, Material mat, Material resulMat)
	{
		if (block.getRelative(BlockFace.UP, 1).getType() == mat)
		{
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.NORTH, 1).getType() == mat)
		{
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.SOUTH, 1).getType() == mat)
		{
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.EAST, 1).getType() == mat)
		{
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.WEST, 1).getType() == mat)
		{
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
	}

	private void getWallBlock(Block block,  BuildManager buildManager, Material mat, Material resulMat)
	{
		if (block.getRelative(BlockFace.NORTH, 1).getType() == mat)
		{
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.SOUTH, 1).getType() == mat)
		{
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.EAST, 1).getType() == mat)
		{
			buildManager.resultBlockRequest().add(new ItemLocation(resulMat, new LocationData(block.getWorld().getName(), block.getX(),block.getY()+1, block.getZ())));
		}
		if (block.getRelative(BlockFace.WEST, 1).getType() == mat)
		{
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
		// lava suchen 
		getFluid(block,  buildManager, Material.LAVA, Material.LAVA_BUCKET);
		// wasser suchen 
		getFluid(block,  buildManager, Material.WATER, Material.WATER_BUCKET);
		// Gravel suchen 
		getFluid(block,  buildManager, Material.GRAVEL, Material.GRAVEL);
		// Sand suchen 
		getFluid(block,  buildManager, Material.SAND, Material.SAND);
		// Torch suchen
		getTorchBlock( block, buildManager, Material .TORCH, Material.TORCH);
		//Wallsign suchen
		getWallBlock(block, buildManager, Material.WALL_SIGN, Material.WALL_SIGN);
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
			break;
		case CHEST :
			Chest chest = (Chest) block.getState();
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
			break;
		case BED_BLOCK:
			System.out.println("Get Bed !");
            block = world.getBlockAt((int)iLoc.position().getX(), (int)iLoc.position().getY(), (int)iLoc.position().getZ());
            block.getRelative(BlockFace.SOUTH).setType(Material.AIR);
			break;
		default :
			block = world.getBlockAt((int)iLoc.position().getX(), (int)iLoc.position().getY(), (int)iLoc.position().getZ());
		}
		mat = block.getType();
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
				getBiome (colony.getBiomeRequest().get(0));
				colony.getBiomeRequest().remove(0);
			}
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
	
	
	

}
