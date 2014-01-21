package net.krglok.realms.model;

import net.krglok.realms.core.Settlement;

public class McmdSetSettler implements iModelCommand
{
	private ModelCommandType commandType = ModelCommandType.SETSETTLER;
	private RealmModel rModel;
	private int settleId;
	private int amount;

	
	
	public McmdSetSettler(RealmModel rModel, int settleId, int amount)
	{
		super();
		this.rModel = rModel;
		this.settleId = settleId;
		this.amount = amount;
	}

	@Override
	public ModelCommandType command()
	{
		return commandType;
	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] { RealmModel.class.getName() , int.class.getName() , int.class.getName()  };
	}

	@Override
	public void execute()
	{
		Settlement settle = rModel.getSettlements().getSettlement(settleId);
		settle.getResident().setSettlerCount(amount);
	}

	@Override
	public boolean canExecute()
	{
		if (rModel.getSettlements().getSettlement(settleId) != null)
		{
			return true;
		}
		return false;
	}

}
