package net.krglok.realms.npc;

import net.krglok.realms.unit.UnitType;

public class NPCData
{
	private int npcId;
	private NPCType npcType ;
	private UnitType unitType ;
	private int settleId;
	private int buildingId;

	public NPCData()
	{
		this.npcId = 0;
		this.npcType = NPCType.BEGGAR;
		this.unitType = UnitType.SETTLER;
		this.settleId = 0;
		this.buildingId = 0;
	}
	
	public NPCData(int npcId, NPCType npcType, UnitType unitType, int settleId, int buildingId)
	{
		this.npcId = npcId;
		this.npcType = npcType;
		this.unitType = unitType;
		this.settleId = settleId;
		this.buildingId = buildingId;
	}

	public int getNpcId()
	{
		return npcId;
	}

	public void setNpcId(int npcId)
	{
		this.npcId = npcId;
	}

	public NPCType getNpcType()
	{
		return npcType;
	}

	public void setNpcType(NPCType npcType)
	{
		this.npcType = npcType;
	}

	public UnitType getUnitType()
	{
		return unitType;
	}

	public void setUnitType(UnitType unitType)
	{
		this.unitType = unitType;
	}

	public int getSettleId()
	{
		return settleId;
	}

	public void setSettleId(int settleId)
	{
		this.settleId = settleId;
	}

	public int getBuildingId()
	{
		return buildingId;
	}

	public void setBuildingId(int buildingId)
	{
		this.buildingId = buildingId;
	}

}
