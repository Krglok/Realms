package net.krglok.realms.unit;

import org.bukkit.Material;

import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;

/**
 * <pre>
 * Stellt eine individuelle Einheit dar.
 * hat eigene Position und health
 * </pre>
 * @author Windu
 *
 */
public class Unit implements iUnit
{
	private static int ID;
	
	private int id;
	private UnitType unitType;
	private int health;
	private LocationData position;
	private ItemList storage;
	private Item used;

	public Unit()
	{
		ID++;
		this.id = ID;
		this.unitType = UnitType.NONE;
		this.health = restoreHealth();
		this.position = new LocationData("", 0.0, 0.0, 0.0);
		this.storage = new ItemList();
		this.used = new Item(Material.AIR.name(),1); 
	}
	
	
	public Unit(UnitType unitType)
	{
		ID++;
		this.id = ID;
		this.unitType = unitType;
		this.health = restoreHealth();
		this.position = new LocationData("", 0.0, 0.0, 0.0);
		this.storage = new ItemList();
		this.used = new Item(Material.AIR.name(),1); 
	}

	
	public static void initID(int initID)
	{
		ID = initID;
	}
	
	
	/**
	 * the id is uniqque and will be set on creation
	 * @return the id, reference number of the unit
	 */
	public int getId()
	{
		return this.id;
	}

	public int restoreHealth()
	{
		return 20;
	}
	
	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD)
	{
		ID = iD;
	}

	@Override
	public void train()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void upgrade()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attack()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void defend()
	{
		// TODO Auto-generated method stub
		
	}
}
