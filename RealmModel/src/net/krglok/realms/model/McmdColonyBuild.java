package net.krglok.realms.model;

import net.krglok.realms.colonist.Colony;

public class McmdColonyBuild implements iModelCommand
{
	private ModelCommandType commandType = ModelCommandType.BUILDCOLONY;
	private RealmModel rModel;
	private int colonyId;
	private boolean isCleanUp;
	
	
	
	public McmdColonyBuild(RealmModel rModel, int colonyId, boolean isCleanUp)
	{
		super();
		this.rModel = rModel;
		this.colonyId = colonyId;
		this.isCleanUp = isCleanUp;
	}

	@Override
	public ModelCommandType command()
	{
		System.out.println("BUILDCOLONY");
		return ModelCommandType.BUILDCOLONY;
	}

	@Override
	public String[] getParaTypes()
	{
		return  new String [] { RealmModel.class.getName(), Integer.class.getName(), Boolean.class.getName() };
	}

	@Override
	public void execute()
	{
		for (Colony colony :  rModel.getColonys().values())
		{
			if (colony.getId() == this.colonyId)
			{
				colony.startUpBuild(colony.getName(),isCleanUp);
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
