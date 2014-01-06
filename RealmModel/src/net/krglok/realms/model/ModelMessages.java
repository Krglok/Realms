package net.krglok.realms.model;

import net.krglok.realms.core.UnitType;

/**
 * 
 * @author oduda
 * generate messages for the model 
 *
 */
public class ModelMessages 
{
	
	public String getUnitTypeName(UnitType index)
	{
		switch(index)
		{
		case UNIT_WORKER:  return "Worker";
		case UNIT_COW:  return "Cow";
		case UNIT_HORSE:  return "Horse";
		case UNIT_WAGON:  return "Wagon";
		case UNIT_MILITIA:  return "Militia";
		case UNIT_SCOUT:  return "Scout";
		case UNIT_ARCHER:  return "Archer";
		case UNIT_LINF:  return "Light Infantry";
		case UNIT_HINF:  return "Heavy Infantry";
		case UNIT_KNIGHT:  return "Knight";
		case UNIT_COMMANDER:  return "Commander";
		default:
			return "None";
		}
	}
	

}
