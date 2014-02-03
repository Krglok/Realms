package net.krglok.realms.builder;

import net.krglok.realms.core.LocationData;

public class RegionLocation
{
	private String regionType;
	private LocationData position;
	private String owner;
	

	public RegionLocation(String regionType, LocationData position, String owner)
	{
		super();
		this.regionType = regionType;
		this.position = position;
		this.owner = owner;
	}


	public String getRegionType()
	{
		return regionType;
	}


	public void setRegionType(String regionType)
	{
		this.regionType = regionType;
	}


	public LocationData getPosition()
	{
		return position;
	}


	public void setPosition(LocationData position)
	{
		this.position = position;
	}


	public String getOwner()
	{
		return owner;
	}


	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	
	
	
}
