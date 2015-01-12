package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.data.DataStoreNpcName;
import net.krglok.realms.npc.NpcNamen;

import org.junit.Test;

public class NpcNamenTest
{

//	@Test
//	public void testNameWrite()
//	{
//		String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 
//		String fileName    = "npcname";
//		
//		NpcNamen womanName = new NpcNamen();
//		DataStoreNpcName womanData = new DataStoreNpcName(dataFolder, fileName);
//		womanData.readDataList();	// open file
//		
//		womanName.getWomanNames().add("Amabella");
//		womanName.getWomanNames().add("Didda");
//		womanName.getManNames().add("Amabella");
//		womanName.getManNames().add("Didda");
//		womanData.writeData(womanName);
//		System.out.println("Woman Namen "+" ["+womanName.getWomanNames().size()+"]");
//		for (String name : womanName.getWomanNames())
//		{
//			System.out.println(name);
//		}
//		
//		fail("Not yet implemented");
//	}

	@Test
	public void testNameRead()
	{
		String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 
		String fileName    = "name_woman";
		
		NpcNamen womanName = new NpcNamen();
		DataStoreNpcName womanData = new DataStoreNpcName(dataFolder, fileName);

		NpcNamen npcName = new NpcNamen();
		DataStoreNpcName npcNameData = new DataStoreNpcName(dataFolder);
		
		int factor = 30;
		womanName = womanData.readData();
		System.out.println("Woman Namen "+" ["+womanName.getWomanNames().size()/factor+"]");
		int index = 0;
		for (String name : womanName.getWomanNames())
		{
			index++;
			if (index == factor)
			{
				npcName.getWomanNames().add(name);
				System.out.println(name);
				index=0;
			}
		}
		
		womanData = new DataStoreNpcName(dataFolder, "name_man");
		womanName = womanData.readData();
		
		System.out.println("Nan Namen "+" ["+womanName.getManNames().size()/factor+"]");
		for (String name : womanName.getManNames())
		{
			index++;
			if (index == factor)
			{
				npcName.getManNames().add(name);
				System.out.println(name);
				index=0;
			}
		}
		npcNameData.writeData(npcName);
		
		fail("Not yet implemented");
	}

}
