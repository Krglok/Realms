package net.krglok.realms.data;

import org.bukkit.configuration.ConfigurationSection;

import net.krglok.realms.core.MemberLevel;
import net.krglok.realms.core.Owner;

/**
 * read Data from YML file 
 * used for initialize the RealmModel with data
 * 
 * @author Windu
 *
 */
public class DataStoreOwner extends DataStore<Owner>
{

	public DataStoreOwner(String dataFolder, String fileName, String sectionName,
			boolean timeMessure)
	{
		super(dataFolder, fileName, sectionName, timeMessure);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initDataSection(ConfigurationSection section, Owner dataObject)
	{
//		// TODO Auto-generated method stub
//		private int id;
//		private MemberLevel level;
//		private int capital;
//		private String playerName;
//		private int realmID;
//		private Boolean isNPC;


	}

	@Override
	public Owner initDataObject(ConfigurationSection data)
	{
//		// TODO Auto-generated method stub
		
		return null;
	}
	
	
	
}
