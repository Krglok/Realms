package net.krglok.realms.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;

import net.krglok.realms.NpcManager;
import net.krglok.realms.Realms;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.colonist.Colony;
import net.krglok.realms.colonist.ColonyList;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.core.TradeMarket;
import net.krglok.realms.core.TradeMarketOrder;
import net.krglok.realms.core.TradeOrder;
import net.krglok.realms.core.TradeStatus;
import net.krglok.realms.core.TradeTransport;
import net.krglok.realms.data.ConfigInterface;
import net.krglok.realms.data.DataInterface;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.data.KnowledgeData;
import net.krglok.realms.data.MessageInterface;
import net.krglok.realms.data.ServerInterface;
import net.krglok.realms.kingdom.Kingdom;
import net.krglok.realms.kingdom.KingdomList;
import net.krglok.realms.kingdom.Lehen;
//<<<<<<< HEAD
//=======
import net.krglok.realms.manager.BuildManager;
import net.krglok.realms.science.CaseBookList;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.RegimentList;
import net.krglok.realms.unit.UnitFactory;

/**
 * <pre>
 * the realmModel is the central Class of the realm handling.
 * all storage, textoutput and command handling will be done outside of the model
 * the model represent a abstract, simplified realization of an ancient political and economical feudal system
 * (Lehensystem).
 * the Technology has a orientation to the european  medieval age and is defined with a List of KnowledgeNode. 
 * the knowledgeNode has required achievements and give permissions for building.
 * the achievements must be collected by the player (Owner)
 * the building and urban objects (settlement) in the game are realized and represented by the separate plugin, HeroStronghold.
 * the aspects and parameters of HeroStronghold are imported in the realmModel.
 * the HeroStronghold make also the area protection.
 * the instances of the regions and superregions are used as buildings and settlements
 * all functions and algorithm of the settlements and realms will be executed inside the model.
 * all rules are implemented here.
 * the settlements breed settlers and  has the task to produce food, materials, equipment and military units.
 * the settlements also can trade automatically  
 * the RealmModel are controlled by the ModelStatus.
 * the RealmModel get Commands from outside over Event Methods.
 * the Model use queues for commands and other actions. 
 * so only one single action will be done thru one cycle.
 * 
 *    
 * @author oduda    19.12.2013
 *</pre>
 */
public class RealmModel
{
	private static final String REALM_MODEL = "RealmModel";
	private static final String REALM_MODEL_VER = "0.9.5";
	private static int garbageCounterLimit = 57;
	
	private ModelStatus modelStatus;
	private ServerInterface server;
	private ConfigInterface config;
	private DataStorage data;
	public MessageInterface messageData;
//	private LogList logList;
	
	private OwnerList owners;
	private SettlementList settlements;
	private CommandQueue commandQueue;				// List von Commands , die abgearbeitet werden muessen
	private ArrayList<Settlement> tradeQueue;		// List von Settlements , die abgearbeitet werden muessen
	private ArrayList<Settlement> productionQueue;	// List von Settlements , die abgearbeitet werden muessen
	private ArrayList<Settlement> taxQueue;			// List von Settlements , die abgearbeitet werden muessen
	private HashMap<Integer,Integer> storeQueue;	// Liste von Settlement Id, die gepeichert werden sollen
	private ArrayList<Regiment> regimentProductionQueue;	// List of Regiments for production, daily event
	private ArrayList<Lehen> lehenProductionQueue;	// List of Regiments for production, daily event

	private TradeTransport tradeTransport = new TradeTransport();
	private TradeMarket tradeMarket = new TradeMarket();
	private ColonyList colonys;				// List of colonys in game
	private RegimentList regiments; 		// List of Regiments in game 
	private CaseBookList caseBooks;			// List of Books in game
	private KingdomList kingdoms;			// List of Kingdoms in game
	private BuildingList buildings;			// List of all buildings in game , make subList for settlements
	
//	public NpcManager npcManager;
	private UnitFactory unitFactory = new UnitFactory();
	private KnowledgeData knowledgeData = new KnowledgeData();
	
	private boolean isInit = false;
	private int garbageCounter;
	private int colonyCounter = 0;
	
	private boolean isDay = false;
	private boolean isNight = false;
	
	/**
	 * instances an empty Model , must be initialize external !
	 * 
	 * @param realmCounter
	 * @param settlementCounter
	 * @param server
	 * @param config
	 * @param data
	 * @param messageData
	 * @param logList
	 */
	public RealmModel(
			int realmCounter, 
			int settlementCounter,
			ServerInterface server,
			ConfigInterface config,
			DataStorage data,
			MessageInterface messageData
//			LogList logList
			)
	{
		modelStatus =  ModelStatus.MODEL_DISABLED;
		commandQueue = new CommandQueue();
		tradeQueue = new ArrayList<Settlement>();
		productionQueue = new ArrayList<Settlement>();
		taxQueue = new ArrayList<Settlement>();
		storeQueue = new HashMap<Integer,Integer>();
		regimentProductionQueue = new ArrayList<Regiment>();
		lehenProductionQueue = new ArrayList<Lehen>();
//		this.logList = logList;
		owners = new OwnerList();
		kingdoms = new KingdomList(realmCounter);
		settlements = new SettlementList(settlementCounter);
		colonys     = new ColonyList(colonyCounter);
		regiments	= new RegimentList(0);
		caseBooks	= new CaseBookList();
		this.server = server;
		this.config = config;
		this.data   = data;
		this.messageData = messageData;
		this.garbageCounter = 3;
	}

	/**
	 * 
	 * @return  name and version
	 */
	public String getModelName()
	{
		return REALM_MODEL+" Ver. "+REALM_MODEL_VER;
	}
	
	/**
	 * 
	 * @return version
	 */
	public String getModelVersion()
	{
		return REALM_MODEL_VER;
	}
	
	public Boolean isInit()
	{
		return this.isInit;
	}
	
	public ModelStatus getModelStatus()
	{
		return modelStatus;
	}
	
	public CommandQueue getcommandQueue()
	{
		return commandQueue;
	}
	
	public ArrayList<Settlement> getProductionQueue()
	{
		return productionQueue;
	}
	
	/**
	 * 
	 * @return OwnerList
	 */
	public OwnerList getOwners()
	{
		return owners;
	}

	/**
	 * replace OwnerList
	 * @param owners
	 */
	public void setOwners(OwnerList owners)
	{
		isInit = true;
		this.owners = owners;
	}

	/**
	 * the settlementList incorporate buildings and units
	 * @return SettlementList
	 */
	public SettlementList getSettlements()
	{
		return settlements;
	}
	

	/**
	 * replace SettlementList
	 * @param settlements
	 */
	public void setSettlements(SettlementList settlements)
	{
		this.settlements = settlements;
	}
	
	public HashMap<Integer,Integer> getStoreQueue()
	{
		return storeQueue;
	}

	public ServerInterface getServer()
	{
		return server;
	}


	public DataStorage getData()
	{
		return data;
	}

	public ConfigInterface getConfig()
	{
		return config;
	}

//	public LogList getLogList()
//	{
//		return logList;
//	}

	public TradeMarket getTradeMarket()
	{
		return tradeMarket;
	}

	public TradeTransport getTradeTransport()
	{
		return tradeTransport;
	}

	/**
	 * @return the colonys
	 */
	public ColonyList getColonys()
	{
		return colonys;
	}

	public RegimentList getRegiments()
	{
		return regiments;
	}

	/**
	 * @return the caseBooks
	 */
	public CaseBookList getCaseBooks()
	{
		return caseBooks;
	}


	/**
	 * @return the kingdoms
	 */
	public KingdomList getKingdoms()
	{
		return kingdoms;
	}


	public void OnEnable()
	{
		switch (modelStatus)
		{
		case MODEL_DISABLED :
			if (initModel())
			{
				this.modelStatus = ModelStatus.MODEL_ENABLED;
			}
			break;
		default :
			break;
		}
	}
	
	/**
	 * link the persistent data to the dateStorage
	 * 
	 * @return
	 */
	public boolean initModel()
	{
		boolean isDone = config.initConfigData();
		caseBooks = data.getCaseBooks();
		owners = data.getOwners();
		buildings = data.getBuildings();
		settlements = data.getSettlements();
		regiments = data.getRegiments();
		kingdoms = data.getKingdoms();
		isInit = isDone;
		return isInit;
	}
	
	public boolean disableModel()
	{
		
		return true;
	}
	
	public void OnDisable()
	{
		switch (modelStatus)
		{
		case MODEL_ENABLED :
			// store Queues
			break;
		default :
			break;
		}
	}

	public void OnCommand(iModelCommand command)
	{
//		System.out.println("OnCommand : "+modelStatus);
		switch (modelStatus)
		{
		case MODEL_ENABLED :
			// store Command in Queue
//			System.out.println("OnCommand : "+modelStatus);
			modelStatus = addCommandQueue(command);
			modelStatus = nextCommandQueue();
			//nextCommandQueue
			break;
		case MODEL_DISABLED:
			System.out.println("OnCommand : "+modelStatus);
			if (command.command() == ModelCommandType.MODELENABLE)
			{
				if (command.canExecute())
				{
					command.execute();
					if (isInit)
					{
						modelStatus = ModelStatus.MODEL_ENABLED;
					}
				}
				System.out.println("OnCommand : "+modelStatus);
			}
			break;
		case MODEL_COMMAND :
			// store command in commandQueue
			System.out.println("OnCommand : "+modelStatus);
			modelStatus = addCommandQueue(command);
			break;
		default :
			// nur Comand Queue erweitern ohne status aenderung
			addCommandQueue(command);
//			System.out.println("OnCommand : "+modelStatus);
			break;
		}
	}

	public void OnProduction(String worldName)
	{
		switch (modelStatus)
		{
		case MODEL_ENABLED :
			// initProductionQueue
			modelStatus = initProductionQueue(worldName);
			break;
		case MODEL_PRODUCTION :
			// NextProduction
			modelStatus = nextProductionQueue();
			break;
		default :
			//modelStatus = initProductionQueue();
			break;
		}
	}
	/**
	 * only for tests used ! Be careful !
	 */
	public void TaxTest()
	{
		modelStatus = ModelStatus.MODEL_PRODUCTION;
		OnTax();
	}
	
	public void OnTax()
	{
		switch (modelStatus)
		{
		case MODEL_ENABLED :
			// initProductionQueue
			//modelStatus = initTaxQueue();
			break;
		case MODEL_PRODUCTION :
			modelStatus = initTaxQueue();
			break;
		case MODEL_TAX :
			// NextProduction
			modelStatus = nextTaxQueue();
			break;
		default :
			break;
		}
		
	}

//	public void OnMove()
//	{
//		switch (modelStatus)
//		{
//		case MODEL_ENABLED :
//			break;
//		default :
//			break;
//		}
//	}

	public void OnTrade(String worldName)
	{
//		System.out.println("OnTrade : "+modelStatus);
		switch (modelStatus)
		{
		case MODEL_ENABLED :
			modelStatus = initTradeQueue(worldName);
			return;
		case MODEL_TRADE :
			modelStatus = nextTradeQueue();
			break;
		default :
			break;
		}
	}

//	public void OnAttack()
//	{
//		switch (modelStatus)
//		{
//		case MODEL_ENABLED :
//			break;
//		default :
//			break;
//		}
//	}

//	public void OnTrain()
//	{
//		switch (modelStatus)
//		{
//		case MODEL_ENABLED :
//			break;
//		default :
//			break;
//		}
//		return;
//	}
	
	/**
	 * <pre>
	 * trigger the tick action for the different objects
	 * - transport
	 * - tradeMarket
	 * - Managers
	 *   - colonyBuilder
	 *   - colony
	 *   - settlement
	 *   - regiment
	 *   - lehen
	 *   
	 * finite state machine for 
	 * - Production
	 * - Tax
	 * - ModelCommand
	 * 
	 * </pre>
	 */
	public void OnTick()
	{
		try
		{
//			System.out.println("OnTick : "+modelStatus);
			// make timer run for trade  every tick 
			// check transportqueues for fullfill 
			tradeTransport.runTick();
			tradeMarket.runTick();
			// Builder
			colonyBuilderRun();
			settlementManagerRun();
			colonyManagerRun();
			regimentManagerRun();
			lehenManagerRun();
			switch (modelStatus)
			{
			case MODEL_ENABLED :
				garbageCounter++;
				// checkCommandQueue
				if (commandQueue.isEmpty() == false)
				{
					messageData.log("Enabled Next Command");
					modelStatus = nextCommandQueue();
					// checkMoveQueue				
					return;
				}
				if (tradeQueue.isEmpty() == false)
				{
					modelStatus = nextTradeQueue();
				}
				
				if (garbageCounter >= garbageCounterLimit)
				{
					garbageCounter = 0;
					messageData.log("Enabled Garbage Collector for TradeOrder");
					doGarbageOrders();
//				} else
//				{
//					logList.run();
//					
				}
				
				break;
			case MODEL_PRODUCTION :
				// nextProduction
//				System.out.println("Next Produktion");
				messageData.log("Next Produktion");
				modelStatus = nextProductionQueue();
				// endProduction
				break;
			case MODEL_TAX :
				// nextTax
				messageData.log("Next Tax");
				modelStatus = nextTaxQueue();
				
				break;
			case MODEL_TRAINING :
				// nextTraining
				// endTraining
				break;
			case MODEL_BATTLE :
				// nextBattleStep
				// endBattle
				break;
			case MODEL_TRADE :
				// nextTradeQueue
				modelStatus = nextTradeQueue();
				// end TradeQueue
				break;
			case MODEL_MOVE :
				// nextMoveQueue
				// endMoveQueue
				break;
			case MODEL_COMMAND :
				// nextCommandQueue
				messageData.log("Command Next Command");
				modelStatus = nextCommandQueue();
				break;
			default :
				break;
			}
		} catch (Exception e)
		{
			messageData.log("[Realms] OnTick exception "+ e.getMessage());
			messageData.log("[Realms] Model Disabled ! ");
			System.out.println("[Realms] ERROR exception "+ e.getMessage());
			e.printStackTrace(System.out);
			System.out.println("[Realms] Model Disabled ! ");
			modelStatus = ModelStatus.MODEL_DISABLED;
		}
		return;
	}

	public void OnNight(long dayTime)
	{
		try 
		{
			switch (modelStatus)
			{
			case MODEL_ENABLED :
				doHunter();
				break;
			default :
				break;
			}
			if (isNight == false)
			{
				System.out.println("Shut Down Gate");
			}
			isNight = true; 
		} catch (Exception e) {
			messageData.log("[Realms] OnNight exception "+ e.getMessage());
			System.out.println("[Realms] ERROR exception "+ e.getMessage());
			e.printStackTrace(System.out);
			System.out.println("[Realms] Model Disabled ! ");
		}
	}
	
	/**
	 * do daily event 
	 * 
	 * @param dayTime
	 */
	public void OnDay(long dayTime)
	{
		try 
		{
			switch (modelStatus)
			{
			case MODEL_ENABLED :
//				System.out.println("[Realms] onDay");
				doTrap();
				break;
			default :
				break;
			}
			if (isDay == false)
			{
			}
			isDay = true;
		} catch (Exception e) {
			messageData.log("[Realms] OnDay exception "+ e.getMessage());
			System.out.println("[Realms] ERROR exception "+ e.getMessage());
			e.printStackTrace(System.out);
			System.out.println("[Realms] Model Disabled ! ");
		}
	}

	/**
	 * start buildManager of each Colony for 1 tick
	 */
	private void colonyBuilderRun()
	{
		for (Colony colony : colonys.values())
		{
//			System.out.println("Colony Buildmanager");
			colony.buildManager().run(this, colony.getWarehouse(),null);
		}
	}
	
	private void colonyManagerRun()
	{
		for (Colony colony : colonys.values())
		{
//			System.out.println("Colony ");
			colony.run(this,colony.getWarehouse());
		}
	}
	
	/**
	 * start each manager for each settlement for 1 tick 
	 */
	private void settlementManagerRun()
	{
		for (Settlement settle : settlements.values())
		{
			settle.settleManager().run(this, settle);
			settle.buildManager().run(this, settle.getWarehouse(),settle);
			settle.tradeManager().run(this, settle);
		}
	}
	
	/**
	 * start the regiment run for each regiment  for 1 tick
	 */
	private void regimentManagerRun()
	{
		if (regiments == null)
		{
			return;
		}
		for (Regiment regiment : regiments.values())
		{
			regiment.run(this);
		}
	}
	
	private void lehenManagerRun()
	{
		for (Lehen lehen : data.getLehen().values())
		{
			lehen.run(this);
		}
	}
	
	private ModelStatus addCommandQueue(iModelCommand command)
	{
		commandQueue.add(command);
		return ModelStatus.MODEL_ENABLED;
	}
	
	/**
	 * start the next command in the queue
	 * only bank command wil be executed
	 * @return
	 */
	private ModelStatus nextCommandQueue()
	{
//		System.out.println("Before CommandQueue ");
		if (commandQueue.isEmpty()) 
		{
			return ModelStatus.MODEL_ENABLED;
		}
		// do Command
		modelStatus = ModelStatus.MODEL_COMMAND;
		iModelCommand command = commandQueue.get(0);
		if (command == null)
		{
			System.out.println("Command NULL");
			return modelStatus;
		}
		if (command.canExecute())
		{
//			System.out.println("Before Switch");
			switch (command.command())
			{
			case NONE :
				break;
			case MODELENABLE :
			case MODELDISABLE:
			case CREATECOLONY:
			case BUILDCOLONY:
			case CREATESETTLEMENT:
			case DEPOSITWAREHOUSE:
			case WITHDRAWWAREHOUSE:
			case WITHDRAWBANK:
				command.execute();
				commandQueue.remove(command);
				break;
			default:
				break;
			}
//			commandQueue.remove(command);
		} else
		{
			
		}
		return ModelStatus.MODEL_ENABLED;
	}

	private ModelStatus initTradeQueue(String worldName)
	{
		for (Settlement settle : settlements.values())
		{
			if (settle.isEnabled() && (settle.getPosition().getWorld().equalsIgnoreCase(worldName)))
			{
				tradeQueue.add(settle);
			}
		}
		return ModelStatus.MODEL_ENABLED;
	}
	
	private ModelStatus nextTradeQueue()
	{
		if (tradeQueue.isEmpty())
		{
			return ModelStatus.MODEL_ENABLED;
		}
		Settlement settle = tradeQueue.get(0);
		if (settle != null)
		{
			settle.setWorkerToBuilding(settle.getResident().getSettlerCount());
			messageData.log("worker to building");
//			System.out.println("[REALMS] worker to building "+settle.getId());
//			settle.setWorkerToBuilding(workerSum);
		}

		
		tradeQueue.remove(0);
		messageData.log("remove 0");
//		System.out.println("[Realms] trade calculation ["+tradeQueue.size()+"] ");
		if (tradeQueue.isEmpty())
		{
			return ModelStatus.MODEL_ENABLED;
		}
		return ModelStatus.MODEL_TRADE;
		
	}
	
	private ModelStatus initProductionQueue(String worldName)
	{
//		System.out.println("Init Production");
		for (Settlement settle : settlements.values())
		{
			if (settle.isEnabled() && (settle.getPosition().getWorld().equalsIgnoreCase(worldName)))
			{
				productionQueue.add(settle);
			}
		}
		for (Lehen lehen : data.getLehen().values())
		{
			if (lehen.isEnabled() && (lehen.getPosition().getWorld().equalsIgnoreCase(worldName)))
			{
				lehenProductionQueue.add(lehen);
			}
		}
		
		for (Regiment regiment : regiments.values())
		{
			if (regiment.isEnabled() && (regiment.getPosition().getWorld().equalsIgnoreCase(worldName)))
			{
				regimentProductionQueue.add(regiment);
			}
		}
		return ModelStatus.MODEL_PRODUCTION;
	}

	/**
	 * <pre>
	 * run the production cycle for the settlement, lehen and regiments
	 * for each tick only 1 settlement are calculated
	 * first all settlement , then all lehen and then all regiment
	 * 
	 * @return ModelStatus : MODEL_PRODUCTION | MODEL_ENABLED
	 * </pre>
	 */
	private ModelStatus nextProductionQueue()
	{
		int lSize = productionQueue.size(); 
		if (productionQueue.isEmpty() == false)
		{
			Settlement settle = productionQueue.get(0);
			System.out.println("[REALMS] Settle production:"+settle.getId()+":"+lSize);
			if (settle.getId()==6)
			{
				System.out.println("[REALMS] Settle production:"+settle.getId()+":"+lSize);
			}
	//		System.out.println("[REALMS] Reset Daily Reputation");
			settle.getReputations().resetDaily();
			messageData.log("settle");
			settle.setSettlerMax();
			messageData.log("settler max");
			settle.checkBuildingsEnabled(server);
			messageData.log("Building enable");
			settle.setWorkerNeeded();
			messageData.log("worker needed");
	//		settle.setWorkerToBuilding(settle.getResident().getSettlerCount());
			settle.doHappiness(data);
			messageData.log("happiness");
			settle.doProduce(server, data);
			messageData.log("produce");
			settle.doUnitTrain(unitFactory);
			data.writeSettlement(settle);
			
	//		storeQueue.put(settle.getId(), settle.getId());
			productionQueue.remove(0);
	//		System.out.println("remove 0");
			messageData.log("remove 0");
	//		System.out.println("[Realms] production calculation ["+productionQueue.size()+"] ");
			return ModelStatus.MODEL_PRODUCTION;
		}

		if (lehenProductionQueue.isEmpty() == false)
		{
			Lehen lehen = lehenProductionQueue.get(0);
			System.out.println("[REALMS] lehen production ");
			lehen.doProduce(server, data);
			lehen.doHappiness(data);
			messageData.log("Lehen happiness");
			data.writeLehen(lehen);
			lehenProductionQueue.remove(0);
			return ModelStatus.MODEL_PRODUCTION;
		}
		
		if (regimentProductionQueue.isEmpty() == false)
		{
			Regiment regiment = regimentProductionQueue.get(0);
			System.out.println("[REALMS] regiment production ");
			regiment.doProduce(server, data);
			data.writeRegiment(regiment);
			regimentProductionQueue.remove(0);
			return ModelStatus.MODEL_PRODUCTION;
		}
		System.out.println("[REALMS] production ended");
		return ModelStatus.MODEL_ENABLED;
	}

	
	private ModelStatus initTaxQueue()
	{
		for (Settlement settle : settlements.values())
		{
			if (settle.isEnabled())
			{
//				System.out.println("initTax");
				taxQueue.add(settle);
			}
		}
		return ModelStatus.MODEL_TAX;
	}
	
	
	private ModelStatus nextTaxQueue()
	{
		if (taxQueue.isEmpty())
		{
			return ModelStatus.MODEL_ENABLED;
		}
		Settlement settle = taxQueue.get(0);
		messageData.log("TAx queue");
//		System.out.println("next Tax");
		settle.doCalcTax();
		taxQueue.remove(0);
		messageData.log("Tax queue remove 0");
		if (taxQueue.isEmpty())
		{
			doFeudalTax();
			return ModelStatus.MODEL_ENABLED;
		}
		return ModelStatus.MODEL_TAX;
	}

	
	private double getChildSum(Lehen lehen)
	{
		double childSum = 0;
		for (Lehen child : getData().getLehen().getChildList(lehen.getId()).values())
		{
			double tax = child.getSales() * ConfigBasis.SALES_TAX / 100.0;
			childSum = childSum + tax;
			child.getBank().depositKonto(child.getSales(), "ME", 0);
			child.getBank().withdrawKonto(tax, "ME", 0);
			child.setSales(0.0);
		}
		return childSum;
	}

	private void doFeudalTax()
	{
    	for (Settlement settle : getData().getSettlements().values())
    	{
    		double umsatzTax = settle.getSales();
    		double settlerTax = settle.getTaxSum();
    		settle.setSales(0.0);
    		settle.setTaxSum(0.0);
    		Owner owner = getData().getOwners().getOwner(settle.getOwnerId());
    		int lehenId = settle.getTributId();
    		int kingdomId = 0;
			Lehen tributLehen = getData().getLehen().getLehen(lehenId);
    		if (tributLehen != null)
    		{
    			kingdomId = tributLehen.getKingdomId();
    			tributLehen.depositSales((umsatzTax));
    		} else if (owner != null)
    		{
    			kingdomId = owner.getKingdomId();
    			owner.depositSales((umsatzTax));
    		}
			Lehen  kingLehen =  getData().getLehen().getKingdomRoot(kingdomId);
			if (kingLehen != null)
			{
    			kingLehen.depositSales((settlerTax));
			}
    	}
    	
    	for (Kingdom kingdom : getData().getKingdoms().values())
    	{
    		if (kingdom.getId() > 0)
    		{
    			Lehen root = getData().getLehen().getKingdomRoot(kingdom.getId());
    			if (root != null)
    			{
	    			for (Lehen lehen : getData().getLehen().getChildList(root.getId()).values())
	    			{
	    				for (Lehen child : getData().getLehen().getChildList(lehen.getId()).values())
	    				{
	    					double childsum = getChildSum(child);
	    					child.depositSales(childsum);
	    				}
	    				double sum = getChildSum(lehen);
	    				lehen.depositSales(sum);
	    			}
	    			double rootSum = getChildSum(root);
	    			root.getBank().depositKonto(rootSum, "TAX", 0);
	    			root.getBank().depositKonto(root.getSales(), "TAX", 0);
	    			root.setSales(0.0);
    			}
    		}
    	}
		
	}
	
	/**
	 * delete Orders with Status NONE from BuyOrders and tradeMarket
	 */
	private void doGarbageOrders()
	{
		// Buyorders of Settlement do Garbage
		try
		{
			Integer[] keyArray = new Integer[tradeTransport.size()];
			int index = 0;
			for (TradeOrder order : tradeTransport.values())
			{
				if (order.getStatus() == TradeStatus.NONE)
				{
					keyArray[index] = order.getId();
					index++;
				}
			}
			for (Integer id : keyArray)
			{
				if (id != null)
				{
					tradeTransport.remove(id);
				}
			}
		} catch (Exception e)
		{
			messageData.log("[Realms] doGarbageOrders BuyOrder exception "+ e.getMessage());
			System.out.println("[Realms] ERROR doGarbageOrders BuyOrder exception "+ e.getMessage());
			modelStatus = ModelStatus.MODEL_DISABLED;
		}
		
		try
		{
			// Central TradeMarket do Garbage
			if ((tradeMarket != null) && (tradeMarket.size() > 0))
			{				
				Integer[] keyArray = new Integer[tradeMarket.size()];
				int index = 0;
				for (TradeMarketOrder order : tradeMarket.values())
				{
					if(order.getStatus() == TradeStatus.DECLINE)
					{
						keyArray[index] = order.getId();
						index ++;
					} else
					{
						if (order.value() <= 0)
						{
							keyArray[index] = order.getId();
							index++;
						}
					}
					
				}
				
				for (Integer id : keyArray)
				{
					if (id != null)
					{
						tradeMarket.remove(id);
					}
				}
			}
		} catch (Exception e)
		{
			messageData.log("[Realms] doGarbageOrders SellOrder exception "+ e.getMessage());
			System.out.println("[Realms] ERROR doGarbageOrders SellOrder exception "+ e.getMessage());
			modelStatus = ModelStatus.MODEL_DISABLED;
		}
	}
	
	/**
	 * Hunters only work at night.
	 */
	private void doHunter()
	{
		for (Settlement settle : settlements.values())
		{
			
		}
	}

	
	private void doTrap()
	{
		for (Settlement settle : settlements.values())
		{
			
		}
	}
	
	/**
	 * not in use now 
	 */
	private void doGateClose()
	{
		for (Settlement settle : settlements.values())
		{
			for (Building building : settle.getBuildingList().values())
			{
				if (building.getBuildingType() == BuildPlanType.GATE)
				{
					
				}
			}
		}
	}

	/**
	 * @return the knowledgeData
	 */
	public KnowledgeData getKnowledgeData()
	{
		return knowledgeData;
	}

	/**
	 * @param knowledgeData the knowledgeData to set
	 */
	public void setKnowledgeData(KnowledgeData knowledgeData)
	{
		this.knowledgeData = knowledgeData;
	}

	/**
	 * @return the buildings
	 */
	public BuildingList getBuildings()
	{
		return buildings;
	}

	/**
	 * @param buildings the buildings to set
	 */
	public void setBuildings(BuildingList buildings)
	{
		this.buildings = buildings;
	}
	
	
}
