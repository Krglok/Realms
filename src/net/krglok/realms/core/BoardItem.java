package net.krglok.realms.core;

/**
 * a row of a statistic
 * 
 * @author Windu
 *
 */
public class BoardItem
{
	private String name;
	private Double inputValue;
	private Double inputSum;
	private Double periodSum;
	private Double outputValue;
	private Double outputSum;
	
	public BoardItem(String name)
	{
		this.name 	= name;
		inputValue 	= 0.0;
		inputSum  	= 0.0;
		periodSum	= 0.0;
		setOutputValue(0.0);
		setOutputSum(0.0);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getInputValue()
	{
		return inputValue;
	}

	public void setInputValue(Double lastValue)
	{
		this.inputValue = lastValue;
	}


	public double getInputSum()
	{
		return inputSum;
	}

	public void setInputSum(Double cycleSum)
	{
		this.inputSum = cycleSum;
	}


	public double getPeriodSum()
	{
		return periodSum;
	}

	public void setPeriodSum(Double periodSum)
	{
		this.periodSum = periodSum;
	}

	/**
	 * @return the outputValue
	 */
	public Double getOutputValue()
	{
		return outputValue;
	}

	/**
	 * @param outputValue the outputValue to set
	 */
	public void setOutputValue(Double outputValue)
	{
		this.outputValue = outputValue;
	}

	/**
	 * @return the outputSum
	 */
	public Double getOutputSum()
	{
		return outputSum;
	}

	/**
	 * @param outputSum the outputSum to set
	 */
	public void setOutputSum(Double outputSum)
	{
		this.outputSum = outputSum;
	}
	
}
