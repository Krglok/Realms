package net.krglok.realms.model;

public class McmdDisable implements iModelCommand
{
	private ModelCommandType commandType = ModelCommandType.MODELDISABLE;
	private RealmModel rModel;
	
	public McmdDisable(RealmModel rModel)
	{
		this.rModel = rModel;
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
