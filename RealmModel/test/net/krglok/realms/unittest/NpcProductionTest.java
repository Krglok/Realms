package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import net.krglok.realms.core.BoardItem;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.npc.NpcList;

import org.junit.Test;

public class NpcProductionTest
{

	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 

	DataStorage data = new DataStorage(dataFolder);

	ServerTest server = new ServerTest(data);
	
	private void showNpcList(NpcList npcList)
	{
		for (NpcData npc : npcList.values())
		{
			System.out.print(" |"+npc.getId());
			System.out.print(" |"+npc.getNpcType());
			System.out.println("");
		}
	}
	
	private void showBuildingNpc(Settlement settle)
	{
		for (Building building : settle.getBuildingList().getGroupSubList(200).values())
		{
			System.out.print(" |"+building.getBuildingType());
			System.out.print(" | owner :"+building.getOwnerId());
			System.out.println("");
			for (NpcData npc :settle.getResident().getNpcList().getWorkerNpc(building.getId()).values())
			{
				System.out.print(" |"+npc.getId());
				System.out.println("");
			}
		}
		
	}
	
	private void showSettleInfo(Settlement settle)
	{
		ArrayList<String> msg = new ArrayList<>();
		// Resident Analyse
		msg.add("["+settle.getId()+"]  "+settle.getName());
		msg.add("Sieldungstatus  ========================= ");
		msg.add("Biome         :"+settle.getBiome());
		msg.add("Age           : "+settle.getAge()+" Tage  ca. " + (settle.getAge()/30/12)+" Jahre ");
		msg.add("Einwohner     : "+settle.getResident().getSettlerCount()+"  :"+settle.getResident().getSettlerMax()+" Maximum");
		msg.add("Arbeiter      : "+settle.getTownhall().getWorkerCount());
		msg.add("freie Siedler : "+(settle.getResident().getSettlerCount()-settle.getTownhall().getWorkerCount()));
		msg.add("Betten        : "+settle.getResident().getSettlerMax());
		msg.add("Bankkonto     : "+(int) settle.getBank().getKonto());
		msg.add("Anzahl Gebäude: "+(int) settle.getBuildingList().size());
		msg.add("Items im Lager: "+(int) settle.getWarehouse().getItemCount());
		msg.add("fehlende Items: "+(int) settle.getRequiredProduction().size());
		msg.add("!  ");
		for (String s : msg)
		{
			System.out.println(s);
		}
	
	}

	private void showRequired(Settlement settle)
	{
		System.out.println("Required Item : ");
		for (Item item : settle.getRequiredProduction().values())
		{
			System.out.print(" |"+item.ItemRef());
			System.out.print(" |"+ConfigBasis.setStrformat2(item.value(),6));
			System.out.println("");
			
		}
	}
	
	private void showProduction(Settlement settle)
	{
		System.out.println("Production Overview : ");
		for (BoardItem item : settle.getProductionOverview().values())
		{
			System.out.print(" |"+ConfigBasis.setStrleft(item.getName(),12));
			System.out.print(" |"+ConfigBasis.setStrformat2(item.getLastValue(),6));
			System.out.print(" |"+ConfigBasis.setStrformat2(item.getCycleSum(),6));
			System.out.print(" |"+ConfigBasis.setStrformat2(item.getPeriodSum(),6));
			System.out.print(" |"+ConfigBasis.setStrformat2(settle.getWarehouse().getItemList().getValue(item.getName()),6));
			System.out.println("");
			
		}
	}
	
	private void ShowSale(Settlement settle)
	{
		System.out.println("Sale per Building : ");
		for (Building building : settle.getBuildingList().getGroupSubList(200).values())
		{
			System.out.print(" |"+building.getBuildingType());
			System.out.print(" |"+ConfigBasis.setStrformat2(building.getSales(),7));
			System.out.println("");
		}
		System.out.println("Sale per Settler : ");
		for (NpcData npc : settle.getResident().getNpcList().getSettleWorker().values())
		{
			System.out.print(" |"+ConfigBasis.setStrleft(npc.getName(),12));
			System.out.print(" |"+ConfigBasis.setStrleft(npc.getNpcType().name(),7));
			System.out.print(" |"+ConfigBasis.setStrformat2(npc.getMoney(),7));
			System.out.println("");
		}
		
	}
	
	@Test
	public void test()
	{
		data.initData();

		Settlement settle = data.getSettlements().getSettlement(1);
		settle.getResident().setNpcList(data.getNpcs().getSubList(settle.getId()));
		System.out.println(" Npc gesamt : "+data.getNpcs().size());
//		showSettleInfo(settle);
		int ohneArbeit = settle.setWorkerToBuilding(settle.getResident().getSettlerCount());
		System.out.println(" Npc Worker : "+settle.getTownhall().getWorkerNeeded()+" / "+ohneArbeit);
		System.out.println(" Settle Npc : "+settle.getResident().getNpcList().size());
//		showNpcList(settle.getResident().getNpcList());
//		showBuildingNpc(settle);
		settle.getWarehouse().depositItemValue("BREAD", 20);
		settle.getWarehouse().depositItemValue("LOG", 164);
		for (Building building : settle.getBuildingList().getGroupSubList(200).values())
		{
			building.setSales(0.0);
		}
		
		settle.doProduce(server, data);
		showRequired(settle);
		showProduction(settle);

		settle.doProduce(server, data);
		ShowSale(settle);
		showRequired(settle);
		showProduction(settle);
		
		fail("Not yet implemented");
	}

}
