package net.krglok.realms.manager;

import java.util.ArrayList;

import net.krglok.realms.builder.BuildStatus;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.McmdBuilder;
import net.krglok.realms.model.McmdBuyOrder;
import net.krglok.realms.model.McmdCreateSettle;
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
	private RealmModel rModel;
	
	private ArrayList<McmdBuilder> cmdBuilder;
	private ArrayList<McmdBuyOrder> cmdBuy;
	
	
	public SettleManager(RealmModel rModel)
	{
		this.rModel = rModel;
		this.cmdBuilder = new  ArrayList<McmdBuilder>();
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
	}
	
	private void getModelCommands(RealmModel rModel, Settlement settle)
	{
		if (rModel.getcommandQueue().isEmpty() == false)
		{
			rModel.messageData.log("Enabled Next Command");
			for (iModelCommand Mcmd : rModel.getcommandQueue())
			{
				switch (Mcmd.command())
				{
				case CREATEBUILDING :
					McmdBuilder cmd = (McmdBuilder) Mcmd;
					if (cmd.getSettleId() == settle.getId())
					{
						cmdBuilder.add(new McmdBuilder(rModel, cmd.getSettleId(), cmd.getbType(), cmd.getPosition(), cmd.getPlayer()));
						int index = rModel.getcommandQueue().indexOf(Mcmd);
						rModel.getcommandQueue().remove(index);
						cmd.getPlayer().sendMessage("Build Queue of SettleManager");

					}
					break;
				case BUYITEM:
					McmdBuyOrder buy = (McmdBuyOrder) Mcmd;
					if (buy.getSettleId() == settle.getId())
					{
						cmdBuy.add(new McmdBuyOrder(rModel, buy.getSettleId(), buy.getItemRef(), buy.getAmount(), buy.getPrice(), buy.getDelayDays()));
						int index = rModel.getcommandQueue().indexOf(Mcmd);
						rModel.getcommandQueue().remove(index);
					}
					
					break;
				case SELLITEM :
					break;
				default:
				}
			}
			// checkMoveQueue				
			return;
		}
	}
	
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
	
}
