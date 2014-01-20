package net.krglok.realms.tool;

import java.util.ArrayList;

import multitallented.redcastlemedia.bukkit.herostronghold.region.RegionType;
import net.krglok.realms.unittest.RegionConfig;

import org.bukkit.Location;

public class RegionData
{
    private int id;
    private String world;
    private double posX;
    private double posY;
    private double posZ;
    private String type;
    private ArrayList<String> owners;
    private ArrayList<String> members;
    

    public RegionData(int id, String world, double posX, double posY,
			double posZ, String type, ArrayList<String> owners,
			ArrayList<String> members)
	{
		super();
		this.id = id;
		this.world = world;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.type = type;
		this.owners = owners;
		this.members = members;
	}

    public RegionData(int id, String locationString, 
    		String type, ArrayList<String> owners,
			ArrayList<String> members)
	{
		super();
		this.id = id;
        String[] params = locationString.split(":");
		this.world = params[0];
		this.posX = Double.parseDouble(params[1]);
		this.posY = Double.parseDouble(params[2]);
		this.posZ = Double.parseDouble(params[3]);
		this.type = type;
		this.owners = owners;
		this.members = members;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getWorld()
	{
		return world;
	}

	public void setWorld(String world)
	{
		this.world = world;
	}

	public double getPosX()
	{
		return posX;
	}

	public void setPosX(double posX)
	{
		this.posX = posX;
	}

	public double getPosY()
	{
		return posY;
	}

	public void setPosY(double posY)
	{
		this.posY = posY;
	}

	public double getPosZ()
	{
		return posZ;
	}

	public void setPosZ(double posZ)
	{
		this.posZ = posZ;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public ArrayList<String> getOwners()
	{
		return owners;
	}

	public void setOwners(ArrayList<String> owners)
	{
		this.owners = owners;
	}

	public ArrayList<String> getMembers()
	{
		return members;
	}

	public void setMembers(ArrayList<String> members)
	{
		this.members = members;
	}
    
	public LocationData getLocationData()
	{
		return new LocationData(world, posX, posY, posZ);
	}

	public LocationData getLocation()
	{
		return new LocationData(world, posX, posY, posZ);
	}
	
	public void setLocationData(LocationData location)
	{
		this.world = location.getWorld();
		this.posX  = location.getX();
		this.posY  = location.getY();
		this.posZ  = location.getZ();
	}
	
	/**
	 * Sucht alle Regions aus der Liste innerhalb des Radius
	 * @param loc
	 * @param rList
	 * @param radius
	 * @return
	 */
    public static ArrayList<RegionData> getContainingRegions(LocationData loc, ArrayList<RegionData> rList, int radius) 
    {
        ArrayList<RegionData> tempList = new ArrayList<RegionData>();
        double x = loc.getX();
        double y = loc.getY();
        y = y < 0 ? 0 : y;
//        y = y > loc.getWorld().getMaxHeight() ? loc.getWorld().getMaxHeight() : y;
        double z = loc.getZ();
        for (RegionData r : rList) 
        {
            try 
            {
                LocationData l = r.getLocation();
                double dX = loc.deltaX(l);
                double dZ = loc.deltaZ(l);
                if ((dX <= radius) && (dZ <= radius))
                {
                	 tempList.add(r);
                }
//                if (l.getX() + radius < x) 
//                {
//                    break;
//                }
//                if (l.getX() - radius < x 
//                		&& l.getY() + radius > y 
//                		&& l.getY() - radius < y 
//                		&& l.getZ() + radius > z 
//                		&& l.getZ() - radius < z 
//                		&& l.getWorld().equals(loc.getWorld())
//                		) 
//                {
//                    tempList.add(r);
//                }
            } catch (NullPointerException npe) 
            {
                System.out.println("Region " + r.getId() + " is corrupted");
            }
        }
        return tempList;
    }
	
    private RegionConfig getRegionConfig(ArrayList<RegionConfig> rConfigList, String regionType)
    {
    	for (RegionConfig rc : rConfigList)
    	{
    		if (rc.getName().equalsIgnoreCase(regionType))
    		{
    			return rc; 
    		}
    	}
    	
    	return null;
    }
    
    /**
     * Find all Region that are not Overlap to the given Radius
     * @param loc
     * @param rList
     * @param rConfigList
     * @param radius
     * @return
     */
    public ArrayList<RegionData> getNotOverlapRegions(
    		LocationData loc, 
    		ArrayList<RegionData> rList, 
    		ArrayList<RegionConfig> rConfigList, 
    		int radius
    		) 
    {
        ArrayList<RegionData> tempList = new ArrayList<RegionData>();
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        for (RegionData r : rList) 
        {
            try 
            {
            	double r2 = getRegionConfig(rConfigList, r.getType()).getRadius();
                double dist = radius + r2;
                LocationData l = r.getLocation();
                double dX = loc.deltaX(l);
                double dZ = loc.deltaZ(l);
                if ((dX >= dist) && (dZ >= dist))
                {
                	 tempList.add(r);
                }
                
            } catch (NullPointerException npe) {
            	System.out.println("Region " + r.getId() + " is corrupted");
            }
        }
        return tempList;
    }

    /**
     * 
     * @param loc
     * @param rList
     * @param rConfigList
     * @param radius
     * @return
     */
    public ArrayList<RegionData> getOverlapRegions(
    		LocationData loc, 
    		ArrayList<RegionData> rList, 
    		ArrayList<RegionConfig> rConfigList, 
    		int radius
    		) 
    {
        ArrayList<RegionData> tempList = new ArrayList<RegionData>();
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        for (RegionData r : rList) 
        {
            try 
            {
            	double r2 = getRegionConfig(rConfigList, r.getType()).getRadius();
                double dist = radius + r2;
                LocationData l = r.getLocation();
                double dX = loc.deltaX(l);
                double dZ = loc.deltaZ(l);
                if ((dX < dist) && (dZ < dist))
                {
                	 tempList.add(r);
                }
                
            } catch (NullPointerException npe) {
            	System.out.println("Region " + r.getId() + " is corrupted");
            }
        }
        return tempList;
    }
    
    public RegionData getClosestRegionType(LocationData loc, String type, ArrayList<RegionData> rList) 
    {
        RegionData re = null;
        double distance = 999999999;
        for (RegionData r : rList) {
            try {
                LocationData l = r.getLocation();
                if (r.getType().equalsIgnoreCase(type) &&
                    l.getWorld().equals(loc.getWorld())) {
                    double tempDistance=r.getLocation().distance(loc);
                    if (tempDistance < distance) {
                        distance=tempDistance;
                        re=r;
                    } else {
                        break;
                    }
                    
                }
            } catch (NullPointerException npe) {
            	System.out.println("Region " + r.getId() + " is corrupted");
            }
        }
        return re;
    }
    
}
