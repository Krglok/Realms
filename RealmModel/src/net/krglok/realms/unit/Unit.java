package net.krglok.realms.unit;

import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;


/**
 * <pre>
 * UnitType Item
 * value has a range of (0..maxInt)
 * no value < 0 are allowed
 * </pre>
 * @author oduda
 *
 */
public class Unit
{
	private UnitType unitType;
	private int ID;
	private int health;
	private LocationData position;
	private ItemList itemList;

	public Unit(UnitType uType, int value)
	{
		this.unitType = uType;
	}

	public UnitType getUnitType()
	{
		return unitType;
	}

	public void setUnitType(UnitType unitType)
	{
		this.unitType = unitType;
	}

	
	public int getID()
	{
		return ID;
	}

	public void setID(int iD)
	{
		ID = iD;
	}

	public int getHealth()
	{
		return health;
	}

	public void setHealth(int health)
	{
		this.health = health;
	}

	public LocationData getPosition()
	{
		return position;
	}

	public void setPosition(LocationData position)
	{
		this.position = position;
	}

	public ItemList getItemList()
	{
		return itemList;
	}

	public void setItemList(ItemList itemList)
	{
		this.itemList = itemList;
	}

	public void addHealth(int value)
	{
		this.health = this.health + value;
		if (this.health < 0)
		{
			this.health = 0;
		}
	}
	
}
