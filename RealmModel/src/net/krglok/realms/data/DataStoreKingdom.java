package net.krglok.realms.data;

import net.krglok.realms.kingdom.Kingdom;

import org.bukkit.configuration.ConfigurationSection;



public class DataStoreKingdom extends AbstractDataStore<Kingdom>
{

	public DataStoreKingdom(String dataFolder)
	{
		super(dataFolder, "kingdom", "KINGDOM", false);
	}

	/**
	 * Override this for the concrete class
	 * 
	 * @param Kingdom dataObject, instance of real data Class
	 */
	@Override
	public void initDataSection(ConfigurationSection section, Kingdom dataObject)
	{
//		private int id;
//		private String name;
//		private Owner owner;
//		private Boolean isNPCkingdom;		
//		private MemberList memberList:		// dynamic data initialize as subList from global Owner
//		private SettlementList settlements; // dynamic data initialize as subList from global settlements

		section.set("id", dataObject.getId());
		section.set("name", dataObject.getName());
		section.set("ownerId", dataObject.getOwner().getId());
		section.set("isNPC", dataObject.isNPCkingdom());

	}


	/**
	 * Override this for the concrete class

	 * @return Kingdom , real data Class
	 */
	@Override
	public Kingdom initDataObject(ConfigurationSection data)
	{
//	
		Kingdom kingdom = new Kingdom();
		kingdom.setId(data.getInt("id",0));
		kingdom.setName(data.getString("name"));
		kingdom.setOwnerId(data.getInt("ownerId",0));
		kingdom.setIsNPCkingdom(data.getBoolean("isNPC",true));
		
		return kingdom;
	}
	
}
