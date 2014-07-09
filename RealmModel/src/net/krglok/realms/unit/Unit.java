package net.krglok.realms.unit;

import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;


/**
 * <pre>
 * UnitType 
 * value has a range of (0..maxInt)
 * no value < 0 are allowed
 * </pre>
 * @author oduda
 *
 */
public class Unit
{
	private static int lfdID = 0; 
	private UnitType unitType;
	private int id;
	private int health;
	private LocationData position;
	private ItemList itemList;
	private Integer settleId;
	private int regimentId;
	private double happiness;
	private int power;
	

	public Unit(UnitType uType)
	{
		this.unitType = uType;
		id = -1;
		health = 1;
		settleId = -1;
		regimentId = -1;
		position = null;
		itemList = new ItemList();
		happiness = 1.0;
		power = 0;
	}

	public void initlfdID(int value)
	{
		lfdID = value;
	}
	
	public int getLfdID()
	{
		return lfdID;
	}
	
	public int addID()
	{
		lfdID++;
		return lfdID;
	}
	
	public UnitType getUnitType()
	{
		return unitType;
	}

	public void setUnitType(UnitType unitType)
	{
		this.unitType = unitType;
	}

	
	public int getId()
	{
		return id;
	}

	public void setId(int iD)
	{
		id = iD;
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

	public int getSettleId() {
		return settleId;
	}

	public void setSettleId(int settleId) {
		this.settleId = settleId;
	}

	public int getRegimentId() {
		return regimentId;
	}

	public void setRegimentId(int regimentId) {
		this.regimentId = regimentId;
	}

	/**
	 * for Healing
	 * limit health >= 0
	 * limit health <= 20
	 * @param value
	 */
	public void addHealth(int value)
	{
		this.health = this.health + value;
		if (this.health < 0)
		{
			this.health = 0;
		}
		if (this.health > 20)
		{
			this.health = 20;
		}
	}

	public double getHappiness() {
		return happiness;
	}

	public void setHappiness(double happiness) {
		this.happiness = happiness ;
	}

	public void addHappiness(double value) {
		this.happiness = this.happiness + value;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}
	
	public void addPower(int value) 
	{
		this.power = this.power + value;
	}

}
