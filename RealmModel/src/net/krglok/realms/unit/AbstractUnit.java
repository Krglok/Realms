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
public abstract class AbstractUnit implements IUnit
{
	
//	private int id;
	protected UnitType unitType;
	protected int health;
	protected LocationData position;
	protected ItemList storage;
	protected Item used;

	protected int speed;
	protected int offense;
	protected int defense;
	protected int offenseRange;
	protected int maxStorage;
	protected int armor;
	
	// required
	protected ItemList requiredItems;
	protected double requiredCost;
	protected long requiredTime;
	protected UnitTypeList requiredUnits;
	
	//consum
	protected ItemList consumItems;
	protected double consumCost;
	protected long consumTime;
	
	
	public AbstractUnit()
	{
//		ID++;
//		this.id = ID;
		this.unitType = UnitType.NONE;
		this.health = restoreHealth();
		this.position = new LocationData("", 0.0, 0.0, 0.0);
		this.storage = new ItemList();
		this.used = new Item(Material.AIR.name(),1); 
		this.armor = 0;
		this.speed = 0;
		this.offense = 0;
		this.defense = 0;
		this.offenseRange = 0;
		this.maxStorage = 0;
	
		// required
		this.requiredItems = new ItemList();
		this.requiredCost = 0.0;
		this.requiredTime = 10;
		this.requiredUnits = new UnitTypeList();
		
		//consum
		this.consumItems = new ItemList();
		this.consumCost  = 0.0;
		this.consumTime  = 10;

	}
	

	
	public int restoreHealth()
	{
		return 20;
	}
	

	@Override
	public abstract void train();

	@Override
	public abstract void upgrade();

	@Override
	public abstract void attack();

	@Override
	public abstract void defend();
	
	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getSpeed()
	 */
	@Override
	public int getSpeed()
	{
		return speed;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#setSpeed(int)
	 */
	@Override
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getOffense()
	 */
	@Override
	public int getOffense()
	{
		return offense;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#setOffense(int)
	 */
	@Override
	public void setOffense(int offense)
	{
		this.offense = offense;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getDefense()
	 */
	@Override
	public int getDefense()
	{
		return defense;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#setDefense(int)
	 */
	@Override
	public void setDefense(int defense)
	{
		this.defense = defense;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getOffenseRange()
	 */
	@Override
	public int getOffenseRange()
	{
		return offenseRange;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#setOffenseRange(int)
	 */
	@Override
	public void setOffenseRange(int offenseRange)
	{
		this.offenseRange = offenseRange;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getMaxStorage()
	 */
	@Override
	public int getMaxStorage()
	{
		return maxStorage;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#setMaxStorage(int)
	 */
	@Override
	public void setMaxStorage(int maxStorage)
	{
		this.maxStorage = maxStorage;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getArmor()
	 */
	@Override
	public int getArmor()
	{
		return armor;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#setArmor(int)
	 */
	@Override
	public void setArmor(int armor)
	{
		this.armor = armor;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getRequiredCost()
	 */
	@Override
	public double getRequiredCost()
	{
		return requiredCost;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#setRequiredCost(double)
	 */
	@Override
	public void setRequiredCost(double requiredCost)
	{
		this.requiredCost = requiredCost;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getConsumCost()
	 */
	@Override
	public double getConsumCost()
	{
		return consumCost;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#setConsumCost(double)
	 */
	@Override
	public void setConsumCost(double consumCost)
	{
		this.consumCost = consumCost;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getConsumTime()
	 */
	@Override
	public long getConsumTime()
	{
		return consumTime;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#setConsumTime(long)
	 */
	@Override
	public void setConsumTime(long consumTime)
	{
		this.consumTime = consumTime;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getRequiredItems()
	 */
	@Override
	public ItemList getRequiredItems()
	{
		return requiredItems;
	}

	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getRequiredUnits()
	 */
	@Override
	public UnitTypeList getRequiredUnits()
	{
		return requiredUnits;
	}

	public long getRequiredTime()
	{
		return requiredTime;
	}



	public void setRequiredTime(long requiredTime)
	{
		this.requiredTime = requiredTime;
	}



	/* (non-Javadoc)
	 * @see net.krglok.realms.unit.iUnitData#getConsumItems()
	 */
	@Override
	public ItemList getConsumItems()
	{
		return consumItems;
	}



	/**
	 * @return the unitType
	 */
	public UnitType getUnitType()
	{
		return unitType;
	}


}
