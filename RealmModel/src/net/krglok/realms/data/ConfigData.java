package net.krglok.realms.data;

import java.util.HashMap;

import net.krglok.realms.core.ItemList;

/**
 * read Data from YML file 
 * used for initialize the Plugin and the RealmModel with data
 * 
 * @author Windu
 *
 */
public class ConfigData implements ConfigInterface
{
	private static final String PLUGIN_NAME = "Realms";
	private static final String PLUGIN_VER = "0.1.0";

	// RegionTypeBuildingType
	private HashMap<String,String> regionBuildingTypes;
	// SuperRegionTypeBuildingType
	private HashMap<String,String> superBuildingTypes;
	// SuperRegionTypeSettlementType
	private HashMap<String,String> superSettleTypes;
	
	
	public ConfigData()
	{
		
	}

	@Override
	public Boolean initConfigData()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getVersion()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPluginName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemList getToolItems()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemList getWeaponItems()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemList getArmorItems()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
