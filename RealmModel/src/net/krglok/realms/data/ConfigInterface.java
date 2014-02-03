package net.krglok.realms.data;

import java.util.HashMap;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.BuildingType;
import net.krglok.realms.core.ItemList;

public interface ConfigInterface
{

	public Boolean initConfigData();
	
	public String getVersion();
	
	public String getPluginName();

	public ItemList getToolItems();

	public ItemList getWeaponItems();

	public ItemList getArmorItems();
	
	public BuildingType superRegionToBuildingType(String superRegionTypeName);
	
	public String getRegionType(BuildingType bType);

	public String getRegionType(BuildPlanType bType);

	public BuildingType regionToBuildingType(String regionTypeName);


}
