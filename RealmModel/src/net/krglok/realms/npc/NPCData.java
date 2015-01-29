package net.krglok.realms.npc;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.unit.AbstractUnit;
import net.krglok.realms.unit.UnitAction;
import net.krglok.realms.unit.UnitFactory;
import net.krglok.realms.unit.UnitType;

public class NpcData
{
	private static int COUNTER;

	private int Id;
	private NPCType npcType ;
	private UnitType unitType ;
	private String name;
	private GenderType gender;
	private int age;	// in days !
	private EthnosType ethno;
	private boolean immortal;
	
	private int settleId;
	private int homeBuilding;
	private int workBuilding;
	
	private double money;
	protected Item itemInHand;
	protected BackpackList backPack;
	private boolean isMaried;
	private int npcHusband;
	private int pcHusband;
	private int mother;
	private int father;
	private boolean isAlive;
	private int schwanger;
	private int producer;
	protected double happiness;
	protected int health;
	private int regimentId;
	private int power;

	protected LocationData location;
	public double foodConsumCounter;
	public double hungerCounter;
	public boolean isSpawned;
	public int spawnId ;
	
	private NpcAction npcAction;
	private UnitAction unitAction;
	private AbstractUnit unit;
	
	public NpcData()
	{
		this.Id = 0;
		this.npcType = NPCType.BEGGAR;
		this.unitType = UnitType.SETTLER;
		this.name = "";
		this.gender = GenderType.WOMAN;
		this.age = 0;
		this.ethno = EthnosType.HUMAN;
		this.immortal = false;
		this.settleId = 0;
		this.homeBuilding = 0;
		this.workBuilding = 0;
		this.money = 0.0;
		this.itemInHand = new Item("AIR",0);
		this.backPack = new BackpackList();
		this.isMaried = false;
		this.npcHusband = 0;
		this.pcHusband = 0;
		this.mother = 0;
		this.father = 0;
		this.isAlive = true;
		this.setSchwanger(0);
		foodConsumCounter = 0.0;
		hungerCounter = 0.0;
		happiness = 0.0;
		isSpawned = false;
		spawnId = -1;
		npcAction = NpcAction.NONE;
		unitAction = UnitAction.NONE;
		location = null;
		this.setHealth(20);
		this.setRegimentId(0);
		this.setPower(1);
		this.unit = new AbstractUnit(this);
	}
	
	
	public NpcData(int npcId, NPCType npcType, UnitType unitType, String name, int settleId, int buildingId, GenderType gender, int age)
	{
		this.Id = npcId;
		this.npcType = npcType;
		this.unitType = unitType;
		this.settleId = settleId;
		this.gender = gender;
		this.homeBuilding = buildingId;
		this.settleId = settleId;
		this.age = age;
		this.name = name;
		
		this.ethno = EthnosType.HUMAN;
		this.immortal = false;
		this.workBuilding = 0;
		this.money = 0.0;
		this.itemInHand = new Item("AIR",0);
		this.backPack = new BackpackList();
		this.isMaried = false;
		this.npcHusband = 0;
		this.pcHusband = 0;
		this.mother = 0;
		this.father = 0;
		this.isAlive = true;
		this.setSchwanger(0);
		foodConsumCounter = 0.0;
		hungerCounter = 0.0;
		happiness = 0.0;
		isSpawned = false;
		spawnId = -1;
		npcAction = NpcAction.NONE;
		unitAction = UnitAction.NONE;
		location = null;
		this.setHealth(20);
		this.setRegimentId(0);
		this.setPower(1);
		UnitFactory unitFactory = new UnitFactory();
		this.unit = unitFactory.erzeugeUnit(this.unitType, this);
	}
	
	
	
	
	public static GenderType findGender()
	{
		int maxValue = 100;
		int index =  (int) Math.rint(Math.random() * maxValue);
		if (index+5 > 55)
		{
			return GenderType.WOMAN;
		}
		return GenderType.MAN;
		
	}
	
	public static NpcData makeChild(NpcNamen npcNameList, int fatherId, int motherId)
	{
		NpcData npc = new NpcData();
		npc.setGender(findGender());
		npc.setNpcType(NPCType.CHILD);
		String npcName = npcNameList.findName(npc.getGender());
		npc.setName(npcName);
		npc.setAge(1);
		npc.setFather(fatherId);
		npc.setMother(motherId);
		return npc;
	}

	/**
	 * @return the cOUNTER
	 */
	public static int getCOUNTER()
	{
		return COUNTER;
	}

	/**
	 * @param cOUNTER the cOUNTER to set
	 */
	public static void initCOUNTER(int cOUNTER)
	{
		COUNTER = cOUNTER;
	}
	
	public int getId()
	{
		return Id;
	}

	public void setId(int npcId)
	{
		this.Id = npcId;
	}

	public NPCType getNpcType()
	{
		return npcType;
	}

	public void setNpcType(NPCType npcType)
	{
		this.npcType = npcType;
	}

	public UnitType getUnitType()
	{
		return unitType;
	}

	public void setUnitType(UnitType unitType)
	{
		this.unitType = unitType;
	}

	public int getSettleId()
	{
		return settleId;
	}

	public void setSettleId(int settleId)
	{
		this.settleId = settleId;
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

	/**
	 * @return the gender
	 */
	public GenderType getGender()
	{
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(GenderType gender)
	{
		this.gender = gender;
	}

	/**
	 * @return the age in yeras !
	 */
	public int getAge()
	{
		return (age / 360);
	}
	
	public int getAgeDay()
	{
		return age;
	}
	

	/**
	 * @param age the age to set
	 */
	public void setAge(int years)
	{
		this.age = (years * 360);
	}

	public void setAgeDay(int days)
	{
		this.age = days;
	}

	/**
	 * increment ag by 1 day
	 */
	public void addAgeDay()
	{
		this.age++;
	}
	
	/**
	 * @return the ethno
	 */
	public EthnosType getEthno()
	{
		return ethno;
	}

	/**
	 * @param ethno the ethno to set
	 */
	public void setEthno(EthnosType ethno)
	{
		this.ethno = ethno;
	}

	/**
	 * @return the homeBuilding
	 */
	public int getHomeBuilding()
	{
		return homeBuilding;
	}

	/**
	 * @param homeBuilding the homeBuilding to set
	 */
	public void setHomeBuilding(int homeBuilding)
	{
		this.homeBuilding = homeBuilding;
	}

	/**
	 * @return the workBuilding
	 */
	public int getWorkBuilding()
	{
		return workBuilding;
	}

	/**
	 * @param workBuilding the workBuilding to set
	 */
	public void setWorkBuilding(int workBuilding)
	{
		this.workBuilding = workBuilding;
	}

	/**
	 * @return the money
	 */
	public double getMoney()
	{
		return money;
	}

	/**
	 * @param money the money to set
	 */
	public void setMoney(double money)
	{
		this.money = money;
	}

	public void depositMoney(double value)
	{
		this.money = this.money + value;
	}
	
	public boolean withdrawMoney(double value)
	{
		if ((this.money-value) > 0.0)
		{
			this.money = this.money - value;
			return true;
		} else
		{
			return false;
		}
	}
	
	/**
	 * @return the itemInHand
	 */
	public Item getItemInHand()
	{
		return itemInHand;
	}

	/**
	 * @param itemInHand the itemInHand to set
	 */
	public void setItemInHand(Item itemInHand)
	{
		this.itemInHand = itemInHand;
	}

	/**
	 * @return the backPack
	 */
	public ItemList getBackPack()
	{
		return backPack;
	}

	/**
	 * @param backPack the backPack to set
	 */
	public void setBackPack(BackpackList backPack)
	{
		this.backPack = backPack;
	}

	/**
	 * @return the isMaried
	 */
	public boolean isMaried()
	{
		return isMaried;
	}

	/**
	 * @param isMaried the isMaried to set
	 */
	public void setMaried(boolean isMaried)
	{
		this.isMaried = isMaried;
	}

	/**
	 * @return the npcHusband
	 */
	public int getNpcHusband()
	{
		return npcHusband;
	}

	/**
	 * @param npcHusband the npcHusband to set
	 */
	public void setNpcHusband(int npcHusband)
	{
		this.npcHusband = npcHusband;
	}

	/**
	 * @return the pcHusband
	 */
	public int getPcHusband()
	{
		return pcHusband;
	}

	/**
	 * @param pcHusband the pcHusband to set
	 */
	public void setPcHusband(int pcHusband)
	{
		this.pcHusband = pcHusband;
	}

	/**
	 * @return the mother
	 */
	public int getMother()
	{
		return mother;
	}

	/**
	 * @param mother the mother to set
	 */
	public void setMother(int mother)
	{
		this.mother = mother;
	}

	/**
	 * @return the father
	 */
	public int getFather()
	{
		return father;
	}

	/**
	 * @param father the father to set
	 */
	public void setFather(int father)
	{
		this.father = father;
	}

	/**
	 * @return the immortal
	 */
	public boolean isImmortal()
	{
		return immortal;
	}

	/**
	 * @param immortal the immortal to set
	 */
	public void setImmortal(boolean immortal)
	{
		this.immortal = immortal;
	}

	/**
	 * @return the isAlive
	 */
	public boolean isAlive()
	{
		return isAlive;
	}

	/**
	 * @param isAlive the isAlive to set
	 */
	public void setAlive(boolean isAlive)
	{
		this.isAlive = isAlive;
	}

	/**
	 * @return the schwanger
	 */
	public int getSchwanger()
	{
		return schwanger;
	}

	/**
	 * @param schwanger the schwanger to set
	 */
	public void setSchwanger(int schwanger)
	{
		this.schwanger = schwanger;
	}
	
	public void addSchwanger(int value)
	{
		this.schwanger = this.schwanger + value;
	}
	
	public boolean isSchwanger()
	{
		if (this.schwanger > 0)
		{
			return true;
		}
		return false;
	}

	public boolean isChild()
	{
		if (this.getNpcType() == NPCType.CHILD)
		{
			return true;
		}
		return false;
	}

	public boolean isBeggar()
	{
		if (this.getNpcType() == NPCType.BEGGAR)
		{
			return true;
		}
		return false;
	}

	public boolean isManager()
	{
		if ((this.getNpcType() == NPCType.BUILDER)
			|| (this.getNpcType() == NPCType.CRAFTSMAN)
			|| (this.getNpcType() == NPCType.FARMER)
			|| (this.getNpcType() == NPCType.MANAGER)
			|| (this.getNpcType() == NPCType.MAPMAKER)
			)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * @return the producer
	 */
	public int getProducer()
	{
		return producer;
	}

	/**
	 * @param producer the producer to set
	 */
	public void setProducer(int producer)
	{
		this.producer = producer;
	}

	/**
	 * @return the happiness
	 */
	public double getHappiness()
	{
		return happiness;
	}

	/**
	 * There is an LIMITER for the value for isAlive npc
	 * 
	 * @param happiness the value to set
	 */
	public void setHappiness(double value)
	{
			// 
		if (this.happiness <= ConfigBasis.MAX_HAPPINESS)
		{
			if (this.happiness >= ConfigBasis.MIN_HAPPINESS)
			{
				this.happiness = value;
			} else
			{
				this.happiness = ConfigBasis.MIN_HAPPINESS;			
			}
		} else
		{
			this.happiness = ConfigBasis.MAX_HAPPINESS;
		}
	}

	/**
	 * @return the npcAction
	 */
	public NpcAction getNpcAction()
	{
		return npcAction;
	}

	/**
	 * @param npcAction the npcAction to set
	 */
	public void setNpcAction(NpcAction npcAction)
	{
		this.npcAction = npcAction;
	}

	/**
	 * @return the unitAction
	 */
	public UnitAction getUnitAction()
	{
		return unitAction;
	}


	/**
	 * @param unitAction the unitAction to set
	 */
	public void setUnitAction(UnitAction unitAction)
	{
		this.unitAction = unitAction;
	}


	/**
	 * @return the location
	 */
	public LocationData getLocation()
	{
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(LocationData location)
	{
		this.location = location;
	}

	/**
	 * @return the health
	 */
	public int getHealth()
	{
		return health;
	}

	/**
	 * @param health the health to set
	 */
	public void setHealth(int health)
	{
		this.health = health;
	}

	/**
	 * @return the regimentId
	 */
	public int getRegimentId()
	{
		return regimentId;
	}

	/**
	 * @param regimentId the regimentId to set
	 */
	public void setRegimentId(int regimentId)
	{
		this.regimentId = regimentId;
	}

	/**
	 * @return the power
	 */
	public int getPower()
	{
		return power;
	}

	/**
	 * @param power the power to set
	 */
	public void setPower(int power)
	{
		this.power = power;
	}


	/**
	 * @return the unit
	 */
	public AbstractUnit getUnit()
	{
		return unit;
	}


	/**
	 * @param unit the unit to set
	 */
	public void setUnit(AbstractUnit unit)
	{
		this.unit = unit;
	}

}
