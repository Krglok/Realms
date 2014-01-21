package net.krglok.realms.model;

import net.krglok.realms.core.Settlement;

public class McmdDepositWarehouse implements iModelCommand
{
	private ModelCommandType commandType = ModelCommandType.DEPOSITWAREHOUSE;
	private RealmModel rModel;
	private int settleId;
	private String itemRef;
	private int amount;
	
	
	public McmdDepositWarehouse(RealmModel rModel, int settleId, String itemRef,int amount )
	{
		this.rModel = rModel;
		this.settleId = settleId;
		this.amount = amount;
		this.itemRef = itemRef;
	}

	@Override
	public ModelCommandType command()
	{
		return commandType;
	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] {RealmModel.class.getName(), int.class.getName(), String.class.getName(), int.class.getName()  };
	}

	@Override
	public void execute()
	{
		Settlement settle = rModel.getSettlements().getSettlement(settleId);
		settle.getWarehouse().depositItemValue(itemRef, amount);
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
