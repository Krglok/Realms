package net.krglok.realms.gui;

import net.krglok.realms.core.ConfigBasis;

public class DataRecord
{
    protected String name;
    protected String amount;
    protected String value;

    public DataRecord() {
        name = "";
        amount = "";
        value = "";
    }

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAmount()
	{
		return amount;
	}

	public void setAmount(String amount)
	{
		this.amount = amount;
	}

	public void setAmount(int amount)
	{
		this.amount =  String.valueOf(amount);
	}

	public void setAmount(double amount)
	{
		this.amount =  String.valueOf(ConfigBasis.format2(amount));
	}
	
	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public void setValue(double value)
	{
		this.value = String.valueOf(ConfigBasis.format2(value));
	}

	public void setValue(int value)
	{
		this.value = String.valueOf(value);
	}
	
}
