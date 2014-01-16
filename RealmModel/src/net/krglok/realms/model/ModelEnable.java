package net.krglok.realms.model;

public class ModelEnable implements iModelCommand
{

	private ModelCommandType commandType = ModelCommandType.MODELENABLE;
	private RealmModel rModel;
	private boolean value ;
	
	public ModelEnable(RealmModel rModel)
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
		if (!rModel.isInit())
		{
			rModel.initModel();
		}
	}

	@Override
	public boolean canExecute()
	{
		return (!rModel.isInit());
	}

}
