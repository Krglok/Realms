package net.krglok.realms;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import multitallented.redcastlemedia.bukkit.herostronghold.HeroStronghold;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.data.*;
import net.krglok.realms.model.RealmModel;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
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
	private final CommandKingdom commandKingdom  = new CommandKingdom(this);
	private final CommandModel commandModel  = new CommandModel(this);
	private final CommandStronghold commandStronghold = new CommandStronghold(this);
	private final CommandSettle commandSettle = new CommandSettle(this);
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
    
    
	
	@Override
	public void onDisable()
	{
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
//        TaxTask taxTask = new TaxTask(this);
//        getServer().getScheduler().scheduleSyncRepeatingTask(this, taxTask, timeUntilDay, TaxTask.getTAX_SCHEDULE());

        if (isReady)
        {
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
	 * interpreter for all commands, start the detailed command execution in seperate
	 * methods. Basic help for no SubCommand are handled in command parser.  
	 * 
	 *  @param sender is player , Operator or console
	 *  @param command 
	 *  @param label , the used alias of the command
	 *  @param args , command parameter, [0] = SubCommand
	 *  @return the command execution status false = show plugin.yml command description  
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
    	switch (RealmsCommandType.getRealmCommandType(command.getName()))
    	{
    	case OWNER :
    		cmdOwnerNone(sender);
    		return true;
    	case SETTLE :
   			commandSettle.run(sender, args);
    		return true;
    	case KINGDOM :
   			commandKingdom.run(sender, args);
    		return true;
    	case REALMS :
		default:
   			commandRealms.run(sender, args);
			return true;
    	}
    }


    /**
     * explanation for realm command
     * @param sender
     */
    private void cmdKingdomNone(CommandSender sender)
    {
    	ArrayList<String> msg = new ArrayList<String>();
    	msg.add(ChatColor.GREEN+"== "+this.getName()+" Vers.: "+config.getVersion()+" ==============");
    	msg.add(ChatColor.GREEN+"usage  : "+ChatColor.YELLOW+"[] = required  {} = optional ");
    	msg.add(ChatColor.GREEN+"command: "+ChatColor.YELLOW+"/realm [SubCommand] {RealmID} {parameter}");
    	msg.add("The command managing the realm. ");
    	msg.add("You must be owner or member of the realm.");
    	msg.add("Some SubCommands only for ops.");
    	msg.add(ChatColor.GREEN+"/realm help "+ChatColor.YELLOW+", show help text for command");
    	for (String line:msg)
    	{
    		line = ChatColor.YELLOW+line;
    		sender.sendMessage(line);
    	}
    }

    /**
     * explanation of Model Command
     * @param sender
     */
    private void cmdModelNone(CommandSender sender)
    {
    	ArrayList<String> msg = new ArrayList<String>();
    	msg.add(ChatColor.GREEN+"== "+this.getName()+" Vers.: "+config.getVersion()+" ==============");
    	msg.add(ChatColor.GREEN+"usage  : "+ChatColor.YELLOW+"[] = required  {} = optional ");
    	msg.add(ChatColor.GREEN+"command: "+ChatColor.YELLOW+"/model [SubCommand] {parameter}");
    	msg.add("The command managing the model and model configuration. ");
    	msg.add("You must be op for using the command.");
    	msg.add(ChatColor.GREEN+"/model help "+ChatColor.YELLOW+", show help text for command");
    	for (String line:msg)
    	{
    		line = ChatColor.YELLOW+line;
    		sender.sendMessage(line);
    	}
    }

    /**
     * explanation of owner command
     * @param sender
     */
    private void cmdOwnerNone(CommandSender sender)
    {
    	ArrayList<String> msg = new ArrayList<String>();
    	msg.add(ChatColor.GREEN+"== "+this.getName()+" Vers.: "+config.getVersion()+" ==============");
    	msg.add(ChatColor.GREEN+"usage  : "+ChatColor.YELLOW+"[] = required  {} = optional ");
    	msg.add(ChatColor.GREEN+"command: "+ChatColor.YELLOW+"/owner [SubCommand] {parameter}");
    	msg.add("The command managing the owner in the model. ");
    	msg.add("You must be op for using the command.");
    	msg.add(ChatColor.GREEN+"/owner help "+ChatColor.YELLOW+", show help text for command");
    	for (String line:msg)
    	{
    		line = ChatColor.YELLOW+line;
    		sender.sendMessage(line);
    	}
    }

    /**
     * explanation of settle command
     * @param sender
     */
    private void cmdSettleNone(CommandSender sender)
    {
    	ArrayList<String> msg = new ArrayList<String>();
    	msg.add(ChatColor.GREEN+"== "+this.getName()+" Vers.: "+config.getVersion()+" ==============");
    	msg.add(ChatColor.GREEN+"usage  : "+ChatColor.YELLOW+"[] = required  {} = optional ");
    	msg.add(ChatColor.GREEN+"command: "+ChatColor.YELLOW+"/settle [SubCommand] {SettlementID} {parameter}");
    	msg.add("The command managing the settlements. ");
    	msg.add("You must be owner of the settlement.");
    	msg.add("NPC settlements are managed only by ops.");
    	msg.add(ChatColor.GREEN+"/settle help "+ChatColor.YELLOW+", show help text for command");
    	for (String line:msg)
    	{
    		line = ChatColor.YELLOW+line;
    		sender.sendMessage(line);
    	}
    }

    private void cmdStrongholdNone(CommandSender sender)
    {
    	ArrayList<String> msg = new ArrayList<String>();
    	msg.add(ChatColor.GREEN+"== "+stronghold.getName()+" ==============");
    	msg.add(ChatColor.GREEN+"usage  : "+ChatColor.YELLOW+"[] = required  {} = optional ");
    	msg.add(ChatColor.GREEN+"command: "+ChatColor.YELLOW+"/stronghold [SubCommand] {parameter}");
    	msg.add("The command show data from the HeroStronghold plugin. ");
    	msg.add(" ");
    	msg.add(" ");
    	msg.add(ChatColor.GREEN+"/stronghold help "+ChatColor.YELLOW+", show help text for command");
    	for (String line:msg)
    	{
    		line = ChatColor.YELLOW+line;
    		sender.sendMessage(line);
    	}
    }
    
    
    public ConfigData getConfigData()
    {
    	return config;
    }

	/**
	 * @return the commandRealm
	 */
	public CommandKingdom getCommandRealm()
	{
		return commandKingdom;
	}

	/**
	 * @return the commandModel
	 */
	public CommandModel getCommandModel()
	{
		return commandModel;
	}

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

}
