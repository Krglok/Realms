package net.krglok.realms.core;

public class BoardItem
{
	private String name;
	private Double lastValue;
	private Double cycleSum;
	private Double periodSum;
	
	public BoardItem(String name)
	{
		this.name 	= name;
		lastValue 	= 0.0;
		cycleSum  	= 0.0;
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


	public double getCycleSum()
	{
		return cycleSum;
	}

	public void setCycleSum(Double cycleSum)
	{
		this.cycleSum = cycleSum;
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
