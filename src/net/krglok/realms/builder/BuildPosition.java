package net.krglok.realms.builder;

import net.krglok.realms.Common.LocationData;



public class BuildPosition
{
	private BuildPlanType bType;
	private LocationData  position;
	
	
	public BuildPosition(BuildPlanType bType, LocationData position)
	{
		super();
		this.bType = bType;
		this.position = position;
	}
	public BuildPlanType getbType()
	{
		return bType;
	}
	public void setbType(BuildPlanType bType)
	{
		this.bType = bType;
	}
	public LocationData getPosition()
	{
		return position;
	}
	public void setPosition(LocationData position)
	{
		this.position = position;
	}

	
	
}
