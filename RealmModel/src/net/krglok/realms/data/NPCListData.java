package net.krglok.realms.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.krglok.realms.npc.NPCData;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.unit.UnitType;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;

public class NPCListData extends DataStore<NPCData>
{

	private static String fileName = "npclist";
    private static String sectionName = "NPC"; 

	public NPCListData(String dataFolder,  boolean timeMessure)
	{
		super(dataFolder, fileName, sectionName, timeMessure);
	}

	@Override
	public void initDataSection(ConfigurationSection section, NPCData dataObject)
	{
		HashMap<String,String> values; // = new HashMap<String,String>();
        values = new HashMap<String,String>();
		
        section.set("npcId", dataObject.getNpcId());
        section.set("npcType", String.valueOf(dataObject.getNpcType().name()));
        section.set("unitType", String.valueOf(dataObject.getUnitType().name()));
        section.set("settleId", dataObject.getSettleId());
        section.set("buildingId", dataObject.getBuildingId());
//        section.set(MemorySection.createPath(section,String.valueOf(dataObject.getNpcId())), values);
	}

	@Override
	public NPCData initDataObject(ConfigurationSection data)
	{
		NPCData npcData = null;
		if (data != null)
		{
			int npcId = data.getInt("npcId",0);
			NPCType npcType = NPCType.valueOf(data.getString("npcType",NPCType.BEGGAR.name()));
			UnitType unitType = UnitType.valueOf(data.getString("unitType",UnitType.SETTLER.name()));
			int settleId = data.getInt("settleId",0);
			int buildingId = data.getInt("buildingId",0);
			npcData = new NPCData(npcId, npcType, unitType,settleId, buildingId);
//			System.out.println("NPC objct : "+"/"+npcId+":"+ npcType.name()+":"+unitType.name());
		}
		return npcData;
	}
	
	/**
	 * write nbpclist to file
	 * use a standard refId
	 * @param dataObject
	 */
	public void writeNpc(NPCData dataObject, String npcId)
	{
		this.writeData(dataObject, npcId);
	}
	
	/**
	 * <pre>
	 * read npclist  from file 
	 * each npc is stored under his npcId
	 * 
	 * @return
	 * </pre>
	 */
	public HashMap<Integer,NPCData> readNpcList()
	{
		HashMap<Integer,NPCData> npcList = new HashMap<Integer,NPCData>();
		ArrayList<String> npcInit = readDataList();
		if (npcInit.size() == 0)
		{
			System.out.println("[REALMS] NPC List is Empty : ");
			return npcList;
		}
		for (String npcId : npcInit)
		{
			NPCData npcData =  readData(npcId);
			System.out.println("[REALMS] NPC READ : "+npcData.getNpcId());
			if (npcData != null)
			{
				npcList.put(Integer.valueOf(npcId), npcData);
			}
		}
		return npcList;
		
	}
	
}
