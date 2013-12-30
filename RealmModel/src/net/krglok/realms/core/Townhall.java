package net.krglok.realms.core;

public class Townhall
{
	private Boolean isEnabled;
	private int workerNeeded;
	private int workerCount;
	
	public Townhall()
	{
		workerCount = 0;
		workerNeeded = 0;
		isEnabled = false;
	}

	public Townhall(boolean isEnabled)
	{
		workerCount = 0;
		workerNeeded = 0;
		this.isEnabled = isEnabled;
		if (isEnabled)
		{
			workerCount =  5;
		}
	}
	
	public Boolean getIsEnabled()
	{
		return isEnabled;
	}

	/**
	 * 
	 * Set the enabled Status , no check for conditions
	 * @param isEnabled
	 */
	public void setIsEnabled(Boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}

	public int getWorkerNeeded()
	{
		return workerNeeded;
	}

	public void setWorkerNeeded(int workerNeeded)
	{
		this.workerNeeded = workerNeeded;
	}

	public int getWorkerCount()
	{
		return workerCount;
	}

	public void setWorkerCount(int workerCount)
	{
		this.workerCount = workerCount;
	}
	/**
	 * withdraw worker from count , the value is a  signed field 
	 * @param value to withdraw
	 * @return  true if withdraw doen otherwise false
	 */
	public Boolean withdrawWorker(int value)
	{
		if (workerCount >= value)
		{
			workerCount = workerCount - value;
			return true;
		}
		return false;
	}

	/**
	 * the value is a  signed field 
	 * @param value added to workerCount
	 * @return new value of workerCount
	 */
	public int depositWorker(int value)
	{
		workerCount = workerCount + value;
		return workerCount;
	}
	
}
