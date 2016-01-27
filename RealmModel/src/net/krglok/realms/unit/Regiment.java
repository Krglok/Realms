package net.krglok.realms.unit;

import java.util.HashMap;

import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.builder.BuildStatus;
import net.krglok.realms.builder.RegionLocation;
import net.krglok.realms.core.AbstractSettle;
import net.krglok.realms.core.Bank;
import net.krglok.realms.core.Barrack;
import net.krglok.realms.core.BoardItemList;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.Resident;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.Warehouse;
import net.krglok.realms.data.DataInterface;
import net.krglok.realms.data.ServerInterface;
import net.krglok.realms.manager.BuildManager;
import net.krglok.realms.manager.CampPositionList;
import net.krglok.realms.manager.PositionFace;
import net.krglok.realms.manager.RaiderAction;
import net.krglok.realms.manager.RaiderManager;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.npc.NpcData;

import org.bukkit.Material;
import org.bukkit.block.Biome;

/**
 * <pre>
 * The regiment is a military formation to hold military units and command them
 * Use the inherited managers for 
 * - build a FORTRESS as regiment home 
 * Has a RaiderManager for controlling and managing the regiment.
 * Can not produce anything. It will be supported by his owner.
 * But the most important aspect is the power of a regiment 
 * and the actions like
 * - move
 * - battle
 * - raid
 * The regiments are the basic for battles
 * - regiment vs regiment
 * - regiment vs settlement
 * The regiment is a superregion in HeroStronghold, because this regiontype 
 * can easy destroyed and create.
 * 
 * The regiment has a buildManager for buildup and remove the CAMP.
 * 
 * A regiment has 2 modes
 * - PlayerMode, all action cammonds by player
 * - NPC mode, all action controlled by Managers
 *   - raiderManager
 *   - regimentManager 
 * 
 * A regiment is a  finite state machine. all action done by the RegimentStatus. 
 * The run() must be trigered by an external tick.
 * The Regiment class self has no time or timing. 
 * 
 * 
 * @author Windu
 * </pre>
 */
public class Regiment extends AbstractSettle
{
	private static final int REGIMENT_ITEM_MAX = 10 * ConfigBasis.CHEST_STORE;

	private static int lfdID = 0;
	
//	private int id;
	private RegimentType regimentType = RegimentType.PRIVATEER;
	private RegimentStatus regStatus = RegimentStatus.NONE;
//	private LocationData position;  // ist in AbstractClass
	private LocationData target;
	private LocationData homePosition;
	private Owner owner;
	private boolean isUncamp;
	private boolean isPlayer;
	private int supportId;
	private int lehenId;
	
	private double hungerCounter = 0.0;
	private double foodConsumCounter = 0.0;
	
	private BoardItemList battleOverview;

//	private double FoodFactor = 0.0;

//	private String world;
	private Biome biome;
//	private long age;
	private RegionLocation newSuperRegion;
	private RegionLocation superRequest;

	private BuildPlanMap buildPlan ;
	private BuildManager buildManager = new BuildManager();

	protected RaiderManager raiderManager = null;
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
	
	private HashMap<Integer,PositionFace> campList;
	

	public Regiment() //LogList logList) 
	{
		super();
		lfdID++;
		this.id			= lfdID;
		this.settleType = SettleType.CAMP;
		this.position 	= new LocationData("", 0.0, 0.0, 0.0);
		this.target 	= new LocationData("", 0.0, 0.0, 0.0);
		this.biome		= null;
		this.name		= "Regiment";
		this.ownerId	= 0;
		this.owner 		= null;
		this.supportId = 0;
		this.barrack.setUnitMax(ConfigBasis.defaultUnitMax(settleType));
		this.barrack.setPowerMax(ConfigBasis.defaultPowerMax(settleType));
		this.warehouse.setItemMax(ConfigBasis.defaultItemMax(settleType));
		this.isUncamp 	= true;
		this.isPlayer   = false;
		this.battleOverview = new BoardItemList();
		this.attackPlan = new BattlePlacement();
		this.battle 	= new BattleSetup();
		this.tickCount  = 0;
		this.maxTicks	= 1;
		this.raiderManager = new RaiderManager();
		this.campList = new HashMap<Integer,PositionFace>();

	}

	public static Regiment makeRaider() //LogList logList)
	{
		Regiment regiment = new Regiment(); //logList);
		regiment.setRegimentType(RegimentType.RAIDER);
		regiment.name		= "Privateer";
		regiment.ownerId		= 0;
		regiment.warehouse.getItemList().depositItem("WHEAT", 300);
		regiment.warehouse.getItemList().depositItem("BREAD", 300);
		return regiment;
	}

	
//	public void newCamp(String name,  String ownerName) //, LogList logList)
//	{
//		LocationData position = new LocationData("", 0.0, 0.0,0.0);
//		newSuperRegion = new RegionLocation("CAMP", position, ownerName, name);
//		warehouse.depositItemValue(Material.BED.name(), 1);
//		warehouse.depositItemValue(Material.WOOL.name(), 120);
//		warehouse.depositItemValue(Material.LOG.name(), 250);
//		warehouse.depositItemValue(Material.WHEAT.name(), 100);
//		warehouse.depositItemValue(Material.TORCH.name(), 10);
//		warehouse.depositItemValue(Material.STONE.name(), 100);
//		warehouse.depositItemValue(Material.WORKBENCH.name(), 1);
//		warehouse.depositItemValue(Material.DIRT.name(), 100);
//		warehouse.depositItemValue(Material.WATER.name(), 10);
//		warehouse.depositItemValue(Material.COBBLESTONE.name(),100);
//		warehouse.depositItemValue(Material.WOOD_DOOR.name(), 1);
//		warehouse.depositItemValue(Material.BEDROCK.name(), 1);
//		warehouse.depositItemValue(Material.CHEST.name(), 4);
//		warehouse.depositItemValue(Material.WOOD.name(), 100);
//		warehouse.depositItemValue(Material.RED_MUSHROOM.name(), 50);
//		warehouse.depositItemValue(Material.BROWN_MUSHROOM.name(), 50);
//	}
	
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

	public RaiderManager getRaiderManager()
	{
		return raiderManager;
	}

	public void setRaiderManager(RaiderManager raiderManager)
	{
		this.raiderManager = raiderManager;
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
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
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

	public Boolean isEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Boolean isActive() {
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

	public Biome getBiome() {
		return biome;
	}

	public void setBiome(Biome biome) {
		this.biome = biome;
	}

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
		for (NpcData unit : barrack.getUnitList())
		{
			if (unit.getUnitType() == UnitType.MILITIA)
			{
				centerList.add(unit);
			}
		}
		attackPlan.setPlaceUnit(BattleFieldPosition.CENTER, centerList);
		
		// second line with archers
		UnitList backList = new UnitList();
		for (NpcData unit : barrack.getUnitList())
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

	public void doProduce(ServerInterface server, DataInterface data)
	{
		this.getMsg().add("[REALMS] unit consum");
		age++;
		for (NpcData unit :barrack.getUnitList())
		{
			doConsumUnit(unit, data);
		}
		resident.getNpcList().clear();
		resident.setNpcList(barrack.getUnitList().asNpcList());
		resident.doSettlerCalculation(this.buildingList, data);
	}
	

	/**
	 * start raid on the given settlement
	 */
	public void startRaid(Settlement settle)
	{
		if (regStatus == RegimentStatus.IDLE)
		{
			//configure Battle
			raiderManager.setHasBattle(true);
			raidTarget = settle;
			target =  LocationData.copyLocation(settle.getPosition());
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
			for (NpcData npc : barrack.getUnitList())
			{
				if (npc.isAlive())
				{
					npc.setUnitAction(UnitAction.RAID);
				}
			}
			
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
					NpcData unit = battle.getAttacker().getPlaceUnit(bPos).get(0);
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
					NpcData unit = battle.getDefender().getPlaceUnit(bPos).get(0);
					for(int i= 0; i < battle.getDefender().getPlaceUnit(bPos).size(); i++)
					{
						raidTarget.getBarrack().getUnitList().add(unit);
					}
				}
			}
		}
		
	}
	
	/*
	 * <pre>
	 * active tick for the Manager 
	 * the manager is is a finite state machine
	 * </pre>
	 */
	public void run(RealmModel rModel)
	{
//		System.out.println("Regiment: "+regStatus.name());
		if (position.getWorld() == "")
		{
			regStatus = RegimentStatus.IDLE;
			return;
		}
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
			
			// raiderManager only activ in NPC Mode
			if (isPlayer == false)
			{
				if (regimentType == RegimentType.RAIDER)
				{
					if (raiderManager.hasBattle())
					{
						raiderManager.setRaiderAction(RaiderAction.BATTLE_END);
					}
					doRaidManager(rModel);
					if (raiderManager.getRaiderAction() == RaiderAction.MOVE)
					{
						startMove();
					}
				}
			} else
			{
				if (regimentType == RegimentType.ARMY)
				{
					
				}
			}
			break;
		default :
			break;
		}
	}
	
	/**
	 * build up the camp with the BuildManager instance.
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
			System.out.println("Regiment Camp Action NONE / No DAY cycle");
			regStatus = RegimentStatus.IDLE;
			raiderManager.setRaiderAction(RaiderAction.NONE);
			rModel.getData().writeRegiment(this);
			// set new position for units
			for (NpcData npc : barrack.getUnitList())
			{
				npc.setLocation(this.position);
				npc.setUnitAction(UnitAction.STARTUP);
			}
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
			buildManager.runRebuild(rModel, warehouse, null);
			System.out.println("Regiment start Move :"+buildManager.getStatus()+this.maxTicks);
			rModel.getServer().destroySuperRegion(name);
			isUncamp =true;
			regStatus = RegimentStatus.MOVE;
		} else
		{
//    		System.out.println("Regiment UnCamp "+buildManager.getStatus());
			buildManager.runRebuild(rModel, warehouse, null);
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
			buildPlan = rModel.getData().readTMXBuildPlan(BuildPlanType.FORT, 7, 0);
			if (buildPlan == null)
			{
				System.out.println("TMX buildplan FORT not found");
				return;
			}
			String ownerName;
			if (owner == null)
			{
				ownerName = ConfigBasis.NPC_0;
			} else
			{
				ownerName = owner.getPlayerName();
			}
			// set BuildManager on PREBUILD
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
			if (buildPlan == null)
			{
				
			}
			if (position.getWorld() == "")
			{
				isUncamp =true;
				regStatus = RegimentStatus.MOVE;
			} else
			{
	
				buildManager.newBuild(buildPlan, position, ownerName);
				regStatus = RegimentStatus.UNCAMP;
			}
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
//					showBattleStatus(battle);
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
			System.out.print("WIN: Attacker WIN Battle, defeat Defender");
			battle.setBattleEnd(true);
			transferWarehouse(raidTarget.getWarehouse(), 25);
			endRaid();
			rModel.getData().writeRegiment(this);
			regStatus = RegimentStatus.IDLE; 

		}  else
		{
			if (battle.isBattleEnd() == true)
			{
				System.out.print("END: Defender WIN  Battle , defeat Attacker ");
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
		this.ownerId = owner.getId();
		this.ownerName = owner.getPlayerName();
	}

	/**
	 * @return the campList
	 */
	public HashMap<Integer,PositionFace> getCampList()
	{
		return campList;
	}

//	/**
//	 * @param campList the campList to set
//	 */
//	public void setCampList(HashMap<Integer,PositionFace> campList)
//	{
//		this.campList = campList;
//	}

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

	public Settlement getRaidTarget()
	{
		return raidTarget;
	}

	public void setRaidTarget(Settlement raidTarget)
	{
		this.raidTarget = raidTarget;
	}

	/**
	 * @return the homePosition
	 */
	public LocationData getHomePosition()
	{
		return homePosition;
	}

	/**
	 * @param homePosition the homePosition to set
	 */
	public void setHomePosition(LocationData homePosition)
	{
		this.homePosition = homePosition;
	}

	/**
	 * @return the isPlayer
	 */
	public boolean isPlayer()
	{
		return isPlayer;
	}

	/**
	 * @param isPlayer the isPlayer to set
	 */
	public void setIsPlayer(boolean isPlayer)
	{
		this.isPlayer = isPlayer;
	}

	/**
	 * @return the supporter
	 */
	public int getSupportId()
	{
		return supportId;
	}

	/**
	 * @param supporter the supporter to set
	 */
	public void setSupportId(int supporter)
	{
		this.supportId = supporter;
	}

	public int getLehenId()
	{
		return lehenId;
	}
	
	
	public void setLehenId(int value)
	{
		this.lehenId = lehenId;
	}
	
	
	/**
	 * run the raiderManager for 1 tick
	 * communication with the world due to commandLists in RealmModel
	 * 
	 * @param rModel
	 */
	public void doRaidManager(RealmModel rModel)
	{
		raiderManager.run(rModel, this);
	}
	
	/**
	 * toggle of raiderAction
	 * hint: used within realms.onDay()
	 */
	public void doNextDay()
	{
		raiderManager.nextDay();
	}
	
}

