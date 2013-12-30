package core;

import java.util.ArrayList;

/**
 * 
 * @author oduda
 *
 */
public class Barrack 
{
	private Boolean isEnabled;
	private int unitMax;
	private ArrayList<UnitType> unitList; 
	
	
	public Barrack()
	{
		setIsEnabled(false);
		setUnitMax(0);
		setUnitList(new ArrayList<UnitType>());
	}


	public Boolean getIsEnabled() {
		return isEnabled;
	}


	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}


	public int getUnitMax() {
		return unitMax;
	}


	public void setUnitMax(int unitMax) {
		this.unitMax = unitMax;
	}


	public ArrayList<UnitType> getUnitList() {
		return unitList;
	}


	public void setUnitList(ArrayList<UnitType> unitList) {
		this.unitList = unitList;
	}
	
	
}
