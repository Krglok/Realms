package net.krglok.realms.data;

import net.krglok.realms.core.ItemList;

public interface ConfigInterface
{

	public Boolean initConfigData();
	
	public String getVersion();
	
	public String getPluginName();

	public ItemList getToolItems();

	public ItemList getWeaponItems();

	public ItemList getArmorItems();
	
}
