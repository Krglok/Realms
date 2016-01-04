package net.krglok.realms.core;

import net.krglok.realms.data.DataInterface;
import net.krglok.realms.npc.GenderType;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.npc.NpcList;

/**
 * <pre>
 * hold the human  resources of a settlement
 * calculate birthrate and deathrate 
 * calculate happines based on settlerCount
 * </pre>
 * @author windu
 *
 */
public class Resident  
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1454415012035643630L;
	private static final double FertilityCounter_Limit = 75.0;
	private static final double BASE_HAPPINES = 0.5;   
	
	private int settlerMax;
	private int settlerBirthrate;
	private int settlerDeathrate;
	private double fertilityCounter ;
	private double deathCounter = 0.0;
	public int oldPopulation = 0; 
	private int settlerCount;
//	private int workerCount;
	private int cowCount;
	private int horseCount;

	private double happiness ;
	
	private NpcList npcList;
	
	public Resident()
	{
		fertilityCounter = 100.0;
		happiness   = 0.6;
		settlerMax  = 0;
		settlerCount = 0;
//		workerCount  = 0;
		cowCount     = 0;
		horseCount = 0;
		npcList = new NpcList(); 
	}

	public int getSettlerMax()
	{
		return settlerMax;
	}

	public void setSettlerMax(int settlerMax)
	{
		this.settlerMax = settlerMax;
	}

	/**
	 * return the number of settler stored in the database
	 * the number will read from datafile.
	 * This is helpful, when npcList is not available
	 * 
	 * @return  number of settlers
	 */
	public int getPopulation()
	{
		return oldPopulation;
	}
	
	/**
	 * 
	 * @return  number of residents in settlement
	 */
	public int getSettlerCount()
	{
		if (npcList == null)
		{
			return settlerCount;
		} else
		{
			return npcList.getAliveNpc().size();
		}
	}

	
	/**
	 * Set resident number , no check for conditions 
	 * @param residentCount
	 */
	public void setSettlerCount(int residentCount)
	{
		this.settlerCount = residentCount;
		this.oldPopulation = residentCount;
	}

	public void setDefaultSettlerCount(SettleType settleType)
	{
		switch(settleType)
		{
		case HAMLET :
			this.settlerCount = 13;
			break;
		case TOWN :
			this.settlerCount = 15;
			break;
		case CITY :
			this.settlerCount = 15;
			break;
		case METROPOLIS :
			this.settlerCount = 15;
			break;
		case CLAIM :
			this.settlerCount = 0;
			break;
		default :
			this.settlerCount = 5;
			break;
		}
	}

	
	/**
	 * the value is a  signed field 
	 *  
	 * @param value withdraw from sttlerCount 
	 * @return  true if withdraw done otherwise false
	 */
	public Boolean withdrawSettler(int value)
	{
		if (settlerCount >= value)
		{
			settlerCount = settlerCount - value;
			return true;
		}
		return false;
	}
	
//	/**
//	 * the value is a  signed field 
//	 * @param value add to settlerCount
//	 * @return  new number of residents
//	 */
//	public int  depositSettler(int value)
//	{
//		settlerCount = settlerCount + value;
//		return settlerCount;
//	}
	
//	/**
//	 * 
//	 * @return  number of available workers
//	 */
//	public int getWorkerCount()
//	{
//		return workerCount;
//	}

//	/**
//	 * set the worker number  , no check for conditions 
//	 * @param workerCount
//	 */
//	public void setWorkerCount(int workerCount)
//	{
//		this.workerCount = workerCount;
//	}

	
	/**
	 * 
	 * @return  number of available cows
	 */
	public int getCowCount()
	{
		return cowCount;
	}

	/**
	 * set nummber of available cows  , no check for conditions 
	 * @param cowCount
	 */
	public void setCowCount(int cowCount)
	{
		this.cowCount = cowCount;
	}

	/**
	 * withdraw cows from count , the value is a  signed field 
	 * @param value to withdraw
	 * @return  true if withdraw doen otherwise false
	 */
	public boolean  withdrawCow(int value)
	{
		if (cowCount >= value)
		{
			cowCount = cowCount - value;
			return true;
		} 
		return false;
	}
	
	/**
	 * the value is a  signed field 
	 * @param value to added to vowCount
	 * @return new value of cowCount
	 */
	public int depositCow(int value)
	{
		cowCount = cowCount + value;
		return cowCount;
	}
	
	/**
	 * 
	 * @return number of available Horse
	 */
	public int getHorseCount()
	{
		return horseCount;
	}

	/**
	 * set number of available horses  , no check for conditions
	 * @param horseCount
	 */
	public void setHorseCount(int horseCount)
	{
		this.horseCount = horseCount;
	}
	
	
	/**
	 * withdraw horses from count , the value is a  signed field 
	 * @param value to withdraw
	 * @return  true if withdraw done otherwise false
	 */
	public Boolean withdrawHorse(int value)
	{
		if (horseCount >= value)
		{
			horseCount = horseCount - value;
			return true;
		}
		return false;
	}
	
	/**
	 * the value is a  signed field 
	 * @param value added to horseCount
	 * @return new value of horseCount
	 */
	public int depositHorse(int value)
	{
		horseCount = horseCount + value;
		return horseCount;
	}

	/**
	 * the fertilityCounter collect birthrate below 1 
	 * when he counter reaches 100 then the birthrate set for cycle to 1
	 * @param happinessDiff
	 */
	public void setFeritilityCounter(double happinessDiff)
	{
		fertilityCounter = fertilityCounter + happiness + happinessDiff;
	}

	/**
	 * 
	 * @return value of fertilityCounter
	 */
	public double getFertilityCounter()
	{
		return fertilityCounter;
	}
	
//	/**
//	 * set happiness value
//	 * @param value
//	 */
//	public void setHappiness(double value)
//	{
//		happiness = value;
//	}
	

	/**
	 * 
	 * @return  happiness
	 */
	public double getHappiness()
	{
		int counter = 0;
		for (NpcData npc : npcList.values())
		{
			if (npc.isAlive())
			{
				happiness = happiness + npc.getHappiness();
				counter++;
			}
		}
		happiness = happiness / counter;
		return happiness;
	}

	/**
	 * 
	 * @return  birthrate of settlers
	 */
	public int getBirthrate()
	{
		return settlerBirthrate;
	}
	
	/**
	 * 
	 * @return  deathrate of settlers
	 */
	public int getDeathrate()
	{
		return settlerDeathrate;
	}
	
	public static double getBaseHappines()
	{
		return BASE_HAPPINES;
	}

	/**
	 * calutae happines based on settlercount
	 * @param value
	 * @return
	 */
	public double calcResidentHappiness(double value)
	{
		double dif = 0.0;
		if (settlerMax <= 0)
		{
			return 0.0;
		}
		if ((settlerMax  - settlerCount) > 0 )
		{
			if (happiness < 5.0) // < 5.0
			{
				if (value < 2.5)
				{
					dif = ((double)((settlerMax  - settlerCount) / (double)settlerMax) / 10.0 );
				}
			}
		} else
		{
			if (happiness > -5.0)  // > -5.0
			{
				if (value > -2.5)
				{
					dif = ((double)((settlerMax  - settlerCount) / (double)settlerMax) / 6.0 );
				}
			}
		}
		value = value + dif;
		return value;
	}
	
	/**
	 * calculate birthrate increase based on happiness
	 * @param value
	 * @return  birthrate
	 */
	private double calcHappyFactor(double value)
	{
		double factor = 0.0;
		if (happiness > 1.5)
		{
			return value;
		} else
		{
			factor = (happiness - BASE_HAPPINES);
			return (value * factor);
		}
	}
	
	private boolean isContact()
	{
		double max = 2.0;
		int Dice = 400;
		double wuerfel = 0;

		wuerfel = (Math.random()*Dice+1);
		if (wuerfel < max)
		{
			return true;
		}
		return false;
	}
	
	
	private boolean isBreed(double fertWoman, double fertMan)
	{
		int Dice = 100;
		double wuerfel = 0;
		
//		System.out.print(" isBreed "+fertWoman+":"+fertMan);

		wuerfel = (Math.random()*Dice+1);
		if (wuerfel < fertWoman)
		{
			wuerfel = (Math.random()*Dice+1);
			if (wuerfel < fertMan)
			{
				return true;
			}	
		}	
		
		return false;
	}

	private double getFertilityMan(int age)
	{
		if (age > 60)
		{
			return 0;
		}
		double fert = ((60.0 - (double) age) * 2.0);
		return  fert;
	}
	
	private double getFertilityWoman(int age)
	{
		if (age > 50)
		{
			return 0;
		}
		double fert = ((50.0 - (double) age) * 2.5) + 8.0;
//		System.out.println(" Fert "+fert);
		return  fert;
	}
	
	public void checkBirthrate(NpcData woman, NpcData man)
	{
//		System.out.print("Birthrate Age "+woman.getAge());
		double fertWoman =  getFertilityWoman(woman.getAge()) * woman.getHappiness();
		double fertMan =  getFertilityMan(man.getAge()) * man.getHappiness();
		
		if (woman.getSchwanger() == 0)
		{
			if (isBreed(fertWoman, fertMan))
			{
				woman.setSchwanger(1);
				woman.setProducer(man.getId());
			}
		}
		
	}
	
	/**
	 * calculate birthrate for settlement based on 
	 * - npc woman
	 * - happiness
	 * - fertility 
	 * - randomNumber for loverContact
	 */
	private void doBirthrate()
	{
		NpcList womanNpc = npcList.getWoman(); //.getSettleWorker();
		
		for (NpcData woman : womanNpc.values())
		{
//			System.out.println("Woman "+woman.getId()+":"+woman.isMaried());
			if (woman.isMaried())
			{
				if (isContact())
				{
	//				System.out.println("isMaried "+woman.getId());
					NpcData man = npcList.get(woman.getNpcHusband());
					if (man != null)
					{
						if ((man.isAlive())
							&& (man.getHomeBuilding() == woman.getHomeBuilding())
							)
						{
//							System.out.println("CheckBirthrate");
							checkBirthrate(woman, man);
						}
					}
				}
			}
			if (woman.getSchwanger() > 0)
			{
				woman.addSchwanger(1);
				
			} else
			{
				if (woman.getSchwanger() < 0)
				{
					woman.addSchwanger(1);
				}
			}

		}
	}

	/**
	 * <pre>
	 * increment age by 1 day
	 * check for CHILD to be Settler
	 * immortal npc dont increment their age
	 * </pre> 
	 */
	private void doLifeCycle(BuildingList buildings, DataInterface data)
	{
		NpcList lifeList = new NpcList();
		for (NpcData npc : this.npcList.values())
		{
			lifeList.add(npc);
		}
		for (NpcData npc : lifeList.values())
		{
			if (npc.isAlive())
			{
				npc.addAgeDay();
				// check for new Status
				if (npc.hungerCounter < ConfigBasis.HUNGER_BEGGAR)
				{
					if (npc.isChild() == false)
					{
						if (npc.getMoney() < 0.3)
						{
							if (npc.isBeggar() == false)
							{
								System.out.println("[REALMS] Settler "+npc.getId()+" rankdown to BEGGAR ");
								npc.setNpcType(NPCType.BEGGAR);
							}
						}
					}
				} else
				{
					if (npc.isBeggar())
					{
						if (npc.hungerCounter >= 0.0)
						{
							if (npc.getHomeBuilding() != 0)
							{
								System.out.println("[REALMS] Beggar "+npc.getId()+" rankup to SETTLER ");
								npc.setNpcType(NPCType.SETTLER);
							}
						}
					}
				}
				if (npc.isChild())
				{
					if (npc.getAge() >= 14)
					{
						System.out.println("[REALMS] Child "+npc.getId()+" Growing to Settler with age "+npc.getAge());
						npc.setNpcType(NPCType.SETTLER);
						npc.depositMoney(10.0);
					}
				}
				if (npc.getId() == 2)
				{
					System.out.println("[REALMS] Schwanger "+npc.getId()+":"+npc.getSchwanger());
				}
				if (npc.isSchwanger())
				{
					if (npc.getSchwanger() > ConfigBasis.BREEDING_DAYS)
					{
						System.out.println("[REALMS] childbirth  "+npc.getId());
						Building building = buildings.getBuilding(npc.getHomeBuilding());
						if (building != null)
						{
							if (npcList.getBuildingNpc(building.getId()).size() < building.getSettler())
							{
								NpcData newChild = NpcData.makeChild( data.getNpcName(), npc.getProducer(), npc.getId());
								newChild.setNoble(npc.getNoble());
								newChild.setHomeBuilding(npc.getHomeBuilding());
								newChild.setSettleId(building.getSettleId());
								data.getNpcs().add(newChild);
								npcList.add(newChild);
								data.writeNpc(newChild);
								System.out.println("[REALMS] New Child "+newChild.getId()+" born into "+building.getId());
							} else
							{
								System.out.println("[REALMS] Baby is die ! "+npc.getId()+" Citizen "+npcList.getBuildingNpc(building.getId()).size()+" < "+building.getSettler());
							}
						}else
						{
							NpcData newChild = NpcData.makeChild( data.getNpcName(), npc.getProducer(), npc.getId());
							newChild.setHomeBuilding(0);
							newChild.setSettleId(0);
							data.getNpcs().add(newChild);
							npcList.add(newChild);
							data.writeNpc(newChild);
							System.out.println("[REALMS] New Child "+newChild.getId()+" born living as BEGGAR ");
						}
						npc.setSchwanger(ConfigBasis.BREEDING_DELAY);
					}
				}
				data.writeNpc(npc);
			}
		}
	}
	
	
	private boolean checkDeath(int age)
	{
		int Dice = 100;
		double wuerfel = 0;

		wuerfel = (Math.random()*Dice+1);
		if (wuerfel < ConfigBasis.LETHALITY)
		{
			wuerfel = (Math.random()*Dice+1);
			if (wuerfel < age)
			{
				return true;
			}	
		}	
		
		return false;
		
	}

	/**
	 * check for husband and set to 0
	 * 
	 * @param npc
	 */
	private void checkLegacy(NpcData npc)
	{
		if (npc.getNpcHusband() > 0)
		{
			NpcData husband = npcList.get(npc.getNpcHusband());
			if (husband != null)
			{
				if (husband.getNpcHusband() == npc.getHomeBuilding())
				{
					husband.setNpcHusband(0);
				}
			} 
				
		}
	}
	
	/**
	 * set npc to death, when he is not immortal !
	 * set happines to max for cooldown the husband
	 * @param npc
	 */
	private void doDeath(NpcData npc)
	{
		if (npc.isImmortal() == false)
		{
			npc.setName(npc.getName()+" +");
			npc.setAlive(false);
			npc.setHappiness(ConfigBasis.MAX_HAPPINESS);
			checkLegacy(npc);
		}
	}
	
	
	/**
	 * calculate deathrate for settlement based on
	 * - npc
	 * - age of npc
	 * - over 60 a random must be done 
	 * 
	 *  After deat the happines count down to 0
	 * - when happiness = the husband are cleared , a grief time 
	 * 
	 *  
	 */
	private void doDeathrate(DataInterface data)
	{

		for (NpcData npc : npcList.values())
		{
			if (npc.isAlive())
			{
				if (npc.getAge() >= ConfigBasis.NORMAL_AGE)
				{
//					System.out.println("CheckDeath "+npc.getAge());
					if (checkDeath(npc.getAge()) == true)
					{
						doDeath(npc);
					}
				}
				if (npc.hungerCounter < 0.0)
				{
					if (npc.getNpcType() == NPCType.CHILD)
					{
						if (npc.hungerCounter < ConfigBasis.HUNGER_LETHALITY_CHILD)
						{
							doDeath(npc);
							System.out.println("HungerTod "+npc.hungerCounter+":"+npc.getNpcType()+":"+npc.getId());
						}
					} else
					{
						if (npc.hungerCounter < ConfigBasis.HUNGER_LETHALITY)
						{
							doDeath(npc);
							System.out.println("HungerTod "+npc.hungerCounter+":"+npc.getNpcType()+":"+npc.getId());
						}
					}
				}
				if (npc.isAlive() == false)
				{
					data.writeNpc(npc);
				}
			} else
			{
				if (npc.getHappiness() > 0.2)
				{
					npc.setHappiness(npc.getHappiness() - 0.1);
				} else
				{
					if (npc.getNpcHusband() > 0)
					{
						NpcData husband = npcList.get(npc.getNpcHusband());
						if (husband != null)
						{
							if (husband.getNpcHusband() == npc.getHomeBuilding())
							{
								husband.setNpcHusband(0);
							}
						}
						npc.setHappiness(0.0);
						data.writeNpc(npc);
					} else
					{
						npc.setHappiness(0.0);
					}
				}
			}
		}
	}
	
	/**
	 * calulate settlerCount for the settlement
	 */
	public void doSettlerCalculation(BuildingList buildings, DataInterface data)
	{
		if (this.npcList != null)
		{
			doLifeCycle(buildings, data);
			doBirthrate();
			doDeathrate(data);
		}
//		settlerCount = settlerCount + settlerBirthrate - settlerDeathrate;
	}

	/**
	 * @return the npcList
	 */
	public NpcList getNpcList()
	{
		return npcList;
	}

	/**
	 * @param npcList the npcList to set
	 */
	public void setNpcList(NpcList npcList)
	{
		this.npcList = npcList;
	}

	
	public NpcData findRecrute()
	{
		for (NpcData npc : npcList.values())
		{
			if ((npc.getGender() != GenderType.WOMAN)
				&& (npc.isChild() == false)
				)
			{
				if (npc.isBeggar())
				{
					return npc;
				}
			}
		}
		System.out.println("No beggar ");
		for (NpcData npc : npcList.values())
		{
//			if (npc.getWorkBuilding() == 0)
			{
				if ((npc.getGender() != GenderType.WOMAN)
					&& (npc.isChild() == false)
					)
				{
					if ((npc.getNpcType() != NPCType.MANAGER)
						&& (npc.getNpcType() != NPCType.BUILDER)
						&& (npc.getNpcType() != NPCType.CRAFTSMAN)
						&&(npc.getNpcType() != NPCType.FARMER)
						&&(npc.getNpcType() != NPCType.MAPMAKER)
						)
					{
						return npc;
					}
				}
			}
		}
		System.out.println("No Settler ");

		return null;
	}
	
}
