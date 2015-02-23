package net.krglok.realms.manager;

import net.krglok.realms.core.LocationData;

public enum PositionFace
{

	NORTH,
	NORTHEAST,
	EAST,
	SOUTHEAST,
	SOUTH,
	SOUTHWEST,
	WEST,
	NORTHWEST;
	
	
	/**
	 * determine raider position on actual campposition
	 * 
	 * @param face
	 * @return
	 */
	public PositionFace getRaiderPos(PositionFace face)
	{
		switch (face)
		{
		case NORTHWEST: return NORTH;
		case NORTHEAST: return EAST;
		case SOUTHEAST: return SOUTH;
		case SOUTHWEST: return WEST;
		default: return NORTH;
			
		}
	}
	
	public static boolean contain(String value)
	{
		for (PositionFace face : PositionFace.values())
		{
			if (face.name().equals(value))
			{
				return true;
			}
		}
		return false;
	}
	
	
	public static String valueHelp()
	{
		String s = "";
		for (PositionFace face : PositionFace.values())
		{
			s = s + face.name()+"|";
		}
		return s;
	}
	
	/**
	 * calculate Position based on face, with angel of 45 degree
	 * 
	 * @param face
	 * @param position
	 * @param range
	 * @return  new Location
	 */
	public static LocationData getScanPos(PositionFace face, LocationData position, int range)
	{
		LocationData newPosition = LocationData.copyLocation(position);
		switch(face)
		{
		case NORTH :
			newPosition.setZ(newPosition.getZ()-range);
			newPosition.setX(newPosition.getX()+range);
			break;
		case EAST:
			newPosition.setZ(newPosition.getZ()+range);
			newPosition.setX(newPosition.getX()+range);
			break;
		case SOUTH:
			newPosition.setZ(newPosition.getZ()+range);
			newPosition.setX(newPosition.getX()-range);
			break;
		case WEST:
			newPosition.setZ(newPosition.getZ()-range);
			newPosition.setX(newPosition.getX()-range);
			break;
		default:
			 break;
		}
		return newPosition;
	}
	
}
