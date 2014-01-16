package net.krglok.realms.data;

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
	
	public BuildingType regionToBuildingType(String regionTypeName);

	
}
