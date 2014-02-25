package net.krglok.realms.manager;

import java.util.ArrayList;

import org.bukkit.Material;

import net.krglok.realms.admin.AdminModus;
import net.krglok.realms.admin.AdminStatus;
import net.krglok.realms.builder.BuildStatus;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.Settlement;
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
	private AdminModus adminMode;
	private AdminStatus status;
	
	private ArrayList<McmdBuilder> cmdBuilder;
	private ArrayList<McmdBuyOrder> cmdBuy;
	private ArrayList<McmdSellOrder> cmdSell;
	
	
	public SettleManager()
	{
		this.cmdBuilder = new  ArrayList<McmdBuilder>();
		this.cmdBuy = new ArrayList<McmdBuyOrder>();
		this.cmdSell = new ArrayList<McmdSellOrder>();
		this.adminMode = AdminModus.PLAYER;
		this.status    = AdminStatus.NONE;
	}
	
	public void newCommand()
	{
		
	}
	
	public void run(RealmModel rModel, Settlement settle)
	{
		getModelCommands(rModel, settle);
		// check for hunger
		checkHunger ( rModel,  settle);
		//check for money
		checkMoneyLevel( rModel,  settle);
		// check for overstocking
		checkOverStockSell( rModel,  settle);
		// check for overpopulation
		
		// check for optional actions
		buildOrder(settle);
		checkSellOrder(rModel, settle);
		sellOrder(rModel,settle);
		
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
	private void buildOrder(Settlement settle)
	{
		if (settle.buildManager().getStatus() == BuildStatus.NONE)
		{
			if (cmdBuilder.isEmpty() == false)
			{
				if (cmdBuilder.get(0).canExecute())
				{
					cmdBuilder.get(0).execute();
					cmdBuilder.remove(0);
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
	
	public AdminModus getAdminMode()
	{
		return adminMode;
	}

	public void setAdminMode(AdminModus adminMode)
	{
		this.adminMode = adminMode;
	}

	public AdminStatus getStatus()
	{
		return status;
	}

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
		
		if (matFactor >= 0)
		{
			if (rModel.getConfig().getToolItems().containsKey(itemRef))
			{
				return 64 - (64 * matFactor / 100);
			}
			if (rModel.getConfig().getWeaponItems().containsKey(itemRef))
			{
				return 64 - (64 * matFactor / 100);
			}
			if (rModel.getConfig().getArmorItems().containsKey(itemRef))
			{
				return 64- (64 * matFactor / 100); 
			}
			if (rModel.getConfig().getFoodItems().containsKey(itemRef))
			{
				return settle.getResident().getSettlerMax() * 16 ;
			}
			if (rModel.getConfig().getValuables().containsKey(itemRef))
			{
				return 64 - (64 * matFactor / 100);
			}
			if (rModel.getConfig().getBuildMaterialItems().containsKey(itemRef))
			{
				return 64 - (64 * matFactor / 100);
			}
			if (rModel.getConfig().getOreItems().containsKey(itemRef))
			{
				return 64 - (64 * matFactor / 100);
			}
			if (rModel.getConfig().getMaterialItems().containsKey(itemRef))
			{
				return 64 - (64 * matFactor / 100);
			}
			if (rModel.getConfig().getRawItems().containsKey(itemRef))
			{
				return 64 - (64 * matFactor / 100);
			}
			
			return 8;
		} else
		{
			return 9999;
		}
	}
	
	private ItemList getOverStock (RealmModel rModel, Settlement settle)
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
				McmdSellOrder sellOrder = new McmdSellOrder(rModel, settle.getId(), sellItem.ItemRef(), sellItem.value(), 0.0, 10);
				settle.tradeManager().newSellOrder(sellOrder );
				return;
		
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
	
	
}
