package net.krglok.realms.data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.RouteOrder;
import net.krglok.realms.core.RouteOrderList;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.unit.Unit;
import net.krglok.realms.unit.UnitFactory;
import net.krglok.realms.unit.UnitType;

import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;

public class DataStoreSettlement extends AbstractDataStore<Settlement>
{

	public DataStoreSettlement(String dataFolder)
	{
		super(dataFolder, "settlements", "SETTLEMENT", false);
	}
	
	public boolean checkSettlements()
	{
        File regFile = new File(dataFolder, fileName+".yml");
		System.out.println("[REALMS] Check settlement converter needed");
        if (regFile.exists() == false) 
        {
			System.out.println("[REALMS] Converter for settlements started !!");
        	return false;
        }
		return true;
	}

	private void printSettleRow(Settlement settle)
	{
		System.out.print(settle.getId());
		System.out.print(" | ");
		System.out.print(settle.getOwnerId());
		System.out.print(" | ");
		System.out.print(settle.getSettleType());
		System.out.print(" | ");
		System.out.print(settle.getName());
		System.out.print(" | ");
		System.out.println("");
	
	}

	public void convertSettlements(SettlementList sList)
	{
		System.out.println("[REALMS] Convert SettleList : ["+sList.size()+"]");
		for (Settlement settle : sList.values())
		{
				printSettleRow(settle);
				writeData(settle, String.valueOf(settle.getId()));
				System.out.println("Settle :"+settle.getId()+" | "+settle.getName());
		}

	}
	
	/**
	 * Override this for the concrete class
	 * 
	 * @param T dataObject, instance of real data Class
	 */
	@Override
	public void initDataSection(ConfigurationSection section, Settlement dataObject)
	{
//		// 
		HashMap<String,String> values; // = new HashMap<String,String>();
		HashMap<String,Object> subList;
		section.set("id", dataObject.getId());
        section.set( "settleType", dataObject.getSettleType().name());
        section.set( "position", LocationData.toString(dataObject.getPosition()));
        section.set( "biome", dataObject.getBiome().name());
        section.set( "age", dataObject.getAge());
        section.set( "name", dataObject.getName());
        section.set( "owner", dataObject.getOwnerId());
        section.set( "isCapital", dataObject.getIsCapital());
//        section.set(MemorySection.createPath(settleSec, "isEnabled"), dataObject.isEnabled());
        section.set( "isActive", dataObject.isActive());
        section.set( "bank", dataObject.getBank().getKonto());

//        System.out.println("SAVE : 1");
//
        values = new HashMap<String,String>();
        values.put("isEnabled",dataObject.getTownhall().getIsEnabled().toString());
        values.put("workerNeeded",String.valueOf(dataObject.getTownhall().getWorkerNeeded()));
        values.put( "workerCount",String.valueOf(dataObject.getTownhall().getWorkerCount()));
        section.set( "townhall", values);

//        System.out.println("SAVE : 2");

        values = new HashMap<String,String>();
        values.put("settlerMax",String.valueOf(dataObject.getResident().getSettlerMax()));
        values.put("settlerCount",String.valueOf(dataObject.getResident().getSettlerCount()));
        values.put("fertilityCounter",String.valueOf(dataObject.getResident().getFertilityCounter()));
        values.put("happiness",String.valueOf(dataObject.getResident().getHappiness()));
        values.put("workerCount","0");
        values.put("cowCount",String.valueOf(dataObject.getResident().getHorseCount()));
        values.put("horseCount",String.valueOf(dataObject.getResident().getCowCount()));
        section.set("resident", values);

        subList = new HashMap<String,Object>();
        values = new HashMap<String,String>();
	    for (RouteOrder rOrder : dataObject.getTrader().getRouteOrders().values())
	    {
	        values.put("id",String.valueOf(rOrder.getId()));
	        values.put("targetId",String.valueOf(rOrder.getTargetId()));
	        values.put("itemref",rOrder.ItemRef());
	        values.put( "amount",String.valueOf(rOrder.value()));
	        values.put( "price",String.valueOf(rOrder.getBasePrice()));
	        values.put( "isEnabled",String.valueOf(rOrder.isEnabled()));
		    subList.put(String.valueOf(rOrder.getId()),values);
	    }
        section.set( "routes", subList);
        
        values = new HashMap<String,String>();
    	values.put("itemMax", String.valueOf(dataObject.getWarehouse().getItemMax()));
    	values.put("itemCount", String.valueOf(dataObject.getWarehouse().getItemCount()));
    	values.put("isEnabled", dataObject.getWarehouse().getIsEnabled().toString());
        section.set("warehouse", values);

        values = new HashMap<String,String>();
    	for (String itemref : dataObject.getWarehouse().getItemList().keySet())
    	{
    		values.put(itemref, String.valueOf(dataObject.getWarehouse().getItemList().getValue(itemref)) );
    	}
        section.set("itemList", values);
////        section.set("", dataObject);
//        System.out.println("SAVE : "+dataObject.getId()+": "+fileName);
        values = new HashMap<String,String>();
        int index = 0;
    	for (Unit unit : dataObject.getBarrack().getUnitList())
    	{
    		values.put(String.valueOf(index), unit.getUnitType().name());
    		index++;
    	}
        section.set("unitlist", values);

	}


	/**
	 * Override this for the concrete class

	 * @return T , real data Class
	 */
	@Override
	public Settlement initDataObject(ConfigurationSection data)
	{
//		// 
	Settlement settle = new Settlement(null);

	settle.setId(data.getInt("id"));
	settle.setSettleType(SettleType.valueOf(data.getString( "settleType")));
	settle.setPosition(LocationData.toLocation(data.getString( "position")));
	settle.setBiome(Biome.valueOf(data.getString( "biome","SKY")));
	settle.setAge(data.getLong( "age",0));

	//Biome.valueOf(settle.getBiome()));
	settle.setName(data.getString( "name"));
	settle.setOwnerId(data.getString( "owner",ConfigBasis.NPC_0));
	settle.setIsCapital(data.getBoolean( "isCapital",false));
	settle.setIsActive(data.getBoolean( "isActive"));
	settle.getBank().addKonto(data.getDouble( "bank",0.0),"SettleRead",settle.getId());
	
	// die werte werden als String gelesen, da verschiedene Datentypen im array sind
	settle.getTownhall().setIsEnabled(Boolean.valueOf(data.getString( "townhall"+".isEnable")));
	settle.getTownhall().setWorkerNeeded(Integer.valueOf(data.getString( "townhall"+".workerNeeded")));
	settle.getTownhall().setWorkerCount(Integer.valueOf(data.getString( "townhall"+".workerCount")));
	
	// die werte werden als String gelesen, da verschiedene Datentypen im array sind
	settle.getResident().setSettlerMax(Integer.valueOf(data.getString( "resident"+".settlerMax")));
	settle.getResident().setSettlerCount(Integer.valueOf(data.getString( "resident"+".settlerCount")));
	settle.getResident().setFeritilityCounter(Double.valueOf(data.getString( "resident"+".fertilityCounter")));
	settle.getResident().setHappiness(Double.valueOf(data.getString( "resident"+".happiness")));
	settle.getResident().setCowCount(Integer.valueOf(data.getString( "resident"+".cowCount")));
	settle.getResident().setHorseCount(Integer.valueOf(data.getString( "resident"+".horseCount")));


	if (data.isConfigurationSection("routes"))
	{
		Map<String,Object> rList = data.getConfigurationSection( "routes").getValues(false);
		if (rList != null)
		{
			RouteOrderList routeOrderList = new RouteOrderList();
	        System.out.println("[REALMS] RouteOrderList ["+rList.size()+"]");
	    	for (String ref : rList.keySet())
	    	{
		        System.out.println("[REALMS] RouteOrder :"+ref);
//	//	        values.put("id",String.valueOf(rOrder.getId()));
//	//	        values.put("targetId",String.valueOf(rOrder.getTargetId()));
//	//	        values.put("itemref",rOrder.ItemRef());
//	//	        values.put( "amount",String.valueOf(rOrder.value()));
//	//	        values.put( "price",String.valueOf(rOrder.getBasePrice()));
//	//	        values.put( "isEnabled",String.valueOf(rOrder.isEnabled()));
	    		int orderId = Integer.valueOf(data.getString( "routes."+ref+".id"));
	    		int targeitId = Integer.valueOf(data.getString( "routes."+ref+".targetId"));
	    		String itemRef = data.getString( "routes."+ref+".itemref","AIR");
	    		int amount = Integer.valueOf(data.getString( "routes."+ref+".amount"));
	    		double price= Double.valueOf(data.getString("routes."+ref+".price"));
	    		boolean isEnabled = data.getBoolean("routes."+ref+".isEnabled",true);
	            RouteOrder rOrder = new RouteOrder(orderId, targeitId, itemRef, amount, price, isEnabled);
	            System.out.println("[REALMS] RouteOrder Id:"+rOrder.getId());
	            routeOrderList.addRouteOrder(rOrder);
	    	}    
	    	settle.getTrader().setRouteOrders(routeOrderList);
		}
	}

    settle.getWarehouse().setItemMax(Integer.valueOf(data.getString( "warehouse"+".itemMax","0")));
    settle.getWarehouse().setIsEnabled(Boolean.valueOf(data.getString( "warehouse"+".isEnable","false")));
    
    ItemList iList = new ItemList();
	Map<String,Object> itemList = data.getConfigurationSection( "itemList").getValues(false);
	if (itemList != null)
	{
    	for (String ref : itemList.keySet())
    	{
    		int value = Integer.valueOf(data.getString( "itemList."+ref,"0"));
//                System.out.println(ref+":"+value);
            iList.addItem(ref, value);
    	}    
    	settle.getWarehouse().setItemList(iList);
	}
	UnitFactory uFactory = new UnitFactory();
	Map<String,Object> uList = data.getConfigurationSection( "unitlist").getValues(false);
	if (uList != null)
	{
    	for (Object ref : uList.values())
    	{
    		String uType = (String) ref;
    		settle.getBarrack().getUnitList().add(uFactory.erzeugeUnit(UnitType.getBuildPlanType(uType)));
//                System.out.println(ref+":");
    	}
	}
		
		return settle;
	}

}
