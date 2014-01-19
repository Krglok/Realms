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
	
	public static final String LINE = "=============================== ";
	
	
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

	public static String setStrleft(String in, int len)
	{
		char[] out = new char[len];
		for (int i = 0; i < out.length; i++)
		{
			out[i] = ' ';
		}
		if (len >= in.length())
		{
			char[] zw  = in.toCharArray();
			for (int i = 0; i < zw.length; i++)
			{
				out[i] = zw[i]; 
			}
		} else
		{
			char[] zw  = in.toCharArray();
			for (int i = 0; i < out.length; i++)
			{
				out[i] = zw[i]; 
			}
		}
		return String.valueOf(out);
	}

	public static String setStrright(String in, int len)
	{
		char[] out = new char[len];
		for (int i = 0; i < out.length; i++)
		{
			out[i] = ' ';
		}
		if (len >= in.length())
		{
			char[] zw  = in.toCharArray();
			int zwl = zw.length;
			for (int i = 0; i < zw.length; i++)
			{
				out[len-i-1] = zw[zwl-i-1]; 
			}
		} else
		{
			char[] zw  = in.toCharArray();
			int zwl = zw.length;
			for (int i = 0; i < out.length; i++)
			{
				out[len-i] = zw[zwl-i]; 
			}
		}
		return String.valueOf(out);
	}
	
}
