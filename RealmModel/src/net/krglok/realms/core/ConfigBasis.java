package net.krglok.realms.core;

import net.krglok.realms.data.ConfigInterface;

public class ConfigBasis implements ConfigInterface
{
	protected static final String CONFIG_SETTLEMENT_COUNTER = "settlementCounter";
	protected static final String CONFIG_REALM_COUNTER = "realmCounter";
	protected static final String CONFIG_PLUGIN_VER = "plugin_ver";
	protected static final String CONFIG_PLUGIN_NAME = "plugin_name";
	protected static final String PLUGIN_NAME = "Realms";
	protected static String PLUGIN_VER = "0.1.0";
	public final static long dayNight = 24000 ; // serverTicks 
	public final static long RealmTick = 20L; 
	public final static long DelayTick = 20L; 
	public  static long GameDay = ConfigBasis.dayNight / ConfigBasis.RealmTick;
	
	@Override
	public Boolean initConfigData()
	{
		// TODO Auto-generated method stub
		return null;
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
	@Override
	public BuildingType regionToBuildingType(String regionTypeName)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
