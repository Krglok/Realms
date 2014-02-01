package net.krglok.realms;
import java.util.Date;
import java.util.logging.Logger;

import multitallented.redcastlemedia.bukkit.herostronghold.HeroStronghold;
import net.krglok.realms.builder.ItemLocation;
import net.krglok.realms.colonist.Colony;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.ConfigData;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.data.MessageData;
import net.krglok.realms.data.ServerData;
import net.krglok.realms.model.RealmModel;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
        tick = new TickTask(this);
        // parameter plugin, Runnable , DealyTick to start, Tick to run
        getServer().getScheduler().scheduleSyncRepeatingTask(this, tick, ConfigBasis.DelayTick, ConfigBasis.RealmTick);
        
        Date date = new Date();
        long timeUntilDay = (TaxTask.DAY_SECONDS + date.getTime() - System.currentTimeMillis()) / TaxTask.TICKTIME;
        TaxTask taxTask = new TaxTask(this);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, taxTask, timeUntilDay, TaxTask.getTAX_SCHEDULE());

        if (isReady)
        {
        	// Enables automatic production cycles 
        	TickTask.setProduction(true);
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
	
	
	public void onBuildRequest()
	{
		for (Settlement settle : realmModel.getSettlements().getSettlements().values())
		{
			if (settle.buildManager().getBuildRequest().size() != 0)
			{
				ItemLocation iLoc =  settle.buildManager().getBuildRequest().get(0);
				World world = getServer().getWorld(iLoc.position().getWorld());
				setBlock(world, iLoc);
				settle.buildManager().getBuildRequest().remove(0);
			}
			if (settle.buildManager().getBuildRequest().size() != 0)
			{
				ItemLocation iLoc =  settle.buildManager().getBuildRequest().get(0);
				World world = getServer().getWorld(iLoc.position().getWorld());
				setBlock(world, iLoc);
				settle.buildManager().getBuildRequest().remove(0);
			}
			if (settle.buildManager().getBuildRequest().size() != 0)
			{
				ItemLocation iLoc =  settle.buildManager().getBuildRequest().get(0);
				World world = getServer().getWorld(iLoc.position().getWorld());
				setBlock(world, iLoc);
				settle.buildManager().getBuildRequest().remove(0);
			}
		}
		
		for (Colony colony : realmModel.getColonys().values())
		{
			if (colony.buildManager().getBuildRequest().size() != 0)
			{
				ItemLocation iLoc =  colony.buildManager().getBuildRequest().get(0);
				World world = getServer().getWorld(iLoc.position().getWorld());
				setBlock(world, iLoc);
				colony.buildManager().getBuildRequest().remove(0);
				
			}
		}
	}

	/**
	 * read MaterialData from World position 
	 * @param world
	 * @param iLoc
	 * @return
	 */
	protected Material getBlock(World world, ItemLocation iLoc)
	{
		Block block ;
		Material mat ;
			
			switch (iLoc.itemRef())
			{
			case BEDROCK: return Material.AIR;
			case WOOD_DOOR : 
				System.out.println("SetDoor !");
				block = world.getBlockAt((int)iLoc.position().getX(), (int)iLoc.position().getY(), (int)iLoc.position().getZ());
				block.getRelative(BlockFace.UP, 1).setType(Material.AIR);
				break;
//			case WALL_SIGN:
//				world.getBlockAt((int)iLoc.position().getX(), (int)iLoc.position().getY(), (int)iLoc.position().getZ()).setType(iLoc.itemRef());
//				break;
			case BED_BLOCK:
				System.out.println("Set Bed !");
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

	/**
	 * read MaterialData from World Position and write into a readRequest
	 */
	public void onCleanRequest()
	{
		for (Settlement settle : realmModel.getSettlements().getSettlements().values())
		{
//			System.out.println(settle.getId()+": cleanRequest "+settle.buildManager().getCleanRequest().size());
			if (settle.buildManager().getCleanRequest().size() != 0)
			{
//				System.out.println("cleanRequest");
				ItemLocation iLoc =  settle.buildManager().getCleanRequest().get(0);
				World world = getServer().getWorld(iLoc.position().getWorld());
				Material mat = getBlock(world, iLoc);
				settle.buildManager().resultBlockRequest().add(new ItemLocation(mat, new LocationData(iLoc.position().getWorld(), iLoc.position().getX(),iLoc.position().getY(), iLoc.position().getZ())));
				settle.buildManager().getCleanRequest().remove(0);
			}
			if (settle.buildManager().getCleanRequest().size() != 0)
			{
//				System.out.println("cleanRequest");
				ItemLocation iLoc =  settle.buildManager().getCleanRequest().get(0);
				World world = getServer().getWorld(iLoc.position().getWorld());
				Material mat = getBlock(world, iLoc);
				settle.buildManager().resultBlockRequest().add(new ItemLocation(mat, new LocationData(iLoc.position().getWorld(), iLoc.position().getX(),iLoc.position().getY(), iLoc.position().getZ())));
				settle.buildManager().getCleanRequest().remove(0);
			}
			if (settle.buildManager().getCleanRequest().size() != 0)
			{
//				System.out.println("cleanRequest");
				ItemLocation iLoc =  settle.buildManager().getCleanRequest().get(0);
				World world = getServer().getWorld(iLoc.position().getWorld());
				Material mat = getBlock(world, iLoc);
				settle.buildManager().resultBlockRequest().add(new ItemLocation(mat, new LocationData(iLoc.position().getWorld(), iLoc.position().getX(),iLoc.position().getY(), iLoc.position().getZ())));
				settle.buildManager().getCleanRequest().remove(0);
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
