package net.krglok.realms.manager;

import java.util.ArrayList;

import net.krglok.realms.admin.AdminModus;
import net.krglok.realms.admin.AdminStatus;
import net.krglok.realms.builder.BuildStatus;
import net.krglok.realms.core.Settlement;
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
		
		//check for money
		
		// check for overstocking
		
		// check for overpopulation
		
		// check for optional actions
		buildOrder(settle);
		sellOrder(settle);
		
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

	
	private void sellOrder(Settlement settle)
	{
		if (settle.tradeManager().isSellActiv() == false)
		{
			if (cmdSell.isEmpty() == false)
			{
				McmdSellOrder sellOrder = cmdSell.get(0);
				settle.tradeManager().newSellOrder(sellOrder);
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
	
	private void checkSellOrder(Settlement settle)
	{
		if (settle.tradeManager().isSellActiv() == false)
		{
			if (cmdSell.isEmpty() == false)
			{
				McmdSellOrder sellOrder = cmdSell.get(0);
				int sellAmount = sellOrder.getAmount();
				int StoreAmount = settle.getWarehouse().getItemList().getValue(sellOrder.getItemRef());
				
				if ()
				int MinAmount = settle.getWarehouse().ge
			}
		}
	}

	
}
