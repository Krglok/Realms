package net.krglok.realms.manager;

import net.krglok.realms.core.LocationData;

/**
 * <pre>
 * Hold the position for regiment camps near by settlements
 * 
 * @author Windu
 * </pre>
 */
public class CampPosition
{
	protected static int COUNTER = 0;
	
	private int Id;
	private int settleId;
	private PositionFace face;
	private LocationData position;
	private boolean isCamp = false;
	protected  HeightAnalysis analysis;
	private boolean isActiv = true;	// scan work in progress
	private boolean isValid = false;  // scan give valid position
	
	public CampPosition()
	{
		this.Id = 0;
		this.settleId = 0;
		this.face = PositionFace.NORTHWEST;
		this.position = new LocationData("", 0.0, 0.0, 0.0);
		this.analysis = new HeightAnalysis(11);
	}

	public static void initCOUNTER(int init)
	{
		COUNTER = init;
	}
	
	public static int getCOUNTER()
	{
		return COUNTER;
	}
	
	public int getSettleId()
	{
		return settleId;
	}

	public void setSettleId(int settleId)
	{
		this.settleId = settleId;
	}

	public PositionFace getFace()
	{
		return face;
	}

	public void setFace(PositionFace face)
	{
		this.face = face;
	}

	public LocationData getPosition()
	{
		return position;
	}

	public void setPosition(LocationData position)
	{
		this.position = position;
	}

	/**
	 * @return the id
	 */
	public int getId()
	{
		return Id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id)
	{
		Id = id;
	}

	/**
	 * @return the isCamp
	 */
	public boolean isCamp()
	{
		return isCamp;
	}

	/**
	 * @param isCamp the isCamp to set
	 */
	public void setCamp(boolean isCamp)
	{
		this.isCamp = isCamp;
	}

	public HeightAnalysis getAnalysis()
	{
		return analysis;
	}

	public void setAnalysis(HeightAnalysis analysis)
	{
		this.analysis = analysis;
	}

	public boolean isActiv()
	{
		return isActiv;
	}

	public void setActiv(boolean isActiv)
	{
		this.isActiv = isActiv;
	}
	
	public boolean isValid()
	{
		return isValid;
	}
	
	public void setValid(boolean value)
	{
		this.isValid = value;
	}
	
}
