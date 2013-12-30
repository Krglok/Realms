package core;

/**
 * 
 * @author oduda
 *
 */
public class UnitType 
{
	final static int UNIT_NONE    = -1;
	final static int UNIT_WORKER  = 1;
	final static int UNIT_COW     = 10;
	final static int UNIT_HORSE   = 20;
	final static int UNIT_WAGON   = 30;
	final static int UNIT_MILITIA = 100;
	final static int UNIT_SCOUT   = 110;
	final static int UNIT_ARCHER  = 120;
	final static int UNIT_LINF    = 130;
	final static int UNIT_HINF    = 140;
	final static int UNIT_KNIGHT  = 150;
	final static int UNIT_COMMANDER = 200;

	private int value;
	
	public UnitType()
	{
		setValue(UNIT_NONE);
	}

	/**
	 * Get the actual Value of the UnitType
	 * @return
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Set integer to the actual Value of the UnitType
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Set Name to actuel Value of UnitType
	 * on error UNIT_NONE will set
	 * @param name
	 */
	public void setValue(String name) 
	{
		this.value = toValue(name);
	}
	
	/**
	 * return integer as NameString equal to Konstantname
	 * on error UNIT_NONE will set
	 * @param index
	 * @return String
	 */
	public String toString(int index)
	{
		switch(index)
		{
		case UNIT_NONE : return "UNIT_NONE";
		case UNIT_WORKER  : return "UNIT_WORKER";
		case UNIT_COW : return "UNIT_COW";
		case UNIT_HORSE : return "UNIT_HORSE";
		case UNIT_WAGON : return "UNIT_WAGON";
		case UNIT_MILITIA : return "UNIT_MILITIA";
		case UNIT_SCOUT : return "UNIT_SCOUT";
		case UNIT_ARCHER : return "UNIT_ARCHER";
		case UNIT_LINF : return "UNIT_LINF";
		case UNIT_HINF : return "UNIT_HINF";
		case UNIT_KNIGHT : return "UNIT_KNIGHT";
		case UNIT_COMMANDER : return "UNIT_COMMANDER";
		}
		return "";
	}
	
	/**
	 * return integer Value of NameString
	 * on error UNIT_NONE (-1) will set
	 * @param name
	 * @return Integer
	 */
	public int toValue(String name)
	{
		if (name.contains("NONE")) { return UNIT_NONE; }
		if (name.contains("WORKER")) { return UNIT_WORKER; }
		if (name.contains("WORK")) { return UNIT_WORKER; }
		if (name.contains("COW")) { return UNIT_COW; }
		if (name.contains("HORSE")) { return UNIT_HORSE; }
		if (name.contains("WAGON")) { return UNIT_WAGON; }
		if (name.contains("MILITIA")) { return UNIT_MILITIA; }
		if (name.contains("SCOUT")) { return UNIT_SCOUT; }
		if (name.contains("ARCHER")) { return UNIT_ARCHER; }
		if (name.contains("LINF")) { return UNIT_LINF; }
		if (name.contains("LIGHT")) { return UNIT_LINF; }
		if (name.contains("HINF")) { return UNIT_HINF; }
		if (name.contains("HEAVY")) { return UNIT_HINF; }
		if (name.contains("KNIGHT")) { return UNIT_KNIGHT; }
		if (name.contains("COMMANDER")) { return UNIT_COMMANDER; }
		return UNIT_NONE;
	}

}
