package net.krglok.realms;

import java.util.ArrayList;

public class CommandArg extends ArrayList<String>
{
	
	public CommandArg()
	{
		
	}
	
	public CommandArg(String[] args)
	{
		for (int i = 0; i < args.length; i++)
		{
			this.add(args[i]);
		}
	}

	public static int argToInt(String value)
	{
		try
		{
			return  Integer.valueOf(value);
		} catch (NumberFormatException e)
		{
			return 0;
		}
	}
	
	public static double argToDouble(String value)
	{
		try
		{
			return Double.valueOf(value);
		} catch (NumberFormatException e)
		{
			return 0.0;
		}
	}
	
	public static boolean argToBool(String value)
	{
		if (value.equalsIgnoreCase("true"))
		{
			return true;
		}
		return false;
	}
}
