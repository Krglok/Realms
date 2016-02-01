package net.krglok.realms.model;

import net.krglok.realms.core.Settlement;
import net.krglok.realms.kingdom.Lehen;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.npc.NpcData;

/**
 * <pre>
 * Search for a settler in a settlement to hire for a lehen.
 * This command can be used from player and from LehenManager
 * The Lehen must pay for a settler !
 * Beggar cost half of price
 * the npc get halfe of the price
 * if npc is married the husband get the money
 * if husband is dead the children get the money
 * 
 * 
 * @author Windu
 * 
 * 17.01.2016
 * 
 * </pre>
 */


public class McmdHireSettler implements iModelCommand
{

	private ModelCommandType commandType = ModelCommandType.HIRESETTLER;
	private RealmModel rModel;
	private int settleId;
	private int lehenId;
	private double price;
	private NpcData npc; 


	public McmdHireSettler(RealmModel rModel, int settleId, int lehenId,
			double price)
	{
		super();
		this.rModel = rModel;
		this.settleId = settleId;
		this.lehenId = lehenId;
		this.price = price;
	}
	
	@Override
	public ModelCommandType command()
	{
		return commandType;
	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] { RealmModel.class.getName() , int.class.getName() , int.class.getName() , int.class.getName() , double.class.getName()  };
	}

	@Override
	public void execute()
	{
		Settlement settle = rModel.getData().getSettlements().getSettlement(settleId);
		Lehen lehen = rModel.getData().getLehen().getLehen(lehenId);
		if (lehen.getResident().getSettlerMax()<=lehen.getResident().getSettlerCount())
		{
			lehen.getMsg().add("[REALMS] Max Resident HIRE Npc "+npc.getId()+" to Lehen "+lehenId+" from "+settleId);
			return;
		}
		if (npc.getNpcType() == NPCType.BEGGAR)
		{
			// Beggar cost only the HalfPrice
			price = price / 2;
		}
		double npcPrice = price / 2;
		double settlePrice  = price / 2;
		if (npc.isMaried())
		{
			if (rModel.getData().getNpcs().get((npc.getNpcHusband())) != null)
			{
				if (rModel.getData().getNpcs().get((npc.getNpcHusband())).isAlive())
				{
					rModel.getData().getNpcs().get((npc.getNpcHusband())).depositMoney(price);
				} else
				{
					int maxChild = rModel.getData().getNpcs().getChildNpc(npc.getId()).values().size();
					if (maxChild > 0)
					{
						double childPrice = price / maxChild;
						for (NpcData child :rModel.getData().getNpcs().getChildNpc(npc.getId()).values())
						{
							if (child.isAlive())
							{
								child.depositMoney(childPrice);
							} else
							{
								npc.depositMoney((childPrice));
							}
						}
					} else
					{
						npc.depositMoney(npcPrice);
					}
				}
			} else
			{
				npc.depositMoney(npcPrice);
			}

		} else
		{
			npc.depositMoney(npcPrice);
		}
		npc.setSettleId(0);
		npc.setLehenId(lehenId);
		settle.getResident().setNpcList(rModel.getData().getNpcs().getSubListSettle(settleId));
		lehen.getResident().getNpcList().add(npc);
		System.out.println("[REALMS] HIRE Npc "+npc.getId()+" to Lehen "+lehenId+" from "+settleId);
		settle.getBank().depositKonto(settlePrice, "Hire",settle.getId());
		lehen.getBank().depositKonto(-price, "Hire", settle.getId());
		
	}

	@Override
	public boolean canExecute()
	{
		if (rModel.getData().getSettlements().getSettlement(settleId) == null)
		{
			return false;
		}
		if (rModel.getData().getLehen().getLehen(lehenId) == null)
		{
			return false;
		}
		if (rModel.getData().getLehen().getLehen(lehenId).getBank().getKonto() < price)
		{
			return false;
		}
		npc = rModel.getData().getSettlements().getSettlement(settleId).getResident().findRecrute();
		if (npc == null)
		{
			return false;
		}

		return true;
	}

}
