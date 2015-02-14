package net.krglok.realms.unit;

import java.util.ArrayList;

import net.krglok.realms.npc.NpcData;
import net.krglok.realms.npc.NpcList;

public class UnitList extends ArrayList<NpcData>
{
	private static final long serialVersionUID = 8833127238708166553L;

	
	public UnitList getUnitTypeList(UnitType uType)
	{
		UnitList subList = new UnitList();
		for (NpcData unit : this)
		{
			if (unit.getUnitType() == uType)
			{
				subList.add(unit);
			}
		}
		
		return subList;
	}
	
	/**
	 * get the first recrute npc of the building 
	 * @param buildingId
	 * @return
	 */
	public NpcData getBuildingRecrute(int buildingId)
	{
		for (NpcData npc : this)
		{
			if (npc.getWorkBuilding() == buildingId)
			{
				if (npc.getUnitType() == UnitType.ROOKIE)
				{
					return npc;
				}
			}
		}
		
		return null;
	}

	public void putUnit(NpcData npc)
	{
		this.add(npc);
	}
	
	public NpcList asNpcList()
	{
		NpcList npcs = new NpcList();
		for (NpcData npc : this)
		{
			npcs.putNpc(npc);
		}
		return npcs;
	}
}
