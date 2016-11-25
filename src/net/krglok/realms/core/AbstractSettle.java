package net.krglok.realms.core;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import net.krglok.realms.Common.Bank;
import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.ItemList;
import net.krglok.realms.Common.ItemPrice;
import net.krglok.realms.Common.ItemPriceList;
import net.krglok.realms.Common.LocationData;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.data.DataInterface;
import net.krglok.realms.data.ServerInterface;
import net.krglok.realms.manager.ReputationList;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.npc.NpcList;
import net.krglok.realms.unit.UnitFactory;
import net.krglok.realms.unit.UnitMilitia;
import net.krglok.realms.unit.UnitType;

/**
 * <pre>
 * Basic settlement class. This elements every settlement needs to functioning
 * - bank
 * - warehouse
 * - resident
 * - barrack
 * - buildingList
 * Also the basic algorithmen and rules here defined
 * - production
 * - training
 * - unit generation
 * - settler breeding
 * - settler lifecycle 
 * - consumFood
 * - automatic trading
 * - can automatic create a building  
 * incorporate the rules for production, fertility and money
 * The algoritmen based on the actual single object.
 * 
 * @author Windu
 * </pre>
 */
public abstract class AbstractSettle
{
	protected static final double MIN_FOODCONSUM_COUNTER = -5.0;
	protected static double TAVERNE_FREQUENT = 10.0;
	protected static final double TAVERNE_UNHAPPY_FACTOR = 2.0;
	
	protected int id; // = 0;
	protected SettleType settleType; // = SettleType.NONE;
	protected Boolean isEnabled; // = true;
	protected Boolean isActive; // = true;
	protected String name;
	protected int ownerId;
	protected String ownerName = "";
//	protected int kingdomId;
	protected int tributId;
	protected long age;
	protected LocationData position;

	protected Trader trader;
	protected Bank bank;
	protected Warehouse warehouse;
	protected Resident resident;
	protected Barrack barrack;
	protected BuildingList buildingList;

	
	protected ItemList requiredProduction;
	protected BoardItemList productionOverview;
	protected ReputationList reputations;
	protected ArrayList<String> msg = new ArrayList<String>();
	protected ItemPriceList prodAnalyse = new ItemPriceList();

	
	public AbstractSettle()
	{
		super();
		this.id = 0;
		this.settleType = SettleType.NONE;
		this.isEnabled = true;
		this.isActive = true;
		this.name = ConfigBasis.NEW_SETTLEMENT;
		this.ownerId = 0;
		this.ownerName = "";
		this.tributId = 0;
		this.age = 0;

		this.bank = new Bank();
		this.warehouse = new Warehouse(ConfigBasis.defaultItemMax(SettleType.NONE)); 
		this.resident = new Resident();
		this.barrack = new Barrack(ConfigBasis.defaultUnitMax(SettleType.NONE));
		this.buildingList = new BuildingList();
		this.requiredProduction = new ItemList();
		this.productionOverview = new BoardItemList();
		this.reputations = new ReputationList();

		this.barrack.setPowerMax(ConfigBasis.defaultPowerMax(settleType));
		
	}
	
	public AbstractSettle(SettleType settleType)
	{
		super();
		this.id = 0;
		this.settleType = settleType;
		// reinit Warehouse
		this.warehouse = new Warehouse(ConfigBasis.defaultItemMax(settleType)); 
		this.barrack.setPowerMax(ConfigBasis.defaultPowerMax(settleType));
		this.isEnabled = true;
		this.isActive = true;
		this.name = ConfigBasis.NEW_SETTLEMENT;
		this.ownerId = 0;
		this.ownerName = "";
		this.tributId = 0;
		this.age = 0;

		this.bank = new Bank();
		this.resident = new Resident();
		this.buildingList = new BuildingList();
		this.requiredProduction = new ItemList();
		this.productionOverview = new BoardItemList();
		this.reputations = new ReputationList();

		this.barrack.setPowerMax(ConfigBasis.defaultPowerMax(settleType));
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SettleType getSettleType()
	{
		return settleType;
	}

	public void setSettleType(SettleType settleType)
	{
		this.settleType = settleType;
	}

	public Boolean isEnabled()
	{
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}

	public Boolean isActive()
	{
		return isActive;
	}

	public void setIsActive(Boolean isActive)
	{
		this.isActive = isActive;
	}

	public long getAge()
	{
		return age;
	}

	public void setAge(long value)
	{
		this.age = value;
	}
	
	public LocationData getPosition()
	{
		return position;
	}

	public void setPosition(LocationData position)
	{
//		System.out.println("New Position : "+this.id);
		this.position = position;
	}

	public Trader getTrader()
	{
		return trader;
	}

	public void setTrader(Trader trader)
	{
		this.trader = trader;
	}
	
	public Bank getBank()
	{
		return bank;
	}

	public void setBank(Bank bank)
	{
		this.bank = bank;
	}

	public Warehouse getWarehouse()
	{
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse)
	{
		this.warehouse = warehouse;
	}

	public Resident getResident()
	{
		return resident;
	}

	public void setResident(Resident resident)
	{
		this.resident = resident;
	}

	public Barrack getBarrack()
	{
		return barrack;
	}

	public void setBarrack(Barrack barrack)
	{
		this.barrack = barrack;
	}

	public BuildingList getBuildingList()
	{
		return buildingList;
	}

	public void setBuildingList(BuildingList buildingList)
	{
		this.buildingList = buildingList;
	}
	
	public ReputationList getReputations()
	{
		return reputations;
	}
	
	public void setReputationList(ReputationList repList)
	{
		this.reputations = repList;
	}
	
	public BoardItemList getProductionOverview()
	{
		return productionOverview;
	}
	

	/**
	 * 
	 * @return list of required items from last production cycle
	 */
	public ItemList getRequiredProduction()
	{
		return requiredProduction;
	}
	
	public ItemPriceList getProdAnalyse()
	{
		return prodAnalyse;
	}

	public 	ArrayList<String> getMsg()
	{
		return this.msg;
	}

	
	public int getDayofWeek()
	{
		int year = (int) (age / (12 * 30));
		int restYear = (int) (age - (year * 12 * 30));
		int month = restYear / 30;
		int restMonth = restYear - (month * 30);
		int week = restMonth / 7;
		int day = restMonth - (week * 7);
		
		return day;
	}

	
	protected void initSettlement()
	{
		warehouse.setItemMax(ConfigBasis.calcItemMax( buildingList, settleType));
		setSettlerMax();
		setUnitMax();
	}
	
	protected void setSettlerMax()
	{
		int settlerMax = 5;
		for (Building building : buildingList.values())
		{
			if (building.isEnabled())
			{
				settlerMax = settlerMax + building.getSettler();
			}
		}
		resident.setSettlerMax(settlerMax);
	}

	protected void setUnitMax()
	{
		int unitMax = 0;
		for (Building building : buildingList.values())
		{
//			if (building.isEnabled())
//			{
				if (BuildPlanType.getBuildGroup(building.getBuildingType()) == 500)
				{
					unitMax = unitMax + ConfigBasis.getDefaultSettler(building.getBuildingType());
				}
//			}
		}
		barrack.setUnitMax(unitMax);
	}

	protected void doService(ServerInterface server, DataInterface data, Building building, Biome biome)
	{
		double prodFactor = 1;
		int iValue = 0;
		double sale = 0.0;
		double cost = 0.0;
		double account = 0.0; 
		ItemArray products;
		ItemList ingredients;

		building.setSales(0.0);
		if ((BuildPlanType.getBuildGroup(building.getBuildingType())== 600)
			)
		{
			if ((building.isEnabled())
				&& building.isIdleReady()	
				)
			{
				building.addIdlleTime();
				sale = 0.0;
				cost = 0.0;
				account = 0.0;
				iValue = 0;
				products = building.produce(server);
//				for (Item item : products)
				if (products.size() > 0)
				{
					Item item = products.get(0);
					
					switch(building.getBuildingType())
					{
					case TAVERNE:
						ingredients = server.getRecipe(item.ItemRef());
						ingredients.remove(item.ItemRef());
						prodFactor = server.getRecipeFactor(item.ItemRef(),biome, item.value());
						building.getMsg().add("Produce :"+this.getId()+" :item: "+item.ItemRef()+" ingred:"+ingredients.size()+": factor:"+prodFactor);
						building.getMsg().add("Ingredients:");
						building.getMsg().addAll(ingredients.keySet());
						if (resident.getHappiness() > Resident.getBaseHappines())
						{
							sale = resident.getSettlerCount() * TAVERNE_FREQUENT / 100.0 * resident.getHappiness();
							building.getMsg().add("resident.getHappiness() > Resident.getBaseHappines()");
						} else
						{
							if (resident.getHappiness() > 0.0)
							{
								sale = resident.getSettlerCount() * TAVERNE_FREQUENT / 100.0 * resident.getHappiness()*TAVERNE_UNHAPPY_FACTOR;
								building.getMsg().add("resident.getHappiness() > 0.0");
							} else
							{
								sale = resident.getSettlerCount() * TAVERNE_FREQUENT / 100.0 * resident.getHappiness()*TAVERNE_UNHAPPY_FACTOR;
								building.getMsg().add("resident.getHappiness() < 0.0");
							}
						}
						if (resident.getDeathrate() > 0)
						{
							sale = resident.getSettlerCount() * TAVERNE_FREQUENT / 100.0 * TAVERNE_UNHAPPY_FACTOR;
							building.getMsg().add("resident.getDeathrate() > 0");
						}
						double salary = sale / 3.0 * 1.5;
						setWorkerSale( building,  salary);
						building.setSales((sale-salary));	
						building.getMsg().add(" Sale :"+ConfigBasis.setStrright((sale-salary),8));
						building.getMsg().add(" NPC  :"+ConfigBasis.setStrright((salary),8));
						ingredients = new ItemList();
						break;
					default :
						ingredients = new ItemList();
						prodFactor = 1;
						break;
					}
//					System.out.println("check");
					if (checkStock(prodFactor, ingredients))
					{
//						iValue = item.value();
						// berechne die MaterialKosten der Produktion
						cost = server.getRecipePrice(item.ItemRef(), ingredients);
						// berechne Verkaufpreis der Produktion
						for (Item product : products)
						{
							iValue = (int)((double) product.value() *prodFactor);
							sale = sale + (building.calcSales(server,product)*iValue);
							warehouse.depositItemValue(product.ItemRef(),iValue);
							productionOverview.addCycleValue(product.ItemRef(), iValue);
						}
						if ((sale - cost) > 0.0)
						{
							// berechne Ertrag fuer Building .. der Ertrag wird versteuert !!
							if (cost > 0.0)
							{
								account = cost;
							} else
							{
								account = sale;
							}
						} else
						{
							// berechne Minimal Ertrag
							account =  1.0 * (double) iValue;
						}
						// berechnet Ertrag 
						double salary = account / 2.0; //* 3.0 * 2.0;
						// Nicht weniger als 1 Kupfer pro Tag
						if (account < 1.0) { account = 1.0; }
						// Ertrag , EInkommen des NPC
						setWorkerSale( building,  salary);
						account = account - salary;
						// Ertrag / Einkommen des Building
						building.addSales(salary);
						// das Settlement bezahlt die NPC
						bank.depositKonto(salary, "Service ", getId());

						// material consum get from warehouse
						consumStock(prodFactor, ingredients);

					} else
					{
						building.getMsg().add("No stock for produce " +building.getHsRegionType()+"|"+item.ItemRef()+":"+item.value()+"*"+prodFactor);
					}
				}
//				building.addSales(sale);
			} else
			{
				building.getMsg().add(this.getId()+" :doEnable:"+building.getHsRegionType()+":"+building.isEnabled());
			}
		}

		
	}

	
	/**
	 * make a production for all buildings in a settlement
	 * only allowed for urban settlement
	 * 
	 * @param server
	 * @param data
	 * @param building
	 * @param biome
	 */
	protected void doProduction(ServerInterface server, DataInterface data, Building building, Biome biome)
	{
		double prodFactor = 1;
		int iValue = 0;
		double sale = 0.0;
		double cost = 0.0;
		double account = 0.0; 
		ItemArray products;
		ItemList ingredients;
		
		building.setSales(0.0);
		if ((BuildPlanType.getBuildGroup(building.getBuildingType())== 200)
			|| (BuildPlanType.getBuildGroup(building.getBuildingType())== 300)
			|| (BuildPlanType.getBuildGroup(building.getBuildingType())== 600)
			)
		{
			this.msg.add("Produce " +building.getBuildingType()+":"+building.isEnabled()+":"+building.isIdleReady());
			if ((building.isEnabled())
				&& building.isIdleReady()	
				)
			{
				building.addIdlleTime();
				sale = 0.0;
				cost = 0.0;
				account = 0.0;
				iValue = 0;
				products = building.produce(server);
//				for (Item item : products)
				if (products.size() > 0)
				{
					Item item = products.get(0);
					switch(building.getBuildingType())
					{
					case WORKSHOP:
						ingredients = server.getRecipe(item.ItemRef());
						ingredients.remove(item.ItemRef());
						prodFactor = server.getRecipeFactor(item.ItemRef(),biome, item.value());
						this.getMsg().add("Produce :"+this.getId()+" :item: "+item.ItemRef()+" ingred:"+ingredients.size()+": factor:"+prodFactor);
						this.getMsg().add("Ingredients:");
						this.getMsg().addAll(ingredients.keySet());
						break;
					case BAKERY:
						if (building.isSlot())
						{
//							System.out.println("SLOT "+item.ItemRef());
							ingredients = server.getRecipe(item.ItemRef());
							ingredients.remove(item.ItemRef());
						} else
						{
							ingredients = server.getRegionUpkeep(building.getHsRegionType());
						}
						prodFactor = server.getRecipeFactor(item.ItemRef(), biome, item.value());
						this.msg.add("Produce :"+this.getId()+" :item: "+item.ItemRef()+" ingred:"+ingredients.size()+": factor:"+prodFactor);
						this.msg.add("Ingredients:");
						this.msg.addAll(ingredients.keySet());
						break;
					default :
//						System.out.println("doProd:"+building.getHsRegionType()+":"+BuildPlanType.getBuildGroup(building.getBuildingType()));
						ingredients = new ItemList();
						ingredients = server.getRecipeProd(item.ItemRef(),building.getHsRegionType());
						prodFactor = server.getRecipeFactor(item.ItemRef(), biome, item.value());
						this.msg.add("Produce :"+this.getId()+" :item: "+item.ItemRef()+" ingred:"+ingredients.size()+": factor:"+prodFactor);
						this.msg.add("Ingredients:");
						this.msg.addAll(ingredients.keySet());
						break;
					}
					for (Item prod : products)
					{
						int pValue = (int) (prod.value() * prodFactor);
						prodAnalyse.setItemPrice(new ItemPrice(prod.ItemRef(),pValue,0.0));
					}

					for (Item prod : ingredients.values())
					{
						double pValue = (double) prod.value() * prodFactor;
						prodAnalyse.setItemPrice(new ItemPrice(prod.ItemRef(),0,pValue));
					}
//					System.out.println("check");
					if (checkStock(prodFactor, ingredients))
					{
						
//						iValue = item.value();
						// berechne die MaterialKosten der Produktion
						cost = server.getRecipePrice(item.ItemRef(), ingredients);
						bank.depositKonto(cost, building.getBuildingType().name()+" Production Cost ",getId());
						// berechne Verkaufpreis der Produktion
						for (Item product : products)
						{
							iValue = (int)((double) product.value() *prodFactor);
							sale = sale + (building.calcSales(server,product)*iValue);
							warehouse.depositItemValue(product.ItemRef(),iValue);
							productionOverview.addCycleValue(product.ItemRef(), iValue);
						}
						if ((sale) > (2*cost))
						{
							// berechne Ertrag fuer Building .. der Ertrag wird versteuert !!
							account = cost; 
						} else
						{
							// berechne Minimal Ertrag
							account =  sale - cost;   //1.0 * (double) iValue;
						}
						// berechnet Ertrag 
						double salary = account / 2.0; //* 3.0 * 2.0;
						// Nicht weniger als 1 Kupfer pro Tag
						if (account < 1.0) 
						{ 
							account = 1.0;
							salary  = 0.5; 
						}
						// Ertrag , EInkommen des NPC
						setWorkerSale( building,  salary);
//						account = account - salary;
						// Ertrag / Einkommen des Building
						building.addSales(salary);
						// das Settlement bezahlt die NPC
						bank.depositKonto(-salary,building.getBuildingType().name()+" Work Salary ", getId());

						// money to Settlement is deleted , because the settlement get the item !
//						bank.depositKonto(salary/2.0, "ProdSale ", getId());
//						bank.depositKonto(salary/10.0, "ProdSale ", getId());
						
						// material consum get from warehouse
						consumStock(prodFactor, ingredients);

					} else
					{
						this.msg.add("No stock for produce " +building.getHsRegionType()+"|"+item.ItemRef()+":"+item.value()+"*"+prodFactor);
					}
				}
//				building.addSales(sale);
			} else
			{
				this.msg.add(this.getId()+" :doEnable:"+building.getHsRegionType()+":"+building.isEnabled());
			}
		}

		
	}
	
	/**
	 * Start training for units
	 * do training progress for units in training
	 *  
	 * @param data
	 * @param building
	 */
	protected void doTrainStart(DataInterface data, Building building)
	{
		double prodFactor = 1;
		ItemList ingredients;

		switch(building.getBuildingType())
		{
		case ARCHERY:
		case GUARDHOUSE:
			// Training activ ?
			if (building.getMaxTrain() > 0)
			{
				// training must start  OR progress
				if (building.getTrainCounter() == 0)
				{
					// new training start
					NpcData recrute = resident.findRecrute();
					if (recrute != null)
					{
						ingredients = building.militaryProduction();
						prodFactor  = 1.0;
						if (checkStock(prodFactor, ingredients))
						{
							building.getMsg().add(this.settleType+"Traning Start for Rookie settle :"+id+":"+recrute.getId());
							// ausrüstung abbuchen
							consumStock(prodFactor, ingredients);
							// Siedler aus vorrat nehmen
							recrute.setNpcType(NPCType.MILITARY);
							recrute.setUnitType(UnitType.ROOKIE);
							recrute.setWorkBuilding(building.getId());
							if (resident.getNpcList().containsKey(recrute.getId()) == true)
							{
								resident.getNpcList().remove(recrute.getId());
							}
							barrack.getUnitList().putUnit(recrute);
//							resident.depositSettler(-1);
							// Counter starten
							building.addTrainCounter(1);
							data.writeBuilding(building);
							data.writeNpc(recrute);
						} else
						{
							building.getMsg().add(this.settleType+"No Traning Start due to Stock");
						}
					} else
					{
						building.getMsg().add(this.settleType+" No Traning Start, missing Rookie :"+id+":"+building.getId());
						System.out.println(this.settleType+" No Traning Start, missing Rookie :"+id+":"+building.getId());
					}
//						System.out.println("GUARD " +item.ItemRef()+":"+item.value()+"*"+prodFactor);
				} else
				{ // do training progress
					ingredients = building.militaryConsum();
					prodFactor  = 1.0;
					if (checkStock(prodFactor, ingredients))
					{
						consumStock(prodFactor, ingredients);
						building.addTrainCounter(1);
						data.writeBuilding(building);
						building.getMsg().add("Traning Consum, training progress");
					} else
					{
						this.requiredProduction.addAll(ingredients);
						String consum = ingredients.asString();
						building.getMsg().add("No Traning Consum, "+consum);
					}
				}
			} else
			{
				building.getMsg().add("GUARDHOUSE NO train : "+building.getMaxTrain());
//				System.out.println("GUARDHOUSE NO train : "+building.getMaxTrain());
			}
			break;
		default:
			break;
		}
		
	}
	
	/**
	 * calculate the whole happines for the different influences 
	 * 
	 * @param data
	 */
	protected void doHappiness(DataInterface data)
	{
		double EntertainFactor = 0.0;
		double sumDif = 0.0;
		EntertainFactor = calcEntertainment();
		consumeFood(data); //SettlerFactor);
		sumDif = EntertainFactor; // + SettlerFactor + FoodFactor;
		
		if (resident.getNpcList().size() > 0)
		{
			System.out.println("[REALMS] "+this.getSettleType()+" Resident:"+resident.getNpcList().size());
			resident.doSettlerCalculation(buildingList,data);
			// resident npc werden nach geladen ! NICHT erlaubt !!!!!!!!!!!!!!!!!!!
//			this.getResident().setNpcList(data.getNpcs().getSubListSettle(this.id));
		}
		for (NpcData unit : barrack.getUnitList())
		{
			doConsumUnit(unit, data);
		}
		int value = (int) (resident.getSettlerCount() * resident.getHappiness()); 
		barrack.addPower(value);
	}
	
	/**
	 * calculate consum for units in settlement or regiment
	 * 
	 * @param unit
	 * @param data
	 */
	protected void doConsumUnit(NpcData unit, DataInterface data)
	{
		ItemList ingredients = unit.getUnit().getConsumItems();
		double prodFactor  = 1.0;
		if (checkStock(prodFactor, ingredients))
		{
//			System.out.println("[REALMS] lehen consum ! :"+ingredients.size());
			if (consumStock(prodFactor, ingredients) == false)
			{
//				System.out.println("[REALMS] lehen food ! ");
				checkNpcFeed(unit, 1,unit,data);
			}

			if (unit.getHappiness() < 1.0)
			{
				unit.getUnit().addHappiness(0.1);
			}
		} else
		{
			msg.add("[REALMS] "+this.settleType+" has no required food ! ");
			checkNpcFeed(unit, 1,unit,data);
			if (unit.getHappiness() > -1.0)
			{
				unit.getUnit().addHappiness(-0.1);
			}
		}
	}
	
	/**
	 * check amount in warehouse for take items
	 * if not set requiredItemList
	 * @param prodFactor
	 * @param items
	 * @return
	 */
	protected boolean checkStock(double prodFactor, ItemList items)
	{
		if (items.size() == 0)
		{
			msg.add("[REALMS] "+this.settleType+" checkStock NO items ");
			return false;
		} 
		int iValue = 0;
		// Check amount in warehouse
		boolean isStock = true;
		for (String itemRef : items.keySet())
		{
			iValue = (int) (items.getValue(itemRef)*prodFactor);
			if (this.warehouse.getItemList().getValue(itemRef) < iValue)
			{
//				System.out.println("miss: "+itemRef+":"+iValue);
				isStock = false;
				if (requiredProduction.containsKey(itemRef))
				{
					requiredProduction.depositItem(itemRef, iValue);
				} else
				{
					requiredProduction.depositItem(itemRef, iValue);
//					requiredProduction.addItem(itemRef, iValue);
				}
			}
		}
		return isStock;
	}

	/**
	 * give npc his part of production sale
	 * 
	 * @param building
	 * @param account
	 */
	protected void setWorkerSale(Building building, double account)
	{
		NpcList homeNpc = resident.getNpcList().getBuildingWorker(building.getId());
		if (homeNpc.size() > 0)
		{
			double value = account / homeNpc.size();
			for (NpcData npc : homeNpc.values())
			{
				if (value > 0.0)
				{
					npc.depositMoney(value);
				}
			}
		} else
		{
//			System.out.println("No NPC");
		}
	}

	/**
	 * consum items from warehouse
	 * @param prodFactor
	 * @param items
	 */
	protected boolean consumStock(double prodFactor, ItemList items)
	{
		int iValue = 0;
		for (Item item : items.values())
		{
			iValue = (int)((double) item.value() *prodFactor);
			if (this.getWarehouse().withdrawItemValue(item.ItemRef(), iValue) == false)
			{
				return false;
			}
			productionOverview.addOutValue(item.ItemRef(), (iValue));
		}
		return true;
	}

	/**
	 * calculate happines for entertaiment
	 * @return happiness
	 */
	private double calcEntertainment()
	{
		int tavernNeeded = (resident.getSettlerCount() / ConfigBasis.ENTERTAIN_SETTLERS);
		int tavernCount = 0;
		double factor = 0.0;
		for (Building building : buildingList.values())
		{
			if (building.isEnabled())
			{
				if (building.getBuildingType() == BuildPlanType.TAVERNE)
				{
					tavernCount++;
				}
			}
		}
		if (tavernCount > 0)
		{
			if (tavernNeeded >= tavernCount)
			{
			  factor = ((double) tavernCount  / (double)tavernNeeded );
			} else
			{
				factor = 0.5;
			}
		}
		
		return factor;
	}

	/**
	 * calculate happines for the food supply of the settlers
	 * - no influence if fodd supply is guarantee 
	 * - haevy influence if food supply too low.
	 * the settlers are all supplied or none  
	 * @param oldFactor
	 * @return happiness factor of food supply 
	 */
	private void consumeFood(DataInterface data) //double oldFactor)
	{
		NpcList homeNpc = resident.getNpcList(); //.getSettleWorker();
		for (NpcData npc : homeNpc.values())
		{
			if (npc.isAlive())
			{
				if (npc.isChild())
				{
					NpcData parent = homeNpc.get(npc.getFather());
					if (parent != null)
					{
						if (parent.isAlive() == false)
						{
							parent = null;
						}
					}
					if (parent == null)
					{
						parent = homeNpc.get(npc.getMother());
						if (parent != null)
						{
							if (parent.isAlive() == false)
							{
								parent = null;
							}
						}
					}
					if (parent != null)
					{	
						if (parent.getMoney() < 1.0)
						{
//							System.out.println("Child Money");
							checkNpcFeed(npc, 1, npc, data);
						} else
						{
//							System.out.println("Child Parent");
							checkNpcFeed(npc, 1, parent,data);
						}
					} else
					{
						msg.add("[REALMS] Child consum food "+npc.getId()+": "+npc.getNpcType());
						checkNpcFeed(npc, 1, npc,data);
					}
						
				} else
				{
//					if ((npc.getNpcType() != NPCType.MANAGER)
//						|| (npc.getNpcType() != NPCType.BUILDER)
//						|| (npc.getNpcType() != NPCType.MAPMAKER)
//						)
//					{
//						double salery = 0.35;
//						bank.withdrawKonto(salery, "Food Child", this.id);
//						npc.depositMoney(salery);
//					}
					if (this.settleType == SettleType.LEHEN_1)
					{
						msg.add("[REALMS] Child consum food "+npc.getId()+": "+npc.getNpcType());
					}
					checkNpcFeed(npc, 1,npc,data);
					
				}
			} else
			{
				if (this.settleType == SettleType.LEHEN_1)
				{
					msg.add("[REALMS] isDead "+npc.getId()+": "+npc.getNpcType());
				}
				
			}
		}
		
//		return factor;
	}

	private void  checkNpcFeed(NpcData npc, int required, NpcData parent, DataInterface data)
	{
		double factor = 0.0; 
//		String foodItem = "";
		int amount = 0;
//		if (npc.getNpcType() != NPCType.CHILD)
//		{
			for (Item item :ConfigBasis.initFoodMaterial().values())
			{
				String foodItem = item.ItemRef();
				if (Material.getMaterial(foodItem) != Material.WHEAT)
				{
					amount = warehouse.getItemList().getValue(foodItem);
					if (amount > 0)
					{
						// check for money for food
						if (parent.getMoney() > data.getPriceList().getBasePrice(foodItem))
						{
							if (amount > required)
							{
								factor = factor + checkConsume(foodItem, amount, required, 0.3,npc,parent,data);
								
							} else
							{
								required = required - amount;
								factor = factor + checkConsume(foodItem, amount, amount, 0.3, npc,parent,data);
							}
							npc.setHappiness(npc.getHappiness() + factor);
							return ;
						}
					}
				}
			}
//		}  // if ( isChild)

		//  Wheat is the last consum item
		//  without wheat the residents are very unhappy
		String foodItem = "WHEAT";
		amount = warehouse.getItemList().getValue(foodItem);
		if (amount > required)
		{
			factor = factor + checkConsume(foodItem, amount, required,0.0, npc,parent,data);
		} else
		{
			factor = factor + checkConsume(foodItem, amount, required, 0.0, npc,parent,data);
		}
		prodAnalyse.setItemPrice(new ItemPrice("FOOD",0,1.0));
		npc.setHappiness(npc.getHappiness() + factor);
	}

	private double checkConsume(String foodItem , int amount, int required, double happyFactor, NpcData npc, NpcData parent, DataInterface data)
	{
		double factor = 0.0;
		double cost = data.getPriceList().getBasePrice(foodItem);
		// check for money of food
		if (parent.getMoney() < cost)
		{
			amount = 0;
		}
		if (required > amount)
		{	
			// keine Versorgung bei WHEAT anzeigen
			if (Material.getMaterial(foodItem) == Material.WHEAT)
			{
				if (resident.getSettlerCount() > 5)
				{
					msg.add(this.getSettleType()+" :"+this.id+" No Money for "+foodItem+" :"+npc.getId());
					factor = -0.1;
					if (npc.foodConsumCounter > MIN_FOODCONSUM_COUNTER)
					{
						npc.foodConsumCounter = npc.foodConsumCounter + factor;
					}
					// setze required food
					requiredProduction.depositItem(foodItem, required);
					npc.hungerCounter = npc.hungerCounter + factor ; // hungerCounter + factor;
				}
			}
		} else
		{
			npc.hungerCounter = 0.0;
			parent.withdrawMoney(cost);
			bank.depositKonto(cost, "Food", this.id);
			warehouse.withdrawItemValue(foodItem, required);
			productionOverview.addOutValue(foodItem, (required));
			npc.foodConsumCounter = npc.foodConsumCounter + (double)required; //((double)resident.getSettlerCount() / 20.0);
			if (npc.foodConsumCounter > 0)
			{
				npc.foodConsumCounter = 0.0; //npc.foodConsumCounter + (double)required; //((double)resident.getSettlerCount() / 20.0);
			}
			if (npc.foodConsumCounter < 0.0)
			{
				
				// ziemlich tief !! -5.0
				if (npc.getHappiness() < MIN_FOODCONSUM_COUNTER)
				{
					factor = 0.1; //changed
				} else
				{
					factor = happyFactor;
				}
			} else
			{
				if (npc.getHappiness() < 0.6)
				{
					if (resident.getSettlerMax() > resident.getSettlerCount())
					{
						factor = happyFactor;
					} else
					{
						factor = happyFactor/2;
					}
				} else
				{
					factor = happyFactor;
				}
				npc.foodConsumCounter = 0;
			}
		}
		return factor;
	}
	
}
