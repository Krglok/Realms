package net.krglok.realms.model;

import net.krglok.realms.Common.LocationData;
import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;

import org.bukkit.entity.Player;

public class McmdBuilderLehen implements iModelCommand
{
	private ModelCommandType commandType = ModelCommandType.LEHENBUILDING;
	private RealmModel rModel;
	private int lehenId; 
	private BuildPlanType bType;
	private Player player;
	private LocationData position;
	
	

	public McmdBuilderLehen(RealmModel rModel, int lehenId, BuildPlanType bType, LocationData position, Player player)
	{
		super();
		this.rModel = rModel;
		this.lehenId = lehenId;
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
		return lehenId;
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
		String ownerName = rModel.getData().getLehen().getLehen(lehenId).getOwner().getPlayerName();
		rModel.getData().getLehen().getLehen(lehenId).buildManager().newBuild(buildPlan, position, ownerName);
//		player.sendMessage("Build Order send to SettleManager");
	}

	@Override
	public boolean canExecute()
	{
		if (( rModel.getData().getLehen().getLehen(lehenId) != null))
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
