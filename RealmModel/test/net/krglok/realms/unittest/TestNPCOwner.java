package net.krglok.realms.unittest;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.MemberLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.LogList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;

public class TestNPCOwner
{

	@Test
	public void testOwnerNPC()
	{
		//  NPC Owner
		Owner NPCOwner = new Owner(0, MemberLevel.MEMBER_NONE, 0, "NPC1", 0, true);
		boolean expected = true;
		boolean actual = NPCOwner.isNPC();
//		readSettledata(1);
//		readSettledata(2);
		assertEquals("Owner ist ein NPC ",expected,actual);
	}
	
	private String getSettleKey(int id)
	{
		return "SETTLEMENT."+String.valueOf(id);
	}

	
	public Settlement readSettledata(int id) 
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins"; //\\Realms";
		LogList logTest = new LogList(path);
		Settlement settle = new Settlement(logTest);
        String settleSec = getSettleKey(id);
		try
		{
            File settleFile = new File("\\Program Files\\BuckitTest\\plugins\\Realms", "settlement.yml");
            if (! settleFile.exists()) 
            {
            	System.out.println("New File");
            	settleFile.createNewFile();
            }
            FileConfiguration config = new YamlConfiguration();
            config.load(settleFile);
        	System.out.println("Load File");
            
            if (config.isConfigurationSection(settleSec))
            {
            	settle.setId(config.getInt(settleSec+".id"));
            	settle.setSettleType(SettleType.valueOf(config.getString(settleSec+".settleType")));
            	//settle.setposition()
            	settle.setName(config.getString(settleSec+".name"));
            	settle.setOwner(null);
            	settle.setIsCapital(config.getBoolean(settleSec+".isCapital"));
            	settle.setIsActive(config.getBoolean(settleSec+".isActive"));
            	
            	// die werte werden als String gelesen, da verschiedene Datentypen im array sind
            	settle.getTownhall().setIsEnabled(Boolean.valueOf(config.getString(settleSec+".townhall"+".isEnable")));
            	settle.getTownhall().setWorkerNeeded(Integer.valueOf(config.getString(settleSec+".townhall"+".workerNeeded")));
            	settle.getTownhall().setWorkerCount(Integer.valueOf(config.getString(settleSec+".townhall"+".workerCount")));
            	
            	// die werte werden als String gelesen, da verschiedene Datentypen im array sind
            	settle.getResident().setSettlerMax(Integer.valueOf(config.getString(settleSec+".resident"+".settlerMax")));
            	settle.getResident().setSettlerCount(Integer.valueOf(config.getString(settleSec+".resident"+".settlerCount")));
            	settle.getResident().setFeritilityCounter(Double.valueOf(config.getString(settleSec+".resident"+".fertilityCounter")));
            	settle.getResident().setHappiness(Double.valueOf(config.getString(settleSec+".resident"+".happiness")));
            	settle.getResident().setCowCount(Integer.valueOf(config.getString(settleSec+".resident"+".cowCount")));
            	settle.getResident().setHorseCount(Integer.valueOf(config.getString(settleSec+".resident"+".horseCount")));

            	System.out.println("================================");
            	System.out.println("== SETTLEMENT read from File ===");
            	System.out.println(settle.getId());
            	System.out.println(settle.getName());
            	System.out.println(settle.getSettleType());
            	System.out.println(settle.getResident().getSettlerMax());
            	System.out.println(settle.getResident().getSettlerCount());
            	System.out.println(settle.getResident().getHappiness());
            	System.out.println(settle.getTownhall().getWorkerCount());
            	System.out.println(settle.getTownhall().getWorkerNeeded());
            	
//            	System.out.println("Part 4 "+config.getConfigurationSection(settleSec+".buildinglist").getValues(false));
    			ArrayList<String> msg = new ArrayList<String>();
    			
    			Map<String,Object> buildings = config.getConfigurationSection(settleSec+".buildinglist").getValues(false);
            	for (String ref : buildings.keySet())
            	{
                	System.out.println("Part 5 "+ref);

            		int buildingId = Integer.valueOf(config.getString(settleSec+".buildinglist."+ref+".id"));
            		BuildPlanType bType = BuildPlanType.getBuildPlanType(config.getString(settleSec+".buildinglist."+ref+".buildingType"));
            		int settler = Integer.valueOf(config.getString(settleSec+".buildinglist."+ref+".settler"));
            		int workerNeeded = Integer.valueOf(config.getString(settleSec+".buildinglist."+ref+".workerNeeded"));
            		int workerInstalled = Integer.valueOf(config.getString(settleSec+".buildinglist."+ref+".workerInstalled"));
            		Boolean isRegion = Boolean.valueOf(config.getString(settleSec+".buildinglist."+ref+".isRegion"));
            		int hsRegion = Integer.valueOf(config.getString(settleSec+".buildinglist."+ref+".hsRegion"));
        			String hsRegionType = config.getString(settleSec+".buildinglist."+ref+".hsRegionType");
        			String hsSuperRegion = config.getString(settleSec+".buildinglist."+ref+".hsSuperRegion");
        			Boolean isEnabled = Boolean.valueOf(config.getString(settleSec+".buildinglist."+ref+".isEnabled"));
        			boolean isActiv = Boolean.valueOf(config.getString(settleSec+".buildinglist."+ref+".isActiv"));
        			String slot1 = config.getString(settleSec+".buildinglist."+ref+".slot1","");
//        			String slot2 = config.getString(settleSec+".buildinglist."+ref+".slot2","");
//        			String slot3 = config.getString(settleSec+".buildinglist."+ref+".slot3","");
//        			String slot4 = config.getString(settleSec+".buildinglist."+ref+".slot4","");
//        			String slot5 = config.getString(settleSec+".buildinglist."+ref+".slot5","");
        			// debug messages
        			msg.add("== Building read from File ==="+buildings.size());
        			msg.add("id   = "+buildingId);
        			msg.add("Type = "+bType);
        			msg.add("Settler = "+settler);
        			msg.add("Need    = "+workerNeeded);
        			msg.add("Install = "+workerInstalled);
        			msg.add("isRegion= "+isRegion);
        			msg.add("hsRegion= "+hsRegion);
        			msg.add("Reg.Type= "+hsRegionType);
        			msg.add("SuperReg= "+hsSuperRegion);
        			msg.add("Enabled = "+isEnabled);
        			msg.add("Active  = "+isActiv);
        			msg.add("Slot1   = "+slot1);
//        			msg.add("===================");
        			
        			for (String line : msg)
        			{
        				System.out.println(line);
        			}
            	}
            }
		} catch (Exception e)
		{
			// TODO: handle exception
			
		}
		return settle;
	}


}
