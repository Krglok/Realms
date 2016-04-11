package net.krglok.realms.model;

import net.krglok.realms.Common.LocationData;
import net.krglok.realms.builder.BuildPlanType;

public class McmdColonyBuilder  implements iModelCommand
{
	private ModelCommandType commandType = ModelCommandType.CREATEBUILDING;
	private RealmModel rModel;
	private BuildPlanType buildPlanType;
	private LocationData centerPos;
	private int colonyId;

	public McmdColonyBuilder(RealmModel rModel, int colonyId, BuildPlanType bType, LocationData centerPos)
	{
		this.rModel = rModel;
		this.colonyId = colonyId;
		this.buildPlanType = bType;
		this.centerPos = centerPos;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canExecute()
	{
//		if (rModel.getColonys().get)
		return false;
	}

}
