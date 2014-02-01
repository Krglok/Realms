package net.krglok.realms.model;

import net.krglok.realms.colonist.Colony;

public class McmdColonyBuild implements iModelCommand
{
	private ModelCommandType commandType = ModelCommandType.BUILDCOLONY;
	private RealmModel rModel;
	private int colonyId;
	
	
	
	public McmdColonyBuild(RealmModel rModel, int colonyId)
	{
		super();
		this.rModel = rModel;
		this.colonyId = colonyId;
	}

	@Override
	public ModelCommandType command()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getParaTypes()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute()
	{
		for (Colony colony :  rModel.getColonys().values())
		{
			if (colony.getId() == this.colonyId)
			{
				colony.startUpBuild(colony.getName());
			}
		}
	}

	@Override
	public boolean canExecute()
	{
		for (Colony colony :  rModel.getColonys().values())
		{
			if (colony.getId() == this.colonyId)
			{
				return true;
			}
		}
		return false;
	}

}
