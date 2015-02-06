package net.krglok.realms.core;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.data.DataInterface;
import net.krglok.realms.data.ServerInterface;
import net.krglok.realms.manager.ReputationList;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.npc.NpcList;
import net.krglok.realms.unit.UnitFactory;
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
 * The algoritmen based on single object. 
 * No list calculation, so the distinct class can define and control the rules.
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

	protected Bank bank;
	protected Warehouse warehouse;
	protected Resident resident;
	protected Barrack barrack;
	protected BuildingList buildingList;

	protected ItemList requiredProduction;
	protected BoardItemList productionOverview;
	protected ReputationList reputations;

	
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
		this.settleType = settleType;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public SettleType getSettleType()
	{
		return settleType;
	}

	public void setSettleType(SettleType settleType)
	{
		this.settleType = settleType;
	}

	public Boolean getIsEnabled()
	{
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}

	public Boolean getIsActive()
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
	
	protected void initSettlement()
	{
		warehouse.setItemMax(ConfigBasis.calcItemMax( buildingList, settleType));
		setSettlerMax();
		
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
			|| (BuildPlanType.getBuildGroup(building.getBuildingType())== 300))
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
					case WORKSHOP:
						ingredients = server.getRecipe(item.ItemRef());
						ingredients.remove(item.ItemRef());
						prodFactor = server.getRecipeFactor(item.ItemRef(),biome, item.value());
//						System.out.println("WS " +item.ItemRef()+":"+item.value()+"*"+prodFactor);
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
						break;
					case TAVERNE:
						if (resident.getHappiness() > Resident.getBaseHappines())
						{
							sale = resident.getSettlerCount() * TAVERNE_FREQUENT / 100.0 * resident.getHappiness();
						} else
						{
							if (resident.getHappiness() > 0.0)
							{
								sale = resident.getSettlerCount() * TAVERNE_FREQUENT / 100.0 * resident.getHappiness()*TAVERNE_UNHAPPY_FACTOR;
							}
						}
						if (resident.getDeathrate() > 0)
						{
							sale = resident.getSettlerCount() * TAVERNE_FREQUENT / 100.0 * TAVERNE_UNHAPPY_FACTOR;
						}
						building.setSales(sale);	
						ingredients = new ItemList();
						break;
					default :
//						System.out.println("doProd:"+building.getHsRegionType()+":"+BuildPlanType.getBuildGroup(building.getBuildingType()));
						ingredients = new ItemList();
						ingredients = server.getRecipeProd(item.ItemRef(),building.getHsRegionType());
						prodFactor = 1;
//							System.out.println(this.getId()+" :doProd:"+building.getHsRegionType()+":"+ingredients.size());
						prodFactor = server.getRecipeFactor(item.ItemRef(), biome, item.value());
//						if (building.getBuildingType() == BuildPlanType.TANNERY)
//						{
//							System.out.println(this.getId()+" :item: "+item.ItemRef()+" igred:"+ingredients.size()+": factor:"+prodFactor);
//						}
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
//							System.out.println("Prod value" +product.ItemRef()+":"+iValue+" | "+prodFactor+" |"+(building.calcSales(server,product)*iValue));
							
//							System.out.println("Prod deposit: "+product.ItemRef()+":"+iValue);
							warehouse.depositItemValue(product.ItemRef(),iValue);
							productionOverview.addCycleValue(product.ItemRef(), iValue);
						}
						if ((sale - cost) > 0.0)
						{
							// berechne Ertrag fuer Building .. der Ertrag wird versteuert !!
							account = (sale-cost); // * (double) iValue / 2;
//							logList.addProductionSale(building.getBuildingType().name(), getId(), building.getId(), account, "CraftManager",getAge());
						} else
						{
							account =  1.0 * (double) iValue;
//							logList.addProductionSale(building.getBuildingType().name(), getId(), building.getId(), account, "CraftManager",getAge());
						}
//						System.out.println("Prod account: "+sale+"-"+cost+"="+account);
						double salary = account / 3.0 * 2.0;
						setWorkerSale( building,  salary);
						account = account - salary;
						building.addSales(account); //-cost);
//						if (this.ownerId != building.getOwnerId())
//						{
//							Owner bOwner = data.getOwners().getOwner(building.getId());
//							if (bOwner !=null)
//							{
//								bOwner.depositCost(cost);
//								bOwner.depositSales(account);
//							}
//						}
						bank.depositKonto(account, "ProdSale ", getId());
						consumStock(prodFactor, ingredients);
					} else
					{
//						System.out.println("No stock for produce " +building.getHsRegionType()+"|"+item.ItemRef()+":"+item.value()+"*"+prodFactor);
					}
				}
//				building.addSales(sale);
			} else
			{
//				System.out.println(this.getId()+" :doEnable:"+building.getHsRegionType()+":"+building.isEnabled());
			}
		}

		
	}
	
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
							System.out.println("Traning Start for Rookie settle :"+id+":"+recrute.getId());
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
						} else
						{
							System.out.println("No Traning Start due to Stock");
						}
					} else
					{
						System.out.println("No Traning Start, missing Rookie :"+id+":"+building.getId());
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
						System.out.println("Traning Consum, training progress");
					} else
					{
						System.out.println("No Traning Consum, NO training progress");
					}
				}
			} else
			{
				System.out.println("GUARDHOUSE NO train : "+building.getMaxTrain());
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
	public void doHappiness(DataInterface data)
	{
		double EntertainFactor = 0.0;
		double sumDif = 0.0;
		EntertainFactor = calcEntertainment();
		consumeFood(data); //SettlerFactor);
		sumDif = EntertainFactor; // + SettlerFactor + FoodFactor;
		resident.doSettlerCalculation(buildingList,data);
		this.getResident().setNpcList(data.getNpcs().getSubListSettle(this.id));
		for (NpcData unit : barrack.getUnitList())
		{
			doConsumUnit(unit);
		}
		int value = (int) (resident.getSettlerCount() * resident.getHappiness()); 
		barrack.addPower(value);
	}
	
	public void doConsumUnit(NpcData unit)
	{
		ItemList ingredients = unit.getUnit().getConsumItems();
		double prodFactor  = 1.0;
		if (checkStock(prodFactor, ingredients))
		{
			consumStock(prodFactor, ingredients);
			if (unit.getHappiness() < 1.0)
			{
				unit.getUnit().addHappiness(0.1);
			}
		} else
		{
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
	protected void consumStock(double prodFactor, ItemList items)
	{
		int iValue = 0;
		for (Item item : items.values())
		{
			iValue = (int)((double) item.value() *prodFactor);
			this.getWarehouse().withdrawItemValue(item.ItemRef(), iValue);
//			System.out.println("Withdraw-"+item.ItemRef()+":"+iValue+":"+prodFactor);
		}
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
//		int required = resident.getSettlerCount();
		
//		int notWorker = resident.getSettlerCount()-townhall.getWorkerCount();
		NpcList homeNpc = resident.getNpcList(); //.getSettleWorker();
//		Iterator<NpcData> npcIterator = homeNpc.values().iterator();
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
//						System.out.println("Child ");
						checkNpcFeed(npc, 1, npc,data);
					}
						
				} else
				{
					if (npc.getNpcType() == NPCType.MANAGER)
					{
						double salery = 3.0;
						bank.withdrawKonto(salery, "MANAGER", this.id);
						npc.depositMoney(salery);
					}
					checkNpcFeed(npc, 1,npc,data);
					
				}
			}
		}
		
//		return factor;
	}

	private void  checkNpcFeed(NpcData npc, int required, NpcData parent, DataInterface data)
	{
		double factor = 0.0; 
		String foodItem = "";
		int amount = 0;
		if (npc.getNpcType() != NPCType.CHILD)
		{
			// Fish consume before wheat consum
			// if not enough bread then the rest will try to consum wheat
			foodItem = Material.COOKED_FISH.name();
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
			// Mushroom Soup consume before wheat or mushroom consum
			// if not enough bread then the rest will try to consum wheat
			foodItem = "MUSHROOM_SOUP";
			amount = warehouse.getItemList().getValue(foodItem);
			if (amount > 0)
			{
				// check for money for food
				if (parent.getMoney() > data.getPriceList().getBasePrice(foodItem))
				{
					if (amount > required)
					{
						factor = factor + checkConsume(foodItem, amount, required,0.3, npc,parent,data);
		
					} else
					{
						required = required - amount;
						factor = factor + checkConsume(foodItem, amount, amount,0.3, npc,parent,data);
					}
					npc.setHappiness(npc.getHappiness() + factor);
					return ;
				}
			}
			// Bread consume before wheat consum
			// if not enough bread then the rest will try to consum wheat
			foodItem = "BREAD";
			amount = warehouse.getItemList().getValue(foodItem);
			if (amount > 0)
			{
				// check for money for food
				if (parent.getMoney() > data.getPriceList().getBasePrice(foodItem))
				{
					if (amount > required)
					{
						factor = factor + checkConsume(foodItem, amount, required, 0.5, npc,parent,data);
						
					} else
					{
						required = required - amount;
						factor = factor + checkConsume(foodItem, amount, amount, 0.5, npc,parent, data);
					}
		//			System.out.println("BREAD "+factor+":"+(npc.getHappiness() + factor));
					npc.setHappiness(npc.getHappiness() + factor);
					return ;
				}
			}
		}
		// Mushroom consume before wheat consum
		// if not enough bread then the rest will try to consum wheat
		foodItem = "RED_MUSHROOM";
		amount = warehouse.getItemList().getValue(foodItem);
		if (amount > 0)
		{
			// check for money for food
			if (parent.getMoney() > data.getPriceList().getBasePrice(foodItem))
			{
				if (amount > required)
				{
					factor = factor + checkConsume(foodItem, amount, required, 0.0, npc,parent,data);
					
				} else
				{
					required = required - amount;
					factor = factor + checkConsume(foodItem, amount, amount, 0.0, npc,parent,data);
				}
				npc.setHappiness(npc.getHappiness() + factor);
				return ;
			}
		}
		// Mushroom consume before wheat consum
		// if not enough bread then the rest will try to consum wheat
		foodItem = "BROWN_MUSHROOM";
		amount = warehouse.getItemList().getValue(foodItem);
		if (amount > 0)
		{
			// check for money for food
			if (parent.getMoney() > data.getPriceList().getBasePrice(foodItem))
			{
				if (amount > required)
				{
					factor = factor + checkConsume(foodItem, amount, required, 0.0, npc,parent,data);
					
				} else
				{
					required = required - amount;
					factor = factor + checkConsume(foodItem, amount, amount, 0.0, npc,parent,data);
				}
				npc.setHappiness(npc.getHappiness() + factor);
				return ;
			}
		}

		//  Wheat is the last consum item
		//  without wheat the residents are very unhappy
		foodItem = "WHEAT";
		amount = warehouse.getItemList().getValue(foodItem);
		if (amount > required)
		{
			factor = factor + checkConsume(foodItem, amount, required,0.0, npc,parent,data);
			
		} else
		{
			factor = factor + checkConsume(foodItem, amount, required, 0.0, npc,parent,data);
		}
		npc.setHappiness(npc.getHappiness() + factor);
//		return factor;
	}

	private double checkConsume(String foodItem , int amount, int required, double happyFactor, NpcData npc, NpcData parent, DataInterface data)
	{
		double factor = 0.0;
		double cost = data.getPriceList().getBasePrice(foodItem);
		// check for money of food
		if (parent.getMoney() < cost)
		{
//			System.out.println("No food money !"+npc.getId());
			amount = 0;
		}
		if (required > amount)
		{	
			// keine Versorgung
			if (resident.getSettlerCount() > 5)
			{
//				System.out.println("keine Versorgung :"+npc.getId());
//				factor = npc.hungerCounter + ((double)required / (double)resident.getSettlerMax()) * -1.0;
				factor = -0.1;
				if (npc.foodConsumCounter > MIN_FOODCONSUM_COUNTER)
				{
					npc.foodConsumCounter = npc.foodConsumCounter + factor;
				}
				// setzte required food
				requiredProduction.depositItem(foodItem, required);
				npc.hungerCounter = npc.hungerCounter + factor ; // hungerCounter + factor;
			}
		} else
		{
			npc.hungerCounter = 0.0;
			parent.withdrawMoney(cost);
			bank.depositKonto(cost, "Food", this.id);
			warehouse.withdrawItemValue(foodItem, required);
//			System.out.println(foodItem+":"+required);
			productionOverview.addCycleValue(foodItem, (required* -1));
//			if (npc.foodConsumCounter > MIN_FOODCONSUM_COUNTER)
//			{
			npc.foodConsumCounter = npc.foodConsumCounter + (double)required; //((double)resident.getSettlerCount() / 20.0);
			if (npc.foodConsumCounter > 0)
			{
				npc.foodConsumCounter = 0.0; //npc.foodConsumCounter + (double)required; //((double)resident.getSettlerCount() / 20.0);
			}
//			}
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
//				System.out.println("Min Food Consum"+factor);
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
//					System.out.println("Low Happiness "+factor);
				} else
				{
//					System.out.println("Normal Happiness"+factor);
					factor = happyFactor;
				}
				npc.foodConsumCounter = 0;
			}
		}
		return factor;
	}
	
}
