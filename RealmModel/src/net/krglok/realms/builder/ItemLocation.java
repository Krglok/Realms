package net.krglok.realms.builder;

import net.krglok.realms.core.LocationData;

public class ItemLocation
{
	private String itemRef;
	private LocationData position;
	public ItemLocation(String itemRef, LocationData position)
	{
		super();
		this.setItemRef(itemRef);
		this.setPosition(position);
	}
	public String itemRef()
	{
		return itemRef;
	}
	public void setItemRef(String itemRef)
	{
		this.itemRef = itemRef;
	}
	public LocationData position()
	{
		return position;
	}
	public void setPosition(LocationData position)
	{
		this.position = position;
	}

}
