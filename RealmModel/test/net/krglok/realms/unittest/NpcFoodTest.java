package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.npc.NpcList;

import org.junit.Test;

public class NpcFoodTest
{
	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 

	DataStorage data = new DataStorage(dataFolder);

	ServerTest server = new ServerTest(data);
	
	private void showNpcList(NpcList npcList)
	{
		System.out.print(" |"+ConfigBasis.setStrleft("id",3));
		System.out.print(" |"+ConfigBasis.setStrleft("Type",5));
		System.out.print(" |"+ConfigBasis.setStrleft("Food",6));
		System.out.print(" |"+ConfigBasis.setStrleft("Hunger",6));
		System.out.print(" |"+ConfigBasis.setStrleft("Happy",6));
		System.out.print(" |"+ConfigBasis.setStrleft("Breed",5));
		System.out.print(" |"+ConfigBasis.setStrleft("Life",5));
		System.out.print(" |"+ConfigBasis.setStrleft("Name",12));
		System.out.print(" |"+ConfigBasis.setStrleft("Money",6));
		System.out.println("");
		for (NpcData npc : npcList.values())
		{
			System.out.print(" |"+ConfigBasis.setStrright(npc.getId(),4));
			System.out.print(" |"+ConfigBasis.setStrleft(npc.getNpcType().name(),5));
			System.out.print(" |"+ConfigBasis.setStrformat2(npc.foodConsumCounter,6));
			System.out.print(" |"+ConfigBasis.setStrformat2(npc.hungerCounter,6));
			System.out.print(" |"+ConfigBasis.setStrformat2(npc.getHappiness(),6));
			System.out.print(" |"+npc.isSchwanger());
			System.out.print(" |"+npc.isAlive());
			System.out.print(" |"+ConfigBasis.setStrleft(npc.getName(),12));
			System.out.print(" |"+ConfigBasis.setStrformat2(npc.getMoney(),6));
			System.out.println("");
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
	
	@Test
	public void test()
	{
		data.initData(); 

		Settlement settle = data.getSettlements().getSettlement(19);
		settle.getResident().setNpcList(data.getNpcs().getSubListSettle(settle.getId()));
		System.out.println(" Npc gesamt : "+data.getNpcs().size());
//		showSettleInfo(settle);
		int ohneArbeit = settle.setWorkerToBuilding(settle.getResident().getSettlerCount());
		System.out.println(" Npc Worker : "+settle.getTownhall().getWorkerNeeded()+" / "+ohneArbeit);
		System.out.println(" Settle Npc : "+settle.getResident().getNpcList().size());
//		settle.getWarehouse().depositItemValue("BREAD", 20);

		for (int i = 0; i < 240; i++)
		{
			System.out.println("Day :"+i);
			settle.doHappiness(data);
//			settle.getWarehouse().depositItemValue("BREAD", 1+i);
			settle.doProduce(server, data);
		}
			
		showNpcList(settle.getResident().getNpcList());
		showSettleInfo(settle);
		System.out.println("Settle Death "+settle.getResident().getNpcList().getDeathNpc().size());
		System.out.println("Settle Beggar "+settle.getResident().getNpcList().getBeggarNpc().size());
		System.out.println("Settle Woman "+settle.getResident().getNpcList().getWoman().size());
		System.out.println("Settle Breed "+settle.getResident().getNpcList().getSchwanger().size());
		fail("Not yet implemented");
	}

	
}
