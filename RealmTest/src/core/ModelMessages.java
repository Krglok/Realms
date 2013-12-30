package core;

/**
 * 
 * @author oduda
 *
 */
public class ModelMessages 
{
	
	public String getUnitTypeName(UnitType index)
	{
		switch(index.getValue())
		{
		case UnitType.UNIT_WORKER:  return "Worker";
		case UnitType.UNIT_COW:  return "Cow";
		case UnitType.UNIT_HORSE:  return "Horse";
		case UnitType.UNIT_WAGON:  return "Wagon";
		case UnitType.UNIT_MILITIA:  return "Militia";
		case UnitType.UNIT_SCOUT:  return "Scout";
		case UnitType.UNIT_ARCHER:  return "Archer";
		case UnitType.UNIT_LINF:  return "Light Infantry";
		case UnitType.UNIT_HINF:  return "Heavy Infantry";
		case UnitType.UNIT_KNIGHT:  return "Knight";
		case UnitType.UNIT_COMMANDER:  return "Commander";
		}
		return "None";
	}
	

}
