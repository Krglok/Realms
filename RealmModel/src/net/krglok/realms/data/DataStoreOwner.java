package net.krglok.realms.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;

import net.krglok.realms.core.Building;
import net.krglok.realms.core.CommonLevel;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.kingdom.LehenList;
import net.krglok.realms.science.Achivement;
import net.krglok.realms.science.AchivementName;
import net.krglok.realms.science.AchivementType;

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

        HashMap<String,String> values = new HashMap<String,String>();
        for (Achivement achiv : dataObject.getAchivList().values())
        {
            
        	values.put(achiv.getName(),String.valueOf(achiv.isEnaled()));
        }
        config.set((MemorySection.createPath(section,"Achivement")), values);
        
	}

	@Override
	public Owner initDataObject(ConfigurationSection data)
	{
		Owner owner = new Owner();
		owner.setId(data.getInt("id",0));
		owner.setUuid(data.getString("uuid",""));
		String ref = data.getString("nobleLevel","COMMONER");
		owner.setNobleLevel(NobleLevel.valueOf(ref));
		owner.setCommonLevel(CommonLevel.valueOf(data.getString("commonLevel","COLONIST")));
		owner.setCapital(data.getInt("capital",0));
		owner.setPlayerName(data.getString("playerName"));
		owner.setKingdomId(data.getInt("realmId",0));
		owner.setIsNPC(data.getBoolean("isNPC",true));

		Map<String,Object> values = data.getConfigurationSection("Achivement").getValues(false);
		for (String key : values.keySet())
		{
			boolean isEnabled = data.getBoolean("Achivement,"+key);
			Achivement achiv = new Achivement(AchivementType.valueOf(Achivement.splitNameTyp(key)), AchivementName.valueOf(Achivement.splitNameName(key)), isEnabled);
		}
		
		return owner;
	}
	
	
	
}
