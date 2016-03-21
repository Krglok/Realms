package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.data.DataStoreNpc;
import net.krglok.realms.npc.GenderType;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.npc.NpcList;
import net.krglok.realms.npc.NpcNamen;

import org.junit.Test;

public class NpcAnalyseTest
{

	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 

	
	
	private void printNpcStat(int anzahl, int man, int woman, int child, int childMan, int childWoman)
	{
		System.out.print("|         "+ConfigBasis.setStrleft("Anzahl",5));
		System.out.print("| "+ConfigBasis.setStrright("Man",5));
		System.out.print("| "+ConfigBasis.setStrright("Woman",5));
		System.out.println("| ");
		System.out.print("|Gesamt : "+ConfigBasis.setStrright(anzahl,5));
		System.out.print("| "+ConfigBasis.setStrright(man,5));
		System.out.print("| "+ConfigBasis.setStrright(woman,5));
		System.out.println("| ");
		System.out.print("| Child : "+ConfigBasis.setStrright(child,5));
		System.out.print("| "+ConfigBasis.setStrright(childMan,5));
		System.out.print("| "+ConfigBasis.setStrright(childWoman,5));
		System.out.println("| ");
	}

	private void printNpc(NpcData npc)
	{
		System.out.print("  |"+ConfigBasis.setStrright(npc.getId(),4));
		System.out.print("| "+ConfigBasis.setStrleft(npc.getName(),10));
		System.out.print("| "+ConfigBasis.setStrleft(npc.getGender().name(),5));
		System.out.print("| "+npc.getAge());
		System.out.print("| "+ConfigBasis.setStrleft(npc.getNpcType().name(),9));
		System.out.println("| ");
	}
	
	@Test
	public void test()
	{
		NpcNamen npcNameList = new NpcNamen();
//		DataStoreNpcName npcNameData = new DataStoreNpcName(dataFolder);
		
		DataStoreNpc npcDataStore = new DataStoreNpc(dataFolder, null);

		NpcList npcs = new NpcList();
		NpcData npcData;
		ArrayList<String> refList = npcDataStore.readDataList();
		for (String ref : refList)
		{
			npcData = npcDataStore.readData(ref);
			npcs.putNpc(npcData);
		}
		int childCount = 0;
		int childManCount = 0;
		int childWomanCount = 0;
		int manCount = 0;
		int womanCount = 0;
		for (NpcData npc : npcs.values())
		{
			if (npc.isChild())
			{
				childCount++;
				if (npc.getGender() == GenderType.MAN)
				{
					childManCount++;
				} else
				{
					childWomanCount++;
				}
			}
			if (npc.getGender() == GenderType.MAN)
			{
				manCount++;
			} else
			{
				womanCount++;
			}
		}
		printNpcStat(npcs.size(), manCount, womanCount, childCount, childManCount, childWomanCount);
		
		fail("Not yet implemented");
	}

}
