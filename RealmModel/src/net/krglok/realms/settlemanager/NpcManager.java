package net.krglok.realms.settlemanager;

import java.util.ArrayList;

import net.krglok.realms.core.Building;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemArray;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.ServerInterface;

/**
 * Der NPC Manager übernimmt die automatische Verwaltung von NPC settlements.
 * Der Manager hat kein Gedächtnis und arbeitet nach dem Ergebnis der Auswertung.
 * Er kann Production building ein/ausschalten. Hierzu benutzt er hysterese
 * Schalter für die verschiedenen Itemtypen.
 * 
 * @author Windu  01.01.2014
 * 
 */
public class NpcManager 
{
	private static final double MaxSupply = 3.0;
	private static final double MinSupply = 2.0;
//	private static final double MaxTool = 3.0;
//	private static final double MinTool = 2.0;
	
	private ArrayList<ProductBuilding> productList;
	private NpcItemList npcVersorgung;
	private NpcItemList npcDefend;
	private ArrayList<NpcRule> ruleList;
	private Settlement settle;
	
	public NpcManager(Settlement settlement) 
	{
		productList = new ArrayList<ProductBuilding>();
		setNpcVersorgung(new NpcItemList());
		setRuleList(new ArrayList<NpcRule>());
		settle = settlement;
	}

	public ArrayList<ProductBuilding> getProductList() 
	{
		return productList;
	}

	public void setProductList(ServerInterface server) 
	{
		ItemArray items ;
		for (Building building : settle.getBuildingList().getBuildingList().values())
		{
			items = building.produce(server);
			for (Item item : items)
			{
				productList.add(new ProductBuilding(item.ItemRef(), building.getBuildingType(), building.getId()));
			}
		}
	}

	
	public NpcItemList getNpcVersorgung()
	{
		return npcVersorgung;
	}

	public void setNpcVersorgung(NpcItemList npcVersorgung)
	{
		this.npcVersorgung = npcVersorgung;
	}

	public NpcItemList getNpcDefend()
	{
		return npcDefend;
	}

	public void setNpcDefend(NpcItemList npcDefend)
	{
		this.npcDefend = npcDefend;
	}

	public ArrayList<NpcRule> getRuleList()
	{
		return ruleList;
	}

	public void setRuleList(ArrayList<NpcRule> ruleList)
	{
		this.ruleList = ruleList;
	}

	public static double getMaxsupply()
	{
		return MaxSupply;
	}

	public static double getMinsupply()
	{
		return MinSupply;
	}

	public void initSupplyRules()
	{
		
	}

	public void doVersorgung()
	{
//		int MaxCount = (int)(MaxSupply * settle.getResident().getSettlerCount());
//		int MinCount = (int)(MinSupply * settle.getResident().getSettlerCount());
		// check supply max
		
	}
}
