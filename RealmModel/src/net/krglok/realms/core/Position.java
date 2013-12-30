package net.krglok.realms.core;

public class Position
{

	private double x;
	private double y;
	private double z;
	
	
	/**
	 * initialize with 0,0,0
	 */
	public Position()
	{
		x = 0;
		y = 0;
		z = 0;
	}

	

	public Position(double x, double y, double z)
	{
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}



	public double getX()
	{
		return x;
	}


	public void setX(double x)
	{
		this.x = x;
	}


	public double getY()
	{
		return y;
	}


	public void setY(double y)
	{
		this.y = y;
	}


	public double getZ()
	{
		return z;
	}


	public void setZ(double z)
	{
		this.z = z;
	}
	
	public String toString()
	{
		return String.valueOf(x)+","+String.valueOf(y)+","+String.valueOf(z);
	}
}
