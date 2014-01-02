package net.krglok.realms.settlemanager;

import java.util.ArrayList;


public class NpcItemList extends ArrayList<String>
{

	public NpcItemList()
	{
		
	}
	
	public NpcItemList(NpcItemType npcItemType)
	{
		switch(npcItemType)
		{
		case SUPPLY :  initNpcSupplyItems();
			break;
		default:
			break;
		}
	}
	
	private void initNpcSupplyItems()
	{
		add("WHEAT");
		add("BREAD");
//		add("");
//		add("");

	}

	private void initNpcToolItems()
	{
		add("LOG");
		add("WOOD");
		add("STICK");
		add("WOOD_HOE");
		add("WOOD_AXE");
		add("WOOD_PICKAXE");
		add("WOOD_SWORD");
//		add("");
//		add("");

	}

	private void initNpcDefendItems()
	{
		add("LOG");
		add("WOOD");
		add("STICK");
		add("COBBLESTONE");
		add("STONE_SWORD");
		add("LEATHER");
//		add("COAL");
//		add("IRON_ORE");
//		add("IRON_INGOT");
//		add("IRON_SWORD");
////		add("");

	}

}
