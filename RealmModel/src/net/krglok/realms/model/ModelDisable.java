package net.krglok.realms.model;

public class ModelDisable implements iModelCommand
{
	private ModelCommandType commandType = ModelCommandType.MODELDISABLE;
	private RealmModel rModel;
	private boolean value ;
	
	public ModelDisable(RealmModel rModel)
	{
		this.rModel = rModel;
		value = false;
	}

	@Override
	public ModelCommandType command()
	{
		return commandType;
	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] {RealmModel.class.getName()};
	}

	@Override
	public void execute()
	{
		if (rModel.isInit())
		{
			rModel.disableModel();
		}
	}

	@Override
	public boolean canExecute()
	{
		return rModel.isInit();
	}

}
