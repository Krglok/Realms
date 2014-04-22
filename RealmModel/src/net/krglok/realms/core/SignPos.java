package net.krglok.realms.core;

/**
 * hold the registered signs of a settlement as List of objects
 * - BuildingId
 * - position
 * - text[4]
 * The standard sign text are structured
 *  0 = BuildingType
 *  1 = Status (enable/disable)
 *  2 = free
 *  3 = free
 *  Some sign have a formatted text,
 *  0 = [KEYWORD]
 *  1 = free
 *  2 = free
 *  3 = free
 * @author Windu
 *
 */
public class SignPos 
{

	private int BuildingId;
	private LocationData position;
	private String[] text;
	
	public SignPos()
	{
		this.BuildingId = 0;
		this.position = new LocationData("",0.0,0.0,0.0);
		this.text = new String[] {"","","",""  }; 
	}

	
	
	public SignPos(int buildingId, LocationData position, String[] text) 
	{
		super();
		BuildingId = buildingId;
		this.position = position;
		this.text = text;
	}



	public int getBuildingId() {
		return BuildingId;
	}

	public void setBuildingId(int buildingId) {
		BuildingId = buildingId;
	}

	public LocationData getPosition() {
		return position;
	}

	public void setPosition(LocationData position) {
		this.position = position;
	}

	public String[] getText() {
		return text;
	}

	public void setText(String[] text) {
		this.text = text;
	}
	
	
}
