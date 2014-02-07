package net.krglok.realms.builder;

import net.krglok.realms.core.LocationData;

public class RegionLocation
{
	private String name;
	private String regionType;
	private LocationData position;
	private String owner;
	

	public RegionLocation(String regionType, LocationData position, String owner, String name)
	{
		super();
		this.setName(name);
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


	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	
	
}
