package net.krglok.realms.data;

import net.krglok.realms.colonist.Colony;
import net.krglok.realms.core.LocationData;

import org.bukkit.configuration.ConfigurationSection;

public class DataStoreColony extends AbstractDataStore<Colony>
{

	public DataStoreColony(String dataFolder)
	{
		super(dataFolder, "colony", "COLONY", false, null);
	}

	/**
	 * Override this for the concrete class
	 * 
	 * @param T dataObject, instance of real data Class
	 */
	@Override
	public void initDataSection(ConfigurationSection section, Colony dataObject)
	{
//		private int id;
//		private ColonyStatus cStatus ;
//		private LocationData position;
//		private String name;
//		private String ownerId;
//		private Warehouse warehouse ;
//		private Bank bank;
//		private ItemList requiredItems;
//		private int settler;
//		private SettleSchema settleSchema;
//		private SettleSchema netherSchema;
//		private BuildPosition actualBuildPos;
//		private int buildPosIndex;
//		private Boolean isEnabled;
//		private Boolean isActive;
//		private ColonyStatus nextStatus;
//		
//		private BuildPlanType center = BuildPlanType.COLONY;
//		private BuildPlanMap buildPlan ;
//		private BuildManager buildManager = new BuildManager();
//		
//		private RegionLocation newSuperRegion;
//		private RegionLocation superRequest;
//		
//		private int markUpStep;
//		
//		private boolean isPrepared ;
//		private int prepareLevel;
//		private int prepareRow;
//		private int prepareCol;
//		private int prepareRadius;
//		private int prepareOffset;
//		private int prepareMaxLevel;
//		
//		
//		private Biome biome;
//		private ArrayList<BiomeLocation> biomeRequest;

		section.set("id", dataObject.getId());
		section.set("name", dataObject.getName());

	}


	/**
	 * Override this for the concrete class

	 * @return T , real data Class
	 */
	@Override
	public Colony initDataObject(ConfigurationSection data)
	{
		Colony colony = new Colony("", new LocationData("",0.0,0.0,0.0), "");
				
		return null;
	}

}
