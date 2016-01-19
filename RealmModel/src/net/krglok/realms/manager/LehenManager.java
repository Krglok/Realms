package net.krglok.realms.manager;

import java.util.ArrayList;

import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.builder.BuildStatus;
import net.krglok.realms.core.AbstractSettle;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.RouteOrder;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.TradeMarketOrder;
import net.krglok.realms.core.TradeOrder;
import net.krglok.realms.core.TradeTransport;
import net.krglok.realms.kingdom.Lehen;
import net.krglok.realms.model.CommandQueue;
import net.krglok.realms.model.McmdBuilder;
import net.krglok.realms.model.McmdBuyOrder;
import net.krglok.realms.model.McmdSellOrder;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.model.iModelCommand;

/**
 * Der Lehen manager steuert die internen automatischen Abläufe des Lehens
 * - warentransport
 *  
 * 
 * Das Lehen hat keine eigene Production von Waren, sondern ist auf die VErsorgung von aussen angewiesen.
 * Zusaetzlich muessen die eigenen Regimenter versorgt werden.
 *  
 * @author Windu
 *
 */

public class LehenManager
{

	
	private int ROUTEDELAY = 20;
	private int delayRoutes;
	private LehenStatus lStatus ;
	private Lehen lehen;

	private ArrayList<McmdBuilder> cmdBuilder;
	private ArrayList<McmdBuyOrder> cmdBuy;
	private ArrayList<McmdSellOrder> cmdSell;
	
	private ItemList buyList;
	private ItemList dontSell;
	private int checkCounter = 100;
	
	public LehenManager()
	{
		delayRoutes = 0;
		lStatus = LehenStatus.NONE;
	}

    public void run(RealmModel rModel, Lehen lehen)
    {
	    //  not on each tick 
		if (checkCounter <= 0)
		{
		
			System.out.println("1");
			getModelCommands(rModel, lehen);
//			System.out.println("6");
			if (checkBuildMaterials(rModel, lehen))
			{
		//				System.out.println("6.1");
				buildOrder(rModel,lehen);
				checkCounter = 0;
			} else
			{	
		//				System.out.println("6.2");
				buyBuildMaterials(rModel, lehen);
				checkCounter = 100;
			}

		}
    	
    }

	
    /**
     * check for items in the required List of the lehen.
     * this are the imediate needed items/blocks
     * 
     * @param lehen
     * @return
     */
    private boolean needResources(Lehen lehen)
    {
    	if (lehen.getrequiredItems().size() > 0)
    	{
    		return true;
    	}
    	return false;
    }
    
    /**
     * Make a transport order for the next item in required list.
     * no special priority 
     * 
     * @param rModel
     * @param lehen
     */
    private void startNextTransport(RealmModel rModel, Lehen lehen)
    {
    	Item item = lehen.getrequiredItems().values().iterator().next();
    	Double price = rModel.getData().getPriceList().getBasePrice(item.ItemRef()); 
    	RouteOrder rOrder = new RouteOrder(0, lehen.getId(), item.ItemRef(), item.value(), price, true);
		lehen.getTrader().makeRouteOrder(rModel.getTradeMarket(), rOrder, rModel.getTradeTransport(), lehen, rModel.getSettlements());
		// item can remove by itenname, because items are unique in the list
		lehen.getrequiredItems().remove(item.ItemRef());
    }
    
	private void getModelCommands(RealmModel rModel, Lehen lehen)
	{
		ArrayList<iModelCommand> removeCmd = new ArrayList<iModelCommand>();
		CommandQueue commandQueue = rModel.getcommandQueue();
		if (commandQueue == null)
		{
			return;
		}
		if (commandQueue.isEmpty() == false)
		{
			rModel.messageData.log("Enabled Next Command");
			for (iModelCommand Mcmd : commandQueue)
			{
				switch (Mcmd.command())
				{
				case CREATEBUILDING :
					McmdBuilder cmd = (McmdBuilder) Mcmd;
					if (cmd.getSettleId() == lehen.getId())
					{
						cmdBuilder.add(new McmdBuilder(rModel, cmd.getSettleId(), cmd.getbType(), cmd.getPosition(), cmd.getPlayer()));
						removeCmd.add(Mcmd);
						if (cmd.getPlayer() != null)
						{	
							cmd.getPlayer().sendMessage("Build Queue of LehenManager");
						}

					}
					break;
				case BUYITEM:
					McmdBuyOrder buy = (McmdBuyOrder) Mcmd;
					if (buy.getSettleId() == lehen.getId())
					{
						cmdBuy.add(new McmdBuyOrder(rModel, buy.getSettleId(), buy.getItemRef(), buy.getAmount(), buy.getPrice(), buy.getDelayDays()));
						removeCmd.add(Mcmd);
					}
					
					break;
				case SELLITEM :
					McmdSellOrder sell = (McmdSellOrder) Mcmd;
					if (sell.getSettleId() == lehen.getId())
					{
						cmdSell.add(new McmdSellOrder(rModel, sell.getSettleId(), sell.getItemRef(), sell.getAmount(), sell.getPrice(), sell.getDelayDays()));
						removeCmd.add(Mcmd);
					}
					break;
				case DEPOSITBANK:
					if (Mcmd.canExecute())
					{
						Mcmd.execute();
						removeCmd.add(Mcmd);
					} else
					{
					}
					break;
				default:
				}
			}
			for (iModelCommand rCmd : removeCmd)
			{
				rModel.getcommandQueue().remove(rCmd);
			}

			// checkMoveQueue				
			return;
		}
	}

	/**
	 * Buy material for build the BuildPlanType
	 * add buy order to cmdBuy.queue
	 * 
	 * @param rModel
	 * @param settle
	 */
	private void buyBuildMaterials(RealmModel rModel, Lehen lehen)
	{
		for (Item item : buyList.values())
		{
			if (lehen.getTrader().getBuyOrders().containsKey(item.ItemRef()) == false)
			{
				boolean isCmdFound = false;
				for (McmdBuyOrder order : cmdBuy)
				{
					if (order.getItemRef().equalsIgnoreCase(item.ItemRef()))
					{
						isCmdFound = true;
					}
				}
				
				if (isCmdFound == false)				
				{
//					System.out.println(item.ItemRef()+":"+item.value());
					cmdBuy.add(new McmdBuyOrder(rModel, lehen.getId(), item.ItemRef(), item.value(), 0.0, 5));
					dontSell.addItem(new Item(item.ItemRef(), item.value()));
					System.out.println("Settle send buy order for build : "+item.ItemRef());
				}
			}
		}
		buyList.clear();
	}
	
	
	/**
	 * prueft ob BuildManager den naechsten Auftrag erledigen kann
	 * @param settle
	 */
	private void buildOrder(RealmModel rModel,Lehen lehen)
	{
		if (lehen.buildManager().getStatus() == BuildStatus.NONE)
		{
			if (cmdBuilder.isEmpty() == false)
			{
				if (cmdBuilder.get(0).canExecute())
				{
					takeBuildingMaterials(rModel,  lehen, cmdBuilder.get(0).getbType());
					cmdBuilder.get(0).execute();
					cmdBuilder.remove(0);
					System.out.println("Settle send build order");
				}
			}
		}
	}
	
	
	/**
	 * take material of building from warehouse
	 * @param rModel
	 * @param settle
	 * @param bType
	 */
	public void takeBuildingMaterials(RealmModel rModel, Lehen lehen, BuildPlanType bType)
	{
		ItemList needMat = new ItemList();
		BuildPlanMap buildPLan = rModel.getData().readTMXBuildPlan(bType, 4, -1);
		ItemList buildMat = BuildManager.makeMaterialList(buildPLan);
		needMat = lehen.getWarehouse().searchItemsNotInWarehouse(buildMat);
		buildMat = rModel.getServer().getRegionReagents(bType.name());
		for (Item item : buildMat.values())
		{
			System.out.println("Take Material from Warehouse");
			lehen.getWarehouse().getItemList().withdrawItem(item.ItemRef(), item.value());
		}
	}
	
	/**
	 * check for material and reagents of new building in warehouse
	 * true = all available take the the material and start building
	 * false = none
	 * else 
	 * @param rModel
	 * @param settle
	 * @return
	 */
	private boolean checkBuildMaterials(RealmModel rModel, Lehen lehen)
	{
		if (cmdBuilder.size() > 0)
		{
			buyList.clear();
			BuildPlanType bType = cmdBuilder.get(0).getbType();
			buyList = checkBuildingMaterials( rModel,  lehen,  bType);
			if (buyList.isEmpty())
			{
				return true;
			} else
			{
				return false;
			}
		}
		
		return false;
	}
	
	/**
	 * check for needed materials of the BuildPlanType
	 * - set nedMat list
	 * - set dontSell list 
	 * 
	 * @param rModel
	 * @param settle
	 * @param bType
	 * @return list of needed material 
	 */
	public ItemList checkBuildingMaterials(RealmModel rModel, Lehen lehen, BuildPlanType bType)
	{
		ItemList ignoreList = ConfigBasis.initIngnoreList();
		ItemList needMat = new ItemList();
		ItemList needReagents = new ItemList();
		BuildPlanMap buildPLan = rModel.getData().readTMXBuildPlan(bType, 4, -1);
		ItemList buildMat = BuildManager.makeMaterialList(buildPLan);
		needMat = lehen.getWarehouse().searchItemsNotInWarehouse(buildMat);
		buildMat = rModel.getServer().getRegionReagents(bType.name());
		needReagents = lehen.getWarehouse().searchItemsNotInWarehouse(buildMat);
		for (Item item : needReagents.values())
		{
			if (ignoreList.containsKey(item.ItemRef()) == false)
			{
				needMat.putItem(item.ItemRef(), item.value());
				dontSell.putItem(item.ItemRef(), item.value());
			}
		}
		for (String ref : ignoreList.keySet())
		{
			if (needMat.containsKey(ref))
			{
				needMat.remove(ref);
			}
		}
		return needMat;
	}
	
}
