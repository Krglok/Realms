package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.SettlementData;
import net.krglok.realms.model.ModelStatus;
import net.krglok.realms.tool.LogList;
import net.krglok.realms.unit.IUnit;
import net.krglok.realms.unit.Unit;
import net.krglok.realms.unit.UnitFactory;
import net.krglok.realms.unit.UnitType;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;

public class SettlementWriteTest
{

	public ItemPriceList getPriceList()
	{
        String base = "BASEPRICE";
        ItemPriceList items = new ItemPriceList();
        try
		{
			//\\Program Files\\BuckitTest\\plugins\\Realms
            File DataFile = new File("\\GIT\\OwnPlugins\\Realms\\plugins\\Realms", "baseprice.yml");
//            if (!DataFile.exists()) 
//            {
//            	DataFile.createNewFile();
//            }
            FileConfiguration config = new YamlConfiguration();
            config.load(DataFile);
            
            if (config.isConfigurationSection(base))
            {
            	java.util.Map<String,Object> buildings = config.getConfigurationSection(base).getValues(false);
            	for (String ref : buildings.keySet())
            	{
            		Double price = config.getDouble(base+"."+ref,0.0);
            		ItemPrice item = new ItemPrice(ref, price);
            		items.add(item);
            	}
            }
		} catch (Exception e)
		{
		}
		return items;
	}

	private void showBarrack(Settlement settle)
	{
		System.out.print("Barracks "+ ConfigBasis.setStrright(settle.getBarrack().getUnitList().size(), 2));
		System.out.println("");
		
		for (Unit unit : settle.getBarrack().getUnitList())
		{
			System.out.print(" "+ ConfigBasis.setStrleft(unit.getUnitType().name(), 10));
			System.out.print(" AR: "+ ConfigBasis.setStrright(unit.getHealth(), 2));
			System.out.println("");
			
		}
	}
	
	private void showTraining(Settlement settle)
	{
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("Settlement ["+settle.getId()+"] : "
				+ChatColor.YELLOW+settle.getName()
				+ChatColor.GREEN+" Age: "+settle.getAge()
				+":"+settle.getProductionOverview().getCycleCount());
		for (Building building : settle.getBuildingList().values())
		{
			if (BuildPlanType.getBuildGroup(building.getBuildingType()) == 500)
			{
				msg.add(" "+ConfigBasis.setStrright(building.getId(),3)
				+" : "+ConfigBasis.setStrleft(building.getBuildingType().name(),12)
				+" : "+ConfigBasis.setStrleft(ChatColor.YELLOW+building.getTrainType().name(),10)
				+" : "+ChatColor.YELLOW+ConfigBasis.setStrright(building.getTrainCounter(),3)
				+" : "+ChatColor.YELLOW+ConfigBasis.setStrright(building.getTrainTime(),3)+" Cycles"
				);
			}
		}
		msg.add(" ");
		for (String line : msg)
		{
			System.out.println(line);
		}
	}
	
	private void showSettleInfo(Settlement settle)
	{
		System.out.print("Settle "+ ConfigBasis.setStrright(settle.getId(), 2));
		System.out.print(" "+ConfigBasis.setStrleft(settle.getName() , 15));
		System.out.println("");
	}
	
	private void fillBarrack(Settlement settle)
	{
		UnitFactory uFactory = new UnitFactory(); 
		Unit militia = uFactory.erzeugeUnit(UnitType.MILITIA);
		Unit settler = uFactory.erzeugeUnit(UnitType.SETTLER);
		settle.getBarrack().addUnit(militia);
		settle.getBarrack().addUnit(settler);
		settle.getBarrack().addUnit(uFactory.erzeugeUnit(UnitType.MILITIA));
		settle.getBarrack().addUnit(uFactory.erzeugeUnit(UnitType.MILITIA));
		settle.getBarrack().addUnit(uFactory.erzeugeUnit(UnitType.MILITIA));
	}
	
	
	@Test
	public void testWriteSettledata()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms";
		LogList logList = new LogList(path);
		ItemPriceList priceList = getPriceList();
		SettlementData settleData = new SettlementData(path);
		int id = 27;
		Settlement settle = settleData.readSettledata(id , priceList); //,logList);
		showSettleInfo(settle);
		
//		fillBarrack(settle);
		showBarrack(settle);
	       long time1 = System.nanoTime();
		settleData.writeSettledata(settle);
	       long time2 = System.nanoTime();
	       System.out.println("Update Time [ms]: "+(time2 - time1)/1000000);
	}

//	@Test
//	public void testReadSettledata()
//	{
//		String path = "\\GIT\\OwnPlugins\\Realms\\plugins";
//		LogList logList = new LogList(path);
////		DataTest data     = new DataTest(logList);
//		ItemPriceList priceList = getPriceList();
////		String dataFolder = "\\GIT\\OwnPlugins\\Realms\\plugins";
//		SettlementData settleData = new SettlementData(path);
//		
//		
//		int id = 2;
//		Settlement settle = settleData.readSettledata(id , priceList,logList);
//		showSettleInfo(settle);
//		
////		fillBarrack(settle);
//		showBarrack(settle);
//		showTraining(settle);
//
//	}

}
