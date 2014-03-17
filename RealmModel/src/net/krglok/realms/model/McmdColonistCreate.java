package net.krglok.realms.model;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.colonist.Colony;
import net.krglok.realms.core.LocationData;

public class McmdColonistCreate implements iModelCommand
{

	private ModelCommandType commandType = ModelCommandType.CREATECOLONY;
	private RealmModel rModel;
	private LocationData centerPos;
	private String colonyName;
	private String owner;
	
	
	public McmdColonistCreate(RealmModel rModel, String name, LocationData centerPos, String owner)
	{
		super();
		this.rModel = rModel;
		this.centerPos = centerPos;
		this.colonyName = name;
		this.owner = owner;
	}

	@Override
	public ModelCommandType command()
	{
		return commandType;
	}

	@Override
	public String[] getParaTypes()
	{
		return new String [] { RealmModel.class.getName(), String.class.getName(), LocationData.class.getName(), String.class.getName() };
	}

	@Override
	public void execute()
	{
		rModel.getColonys().addColony(Colony.newColony(colonyName, centerPos, owner,rModel.getLogList()));
	}

	@Override
	public boolean canExecute()
	{
		return true;
	}

}
