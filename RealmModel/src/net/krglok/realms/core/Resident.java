package net.krglok.realms.core;

import java.io.Serializable;

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
	private static double FERTILITY = 3.0;   //  % satz 
	private static double  LETHALITY= 1.0;   //  % satz
	private static final double BASE_HAPPINES = 0.5;   
	
	private int settlerMax;
	private int settlerBirthrate;
	private int settlerDeathrate;
	private double fertilityCounter ;
	private double deathCounter = 0.0;
	
	private int settlerCount;
//	private int workerCount;
	private int cowCount;
	private int horseCount;

	private double happiness ;
	
	public Resident()
	{
		fertilityCounter = 100.0;
		happiness   = 0.6;
		settlerMax  = 0;
		settlerCount = 0;
//		workerCount  = 0;
		cowCount     = 0;
		horseCount = 0;
		
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
	 * 
	 * @return  number of residents in settlement
	 */
	public int getSettlerCount()
	{
		return settlerCount;
	}

	/**
	 * Set resident number , no check for conditions 
	 * @param residentCount
	 */
	public void setSettlerCount(int residentCount)
	{
		this.settlerCount = residentCount;
	}

	public void setDefaultSettlerCount(SettleType settleType)
	{
		switch(settleType)
		{
		case HAMLET :
			this.settlerCount = 15;
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
	
	/**
	 * the value is a  signed field 
	 * @param value add to settlerCount
	 * @return  new number of residents
	 */
	public int  depositSettler(int value)
	{
		settlerCount = settlerCount + value;
		return settlerCount;
	}
	
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
	
	/**
	 * set happiness value
	 * @param value
	 */
	public void setHappiness(double value)
	{
		happiness = value;
	}
	

	/**
	 * 
	 * @return  happiness
	 */
	public double getHappiness()
	{
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

	/**
	 * calculate birthrate for settlement based on 
	 * - settlerMax
	 * - FERTILITY
	 * - happiness
	 * - fertilityCounter for birthrate below 1 
	 * birthrate = 0 below BASE_HAPPINESS
	 */
	private void setBirthrate()
	{
		// guaranted minimum settler
		double value = 0.0;
		if (settlerCount < 5)
		{
			settlerCount = 5;
			settlerBirthrate = 0;
			
		}
		if (happiness < 0.0)
		{
			value  = 0;
		} else
		{
			if (fertilityCounter >= FertilityCounter_Limit)
			{
				value = value + 1.0;
				fertilityCounter = 0.0; //fertilityCounter - FertilityCounter_Limit;
			} else
			{
				double baseFertility = (FERTILITY / 3);
				if ((settlerMax-settlerCount) > 0)
				{
					if (happiness > BASE_HAPPINES)
					{
						fertilityCounter = fertilityCounter + baseFertility + (FERTILITY/100 * (settlerCount/2))  ;
					} else
					{
						fertilityCounter = fertilityCounter + baseFertility + (FERTILITY/2 /100 * (settlerCount/2)) ;
					}
				} else
				{
					fertilityCounter = fertilityCounter + baseFertility + (FERTILITY/2 /100 * (settlerCount/2)); 
					
				}
			}
		}		
		settlerBirthrate = (int) value;
		
	}

	/**
	 * calculate deathrate for settlement based on
	 * - settlerMAx
	 * - LETHALITY 
	 * - happiness
	 * no deathrate above BASE_HAPPINESS
	 * extreme deathrate for happiness below 0 
	 */
	private void setDeathrate()
	{
		// guaranted minimum settler
		double value = 0.0;
		if (settlerCount <= 5)
		{
			settlerCount = 5;
			settlerDeathrate = 0;
			return;
		}
		double factor = 0.0;
		value = (double)(settlerCount) * LETHALITY / 100.0;
		if (happiness > BASE_HAPPINES )
		{
			value  = 0;
		} else
		{
			if (happiness < 0)
			{
				factor = value;  // 
				value = value + factor;
				deathCounter = deathCounter +  19.0;  //(double)(settlerCount) *LETHALITY; // - happiness;
			} else
			{
//				value = value / 2;
			}
		}
		if (deathCounter >= 100.0)
		{
			value = value + 1;
			deathCounter = 0.0; //deathCounter -100.0;
		} else
		{
			deathCounter = deathCounter + 0.3;
		}
		// deatrate !! not  > =
		settlerDeathrate = (int) value;
	}
	
	/**
	 * calulate settlerCount for the settlement
	 */
	public void settlerCalculation()
	{
		setBirthrate();
		setDeathrate();
		settlerCount = settlerCount + settlerBirthrate - settlerDeathrate;
	}

	
}
