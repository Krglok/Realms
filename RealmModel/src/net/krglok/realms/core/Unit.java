package net.krglok.realms.core;


/**
 * 
 * @author oduda
 * All Units in the plugin are of these type
 */

public class Unit 
{
	// upkeep
	private UnitType unitType;
	private int speed;
	private int offense;
	private int defense;
	private float upkeepcost;
	private int food;
	private int count;
	
	// building
	private int worker;
	private int cow;
	private int horse;
	private double buildcost;
	private int training;
	private ItemList itemList;
	
	/**
	 *  constructor make initaliced instanz
	 *  parameters set to default values
	 *  UnitTYpe = UNIT_NONE
	 */
	public Unit()
	{
		// upkeep
		setUnitType(UnitType.UNIT_NONE);  // default Type = UNIT_NONE
		speed		= 0;
		offense		= 0;
		defense		= 0;
		upkeepcost	= 0;
		food		= 0;
		count		= 0;
		
		//  building
		worker 		= 0;
		cow			= 0;
		horse		= 0;
		buildcost	= 0;
		training	= 0;
		itemList = new ItemList();
	}

	public Unit(UnitType unitType)
	{
		// upkeep
		setUnitType(unitType);  // default Type = UNIT_NONE
		speed		= 0;
		offense		= 0;
		defense		= 0;
		upkeepcost	= 0;
		food		= 0;
		count		= 0;
		
		//  building
		worker 		= 0;
		cow			= 0;
		horse		= 0;
		buildcost	= 0;
		training	= 0;
		itemList = new ItemList();
	}
	
	public UnitType getUnitType() {
		return unitType;
	}

	public void setUnitType(UnitType unitType) {
		this.unitType = unitType;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getOffense() {
		return offense;
	}

	public void setOffense(int offense) {
		this.offense = offense;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public float getCost() {
		return upkeepcost;
	}

	public void setCost(float cost) {
		this.upkeepcost = cost;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public ItemList getItemList() {
		return itemList;
	}

	public void setItemList(ItemList itemList) {
		this.itemList = itemList;
	}

	public int getWorker() {
		return worker;
	}

	public void setWorker(int worker) {
		this.worker = worker;
	}

	public int getCow() {
		return cow;
	}

	public void setCow(int cow) {
		this.cow = cow;
	}

	public int getHorse() {
		return horse;
	}

	public void setHorse(int horse) {
		this.horse = horse;
	}

	public Double getBuildcost() {
		return buildcost;
	}

	public void setBuildcost(float buildcost) {
		this.buildcost = buildcost;
	}

	public int getTraining() {
		return training;
	}

	public void setTraining(int training) {
		this.training = training;
	}

	public int getUnitCount()
	{
		int value = this.count; 
		return value;
	}

	public void setUnitCount(int value)
	{
		this.count = value;
	}
	
	public void addUnitCount(int value)
	{
		this.count = this.count + value;
	}
	

}
