package net.krglok.realms.model;

import org.bukkit.entity.Player;

import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.LocationData;

public class McmdBuilder implements iModelCommand
{
	private ModelCommandType commandType = ModelCommandType.CREATEBUILDING;
	private RealmModel rModel;
	private int settleId; 
	private BuildPlanType bType;
	private Player player;
	private LocationData position;
	
	

	public McmdBuilder(RealmModel rModel, int settleId, BuildPlanType bType, LocationData position, Player player)
	{
		super();
		this.rModel = rModel;
		this.settleId = settleId;
		this.bType = bType;
		this.position = position;
		this.player = player;
	}

	public ModelCommandType getCommandType()
	{
		return commandType;
	}

	public int getSettleId()
	{
		return settleId;
	}

	public BuildPlanType getbType()
	{
		return bType;
	}
	
	public LocationData getPosition()
	{
		return position;
	}

	public Player getPlayer()
	{
		return player;
	}

	@Override
	public ModelCommandType command()
	{
		return commandType;
	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] {RealmModel.class.getName(), int.class.getName(),BuildPlanType.class.getName(), LocationData.class.getName() };
	}

	@Override
	public void execute()
	{
//		System.out.println("Builder");
		BuildPlanMap buildPlan = rModel.getData().readTMXBuildPlan(bType, 4, -1);
		rModel.getSettlements().getSettlement(settleId).buildManager().newBuild(buildPlan, position, rModel.getSettlements().getSettlement(settleId).getOwner());
//		player.sendMessage("Build Order send to SettleManager");
	}

	@Override
	public boolean canExecute()
	{
		if ((rModel.getSettlements().getSettlement(settleId) != null))
		{
			if (bType != BuildPlanType.NONE)
			{
				if (position.getWorld() != "")
				{
					return true;
				}
			}
		} else
		{
//			System.out.println();
		}
		return false;
	}

}
