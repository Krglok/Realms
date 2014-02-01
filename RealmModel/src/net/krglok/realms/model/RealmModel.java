package net.krglok.realms.model;

import java.util.ArrayList;
import java.util.HashMap;

import net.krglok.realms.colonist.Colony;
import net.krglok.realms.colonist.ColonyList;
import net.krglok.realms.core.KingdomList;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.core.TradeMarket;
import net.krglok.realms.core.TradeStatus;
import net.krglok.realms.core.TradeTransport;
import net.krglok.realms.data.ConfigInterface;
import net.krglok.realms.data.DataInterface;
import net.krglok.realms.data.MessageInterface;
import net.krglok.realms.data.ServerInterface;
import net.krglok.realms.manager.BuildManager;

/**
 * the realmModel is the top Class of the realm handling.
 * all functions and algorithm of the settlements and realm will be executed inside the model.
 * all rules are implemented here.
 * all storage, textoutput, commandhandling we be done outside of the model
 * the model represent a abstract realisation of an ancient political and economic feudalsystem
 * (Lehensystem oder Feudalismus).
 * the real objects in the game are realized and representent by a different plugin, HeroStronghold.
 * the aspects and parameters of HeroStronghold are imported in the realmModel
 * the instances of the regions and superregions are used as Buildings and Areas
 * the aspects and parameters of the HeroStronghold are abstracted in the realmModel the real value 
 * and connection to the HeroStronghold objects are done outside the realmModel by the plugin that 
 * use this realmModel.
 * the RealmModel are controlled by the ModelStatus.
 * the RealmModel get Commands from outside over Event Methods.
 * the Model use queues for commands and other actions. 
 * so only one single action will be done thru one cycle.s
 * 
 *    
 * @author oduda    19.12.2013
 *
 */
public class RealmModel
{
	private static final String REALM_MODEL = "RealmModel";
	private static final String REALM_MODEL_VER = "0.4.0";
	private static int garbageCounterLimit = 57;
	
	private ModelStatus modelStatus;
	private ServerInterface server;
	private ConfigInterface config;
	private DataInterface data;
	private MessageInterface messageData;
	
	private OwnerList owners;
	private KingdomList realms;
	private SettlementList settlements;
	private CommandQueue commandQueue;				// List von Commands , die abgearbeitet werden muessen
	private ArrayList<Settlement> tradeQueue;		// List von Settlements , die abgearbeitet werden muessen
	private ArrayList<Settlement> productionQueue;	// List von Settlements , die abgearbeitet werden muessen
	private ArrayList<Settlement> taxQueue;			// List von Settlements , die abgearbeitet werden muessen
	// private ArrayList<Training> trainingQueue;
	// private private ArrayList<Trade> tradeQueue;
	private HashMap<Integer,Integer> storeQueue;	// Liste von Settlement Id, die gepeichert werden sollen

	private TradeTransport tradeTransport = new TradeTransport();
	private TradeMarket tradeMarket = new TradeMarket();
	private ColonyList colonys;				// List of colonys in game
	
	private boolean isInit = false;
	private int garbageCounter;
	private int colonyCounter = 0;
	
	/**
	 * instances an empty Model , must be initialize external !
	 * 
	 * @param realmCounter
	 * @param settlementCounter
	 * @param server
	 * @param config
	 * @param data
	 */
	public RealmModel(int realmCounter, int settlementCounter,
			ServerInterface server,
			ConfigInterface config,
			DataInterface data,
			MessageInterface messageData
			)
	{
		modelStatus =  ModelStatus.MODEL_DISABLED;
		commandQueue = new CommandQueue();
		tradeQueue = new ArrayList<Settlement>();
		productionQueue = new ArrayList<Settlement>();
		taxQueue = new ArrayList<Settlement>();
		storeQueue = new HashMap<Integer,Integer>();
		
		owners = new OwnerList();
		realms = new KingdomList(realmCounter);
		settlements = new SettlementList(settlementCounter);
		colonys     = new ColonyList(colonyCounter);
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
	 * 
	 * @return RealmList
	 */
	public KingdomList getRealms()
	{
		return realms;
	}

	/**
	 * replace RealmList
	 * @param realms
	 */
	@SuppressWarnings("unused")
	private void setRealms(KingdomList kingdoms)
	{
		this.realms = kingdoms;
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


	public DataInterface getData()
	{
		return data;
	}

	public ConfigInterface getConfig()
	{
		return config;
	}

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
	
	public boolean initModel()
	{
		boolean isDone = config.initConfigData();
		owners = data.initOwners();
		realms = data.initKingdoms();
		settlements = data.initSettlements();
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
			System.out.println("OnCommand : "+modelStatus);
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
			System.out.println("OnCommand : "+modelStatus);
			break;
		}
	}

	public void OnProduction()
	{
		switch (modelStatus)
		{
		case MODEL_ENABLED :
			// initProductionQueue
			modelStatus = initProductionQueue();
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

	public void OnMove()
	{
		switch (modelStatus)
		{
		case MODEL_ENABLED :
			break;
		default :
			break;
		}
	}

	public void OnTrade()
	{
		System.out.println("OnTrade : "+modelStatus);
		switch (modelStatus)
		{
		case MODEL_ENABLED :
			modelStatus = initTradeQueue();
			return;
		case MODEL_TRADE :
			modelStatus = nextTradeQueue();
			break;
		default :
			break;
		}
	}

	public void OnAttack()
	{
		switch (modelStatus)
		{
		case MODEL_ENABLED :
			break;
		default :
			break;
		}
	}

	public void OnTrain()
	{
		switch (modelStatus)
		{
		case MODEL_ENABLED :
			break;
		default :
			break;
		}
		return;
	}
	
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
			buildManagers();
			
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
				}
				
				break;
			case MODEL_PRODUCTION :
				// nextProduction
				messageData.log("production Next Produktion");
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
			messageData.log("[Realms] exception "+ e.getMessage());
			messageData.log("[Realms] Model Disabled ! ");
			System.out.println("[Realms] ERROR exception "+ e.getMessage());
			e.printStackTrace(System.out);
			System.out.println("[Realms] Model Disabled ! ");
			modelStatus = ModelStatus.MODEL_DISABLED;
		}
		return;
	}

	/**
	 * ruft den BuildManager jedes Settlement auf und laesst ihn eine Runde arbeiten
	 */
	private void buildManagers()
	{
		for (Settlement settle : settlements.getSettlements().values())
		{
//			if (settle.buildManager().getStatus().equalsIgnoreCase("None") == false)
			{
				settle.buildManager().run(settle.getWarehouse());
			}

		}
		
		for (Colony colony : colonys.values())
		{
			colony.buildManager().run(colony.getWarehouse());
			colony.run(colony.getWarehouse());
		}
	}
	
	
	private ModelStatus addCommandQueue(iModelCommand command)
	{
		commandQueue.add(command);
		return ModelStatus.MODEL_ENABLED;
	}
	
	
	private ModelStatus nextCommandQueue()
	{
		if (commandQueue.isEmpty()) 
		{
			return ModelStatus.MODEL_ENABLED;
		}
		// do Command
		modelStatus = ModelStatus.MODEL_COMMAND;
		iModelCommand command = commandQueue.get(0);
		if (command.canExecute())
		{
			command.execute();
			commandQueue.remove(0);
		} else
		{
			
		}
		return ModelStatus.MODEL_ENABLED;
	}

	private ModelStatus initTradeQueue()
	{
		for (Settlement settle : settlements.getSettlements().values())
		{
			if (settle.isEnabled())
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

		settle.getTrader().checkMarket(tradeMarket, tradeTransport, settle, settlements);
		tradeTransport.fullfillTarget(settle);
		tradeTransport.fullfillSender(settle);
		
		tradeQueue.remove(0);
		messageData.log("remove 0");
//		System.out.println("[Realms] trade calculation ["+tradeQueue.size()+"] ");
		if (tradeQueue.isEmpty())
		{
			return ModelStatus.MODEL_ENABLED;
		}
		return ModelStatus.MODEL_TRADE;
		
	}
	
	private ModelStatus initProductionQueue()
	{
		for (Settlement settle : settlements.getSettlements().values())
		{
			if (settle.isEnabled())
			{
				productionQueue.add(settle);
			}
		}
		return ModelStatus.MODEL_PRODUCTION;
	}

	private ModelStatus nextProductionQueue()
	{
		if (productionQueue.isEmpty())
		{
			return ModelStatus.MODEL_ENABLED;
		}
		Settlement settle = productionQueue.get(0);
		messageData.log("settle");
		settle.setSettlerMax();
		messageData.log("settler max");
		settle.checkBuildingsEnabled(server);
		messageData.log("Building enable");
		settle.setWorkerNeeded();
		messageData.log("worker needed");
		settle.setWorkerToBuilding(settle.getResident().getSettlerCount());
		messageData.log("worker to building");
		settle.setHappiness();
		messageData.log("happiness");
		settle.doProduce(server);
		messageData.log("produce");
		data.writeSettlement(settle);
//		storeQueue.put(settle.getId(), settle.getId());
		productionQueue.remove(0);
		messageData.log("remove 0");
//		System.out.println("[Realms] production calculation ["+productionQueue.size()+"] ");
		if (productionQueue.isEmpty())
		{
			return ModelStatus.MODEL_ENABLED;
		}
		return ModelStatus.MODEL_PRODUCTION;
	}

	
	private ModelStatus initTaxQueue()
	{
		for (Settlement settle : settlements.getSettlements().values())
		{
			if (settle.isEnabled())
			{
				System.out.println("initTax");
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
		System.out.println("next Tax");
		settle.doCalcTax();
		taxQueue.remove(0);
		messageData.log("Tax queue remove 0");
		if (taxQueue.isEmpty())
		{
			return ModelStatus.MODEL_ENABLED;
		}
		return ModelStatus.MODEL_TAX;
	}

	/**
	 * delete Orders with Status NONE from BuyOrders and tradeMarket
	 */
	private void doGarbageOrders()
	{
		// Buyorders of Settlement do Garbage
		try
		{
			for (Settlement settle : settlements.getSettlements().values())
			{
				for (int i = 0; i < settle.getTrader().getBuyOrders().size()-1; i++)
				{
					Integer[] keyArray = new Integer[settle.getTrader().getBuyOrders().size()];
					keyArray = settle.getTrader().getBuyOrders().keySet().toArray(keyArray);
					for (Integer id : keyArray)
					{
						if (settle.getTrader().getBuyOrders().get(id).getStatus() == TradeStatus.NONE)
						{
							settle.getTrader().getBuyOrders().remove(id);
						}
					}
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
				keyArray = tradeMarket.keySet().toArray(keyArray);
				for (Integer id : keyArray)
				{
					if(tradeMarket.get(id).getStatus() == TradeStatus.NONE)
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

//	/**
//	 * @return the buildManager
//	 */
//	public BuildManager getBuildManager()
//	{
//		return buildManager;
//	}
	
}
