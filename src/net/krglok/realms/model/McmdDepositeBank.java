package net.krglok.realms.model;

import net.krglok.realms.core.Settlement;

public class McmdDepositeBank implements iModelCommand
{
	private ModelCommandType commandType = ModelCommandType.DEPOSITBANK;
	private RealmModel rModel;
	private int settleId;
	private double amount;
	private String userName;

	
	public McmdDepositeBank(RealmModel rModel, int settleId, double amount, String userName)
	{
		super();
		this.rModel = rModel;
		this.settleId = settleId;
		this.amount = amount;
		this.userName = userName;
	}
	
	@Override
	public ModelCommandType command()
	{
		return commandType;
	}


	@Override
	public String[] getParaTypes()
	{
		return new String[] {RealmModel.class.getName(), int.class.getName() , double.class.getName(), String.class.getName() };
	}

	@Override
	public void execute()
	{
		Settlement settle = rModel.getSettlements().getSettlement(settleId);
		settle.getBank().depositKonto(amount, userName,settle.getId());
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
