package net.krglok.realms.tool;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.krglok.realms.core.LocationData;

public class SuperRegionData
{
    private String name;
    private LocationData l;
    private String type;
    private Map<String, List<String>> members;
    private List<String> owners;
    private int power;
    private double taxes = 0;
    private double balance = 0;
    private LinkedList<Double> taxRevenue;
    private int maxPower;
    
	public SuperRegionData()
	{
		super();
		this.name = "";
		this.l = new LocationData("",0.0,0.0,0.0);
		this.type = "";
		this.members = null;
		this.owners = null;
		this.power = 0;
		this.taxes = 0.0;
		this.balance = 0.0;
		this.taxRevenue = null;
		this.maxPower = 0;
	}
	
    public SuperRegionData(String name, LocationData l, String type, List<String> owner, Map<String, List<String>> members, int power, double taxes, double balance, LinkedList<Double> taxRevenue, int maxPower) 
	{
		super();
		this.name = name;
		this.l = l;
		this.type = type;
		this.members = members;
		this.owners = owner;
		this.power = power;
		this.taxes = taxes;
		this.balance = balance;
		this.taxRevenue = taxRevenue;
		this.maxPower = maxPower;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public LocationData getL()
	{
		return l;
	}
	public void setL(LocationData l)
	{
		this.l = l;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public Map<String, List<String>> getMembers()
	{
		return members;
	}
	public void setMembers(Map<String, List<String>> members)
	{
		this.members = members;
	}
	public List<String> getOwners()
	{
		return owners;
	}
	public void setOwners(List<String> owners)
	{
		this.owners = owners;
	}
	public int getPower()
	{
		return power;
	}
	public void setPower(int power)
	{
		this.power = power;
	}
	public double getTaxes()
	{
		return taxes;
	}
	public void setTaxes(double taxes)
	{
		this.taxes = taxes;
	}
	public double getBalance()
	{
		return balance;
	}
	public void setBalance(double balance)
	{
		this.balance = balance;
	}
	public LinkedList<Double> getTaxRevenue()
	{
		return taxRevenue;
	}
	public void setTaxRevenue(LinkedList<Double> taxRevenue)
	{
		this.taxRevenue = taxRevenue;
	}
	public int getMaxPower()
	{
		return maxPower;
	}
	public void setMaxPower(int maxPower)
	{
		this.maxPower = maxPower;
	}

    
	
}
