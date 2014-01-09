package net.krglok.realms.core;

public class BoardItem
{
	private String name;
	private Double lastValue;
	private int cycleCount;
	private Double cycleSum;
	private int periodCount;
	private Double periodSum;
	
	public BoardItem(String name)
	{
		this.name 	= name;
		lastValue 	= 0.0;
		cycleCount	= 0;
		cycleSum  	= 0.0;
		periodCount = 0;
		periodSum	= 0.0;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getLastValue()
	{
		return lastValue;
	}

	public void setLastValue(Double lastValue)
	{
		this.lastValue = lastValue;
	}

	public int getCycleCount()
	{
		return cycleCount;
	}

	public void setCycleCount(int cycleCount)
	{
		this.cycleCount = cycleCount;
	}

	public double getCycleSum()
	{
		return cycleSum;
	}

	public void setCycleSum(Double cycleSum)
	{
		this.cycleSum = cycleSum;
	}

	public int getPeriodCount()
	{
		return periodCount;
	}

	public void setPeriodCount(int periodCount)
	{
		this.periodCount = periodCount;
	}

	public double getPeriodSum()
	{
		return periodSum;
	}

	public void setPeriodSum(Double periodSum)
	{
		this.periodSum = periodSum;
	}
	
}
