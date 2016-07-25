package net.krglok.realms.tool;

import java.io.File;
import java.util.HashMap;

import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.ItemList;
import net.krglok.realms.builder.BuildPlanMap;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.builder.RegionConfig;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.manager.BuildManager;
import net.krglok.realms.unittest.DataTest;

import org.bukkit.inventory.ItemStack;
import org.junit.Test;

public class RealmsBuildingBlueprint
{
	


	
	
	private boolean isInList(String name, String[] sList)
	{
		for (int i = 0; i < sList.length; i++)
		{
			if (name.contains(sList[i]))
			{
				return true;
			}
		}
		return false;
	}
	

	@SuppressWarnings("unused")
	private String[] setStandardList()
	{
		return new String[] 
        		{"HALL",
        		"HOME", 
        		"WHEAT",
        		"WOODCUTTER",
        		"QUARRY",
        		"FARMHOUSE",
        		"SHEPHERD",
        		"CARPENTER",
        		"CABINETMAKER",
        		"BAKERY",
        		"CHARBURNER",
				"AXESHOP",
        		"HOESHOP", 
        		"PICKAXESHOP", 
        		"KNIFESHOP",
        		"SPADESHOP",
        		"COWSHED",
        		"CHICKENHOUSE",
        		"FISHERHOOD",
        		"FARM",
        		"WORKSHOP",
        		"COWSHED",
        		"CHICKENHOUSE",
        		"BRICKWORK",
        		"STONEMINE",
        		"SMELTER",
        		"PIGPEN",
        		"GOLDSMELTER",
        		"HUNTER",
        		"TAMER",
        		"SPIDERSHED",
        		"STONEYARD",
        		"MUSHROOM",
        		"KITCHEN",
				"TOWNHALL",
				"GATE",
				"CITYGATE",
				"HOUSE",
				"MANSION",
				"COTTAGE",
				"TAVERNE",
        		"NETHERQUARRY"
        		};
	}

	private String[] setVillageList()
	{
		return new String[] 
        		{
				"PARISHHOUSE",
        		"WELL",
        		"SMITH",
        		"FIELD",
        		"BUTCHER",
				"LARGEHOUSE",
				"ARMOURY",
				"CHURCH",
				"CABIN",
				"SMALLIBRARY"
        		};
	}
	
	private String[] setStructureList()
	{
		return new String[] 
        		{
				"COLONY",
				"PILLAR",
				"WALLN",
				"WALLE",
				"ROAD",
				"STEEPLE",
				"FORT",
				"SHIP_0",
				"BOATYARD",
				"MONEYBANK",
				"XPBANK",
				"COALMINE",
				"IRONMINE",
				"EMERALDMINE",
				"GOLDMINE",
				"DIAMONDMINE"
        		};
	}

	@SuppressWarnings("unused")
	private String[] setMilitaryList()
	{
		return new String[] 
        		{
				"BLACKSMITH",
				"TANNERY",
				"BOWMAKER",
				"FLETCHER",
				"HORSEBARN",
				"WEAPONSMITH",
				"ARMOURER",
				"CHAINMAKER",
				"TRADER",
				"GUARDHOUSE",
				"WATCHTOWER",
				"DEFENSETOWER",
				"ARCHERY",
				"BARRACK",
				"TOWER",
				"CASERN",
				"GARRISON",
				"BIBLIOTHEK",
				"LIBRARY",
        		};
	}

	@SuppressWarnings("unused")
	private String[] setFeudalList()
	{
		
		return new String[] 
        		{
				"KEEP",
				"CASTLE",
				"STRONGHOLD",
				"PALACE",
				"HEADQUARTER"
        		};
	}
	
	
	private String getBuildPlanTypeHeader(BuildPlanType bType)
	{
			//BUILDING_HOME (100),
			//BUILDING_PROD (200),
			//BUILDING_PRODMILITARY (300),
			//BUILDING_WAREHOUSE (300),
			//BUILDING_TRADER (400),
			//BUILDING_MILITARY (500),
			//BUILDING_ENTERTAIN (600),
			//BUILDING_EDUCATION (700),
			//BUILDING_RELIGION (800),
			//BUILDING_KEEP (900),
			//BUILDING_GOVERNMENT (1000)
			if (BuildPlanType.getBuildGroup(bType)==1)
			{
				return ("Structure");
			}
			if (BuildPlanType.getBuildGroup(bType)==10)
			{
				return("Management");
			}
			if (BuildPlanType.getBuildGroup(bType)==100)
			{
				return("Homes");
			}
			if (BuildPlanType.getBuildGroup(bType)==200)
			{
				return("Production");
			}
			if (BuildPlanType.getBuildGroup(bType)==300)
			{
				return("Military Production");
			}
			if (BuildPlanType.getBuildGroup(bType)==400)
			{
				return("Trader");
			}
			if (BuildPlanType.getBuildGroup(bType)==500)
			{
				return("Military ");
			}
			if (BuildPlanType.getBuildGroup(bType)==600)
			{
				return("Entertain");
			}
			if (BuildPlanType.getBuildGroup(bType)==700)
			{
				return("Education");
			}
			if (BuildPlanType.getBuildGroup(bType)==800)
			{
				return("Religion");
			}
			if (BuildPlanType.getBuildGroup(bType)==900)
			{
				return("Feudal");
			}
			if (BuildPlanType.getBuildGroup(bType)==1000)
			{
				return("Government");
			}
			return "??";
	}
	
	@Test
	public void getStrongholdConstructionMaterial()
	{
//		StrongholdTools shTools = new StrongholdTools();
		DataTest testData = new DataTest(); //logTest);
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Stronghold";
        File regionFolder = new File(path, "RegionConfig");
        if (!regionFolder.exists()) {
        	System.out.println("Folder not found !");
            return;
        }
        HashMap<String,Integer> required = new HashMap<String,Integer>();
        HashMap<String,Integer> reagent = new HashMap<String,Integer>();
        HashMap<String,Integer> superRef = new HashMap<String,Integer>();
        HashMap<String,Integer> ingredient = new HashMap<String,Integer>();
        HashMap<String,Integer> product = new HashMap<String,Integer>();
        superRef.put("Anywhere",0);
        String sRegionFile = "";
        
        RegionConfig region;
        String[] sList ;
//        sList = setStandardList();
//        sList = setMilitaryList();
//        sList = setVillageList();
        sList = setStructureList();
//        sList = setFeudalList();
//        sList = new String[]
//        		{
//        		"MUSHROOM",
//        		};
        System.out.println("[Stronghold] Building   Blueprint " );
        System.out.println("" );
        System.out.println("123456789012345678901234567890123456789012345678901234567890" );

        for (File RegionFile : regionFolder.listFiles()) 
        {
        	
        	sRegionFile = RegionFile.getName();
            if ( isInList(sRegionFile,sList))
            {
	        	region = StrongholdTools.getRegionConfig(path+"\\RegionConfig", sRegionFile);
	        	String sBiome = "All";
	        	if (region.getBiome().size() > 0)
	        	{
	        		sBiome = region.getBiome().get(0);
	        	}
	        	String sTypeName = sRegionFile.replace(".yml", "");
	            System.out.println("======================================================================" );
	            System.out.print("Buildplan:"+ConfigBasis.setStrleft(sTypeName,20));
	            System.out.print(" Group: "+ ConfigBasis.setStrleft(getBuildPlanTypeHeader(BuildPlanType.getBuildPlanType(sTypeName) ),20));
	            System.out.print(" ID: "+ ConfigBasis.set0right((BuildPlanType.getBuildPlanType(sTypeName).getValue() ),4));
                System.out.println("");
	            System.out.println("Biome    :"+ConfigBasis.setStrleft(sBiome,20)+" Radius: "+String.valueOf(region.getRadius()) );
	            System.out.println("---------------------------------------------------------------------" );

	            
	            System.out.println("Requirements:                  Reagents:" );
	            if (region.getRequirements().size() > region.getReagents().size())
	            {
		            for (int i=0; i<region.getRequirements().size(); i++)
		            {
		            	String sName = region.getRequirements().get(i).getType().name()+"."+String.valueOf(region.getRequirements().get(i).getAmount());
		                System.out.print("-"+ConfigBasis.setStrleft(sName,30) );
		                if (region.getReagents().size() > i )
		                {
			                System.out.print("-"+ConfigBasis.setStrleft(region.getReagents().get(i).getType().name()+"."+String.valueOf(region.getReagents().get(i).getAmount()),20) );
		                }
		                System.out.println("");
		            }
	            } else
	            {
		            for (int i=0; i<region.getReagents().size(); i++)
		            {
		            	String sName = region.getReagents().get(i).getType().name()+"."+String.valueOf(region.getReagents().get(i).getAmount());
		                if (region.getRequirements().size() > i )
		                {
			                System.out.print("-"+ConfigBasis.setStrleft(region.getRequirements().get(i).getType().name()+"."+String.valueOf(region.getRequirements().get(i).getAmount()),30) );
		                } else
		                {
			                System.out.print("-"+ConfigBasis.setStrleft("",30) );
		                }
		                System.out.print("-"+ConfigBasis.setStrleft(sName,20) );
		                System.out.println("");
		            }
	            }
	            System.out.println("" );
	            System.out.println("RequiredMoney:"+ConfigBasis.setStrright(region.getMoneyRequirement(),10) );
	            System.out.println("---------------------------------------------------------------------" );
	            System.out.println("Upkeep:                        Output:   " );
	            if (region.getUpkeep().size() > region.getOutput().size())
	            {
		            for (int i=0; i<region.getUpkeep().size(); i++)
		            {
		            	String sName = region.getUpkeep().get(i).getType().name()+"."+String.valueOf(region.getUpkeep().get(i).getAmount());
		                System.out.print("-"+ConfigBasis.setStrleft(sName,30) );
		                if (i<region.getOutput().size())
		                {
			                System.out.print("-"+ConfigBasis.setStrleft(region.getOutput().get(i).getType().name()+"."+String.valueOf(region.getOutput().get(i).getAmount()),20) );
		                }
		                System.out.println("");
		            }
	            } else
	            {
		            for (int i=0; i<region.getOutput().size(); i++)
		            {
		            	String sName = region.getOutput().get(i).getType().name()+"."+String.valueOf(region.getOutput().get(i).getAmount());
		                if (i<region.getUpkeep().size())
		                {
			                System.out.print("-"+ConfigBasis.setStrleft(region.getUpkeep().get(i).getType().name()+"."+String.valueOf(region.getUpkeep().get(i).getAmount()),30) );
		                } else
		                {
			                System.out.print("-"+ConfigBasis.setStrleft("",30) );
		                }
		                System.out.print("-"+ConfigBasis.setStrleft(sName+"."+String.valueOf(region.getOutput().get(i).getAmount()),20) );
		                System.out.println("");
		            }
	            }
	            System.out.println("" );
	            System.out.println("UpkeepMoney:"+ConfigBasis.setStrright(region.getMoneyOutput(),10) );
	            System.out.println("======================================================================" );

	            
	            System.out.println("TMX  Material List:" );
	            BuildPlanMap buildPLan;

	    		BuildPlanType bType = BuildPlanType.getBuildPlanType(sTypeName);
	    		buildPLan = testData.readTMXBuildPlan(bType, 4, -1);
	    		if (buildPLan != null)
	    		{
		            ItemList matItems = BuildManager.makeMaterialList(buildPLan);
		            for (Item item : matItems.values())
		            {
		                System.out.print("-"+ConfigBasis.setStrleft(item.ItemRef()+"."+String.valueOf(item.value() ),30) );
			            System.out.println("" );
		            }
		            System.out.println("======================================================================" );
	    		}
            }
        }

	}

}
