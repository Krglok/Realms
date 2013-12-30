package net.krglok.realms.data;

import net.krglok.realms.core.Building;
import net.krglok.realms.core.BuildingType;

public class BuildingData
{

	
	public BuildingData()
	{
		
	}
	
	public Building smallHome()
	{
		Building building = new Building(BuildingType.BUILDING_HOME,"haus_einfach",true);
		return building;
	}
	
	public Building largeHome()
	{
		Building building = new Building(BuildingType.BUILDING_HOME,"haus_gross",true);
		building.setSettler(4);
		return building;
	}
	
	
	public int getRegionBuilding()
	{
		int reginId = 0;
		
		return reginId;
	}
	
	public void getregion(int index)
	{

//		location: DRASKORIA:321.69999998807907:85:-1809.6568177495155
//		type: haus_einfach
//		owners:
//		- sebbra
//		members: []
		
//		friendly-classes: []
//		enemy-classes: []
//		effects:
//		- denyblockbuild.1
//		- denyblockbreak.1
//		- denyplayerinteract.1
//		- periodicupkeep.1
//		- denyfriendlyfire.1
//		radius: 4
//		requirements:
//		- WOODEN_DOOR.1
//		- BED_BLOCK.2
//		- WORKBENCH.1
//		- CHEST.1
//		- WALL_SIGN.1
//		- COBBLESTONE.49
//		reagents:
//		- GOLD_NUGGET.1
//		- IRON_INGOT.8
//		- BREAD.4
//		upkeep: []
//		upkeep-chance: 0.001
//		output: []
//		money-requirement: 1000.0
//		upkeep-money-output: 1.0
		
	}


}