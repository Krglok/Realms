package net.krglok.realms.data;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;

import org.bukkit.configuration.ConfigurationSection;

public class DataStoreBuilding  extends AbstractDataStore<Building>
{

	public DataStoreBuilding(String dataFolder, SQliteConnection sql)
	{
		super(dataFolder, "buildings", "BUILDING", false, sql);
	}
	
	
	/**
	 * Override this for the concrete class
	 * 
	 * @param T dataObject, instance of real data Class
	 */
	@Override
	public void initDataSection(ConfigurationSection section, Building dataObject)
	{
//		// 
    	section.set("id", String.valueOf(dataObject.getId()));
    	section.set("buildingType", dataObject.getBuildingType().name());
    	section.set("settleId", String.valueOf(dataObject.getSettleId()));
    	section.set("lehenId", String.valueOf(dataObject.getLehenId()));
    	section.set("ownerId", dataObject.getOwnerId());
    	section.set("settler", String.valueOf(dataObject.getSettler()));
    	section.set("settlerInstalled", String.valueOf(dataObject.getSettlerInstalled()));
    	section.set("workerNeeded", String.valueOf(dataObject.getWorkerNeeded()));
    	section.set("workerInstalled", String.valueOf(dataObject.getWorkerInstalled()));
//    	section.set("isRegion", dataObject.isRegion().toString());
    	section.set("hsRegion", String.valueOf(dataObject.getHsRegion()));
//    	section.set("hsRegionType", dataObject.getHsRegionType());
//    	section.set("hsSuperRegion", dataObject.getHsSuperRegion());
    	section.set("isEnabled", dataObject.isEnabled().toString());
    	section.set("isActiv", dataObject.isActive().toString());
    	section.set("position", LocationData.toString(dataObject.getPosition()));
    	for (int i = 0; i < dataObject.getSlots().length; i++)
		{
    		if (dataObject.getSlots()[i] != null)
    		{
    			if (dataObject.getSlots()[i].ItemRef() != "")
    			{
    				section.set("slot"+i, dataObject.getSlots()[i].ItemRef());
    			}
    		}
		}
//    	section.set("trainType", dataObject.getTrainType().name());
    	section.set("trainCounter", String.valueOf(dataObject.getTrainCounter()));
    	section.set("trainTime", String.valueOf(dataObject.getTrainTime()));
    	section.set("maxProduction", String.valueOf(dataObject.getMaxTrain()));
	}


	/**
	 * Override this for the concrete class

	 * @return T , real data Class
	 */
	@Override
	public Building initDataObject(ConfigurationSection data)
	{
//		// 
		int buildingId = Integer.valueOf(data.getString( "id","0"));
		BuildPlanType buildingType = BuildPlanType.getBuildPlanType(data.getString(  "buildingType","None"));
		int settleId = Integer.valueOf(data.getString(  "settleId","0"));
		int lehenId = Integer.valueOf(data.getString(  "lehenId","0"));
		int ownerId = data.getInt(  "ownerId",0);
		int settler = Integer.valueOf(data.getString(  "settler","0"));
		int settlerInstalled = Integer.valueOf(data.getString(  "settlerInstalled","0"));
		int workerNeeded = Integer.valueOf(data.getString(  "workerNeeded","0"));
		int workerInstalled = Integer.valueOf(data.getString(  "workerInstalled","0"));
		int hsRegion = Integer.valueOf(data.getString("hsRegion","0"));
		Boolean isEnabled = Boolean.valueOf(data.getString(  "isEnabled","false"));
		Boolean.valueOf(data.getString(  "isActiv","false"));

//		UnitType trainType= UnitType.getBuildPlanType(data.getString(settleSec+".buildinglist."+ ".trainType", "NONE"));
		int trainCounter = Integer.valueOf(data.getString(  "trainCounter", "0"));
		int trainTime = Integer.valueOf(data.getString(  "trainTime", "0"));
		int maxProduction = Integer.valueOf(data.getString(  "maxProduction", "0"));
		String slot1 = "";
		String slot2 = "";
		String slot3 = "";
		String slot4 = "";
		String slot5 = "";
		slot1 = data.getString(  "slot1","");
		slot2 = data.getString(  "slot2","");
		slot3 = data.getString(  "slot3","");
		slot4 = data.getString(  "slot4","");
		slot5 = data.getString(  "slot5","");
		LocationData position = LocationData.toLocation(data.getString("position"));
		Double sale = Double.valueOf(data.getString(  ".sale","0.0"));
		
//		System.out.println(buildingId+" : "+buildingType);
		
		Building building = null; 
		building = 	new Building(
						buildingId, 
						buildingType, 
						settler, 
						workerNeeded, 
						workerInstalled, 
						hsRegion, 
						isEnabled, 
						slot1, 
						slot2, 
						slot3, 
						slot4, 
						slot5,
						sale,
						position,
//						trainType,
						trainCounter,
						trainTime,
						maxProduction,
						settleId,
						ownerId
						);
		building.setLehenId(lehenId);
		building.setSettlerInstalled(settlerInstalled);
		return building;
	}

}
