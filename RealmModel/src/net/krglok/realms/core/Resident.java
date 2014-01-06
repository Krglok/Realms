package net.krglok.realms.core;

/**
 * hold the human  resources of a settlement
 * calculate birthrate and deathrate 
 * calculate happines based on settlerCount
 * @author windu
 *
 */
public class Resident
{
	private static double FERTILITY = 3.0;   //  % satz 
	private static double  LETHALITY= 3.0;   //  % satz
	private static final double BASE_HAPPINES = 0.5;   
	
	private int settlerMax;
	private int settlerBirthrate;
	private int settlerDeathrate;
	private double fertilityCounter ;
	
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
	
	/**
	 * calutae happines based on settlercount
	 * @param value
	 * @return
	 */
	public double calcResidentHappiness(double value)
	{
		double dif = 0.0;
		if (settlerMax > 0)
		{
	    	dif = ((settlerMax  - settlerCount) / settlerMax );
			value = value + dif;
		}
		if (value < 0.3)
		{
			value = 0.3;
		}
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
		factor = (happiness - BASE_HAPPINES);
		value = (value * factor);
		return value;
	}

	/**
	 * calculate deathrate bae�sed on unhappyfactor
	 * @param value
	 * @return
	 */
	private double calcUnhappyFactor(double value)
	{
		double factor = 0.0;
		factor = (BASE_HAPPINES - happiness);
		
		value = (value * factor) ;
		return value;
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
		}
		double factor = (settlerMax-settlerCount);
//		factor = factor / settlerMax;
		if (happiness > 0.5)
		{
			value = (settlerCount * FERTILITY/100)+(factor * calcHappyFactor(FERTILITY)/100);
		} else
		{
			value = (factor * calcHappyFactor(FERTILITY)/100);
		}
		if (fertilityCounter >= 100.0)
		{
			value = value +1;
			fertilityCounter = factor/10 ;
		} else
		{
			fertilityCounter = fertilityCounter + factor/10;
		}
		if (happiness < 0)
		{
			value = 0;
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
		if (settlerCount < 5)
		{
			settlerCount = 5;
		}
		double factor = 0.0;
		if (happiness > BASE_HAPPINES )
		{
			factor = 0;
		} else
		{
			if (happiness < 0)
			{
				factor = happiness-1;
			} else
			{
				factor = ((settlerMax-settlerCount) * LETHALITY/100)  + ((settlerMax-settlerCount) *calcUnhappyFactor(LETHALITY)/100);
			}
		}
		// deatrate !! not  > =
		settlerDeathrate = (int) factor;
	}
	
	/**
	 * calulate settlerCount for the settlement
	 */
	public void settlerCount()
	{
		setBirthrate();
		setDeathrate();
		settlerCount = settlerCount + settlerBirthrate + settlerDeathrate;
	}

	
}