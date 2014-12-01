package net.krglok.realms.manager;

import java.util.ArrayList;

import org.bukkit.Material;

import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.builder.BuildStatus;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.TradeMarketOrder;
import net.krglok.realms.core.TradeOrder;
import net.krglok.realms.core.TradeStatus;
import net.krglok.realms.core.TradeType;
import net.krglok.realms.model.CommandQueue;
import net.krglok.realms.model.McmdBuilder;
import net.krglok.realms.model.McmdBuyOrder;
import net.krglok.realms.model.McmdCreateSettle;
import net.krglok.realms.model.McmdDepositeBank;
import net.krglok.realms.model.McmdSellOrder;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.model.iModelCommand;

/**
 * the SettleManager realize the Controller and Manager for the Settlement
 * - basic trading management
 * - basic mission management
 * - command management 
 * the manager can interact with the world and send commands and requests to other managers
 * 
 * @author oduda
 *
 */
public class SettleManager
{
//	private AdminModus adminMode;
//	private AdminStatus status;
	
	private ArrayList<McmdBuilder> cmdBuilder;
	private ArrayList<McmdBuyOrder> cmdBuy;
	private ArrayList<McmdSellOrder> cmdSell;
	
	private ItemList buyList;
	private ItemList dontSell;
	private int checkCounter = 0;
	
	public SettleManager()
	{
		this.cmdBuilder = new  ArrayList<McmdBuilder>();
		this.cmdBuy = new ArrayList<McmdBuyOrder>();
		this.cmdSell = new ArrayList<McmdSellOrder>();
//		this.adminMode = AdminModus.PLAYER;
//		this.status    = AdminStatus.NONE;
		this.buyList = new ItemList();
		this.dontSell = new ItemList();
	}
	
	public ItemList getBuyList()
	{
		return buyList;
	}

	public void setBuyList(ItemList buyList)
	{
		this.buyList = buyList;
	}

	public ItemList getDontSell()
	{
		return dontSell;
	}

	public void setDontSell(ItemList dontSell)
	{
		this.dontSell = dontSell;
	}

	public void newCommand()
	{
		
	}
	
	public void run(RealmModel rModel, Settlement settle)
	{
		// check for optional actions
		if (checkCounter <= 0)
		{
		
//			System.out.println("1");
			getModelCommands(rModel, settle);
			// check for hunger
//			System.out.println("2");
			checkHunger ( rModel,  settle);
			//check for money
//			System.out.println("3");
			checkMoneyLevel( rModel,  settle);
			// check for Required Items
//			System.out.println("4");
			if (checkRequiredMaterials(rModel,  settle))
			{
//				System.out.println("4.1");
				buyRequiredMaterials(rModel, settle);
			}
	
			// check for overstocking
//			System.out.println("5");
			checkOverStockSell( rModel,  settle);
			// check for overpopulation
			
//				System.out.println("6");
				if (checkBuildMaterials(rModel, settle))
				{
//					System.out.println("6.1");
					buildOrder(rModel,settle);
					checkCounter = 0;
				} else
				{	
//					System.out.println("6.2");
					buyBuildMaterials(rModel, settle);
					checkCounter = 100;
				}
//			System.out.println("7");
			checkSellOrder(rModel, settle);
//			System.out.println("8");
			sellOrder(rModel,settle);
//			System.out.println("9");
			buyOrder( rModel, settle);
		} else
		{
			checkCounter--;
		}
	}
	
	private void getModelCommands(RealmModel rModel, Settlement settle)
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
					if (cmd.getSettleId() == settle.getId())
					{
						cmdBuilder.add(new McmdBuilder(rModel, cmd.getSettleId(), cmd.getbType(), cmd.getPosition(), cmd.getPlayer()));
						removeCmd.add(Mcmd);
						if (cmd.getPlayer() != null)
						{	
							cmd.getPlayer().sendMessage("Build Queue of SettleManager");
						}

					}
					break;
				case BUYITEM:
					McmdBuyOrder buy = (McmdBuyOrder) Mcmd;
					if (buy.getSettleId() == settle.getId())
					{
						cmdBuy.add(new McmdBuyOrder(rModel, buy.getSettleId(), buy.getItemRef(), buy.getAmount(), buy.getPrice(), buy.getDelayDays()));
						removeCmd.add(Mcmd);
					}
					
					break;
				case SELLITEM :
					McmdSellOrder sell = (McmdSellOrder) Mcmd;
					if (sell.getSettleId() == settle.getId())
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
	 * prueft ob BuildManager den naechsten Auftrag erledigen kann
	 * @param settle
	 */
	private void buildOrder(RealmModel rModel,Settlement settle)
	{
		if (settle.buildManager().getStatus() == BuildStatus.NONE)
		{
			if (cmdBuilder.isEmpty() == false)
			{
				if (cmdBuilder.get(0).canExecute())
				{
					takeBuildingMaterials(rModel,  settle, cmdBuilder.get(0).getbType());
					cmdBuilder.get(0).execute();
					cmdBuilder.remove(0);
					System.out.println("Settle send build order");
				}
			}
		}
	}

	
	private void sellOrder(RealmModel rModel,Settlement settle)
	{
		if (settle.tradeManager().isSellActiv() == false)
		{
			if (cmdSell.isEmpty() == false)
			{
				McmdSellOrder sellOrder = cmdSell.get(0);
				switch (sellOrder.getTradeStatus())
				{
				case READY :
					settle.tradeManager().newSellOrder(sellOrder);
					sellOrder.setTradeStatus(TradeStatus.STARTED);
					break;
				case STARTED :
					break;
				case FULFILL:
				case DECLINE:
					cmdSell.remove(sellOrder);
					break;
				default :
				}
			}
		}
	}
	
	
	private void buyOrder(RealmModel rModel,Settlement settle)
	{
		if (settle.tradeManager().isBuyActiv() == false)
		{
			if (cmdBuy.isEmpty() == false)
			{
				McmdBuyOrder buyOrder = cmdBuy.get(0);
				settle.tradeManager().newBuyOrder(settle, buyOrder.getItemRef(), buyOrder.getAmount());
				cmdBuy.remove(0);
			}
		}
		
	}
	
	
//	public AdminModus getAdminMode()
//	{
//		return adminMode;
//	}
//
//	public void setAdminMode(AdminModus adminMode)
//	{
//		this.adminMode = adminMode;
//	}
//
//	public AdminStatus getStatus()
//	{
//		return status;
//	}

	public ArrayList<McmdBuilder> getCmdBuilder()
	{
		return cmdBuilder;
	}

	public ArrayList<McmdBuyOrder> getCmdBuy()
	{
		return cmdBuy;
	}

	public ArrayList<McmdSellOrder> getCmdSell()
	{
		return cmdSell;
	}
	
	private void checkSellOrder(RealmModel rModel, Settlement settle)
	{
		if (settle.tradeManager().isSellActiv() == false)
		{
			if (cmdSell.isEmpty() == false)
			{
				McmdSellOrder sellOrder = cmdSell.get(0);
				int sellAmount = sellOrder.getAmount();
				int storeAmount = settle.getWarehouse().getItemList().getValue(sellOrder.getItemRef());
				int minAmount = getMinStorage(rModel, settle, sellOrder.getItemRef());
				if (storeAmount > minAmount)
				{
					if ((storeAmount-minAmount) < sellAmount)
					{
						sellOrder.setAmount((storeAmount-minAmount));
					}
					
				} else
				{
					sellOrder.setAmount(0);
					/// bei DECLINE wird der nächste Auftrag bearbeitet
					/// bei WAIT wird nicjt der nächste genommen
					sellOrder.setTradeStatus(TradeStatus.DECLINE);
				}
			}
		}
	}


	private int getMinStorage(RealmModel rModel, Settlement settle, String itemRef)
	{
		int matFactor = rModel.getServer().getBioneFactor( settle.getBiome(), Material.getMaterial(itemRef));
		int sellLimit = 0;
		if (dontSell.containsKey(itemRef))
		{
			sellLimit = dontSell.getValue(itemRef);
		}
		
		if (matFactor >= 0)
		{
			if (rModel.getConfig().getToolItems().containsKey(itemRef))
			{
				return 64 - (64 * matFactor / 100) + sellLimit;
			}
			if (rModel.getConfig().getWeaponItems().containsKey(itemRef))
			{
				return 64 - (64 * matFactor / 100) + sellLimit;
			}
			if (rModel.getConfig().getArmorItems().containsKey(itemRef))
			{
				return 64- (64 * matFactor / 100) + sellLimit; 
			}
			if (rModel.getConfig().getFoodItems().containsKey(itemRef))
			{
				return settle.getResident().getSettlerMax() * 16 ;
			}
			if (rModel.getConfig().getValuables().containsKey(itemRef))
			{
				return 64 - (64 * matFactor / 100) + sellLimit;
			}
			if (rModel.getConfig().getBuildMaterialItems().containsKey(itemRef))
			{
				return 196 - (64 * matFactor / 100) + sellLimit;
			}
			if (rModel.getConfig().getOreItems().containsKey(itemRef))
			{
				return 64 - (64 * matFactor / 100) + sellLimit;
			}
			if (rModel.getConfig().getMaterialItems().containsKey(itemRef))
			{
				return 64 - (64 * matFactor / 100) + sellLimit;
			}
			if (rModel.getConfig().getRawItems().containsKey(itemRef))
			{
				return 64 - (64 * matFactor / 100) + sellLimit;
			}
			
			return 8 + sellLimit;
		} else
		{
			return 9999;
		}
	}
	
	public ItemList getOverStock (RealmModel rModel, Settlement settle)
	{
		ItemList items = new ItemList();
		
		for (Item store : settle.getWarehouse().getItemList().values())
		{
			int min = getMinStorage(rModel, settle, store.ItemRef());
			if (store.value() > min)
			{
				items.addItem(new Item(store.ItemRef(),store.value() - min));
			}
		}
		
		return items;
	}
	
	/**
	 * Erstellt SellOrder fuer Ueberbestand
	 * @param rModel
	 * @param settle
	 */
	private void checkOverStockSell(RealmModel rModel, Settlement settle)
	{
		if (settle.tradeManager().isSellActiv() == false)
		{
			ItemList sellItems = getOverStock(rModel, settle);
			
			for (Item sellItem : sellItems.values())
			{
				boolean isCmdFound = false;
				for (TradeMarketOrder order : rModel.getTradeMarket().getSettleOrders(settle.getId()).values())
				{
					if (order.ItemRef().equalsIgnoreCase(sellItem.ItemRef()))
					{
						isCmdFound = true;
					}
				}
				
				if (isCmdFound == false)				
				{
					McmdSellOrder sellOrder = new McmdSellOrder(rModel, settle.getId(), sellItem.ItemRef(), sellItem.value(), 0.0, 10);
					settle.tradeManager().newSellOrder(sellOrder );
					return;
				}
			}
		}
	}
	
	private void checkHunger (RealmModel rModel, Settlement settle)
	{
		if (settle.getFoodFactor() < 0)
		{
			if (settle.tradeManager().isBuyActiv() == false)
			{
				settle.tradeManager().newBuyOrder(settle, Material.WHEAT.name(), 100);
			}
		}
	}
	
	private void checkMoneyLevel( RealmModel rModel, Settlement settle)
	{
		
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
	private boolean checkBuildMaterials(RealmModel rModel, Settlement settle)
	{
		if (cmdBuilder.size() > 0)
		{
			buyList.clear();
			BuildPlanType bType = cmdBuilder.get(0).getbType();
			buyList = checkBuildingMaterials( rModel,  settle,  bType);
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
	 * take material of building from warehouse
	 * @param rModel
	 * @param settle
	 * @param bType
	 */
	public void takeBuildingMaterials(RealmModel rModel, Settlement settle, BuildPlanType bType)
	{
		ItemList needMat = new ItemList();
		BuildPlanMap buildPLan = rModel.getData().readTMXBuildPlan(bType, 4, -1);
		ItemList buildMat = BuildManager.makeMaterialList(buildPLan);
		needMat = settle.getWarehouse().searchItemsNotInWarehouse(buildMat);
		buildMat = rModel.getServer().getRegionReagents(bType.name());
		for (Item item : buildMat.values())
		{
			System.out.println("Take Material from Warehouse");
			settle.getWarehouse().getItemList().withdrawItem(item.ItemRef(), item.value());
		}
	}
	
	/**
	 * check for needed materials of the BuildPlanType
	 * 
	 * @param rModel
	 * @param settle
	 * @param bType
	 * @return list of needed material 
	 */
	public ItemList checkBuildingMaterials(RealmModel rModel, Settlement settle, BuildPlanType bType)
	{
		ItemList needMat = new ItemList();
		ItemList needReagents = new ItemList();
		BuildPlanMap buildPLan = rModel.getData().readTMXBuildPlan(bType, 4, -1);
		ItemList buildMat = BuildManager.makeMaterialList(buildPLan);
		needMat = settle.getWarehouse().searchItemsNotInWarehouse(buildMat);
		buildMat = rModel.getServer().getRegionReagents(bType.name());
		needReagents = settle.getWarehouse().searchItemsNotInWarehouse(buildMat);
		for (Item item : needReagents.values())
		{
			needMat.putItem(item.ItemRef(), item.value());
			dontSell.putItem(item.ItemRef(), item.value());
		}
		return needMat;
	}
	

	/**
	 * Buy material for build the BuildPlanType
	 * add buy order to cmdBuy.queue
	 * 
	 * @param rModel
	 * @param settle
	 */
	private void buyBuildMaterials(RealmModel rModel, Settlement settle)
	{
		for (Item item : buyList.values())
		{
			if (settle.getTrader().getBuyOrders().containsKey(item.ItemRef()) == false)
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
					cmdBuy.add(new McmdBuyOrder(rModel, settle.getId(), item.ItemRef(), item.value(), 0.0, 5));
					dontSell.addItem(new Item(item.ItemRef(), item.value()));
					System.out.println("Settle send buy order for build : "+item.ItemRef());
				}
			}
		}
		buyList.clear();
	}

	/**
	 * check for required material for production
	 * add the material to the buy list
	 *  
	 * @param rModel
	 * @param settle
	 * @return
	 */
	private boolean checkRequiredMaterials(RealmModel rModel, Settlement settle)
	{
			buyList.clear();
//			System.out.println(settle.getId()+"/"+ settle.getRequiredProduction().size());
			for (Item item : settle.getRequiredProduction().values())
			{
				buyList.addItem(new Item(item.ItemRef(), item.value()));
			}
			
			if (buyList.isEmpty())
			{
				return false;
			} else
			{
				return true;
			}
	}

	/**
	 * buy the required material for production
	 * add buy order to cmdBuy.queue
	 * 
	 * @param rModel
	 * @param settle
	 */
	private void buyRequiredMaterials(RealmModel rModel, Settlement settle)
	{
		for (Item item : buyList.values())
		{
			if (settle.getTrader().getBuyOrders().containsKey(item.ItemRef()) == false)
			{
				boolean isCmdFound = false;
				
				if (isCmdFound == false)				
				{
//					System.out.println(item.ItemRef()+":"+item.value());
					cmdBuy.add(new McmdBuyOrder(rModel, settle.getId(), item.ItemRef(), item.value(), 0.0, 5));
					dontSell.addItem(new Item(item.ItemRef(), item.value()));
				}
			}
		}
		buyList.clear();
	}
	
}
