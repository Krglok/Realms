package net.krglok.realms.manager;

import org.bukkit.Material;

import net.krglok.realms.core.LocationData;

public class HeightAnalysis
{

	private int radius = 10;
	private int edge = radius + radius -1;
	private int highSum = 0;
	private int counter = 0; 
	private int min = 256;
	private int max = 1;
	private int start =0;
	private boolean isGround;
	private boolean isLava;
	private LocationData position;
	private boolean isRegion= false;
	private boolean isSettlement = false;
	
	public HeightAnalysis(int radius)
	{
		this.radius = radius;
		initHeightAnalysis();
	}
	
	public void initHeightAnalysis()
	{
		edge = radius + radius -1;
		start = 0;
		counter = 0;
		highSum = 0;
		min = 256;
		max = 1;
		isGround = false;
		isLava = false;
		position = new LocationData("", 0.0, 0.0, 0.0);
	}
	
	public void setStart(int value)
	{
		this.start = value;
	}
	
	public int getStart()
	{
		return this.start;
	}
	
	public void addValue(int value)
	{
		highSum = highSum + value;
		counter++;
		if (value > max)
		{
			max = value;
		}
		if (value < min)
		{
			min = value;
		}
	}
	
	/**
	 * 
	 * @return average of heigtMap values
	 */
	public int getAverage()
	{
		if (highSum > 0)
		{
			return (highSum / counter);
		}
		return 0;
	}
	
	public int getMin()
	{
		return min;
	}
	
	public int getMax()
	{
		return max;
	}
	
	public int scale()
	{
		return counter * 100 / (edge*edge);
	}
	
	public int getCounter()
	{
		return counter;
	}
	
	public int getEdge()
	{
		return edge;
	}
	
	public boolean isGround()
	{
		return isGround;
	}
	
	public void setIsGround(boolean value)
	{
		this.isGround = value;
	}
	
	/**
	 * store a copy (clone) of the locationData
	 * @param pos
	 */
	public void setPosition(LocationData pos)
	{
		this.position = LocationData.copyLocation(pos);
	}
	
	/**
	 * 
	 * @return a copy (clone) of the LocationData
	 */
	public LocationData getPosition()
	{
		return LocationData.copyLocation(this.position);
	}

	public boolean checkGround(Material mat)
	{
		switch(mat)
		{
		case LOG : return false;
		case LOG_2 : return false;
		case LEAVES : return false;					
		case LEAVES_2 : return false;
		case AIR : return false;
		case WATER : return false;
		case STATIONARY_WATER: return false;
		case LAVA: 
		case STATIONARY_LAVA: 
			isLava = true;
			return false;
		default:
			return true;
		}
	}
	
	/**
	 * <pre>
	 * make a standard analysis on the HeighAnalysis
	 * - Height difference < 5
	 * - Delta Average/Start < 3
	 * - Area scale > 25 %
	 * - isGround true
	 * - NO Lava in range, detected with  checkGround
	 *  
	 * @return  true if parameters fullfil
	 * </pre>
	 */
	public boolean isValid()
	{
		if (isLava == true)
		{
			return false;
		}
		if (isGround == false)
		{
			return false;
		}
		if ((max - min) > 5)
		{
			return false;
		}
		if (Math.abs(getAverage() - start) > 2)
		{
			return false;
		}
		if (scale() < 25)
		{
			return false;
		}
		if (isRegion)
		{
			return false;
		}
		if (isSettlement)
		{
			return false;
		}
			
		return true;
	}

	public int getRadius()
	{
		return radius;
	}

	public void setRadius(int radius)
	{
		this.radius = radius;
	}

	public boolean isLava()
	{
		return isLava;
	}

	/**
	 * @return the isRegion
	 */
	public boolean isRegion()
	{
		return isRegion;
	}

	/**
	 * @param isRegion the isRegion to set
	 */
	public void setRegion(boolean isRegion)
	{
		this.isRegion = isRegion;
	}

	/**
	 * @return the isSettlement
	 */
	public boolean isSettlement()
	{
		return isSettlement;
	}

	/**
	 * @param isSettlement the isSettlement to set
	 */
	public void setSettlement(boolean isSettlement)
	{
		this.isSettlement = isSettlement;
	}
	
}
