package net.krglok.realms.unit;

import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.builder.BuildStatus;
import net.krglok.realms.builder.RegionLocation;
import net.krglok.realms.core.Bank;
import net.krglok.realms.core.Barrack;
import net.krglok.realms.core.BoardItemList;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.Warehouse;
import net.krglok.realms.manager.BuildManager;
import net.krglok.realms.model.RealmModel;

import org.bukkit.Material;
import org.bukkit.block.Biome;

/**
 * <pre>
 * the regiment is a military formation to hold military units and command them
 * the regiment is something organize as a settlement with
 * - warehouse
 * - bank
 * - unitlist (Militia, Archer, LightInfantry, HeavyInfantry, Knight, Commander)
 * but the most important aspect is the power of a regiment 
 * and the actions like
 * - move
 * - battle
 * - raid
 * the regiments are the basic for battles
 * - regiment vs regiment
 * - regiment vs settlement
 * the regiment is a superregion in HeroStronghold, because this region type can easy destroy and create
 * 
 * @author Windu
 * </pre>
 */
public class Regiment {
	private static final int REGIMENT_ITEM_MAX = 10 * ConfigBasis.CHEST_STORE;

	private static int lfdID = 0;
	
	private int id;
	private RegimentType regimentType = RegimentType.PRIVATEER;
	private RegimentStatus regStatus = RegimentStatus.NONE;
	private LocationData position;
	private LocationData target;
	private String name;
	private int ownerId;
	private Owner owner;
	private Barrack barrack ;
	private Warehouse warehouse ;
	private Bank bank;
	private ItemList requiredProduction;
	
	private Boolean isEnabled;
	private Boolean isActive;
	private boolean isUncamp;
	
	private double hungerCounter = 0.0;
	private double foodConsumCounter = 0.0;
	
	private BoardItemList battleOverview;

	private double FoodFactor = 0.0;

	private String world;
	private Biome biome;
	private long age;
	private RegionLocation newSuperRegion;
	private RegionLocation superRequest;

	private BuildPlanMap buildPlan ;
	private BuildManager buildManager = new BuildManager();

	//	private Colony colonist;
	private BattleSetup battle;
	private AbstractUnit commander = null;
	private BattlePlacement attackPlan;
	private int firstWave = 5;
	private int waveCount = 2;
	private int maxWave = 2;
	private int fightCount = 0;
	private Settlement raidTarget = null;

	private int tickCount;
	private int maxTicks;
	
	private int settleId = -1;
	

	public Regiment() //LogList logList) 
	{
		super();
		lfdID++;
		this.id			= lfdID;
		this.age         = 0;
		this.position 	= new LocationData("", 0.0, 0.0, 0.0);
		this.target 	= new LocationData("", 0.0, 0.0, 0.0);
		this.world 		= "";
		this.biome		= null;
		this.name		= "Regiment";
		this.ownerId	= 0;
		this.owner 		= null;
		this.bank		= new Bank(); //logList);
		this.barrack	= new Barrack(60);
		this.warehouse		= new Warehouse(REGIMENT_ITEM_MAX);
		this.isEnabled  = true;
		this.isActive   = true;
		this.isUncamp 	= true;
		this.battleOverview = new BoardItemList();
//		this.colonist =  Colony.newCamp(this.name, this.owner, logList);
		this.attackPlan = new BattlePlacement();
		this.battle 	= new BattleSetup();
		this.tickCount  = 0;
		this.maxTicks	= 1;

	}

	public static Regiment makeRaider() //LogList logList)
	{
		Regiment regiment = new Regiment(); //logList);
		UnitList militiaList = makeMilitia(10);
		regiment.getBarrack().addUnitList(militiaList);
		UnitList settlerList = makeSettler(10);
		regiment.getBarrack().addUnitList(settlerList);
		regiment.name		= "Privateer";
		regiment.ownerId		= 0;
//		regiment.commander 	= new AbstractUnit(UnitType.COMMANDER);
//		regiment.getColonist().setPosition(regiment.getPosition());
		return regiment;
	}
	
	private static UnitList makeMilitia(int amount)
	{
		UnitFactory unitFactory = new UnitFactory();
		UnitList unitList = new UnitList();
		for (int i=0; i < amount; i++)
		{
			unitList.add(unitFactory.erzeugeUnit(UnitType.MILITIA));
		}
		return unitList;
	}

	private static UnitList makeArcher(int amount)
	{
		UnitFactory unitFactory = new UnitFactory();
		UnitList unitList = new UnitList();
		for (int i=0; i < amount; i++)
		{
			unitList.add(unitFactory.erzeugeUnit(UnitType.ARCHER));
		}
		return unitList;
	}

	private static UnitList makeSettler(int amount)
	{
		UnitFactory unitFactory = new UnitFactory();
		UnitList unitList = new UnitList();
		for (int i=0; i < amount; i++)
		{
			unitList.add(unitFactory.erzeugeUnit(UnitType.SETTLER));
		}
		return unitList;
	}

	public static int getLfdID() {
		return lfdID;
	}



	public static void initLfdID(int lfdID) {
		Regiment.lfdID = lfdID;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}


	public BuildManager buildManager()
	{
		return buildManager;
	}

	public RegimentType getRegimentType() {
		return regimentType;
	}



	public void setRegimentType(RegimentType regimentType) {
		this.regimentType = regimentType;
	}



	public RegimentStatus getRegStatus() {
		return regStatus;
	}



	public void setRegStatus(RegimentStatus regStatus) {
		this.regStatus = regStatus;
	}



	public LocationData getPosition() {
		return position;
	}



	public void setPosition(LocationData position) {
		this.position = position;
	}



	public LocationData getTarget()
	{
		return target;
	}

	public void setTarget(LocationData target)
	{
		this.target = target;
	}

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
//		this.colonist.setName(name);
	}



	public int getOwnerId() {
		return ownerId;
	}



	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
//		this.colonist.setOwner(owner);
	}



	public Barrack getBarrack() {
		return barrack;
	}



	public void setBarrack(Barrack barrack) {
		this.barrack = barrack;
	}



	public Warehouse getWarehouse() {
		return warehouse;
	}



	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}



	public Bank getBank() {
		return bank;
	}



	public void setBank(Bank bank) {
		this.bank = bank;
	}



	public ItemList getRequiredProduction() {
		return requiredProduction;
	}



	public void setRequiredProduction(ItemList requiredProduction) {
		this.requiredProduction = requiredProduction;
	}



	public Boolean getIsEnabled() {
		return isEnabled;
	}



	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}



	public Boolean getIsActive() {
		return isActive;
	}



	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}



	public double getHungerCounter() {
		return hungerCounter;
	}



	public void setHungerCounter(double hungerCounter) {
		this.hungerCounter = hungerCounter;
	}



	public double getFoodConsumCounter() {
		return foodConsumCounter;
	}



	public void setFoodConsumCounter(double foodConsumCounter) {
		this.foodConsumCounter = foodConsumCounter;
	}



	public BoardItemList getBattleOverview() {
		return battleOverview;
	}



	public void setBattleOverview(BoardItemList battleOverview) {
		this.battleOverview = battleOverview;
	}



	public double getFoodFactor() {
		return FoodFactor;
	}



	public void setFoodFactor(double foodFactor) {
		FoodFactor = foodFactor;
	}



	public String getWorld() {
		return world;
	}



	public void setWorld(String world) {
		this.world = world;
	}



	public Biome getBiome() {
		return biome;
	}



	public void setBiome(Biome biome) {
		this.biome = biome;
	}



	public long getAge() {
		return age;
	}



	public void setAge(long age) {
		this.age = age;
	}



//	public Colony getColonist() {
//		return colonist;
//	}



	public BattleSetup getBattle() {
		return battle;
	}



	public void setBattle(BattleSetup battle) {
		this.battle = battle;
	}



	public static int getRegimentItemMax() {
		return REGIMENT_ITEM_MAX;
	}

	/**
	 * <pre>
	 * setup Attacker for Raider, typical tactical setup
	 * - first line Militia
	 * - second line Archer
	 * Default Raider only has Militia and Archers
	 * </pre>
	 */
	public void setDefaultAttackPlan()
	{
//		BattlePlacement units = new BattlePlacement();
		
		//first line with militia
		UnitList centerList = new UnitList();
		for (AbstractUnit unit : barrack.getUnitList())
		{
			if (unit.getUnitType() == UnitType.MILITIA)
			{
				centerList.add(unit);
			}
		}
		attackPlan.setPlaceUnit(BattleFieldPosition.CENTER, centerList);
		
		// second line with archers
		UnitList backList = new UnitList();
		for (AbstractUnit unit : barrack.getUnitList())
		{
			if (unit.getUnitType() == UnitType.ARCHER)
			{
				backList.add(unit);
			}
		}
		attackPlan.setPlaceUnit(BattleFieldPosition.CENTERBACK, backList);
		
	}
	
	public BuildPlanMap getBuildPlan()
	{
		return buildPlan;
	}

	public void setBuildPlan(BuildPlanMap buildPlan)
	{
		this.buildPlan = buildPlan;
	}

	public void doProduce(RealmModel rModel)
	{
		
	}
	
	public void newCamp(String name,  String ownerName) //, LogList logList)
	{
		LocationData position = new LocationData("", 0.0, 0.0,0.0);
		newSuperRegion = new RegionLocation("CAMP", position, ownerName, name);
		warehouse.depositItemValue(Material.BED.name(), 1);
		warehouse.depositItemValue(Material.WOOL.name(), 120);
		warehouse.depositItemValue(Material.LOG.name(), 250);
		warehouse.depositItemValue(Material.WHEAT.name(), 100);
		warehouse.depositItemValue(Material.TORCH.name(), 10);
		warehouse.depositItemValue(Material.STONE.name(), 100);
		warehouse.depositItemValue(Material.WORKBENCH.name(), 1);
		warehouse.depositItemValue(Material.DIRT.name(), 100);
		warehouse.depositItemValue(Material.WATER.name(), 10);
		warehouse.depositItemValue(Material.COBBLESTONE.name(),100);
		warehouse.depositItemValue(Material.WOOD_DOOR.name(), 1);
		warehouse.depositItemValue(Material.BEDROCK.name(), 1);
		warehouse.depositItemValue(Material.CHEST.name(), 4);
		warehouse.depositItemValue(Material.WOOD.name(), 100);
		warehouse.depositItemValue(Material.RED_MUSHROOM.name(), 50);
		warehouse.depositItemValue(Material.BROWN_MUSHROOM.name(), 50);
	}

	/**
	 * 
	 */
	public void newRaid(Settlement settle)
	{
		if (regStatus == RegimentStatus.IDLE)
		{
			//configure Battle
			raidTarget = settle;
			battle.setDefender(raidTarget.getDefenders());
			setDefaultAttackPlan();
			battle.setAttacker(attackPlan);
			fightCount = 0;
			waveCount = 0;
			battle.setBattleEnd(true);
			regStatus = RegimentStatus.RAID;
			// start Battle
			battle.startBattle();
			battle.setNextAttack(true);
			
		} else
		{
			System.out.println("Regiment is BUSY ");
		}
	}
	
	private void endRaid()
	{
		barrack.getUnitList().clear();
		for (BattleFieldPosition bPos : BattleFieldPosition.values())
		{
			if (battle.getAttacker().getPlaceUnit(bPos)!= null)
			{
				if (battle.getAttacker().getPlaceUnit(bPos).size() > 0)
				{
					AbstractUnit unit = battle.getAttacker().getPlaceUnit(bPos).get(0);
					for(int i= 0; i < battle.getAttacker().getPlaceUnit(bPos).size(); i++)
					{
						barrack.getUnitList().add(unit);
					}
				}
			}
		}
		raidTarget.getBarrack().getUnitList().clear();
		for (BattleFieldPosition bPos : BattleFieldPosition.values())
		{
			if (battle.getDefender().getPlaceUnit(bPos)!= null)
			{
				if (battle.getDefender().getPlaceUnit(bPos).size() > 0)
				{
					AbstractUnit unit = battle.getDefender().getPlaceUnit(bPos).get(0);
					for(int i= 0; i < battle.getDefender().getPlaceUnit(bPos).size(); i++)
					{
						raidTarget.getBarrack().getUnitList().add(unit);
					}
				}
			}
		}
		
	}
	
	public void run(RealmModel rModel)
	{
//		System.out.println("Regiment: "+regStatus.name());
		switch (regStatus)
		{
		case NONE :
			
			break;
		case CAMP :
			doCamp(rModel);
			break;
		case UNCAMP :
			doUncamp(rModel);
			break;
		case MOVE :
			doMove(rModel);
			break;
		case BATTLE :
			doBattle(rModel);
			break;
		case RAID :
			doRaid(rModel);
			break;
		case HIDE :
			doHide(rModel);
			break;
		case IDLE :
			doWait(rModel);
			break;
		default :
			break;
		}
	}
	
	/**
	 * build up the camp with the colonist instance.
	 * when ready go to wait status
	 *  
	 * @param rModel
	 */
	private void doCamp(RealmModel rModel)
	{
		if ((buildManager.getStatus() == BuildStatus.DONE))
		{
			// set superRegionRequest, it will automaticly used , after request fulfill it is NULL
			superRequest = newSuperRegion;
			System.out.println("Regiment start Wait");
			regStatus = RegimentStatus.IDLE;
			rModel.getData().writeRegiment(this);
		} else
		{
//    		System.out.println("Regiment Camp "+buildManager.getStatus()+"/"+buildManager.getActualBuild().getBuildingType()+":"+buildManager.getH());
			buildManager.run(rModel, warehouse,null);
		}
	}
	
	/**
	 * destroy old camp
	 * destroy old SuperRegion 
	 * start to move to new position
	 * 
	 * @param rModel
	 */
	private void doUncamp(RealmModel rModel)
	{
		// Ready is the last status for clean old camp,
		if (buildManager.getStatus() == BuildStatus.READY)
		{
			buildManager.runClean(rModel, warehouse, null);
			System.out.println("Regiment start Move :"+buildManager.getStatus()+this.maxTicks);
			rModel.getServer().destroySuperRegion(name);
			isUncamp =true;
			regStatus = RegimentStatus.MOVE;
		} else
		{
//    		System.out.println("Regiment UnCamp "+buildManager.getStatus());
			buildManager.runClean(rModel, warehouse, null);
		}
	}
	
	/**
	 * make move steps 
	 * then read BuildPlan and go to CAMP Status 
	 * @param rModel
	 */
	private void doMove(RealmModel rModel)
	{
		moveTick();
		if (position.distance(target) > 120)
		{	
    		System.out.println("Regiment Distance "+buildManager.getStatus());
    		
		} else
		{
			System.out.println("Regiment start Camp :"+position.getWorld()+":"+position.getX()+":"+position.getY()+":"+position.getZ());
			buildPlan = rModel.getData().readTMXBuildPlan(BuildPlanType.FORT, 4, 0);
			String ownerName;
			if (owner == null)
			{
				ownerName = ConfigBasis.NPC_0;
			} else
			{
				ownerName = owner.getPlayerName();
			}
			buildManager.newBuild(buildPlan, this.position, ownerName);
			regStatus = RegimentStatus.CAMP;
		}
	}
	
	/**
	 * count the time delay for move
	 * and set the target to position
	 * 
	 */
	private void moveTick()
	{
		if (regStatus == RegimentStatus.MOVE)
		{
			this.tickCount++;
			if (this.tickCount >= this.maxTicks)
			{
				tickCount 	= 0;
				maxTicks 	= 0;
				position 	= target;
//				colonist.setPosition(position);
//				regStatus = RegimentStatus.CAMP;
				System.out.println("New Position : "+":"+position.getX()+":"+position.getY()+":"+position.getZ());
			}
		}
	}

	/**
	 * initialize move sequence
	 * - UNCAMP , clean old camp building, destroy superRegion
	 * - MOVE , make time for distance and set target to position 
	 * - CAMP , make new camp building
	 */
	public void startMove()
	{
		
		double distance = position.distance(target);
		if (position.getWorld().equals(""))
		{
			position.setWorld(target.getWorld());
		}
//		long travelTime = 20; //Trader.getTransportDelay(distance);
		if (position.getWorld().equalsIgnoreCase(target.getWorld()) == true)
		{
			System.out.println("Regiment Start Uncamp!");
			String ownerName;
			if (owner == null)
			{
				ownerName = ConfigBasis.NPC_0;
			} else
			{
				ownerName = owner.getPlayerName();
			}
			newSuperRegion = new RegionLocation("CAMP", target, ownerName, name);
			buildManager.newBuild(buildPlan, position, ownerName);
			regStatus = RegimentStatus.UNCAMP;
//			colonist.startReinforce(colonist.getSettleSchema().getRadius());
			
		} else
		{
			System.out.println("Regiment cant move to other Kontinent!");
		}
		
	}
	
	public RegionLocation getSuperRequest()
	{
		return superRequest;
	}

	public void setSuperRequest(RegionLocation superRequest)
	{
		this.superRequest = superRequest;
	}


	private void doHide(RealmModel rModel)
	{
		
	}

	private void doWait(RealmModel rModel)
	{
		
	}

	/**
	 * 
	 * @param rModel
	 */
	private void doBattle(RealmModel rModel)
	{
		
	}

	private void doRaid(RealmModel rModel)
	{
		while ((fightCount < firstWave) && (battle.winBattle() == false))
		{
				if (battle.isNextAttack())
				{
					battle.run();
					showBattleStatus(battle);
				} else
				{
					battle.setBattleEnd(true);
				}
				fightCount++;
		}
		if ((fightCount < firstWave) == false)
		{
			if (waveCount < maxWave)
			{
				waveCount++;
				fightCount = 0;
			}
		}
		
		if (battle.winBattle() == true)
		{
			System.out.print("Battle WIN defeat Defender");
			battle.setBattleEnd(true);
			transferWarehouse(raidTarget.getWarehouse(), 25);
			endRaid();
			rModel.getData().writeRegiment(this);
			regStatus = RegimentStatus.IDLE; 

		}  else
		{
			if (battle.isBattleEnd() == true)
			{
				System.out.print("END Battle LOST defeat Attacker");
				endRaid();
				rModel.getData().writeRegiment(this);
				regStatus = RegimentStatus.IDLE; 
			} else
			{
				if (waveCount < maxWave)
				{
					System.out.print("Battle SecondWave");
				} else
				{
					battle.setBattleEnd(true);
					endRaid();
					rModel.getData().writeRegiment(this);
					System.out.print("Max Wave Battle LOST defeat Attacker");
					regStatus = RegimentStatus.IDLE; 
				}
			}				
		}
	}

	private void showBattleStatus(BattleSetup battle)
	{
		System.out.print("|"+ConfigBasis.setStrleft("Attacker", 10));
		for (BattleFieldPosition bPos : BattleFieldPosition.values())
		{
			String s = ""; 
			if (battle.getAttacker().getUnitPlacement().get(bPos) != null)
			{
				if (battle.getAttacker().getUnitPlacement().get(bPos).size() > 0)
				{
					s = "|"+ConfigBasis.setStrleft(battle.getAttacker().getUnitPlacement().get(bPos).get(0).getUnitType().name(),8);
					s = ConfigBasis.setStrright(battle.getAttacker().getUnitPlacement().get(bPos).size(), 2);
				} else
				{
					s = "|"+ConfigBasis.setStrleft(" ", 10);
				}
			} else
			{
				s = "|"+ConfigBasis.setStrleft(" ", 10);
			}
			System.out.print(s);
		}
		System.out.print("|"+ConfigBasis.setStrleft("Defender", 10));
		for (BattleFieldPosition bPos : BattleFieldPosition.values())
		{
			String s = "";
			if (battle.getDefender().getUnitPlacement().get(bPos) != null)
			{
				if (battle.getDefender().getUnitPlacement().get(bPos).size() > 0)
				{
					s = "|"+ConfigBasis.setStrleft(battle.getDefender().getUnitPlacement().get(bPos).get(0).getUnitType().name(),8);
					s = ConfigBasis.setStrright(battle.getDefender().getUnitPlacement().get(bPos).size(),2);
				} else
				{
					s = "|"+ConfigBasis.setStrleft(" ", 10);
				}
			} else
			{
				s = "|"+ConfigBasis.setStrleft(" ", 10);
			}
 
			System.out.print(s);
		}
		System.out.println("------------------------------------------------------------------------------");
	}
	
	private void transferWarehouse(Warehouse warehouse, int factor)
	{
		int give = 0;
		if (warehouse != null)
		{
			for (Item item : warehouse.getItemList().values())
			{
				if (item.value() > 0)
				{
					give = item.value() * factor / 100;
					if (give < 1 )
					{
						give = 1;
					}
					System.out.println("Item : "+item.ItemRef()+":"+give);
					this.warehouse.depositItemValue(item.ItemRef(), give);
					warehouse.withdrawItemValue(item.ItemRef(), give);
				}
			}
			System.out.println("Regiment transfer warehouse ");
		}
	}

	/**
	 * @return the settleId
	 */
	public int getSettleId()
	{
		return settleId;
	}

	/**
	 * @param settleId the settleId to set
	 */
	public void setSettleId(int settleId)
	{
		this.settleId = settleId;
	}

	/**
	 * @return the owner
	 */
	public Owner getOwner()
	{
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(Owner owner)
	{
		this.owner = owner;
	}

	/**
	 * @return the commander
	 */
	public AbstractUnit getCommander()
	{
		return commander;
	}

	/**
	 * @param commander the commander to set
	 */
	public void setCommander(AbstractUnit commander)
	{
		this.commander = commander;
	}

}

