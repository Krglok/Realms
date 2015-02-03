package net.krglok.realms.data;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.npc.BackpackList;
import net.krglok.realms.npc.EthnosType;
import net.krglok.realms.npc.GenderType;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.unit.UnitType;

public class DataStoreNpc extends AbstractDataStore<NpcData> 
{

	public DataStoreNpc(String dataFolder,SQliteConnection sql)
	{
		super(dataFolder, "npc", "NPCNAME", false, sql);
	}

	/**
	 * Override this for the concrete class
	 * 
	 * @param T dataObject, instance of real data Class
	 */
	@Override
	public void initDataSection(ConfigurationSection section, NpcData dataObject)
	{
//		private int Id;
//		private NPCType npcType ;
//		private UnitType unitType ;
//		private String name;
//		private GenderType gender;
//		private int age;
//		private EthnosType ethno;
//		private boolean immortal;
//		
//		private int settleId;
//		private int homeBuilding;
//		private int workBuilding;
//		
//		private double money;
//		private Item itemInHand;
//		private BackpackList backPack;
//		private boolean isMaried;
//		private int npcHusband;
//		private int pcHusband;
//		private int mother;
//		private int father;

		section.set("id", dataObject.getId());
		section.set("npcType", dataObject.getNpcType().name());
		section.set("unitType", dataObject.getUnitType().name());
		section.set("noble", dataObject.getNoble().name());
		section.set("name", dataObject.getName());
		section.set("gender", dataObject.getGender().name());
		section.set("ageDay", dataObject.getAgeDay());
		section.set("ethno", dataObject.getEthno().name());
		section.set("immortal", dataObject.isImmortal());
		section.set("isAlive", dataObject.isAlive());
		section.set("settleId", dataObject.getSettleId());
		section.set("homeBuilding", dataObject.getHomeBuilding());
		section.set("workBuilding", dataObject.getWorkBuilding());
		section.set("money", dataObject.getMoney());
		section.set("isMaried", dataObject.isMaried());
		section.set("npcHusband", dataObject.getNpcHusband());
		section.set("pcHusband", dataObject.getPcHusband());
		section.set("mother", dataObject.getMother());
		section.set("father", dataObject.getFather());
		section.set("schwanger", dataObject.getSchwanger());
		section.set("producer", dataObject.getProducer());
		section.set("happiness", dataObject.getHappiness());
		section.set("health", dataObject.getHealth());
		section.set("regimentId", dataObject.getRegimentId());
		section.set("power", dataObject.getPower());

		HashMap<String,String>values = new HashMap<String,String>();
		
		values.put(dataObject.getItemInHand().ItemRef(), String.valueOf(dataObject.getItemInHand().value()));
		section.set("itemInHand", values);

		values = new HashMap<String,String>();
    	for (Item itemref : dataObject.getBackPack().values())
    	{
    		values.put(itemref.ItemRef(), String.valueOf(itemref.value()));
    	}
        section.set("backpack", values);

	}


	/**
	 * Override this for the concrete class

	 * @return T , real data Class
	 */
	@Override
	public NpcData initDataObject(ConfigurationSection data)
	{
//		// 
		NpcData npc = new NpcData();
		npc.setId(data.getInt("id"));
		npc.setNpcType(NPCType.valueOf(data.getString("npcType","BEGGAR")));
		npc.setUnitType(UnitType.valueOf(data.getString("unitType","SETTLER")));
		npc.setNoble(NobleLevel.valueOf(data.getString("noble","COMMONER"))); 
		npc.setName(data.getString("name"));
		npc.setGender(GenderType.valueOf(data.getString("gender","MAN")));
		npc.setAgeDay(data.getInt("ageDay"));
		npc.setEthno(EthnosType.valueOf(data.getString("ethno","HUMAN")));
		npc.setImmortal(data.getBoolean("immortal",false));
		npc.setAlive(data.getBoolean("isAlive",false));
		npc.setMaried(data.getBoolean("isMaried",false));
		npc.setImmortal(data.getBoolean("immortal",false));
		npc.setSettleId(data.getInt("settleId",0));
		npc.setHomeBuilding(data.getInt("homeBuilding",0));
		npc.setWorkBuilding(data.getInt("workBuilding",0));
		npc.setMoney(data.getDouble("money",0.0));
		npc.setNpcHusband(data.getInt("npcHusband",0));
		npc.setPcHusband(data.getInt("pcHusband",0));
		npc.setMother(data.getInt("mother",0));
		npc.setFather(data.getInt("father",0));
		npc.setSchwanger(data.getInt("schwanger",0));
		npc.setProducer(data.getInt("producer",0));
		npc.setHappiness(data.getDouble("happiness",0.1));
		npc.setHealth(data.getInt("health",20));
		npc.setRegimentId(data.getInt("regimentId",0));
		npc.setPower(data.getInt("power",1));
		

		if (data.isConfigurationSection("itemInHand"))
		{
			Item item = new Item();
			Map<String,Object> itemList = data.getConfigurationSection( "itemInHand").getValues(false);
			if (itemList != null)
			{
		    	for (String ref : itemList.keySet())
		    	{
		    		
		    		int value = Integer.valueOf(data.getString( "itemInHand."+ref,"0"));
		            item.setItemRef(ref);
		            item.setValue(value);
		    	}    
				npc.setItemInHand(item);
			}
		}
		if (data.isConfigurationSection("backpack"))
		{
		    BackpackList iList = new BackpackList();
			Map<String,Object> itemList = data.getConfigurationSection( "backpack").getValues(false);
			if (itemList != null)
			{
		    	for (String ref : itemList.keySet())
		    	{
		    		int value = Integer.valueOf(data.getString( "backpack."+ref,"0"));
		            iList.addItem(ref, value);
		    	}
		    	npc.setBackPack(iList);
			}
		}
		return npc;
	}

}
