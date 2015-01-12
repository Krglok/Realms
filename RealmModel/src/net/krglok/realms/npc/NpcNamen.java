package net.krglok.realms.npc;

import java.util.ArrayList;
import java.util.List;

public class NpcNamen
{
	private List<String> womanNames;
	private List<String> manNames;
	
	public NpcNamen()
	{
		womanNames = new ArrayList<String>();
		manNames = new ArrayList<String>();
	}

	/**
	 * @return the womanNames
	 */
	public List<String> getWomanNames()
	{
		return womanNames;
	}

	/**
	 * @param womanNames the womanNames to set
	 */
	public void setWomanNames(List<String> womanNames)
	{
		this.womanNames = womanNames;
	}

	/**
	 * @return the manNames
	 */
	public List<String> getManNames()
	{
		return manNames;
	}

	/**
	 * @param manNames the manNames to set
	 */
	public void setManNames(List<String> manNames)
	{
		this.manNames = manNames;
	}

	
	public String findName(GenderType gender)
	{
		// get the first name as defualt;
		
		String found = "";
		List<String> names;
		if (gender == GenderType.WOMAN)
		{
			names = this.getWomanNames();
		} else
		{
			names = this.getManNames();
		}
		int maxValue = names.size()-1;
		int index =  (int) Math.rint(Math.random() * maxValue);
		return names.get(index);
	}
}
