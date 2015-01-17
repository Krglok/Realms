package net.krglok.realms.core;

import java.io.Serializable;


/**
 * Independent  location definition, seperate the model from minecraft server
 * @author Windu
 *
 */
public class LocationData  implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5829724671027753674L;
	private String world;
    private double posX;
    private double posY;
    private double posZ;

//    public LocationData()
//    {
//    	world = "";
//    	posX = 0.0;
//    	posY = 
//    }

	public LocationData(String world, double posX, double posY, double posZ)
	{
		super();
		this.world = world;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	public String getWorld()
	{
		return world;
	}

	public void setWorld(String world)
	{
		this.world = world;
	}

	public double getX()
	{
		return posX;
	}

	public void setX(double posX)
	{
		this.posX = posX;
	}

	public double getY()
	{
		return posY;
	}

	public void setY(double posY)
	{
		this.posY = posY;
	}

	public double getZ()
	{
		return posZ;
	}

	public void setZ(double posZ)
	{
		this.posZ = posZ;
	}
	
	/**
	 * berechnet die 2D entfernung zum uebergebenen Punkt
	 * @param loc
	 * @return
	 */
	public double distance2D(LocationData loc)
	{
		double x1 = deltaX(loc); //loc.posX - this.posX;
		double z1 = deltaZ(loc); //loc.posZ - this.posZ;
		double d2 = Math.sqrt((x1*x1)+(z1*z1));
		return  d2;
	}
    
    public double distance(LocationData loc)
    {
    	double y1 = deltaY(loc); //loc.posY - this.posY;
    	double d2 = distance2D(loc);
    	double d3 = Math.sqrt((d2*d2)+(y1*y1)); 
		return  d3;   	
    }
    
    public double deltaX(LocationData loc)
    {
		return Math.abs(loc.posX - this.posX);
    	
    }
    
    public double deltaZ(LocationData loc)
    {
		return Math.abs(loc.posZ - this.posZ);
    	
    }
    
    public double deltaY(LocationData loc)
    {
    	return Math.abs(loc.posY - this.posY);    	
    }
 
    /**
     * erzeugt eine Location als String
     * World:X:Y:Z
     * @param position
     * @return
     */
    public static String toString(LocationData position)
    {
    	if (position.getWorld() == null)
    	{
    		position.world = "";
    	}
    	String sLocation = position.getWorld()+":"+String.valueOf(position.getX())+":"+String.valueOf(position.getY())+":"+String.valueOf(position.getZ()); 
    	return sLocation ;
    }
    
    /**
     * Erzeugt aus einem String eine Location
     * error Handling , erzeugt  Location mit "", 0.0, 0.0, 0.0
     * @param locationString
     * @return
     */
    public static LocationData toLocation(String locationString)
    {
        if (locationString != null) 
        {
        	try
			{
                String[] params = locationString.split(":");
                return new LocationData(params[0], Double.parseDouble(params[1]),Double.parseDouble(params[2]),Double.parseDouble(params[3]));
				
			} catch (Exception e)
			{
		    	return new LocationData("", 0.0, 0.0, 0.0); 
			}
        }
    	return new LocationData("", 0.0, 0.0, 0.0); 
    }
    
}

