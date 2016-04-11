package net.krglok.realms.builder;

import java.util.ArrayList;

import net.krglok.realms.Common.LocationData;


public class BuildPositionList extends ArrayList<BuildPosition>
{

	private static final long serialVersionUID = -7910636129211985755L;


	public boolean contains(LocationData  position)
	{
		
		return false;
	}
	
	public boolean contains (BuildPlanType bType)
	{
		
		return false;
	}
	
}
