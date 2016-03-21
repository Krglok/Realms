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
	private boolean isCamp;
	protected  HeightAnalysis analysis;
	private boolean isActiv;	// scan work in progress
	private boolean isValid;  // scan give valid position
	private int redo;
	
	
	public CampPosition()
	{
		this.Id = 0;
		this.settleId = 0;
		this.face = PositionFace.NORTHWEST;
		this.position = new LocationData("", 0.0, 0.0, 0.0);
		this.analysis = new HeightAnalysis(11);
		this.isActiv = true;
		this.isValid = false;
		this.isCamp = false;
		this.setRedo(0);
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

	/**
	 * @return the redo
	 */
	public int getRedo()
	{
		return redo;
	}

	/**
	 * @param redo the redo to set
	 */
	public void setRedo(int redo)
	{
		this.redo = redo;
	}
	
	public void addRedo()
	{
		this.redo++;
	}
}
