package net.krglok.realms.data;

import java.util.ArrayList;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;

import net.krglok.realms.core.CommonLevel;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.kingdom.LehenList;

/**
 * read Data from YML file 
 * used for initialize the RealmModel with data
 * 
 * @author Windu
 *
 */
public class DataStoreOwner extends AbstractDataStore<Owner>
{

	public DataStoreOwner(String dataFolder)
	{
		super(dataFolder, "owner", "OWNER", false);
	}
	
	public DataStoreOwner(String dataFolder, String fileName, String sectionName,
			boolean timeMessure)
	{
		super(dataFolder, fileName, sectionName, timeMessure);
	}

	@Override
	public void initDataSection(ConfigurationSection section, Owner dataObject)
	{
//		// 
//		private int id;
//		private String uuid;
//		private NobleLevel nobleLevel;
//		private CommonLevel commonLevel;
//		private int capital;
//		private String playerName;
//		private int realmID;
//		private Boolean isNPC;
//		private ArrayList<String> msg;  	// only temporrayr data
//		private SettlementList settleList;	// dynamic data initialize as subList from global settlelemntList
//		private LehenList lehenList;		// dynamic data initialize as subList from global lehenList
        config.set(MemorySection.createPath(section, "id"), dataObject.getId());
        config.set(MemorySection.createPath(section, "uuid"), dataObject.getUuid());
        config.set(MemorySection.createPath(section, "nobleLevel"), dataObject.getNobleLevel().name());
        config.set(MemorySection.createPath(section, "commonLevel"), dataObject.getCommonLevel().name());
        config.set(MemorySection.createPath(section, "capital"), dataObject.getCapital());
        config.set(MemorySection.createPath(section, "playerName"), dataObject.getPlayerName());
        config.set(MemorySection.createPath(section, "realmId"), dataObject.getKingdomId());
        config.set(MemorySection.createPath(section, "isNPC"), dataObject.isNPC());

	}

	@Override
	public Owner initDataObject(ConfigurationSection data)
	{
		Owner owner = new Owner();
		owner.setId(data.getInt("id"));
		owner.setUuid(data.getString("uuid"));
		String ref = data.getString("nobleLevel");
		owner.setNobleLevel(NobleLevel.valueOf(ref));
		owner.setCommonLevel(CommonLevel.valueOf(data.getString("commonLevel")));
		owner.setCapital(data.getInt("capital"));
		owner.setPlayerName(data.getString("playerName"));
		owner.setKingdomId(data.getInt("realmId"));
		owner.setIsNPC(data.getBoolean("isNPC"));

		return owner;
	}
	
	
	
}
