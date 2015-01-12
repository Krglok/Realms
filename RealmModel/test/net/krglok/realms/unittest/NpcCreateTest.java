package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.util.List;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.data.DataStoreNpcName;
import net.krglok.realms.data.DataStoreSettlement;
import net.krglok.realms.npc.GenderType;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.npc.NpcNamen;

import org.junit.Test;

public class NpcCreateTest
{

	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 

	DataStorage data = new DataStorage(dataFolder);

	private void printSettleList( Settlement settle)
	{
		System.out.print("| "+settle.getId());
		System.out.print("| "+ConfigBasis.setStrleft(settle.getName(),15));
		System.out.print("| "+ConfigBasis.setStrleft(settle.getSettleType().name(),5));
		System.out.print("| "+settle.getResident().getSettlerCount());
		System.out.print("| "+settle.getBuildingList().size());
		System.out.print("|Bank: "+ConfigBasis.setStrformat2(settle.getBank().getKonto(),10));
		System.out.print("|age: "+settle.getAge());
		System.out.print("|years: "+(settle.getAge() / 30/12));
		System.out.println("| ");
	}
	
	private void printNameList( List<String> names)
	{
		for (String name : names)
		{
			System.out.println(name);
		}
	}
	
	private int makeOlder()
	{
		int maxValue = 9;
		int index =  (int) Math.rint(Math.random() * maxValue);
		return index;
	}

	private void makePair(NpcData man, NpcData woman)
	{
		man.setMaried(true);
		man.setNpcHusband(woman.getId());
		woman.setMaried(true);
		woman.setNpcHusband(man.getId());
	}
	
	private NpcData makeFather(NpcNamen npcNameList)
	{
		NpcData npc = new NpcData();
		String npcName = npcNameList.findName(GenderType.MAN);
		npc.setNpcType(NPCType.SETTLER);
		npc.setName(npcName);
		npc.setGender(GenderType.MAN);
		npc.setAge(20+makeOlder());
		printNpc(npc);
		return npc;
	}

	private NpcData makeMother(NpcNamen npcNameList)
	{
		NpcData npc = new NpcData();
		String npcName = npcNameList.findName(GenderType.WOMAN);
		npc.setNpcType(NPCType.SETTLER);
		npc.setName(npcName);
		npc.setGender(GenderType.WOMAN);
		npc.setAge(20+makeOlder());
		printNpc(npc);
		return npc;
	}
	
	private GenderType findGender()
	{
		int maxValue = 100;
		int index =  (int) Math.rint(Math.random() * maxValue);
		if (index+5 > 55)
		{
			return GenderType.WOMAN;
		}
		return GenderType.MAN;
		
	}
	
	private NpcData makeChild(NpcNamen npcNameList, int index, int fatherId, int motherId)
	{
		NpcData npc = new NpcData();
		npc.setGender(findGender());
		npc.setNpcType(NPCType.CHILD);
		String npcName = npcNameList.findName(npc.getGender());
		npc.setName(npcName);
		npc.setAge(1+makeOlder()+index);
		npc.setFather(fatherId);
		npc.setMother(motherId);
		printNpc(npc);
		return npc;
	}
	
	private void makeFamily(Building building, NpcNamen npcNameList, int maxChild)
	{
		int max = ConfigBasis.getDefaultSettler(building.getBuildingType());
		NpcData father = makeFather(npcNameList);
		father.setSettleId(building.getSettleId());
		father.setHomeBuilding(building.getId());
		data.getNpcs().add(father);
		NpcData mother = makeMother(npcNameList);
		mother.setSettleId(building.getSettleId());
		mother.setHomeBuilding(building.getId());
		makePair(father, mother);
		data.getNpcs().add(mother);
		data.writeNpc(father);
		data.writeNpc(mother);
		NpcData child1;
		NpcData child2;
		NpcData child3;
		
		switch(maxChild)
		{
		case 2 :
			child1 = makeChild(npcNameList,1,father.getId(),mother.getId());
			child1.setSettleId(building.getSettleId());
			child1.setHomeBuilding(building.getId());
			data.getNpcs().add(child1);
			data.writeNpc(child1);
			child2 = makeChild(npcNameList,2,father.getId(),mother.getId());
			child2.setSettleId(building.getSettleId());
			child2.setHomeBuilding(building.getId());
			data.getNpcs().add(child2);
			data.writeNpc(child2);
		break;
		case 3 :
			child1 = makeChild(npcNameList,1,father.getId(),mother.getId());
			child1.setSettleId(building.getSettleId());
			child1.setHomeBuilding(building.getId());
			data.getNpcs().add(child1);
			data.writeNpc(child1);
			child2 = makeChild(npcNameList,2,father.getId(),mother.getId());
			child2.setSettleId(building.getSettleId());
			child2.setHomeBuilding(building.getId());
			data.getNpcs().add(child2);
			data.writeNpc(child2);
			child3 = makeChild(npcNameList,3,father.getId(),mother.getId());
			child3.setSettleId(building.getSettleId());
			child3.setHomeBuilding(building.getId());
			data.getNpcs().add(child3);
			data.writeNpc(child3);
		break;
		default :
			child1 = makeChild(npcNameList,1,father.getId(),mother.getId());
			child1.setSettleId(building.getSettleId());
			child1.setHomeBuilding(building.getId());
			data.getNpcs().add(child1);
			data.writeNpc(child1);
		break;
		}
	}
	
	private int checkBuildingNpc(Building building, NpcNamen npcNameList)
	{
		int max = 0;
		int child = 0;
		max = ConfigBasis.getDefaultSettler(building.getBuildingType());
		if (max > 0)
		{
			child = (max - 2) / 2;
//			System.out.print(" | "+building.getId());
			System.out.print("| "+ConfigBasis.setStrleft(building.getBuildingType().name(),10));
			System.out.print("| "+max);
			System.out.print("| "+2);
			System.out.print("| "+child);
			System.out.println("| ");
			makeFamily(building, npcNameList, child); 
			return (2 + child);
		}
		return 0;
	}
	
	
	private void printNpc(NpcData npc)
	{
		System.out.print("      |"+npc.getId());
		System.out.print("| "+ConfigBasis.setStrleft(npc.getName(),10));
		System.out.print("| "+ConfigBasis.setStrleft(npc.getGender().name(),5));
		System.out.print("| "+npc.getAge());
		if (npc.getFather() > 0)
		{
			System.out.print("| "+ConfigBasis.setStrleft(data.getNpcs().get(npc.getFather()).getName(),15));
		}
		if (npc.getMother() > 0)
		{
			System.out.print("| "+ConfigBasis.setStrleft(data.getNpcs().get(npc.getMother()).getName(),15));
		}
		System.out.println("| ");
	}
	
	@Test
	public void testCreateWriteNPC()
	{
		NpcNamen npcNameList = new NpcNamen();
//		DataStoreNpcName npcNameData = new DataStoreNpcName(dataFolder);
		
		data.initData();
		
		npcNameList = data.getNpcName();
		int counter = 0;
		int bcount = 0;
		for (Settlement settle : data.getSettlements().values())
		{
			printSettleList(settle);
//			counter = counter + (settle.getResident().getSettlerCount()/4*3);
			bcount = bcount + settle.getBuildingList().size();
			for (Building building : settle.getBuildingList().values())
			{
				counter = counter + checkBuildingNpc(building, npcNameList);
			}
		}
		System.out.print("|   | Settler gesamt      ");
		System.out.println("["+counter+"]");
		System.out.print("|   | Building gesamt     ");
		System.out.println("["+bcount+"]");
		
		
		String npcName = npcNameList.findName(GenderType.MAN);
		NpcData npc = new NpcData();
		npc.setName(npcName);
		npc.setGender(GenderType.MAN);
		npc.setAge(20);
		printNpc(npc);
		
		
		fail("Not yet implemented");
	}

}
