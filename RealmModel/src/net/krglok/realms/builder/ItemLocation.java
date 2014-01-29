package net.krglok.realms.builder;

import org.bukkit.Material;

import net.krglok.realms.core.LocationData;

public class ItemLocation
{
	private Material itemRef;
	private LocationData position;
	public ItemLocation(Material itemRef, LocationData position)
	{
		super();
		this.setItemRef(itemRef);
		this.setPosition(position);
	}
	public Material itemRef()
	{
		return itemRef;
	}
	public void setItemRef(Material itemRef)
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
