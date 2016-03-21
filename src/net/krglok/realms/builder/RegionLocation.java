package net.krglok.realms.builder;

import net.krglok.realms.core.LocationData;

/**
 * this class contain the data for a RegionRequest.
 * this contain a regiontype , the position of the region , the name of the region and the owner
 *
 * @author oduda
 *
 */
public class RegionLocation
{
	private String name;
	private String regionType;
	private LocationData position;
	private String ownerName;
	

	public RegionLocation(String regionType, LocationData position, String ownerName, String name)
	{
		super();
		this.setName(name);
		this.regionType = regionType;
		this.position = position;
		this.ownerName = ownerName;
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
		return ownerName;
	}


	public void setOwner(String ownerName)
	{
		this.ownerName = ownerName;
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
