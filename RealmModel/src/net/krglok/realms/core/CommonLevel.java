package net.krglok.realms.core;

/**
 * these are the none noble level of owner
 * 
 * @author Windu
 *
 */
public enum CommonLevel
{

	COLONIST,
	SETTLER,
	MAYOR,
	COUNCILOR,
	SENATOR;
	
	public CommonLevel rankUp(CommonLevel value)
	{
		switch(value)
		{
		case COLONIST:
			return SETTLER;
		case SETTLER:
			return MAYOR;
		case MAYOR:
			return SENATOR;
		default:
			return value;
		}
	}
	
	public CommonLevel rankDown(CommonLevel value)
	{
		switch(value)
		{
		case SENATOR:
			return MAYOR;
		case MAYOR:
			return SETTLER;
		case SETTLER:
			return COLONIST;
		default:
			return value;
		}
	}
	
}
